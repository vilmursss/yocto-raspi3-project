inherit update-rc.d

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/vilmursss/led-control-device-driver.git;protocol=ssh;branch=master \
           file://S99ledctrldevice \
           file://led_control.sh"

PV = "1.0+git${SRCPV}"

SRCREV = "dc38c6d8b44479a72789934cffbaef949d91bd47"

S = "${WORKDIR}/git"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S99ledctrldevice"
INITSCRIPT_PARAMS = "defaults 99"

inherit module

# Add extra CFLAGS
CFLAGS = "-std=gnu17"

# Append to EXTRA_OEMAKE for the do_compile task
EXTRA_OEMAKE:append:task-compile = " -C ${STAGING_KERNEL_DIR} M=${S}"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR} EXTRA_CFLAGS='${CFLAGS}'"

FILES:${PN} += "${sysconfdir}/init.d/S99ledctrldevice"
FILES:${PN} += "${bindir}/led_control"

do_configure() {
    :
}

do_compile() {
    oe_runmake
}

do_install() {
    # Install the init script
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/S99ledctrldevice ${D}${sysconfdir}/init.d/S99ledctrldevice
    
    # Install control script
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/led_control.sh ${D}${bindir}/led_control

    # Install device driver
    install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/
    install -m 0755 ${S}/led_control.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/
}

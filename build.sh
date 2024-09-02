#!/bin/bash
# Script for building Raspberry Pi 3 image

set -e

REPO_ROOT_DIR=$(pwd)

# Initialize and update submodules
git submodule init
git submodule sync
git submodule update

# Source the Yocto environment setup script
source poky/oe-init-build-env

# Define the paths for the configuration files
YOCTO_BUILD_CONF_DIR="${BUILDDIR}/conf"

# Copy local.conf and bblayers.conf from the repository root conf directory to the Yocto build conf directory
cp "${REPO_ROOT_DIR}/conf/local.conf" "${YOCTO_BUILD_CONF_DIR}/local.conf"
cp "${REPO_ROOT_DIR}/conf/bblayers.conf" "${YOCTO_BUILD_CONF_DIR}/bblayers.conf"

# Build the image
bitbake core-image-minimal

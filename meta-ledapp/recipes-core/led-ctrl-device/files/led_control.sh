#!/bin/sh

# Script to interact with /dev/led-control device driver

usage() {
    echo "Usage: $0 {read|write <state>}"
    echo "Commands:"
    echo "  read            Read the current state of the LED"
    echo "  write <state>   Write a new state to the LED (e.g., 'on' or 'off')"
    exit 1
}

read_led_state() {
    echo "Reading LED state..."
    cat /dev/led-control
}

write_led_state() {
    new_state=$1
    echo "Writing new LED state: $new_state"
    echo $new_state > /dev/led-control
}

if [ $# -lt 1 ]; then
    usage
fi

command=$1

case $command in
    read)
        read_led_state
        ;;
    write)
        if [ $# -ne 2 ]; then
            usage
        fi
        new_state=$2
        write_led_state $new_state
        ;;
    *)
        usage
        ;;
esac

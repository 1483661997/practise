from OTA import update_firmware, update_firmware_break, DeviceCommands
from PowerSupplu import power_control
from picocom import mcu_get_data
import time
from logger import setup_logger,StreamToLogger
import logging
import sys
import logger

commands = DeviceCommands()

def loader_fireware():

    print("****************转到loader状态*************************")
    mcu_get_data(['version'])
    update_firmware(commands.ethernet_upgrade_b)
    # power_control('off')
    # time.sleep(3)
    # power_control('on')
    # time.sleep(10)
    mcu_get_data(['version'])
    print("****************已经转到loader状态*************************")
    print("****************已经转到loader状态*************************")
    print("****************已经转到loader状态*************************")


def uart_fireware():
    mcu_get_data(['version'])
    update_firmware(commands.uart_update)
    power_control('off')
    time.sleep(3)
    power_control('on')
    time.sleep(10)
    mcu_get_data(['version'])

def eth_fireware():
    mcu_get_data(['version'])
    update_firmware(commands.ethernet_upgrade)
    power_control('off')
    time.sleep(3)
    power_control('on')
    time.sleep(10)
    mcu_get_data(['version'])

def can_fimeware():
    mcu_get_data(['version'])
    update_firmware(commands.pcan_upgrade)
    power_control('off')
    time.sleep(3)
    power_control('on')
    time.sleep(10)
    mcu_get_data(['version'])

def can_fireware_break():

    break_percent = [10,30,50,70,99]
    for number in break_percent:
        print("///////////////////////////////////////////////")
        mcu_get_data(['version'])
        print("****************升级前*************************")
        
        commands = DeviceCommands()
        # update_firmware(commands.ethernet_upgrade)
        update_firmware_break(number, commands.pcan_upgrade)

        # power_control('off')
        time.sleep(3)
        power_control('on')
        time.sleep(10)
        print("****************升级后**************************")
        mcu_get_data(['version'])

def uart_frimware_break():
    break_percent = [10,30,50,70,99]
    for number in break_percent:
        print("///////////////////////////////////////////////")
        mcu_get_data(['version'])
        print("****************升级前*************************")
        
        commands = DeviceCommands()
        # update_firmware(commands.ethernet_upgrade)
        update_firmware_break(number, commands.uart_update)

        # power_control('off')
        time.sleep(3)
        power_control('on')
        time.sleep(10)
        print("****************升级后**************************")
        mcu_get_data(['version'])

def main(): 
 
    # loader_fireware()
    eth_fireware()
    # power_control('off')
    # time.sleep(3)
    # power_control('on')

    # loader_fireware()
    # uart_fireware()

    # loader_fireware()
    # can_fimeware()


    
    
    # #loader can uart
    # mcu_get_data(['version'])
    # print("****************升级前*************************")
        
    # update_firmware(commands.ethernet_upgrade_b)
    #     # update_firmware_break(number, commands.ethernet_upgrade)

    # power_control('off')
    # time.sleep(3)
    # power_control('on')

    # print("****************升级后**************************")
    # mcu_get_data(['version'])



   

if __name__ == "__main__":
 
    main()
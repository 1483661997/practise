#!/usr/bin/env python3
# Authors: HaobaiSOng

import subprocess
import os
import time
import sys
import signal
from test_main_power_supply import power_control
#from test_main_mcu_get_data import mcu_get_data
from test_main_logger import setup_logging,isolation
#from common.experimental.clover_esw_test_code.src.main_system.log_path import LOG_PATHS,TOOL_PATH
#from common.experimental.clover_esw_test_code.scripts.Logger import setup_logger  # type: ignore

# logger = setup_logger()
logger = setup_logging()


class DeviceCommands:

    BASE_PATH = '/home/weiduo/psu'

    MCU_FIRMWARE_PATH   = os.path.join(BASE_PATH,  'psu_0712', 'psu', 'psu_mcu.pad2')

    MCU_UPDATER_PACKAGE = os.path.join(BASE_PATH,  'updater_package')

    #IMU_CLI_PACKAGE     = os.path.join(TOOL_PATH.get('imu'), 'pony_imu_cli_package')

    def __init__(self):
        
        self.ethernet_upgrade = [
            self.MCU_UPDATER_PACKAGE, 'update',
            '-d', 'psu_mcu',
            '-e',
            '-i', '1',
            '-p', self.MCU_FIRMWARE_PATH
        ]
        self.ethernet_upgrade_b = [
            self.MCU_UPDATER_PACKAGE, 'update',
            '-d', 'psu_mcu',
            '-e',
            '-i', '1',
            '-p', self.MCU_FIRMWARE_PATH
        ]
        self.ethernet_query = [
            self.MCU_UPDATER_PACKAGE, 'cmd',
            '-d', 'psu_mcu',
            '-e',
            '-i', '1',
            '-v'
        ]

        '''self.uart_update = [
            self.MCU_UPDATER_PACKAGE, 'update',
            '-d', 'psu_mcu',
            '-e',
            '--target_ip', '192.168.2.64',
            '--target_port', '32888',
            '-i', '17',
            '-p', self.MCU_FIRMWARE_PATH
        ]'''

        '''self.pcan_upgrade = [
            self.MCU_UPDATER_PACKAGE, 'update',
            '-d', 'clover_main_mcu',
            '-c',
            '-i', '2',
            '-p', self.MCU_FIRMWARE_PATH
        ]

        self.pcan_query = [
            self.MCU_UPDATER_PACKAGE, 'cmd',
            '-d', 'clover_main_mcu',
            '-c',
            '-i', '2',
            '-v'
        ]
        
        self.imu_query = [
            self.IMU_CLI_PACKAGE, 
            '--ip', '192.168.1.64', 
            '--target_device_id', '1794', 
            '--local_device_id', '4', 
            '-u', '0:125:log'

        ]'''

commands = DeviceCommands()

def get_firmware_version(command):
    """
    执行 updater_package 命令查询固件版本，并在执行后立即获取输出。
    """
    try:
        process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        # 等待命令执行完成或等待一段时间
        output, error = process.communicate()

        if process.returncode != 0:
            logger.error("Command failed with error: " + error)
            return None

        # 检查 stderr 和 stdout
        combined_output = output.strip() + error.strip()
        logger.info('Combined output: ' + combined_output)

        # 分析输出，寻找版本信息
        if "Got psu_mcu version:" in combined_output:
            version_info = combined_output.split("INFO")[-1].strip()
            logger.info("Extracted Version Info: " + version_info)
            return version_info

        logger.info("No version information found in output.")
    except subprocess.CalledProcessError as e:
        logger.error(f"Error executing command: {e}")
    except Exception as e:
        logger.error(f"An unexpected error occurred: {e}")
    return None

# def update_firmware(command):
#     """
#     使用 updater_package 工具更新固件。
#     """
#     # 执行命令
#     try:
#         process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

#         for line in process.stdout:
#             logger.debug(line)

#         # 等待命令执行完成
#         process.wait()

#         # 检查命令执行是否成功self.UPDATER_PACKAGE
#         if process.returncode != 0:
#             # 读取错误输出
#             error = process.stderr.read()
#             logger.error("STDERR: ", error)
#         else:
#             logger.info('执行成功')

#     except subprocess.CalledProcessError as e:
#         # 如果命令执行失败，打印错误信息
#         logger.error("更新固件发生错误:", e)
#     except Exception as e:
#         # 捕获其他可能的错误
#         logger.error("error:", e)


class TimeoutException(Exception):
    pass

def timeout_handler(signum, frame):
    raise TimeoutException

def update_firmware(command):
    """
    使用 updater_package 工具更新固件，并设置超时为10分钟。
    """
    signal.signal(signal.SIGALRM, timeout_handler)
    signal.alarm(600)  # 设置超时时间为600秒（10分钟）

    try:
        process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

        for line in process.stdout:
            logger.debug(line.strip())  # 确保移除行尾的换行符

        # 等待命令执行完成
        process.wait()

        # 取消超时定时器
        signal.alarm(0)

        # 检查命令执行是否成功
        if process.returncode != 0:
            # 读取错误输出
            error = process.stderr.read()
            logger.error(f"STDERR: {error}")
            return False
        else:
            logger.info('执行成功')
            return True

    except TimeoutException:
        process.terminate()  # 尝试终止进程
        logger.error("固件更新超时，进程已终止")
        return False
    except subprocess.CalledProcessError as e:
        logger.error(f"更新固件发生错误: {e}")
        return False
    except Exception as e:
        logger.error(f"在固件更新过程中遇到错误: {e}")
        return False

def update_firmware_break(break_at_percent, command, break_type='power', interface_name=None):
    """
    使用 updater_package 工具更新固件，并在指定的进度处中断更新。
    参数:
    - break_at_percent: 中断的进度百分比
    - command: 执行固件更新的命令
    - break_type: 'power' 或 'network'，指定中断类型
    - interface_name: 如果中断类型是 'network'，需要提供网络接口名称
    """
    try:
        process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

        for line in process.stdout:
            logger.debug(line)
            
            if f'The upgrade has finished :{break_at_percent}%.' in line:
                if break_type == 'power':
                    power_control('off')
                elif break_type == 'network' and interface_name:
                    disable_network_interface(interface_name)
                    time.sleep(5)
                else:
                    logger.warning("中断类型不存在，只有[power] | [network]")

                process.kill()
                process.wait()
                logger.info(f"固件更新在 {break_at_percent}% 由于 {break_type} 被打断。")
                return

        process.wait()

        if process.returncode != 0:
            error = process.stderr.read()
            logger.error("STDERR:", error)
        else:
            logger.info("执行成功")

    except subprocess.CalledProcessError as e:
        logger.error("固件更新错误: ",e)
    except Exception as e:
        logger.error("错误L:", e)
    finally:
        if break_type == 'network' and interface_name:
            enable_network_interface(interface_name)

def disable_network_interface(interface_name):
    try:
        # 使用 'ip' 命令关闭指定的网络接口
        subprocess.run(['sudo', 'ip', 'link', 'set', interface_name, 'down'], check=True)
        print(f"指定网口 {interface_name} 已关闭.")
    except subprocess.CalledProcessError as e:
        print(f"未能关闭指定网口 {interface_name}: {e}")
    except Exception as e:
        print(f"出错辣: {e}")

def enable_network_interface(interface_name):
    try:
        # 使用 'ip' 命令启用指定的网络接口
        subprocess.run(['sudo', 'ip', 'link', 'set', interface_name, 'up'], check=True)
        print(f"指定网口 {interface_name} 已开启.")
    except subprocess.CalledProcessError as e:
        print(f"未能开启指定网口 {interface_name}: {e}")
    except Exception as e:
        print(f"出错辣 : {e}")


def call_update_break_multiple_times(n, break_at_percent, command, type='power', network='enp8s0'):
    """
        调用 update_firmware_break 函数n次，并统计成功和失败的次数。
    """
    successful_updates = 0
    failed_updates = 0
    for i in range(n):
        logger.info(f"开始第 {i+1} 次固件更新...")
        update_break_before = get_firmware_version(commands.ethernet_query)
        update_firmware_break(break_at_percent, command, type, network)
        
        power_control('off')
        time.sleep(5)
        power_control('on')
        time.sleep(0)

        update_break_after = get_firmware_version(commands.ethernet_query)

        if compare_data(update_break_before, update_break_after):
            failed_updates += 1
        else :
            successful_updates += 1
        
        logger.info(f"第 {i+1} 次固件更新尝试完成...")

    logger.info(f"更新总结: {n}次升级中断， 在{break_at_percent}%被 {type} 中断， 共{successful_updates} 次出现重启后版本异常，共{failed_updates} 次重启后版本无异常")

def call_update_multiple_times(n, command):
    """
        调用 update_firmware 函数n次，并统计成功和失败的次数。
    """
    successful_updates = 0
    failed_updates = 0

    for i in range(n):
        logger.info(f"开始第 {i+1} 次固件更新...")
        update_break_before = get_firmware_version(commands.ethernet_query)

        
        if(not update_firmware(command)):
            logger.error(f"第 {i+1} 次固件更新失败，程序退出。")
            sys.exit(1)  # 非零退出码表示错误
        
        power_control('off')
        time.sleep(5)   
        power_control('on')
        time.sleep(10)

        update_break_after = get_firmware_version(commands.ethernet_query)
        


        if compare_data(update_break_before, update_break_after):
            failed_updates += 1

            logger.error(f"第 {i+1} 次固件更新失败，程序退出。")
            sys.exit(1)  # 非零退出码表示错误
        else :
            successful_updates += 1
        
        logger.info(f"第 {i+1} 次固件更新尝试完成...")

    logger.info(f"更新总结: {n}次升级中断， {successful_updates} 次成功，{failed_updates} 次失败")

def call_update_multiple_times_no_reboot(n, command):
    """
        调用 update_firmware 函数n次，并统计成功和失败的次数。
    """

    for i in range(n):
        logger.info(f"开始第 {i+1} 次固件更新...")

        update_break_before = get_firmware_version(commands.ethernet_query)
        
        update_firmware(command)

        update_break_after = get_firmware_version(commands.ethernet_query)


        
        
        logger.info(f"第 {i+1} 次固件更新尝试完成...")



def compare_data(before, after):
    logger.info(isolation)
    # logger.info('更新前的clover main mcu版本如下: %s', before)
    logger.info('更新前的psu mcu版本如下: %s', str(before))


    logger.info('更新后的psu mcu版本如下: %s', str(after) )

    if before == after:
        logger.info("固件信息相同，没有变化。")
        return True
    else:
        logger.info("固件信息发生变化。")
        return False

def main():
    
 
    # update_firmware(commands.ethernet_upgrade)
    # call_update_break_multiple_times(10,10,commands.ethernet_upgrade, 'network', 'enp60s0')
    # call_update_break_multiple_times(10,10,commands.ethernet_upgrade, 'power')
    # call_update_break_multiple_times
    call_update_multiple_times(2, commands.ethernet_upgrade)
    # version_info = get_firmware_version(commands.ethernet_query)
    # if version_info:
    #    print(f"Firmware Version: {version_info}")
    # else:
    #     print("Failed to get firmware version.")

if __name__ == "__main__":
    main()
    
   

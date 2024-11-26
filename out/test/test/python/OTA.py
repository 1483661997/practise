import serial
import time
import subprocess
import pyvisa
import logging
import datetime
import os
from PowerSupplu import power_control
import sys
import paramiko

class DeviceCommands:
    # FIRMWARE_PATH = '/home/haobaisong/Documents/work/Kunlun/release/release_v2_clover-main-mcu_v0-3_RC13/b0200.00.a01'
    FIRMWARE_PATH = '/home/haobaisong/Documents/work/Kunlun/release/release_0.3_imu_version'

    BFIRMWARE_PATH = '/home/haobaisong/Documents/work/Kunlun/release/release_bad/broken'
    # FIRMWARE_PATH = '/home/haobaisong/Documents/work/Kunlun/release/release_6MB/clover_main_mcu.pad2'

    UPDATER_PACKAGE = '/home/haobaisong/Documents/work/Kunlun/release/update/updater_package'

    MCT_MA1X_MAIN = '/home/nvidia/orin_ota/mct_ma1x_main_package'

    IMU_FIRMWARE_PATH = '/home/nvidia/orin_ota/IPS_MA1_V1.3.4.0315.bin' # IPS_MA1_V1.3.4.0315.bin
    
    ''''
    ./work/ponyai/.sub-repos/bazel-bin/sensors/tools/mct_ma1x/mct_ma1x_main 
    --updater_file /home/chongzhou/firmware/mct_imu/updater.config    
    --tool_file /home/chongzhou/firmware/mct_imu/linux_ota/LIB/libUpgrade.so 
    --logtostderr --firmware_file /home/chongzhou/Downloads/IPS_MA1_V1.3.4.0316.bin 
    '''
    def __init__(self):
        
        self.ethernet_upgrade = [
            self.UPDATER_PACKAGE, 'update',
            '-d', 'clover_main_mcu',
            '-e',
            '-i', '1',
            '-p', self.FIRMWARE_PATH
        ]
        self.ethernet_upgrade_b = [
            self.UPDATER_PACKAGE, 'update',
            '-d', 'clover_main_mcu',
            '-e',
            '-i', '1',
            '-p', self.BFIRMWARE_PATH
        ]
        self.ethernet_query = [
            self.UPDATER_PACKAGE, 'cmd',
            '-d', 'clover_main_mcu',
            '-e',
            '-i', '1',
            '-v'
        ]

        self.uart_update = [
            self.UPDATER_PACKAGE, 'update',
            '-d', 'clover_main_mcu',
            '-e',
            '--target_ip', '192.168.2.64',
            '--target_port', '32888',
            '-i', '17',
            '-p', self.FIRMWARE_PATH
        ]

        self.pcan_upgrade = [
            self.UPDATER_PACKAGE, 'update',
            '-d', 'clover_main_mcu',
            '-c',
            '-i', '2',
            '-p', self.FIRMWARE_PATH
        ]

        self.pcan_query = [
            self.UPDATER_PACKAGE, 'cmd',
            '-d', 'clover_main_mcu',
            '-c',
            '-i', '2',
            '-v'
        ]
        
        self.imu_query = [
            '/home/haobaisong/Documents/work/Kunlun/components/imu/pony_imu_cli_package', 
            '--ip', '192.168.1.64', 
            '--target_device_id', '1794', 
            '--local_device_id', '4', 
            '-u', '0:125:log'
        ]
        
        self.imu_version = [
            '/home/haobaisong/Documents/work/Kunlun/components/imu/pony_imu_cli_package', 
            '--ip', '192.168.1.64', 
            '--target_device_id', '1794', 
            '--local_device_id', '4', 
            '-l'
        ]


        self.imu_update = [
            self.MCT_MA1X_MAIN,
            '--updater_file', '/home/nvidia/orin_ota/update.config',
            '--tool_file', '/home/nvidia/orin_ota/libUpgrade-aarch64.so',
            '--logtostderr', '--firmware_file', self.IMU_FIRMWARE_PATH

        ]

    def update_firmware_path(self, condition):
        # 根据条件设置不同的固件路径
        if condition == '0315':
            new_path = '/home/nvidia/orin_ota/IPS_MA1_V1.3.4.0315.bin'
        else:
            new_path = '/home/nvidia/orin_ota/IPS_MA1_V1.3.4.0316.bin'
        
        # 更新固件文件路径
        for i, item in enumerate(self.imu_update):
            if item == '--firmware_file':
                self.imu_update[i + 1] = new_path
                break

    def get_update_command(self):
        return self.imu_update

    def execute_remote_script(self, hostname, username, password, script_path):
        # 建立SSH连接
        client = paramiko.SSHClient()
        client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        client.connect(hostname, username=username, password=password)
        
        # 执行远程Python脚本
        # stdin, stdout, stderr = client.exec_command(f'python {script_path}')
        stdin, stdout, stderr = client.exec_command('/home/nvidia/orin_ota/mct_ma1x_main_package --updater_file /home/nvidia/orin_ota/updater.config --tool_file /home/nvidia/orin_ota/libUpgrade-aarch64.so --logtostderr --firmware_file /home/nvidia/orin_ota/IPS_MA1_V1.3.4.0315.bin'
)
        
        # 等待命令执行完成并获取输出
        output = stdout.read()
        error = stderr.read()

        # 关闭连接
        client.close()

        return output, error
        
def update_firmware(command):
    """
    使用 updater_package 工具更新固件。
    """

    commands = DeviceCommands()
    # 执行命令
    try:
        # 使用 subprocess.Popen 运行命令
        process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

        # 实时打印标准输出和错误输出
        # for line in process.stdout:
        #     print(line, end='')  # 打印每一行输出，不增加额外的换行符

        for line in process.stdout:
            sys.stdout.write('\r' + line.strip())  # 使用 \r 回到行首，并使用 strip() 去除行尾的换行符
            sys.stdout.flush()  # 确保立即输出

        # 等待命令执行完成
        process.wait()

        # 检查命令执行是否成功
        if process.returncode != 0:
            # 读取错误输出
            error = process.stderr.read()
            print("STDERR:", error)
        else:
            print("Command executed successfully")

    except subprocess.CalledProcessError as e:
        # 如果命令执行失败，打印错误信息
        print("更新固件发生错误:", e)
    except Exception as e:
        # 捕获其他可能的错误
        print("error:", e)

def compare_data(before, after):
    logger.info(isolation)
    # logger.info('更新前的clover main mcu版本如下: %s', before)
    logger.info('更新前的clover main mcu版本如下: %s', str(before))

    logger.info('更新后的clover main mcu版本如下: %s', str(after))

    if before == after:
        logger.info("固件信息相同,没有变化。")
        return True
    else:
        logger.info("固件信息发生变化。")
        return False

def get_firmware_version(command):
    """
    执行 updater_package 命令查询固件版本,并在执行后立即获取输出。
    """
    print(command)
    try:
        process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        # 等待命令执行完成或等待一段时间
        output, error = process.communicate()

        if process.returncode != 0:
            print(error)
            # logger.error("Command failed with error: ", error)
            return None
        
        # 检查 stderr 和 stdout
        combined_output = output.strip() + error.strip()
        # logger.info('Combined output: ', combined_output)
        # print(combined_output)
        # 分析输出,寻找版本信息
        if "SW_VERSION:" in combined_output:
            parts = output.split()
            # version_info = combined_output.split("INFO")[-1].strip()
            # sw_version = [part.split(':')[1] for part in parts if "SW_VERSION" in part][0]
            # print(sw_version)
            # print(parts[8].strip())
            # logger.info("Extracted Version Info: ", version_info)
            # return output
            return parts[8].strip()

        # logger.info("No version information found in output.")
    except subprocess.CalledProcessError as e:
        print(e)
        # logger.error(f"Error executing command: {e}")
    except Exception as e:
        print(e)
        # logger.error(f"An unexpected error occurred: {e}")
    return None


    '''
    
    My name is Haobai Song, 
    and I am a software engineer 
    specializing in backend development, 
    particularly with Java technologies. 
    I hold a bachelor's degree 
    in Software Engineering 
    from China University of Petroleum in Qingdao 
    and am currently pursuing 
    a master's degree 
    in the same field 
    at Beijing Institute of Technology.

    My professional journey 
    includes working at CETC Thinks Instruments Co., Ltd. 
    as an engineer 
    in the Common Technology Research Department, 
    where I significantly 
    enhanced B/S application performance 
    by optimizing TCP and UDP communication 
    within the Netty framework. 
    This role involved improving data 
    transfer response times to 
    less than 10 milliseconds, 
    ensuring efficient data exchanges
    between instruments and higher-level systems.

    Most recently, I contributed to the development of an online platform for exams and classroom quizzes 
    called the Peer Teaching Platform. This project employed technologies such as SpringCloud, MyBatis-Plus, 
    and RabbitMQ within a microservices architecture, supported by Docker for deployment. 
    My responsibilities included developing and managing services like user management, course management, 
    and a file resource service. 
    I was particularly focused on implementing a microservice messaging architecture 
    using RabbitMQ to enhance system responsiveness and reliability.

    My technical skills are well-rounded and include a deep understanding of Java basics like collections, 
    reflection, and I/O, along with advanced knowledge in concurrent programming, 
    JVM, and frameworks such as Spring and Mybatis. Additionally, 
    I am proficient in MySQL database design, Linux, Docker commands, 
    and developing tools like IDEA and Maven.

    I am passionate about creating robust software solutions that enhance user experiences and business outcomes. 
    I am eager to bring my expertise and enthusiasm for software development to new opportunities 
    where I can contribute to challenging projects and innovative solutions.

    Thank you for considering my application.

    '''
def update_firmware_break(break_at_percent, command):
    """
    使用 updater_package 工具更新固件。
    """

    # commands = DeviceCommands()

    # 执行命令
    try:
        # 使用 subprocess.Popen 运行命令
        # process = subprocess.Popen(commands.ethernet_upgrade, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        process = subprocess.Popen(command, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)


        # 实时打印标准输出和错误输出
        for line in process.stdout:
            sys.stdout.write('\r' + line.strip())  # 使用 \r 回到行首，并使用 strip() 去除行尾的换行符
            sys.stdout.flush()  # 确保立即输出
            
            if f'The upgrade has finished :{break_at_percent}%.' in line:
                power_control('off')
                # 杀死子进程
                process.kill()
                # 等待子进程退出，防止僵尸进程
                process.wait()
                print(f"固件更新在 {break_at_percent}% 打断.")
                return
        # for(int i = 0; i < )
        # 等待命令执行完成
        process.wait()

        # 检查命令执行是否成功
        if process.returncode != 0:
            # 读取错误输出
            error = process.stderr.read()
            print("STDERR:", error)
        else:
            print("Command executed successfully")

    except subprocess.CalledProcessError as e:
        # 如果命令执行失败，打印错误信息
        print("更新固件发生错误:", e)
    except Exception as e:
        # 捕获其他可能的错误
        print("error:", e)

        
def call_update_multiple_times(n, break_at_percent, command):
    """
    调用 update_firmware_break 函数 n 次。
    """
    for i in range(n):
        print(f"开始第 {i+1} 次固件更新尝试...")
        update_firmware_break(break_at_percent, command)
        print(f"第 {i+1} 次固件更新尝试完成。\n")

def call_update_imu_multiple_times(n, command, updater):
    
    for i in range(n):
        
        updater.execute_remote_script('192.168.1.100', 'nvidia', 'nvidia', updater.imu_update)
        
        
    

def main1():
    # logging.info("程序开始执行")


    updater = DeviceCommands()
    # print(updater.get_update_command())

    # updater.update_firmware_path('0316')
    # print(updater.get_update_command())
    # updater.update_firmware_path('0315')
    # print(updater.get_update_command())
    
    # output, error = updater.execute_remote_script('192.168.2.101', 'nvidia', 'nvidia', updater.imu_update)
    # print('Output:', output.decode())
    # print('Error:', error.decode())

    print(get_firmware_version(updater.imu_version))

    # power_control('on', 'IT')
    
    # mcu_get_data(['version','swap-info'])

    # update_firmware_break(path, 99)

    # update_firmware(path)

    # time.sleep(3)
    # power_control('off', 'IT')
    # time.sleep(3)
    # power_control('on', 'IT')
    # time.sleep(5)

    # mcu_get_data(['version','swap-info'])


if __name__ == "__main__":
    main1()
    
   
import serial
import time
import subprocess
import pyvisa
import logging
import datetime
import os

class DeviceCommands:
    FIRMWARE_PATH = '/home/haobaisong/Documents/work/Kunlun/release/release_v2_clover-main-mcu_v0-3_RC1/b0200.00.a01/'

    def __init__(self):
        
        self.ethernet_upgrade = [
            'updater_package', 'update',
            '-d', 'clover_main_mcu',
            '-e',
            '-i', '1',
            '-p', self.FIRMWARE_PATH
        ]

        self.ethernet_query = [
            'updater_package', 'cmd',
            '-d', 'clover_main_mcu',
            '-e',
            '-i', '1',
            '-v'
        ]

        self.uart_update = [
            'updater_package', 'update',
            '-d', 'clover_main_mcu',
            '-e',
            '--target_ip', '192.168.2.64',
            '--target_port', '32888',
            '-i', '17',
            '-p', self.FIRMWARE_PATH
        ]

        self.pcan_upgrade = [
            'updater_package', 'update',
            '-d', 'clover_main_mcu',
            '-c',
            '-i', '2',
            '-p', self.FIRMWARE_PATH
        ]

        self.pcan_query = [
            'updater_package', 'cmd',
            '-d', 'clover_main_mcu',
            '-c',
            '-i', '2',
            '-v'
        ]
        
        self.imu_query = [
            '/home/haobaisong/Documents/work/Kunlun/tools/imu/pony_imu_cli_package', 
            '--ip', '192.168.1.64', 
            '--target_device_id', '1794', 
            '--local_device_id', '4', 
            '-u', '0:125:log'
        ]

# 设置日志的配置
def setup_logging():
    # 获取当前时间，用于文件名
    current_time = datetime.datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    log_filename = f"log_{current_time}.log"

    # 创建一个配置日志系统的基本配置
    logging.basicConfig(level=logging.DEBUG,
                        format='%(asctime)s %(levelname)s %(message)s',
                        filename=log_filename,
                        filemode='w')

    # 添加日志打印到控制台
    console = logging.StreamHandler()
    console.setLevel(logging.INFO)
    formatter = logging.Formatter('%(asctime)s %(levelname)s %(message)s')
    console.setFormatter(formatter)
    logging.getLogger('').addHandler(console)

def power_control(switch, power_supply_type):
    rm = None
    inst = None
    try:
        print("初始化VISA resource ...")
        rm = pyvisa.ResourceManager()
        print("打开: ASRL::/dev/ttyUSB0::INSTR")

        inst = rm.open_resource("ASRL/dev/ttyUSB0::INSTR")

        if power_supply_type == "IT":
            print("设置远程控制...")
            inst.write("SYSTem:REMote")
            command = f"OUTP {1 if switch == 'on' else 0}"
            print(f"发送命令: {command}")
            inst.write(command)

        elif power_supply_type == "NGI":
            command = f"id00:output {1 if switch == 'on' else 0}"
            print(f"发送命令 to NGI: {command}")
            inst.write(command)
        
        else:
            print("出错辣!!!")
    except Exception as e:
        print(f"An error occurred: {e}")
    finally:
        if inst:
            inst.close()
        if rm:
            rm.close()

def update_firmware():
    """
    使用 updater_package 工具更新固件。
    """

    commands = DeviceCommands()
    # 执行命令
    try:
        # 使用 subprocess.Popen 运行命令
        process = subprocess.Popen(commands.ethernet_upgrade, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

        # 实时打印标准输出和错误输出
        for line in process.stdout:
            print(line, end='')  # 打印每一行输出，不增加额外的换行符

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


def mcu_update_firmware_break(break_at_percent):
    """
    使用 updater_package 工具更新固件。
    """

    commands = DeviceCommands()

    # 执行命令
    try:
        # 使用 subprocess.Popen 运行命令
        process = subprocess.Popen(commands.ethernet_upgrade, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

        # 实时打印标准输出和错误输出
        for line in process.stdout:
            print(line, end='')  # 打印每一行输出，不增加额外的换行符
            
            if f'The upgrade has finished :{break_at_percent}%.' in line:
                power_control('off', 'IT')
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


def mcu_get_data(commands, port='/dev/ttyCH9344USB0', baudrate=115200, timeout=2):
    """
    从通过串口连接的MCU获取数据。

    参数:   
    - command: str, 要发送到MCU的命令。
    - port: str, 串口设备的路径。
    - baudrate: int, 串口通信的波特率。
    - timeout: int, 命令响应的等待时间（秒）。
        
    返回:
    - response: list of str, 从MCU接收到的响应行。
    """
    # 创建串口连接
    ser = serial.Serial(port, baudrate, timeout=1)
    response = []

    # 确保串口已打开
    if ser.isOpen():
        print(f"Connected to {port} at {baudrate} baud.")
        try:
            # 清空串口输入和输出缓冲区
            ser.flushInput()
            ser.flushOutput()

            for command in commands:
                # 发送命令，确保以正确的终止符结束
                ser.write(f'{command}\r'.encode('utf-8'))
                
                # 稍等一会儿，让设备有时间响应
                time.sleep(timeout)

                # 读取返回的数据
                command_responses = []
                while ser.inWaiting() > 0:
                    data = ser.readline().decode('utf-8').strip()
                    if data:  # 确保不记录空行
                        command_responses.append(data)
                        # print(f"Response to {command}: {data}")
                        print(f"{data}")

                # 将命令与其对应的响应保存到字典中s

        except Exception as e:
            print("通信问题...:", e)
        finally:
            # 关闭串口
            ser.close()
    else:
        print("F串口错误")




def main1():
    setup_logging()
    logging.info("程序开始执行")


    
    # power_control('on', 'IT')
    
    mcu_get_data(['version','swap-info'])

    # update_firmware_break(path, 99)

    # update_firmware(path)

    # time.sleep(3)
    # power_control('off', 'IT')
    # time.sleep(3)
    # power_control('on', 'IT')
    # time.sleep(5)

    # mcu_get_data(['version','swap-info'])

def imu_cal_frequent():
    """
    0:1715845024.4408717155,-0.973570, status:00000001, acc:   0.867260 |  -2.299440 |   9.452310, gyro:   0.200820 |  -0.095680 |  -0.131450, temp:  37.796875 |  37.796875
    0:1715845024.4488716125,-0.971092, status:00000001, acc:   0.866220 |  -2.307910 |   9.500600, gyro:  -0.019120 |   0.045230 |  -0.086420, temp:  37.796875 |  37.796875
    0:1715845024.4568717480,-0.978841, status:00000001, acc:   0.862060 |  -2.350540 |   9.447980, gyro:  -0.171660 |  -0.022980 |  -0.066530, temp:  37.796875 |  37.796875
    0:1715845024.4648716450,-0.976361, status:00000001, acc:   0.883040 |  -2.320140 |   9.438630, gyro:   0.177190 |  -0.205710 |  -0.082430, temp:  37.796875 |  37.796875
    0:1715845024.4728715420,-0.973877, status:00000001, acc:   0.856120 |  -2.313930 |   9.498920, gyro:   0.070440 |  -0.014990 |  -0.100530, temp:  37.796875 |  37.796875
    
    0:时间戳,
    -值, 表示从1970年1月1日00:00:00 UTC到当前时间的秒数。这里的时间戳精确到纳秒
    状态:状态码, 
    acc: 加速度 | 加速度 | 加速度, 
    gyro: 角速度 | 角速度 | 角速度, 
    temp: 温度 | 温度

    """
    
    command = DeviceCommands()
    process = subprocess.Popen(command.imu_query, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    # 读取一定数量的输出行来进行频率计算
    data = []
    start_time = time.time()
    while True:
        line = process.stdout.readline()
        if line:
            data.append(line.strip())
            if len(data) >= 1000:  
                break
        if time.time() - start_time > 10: 
            break

    # 提取时间戳
    timestamps = [float(line.split(',')[0].split(':')[1]) for line in data]
    # 计算连续测量之间的时间差
    time_differences = [timestamps[i+1] - timestamps[i] for i in range(len(timestamps) - 1)]
    # 计算平均采样周期
    average_sampling_period = sum(time_differences) / len(time_differences)
    # 计算采样频率
    sampling_frequency = 1 / average_sampling_period
    # 输出采样频率
    print(f"IMU采集频率: {sampling_frequency:.3f} Hz")

def imu_main():
    command = DeviceCommands()
    process = subprocess.Popen(command.imu_query, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    current_dir = os.getcwd()
    log_file_path = os.path.join(current_dir, "imu_data_log.txt")
    # 打开一个文件以写入日志
    with open(log_file_path, "w") as logfile:
        start_time = time.time()
        while time.time() - start_time < 20 * 60:  # 持续20分钟
            line = process.stdout.readline()
            if line:
                logfile.write(line)
                logfile.flush()  # 确保每行数据都直接写入文件，而不是留在缓冲区中

    process.terminate()  # 在20分钟后终止进程

if __name__ == "__main__":
 
    imu_cal_frequent()
    
   
'''

clover_main_mcu
------------------------------
FW Is: app
FW Version: release_v2_clover-main-mcu_v0-2_RC1.B
Buildstamp: 2024-04-19/13:47:05
Githash: 999603486b369cb4f8db8416991da97c84ba6171
------------------------------


clover_main_mcu
------------------------------
FW Is: app
FW Version: release_v2_clover-main-mcu_v0-2_RC1.A
Buildstamp: 2024-04-19/13:47:05
Githash: 999603486b369cb4f8db8416991da97c84ba6171
------------------------------
'''
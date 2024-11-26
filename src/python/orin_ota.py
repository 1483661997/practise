import subprocess
import sys

class DeviceCommands:

    UPDATER_PACKAGE = '/home/haobaisong/Documents/work/Kunlun/release/update/updater_package'

    MCT_MA1X_MAIN = '/home/nvidia/orin_ota/mct_ma1x_main_package'

    IMU_FIRMWARE_PATH = '/home/nvidia/orin_ota/IPS_MA1_V1.3.4.0315.bin' # IPS_MA1_V1.3.4.0315.bin
    

    def __init__(self):
        
        self.imu_query = [
            '/home/haobaisong/Documents/work/Kunlun/tools/imu/pony_imu_cli_package', 
            '--ip', '192.168.1.64', 
            '--target_device_id', '1794', 
            '--local_device_id', '4', 
            '-u', '0:125:log'
        ]

        self.imu_update = [
            self.MCT_MA1X_MAIN,
            '--updater_file', '/home/nvidia/orin_ota/updater.config',
            '--tool_file', '/home/nvidia/orin_ota/libUpgrade-aarch64.so',
            '-logtostderr', '--firmware_file', self.IMU_FIRMWARE_PATH

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

def update_firmware(command):
    """
    使用 updater_package 工具更新固件。
    """

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

def main1():


    updater = DeviceCommands()
    update_firmware(updater.imu_update)
    # print(updater.get_update_command())

    # updater.update_firmware_path('0316')
    # print(updater.get_update_command())
    # updater.update_firmware_path('0315')
    # print(updater.get_update_command())



if __name__ == "__main__":
    main1()
    
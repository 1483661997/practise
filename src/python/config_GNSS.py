import serial
import time
gnss_config = [
    "set-gnss-config ppscontrol_enable_positive_1.0_10000",
    "set-gnss-config com_com1_921600",
    "set-gnss-config com_com2_115200",
    "set-gnss-config interfacemode_com1_auto_auto_on",
    "set-gnss-config unlogall",
    "set-gnss-config ecutoff_10.0",
    "set-gnss-config log_com2_sysrts_ontime_1",
    "set-gnss-config log_com2_gpgga_ontime_1",
    "set-gnss-config log_com2_gprmc_ontime_1",
    "set-gnss-config log_com1_bd2ephemb_ontime_20",
    "set-gnss-config log_com1_bd3ephemb_ontime_20",
    "set-gnss-config log_com1_gpsephemb_ontime_20",
    "set-gnss-config log_com1_galephemerisb_ontime_20",
    "set-gnss-config log_com1_glorawephemb_ontime_20",
    "set-gnss-config log_com1_rangecmpb_ontime_0.1",
    "set-gnss-config log_com1_psrposb_ontime_0.1",
    "set-gnss-config log_com1_psrdopb_ontime_0.1",
    "set-gnss-config log_com1_psrvelb_ontime_0.1",
    "set-gnss-config log_com1_bestposb_ontime_0.1",
    "set-gnss-config log_com1_bestvelb_ontime_0.1",
    "set-gnss-config saveconfig"
]


def mcu_set_data(commands, port='/dev/ttyCH9344USB0', baudrate=115200, timeout=2):
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

if __name__ == "__main__":
    mcu_set_data(gnss_config)
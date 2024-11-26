import serial
import time
import subprocess
import pyvisa
import logging

logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

def power_control(switch):
    rm = None
    inst = None
    try:
        # print("初始化VISA 资源...")
        logging.info("初始化VISA 资源...")
        rm = pyvisa.ResourceManager()

        print("打开: ASRL::/dev/ttyUSB0::INSTR")
        inst = rm.open_resource("ASRL/dev/ttyUSB0::INSTR")

        print("设置远程控制...")
        inst.write("SYSTem:REMote")

        command = f"OUTP {1 if switch == 'on' else 0}"
        
        print(f"发送命令: {command}")
        inst.write(command)
       
    except Exception as e:
        print(f"发生错误: {e}")
    finally:
        if inst:
            inst.close()
        if rm:
            rm.close()

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
                        print(f"{data}")

        except Exception as e:
            print("错误:", e)
        finally:
            # 关闭串口
            ser.close()
    else:
        print("串口错误")

def ping_ips():
    ip_list = ['192.168.1.100', '192.168.1.101', '192.168.1.102', '192.168.1.103']

    for ip in ip_list:
        try:
            response = subprocess.run(['ping', '-c', '1', ip], stdout=subprocess.PIPE, text=True)
            if response.returncode == 0:
                print(f"成功ping： {ip}")
            else:
                print(f"失败ping: {ip}")
        except Exception as e:
            print(f"出错： {ip}: {e}")


def main():
  

    # mcu_get_data(['version','swap-info'])
    # ping_ips()

    power_control('off')
    # time.sleep(3)
    # power_control('on')
    
    time.sleep(3)
    # power_control('off')
    
    # time.sleep(3)

    # mcu_get_data(['version','swap-info'])
    # ping_ips()

if __name__ == "__main__":
    main()
    
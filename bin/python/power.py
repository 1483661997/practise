import pyvisa

def initialize_power_supply(resource_name):
    """
    初始化电源，并进行简单的通信测试
    :param resource_name: VISA资源名称，例如 'USB0::0x0957::0x8C18::MY52603405::INSTR'
    :return: 电源对象
    """
    rm = pyvisa.ResourceManager()
    power_supply = rm.open_resource(resource_name)
    
    # 进行简单的通信测试
    idn = power_supply.query('*IDN?')
    print(f"Connected to: {idn}")
    
    return power_supply

def set_voltage_current(power_supply, channel, voltage, current):
    """
    设置电源的电压和电流
    :param power_supply: 电源对象
    :param channel: 频道号（1, 2 或 3）
    :param voltage: 设置的电压值
    :param current: 设置的电流值
    """
    power_supply.write(f'INSTrument:NSELect {channel}')
    power_supply.write(f'VOLTage {voltage}')
    power_supply.write(f'CURRent {current}')
    print(f"Channel {channel}: Voltage set to {voltage}V, Current set to {current}A")

def turn_output_on_off(power_supply, channel, state):
    """
    打开或关闭电源输出
    :param power_supply: 电源对象
    :param channel: 频道号（1, 2 或 3）
    :param state: 打开（True）或关闭（False）输出
    """
    power_supply.write(f'INSTrument:NSELect {channel}')
    power_supply.write(f'OUTPut {"ON" if state else "OFF"}')
    print(f"Channel {channel}: Output {'ON' if state else 'OFF'}")

def list_visa_resources():
    """
    列出所有已连接的VISA设备的资源名称
    """
    rm = pyvisa.ResourceManager()
    resources = rm.list_resources()

    for resource in resources:
        print(resource)

def main():
    # VISA资源名称，请根据实际情况进行更改
    resource_name = 'USB0::0x2A8D::0x5902::MYXXXXXXXX::INSTR'
    
    # 初始化电源
    power_supply = initialize_power_supply(resource_name)
    
    # 设置电压和电流
    set_voltage_current(power_supply, channel=1, voltage=5.0, current=1.0)
    
    # 打开输出
    turn_output_on_off(power_supply, channel=1, state=True)
    
    # 等待一段时间，模拟一些工作
    import time
    time.sleep(10)
    
    # 关闭输出
    turn_output_on_off(power_supply, channel=1, state=False)
    
    # 关闭电源连接
    power_supply.close()
    print("Power supply connection closed.")

if __name__ == '__main__':
    # main()
    list_visa_resources()

import os
import time
import subprocess


command1 = ['/home/haobaisong/Documents/work/Kunlun/tools/imu/pony_imu_cli_package', 
            '--ip', '192.168.1.64', 
            '--target_device_id', '1794', 
            '--local_device_id', '4', 
            '-u', '0:125:log']  
command2 = ['/home/haobaisong/Documents/work/Kunlun/tools/imu/pony_imu_cli_package', 
            '--ip', '192.168.2.64', 
            '--target_device_id', '1795', 
            '--local_device_id', '4', 
            '-u', '0:125:log']  

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
    
   
    process = subprocess.Popen(command1, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

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

def imu_record_data():
    process = subprocess.Popen(command1, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
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
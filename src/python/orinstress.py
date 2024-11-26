import paramiko
import threading
# from common.experimental.clover_esw_test_code.scripts import Logger # type: ignore #

username = 'ponyai'
# Orin设备的IP地址和SSH登录信息
orin_devices = [
    {"host": "192.168.1.100", "username": username, "password": "pony_bjkunluncart2"},
    {"host": "192.168.1.101", "username": username, "password": "pony_bjkunluncart2r"},
    {"host": "192.168.1.102", "username": username, "password": "pony_bjkunluncart2a"},
    {"host": "192.168.1.103", "username": username, "password": "pony_bjkunluncart2b"}
]

# 要在每个设备上运行的stress-ng命令
stress_ng_command = "stress-ng --cpu 2 --cpu-load 5 --vm 2 --vm-bytes 5G --udp 3 --timeout 43200s"
stress_ng_commandr = "stress-ng --cpu 2 --cpu-load 15 --vm 2 --vm-bytes 10G --udp 4 --timeout 43200s"
stress_ng_commanda = "stress-ng --cpu 4 --cpu-load 5 --vm 2 --vm-bytes 15G --udp 4 --timeout 43200s"
stress_ng_commandb = "stress-ng --cpu 4 --cpu-load 5 --vm 2 --vm-bytes 15G --udp 4 --timeout 43200s"

pid_dict = {}

def run_stress_test(device):
    host = device["host"]
    username = device["username"]
    password = device["password"]
    
    try:
        # 创建SSH客户端
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(host, username=username, password=password)
        
        print(f"Connected to {host}")
        
        # 运行stress-ng命令
        if host == "192.168.1.100":
            stdin, stdout, stderr = ssh.exec_command(stress_ng_command)
        elif host == "192.168.1.101":
            stdin, stdout, stderr = ssh.exec_command(stress_ng_commandr)
        elif host == "192.168.1.102":
            stdin, stdout, stderr = ssh.exec_command(stress_ng_commanda)
        else:
            stdin, stdout, stderr = ssh.exec_command(stress_ng_commandb)
            
        # 打印命令输出
        for line in stdout:
            print(f"{host}: {line.strip()}")
        
        # 打印错误输出
        for line in stderr:
            print(f"{host} Error: {line.strip()}")
        
        ssh.close()
    except Exception as e:
        print(f"Failed to run stress-ng on {host}: {str(e)}")

def stop_stress_test(device):
    host = device["host"]
    username = device["username"]
    password = device["password"]
    
    try:
        # 创建SSH客户端
        ssh = paramiko.SSHClient()
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
        ssh.connect(host, username=username, password=password)
        
        print(f"Connected to {host} for stopping stress-ng")
        
       
        # 停止stress-ng进程
        ssh.exec_command(f"killall stress-ng")
        print(f"Stress-ng stopped on {host}")
        
        ssh.close()
    except Exception as e:
        print(f"Failed to stop stress-ng on {host}: {str(e)}")

# 使用线程并行运行压力测试
threads = []
for device in orin_devices:
    t = threading.Thread(target=run_stress_test, args=(device,))
    t.start()
    threads.append(t)

print("Stress-ng has been started on all devices.")
print("Press Enter to stop stress-ng on all devices...")

# 等待用户输入来停止stress-ng
input()

# 使用线程并行停止压力测试
stop_threads = []
for device in orin_devices:
    t = threading.Thread(target=stop_stress_test, args=(device))
    t.start()
    stop_threads.append(t)

# 等待所有停止线程完成
for t in stop_threads:
    t.join()
# 等待所有线程完成
for t in threads:
    t.join()



print("Stress-ng stopped on all devices.")
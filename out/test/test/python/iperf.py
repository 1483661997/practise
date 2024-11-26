

import paramiko
import time

def ssh_login(hostname, username, password):
    client = paramiko.SSHClient()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    client.connect(hostname, username=username, password=password)
    return client

def run_command(client, command):
    stdin, stdout, stderr = client.exec_command(command)
    return stdout, stderr

def kill_iperf(client):
    """向远程主机发送信号杀死iperf3进程"""
    run_command(client, "pkill -f 'iperf3 -s'")

def main():
    orinA_client = ssh_login("192.168.1.102", "nvidia", "nvidia")
    orinC_client = ssh_login("192.168.1.100", "nvidia", "nvidia")

    try:
        # 在 orinC 上设置为服务器模式，并在后台运行
        run_command(orinC_client, "iperf3 -s &")  # 确保在后台运行

        # 在 orinA 上启动iperf客户端，连接到 orinC
        stdout, _ = run_command(orinA_client, "iperf3 -c 192.168.1.100 -b 20000M -t 300")
        # stdout, _ = run_command(orinC_client, "iperf3 -c 192.168.1.102 -b 20000M -t 300")

        run_command(orinA_client, "iperf3 -s &")  # 确保在后台运行

        # 在 orinA 上启动iperf客户端，连接到 orinC
        stdout, _ = run_command(orinC_client, "iperf3 -c 192.168.1.102 -b 20000M -t 300")


        for line in stdout:
            print(line.strip())

        # time.sleep(10)  # 等待测试完成

    except Exception as e:
        print(f"出错辣: {e}")
    finally:
        # 清理服务器上的iperf3进程
        kill_iperf(orinC_client)

        # 关闭SSH连接
        orinA_client.close()
        orinC_client.close()

if __name__ == "__main__":
    # for i in range(2):
        main()

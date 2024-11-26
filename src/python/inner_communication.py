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

def perform_test(server_ip, client_ip, server_client, client_client):
    try:
        # 在服务器上设置为iperf3服务器模式，并在后台运行
        run_command(server_client, "iperf3 -s  -i 1 -p 5201 &")  # 确保在后台运行
        time.sleep(2)  # 等待服务器启动

        # 在客户端启动iperf3客户端，连接到服务器
        stdout, _ = run_command(client_client, f"iperf3 -c {server_ip} -u -b 10G -t 60 -p 5201")

        # 读取并打印输出
        print(f"Results from {client_ip} to {server_ip}:")
        for line in stdout:
            print(line.strip())

    except Exception as e:
        print(f"Error: {e}")

    finally:
        # 清理服务器上的iperf3进程
        kill_iperf(server_client)

def kill_all_iperf(clients):
    """在所有客户端上杀死所有iperf3进程"""
    for client in clients.values():
        kill_iperf(client)

def main():
    ips = {
        "OrinA": "192.168.1.102",
        "OrinB": "192.168.1.103",
        "OrinC": "192.168.1.100",
        "OrinF": "192.168.1.101",

    }
    
    credentials = ("nvidia", "nvidia")
    clients = {}

    # 登录所有Orin设备
    try:
        for name, ip in ips.items():
            clients[name] = ssh_login(ip, *credentials)

        # 在开始测试之前清理所有可能残留的iperf进程
        kill_all_iperf(clients)

        # 定义要测试的设备对
        pairs = [
            ("OrinA", "OrinC", "192.168.3.102", "192.168.3.100"),
            ("OrinC", "OrinA", "192.168.3.100", "192.168.3.102"),
            
            ("OrinA", "OrinB", "192.168.4.102", "192.168.4.103"),
            ("OrinB", "OrinA", "192.168.4.103", "192.168.4.102"),
            
            ("OrinA", "OrinF", "191.168.1.102", "192.168.1.101"),
            ("OrinF", "OrinA", "192.168.1.101", "192.168.1.102"),

            ("OrinB", "OrinC", "192.168.5.103", "192.168.5.100"),
            ("OrinC", "OrinB", "192.168.5.100", "192.168.5.103"),
            
            ("OrinB", "OrinC", "192.168.1.103", "192.168.1.100"),
            ("OrinC", "OrinB", "192.168.1.100", "192.168.1.103"),

            ("OrinB", "OrinF", "192.168.1.103", "192.168.1.101"),
            ("OrinF", "OrinB", "192.168.1.101", "192.168.1.103"),

            ("OrinC", "OrinF", "192.168.1.100", "192.168.1.101"),
            ("OrinF", "OrinC", "192.168.1.101", "192.168.1.100")


        ]

        # 执行iperf测试
        for server, client, server_ips, client_ips in pairs:
            # server_ip = ips[server]
            # client_ip = ips[client]
            # send_ip = ips[client_ips]
            # reveive_ip = ips[server_ips]
            perform_test(server_ips, client_ips, clients[server], clients[client])
            # perform_test(server_ip, client_ip, clients[server], clients[client])



    except Exception as e:
        print(f"Error occurred: {e}")

    finally:
        # 确保清理所有iperf进程
        kill_all_iperf(clients)

        # 关闭所有SSH连接
        for client in clients.values():
            client.close()

if __name__ == "__main__":
    main()

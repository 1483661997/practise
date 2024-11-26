import paramiko
import time

def ssh_login(hostname, username, password):
    """使用用户名和密码登录远程主机。"""
    client = paramiko.SSHClient()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    try:
        client.connect(hostname, username=username, password=password)
        print(f"Connected to {hostname} successfully.")
        return client
    except paramiko.ssh_exception.NoValidConnectionsError:
        print(f"Failed to connect to {hostname}.")
        return None

def run_command(client, command, background=False):
    """执行命令，如果是后台运行，不等待输出。"""
    stdin, stdout, stderr = client.exec_command(command)
    if background:
        return None  # 如果是后台运行，立即返回
    return stdout.read().decode(), stderr.read().decode()


def kill_iperf(client):
    """结束远程主机上的所有 iperf3 进程。"""
    print("Killing iperf on remote host...")
    run_command(client, "pkill -f 'iperf3 -s'")

def perform_test(server_ip, client_ip, server_client, client_client):
    """在远程主机上设置iperf服务器并从客户端进行测试。"""
    print(f"Setting up iperf3 server on {server_ip}...")
    run_command(server_client, "iperf3 -s &", background=True)  # 启动服务器
    time.sleep(2)  # 等待服务器准备好
        
    print(f"Running iperf3 client on {client_ip} connecting to {server_ip}...")
    stdout, stderr = run_command(client_client, f"iperf3 -c {server_ip} -t 10")
    print(stdout)

    kill_iperf(server_client)

def perform_test1(server_ip, client_ip, server_client, client_client):
    try:
        # 在服务器上设置为iperf3服务器模式，并在后台运行
        run_command(server_client, "iperf3 -s &", background=True)
        time.sleep(2)  # 等待服务器启动

        # UDP 测试示例，10Gbps带宽，10秒钟
        command = f"iperf3 -c {server_ip} -u -b 10G -t 10 --get-server-output"
        stdout, stderr = run_command(client_client, command)

        # 读取并打印输出结果
        print(f"Results from {client_ip} to {server_ip}:")
        print(stdout)

    except Exception as e:
        print(f"Error: {e}")

    finally:
        # 清理服务器上的iperf3进程
        kill_iperf(server_client)

def main():
    ips = {
        "OrinA": "192.168.1.100",
        "OrinC": "192.168.1.102",
    }
    
    credentials = ("nvidia", "nvidia")
    clients = {}

    # 登录到 OrinA 和 OrinC
    for name, ip in ips.items():
        clients[name] = ssh_login(ip, *credentials)
        if clients[name] is None:
            return  # 如果连接失败，终止程序

    try:
        # 清理残留的 iperf3 进程
        for client in clients.values():
            kill_iperf(client)

        # 测试 OrinA 和 OrinC 之间的网络性能
        perform_test(ips["OrinC"], ips["OrinA"], clients["OrinC"], clients["OrinA"])
    finally:
        # 清理并关闭连接
        for client in clients.values():
            if client:
                client.close()

if __name__ == "__main__":
    main()

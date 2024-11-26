import paramiko
import time
import threading
import os
import datetime

base_log_dir = "/home/haobaisong/Documents/work/Kunlun/eth/record"
if not os.path.exists(base_log_dir):
    os.makedirs(base_log_dir)

def ssh_login(hostname, username, password):
    client = paramiko.SSHClient()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    try:
        client.connect(hostname, username=username, password=password)
    except Exception as e:
        log_message = f"Failed to connect to {hostname}: {str(e)}"
        print(log_message)
        save_log(hostname, log_message)
        return None
    return client

def run_command(client, command):
    if client is None:
        return "SSH Client is not connected."
    stdin, stdout, stderr = client.exec_command(command)
    stdout.channel.set_combine_stderr(True)
    output = stdout.read().decode()
    return output

def kill_iperf(client):
    if client is None:
        return "SSH Client is not connected."
    output = run_command(client, "pkill -f 'iperf3 -s'")
    return output

def save_log(filename, data):
    with open(filename, "a") as file:
        file.write(data + "\n")

def perform_test(server_ip, client_ip, server_client, client_client, port):
    # 获取当前时间，并格式化为字符串
    current_time = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
    # 使用当前时间字符串构建文件名
    filename = os.path.join(base_log_dir, f"iperf_{server_ip}_to_{client_ip}_{port}_{current_time}.txt")
    try:
        server_cmd = f"iperf3 -s -i 1 -p {port} &"
        run_command(server_client, server_cmd)
        log_message = f"Starting iperf server on {server_ip} at port {port}"
        print(log_message)
        save_log(filename, log_message)

        time.sleep(2)

        client_cmd = f"iperf3 -c {server_ip} -u -b 10G -t 10 -p {port}"
        output = run_command(client_client, client_cmd)
        log_message = f"Results from {client_ip} to {server_ip}:{port}\n{output}"
        print(log_message)
        save_log(filename, log_message)

    finally:
        output = kill_iperf(server_client)
        save_log(filename, f"Killed iperf server on {server_ip}: {output}")

        
def create_ssh_client(ip, username, password):
    client = paramiko.SSHClient()
    client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    try:
        client.connect(ip, username=username, password=password)
    except Exception as e:
        log_message = f"Failed to connect to {ip}: {str(e)}"
        print(log_message)
        save_log(ip, log_message)
        return None
    return client


def main():
    ips = {
        "OrinA": "192.168.1.102",
        "OrinB": "192.168.1.103",
        "OrinC": "192.168.1.100",
        "OrinF": "192.168.1.101",
    }
    credentials = ("nvidia", "nvidia")
    clients = {}
    starting_port = 5201

    try:
        for name, ip in ips.items():
            # Create a new SSH client for server and client roles separately
            server_client = create_ssh_client(ip, *credentials)
            if server_client:
                clients[name + "_server"] = server_client
            else:
                print(f"Connection failed for server at {ip}")

            client_client = create_ssh_client(ip, *credentials)
            if client_client:
                clients[name + "_client"] = client_client
            else:
                print(f"Connection failed for client at {ip}")

       
        pairs = [
            ("OrinA_server", "OrinC_client", "192.168.3.102", "192.168.3.100", starting_port),
            ("OrinA_server", "OrinB_client", "192.168.4.102", "192.168.4.103", starting_port + 1),
            ("OrinA_server", "OrinF_client", "192.168.1.102", "192.168.1.101", starting_port + 2),
            ("OrinB_server", "OrinC_client", "192.168.5.103", "192.168.5.100", starting_port + 3),
            ("OrinB_server", "OrinC_client", "192.168.1.103", "192.168.1.100", starting_port + 4),
            ("OrinB_server", "OrinF_client", "192.168.1.103", "192.168.1.101", starting_port + 5),
            ("OrinC_server", "OrinF_client", "192.168.1.100", "192.168.1.101", starting_port + 6),
        ]

        threads = []
        for server, client, server_ip, client_ip, port in pairs:
            thread = threading.Thread(target=perform_test, args=(server_ip, client_ip, clients.get(server), clients.get(client), port))
            threads.append(thread)
            thread.start()

        for thread in threads:
            thread.join()

    finally:
        for client in clients.values():
            if client:
                client.close()

if __name__ == "__main__":
    main()


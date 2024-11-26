import subprocess

def set_ip_and_route( gateway):
    # 网关
   subprocess.run(["sudo", "route", "add", "default", "gw", gateway], check=True)

def setup_dns(dns_server):
    # 修改/etc/resolv.conf文件
    cmd = f'echo "nameserver {dns_server}" | sudo tee /etc/resolv.conf > /dev/null'
    subprocess.run(cmd, shell=True, check=True)

def test_connection():
    # 测试网络连接
    try:
        # 测试能否ping通Google的DNS
        subprocess.run(["ping", "-c", "4", "8.8.8.8"], check=True)
        print("Ping 8.8.8.8 success!")
    except subprocess.CalledProcessError:
        print("Failed to ping 8.8.8.8")

    try:
        # 测试能否ping通百度
        subprocess.run(["ping", "-c", "4", "baidu.com"], check=True)
        print("Ping baidu.com success!")
    except subprocess.CalledProcessError:
        print("Failed to ping baidu.com")

# 设置IP和网关
set_ip_and_route( "192.168.1.99")

# 配置DNS服务器
setup_dns("8.8.8.8")

# 测试网络连接
test_connection()

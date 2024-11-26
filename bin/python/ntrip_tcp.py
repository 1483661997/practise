import socket
import struct
import logging
from zlib import crc32  
# 配置变量
_SYNC1 = 170 # 同步字节1
_SYNC2 = 68  # 同步字节2
_SYNC3 = 18  # 同步字节3
_SOCKET_TIMEOUT_SECONDS = 2  # 套接字超时时间
_SYNC_BYTES = bytes([_SYNC1, _SYNC2, _SYNC3])  # 同步字节序列
_CRC_LEN = 4  # CRC校验长度
_PACKET_SIZE = 40960  # 包大小
_TEST_TIMEOUT = 30  # 测试超时时间
_TOTAL_PACKET_NUM = 1000
_GPS_QUAL_NUM = 6
_RTK_FIXED_SOLUTION = '4'  # RTK fixed solution
_GPGGA_START = bytes([0x24, 0x47, 0x50, 0x47, 0x47, 0x41])  # $GPGGA
_ASCII_MSG_START = bytes([0x23, 0x24])  # '#' or '$'
_ASCII_MSG_END = bytes([0x0D, 0x0A])  # CR LF
_FORWARD_GPGGA_CONFIG = 'log com1 gpgga ontime 1'


class K802MsgHeader:
    def __init__(
        self,
        sync1,
        sync2,
        sync3,
        header_len,
        msg_id,
        msg_type,
        port_addr,
        msg_len,
        sequence,
        idle_time,
        time_status,
        week,
        milli_seconds,
        receiver_status,
        reserved,
        sw_version,
    ):
        self.sync1 = sync1
        self.sync2 = sync2
        self.sync3 = sync3
        self.header_len = header_len
        self.msg_id = msg_id
        self.msg_type = msg_type
        self.port_addr = port_addr
        self.msg_len = msg_len
        self.sequence = sequence
        self.idle_time = idle_time
        self.time_status = time_status
        self.week = week
        self.milli_seconds = milli_seconds
        self.receiver_status = receiver_status
        self.reserved = reserved
        self.sw_version = sw_version

    def __str__(self):
        return (
            f'sync1: {self.sync1}, sync2: {self.sync2}, sync3: {self.sync3}, '
            f'header_len: {self.header_len}, msg_id: {self.msg_id}, msg_type: {self.msg_type}, '
            f'port_addr: {self.port_addr}, msg_len: {self.msg_len}, sequence: {self.sequence}, '
            f'idle_time: {self.idle_time}, time_status: {self.time_status}, week: {self.week}, '
            f'milli_seconds: {self.milli_seconds}, receiver_status: {self.receiver_status}, sw_version: {self.sw_version}'
        )


def _novatel_crc32(u8_data):
    _CRC32_POLYNOMIAL = 0xEDB88320
    crc_value = u8_data
    for _ in range(8):
        if crc_value & 1:
            crc_value = (crc_value >> 1) ^ _CRC32_POLYNOMIAL
        else:
            crc_value = crc_value >> 1
    return crc_value


def _novatel_binary_message_crc32(binary_data):
    crc_value = 0
    for x in binary_data:
        temp1 = (crc_value >> 8) & 0x00FFFFFF
        temp2 = _novatel_crc32((crc_value ^ x) & 0xFF)
        crc_value = temp1 ^ temp2
    return crc_value



# def unpack_packet(data, logger, sync1, sync2, sync3, crc_function, ascii_msg_start, ascii_msg_end, crc_len):
#     print(123)
#     format_str = '<4B1H1c1B2H2B1H2I2H'
#     header_size = struct.calcsize(format_str)
#     processed_packets = 0
#     crc_fail_packets = 0
#     dropped_bytes = 0

#     logger.info(f'header_size: {header_size}')

#     while len(data) >= header_size:
#         header_data = data[:header_size]
#         unpacked_data = struct.unpack(format_str, header_data)
#         k802_header = K802MsgHeader(*unpacked_data)  # This needs definition outside the function or passed as a dependency

#         if (k802_header.sync1 != sync1 or k802_header.sync2 != sync2 or k802_header.sync3 != sync3):
#             end_index = data.find(ascii_msg_end)
#             if end_index != -1 and data[0] in ascii_msg_start:
#                 logger.info(f'Skipped an ASCII message, length: {end_index} bytes')
#                 data = data[end_index + len(ascii_msg_end):]
#                 continue
#             data = data[1:]
#             dropped_bytes += 1
#             continue
        
#         packet_size = k802_header.header_len + k802_header.msg_len + crc_len
#         if len(data) < packet_size:
#             break

#         compute_crc = _novatel_binary_message_crc32(data[0:packet_size - crc_len])
#         packet_chksum = struct.unpack('<I', data[packet_size - crc_len:packet_size])[0]
        
#         if compute_crc != packet_chksum:
#             crc_fail_packets += 1
#             data = data[1:]
#             logger.error(f'CRC mismatch! Computed CRC: {compute_crc}, Packet CRC: {packet_chksum} \n {k802_header}')
#         else:
#             data = data[packet_size:]
#             processed_packets += 1

#     return {
#         'processed_packets': processed_packets,
#         'crc_fail_packets': crc_fail_packets,
#         'dropped_bytes': dropped_bytes
#     }

def unpack_packet(data, logger, sync1, sync2, sync3, crc_function, ascii_msg_start, ascii_msg_end, crc_len):
    format_str = '<4B1H1c1B2H2B1H2I2H'
    header_size = struct.calcsize(format_str)
    processed_packets = 0
    crc_fail_packets = 0
    dropped_bytes = 0
    received_data = []  # 用于存储接收到的数据

    logger.info(f'header_size: {header_size}')

    while len(data) >= header_size:
        # if not (data[0] == sync1 and data[1] == sync2 and data[2] == sync3):
        #     logger.error(f"Sync failed, data: {data[:10].hex()}")  # 只显示前10个字节
        #     data = data[1:]  # 尝试下一个字节进行同步
        #     dropped_bytes += 1
        #     continue
        header_data = data[:header_size]
        unpacked_data = struct.unpack(format_str, header_data)
        k802_header = K802MsgHeader(*unpacked_data)  # This needs definition outside the function or passed as a dependency

        if (k802_header.sync1 != sync1 or k802_header.sync2 != sync2 or k802_header.sync3 != sync3):
            end_index = data.find(ascii_msg_end)
            if end_index != -1 and data[0] in ascii_msg_start:
                logger.info(f'Skipped an ASCII message, length: {end_index} bytes')
                data = data[end_index + len(ascii_msg_end):]
                continue
            data = data[1:]
            dropped_bytes += 1
            continue

        packet_size = k802_header.header_len + k802_header.msg_len + crc_len
        if len(data) < packet_size:
            break

        compute_crc = crc_function(data[0:packet_size - crc_len])
        packet_chksum = struct.unpack('<I', data[packet_size - crc_len:packet_size])[0]

        if compute_crc != packet_chksum:
            crc_fail_packets += 1
            data = data[1:]
            logger.error(f'CRC mismatch! Computed CRC: {compute_crc}, Packet CRC: {packet_chksum} \n {k802_header}')
        else:
            received_packet = data[:packet_size]  # Store the entire packet before slicing it off
            received_data.append(received_packet)
            data = data[packet_size:]
            processed_packets += 1


    return {
        'processed_packets': processed_packets,
        'crc_fail_packets': crc_fail_packets,
        'dropped_bytes': dropped_bytes,
        'received_data': received_data  # 返回接收到的数据
    }


def binary_to_ascii(data):
    try:
        return data.decode('ascii')  # 或者 'utf-8'，取决于数据的编码
    except UnicodeDecodeError:
        return "Data cannot be decoded to ASCII."

def read_data_from_port(ip, port, handle_packet_func, logger, sync1, sync2, sync3, crc_function, ascii_msg_start, ascii_msg_end, crc_len):
    # 创建 socket 对象
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 连接到服务器
    client_socket.connect((ip, port))
    logger.info(f'Connected to {ip}:{port}')

    try:
        while True:
            # 接收数据
            data = client_socket.recv(1024)  # 可以根据需要调整缓冲大小
            if not data:
                logger.info('No more data received.')
                break  # 没有更多的数据，断开连接
            # 调用处理函数
            results = handle_packet_func(data, logger, sync1, sync2, sync3, crc_function, ascii_msg_start, ascii_msg_end, crc_len)
            logger.info(f'Packet processed: {results}')
    finally:
        client_socket.close()
        logger.info('Connection closed')
        
# 设置日志级别
# logging.basicConfig(level=logging.INFO)
logger = logging.getLogger('GNSSDataReceiver')
logging.basicConfig(level=logging.INFO)

# 参数定义（必须提前定义或导入这些函数和变量）
sync1, sync2, sync3 = 170, 68, 18
crc_function = _novatel_binary_message_crc32  # 这需要是一个合适的CRC函数
ascii_msg_start = bytes([0x23, 0x24])  # '#' or '$'
ascii_msg_end = bytes([0x0D, 0x0A])  # CR LF
crc_len = 4

if __name__ == "__main__":
    gnss_ip = '192.168.1.64'  # GNSS设备的IP地址
    gnss_port = 8001        # GNSS设备的端口号
    # receive_and_unpack_gnss_data(gnss_ip, gnss_port)
    read_data_from_port(gnss_ip, gnss_port, unpack_packet, logger, sync1, sync2, sync3, crc_function, ascii_msg_start, ascii_msg_end, crc_len)
    # parse()
    # connect()
'''
`\x17\xdaW\x0f]@\x00\x00\x92\xd9O\xe7a@\xe5x\x18\xc1=\x00\x00\x009\x82\xa8>\x98\x95\t>\xa9#7?\x00\x00\x00\x00\x00\x00\x00\x00\x00\x00bE\x14\x14\x14\x14o\x00\x00\ta\x026\xc2\xaaD\x12\x1cc\x00\x02 
'''
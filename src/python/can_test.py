import can
import time
from collections import defaultdict
from collections import deque
import threading
import statistics

class CANSender:
    def __init__(self, channel_send='PCAN_USBBUS1', baudrate=500000, bustype='pcan'):
        # 初始化CAN发送通道
        self.bus_send = can.interface.Bus(channel=channel_send, bustype=bustype, baudrate=baudrate)
        self.send_msg_count = defaultdict(int)
        self.send_msg_timestamps = defaultdict(list)

    def send_data(self, can_id, message, duration, fps):
        message.extend([0x00] * (8 - len(message)))  # 确保消息长度为8字节
        interval = 1 / fps
        end_time = time.time() + duration
        while time.time() < end_time:
            msg = can.Message(arbitration_id=can_id, data=message, is_extended_id=False)
            try:
                self.bus_send.send(msg)
                time.sleep(interval)  # 控制发送频率，可能需要调整这个时间来防止缓冲区溢出
            except can.CanError as e:
                print(f"发送数据失败，CAN ID {can_id}，错误：{e}")
                time.sleep(interval)  # 增加失败后的等待时间，给硬件更多恢复的时间
                continue

    def shutdown(self):
        # 关闭CAN通道
        self.bus_send.shutdown()


class CANReceiver:
    def __init__(self, channel_recv='PCAN_USBBUS2', baudrate=500000, bustype='pcan'):
        # 初始化CAN接收通道
        self.bus_recv = can.interface.Bus(channel=channel_recv, bustype=bustype, baudrate=baudrate)
        self.recv_msg_timestamps = []
        self.estimated_frequency = 0

        # self.bus_recv = can.interface.Bus(channel=channel_recv, bustype=bustype, baudrate=baudrate)
        # self.recv_msg_timestamps = defaultdict(deque)
        # self.estimated_frequencies = {}
        

    # def receive_data(self, capture_duration):
    #     """
    #     接收CAN数据，并计算每个CAN ID的发送频率。
    #     :param capture_duration: 数据捕获的持续时间（秒）。
    #     """
    #     end_time = time.time() + capture_duration
    #     print("开始接收数据...")
    #     while time.time() < end_time:
    #         msg = self.bus_recv.recv(timeout=1)  # 设置超时，避免无限等待
    #         if msg:
    #             print(f"接收到数据: CAN ID={msg.arbitration_id}, 数据={msg.data}")
    #             current_time = time.perf_counter()
    #             timestamps = self.recv_msg_timestamps[msg.arbitration_id]
    #             timestamps.append(current_time)
    #             if len(timestamps) > 1:
    #                 # 计算每个CAN ID的平均间隔和估计频率
    #                 intervals = [timestamps[i] - timestamps[i-1] for i in range(1, len(timestamps))]
    #                 average_interval = statistics.mean(intervals)
    #                 self.estimated_frequencies[msg.arbitration_id] = 1 / average_interval if average_interval else 0
    #         else:   
    #             print("在设定的超时时间内未接收到数据")
    
    # def display_estimated_frequencies(self):
    #     # 打印每个CAN ID的估计发送频率
    #     for can_id, frequency in self.estimated_frequencies.items():
    #         print(f"CAN ID {can_id}: Estimated Frequency = {frequency:.2f} Hz")

    def receive_data(self, capture_duration):
        """
        接收CAN数据，并计算总的发送频率。
        :param capture_duration: 数据捕获的持续时间（秒）。
        """
        end_time = time.time() + capture_duration
        print("开始接收数据...")
        try:
            while time.time() < end_time:
                msg = self.bus_recv.recv(timeout=1)  # 设置超时，避免无限等待
                if msg:
                    print(f"接收到数据: CAN ID={msg.arbitration_id}, 数据={msg.data}")
                    current_time = time.perf_counter()
                    self.recv_msg_timestamps.append(current_time)
                    if len(self.recv_msg_timestamps) > 1:
                        # 计算所有消息的平均间隔和估计频率
                        intervals = [self.recv_msg_timestamps[i] - self.recv_msg_timestamps[i-1] for i in range(1, len(self.recv_msg_timestamps))]
                        average_interval = statistics.mean(intervals)
                        self.estimated_frequency = 1 / average_interval if average_interval else 0
                else:
                    print("在设定的超时时间内未接收到数据")
        except Exception as e:
            print(f"数据接收过程中发生异常: {e}")
        finally:
            self.bus_recv.shutdown()  # 确保总线正确关闭
            print("PcanBus was properly shut down")

    def display_estimated_frequency(self):
        # 打印总的估计发送频率
        print(f"Estimated Frequency = {self.estimated_frequency:.2f} Hz")

    def shutdown(self):
        # 关闭CAN通道
        self.bus_recv.shutdown()


def send_data(sender, can_id, message, duration, fps):
    sender.send_data(can_id, message, duration, fps)
    sender.shutdown()

def receive_data(receiver, duration):
    receiver.receive_data(duration)
    receiver.display_estimated_frequency()
    receiver.shutdown()

if __name__ == "__main__":
    sender = CANSender()
    receiver = CANReceiver()

    # 创建线程用于发送和接收)
    sender_thread = threading.Thread(target=send_data, args=(sender, 0x123, [0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08], 5, 1))
    receiver_thread = threading.Thread(target=receive_data, args=(receiver, 3))

    # 启动线程

    receiver_thread.start()
    sender_thread.start()

    # 等待线程完成
    sender_thread.join()
    receiver_thread.join()
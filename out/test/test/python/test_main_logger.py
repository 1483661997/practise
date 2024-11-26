import datetime
import logging
import os

isolation = "**************************************************************************************************************************************************************"
isolation2 = "--------------------------------------------------------------------------------------------------------------------------------------------------------------"


def create_timed_filename(path):
    # 获取当前工作目录
    # 确定data目录的路径
    data_directory = path
    # 如果data目录不存在,创建它
    if not os.path.exists(data_directory):
        os.makedirs(data_directory)
    # 获取当前时间并格式化为字符串
    current_time = datetime.datetime.now().strftime('%Y-%m-%d_%H-%M-%S')
    # 构建文件名称,包含当前时间
    filename = f"log_{current_time}.txt"
    full_path = os.path.join(data_directory, filename)
    # 返回完整的文件路径
    return full_path

# def create_timed_filename():
#     # 获取当前工作目录
#     # current_path = os.getcwd()
#     current_file_path = os.path.abspath(__file__)
#     current_path = os.path.dirname(current_file_path)
#     print(current_path)
#     # 确定data目录的路径
#     data_directory = os.path.join(current_path, 'data')
#     # 如果data目录不存在,创建它
#     if not os.path.exists(data_directory):
#         os.makedirs(data_directory)
#     # 获取当前时间并格式化为字符串
#     current_time = datetime.datetime.now().strftime('%Y-%m-%d_%H-%M-%S')
#     # 构建文件名称,包含当前时间
#     filename = f"log_{current_time}.txt"
#     full_path = os.path.join(data_directory, filename)
#     # 返回完整的文件路径
#     return full_path


def setup_logging(path="/var/pony/recorded_data/esw_test_log/localization/"):
    log_filename = create_timed_filename(path)
    # 创建日志器对象,并设置日志级别
    logger = logging.getLogger(__name__)
    logger.setLevel(logging.DEBUG)

    # 创建一个输出到文件的处理器,并设置级别为 DEBUG
    file_handler = logging.FileHandler(log_filename)
    file_handler.setLevel(logging.DEBUG)
    file_formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
    file_handler.setFormatter(file_formatter)
    logger.addHandler(file_handler)

    # 创建一个输出到控制台的处理器,并设置级别为 INFO
    console_handler = logging.StreamHandler()
    console_handler.setLevel(logging.INFO)
    console_formatter = logging.Formatter('%(name)s - %(levelname)s - %(message)s')
    console_handler.setFormatter(console_formatter)
    logger.addHandler(console_handler)

    return logger
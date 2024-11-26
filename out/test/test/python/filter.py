import re

def filter_log_file(input_file_path, output_file_path):
    # 定义一个字符串包含 "更新总结"
    keyword = "更新总结"

    with open(input_file_path, 'r') as file:
        lines = file.readlines()
    
    with open(output_file_path, 'w') as output_file:
        for line in lines:
            # 检查每一行是否包含关键词 "更新总结"
            if keyword in line:
                # 如果找到关键词，则将该行写入新的文件
                output_file.write(line)
# def filter_log_file(input_file_path, output_file_path):
#     # 定义一个正则表达式匹配包含 'The upgrade has finished :X%.' 的行，其中X可以是任何数字
#     pattern = re.compile(r'The upgrade has finished :\d+%.')

#     with open(input_file_path, 'r') as file:
#         lines = file.readlines()
    
#     with open(output_file_path, 'w') as output_file:
#         for line in lines:
#             # 搜索每一行是否符合定义的模式
#             if not pattern.search(line) and line.strip():
#                 # 如果没有找到模式且行不为空，则将行写入新的文件
#                 output_file.write(line)
def filter_data(file_path):
    with open(file_path, 'r') as file:
        # 读取每一行数据
        for line in file:
            # 判断status是否不等于'00000001'
            if 'status:00000001' not in line:
                print(line.strip())  # 打印不符合条件的行

# if __name__ == '__main__':
#     data_file = 'data.txt'  # 数据文件路径
#     filter_data(data_file)

# 使用示例
input_path = '/home/haobaisong/Documents/AutotestLog/log_2024-06-27_19-21-04_copy.txt'  # 这是要过滤的日志文件的路径
output_path = '/home/haobaisong/Documents/AutotestLog/log_2024-06-27_19-21-04_copy_summary.txt'  # 这是输出的日志文件的路径
imu_log_path = '/home/haobaisong/Documents/work/Kunlun/components/imu/chunhui_down.txt'
# filter_log_file(input_path, output_path)
filter_data(imu_log_path)

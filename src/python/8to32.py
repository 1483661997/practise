
def read_and_filter_data(file_path):
    with open(file_path, 'r') as file:
        lines = file.readlines()

    filtered_data = []
    last_status = None

    for line in lines:
        # 解析status
        status_part = line.split('status:')[1].split(',')[0].strip()
        
        # 比较status，如果发生变化或者是第一条数据，则添加到结果中
        if status_part != last_status:
            filtered_data.append(line)
            last_status = status_part

    return filtered_data


def btox(value8):

    # status:00000C01
    # 打印从bit0到bit31的每一位
    '''
00000001 
00000C01
00000E01

status:00000E01,
status:00000601,
status:00000201,
status:00000001,

status:00000A01,
    '''
    for i in range(32):
        print(f'bit{i}: {(value8 >> i) & 0x1}')

def main():
    # file_path = '/home/haobaisong/Documents/work/Kunlun/components/imu/record/imu_pps_CToDis_DisToC_20.txt'  # 替换为你的文件路径
    # filtered_data = read_and_filter_data(file_path)

    # # 打印或保存过滤后的结果
    # for data in filtered_data:
    #     print(data)
    btox(0x00000A01)
    
if __name__ == "__main__":
 
    main()
    
   

import logging
from datetime import datetime
import sys
def setup_logger():
    # 获取当前时间，用于生成文件名
    current_time = datetime.now().strftime("%Y-%m-%d_%H-%M-%S")
    log_filename = f"kevindffs_{current_time}.log"

    # 创建 logger
    logger = logging.getLogger('AppLogger')
    logger.setLevel(logging.DEBUG)  # 捕获所有等级的日志

    # 创建文件处理器，并设置日志级别
    file_handler = logging.FileHandler(log_filename)
    file_handler.setLevel(logging.DEBUG)

    # 创建一个 formatter
    formatter = logging.Formatter('%(asctime)s - %(levelname)s - %(message)s')
    file_handler.setFormatter(formatter)

    # 添加文件处理器到 logger
    logger.addHandler(file_handler)

    return logger

class StreamToLogger:
    def __init__(self, logger, log_level):
        self.logger = logger
        self.log_level = log_level
        self.linebuf = ''

    def write(self, buf):
        for line in buf.rstrip().splitlines():
            self.logger.log(self.log_level, line.rstrip())
    
    def write_line(self, buf):
        # This will remove any trailing new line and split into lines
        lines = buf.rstrip().splitlines()
        if lines:
            last_line = lines[-1]  # We only care about the last line for console output
            # Update the console on the same line
            sys.stdout.write('\r' + last_line)
            sys.stdout.flush()
            # Log all lines to the file
            for line in lines:
                self.logger.log(self.log_level, line.rstrip())
    def flush(self):
        pass
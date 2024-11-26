#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include "PCANBasic.h"

// 发送线程函数
void* send_thread(void* arg) {
    TPCANStatus status;
    TPCANMsg message;
    message.ID = 0x123;
    message.LEN = 2;
    message.MSGTYPE = PCAN_MESSAGE_STANDARD;
    message.DATA[0] = 0x11;
    message.DATA[1] = 0x22;

    // 初始化PCAN通道
    status = CAN_Initialize(PCAN_USBBUS1, PCAN_BAUD_500K, 0, 0, 0);
    if (status != PCAN_ERROR_OK) {
        printf("initializing 出错辣\n");
        pthread_exit(NULL);
    }

    // 发送消息
    for (int i = 0; i < 10; i++) {
        status = CAN_Write(PCAN_USBBUS1, &message);
        if (status != PCAN_ERROR_OK) {
            printf("出错了\n");
        } else {
            printf("Victory\n");
        }
        sleep(1);  // Wait a bit before next send
    }

    // 取消初始化PCAN通道
    CAN_Uninitialize(PCAN_USBBUS1);
    pthread_exit(NULL);
}

// 接收线程函数
void* receive_thread(void* arg) {
    TPCANStatus status;
    TPCANMsg message;

    // 初始化PCAN通道
    status = CAN_Initialize(PCAN_USBBUS2, PCAN_BAUD_500K, 0, 0, 0);
    if (status != PCAN_ERROR_OK) {
        printf("Error initializing 出错辣\n");
        pthread_exit(NULL);
    }

    // 接收消息
    while (1) {
        status = CAN_Read(PCAN_USBBUS2, &message, NULL);
        if (status == PCAN_ERROR_QRCVEMPTY) {
            usleep(100000);  // No message available, wait 100ms
        } else if (status != PCAN_ERROR_OK) {
            printf("出错辣\n");
        } else {
            printf("Victory 收到 ID: 0x%X\n", message.ID);
        }
    }

    // 取消初始化PCAN通道
    CAN_Uninitialize(PCAN_USBBUS2);
    pthread_exit(NULL);
}

int main() {
    pthread_t tid1, tid2;

    // 创建发送和接收线程
    pthread_create(&tid1, NULL, send_thread, NULL);
    pthread_create(&tid2, NULL, receive_thread, NULL);

    // 等待线程结束
    pthread_join(tid1, NULL);
    pthread_join(tid2, NULL);

    return 0;
}

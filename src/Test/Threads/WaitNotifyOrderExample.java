package Test.Threads;

public class WaitNotifyOrderExample {
    private static final Object lock = new Object();
    private static boolean isThread2Waiting = false; // 用于确保线程2已经在等待

    public static void main(String[] args) {
        // 线程2: 等待被唤醒
        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    while (!isThread2Waiting) { // 确保线程2报告已准备好等待
                        System.out.println("线程2准备进入等待状态");
                        isThread2Waiting = true; // 标记线程2正在等待
                        lock.wait();
                        System.out.println("线程2被唤醒并继续执行...");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("线程2被中断");
                }
            }
        });


        // 线程1: 检查线程2是否在等待后发送通知
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                while (!isThread2Waiting) { // 等待直到线程2准备好进入等待状态
                    try {
                        System.out.println("线程1正在等待线程2准备好进入等待状态");
                        lock.wait(); // 可选，使得线程1不会在检查前独占 CPU
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println("线程1发现线程2已在等待，准备发出通知");
                lock.notify(); // 唤醒线程2
                System.out.println("线程1已发出通知");
            }
        });

        thread1.start();

        try {
            Thread.sleep(100); // 确保线程2有足够时间进入wait状态
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread2.start(); // 先启动线程2确保它可以先进入wait状态
      
    }
}

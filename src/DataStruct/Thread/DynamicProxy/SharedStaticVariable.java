package DataStruct.Thread.DynamicProxy;

public class SharedStaticVariable {
    // 静态变量
    public static int sharedCount = 0;

    private static int sharedCount1 = 0;

    private static int sharedCount2 = 0;

    public static void main(String[] args) {
        // 线程1，模拟第一个程序
        Thread program1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                incrementCount();
                System.out.println("Program 1 - Count: " + sharedCount);
                try {
                    Thread.sleep(100); // 暂停100ms模拟处理
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // 线程2，模拟第二个程序
        Thread program2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                incrementCount();
                System.out.println("Program 2 - Count: " + sharedCount);
                try {
                    Thread.sleep(100); // 暂停100ms模拟处理
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        // 启动线程
        program1.start();
        program2.start();
    }

    // 增加sharedCount的同步方法
    public synchronized static void incrementCount() {
        sharedCount++;
    }
}

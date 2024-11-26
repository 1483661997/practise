package Test.Threads;

public class Client2 {
    private static final Object lock = new Object();
    private static int num = 0;
    private static boolean find = false;
    private static boolean running = true; // 控制线程运行的变量

    public static void main(String[] args) throws InterruptedException {
        Thread t2 = new Thread(() -> {
            while (running) {
                synchronized(lock){
                    while (!find && running) { // 循环等待直到find为true或者不再运行
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Thread interrupted.");
                            return; // 如果线程被中断，优雅退出
                        }
                    }
                    if (!running) break; // 如果running为false，退出循环
                    num++;
                    find = false;
                    lock.notifyAll();
                }
            }
            System.out.println("Thread2结束");
        });

        Thread t1 = new Thread(() -> {
            for(int i = 1; i <= 1000; i++){
                if(isDiv(i)){
                    System.out.println(i + " " + num);
                    synchronized(lock){
                        find = true;
                        lock.notifyAll();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            synchronized (lock) {
                running = false; // 通知t2线程结束
                lock.notifyAll(); // 确保如果t2正在等待，它能够退出等待状态
            }
        });

        t1.start();
        t2.start();

        t1.join(); // 等待t1线程结束
        t2.join(); // 硉待t2线程结束

        System.out.println("Final counter: " + num);
    }

    public static boolean isDiv(int n){
        return (n % 2 == 0 && n % 3 == 0 && n % 5 == 0);
    }
}

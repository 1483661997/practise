package Test.Threads;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 第一个线程：遍历 [1,1000][1,1000] 中所有的数字，在遍历过程中，如果发现这个数字能同时被 2,3,52,3,5 整除，立即等待，让第二个线程进入。
 *  第二个线程：运行时，将一个计数器 +1+1，然后唤醒等待的线程。 主线程：睡眠 22 秒，让两个线程全部执行完毕，打印计数器的结果。

 */
public class Client3 {
    private static final ReentrantLock  lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();
    private static  boolean running = true;
    private static  boolean find = false;
    private static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run(){
                for(int i = 1; i < 1000; i++){
                    if(isDiv(i)){
                        System.out.println(i + " " + num);
                        lock.lock();
                        find = true;
                        condition.signalAll();
                        try {
                            condition.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        lock.unlock();
                    }
                }

                lock.lock();
                running = false;
                find = true;
                condition.signalAll();
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            while(running){
                lock.lock();
                while(!find){
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(!running) break; 
                num++;
                find = false;
                condition.signalAll();
                lock.unlock();  
            }
        });

        t1.start();
        t2.start();
        t1.join();;
        t2.join();

        Thread.sleep(3000);
        System.out.println("result: " + num);
        
    }

    public static boolean isDiv(int n){
        return (n % 2 == 0 && n % 3 == 0 && n % 5 == 0);
    }

}

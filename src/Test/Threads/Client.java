package Test.Threads;

public class Client {
    public static volatile int number = 0;
    private static final Object lock = new Object();
    private static boolean writeComplete = false;

    private static void read(){
        synchronized(lock){
            while(!writeComplete){
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("***********");
        }        
    }
    
    public static void write(int change){
       synchronized(lock){
            number += change;
       }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            
            for(int i = 0; i < 10; i++){
                write(1);   
                System.out.println("+1 times");
            }
            // System.out.println("+10000 times");

            writeComplete = true;
            // synchronized(lock){
            //     lock.notifyAll();

            // }
        }
            
        ).start();

        new Thread(() -> {
            
            // read();
            for(int i = 0; i < 10; i++){
                read();
            }
            // System.out.println("-10000 times");
          }

        ).start();
        Thread.sleep(1000);
        // read();
    }
}

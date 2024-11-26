package Test;

import java.util.HashMap;

public class TestHashMap {
    public static void main(String[] args) throws InterruptedException{
        final int DATA_SIZE = 2;
        HashMap<String, String> map = new HashMap<>();
        for(int i = 0; i < DATA_SIZE; i++){
            final int index = i;
            new Thread(new Runnable() {
                @Override
                public void run(){
                    System.out.println("Thread" + index + "starts.");
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    map.put("key" + index, "value" + index);
                    System.out.println("Thread " + index + " is done.");
                }                 
            }).start();        
        }
        Thread.sleep(2000);
        System.out.println("size: " + map.entrySet().size());
        for(int i = 0; i < DATA_SIZE; i++){
            System.out.println("entry: key" + i + " -> " + map.get("key" + i) + "\t");
        }
    }

}

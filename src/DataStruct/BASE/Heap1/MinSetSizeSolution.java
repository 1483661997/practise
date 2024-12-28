package DataStruct.BASE.Heap1;

import java.util.*;
import java.util.PriorityQueue;

public class MinSetSizeSolution {
    
    public int minSetSize(int[] arr) {
        int len = arr.length;
        Map<Integer, Integer> map = new HashMap<>();
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b) -> b-a);
        
        for(int i : arr){
            map.put(i , map.getOrDefault(i, 0)+1);
        }
        
        for(Map.Entry<Integer, Integer> i : map.entrySet()){
            queue.add(i.getValue());
        }

        int num =0 , sum = 0;
        int target = (len+1)/2;
        while (sum < target) {
            sum += queue.poll();
            num++;
        }
        return num;
    }
}

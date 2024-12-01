package DataStruct.BASE.Heap1;

import java.util.PriorityQueue;

public class FindKthLargestSoultion {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b) -> b-a);
        for(int i : nums) 
            queue.add(i);
        for(int i = 1; i < k; i++){
            queue.poll();
        }
        return queue.peek();
    }
    
}

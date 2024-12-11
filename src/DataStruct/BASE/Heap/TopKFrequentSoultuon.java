package DataStruct.BASE.Heap;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 
 * 347. 前 K 个高频元素
已解答
中等
相关标签
相关企业
给你一个整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素。你可以按 任意顺序 返回答案。

 

示例 1:

输入: nums = [1,1,1,2,2,3], k = 2
输出: [1,2]
示例 2:

输入: nums = [1], k = 1
输出: [1]
 

提示：

1 <= nums.length <= 105
k 的取值范围是 [1, 数组中不相同的元素的个数]
题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的

 */
public class TopKFrequentSoultuon {
      public int[] topKFrequent(int[] nums, int k) {
        int[] result = new int[k];
        int len = nums.length;

        Map<Integer, Integer> map = new HashMap<>();

        for(int i = 0; i < len; i++){
            map.put(nums[i], map.getOrDefault(nums[i], 0)+1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>((a,b) -> b.getValue() - a.getValue());
        
        for(Map.Entry<Integer, Integer> set : map.entrySet()){
            queue.add(set);
        }
        int pos = 0;
        while(!queue.isEmpty() && pos != k){
            result[pos++] = queue.peek().getKey();
            queue.poll();
        }

        return result;
    }
    
}

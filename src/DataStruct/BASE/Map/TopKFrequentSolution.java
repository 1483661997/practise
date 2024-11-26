package DataStruct.Map;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
 * 前 K 个高频元素
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

 

进阶：你所设计算法的时间复杂度 必须 优于 O(n log n) ，其中 n 是数组大小。

 */
public class TopKFrequentSolution {

    public static void main(String[] args) {
        TopKFrequentSolution solution = new TopKFrequentSolution();
        for(int i : solution.topKFrequent(new int[]{1,2}, 2))
            System.out.println(i);
    }
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int len = nums.length; 
        for(int i = 0; i < len; i++){
            int num = map.getOrDefault(nums[i], 0) + 1;
            map.put(nums[i], num);
        }
        int[] res = new int[k];
        
        PriorityQueue<HashMap.Entry<Integer, Integer>> queue = new PriorityQueue<>((a,b) ->  ( - a.getValue() + b.getValue()) );
        
        for(HashMap.Entry<Integer, Integer> tmp : map.entrySet()){
            System.out.println(tmp.getKey());
            queue.add(tmp);
        }
        
        // Collections.sort(ma);
        for(int i = 0 ; i < k; i++){

            int cur = queue.poll().getKey();
            System.out.print(cur + " ");
            res[i] = cur;
        }
        Collections.reverseOrder();
        return res;
    }
    
}

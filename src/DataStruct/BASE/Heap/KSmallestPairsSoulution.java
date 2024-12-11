package DataStruct.BASE.Heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/*
 * 
 *  查找和最小的 K 对数字
中等
相关标签
相关企业

给定两个以 非递减顺序排列 的整数数组 nums1 和 nums2 , 以及一个整数 k 。

定义一对值 (u,v)，其中第一个元素来自 nums1，第二个元素来自 nums2 。

请找到和最小的 k 个数对 (u1,v1),  (u2,v2)  ...  (uk,vk) 。

 

示例 1:

输入: nums1 = [1,7,11], nums2 = [2,4,6], k = 3
输出: [1,2],[1,4],[1,6]
解释: 返回序列中的前 3 对数：
     [1,2],[1,4],[1,6],[7,2],[7,4],[11,2],[7,6],[11,4],[11,6]

示例 2:

输入: nums1 = [1,1,2], nums2 = [1,2,3], k = 2
输出: [1,1],[1,1]
解释: 返回序列中的前 2 对数：
     [1,1],[1,1],[1,2],[2,1],[1,2],[2,2],[1,3],[1,3],[2,3]

 

 */
public class KSmallestPairsSoulution {
    public static void main(String[] args) {
        List<List<Integer>> list = kSmallestPairs(new int[]{1,1}, new int[]{1,1,}, 2);
        for (List<Integer> list2 : list) {
            System.out.println(list2.get(0) + " " + list2.get(1));
        }
    }

    public static List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        PriorityQueue<List<Integer>> queue = new PriorityQueue<>((a, b) -> - a.get(0) - a.get(1) + b.get(0) + b.get(1));
        
        for(int i = 0; i < len1; i++){
            for(int j = 0; j < len2 && i + j < k; j++){
                if(queue.size() < k)
                    queue.add(Arrays.asList(nums1[i], nums2[j]));
                else{
                    if(queue.peek().get(0) + queue.peek().get(1)  > nums1[i] + nums2[j]){
                        queue.poll();
                        queue.add(Arrays.asList(nums1[i], nums2[j]));
                    }else break;
                }
            }
        }

        List<List<Integer>> list = new ArrayList<>();
        while (k-- != 0) {
            
            list.add(queue.poll());

        }
        return list;
    }
}

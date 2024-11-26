package DataStruct.BASE.Window;

import java.util.Deque;
import java.util.LinkedList;

/*
 * 1438. 绝对差不超过限制的最长连续子数组
中等
相关标签
相关企业
提示
给你一个整数数组 nums ，和一个表示限制的整数 limit，请你返回最长连续子数组的长度，该子数组中的任意两个元素之间的绝对差必须小于或者等于 limit 。

如果不存在满足条件的子数组，则返回 0 。

 

示例 1：

输入：nums = [8,2,4,7], limit = 4
输出：2 
解释：所有子数组如下：
[8] 最大绝对差 |8-8| = 0 <= 4.
[8,2] 最大绝对差 |8-2| = 6 > 4. 
[8,2,4] 最大绝对差 |8-2| = 6 > 4.
[8,2,4,7] 最大绝对差 |8-2| = 6 > 4.
[2] 最大绝对差 |2-2| = 0 <= 4.
[2,4] 最大绝对差 |2-4| = 2 <= 4.
[2,4,7] 最大绝对差 |2-7| = 5 > 4.
[4] 最大绝对差 |4-4| = 0 <= 4.
[4,7] 最大绝对差 |4-7| = 3 <= 4.
[7] 最大绝对差 |7-7| = 0 <= 4. 
因此，满足题意的最长子数组的长度为 2 。
示例 2：

输入：nums = [10,1,2,4,7,2], limit = 5
输出：4 
解释：满足题意的最长子数组是 [2,4,7,2]，其最大绝对差 |2-7| = 5 <= 5 。
示例 3：

输入：nums = [4,2,2,2,4,4,2,2], limit = 0
输出：3 

提示：

1 <= nums.length <= 10^5
1 <= nums[i] <= 10^9
0 <= limit <= 10^9
 */

 /*
  * 滑动窗口记录滑动过程的最大值和最小值 ----------- 单调队列
  */
public class longestSubarrayLimit {
    public static void main(String[] args) {
        System.out.println(new longestSubarrayLimit().longestSubarray(new int[]{4,2,2,2,4,4,2,2}, 0));
    }
    public int longestSubarray(int[] nums, int limit) {

        Deque<Integer> maxList = new LinkedList<>();
        Deque<Integer> minList = new LinkedList<>();

        int len = nums.length;
        if(len == 1) return 1;
        int maxLen = 0;
        
        int left = 0, right = 0;
            //如果窗口滑动的过程如果最大最小值消失了再找就行了
        while(right < len){
            while(!maxList.isEmpty() && nums[right] > maxList.peekLast()){
                maxList.pollLast();
            }
            while(!minList.isEmpty() && nums[right] < minList.peekLast()){
                minList.pollLast();
                
            }

            maxList.offerLast(nums[right]);
            minList.offerLast(nums[right]);

            while (!maxList.isEmpty() && !minList.isEmpty() && maxList.peekFirst() - minList.peekFirst() > limit) {
                if(nums[left] == maxList.peekFirst()){
                    maxList.pollFirst();
                }
                if(nums[left] == minList.peekFirst()){
                    minList.pollFirst();
                }
                left++;
            }
            
            maxLen = Math.max(maxLen, right -left + 1);
            right++;

        }

            
        return maxLen;
        

    }
}

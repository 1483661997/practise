package DataStruct.BASE.array;

import java.util.*;

/*
 * 503. 下一个更大元素 II
给定一个循环数组 nums （ nums[nums.length - 1] 的下一个元素是 nums[0] ），返回 nums 中每个元素的 下一个更大元素 。

数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1 。

 

示例 1:

输入: nums = [1,2,1]
输出: [2,-1,2]
解释: 第一个 1 的下一个更大的数是 2；
数字 2 找不到下一个更大的数； 
第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
示例 2:

输入: nums = [1,2,3,4,3]
输出: [2,3,4,-1,4]
 

提示:

1 <= nums.length <= 104
-109 <= nums[i] <= 109
 */

 /*
  * 5 4 3 2 1
  */
public class NextGreaterElementsSolution {
    public int[] nextGreaterElements(int[] nums) {
        
        Deque<Integer> stack = new ArrayDeque<>();
        int len = nums.length;
        int[] res = new int[len];
        Arrays.fill(res, -1);
        for(int i = 0; i < 2 * len; i++){
            int x = nums[i % len];
            while (!stack.isEmpty() && x > nums[stack.peek()]) {
                res[stack.pop()] = x;
            }
            if(i < len)
                stack.push(i);
        }

        return res;
    }
    public int[] nextGreaterElements1(int[] nums) {
    
        int len = nums.length;
        if(len == 1) return new int[]{-1};
        int[] res = new int[len];
        System.out.println(res[0]);
        for(int i = 0; i < len; i++){
            int flag = 1;
            for(int j = 1; j < len; j++){
                int idx = (i + j) % len;
                if(nums[idx] > nums[i]){
                    flag = 0;
                    res[i] = nums[idx];
                    break;
                }

            }
            if(flag == 1) res[i] = -1;
        }
        return res;
    }
}

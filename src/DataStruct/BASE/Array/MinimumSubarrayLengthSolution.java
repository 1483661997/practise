package DataStruct.BASE.Array;

import DataStruct.Leetcode.Competition430.MinimumOperationsSolution;

/**
 * 3095. 或值至少 K 的最短子数组 I
 * 简单
 * 给你一个 非负 整数数组 nums 和一个整数 k 。
 * 如果一个数组中所有元素的按位或运算 OR 的值 至少 为 k ，那么我们称这个数组是 特别的 。
 * 请你返回 nums 中 最短特别非空
 * 子数组
 * 的长度，如果特别子数组不存在，那么返回 -1 。
 * 示例 1：
 * 输入：nums = [1,2,3], k = 2
 * 输出：1
 * 解释：
 * 子数组 [3] 的按位 OR 值为 3 ，所以我们返回 1 。
 * 注意，[2] 也是一个特别子数组。
 * 示例 2：
 * 输入：nums = [2,1,8], k = 10
 * 输出：3
 * 解释：
 * 子数组 [2,1,8] 的按位 OR 值为 11 ，所以我们返回 3 。
 * 示例 3：
 * 输入：nums = [1,2], k = 0
 * 输出：1
 * 解释：
 * 子数组 [1] 的按位 OR 值为 1 ，所以我们返回 1 。
 */
public class MinimumSubarrayLengthSolution {
    public static void main(String[] args) {
        System.out.println(new MinimumSubarrayLengthSolution().minimumSubarrayLength(new int[]{1,2}, 0));
    }
    public int minimumSubarrayLength(int[] nums, int k) {
        if(k == 0) return 1;
        int len = nums.length;
        int ans = Integer.MAX_VALUE;
        int[] bits = new int[30];
        int left = 0 , right = 0;
        while (right < len){
            int val = nums[right++];
            int pos = 0;

            while (val > 0){
                if(val % 2 == 1) bits[pos] = bits[pos] + 1;
                pos++;
                val /= 2;
            }

            while (left < len && cal(bits) >= k ){
                System.out.println(left + " " + right + " " + cal(bits));
                ans = Math.min(ans, right - left);
                System.out.println(ans + "****");

                //删除左侧的数字
                int num  = nums[left++];
                int p = 0;
                while (num > 0){
                    if(num % 2 == 1) bits[p] = bits[p] - 1 ;
                    p++;
                    num /= 2;
                }
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;

    }

    public int cal(int[] bits){
        int value = 0;
        int bit = 1;
        for(int i : bits){
            if(i > 0){
                value += bit;
            }

            bit *= 2;
        }

        return value;
    }
}

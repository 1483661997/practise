package DataStruct.Dp;

/*
 * 152. 乘积最大子数组
中等
相关标签
相关企业

给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续
子数组
（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。

测试用例的答案是一个 32-位 整数。

 

示例 1:

输入: nums = [2,3,-2,4]
输出: 6
解释: 子数组 [2,3] 有最大乘积 6。

示例 2:

输入: nums = [-2,0,-1]
输出: 0
解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。

 

提示:

    1 <= nums.length <= 2 * 104
    -10 <= nums[i] <= 10
    nums 的任何前缀或后缀的乘积都 保证 是一个 32-位 整数


 */
public class MaxProductSolution {
    public int maxProduct(int[] nums) {
        int len = nums.length;
        int[] maxDp = new int[len];
        int[] minDp = new int[len];
        maxDp[0] = nums[0];
        minDp[0] = nums[0];
        int res = nums[0];
        for(int i = 1; i < len; i++){
            maxDp[i] = Math.max(Math.max(maxDp[i-1] * nums[i], minDp[i-1] *nums[i]), nums[i]);
            minDp[i] = Math.min(Math.min(maxDp[i-1] * nums[i], minDp[i-1] *nums[i]), nums[i]);
        }
        
        for(int i = 1; i < len; i++){
            if(maxDp[i] > res ) res = maxDp[i];
        }

        return res;
           
    }
}

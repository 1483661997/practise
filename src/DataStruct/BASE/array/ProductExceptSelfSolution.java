package DataStruct.BASE.array;

/*
 * 除自身以外数组的乘积
中等
相关标签
相关企业
提示

给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。

题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。

请 不要使用除法，且在 O(n) 时间复杂度内完成此题。

 

示例 1:

输入: nums = [1,2,3,4]
输出: [24,12,8,6]

示例 2:

输入: nums = [-1,1,0,-3,3]
输出: [0,0,9,0,0]

 */
public class ProductExceptSelfSolution {
    public static void main(String[] args) {
        for(int i : productExceptSelf(new int[]{-1, 1, 0, -3, 3}))
            System.out.println(i);
    }
    public static int[] productExceptSelf(int[] nums) {
        int len = nums.length;
        int[] leftProduct = new int[len+1];
        int[] rightProduct = new int[len+1];
        leftProduct[0] = 1;
        rightProduct[len-1] = 1;
        for(int i = 0; i < len; i++){
            leftProduct[i+1] = leftProduct[i] * nums[i];
        }
        for(int i = len-1; i > 0; i--)
            rightProduct[i-1] = rightProduct[i] * nums[i];

        int[] res = new int[len];

        for(int i = 0; i < len; i++){
            res[i] = leftProduct[i] * rightProduct[i];
        }
        return res;
    }
}

package DataStruct.BASE.SubString;

/*
 * 和为 K 的子数组
中等
相关标签
相关企业
提示

给你一个整数数组 nums 和一个整数 k ，请你统计并返回 该数组中和为 k 的子数组的个数 。

子数组是数组中元素的连续非空序列。

 

示例 1：

输入：nums = [1,1,1], k = 2
输出：2

示例 2：

输入：nums = [1,2,3], k = 3
输出：2
0
 */
public class SubarraySumSolution {
    public static void main(String[] args) {
        System.out.println(subarraySum(new int[]{1, 1, 1}, 2));
    }
    public static int subarraySum(int[] nums, int k) {
        int left = 0, right = 0;
        int len = nums.length;
        int sum = 0;
        int num = 0;
        int[] preNum = new int[len + 1];
        // preNum[0] = nums[0];
        for(int i = 0; i < len; i++){
            preNum[i+1] = preNum[i] + nums[i];
        }

        for(int i = 0; i < len; i++){
            for(int j = i; j < len; j++)
                if(preNum[j+1] - preNum[i] == k) num++; 
        }
        

        return num;
    }
}

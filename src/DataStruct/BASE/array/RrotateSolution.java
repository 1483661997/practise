package DataStruct.array;


/*
 * 轮转数组
中等
相关标签
相关企业
提示

给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。

 

示例 1:

输入: nums = [1,2,3,4,5,6,7], k = 3
输出: [5,6,7,1,2,3,4]
解释:
向右轮转 1 步: [7,1,2,3,4,5,6]
向右轮转 2 步: [6,7,1,2,3,4,5]
向右轮转 3 步: [5,6,7,1,2,3,4]

示例 2:

输入：nums = [-1,-100,3,99], k = 2
输出：[3,99,-1,-100]
解释: 
向右轮转 1 步: [99,-1,-100,3]
向右轮转 2 步: [3,99,-1,-100]
 */
public class RrotateSolution {
    public static void main(String[] args) {
        rotate(new int[]{1,2,3,4,5,6,7}, 3);
        // System.out.println();
    }
    public static void rotate(int[] nums, int k) {
        int len = nums.length;
        int[] res = new int[len];
        for(int i = 0; i < len; i++){
            res[i] = nums[(len - k - 1 + i) % k];
        }
        for(int i = 0;i < len; i++)
            nums[i] = res[i];
    }
}

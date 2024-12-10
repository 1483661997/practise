package DataStruct.BASE.Array;


/*
 * 移动零
简单
相关标签
相关企业
提示

给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。

请注意 ，必须在不复制数组的情况下原地对数组进行操作。

 

示例 1:

输入: nums = [0,1,0,3,12]
输出: [1,3,12,0,0]

示例 2:

输入: nums = [0]
输出: [0]


 */
public class pointers {
    public void moveZeroes(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len+1];
        int num = 0;
        for(int i = 1; i <= len; i++){
            dp[i] = nums[i-1] == 0 ? dp[i-1] + 1 : dp[i-1];
            if(nums[i] == 0 ) num++;
        }
        for(int i = 0; i < len; i++){
            if(dp[i] <= i){
                nums[i-dp[i]] = nums[i];
            }
        }

        for(int i = len-num; i < len; i++)
            nums[i] = 0;

    }

}

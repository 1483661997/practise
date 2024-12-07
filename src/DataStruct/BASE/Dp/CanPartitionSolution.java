package DataStruct.BASE.Dp;

import java.util.Arrays;

/*
 * 416. 分割等和子集
给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
示例 1：
输入：nums = [1,5,11,5]
输出：true
解释：数组可以分割成 [1, 5, 5] 和 [11] 。
示例 2：
输入：nums = [1,2,3,5]
输出：false
解释：数组不能分割成两个元素和相等的子集。
提示：
    1 <= nums.length <= 200
    1 <= nums[i] <= 100
 */
public class CanPartitionSolution {
    public static void main(String[] args) {
        CanPartitionSolution solution = new CanPartitionSolution();
        System.out.println(solution.canPartition(new int[]{2,2,3,5}));
    }
    
    public boolean canPartition(int[] nums) {
        int sum = 0;
        int len = nums.length;
        for(int i : nums)
            sum += i;
        if(sum % 2 == 1) return false;
        int target = sum/2;

        boolean[] dp = new boolean[target+1];
        dp[0] = true;

        for(int i = 0; i < len; i++){
            int num = nums[i];
            for(int j = target; j >= num; j--){
                dp[j] |= dp[j-num];
            }
        }


        return dp[target];
    }
    public boolean canPartition2(int[] nums) {
        int sum = 0;
        int len = nums.length;
        for(int i : nums)
            sum += i;
        if(sum % 2 == 1) return false;
        int target = sum/2;
        boolean[][] dps = new boolean[len][target+1];
        for(int i = 0; i < len; i++){
            dps[i][0] = true;
        }
        
        for(int i = 1; i < len; i++){
            for(int j = 0; j < target; j++){
                if(j < nums[i]){
                    dps[i][j] = dps[i-1][j];
                }else{
                    dps[i][j] = dps[i-1][j] | dps[i-1][j-nums[i]];
                }
            }
        }

        return dps[len-1][target];
    }
//    public void cal(int[] nums, int target){
//        if(target==0){
//            isValid = true;
//            return;
//        }
//        int len = nums.length;
//        for(int i = 0; i < len && !isValid; i++){
//            if(!isVisited[i] && nums[i] <= target){
//                isVisited[i] = true;
//                cal(nums, target-nums[i]);
//                isVisited[i] = false;
//            }
//        }
//
//    }



    public boolean canPartition1(int[] nums) {
        int target = 0;
        int len = nums.length;
        
        for(int i : nums)
            target += i;
        if(target %2 == 1) return false;
        target >>= 1;
        //包含 n 行 target+1 列

        boolean[][] dp = new boolean[len][target+1];
        dp[0][nums[0]] = true;
        //其中 dp[i][j] 
        // 表示从数组的 [0,i][0,i][0,i] 下标范围内选取若干个正整数（可以是 0 个），
        //是否存在一种选取方案使得被选取的正整数的和等于 j。
        //初始时，dp 中的全部元素都是 false。

        // dp[i][j]={dp[i−1][j] ∣ dp[i−1][j−nums[i]], dp[i−1][j],​j≥nums[i]j<nums[i]​

        for(int i = 0; i < len; i++)
            dp[i][0] = true;

        // for(int i = 1; i <= target; i++){
        //         if(nums[i] == i) dp[0][i] =true;
        //         else if(nums[i] < i) dp[0][i] = dp[0][i-1]; 


            
        // }
        for(int i =1; i < len; i++){
            for(int j = 1; j <= target; j++){
                if(nums[i] > j){
                    dp[i][j] = dp[i-1][j];
                }else{
                    dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i]];
                }
                
            }
        }

        

       
        
        // 1, 12, 123, 1234, 2,23,24,3,34,4
        // 2 3 4 8 9 14 
        

        return dp[len-1][target];
    }
}

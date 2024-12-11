package DataStruct.BASE.KadaneAlogorithm;

/*
 * 环形子数组的最大和

给定一个长度为 n 的环形整数数组 nums ，返回 nums 的非空 子数组 的最大可能和 。

环形数组 意味着数组的末端将会与开头相连呈环状。形式上， nums[i] 的下一个元素是 nums[(i + 1) % n] ， nums[i] 的前一个元素是 nums[(i - 1 + n) % n] 。

子数组 最多只能包含固定缓冲区 nums 中的每个元素一次。形式上，对于子数组 nums[i], nums[i + 1], ..., nums[j] ，不存在 i <= k1, k2 <= j 其中 k1 % n == k2 % n 。

 

示例 1：

输入：nums = [1,-2,3,-2]
输出：3
解释：从子数组 [3] 得到最大和 3

示例 2：

输入：nums = [5,-3,5]
输出：10
解释：从子数组 [5,5] 得到最大和 5 + 5 = 10

示例 3：

输入：nums = [3,-2,2,-3]
输出：3
解释：从子数组 [3] 和 [3,-2,2] 都可以得到最大和 3

 */
public class maxSubarraySumCircularSolution {
    public static void main(String[] args) {
        System.out.println(
            maxSubarraySumCircular4(new int[]{5,-3,5}));;
    }

    public static int maxSubarraySumCircular4(int[] nums) {
        int max = Integer.MIN_VALUE;
        
        int n = nums.length;
        int[] leftMax = new int[n];
        // 对坐标为 0 处的元素单独处理，避免考虑子数组为空的情况
        leftMax[0] = nums[0];
        int leftSum = nums[0];
        int pre = nums[0];
        int res = nums[0];

        for (int i = 1; i < n; i++) {
            pre = Math.max(pre + nums[i], nums[i]);
            res = Math.max(res, pre);

            leftSum += nums[i];
            leftMax[i] = Math.max(leftMax[i - 1], leftSum);
        
        }

        max = 0;
        for(int i = n - 1; i > 0; i--){
            max += nums[i];
            int tmp =  Math.max(leftMax[i-1] , leftMax[i-1] + max);
            res = Math.max(res,tmp);

        }

        return res;
    }
    public int maxSubarraySumCircular1(int[] nums) {
        int n = nums.length;
        int[] leftMax = new int[n];
        // 对坐标为 0 处的元素单独处理，避免考虑子数组为空的情况
        leftMax[0] = nums[0];
        int leftSum = nums[0];
        int pre = nums[0];
        int res = nums[0];

        for (int i = 1; i < n; i++) {
            pre = Math.max(pre + nums[i], nums[i]);
            res = Math.max(res, pre);
            leftSum += nums[i];
            leftMax[i] = Math.max(leftMax[i - 1], leftSum);
        }

        // 从右到左枚举后缀，固定后缀，选择最大前缀
        int rightSum = 0;
        for (int i = n - 1; i > 0; i--) {
            rightSum += nums[i];
            res = Math.max(res, rightSum + leftMax[i - 1]);
        }
        return res;
    }

    public int maxSubarraySumCircular(int[] nums) {
         
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int len = nums.length;
        int max = dp[0];

        for(int i = 0; i < len; i++){
            int[] tmp = new int[len];
            for(int j = i; j < len; j++)
                tmp[j-i] = nums[j];
            for(int j = 0; j < i; j++){
                tmp[len - i + j] = nums[j];
            } 
            
            dp[i] = maxSubArray(tmp);
            
            if(dp[i] > max ) max =dp[i];
        
        }

        
        return max;
    }
    public int maxSubArray(int[] nums) {
       
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int len = nums.length;
        int max = dp[0];
        for(int i = 1; i < len; i++){
            
            dp[i] = nums[i] > nums[i] + dp[i-1] ? nums[i] : nums[i] + dp[i-1];

            if(dp[i] > max ) max =dp[i];
        }
        return max;
    }

    public int maxSubarraySumCircular2(int[] nums) {
        int totalSum = 0;
        int maxKadane = kadane(nums);
        int maxInvertedKadane = 0;
        
        for (int i = 0; i < nums.length; i++) {
            totalSum += nums[i];
            nums[i] = -nums[i]; // 取反
        }
        
        maxInvertedKadane = kadane(nums);
        
        // 如果 maxInvertedKadane 是整个数组和的负数，说明全部元素都被取反了
        if (maxInvertedKadane == -totalSum) {
            return maxKadane;
        }
        
        int maxWrap = totalSum + maxInvertedKadane; // maxInvertedKadane 是负的
        
        return Math.max(maxKadane, maxWrap);
    }

    private int kadane(int[] nums) {
        int maxEndingHere = nums[0];
        int maxSoFar = nums[0];
        for (int i = 1; i < nums.length; i++) {
            maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }
}
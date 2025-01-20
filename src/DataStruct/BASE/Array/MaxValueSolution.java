package DataStruct.BASE.Array;

/**
 * 3287. 求出数组中最大序列值
 * 困难
 * 给你一个整数数组 nums 和一个 正 整数 k 。
 * 定义长度为 2 * x 的序列 seq 的 值 为：
 * (seq[0] OR seq[1] OR ... OR seq[x - 1]) XOR (seq[x] OR seq[x + 1] OR ... OR seq[2 * x - 1]).
 * 请你求出 nums 中所有长度为 2 * k 的子序列的 最大值 。
 * 示例 1：
 * 输入：nums = [2,6,7], k = 1
 * 输出：5
 * 解释：
 * 子序列 [2, 7] 的值最大，为 2 XOR 7 = 5 。
 * 示例 2：
 * 输入：nums = [4,2,5,6,7], k = 2
 * 输出：2
 * 解释：
 * 子序列 [4, 5, 6, 7] 的值最大，为 (4 OR 5) XOR (6 OR 7) = 2 。
 * 提示：
 * 2 <= nums.length <= 400
 * 1 <= nums[i] < 27
 */
public class MaxValueSolution {
    public int maxValue(int[] nums, int k) {
        int len = nums.length;
        if(2 * k > len) return 0;
        int[][] dp = new int[len][len];

        int ans = 0;

        for(int i = 0; i < len; i++){
            dp[i][i] = nums[i];
        }

        for(int i = 0; i < len; i++){
            for(int j = i + 1; j < len; j++){
                dp[i][j] = nums[j] & dp[i][j-1];
            }
        }

        return  0;

    }

}

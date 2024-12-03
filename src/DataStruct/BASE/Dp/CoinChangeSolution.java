package DataStruct.BASE.Dp;

import java.util.Arrays;

import javax.swing.text.Style;

/**
 * 322. 零钱兑换
给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。
计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。
你可以认为每种硬币的数量是无限的。
示例 1：
输入：coins = [1, 2, 5], amount = 11
输出：3 
解释：11 = 5 + 5 + 1
示例 2：
输入：coins = [2], amount = 3
输出：-1
示例 3：
输入：coins = [1], amount = 0
输出：0
 */
public class CoinChangeSolution {
    public static void main(String[] args) {
        System.out.println(new CoinChangeSolution().coinChange(new int[]{186,419,83,408}, 6249));
    }
    public int coinChange(int[] coins, int amount) {
        int len = coins.length;
        int[] dp = new int[amount+1];
        Arrays.fill(dp, Integer.MAX_VALUE/2);
        dp[0] = 0;
        
        for(int i = 1; i <= amount; i++){
            int min = Integer.MAX_VALUE/2;
            for(int j = 0; j < len; j++){
                if(coins[j] <= i){
                    min = Math.min(min, dp[i-coins[j]]);
                }   
            }
            dp[i] = min+1;
        }

        return dp[amount] == Integer.MAX_VALUE/2 ? -1 : dp[amount];
    }
}

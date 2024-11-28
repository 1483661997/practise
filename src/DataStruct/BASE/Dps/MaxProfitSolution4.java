package DataStruct.BASE.Dps;

/*
 * 买卖股票的最佳时机 IV
困难
相关标签
相关企业

给你一个整数数组 prices 和一个整数 k ，其中 prices[i] 是某支给定的股票在第 i 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 k 笔交易。也就是说，你最多可以买 k 次，卖 k 次。

注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

 

示例 1：

输入：k = 2, prices = [2,4,1]
输出：2
解释：在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。

示例 2：

输入：k = 2, prices = [3,2,6,5,0,3]
输出：7
解释：在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
     随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。

 

提示：

    1 <= k <= 100
    1 <= prices.length <= 1000
    0 <= prices[i] <= 1000


 */
public class MaxProfitSolution4 {
    public int maxProfit(int k, int[] prices) {

        /*
         *     定义状态：
        使用一个三维数组 dp 来表示状态：
            dp[i][j][0] 表示在第 ii 天进行了 jj 次交易且没有持有股票时的最大利润。
            dp[i][j][1] 表示在第 ii 天进行了 jj 次交易且持有股票时的最大利润。

    状态转移方程：
    
        对于 dp[i][j][0]：
            第 ii 天没有持有股票，可以由前一天没有持有股票（dp[i-1][j][0]）或前一天持有股票并在今天卖出（dp[i-1][j][1] + prices[i]）转移过来。
            dp[i][j][0] = max(dp[i-1][j][0], dp[i-1][j][1] + prices[i])
        对于 dp[i][j][1]：
            第 ii 天持有股票，可以由前一天持有股票（dp[i-1][j][1]）或前一天没有持有股票并在今天买入（dp[i-1][j-1][0] - prices[i]）转移过来。
            dp[i][j][1] = max(dp[i-1][j][1], dp[i-1][j-1][0] - prices[i])

    初始化：
        dp[0][0][0] = 0，表示在第0天没有进行交易且不持有股票的最大利润为0。
        dp[0][0][1] = -prices[0]，表示在第0天进行一次交易且持有股票的最大利润为负的股票价格。
        对于所有其他的初始状态，应该设置为适当的初值。

    边界条件：
        如果没有进行任何交易，利润为0。
        如果 k 大于总交易天数的一半，相当于没有限制交易次数，可以使用贪心算法直接求解。
         */

         if (prices == null || prices.length == 0) return 0;
        int n = prices.length;
        
        // If k >= n/2, then it's equivalent to unlimited transactions.
        if (k >= n / 2) {
            int maxProfit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i - 1]) {
                    maxProfit += prices[i] - prices[i - 1];
                }
            }
            return maxProfit;
        }
        
        // DP arrays
        
        int[][][] dp = new int[n][k + 1][2];
        
        // Initialize base cases
        for (int i = 0; i <= k; i++) {
            dp[0][i][0] = 0; // No stock on day 0 with j transactions is 0 profit
            dp[0][i][1] = -prices[0]; // Holding stock on day 0 with j transactions is -prices[0]
        }
        
        // Fill the dp array
        for (int i = 1; i < n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i-1][j][1], dp[i-1][j-1][0] - prices[i]);
            }
        }
        
        // The result will be the maximum profit with k transactions and no stock on the last day
        return dp[n-1][k][0];
    }
}

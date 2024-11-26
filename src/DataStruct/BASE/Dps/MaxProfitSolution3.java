package DataStruct.Dps;

/*
 * 买卖股票的最佳时机 III
困难
相关标签
相关企业

给定一个数组，它的第 i 个元素是一支给定的股票在第 i 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 两笔 交易。

注意：你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

 

示例 1:

输入：prices = [3,3,5,0,0,3,1,4]
输出：6
解释：在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
     随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。

示例 2：

输入：prices = [1,2,3,4,5]
输出：4
解释：在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。   
     注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。   
     因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。

示例 3：

输入：prices = [7,6,4,3,1] 
输出：0 
解释：在这个情况下, 没有交易完成, 所以最大利润为 0。

示例 4：

输入：prices = [1]
输出：0

 */

/*
 * 
 */
public class MaxProfitSolution3 {
    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{1,2,3,4,5}));
    }
    public static int maxProfit(int[] prices) {
        int len = prices.length;
        int[] left = new int[len];
        int[] right = new int[len];
        left[0] = 0;
        right[len-1] = 0;
        int leftMin = prices[0], rightMax = prices[len-1];
        for(int i = 1; i < len; i++){
	        left[i] = Math.max(left[i-1], prices[i] - leftMin);
          if(prices[i] < leftMin) leftMin = prices[i];
        } 	
	      for(int i = len-2; i >= 0; i--){
	        right[i] = Math.max(rightMax - prices[i], right[i+1]);
          if(prices[i] > rightMax) rightMax = prices[i];
	      }

	int max = 0;
	for(int i = 0; i < len; i++){
    max = Math.max(max, left[i] + (i + 1 < len ? right[i + 1] : 0));
	  // max = left[i] + right[i+1] > max ? left[i] + right[i+1] : max;

      


    }
    return max;
}

public static int maxProfit2(int[] prices) {
  if (prices == null || prices.length == 0) return 0;
  
  int n = prices.length;
  int[] left = new int[n];
  int[] right = new int[n];
  
  // Calculate left max profit array
  int minPrice = prices[0];
  for (int i = 1; i < n; i++) {
      left[i] = Math.max(left[i - 1], prices[i] - minPrice);
      minPrice = Math.min(minPrice, prices[i]);
  }
  
  // Calculate right max profit array
  int maxPrice = prices[n - 1];
  for (int i = n - 2; i >= 0; i--) {
      right[i] = Math.max(right[i + 1], maxPrice - prices[i]);
      maxPrice = Math.max(maxPrice, prices[i]);
  }
  
  // Calculate the maximum profit with at most two transactions
  int maxProfit = 0;
  for (int i = 0; i < n; i++) {
      maxProfit = Math.max(maxProfit, left[i] + (i + 1 < n ? right[i + 1] : 0));
  }
  
  return maxProfit;
}
}

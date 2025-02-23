package DataStruct.BASE.Dps;


/**
 * 72. 编辑距离
给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数  。
你可以对一个单词进行如下三种操作：
插入一个字符
删除一个字符
替换一个字符
示例 1：
输入：word1 = "horse", word2 = "ros"
输出：3
解释：
horse -> rorse (将 'h' 替换为 'r')
rorse -> rose (删除 'r')
rose -> ros (删除 'e')
示例 2：

输入：word1 = "intention", word2 = "execution"
输出：5
解释：
intention -> inention (删除 't')
inention -> enention (将 'i' 替换为 'e')
enention -> exention (将 'n' 替换为 'x')
exention -> exection (将 'n' 替换为 'c')
exection -> execution (插入 'u')
 */
public class MinDistanceSolution {
    public static void main(String[] args) {
        System.out.println(new MinDistanceSolution().minDistance("intention", "execution"));
    }
    public int minDistance(String word1, String word2) {
    
        int m = word1.length(), n = word2.length();
        int[][] dp = new int[m+1][n+1];
        for(int i = 1; i <= m; i++){
            dp[i][0] = dp[i-1][0] + 1;
        }
        for(int j = 1; j <= n; j++){
            dp[0][j] = dp[0][j-1] + 1;
        }

        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.min(Math.min(dp[i-1][j]+1, dp[i-1][j-1]+1), dp[i][j-1]+1);
                }
            }
        }

        // for(int i = 0; i <= m; i++){
        //     for(int j = 0; j <= n; j++){
        //         System.out.print(dp[i][j] + " ");
        //     }
        //     System.out.println();
        // }

        return dp[m][n];
    }

}

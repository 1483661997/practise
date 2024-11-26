package DataStruct.Dps;

/*
 * 最长回文子串
中等
相关标签
相关企业
提示

给你一个字符串 s，找到 s 中最长的回文
子串
。

如果字符串的反序与原始字符串相同，则该字符串称为回文字符串。

 

示例 1：

输入：s = "babad"
输出："bab"
解释："aba" 同样是符合题意的答案。

示例 2：

输入：s = "cbbd"
输出："bb"

 */
public class LongestPalindromeSolution {
    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad"));
    }
     


    public static String longestPalindrome(String s) {
        int len = s.length();
        int[][] dp  = new int[len][len];
        for(int i = 0; i < len; i++) dp[0][i] = 1;
        for(int i = 1; i < len; i++){
            if(s.charAt(i) == s.charAt(i-1)) dp[1][i] = 1;

        }
        for(int i = 2; i < len; i++){
            for(int j = i; j < len; j++){
                if(dp[i-2][j-1] == 1 && s.charAt(j) == s.charAt(j-i)){
                    dp[i][j] = 1;
                }
            }
        }


      
       
        for(int i = len-1; i >= 0; i--){
            for(int j = len-1; j >= i; j--){
                if(dp[i][j] == 1){
                   return s.substring(j - i, j+1);
                }
        }
        
        }
        return "";
        
    }
}

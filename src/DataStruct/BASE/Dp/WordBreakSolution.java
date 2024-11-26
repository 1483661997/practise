package DataStruct.Dp;
import java.util.*;

/*
 * 单词拆分
中等
相关标签
相关企业

给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。

注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。

 

示例 1：

输入: s = "leetcode", wordDict = ["leet", "code"]
输出: true
解释: 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。

示例 2：

输入: s = "applepenapple", wordDict = ["apple", "pen"]
输出: true
解释: 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
     注意，你可以重复使用字典中的单词。

示例 3：

输入: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
输出: false

 */
public class WordBreakSolution {
    public static void main(String[] args) {
        System.out.println(wordBreak("catsandog", new ArrayList<>(Arrays.asList("cats", "dog", "sand", "and", "cat"))));
    }
    public static boolean wordBreak(String s, List<String> wordDict) {
        int len = s.length();
        int[] dp = new int[len + 1];
        dp[0] = 1;
        
        for(int i = 1; i <= len ; i++){
            //当前字符满足
            if(dp[i-1] == 1 && contains(wordDict, String.valueOf(s.charAt(i-1)))){
                dp[i] = 1;
            }
            for(int j = i-1; j >= 0 && dp[i] != 1; j--){
                //如果符合条件
                if(dp[j] == 1){
                    if(contains(wordDict, s.substring(j, i))){
                        dp[i] = 1;
                    }
                }
                   
            }
        }
        for (int i : dp) {
            System.out.print(i + " ");
        }
        return dp[len] == 1 ? true : false;
    }

    public static boolean contains(List<String> wordDict, String str){
        System.out.println(str);
        for (String string : wordDict) {
            if(string.equals(str)) return true;
        }
        return false;
    }
}

package DataStruct.BASE.Dp;

import java.util.ArrayList;
import java.util.List;


/**
 * 5. 最长回文子串
给你一个字符串 s，找到 s 中最长的 
回文
子串
示例 1：
输入：s = "babad"
输出："bab"
解释："aba" 同样是符合题意的答案。
示例 2：
输入：s = "cbbd"
输出："bb"
 */
public class LongestPalindromeSolution {
    public String longestPalindrome(String s) {
        int len = s.length();
        int[][] dps = new int[len][len];
        String result = new String();

        for(int i = 0; i < len; i++){
            dps[i][i] = 1;
            result = s.substring(i, i+1);
        }
        for(int i = 0; i < len-1; i++){
            if(s.charAt(i) == s.charAt(i+1)){
                dps[i][i+1] = 1;
                result = s.substring(i, i+2);
            }
        }

        
        for(int i = 2; i <len; i++){
            for(int j = 0; j < len - i; j++){
                char left = s.charAt(j), right = s.charAt(i+j);
                if(left == right && dps[j+1][j+i-1] == 1){
                    dps[j][j+i] = 1;
                    result = s.substring(j, i+j+1);
                }
            }
            
        }
        
        return result;
    }  
}

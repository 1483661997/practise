package DataStruct.BitsDP;

import java.util.Arrays;

/*
 * 902. 最大为 N 的数字组合
给定一个按 非递减顺序 排列的数字数组 digits 。你可以用任意次数 digits[i] 来写的数字。
例如，如果 digits = ['1','3','5']，我们可以写数字，如 '13', '551', 和 '1351315'。

返回 可以生成的小于或等于给定整数 n 的正整数的个数 。

示例 1：

输入：digits = ["1","3","5","7"], n = 100
输出：20
解释：
可写出的 20 个数字是：
1, 3, 5, 7, 11, 13, 15, 17, 31, 33, 35, 37, 51, 53, 55, 57, 71, 73, 75, 77.
示例 2：

输入：digits = ["1","4","9"], n = 1000000000
输出：29523
解释：
我们可以写 3 个一位数字，9 个两位数字，27 个三位数字，
81 个四位数字，243 个五位数字，729 个六位数字，
2187 个七位数字，6561 个八位数字和 19683 个九位数字。
总共，可以使用D中的数字写出 29523 个整数。
示例 3:

输入：digits = ["7"], n = 8
输出：1
 

提示：

1 <= digits.length <= 9
digits[i].length == 1
digits[i] 是从 '1' 到 '9' 的数
digits 中的所有值都 不同 
digits 按 非递减顺序 排列
1 <= n <= 109
 */
public class AtMostNGivenDigitSetSolution {
    char[] ch;
    private int dp[];

    public int atMostNGivenDigitSet(String[] digits, int n) {
        int len = ch.length;

        dp = new int[len];
        Arrays.fill(dp, -1); // dp[i] = -1 表示 i 这个状态还没被计算出来
        
        ch = String.valueOf(n).toCharArray();
        
        return fun(digits, 0, true, false);
        // number has x bits.
        // 1位 2位 .... x位
        
    }
    public int fun(String[] digits, int i, boolean isLimit, boolean isNum){
        if(i == ch.length) return isNum ? 1 : 0;

        if (!isLimit && isNum && dp[i] >= 0) return dp[i]; // 在不受到任何约束的情况下，返回记录的结果，避免重复运算

        int res = 0;
        if(!isNum) res += fun(digits, i+1, false, false);
        
        int pos = digits.length;
        for(int j = 0; j < digits.length; j++){
            if(!compare(digits[j], ch[i])){
                pos = j;
                break;
            }
        }
        
        int top = isLimit ? pos  : digits.length;

        for(int j = 0; j < top ; j++){
                res += fun(digits, i+1, isLimit && digits[j].equals(String.valueOf(ch[i])) ,true);
        }

        if (!isLimit && isNum) dp[i] = res; // 在不受到任何约束的情况下，记录结果

        return res;
    }

    public boolean compare(String str, char ch){
        return str.toCharArray()[0] - ch <= 0;
    }

    class Solution {
    private String[] digits;
    private char s[];
    private int dp[];

    public int atMostNGivenDigitSet(String[] digits, int n) {
        this.digits = digits;
        s = Integer.toString(n).toCharArray();
        dp = new int[s.length];
        Arrays.fill(dp, -1); // dp[i] = -1 表示 i 这个状态还没被计算出来
        return f(0, true, false);
    }

    private int f(int i, boolean isLimit, boolean isNum) {
        if (i == s.length) return isNum ? 1 : 0; 
        if (!isLimit && isNum && dp[i] >= 0) return dp[i];
        var res = 0;
        if (!isNum) 
            res = f(i + 1, false, false);
        var up = isLimit ? s[i] : '9';
        for (var d : digits) { // 枚举要填入的数字 d
            if (d.charAt(0) > up) break; // d 超过上限，由于 digits 是有序的，后面的 d 都会超过上限，故退出循环
            // isLimit：如果当前受到 n 的约束，且填的数字等于上限，那么后面仍然会受到 n 的约束
            // isNum 为 true，因为填了数字
            res += f(i + 1, isLimit && d.charAt(0) == up, true);
        }
        if (!isLimit && isNum) dp[i] = res; // 在不受到任何约束的情况下，记录结果
        return res;
    }
}

}

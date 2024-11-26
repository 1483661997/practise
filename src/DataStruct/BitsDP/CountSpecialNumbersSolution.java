package DataStruct.BitsDP;

import java.util.Arrays;

/*
 * 2376. 统计特殊整数
困难
相关标签
相关企业
提示
如果一个正整数每一个数位都是 互不相同 的，我们称它是 特殊整数 。

给你一个 正 整数 n ，请你返回区间 [1, n] 之间特殊整数的数目。

 

示例 1：

输入：n = 20
输出：19
解释：1 到 20 之间所有整数除了 11 以外都是特殊整数。所以总共有 19 个特殊整数。
示例 2：

输入：n = 5
输出：5
解释：1 到 5 所有整数都是特殊整数。
示例 3：

输入：n = 135
输出：110
解释：从 1 到 135 总共有 110 个整数是特殊整数。
不特殊的部分数字为：22 ，114 和 131 。
 

提示：

1 <= n <= 2 * 109
 */
public class CountSpecialNumbersSolution {
    String str;
    int memo[][];
    public int countSpecialNumbers(int n) {
        str = String.valueOf(n);
        memo = new int[str.length()][1 << 10];
        for (int i = 0; i < str.length(); i++) 
        Arrays.fill(memo[i], -1); 
        return f(0, 0, true, false);
    }


    public int f(int i, int mask, boolean isLimit, boolean isNum){
        if(i == str.length())
            return isNum ? 1 : 0;   
        
        if(!isLimit && isNum && memo[i][mask] != -1) return memo[i][mask]; 

        int res = 0;
        if(!isNum){
            res = f(i+1, mask, false, false);
        }
        
        int top = isLimit ? str.charAt(i) - '0' : 9;
        int low = isNum ? 0 : 1;
        for(int j = low; j <= top; j++){
            if((mask >> j & 1) == 0){
                res += f(i+1, mask|(1<<j), isLimit && j == top, true);
            }
        }
        if(!isLimit && isNum) memo[i][mask] = res;

        return res;
        

    }
    class Solution {
    char s[];
    int memo[][];

    public int countSpecialNumbers(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        memo = new int[m][1 << 10];
        for (int i = 0; i < m; i++) 
            Arrays.fill(memo[i], -1); // -1 表示没有计算过
        return f(0, 0, true, false);
    }

    int f(int i, int mask, boolean isLimit, boolean isNum) {
        if (i == s.length)
            return isNum ? 1 : 0; // isNum 为 true 表示得到了一个合法数字
        if (!isLimit && isNum && memo[i][mask] != -1)
            return memo[i][mask];
        int res = 0;
        if (!isNum) // 可以跳过当前数位
            res = f(i + 1, mask, false, false);
        int up = isLimit ? s[i] - '0' : 9; // 如果前面填的数字都和 n 的一样，那么这一位至多填数字 s[i]（否则就超过 n 啦）
        for (int d = isNum ? 0 : 1; d <= up; ++d) // 枚举要填入的数字 d
            if ((mask >> d & 1) == 0) // d 不在 mask 中
                res += f(i + 1, mask | (1 << d), isLimit && d == up, true);
        if (!isLimit && isNum)
            memo[i][mask] = res;
        return res;
    }
}

    
}

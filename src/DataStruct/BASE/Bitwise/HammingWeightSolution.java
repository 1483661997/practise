package DataStruct.BASE.Bitwise;

/*
 * 位1的个数
简单
相关标签
相关企业

编写一个函数，输入是一个无符号整数（以二进制串的形式），返回其二进制表达式中
设置位
的个数（也被称为汉明重量）。

 

示例 1：

输入：n = 11
输出：3
解释：输入的二进制串 1011 中，共有 3 个设置位。

示例 2：

输入：n = 128
输出：1
解释：输入的二进制串 10000000 中，共有 1 个设置位。

示例 3：

输入：n = 2147483645
输出：30
解释：输入的二进制串 11111111111111111111111111111101 中，共有 30 个设置位。

 
 */
public class HammingWeightSolution {
    public int hammingWeight(int n) {
        int res = 0;
        while(n !=0){
            if(n % 2 != 0) res++;
            n /= 2;
        }
        return res;
    }
}

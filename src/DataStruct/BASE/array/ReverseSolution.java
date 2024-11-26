package DataStruct.array;
/*
 * 7. 整数反转
中等
相关标签
相关企业
给你一个 32 位的有符号整数 x ，返回将 x 中的数字部分反转后的结果。

如果反转后整数超过 32 位的有符号整数的范围 [−231,  231 − 1] ，就返回 0。

假设环境不允许存储 64 位整数（有符号或无符号）。
 

示例 1：

输入：x = 123
输出：321
示例 2：

输入：x = -123
输出：-321
示例 3：

输入：x = 120
输出：21
示例 4：

输入：x = 0
输出：0
 

提示：

-231 <= x <= 231 - 1
 */
public class ReverseSolution {
    public static void main(String[] args) {
        System.out.println(new ReverseSolution().reverse(-321));
    }
    public int reverse(int x) {
        long reverse = 0;
        
        while(x != 0){
            reverse = reverse * 10 + x%10;
            x /= 10;
        }
        return reverse > Integer.MAX_VALUE || reverse < Integer.MIN_VALUE ? 0 : (int)reverse;
    }
}

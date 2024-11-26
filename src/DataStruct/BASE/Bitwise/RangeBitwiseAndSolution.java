package DataStruct.BASE.Bitwise;

/*
 * 数字范围按位与
中等
相关标签
相关企业

给你两个整数 left 和 right ，表示区间 [left, right] ，返回此区间内所有数字 按位与 的结果（包含 left 、right 端点）。

 

示例 1：

输入：left = 5, right = 7
输出：4

示例 2：

输入：left = 0, right = 0
输出：0

示例 3：

输入：left = 1, right = 2147483647
输出：0

 
 */
public class RangeBitwiseAndSolution {
    public static void main(String[] args) {
        System.out.println(rangeBitwiseAnd(1, 2147483647));
    }
    public static int rangeBitwiseAnd(int left, int right) {

        // 最长公共前缀
        int zero = 0;
        while(left < right){
            left >>= 1;
            right >>= 1;
            zero++;
        }
        return left << zero;
    }
}


/*
 *
 * 
 * 
 */

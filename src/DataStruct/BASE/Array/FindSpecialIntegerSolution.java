package DataStruct.BASE.Array;

/**
 * 1287. 有序数组中出现次数超过25%的元素
 * 简单
 * 提示
 * 给你一个非递减的 有序 整数数组，已知这个数组中恰好有一个整数，它的出现次数超过数组元素总数的 25%。
 * 请你找到并返回这个整数
 * 示例：
 * 输入：arr = [1,2,2,6,6,6,6,7,10]
 * 输出：6
 */
public class FindSpecialIntegerSolution {
    public int findSpecialInteger(int[] arr) {
        int len = arr.length;
        double num = Math.ceil((len+1)/4.0) -1;
        int pos = 1;
        while (pos < len){
//            if(num <= 0.0) return arr[pos-1];
            if(arr[pos] == arr[pos-1]){
                num--;
            }else{
                num = Math.ceil((len+1)/4.0) -1;
            }
            pos++;
            if(num <= 0.0){
                return arr[pos-1];
            }
        }
        return arr[0];
    }
}

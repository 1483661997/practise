package DataStruct.BASE.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * 1213. 三个有序数组的交集
 * 简单
 * 给出三个均为 严格递增排列 的整数数组 arr1，arr2 和 arr3。返回一个由 仅 在这三个数组中 同时出现 的整数所构成的有序数组。
 * 示例 1：
 * 输入: arr1 = [1,2,3,4,5], arr2 = [1,2,5,7,9], arr3 = [1,3,4,5,8]
 * 输出: [1,5]
 * 解释: 只有 1 和 5 同时在这三个数组中出现.
 * 示例 2:
 * 输入: arr1 = [197,418,523,876,1356], arr2 = [501,880,1593,1710,1870], arr3 = [521,682,1337,1395,1764]
 * 输出: []
 * 提示：
 * 1 <= arr1.length, arr2.length, arr3.length <= 1000
 * 1 <= arr1[i], arr2[i], arr3[i] <= 2000
 */
public class ArraysIntersectionSolution {
    public static void main(String[] args) {

    }
    public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {
        int len1 = arr1.length, len2 = arr2.length, len3 = arr3.length;
        int pos1 = 0, pos2 = 0, pos3 = 0;
        List<Integer> ans = new ArrayList<>();
        while (pos1 < len1 && pos2 < len2 && pos3 < len3){
            if(arr1[pos1] == arr2[pos2] && arr2[pos2] == arr3[pos3]){
                ans.add(arr1[pos1]);
                pos1++;
                continue;
            }
            if(arr1[pos1] >= arr3[pos3] && arr2[pos2] >= arr3[pos3]){
                pos3++;
            }
            else if(arr2[pos2] >= arr1[pos1] && arr3[pos3] >= arr1[pos1]){
                pos1++;
            }
            else if(arr3[pos3] >= arr2[pos2] && arr1[pos1] >= arr2[pos2]){
                pos2++;
            }
        }
        return ans;
    }
}

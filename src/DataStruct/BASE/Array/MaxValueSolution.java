package DataStruct.BASE.Array;
import java.util.*;

import DataStruct.BASE.BinarySearch.searchInsertSolution;
/**
 * 3287. 求出数组中最大序列值
 * 困难
 * 给你一个整数数组 nums 和一个 正 整数 k 。
 * 定义长度为 2 * x 的序列 seq 的 值 为：
 * (seq[0] OR seq[1] OR ... OR seq[x - 1]) XOR (seq[x] OR seq[x + 1] OR ... OR seq[2 * x - 1]).
 * 请你求出 nums 中所有长度为 2 * k 的子序列的 最大值 。
 * 示例 1：
 * 输入：nums = [2,6,7], k = 1
 * 输出：5
 * 解释：
 * 子序列 [2, 7] 的值最大，为 2 XOR 7 = 5 。
 * 示例 2：
 * 输入：nums = [4,2,5,6,7], k = 2
 * 输出：2
 * 解释：
 * 子序列 [4, 5, 6, 7] 的值最大，为 (4 OR 5) XOR (6 OR 7) = 2 。
 * 提示：
 * 2 <= nums.length <= 400
 * 1 <= nums[i] < 27
 */
public class MaxValueSolution {
    public static void main(String[] args) {
        System.out.println(new MaxValueSolution().maxValue(new int[]{4,5,6,7}, 2));
    }
    public int maxValue(int[] nums, int k) {
        List<Set<Integer>> A = findOr(nums, k);
        List<Set<Integer>> B = findOr(reverse(nums), k);
        int max = 0;
        for(int i = k - 1; i < nums.length - k; i++){
            for(int a : A.get(i)){
                for(int b: B.get(nums.length-i-2)){
                    max = Math.max(max, a ^ b);
                }
            }
        }
        return max;
    }
    public List<Set<Integer>> findOr(int[] nums, int k){
        List<Set<Integer>> prev= new ArrayList<>();
        List<Set<Integer>> ans = new ArrayList<>();
        int len = nums.length;
        for(int i = 0; i < len; i++){
            prev.add(new HashSet<>());
        }

        prev.get(0).add(0);
        for(int i = 0; i < len; i++){
            for(int j = Math.min(k-1, i+1); j >= 0; j--){
                for(int x : prev.get(j))
                    prev.get(j+1).add(x | nums[i]);
            }
            ans.add(new HashSet<>(prev.get(k)));
        }
        
        return ans;

    }
    public int[] reverse(int[] nums){
        int left = 0, right = nums.length-1;
        while (left < right){
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;

            left++;
            right--;
        }
        return nums;
    }
}

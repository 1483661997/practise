package DataStruct.BASE.Permutation;
/*
 * 60. 排列序列
给出集合 [1,2,3,...,n]，其所有元素共有 n! 种排列。

按大小顺序列出所有排列情况，并一一标记，当 n = 3 时, 所有排列如下：

"123"
"132"
"213"
"231"
"312"
"321"
给定 n 和 k，返回第 k 个排列。
示例 1：

输入：n = 3, k = 3
输出："213"
示例 2：

输入：n = 4, k = 9
输出："2314"
示例 3：

输入：n = 3, k = 1
输出："123"

提示：

1 <= n <= 9
1 <= k <= n!
 */
public class GetPermutationSolution {
    /*
     * 1 2 3 4 5 6 7
     * 
     * 1 2 3 4 5 7 6
     * 1 2 3 4 6 5 7
     * 1 2 3 4 6 7 5
     * 
     */
    public String getPermutation(int n, int k) {
        int[] arr = new int[n];
        for(int i = 1; i <= n; i++)
            arr[i-1] = i;
        for(int i = 0; i < k; i++)
            nextPermutation(arr);

        StringBuilder str = new StringBuilder();
        for(int i = 0; i < n; i++)
            str.append(arr[i]);
        
        //

        return str.toString();
    }

    public void nextPermutation(int[] nums) {
        int flag = 0;
        int len = nums.length;
        for(int i = len - 1; i >= 1; i--){
            if(nums[i] > nums[i-1]){
                flag = 1;
                for(int j = len -1; j >=i; j--){
                    if(nums[i-1] < nums[j]){
                        int tmp = nums[i-1];
                        nums[i-1] = nums[j];
                        nums[j] = tmp;
                        break;
                    }
                }
                reverse(nums, i, len-1);
                break;
            }
        }
        if(flag == 0) reverse(nums, 0, len-1);
        
    }

    public void reverse(int[] nums, int left, int right){
        while(left < right){
            int tmp = nums[left];
            nums[left] = nums[right];
            nums[right] = tmp;
            left++;
            right--;
        }
    }
}

package DataStruct.Window;

/*
 * 1004. 最大连续1的个数 III
给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1 的最大个数 。

示例 1：

输入：nums = [1,1,1,0,0,0,1,1,1,1,0], K = 2
输出：6
解释：[1,1,1,0,0,1,1,1,1,1,1]
粗体数字从 0 翻转到 1，最长的子数组长度为 6。

示例 2：

输入：nums = [0,0,1,1,0,0,1,1,1,0,1,1,0,0,0,1,1,1,1], K = 3
输出：10
解释：[0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,1,1,1,1]
粗体数字从 0 翻转到 1，最长的子数组长度为 10。

提示：

    1 <= nums.length <= 105
    nums[i] 不是 0 就是 1
    0 <= k <= nums.length


 */
public class LongestOnesSolution {
    public static void main(String[] args) {
        LongestOnesSolution solution = new LongestOnesSolution();
        System.out.println(
        solution.longestOnes(new int[]{1,1,1,0,0,0,1,1,1,1,0}, 2));
    }
    
    public int longestOnes(int[] nums, int k) {
        int len = nums.length;
        int left = 0, right = 0;
        int max = nums[0];
        //起始状态
        while(right < len){
            if(nums[right] == 0 && k > 0){
                right++; k--;
            }else if(nums[right] == 1){
                right++;
            }else break;
        }

        if(right- left > max) max = right - left;


        while(left < len && right < len){
            while(left < len && nums[left] == 1){
                left++;
            }
            left++;
            k++;
            while( right < len){
                if(nums[right] == 0 && k > 0){
                    right++; k--;
                }else if(nums[right] == 1){
                    right++;
                } else break;
            }
            if(right - left > max) max = right - left;
        }        

        return max;
    }
}

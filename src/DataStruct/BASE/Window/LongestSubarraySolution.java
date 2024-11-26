package DataStruct.Window;

/*
 * 1493. 删掉一个元素以后全为 1 的最长子数组
给你一个二进制数组 nums ，你需要从中删掉一个元素。

请你在删掉元素的结果数组中，返回最长的且只包含 1 的非空子数组的长度。

如果不存在这样的子数组，请返回 0 。

 

提示 1：

输入：nums = [1,1,0,1]
输出：3
解释：删掉位置 2 的数后，[1,1,1] 包含 3 个 1 。

示例 2：

输入：nums = [0,1,1,1,0,1,1,0,1]
输出：5
解释：删掉位置 4 的数字后，[0,1,1,1,1,1,0,1] 的最长全 1 子数组为 [1,1,1,1,1] 。

示例 3：

输入：nums = [1,1,1]
输出：2
解释：你必须要删除一个元素。

 

提示：

    1 <= nums.length <= 105
    nums[i] 要么是 0 要么是 1 。


 */
public class LongestSubarraySolution {
    public static void main(String[] args) {
        LongestSubarraySolution solution = new LongestSubarraySolution();
        System.out.println(solution.longestSubarray(new int[]{0,1,1,1,0,1,1,0,1}));
    }
    public int longestSubarray(int[] nums) {
        int len = nums.length;
        int k = 1;
        int left = 0, right = 0;
        int max = 0;
        while(right < len){
            if(nums[right] == 0 && k > 0){
                right++;k--;
            }else if(nums[right] == 1) right++;
            else break;
        }
        max = right - left - 1;
        while(left < len && right < len){
            while(nums[left] == 1) left++;
            left++;
            k++;
            while(right < len){
                if(nums[right] == 0 && k > 0){
                    right++;k--;
                }else if(nums[right] == 1) right++;
                else break;
            }
            max = Math.max(right - left - 1, max);
        }
        return max;
    }
}

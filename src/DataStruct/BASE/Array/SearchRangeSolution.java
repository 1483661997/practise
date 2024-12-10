package DataStruct.BASE.Array;

/**
 * 34. 在排序数组中查找元素的第一个和最后一个位置
 * 给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。
 * 如果数组中不存在目标值 target，返回 [-1, -1]。
 * 你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。
 * 示例 1：
 * 输入：nums = [5,7,7,8,8,10], target = 8
 * 输出：[3,4]
 * 示例 2：
 * 输入：nums = [5,7,7,8,8,10], target = 6
 * 输出：[-1,-1]
 * 示例 3：
 * 输入：nums = [], target = 0
 * 输出：[-1,-1]
 */
public class SearchRangeSolution {

    public static void main(String[] args) {
//        int[] nums = new int[]{5,7,7,8,8,10};
        int[] nums = new int[]{1};
        SearchRangeSolution searchSolution = new SearchRangeSolution();
        for(int i : searchSolution.searchRange(nums, 1)){
            System.out.println(i);
        }
    }
    public int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1, -1};
        int len = nums.length;
        int left = 0 , right = len-1;
        while(left <= right && (nums[left] != target || nums[right] != target)){

            while (left <= right && nums[left] != target ){
                left++;
                System.out.println("\\\\");
            }
            while (left <= right && nums[right] != target){
                System.out.println("////");
                right--;
            }
        }
        if(left <= right){
            res[0] = left;
            res[1] = right;
        }

        return res;
    }
}

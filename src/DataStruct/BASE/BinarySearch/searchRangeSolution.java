package DataStruct.BinarySearch;


/*
 * 
 * 在排序数组中查找元素的第一个和最后一个位置
中等
相关标签
相关企业

给你一个按照非递减顺序排列的整数数组 nums，和一个目标值 target。请你找出给定目标值在数组中的开始位置和结束位置。

如果数组中不存在目标值 target，返回 [-1, -1]。

你必须设计并实现时间复杂度为 O(log n) 的算法解决此问题。

 

示例 1：

输入：nums = [5,7,7,8,8,10], target = 8
输出：[3,4]

示例 2：

输入：nums = [5,7,7,8,8,10], target = 6
输出：[-1,-1]

示例 3：

输入：nums = [], target = 0
输出：[-1,-1]

 */
public class searchRangeSolution {
    public int[] searchRange(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while(left <= right){
            if(nums[left] == target){
                break;
            }
            left++;

        }

        if(left > right) return new int[]{-1, -1};

        while(right >= 0){
            if(nums[right] ==  target){
                break;
            }

            right--;
        }

        return new int[]{left, right};

    }
}

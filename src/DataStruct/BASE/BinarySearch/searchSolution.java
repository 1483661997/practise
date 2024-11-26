package DataStruct.BASE.BinarySearch;

/*
 * . 搜索旋转排序数组
中等
相关标签
相关企业

整数数组 nums 按升序排列，数组中的值 互不相同 。

在传递给函数之前，nums 在预先未知的某个下标 k（0 <= k < nums.length）上进行了 旋转，使数组变为 [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]（下标 从 0 开始 计数）。例如， [0,1,2,4,5,6,7] 在下标 3 处经旋转后可能变为 [4,5,6,7,0,1,2] 。

给你 旋转后 的数组 nums 和一个整数 target ，如果 nums 中存在这个目标值 target ，则返回它的下标，否则返回 -1 。

你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。

 

示例 1：

输入：nums = [4,5,6,7,0,1,2], target = 0
输出：4

示例 2：

输入：nums = [4,5,6,7,0,1,2], target = 3
输出：-1

示例 3：

输入：nums = [1], target = 0
输出：-1

 */
public class searchSolution {
    
    public int search(int[] nums, int target) {
        
        
        int len = nums.length;
        
        int idx = 0;
        for(int i = 1; i < len; i++)
            if(nums[i-1] > nums[i]){
                idx = i;
                break;
            }
          
        if(idx == 0) return binarySearch(nums, target, 0, len - 1);
        else if( (target > nums[idx - 1]  || target < nums[idx]) ) return -1;
        if(target >= nums[0]) return binarySearch(nums, target, 0, idx-1);
        else return binarySearch(nums, target, idx, len -1 );
        
        
    }

    public static int binarySearch(int[] nums, int target, int left, int right){
         
        if(left > right) return -1;
 
        int len =  (left + right)/2;
        
        if(nums[len] == target) return len;
        else if( nums[len] > target ){
            
         
             return binarySearch(nums, target, left, len-1);
        }else{
      

            return binarySearch(nums, target, len+1, right);

        }
        
        
    }


}

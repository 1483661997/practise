package DataStruct.BinarySearch;


/*
 * 搜索插入位置
简单
相关标签
相关企业

给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。

请必须使用时间复杂度为 O(log n) 的算法。
 * 
 */
public class searchInsertSolution {
    public static void main(String[] args) {
        System.out.println(searchInsert(new int[]{1,3,4,5,6}, 7));
    }
    public static int searchInsert(int[] nums, int target) {
        int len = nums.length;
        int l = 0, r = len -1;
        while ((l <= r)) {
            int mid = (l + r) /2;
            if(nums[mid] < target) l = mid+1;
            else r = mid -1;
            
        }
        return l;
        // return binarySearch(nums, target, 0, nums.length - 1);
    }

    

    public static int binarySearch(int[] nums, int target, int left, int right){
          
        // if(nums.length == 0) return -1;
        int len =  (left + right)/2;
        
        if(nums[len] == target) return len;
        else if( nums[len] > target ){
            
            

            if(left == len) return len;
            else return binarySearch(nums, target, left, len-1);
        }else{
            if(len == right) return len+1;

            return binarySearch(nums, target, len+1, right);

        }
        
        
    }
}

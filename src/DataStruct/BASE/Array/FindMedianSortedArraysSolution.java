package DataStruct.BASE.Array;

/**
 * 4. 寻找两个正序数组的中位数
 * 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 * 算法的时间复杂度应该为 O(log (m+n)) 。
 * 示例 1：
 * 输入：nums1 = [1,3], nums2 = [2]
 * 输出：2.00000
 * 解释：合并数组 = [1,2,3] ，中位数 2
 * 示例 2：
 * 输入：nums1 = [1,2], nums2 = [3,4]
 * 输出：2.50000
 * 解释：合并数组 = [1,2,3,4] ，中位数 (2 + 3) / 2 = 2.5
 */
public class FindMedianSortedArraysSolution {
    public static void main(String[] args) {
        System.out.println(new FindMedianSortedArraysSolution().findMedianSortedArrays(new int[]{1,2}, new int[]{3}));;
    }
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int target = (nums1.length+nums2.length)/2+1;
        int l1 = 0, l2 = 0;
        int pos = 0, result1 = 0, result2 = result1;

        while(pos < target && l1 < nums1.length && l2 < nums2.length){
            if(nums1[l1] < nums2[l2]){
                result2 = result1;
                result1 = nums1[l1];
                l1++;
            }
            else{
                result2 = result1;
                result1 = nums2[l2];
                l2++;
            }
            pos++;
        }

        while (pos < target){
            pos++;
            if(l1 < nums1.length){
                result2 = result1;
                result1 = nums1[l1];
                l1++;
            }
            else{
                result2 = result1;
                result1 = nums2[l2];
                l2++;
            }
        }

        if((nums1.length + nums2.length) % 2 == 1) return result1;

        return (result2 + result1)/2.0;
    }
}

package DataStruct.skill;
/*
 * 169. 多数元素
简单
相关标签
相关企业

给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。

你可以假设数组是非空的，并且给定的数组总是存在多数元素。

示例 1：

输入：nums = [3,2,3]
输出：3

示例 2：

输入：nums = [2,2,1,1,1,2,2]
输出：2

 
提示：

    n == nums.length
    1 <= n <= 5 * 104

 */
public class MajorityElementSolution {
    public static void main(String[] args) {
        MajorityElementSolution solution = new MajorityElementSolution();
        System.out.println(solution.majorityElement(new int[]{6,5,5}));
    }
    public int majorityElement(int[] nums) {
        int n = nums[0];
        int num = 1;
        for(int i = 1; i < nums.length; i++){
            if(nums[i] != n) num--;
            else num++;
            if(num == 0){
                n = nums[i];
                num = 1;
            }
        }

        return n;
    }
}

package DataStruct.BASE.Array;

import java.util.*;
/*
 * 18. 四数之和
中等
相关标签
相关企业
给你一个由 n 个整数组成的数组 nums ，和一个目标值 target 。
请你找出并返回满足下述全部条件且不重复的四元组 [nums[a], nums[b], nums[c], nums[d]] （
若两个四元组元素一一对应，则认为两个四元组重复）：

0 <= a, b, c, d < n
a、b、c 和 d 互不相同
nums[a] + nums[b] + nums[c] + nums[d] == target
你可以按 任意顺序 返回答案 。

 

示例 1：

输入：nums = [1,0,-1,0,-2,2], target = 0
输出：[[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
示例 2：

输入：nums = [2,2,2,2,2], target = 8
输出：[[2,2,2,2]]
 

提示：

1 <= nums.length <= 200
-109 <= nums[i] <= 109
-109 <= target <= 109
 */

 /*
  * nums =
[-3,-2,-1,0,0,1,2,3]
target =
0

添加到测试用例
输出
[[-2,-1,0,3],[-2,0,0,2],[-3,-1,1,3],[-3,0,1,2],[-3,0,0,3],[-1,0,0,1],[-3,-2,2,3]]
预期结果
[[-3,-2,2,3],[-3,-1,1,3],[-3,0,0,3],[-3,0,1,2],
[-2,-1,0,3],
[-2,-1,1,2],
[-2,0,0,2],[-1,0,0,1]]
  */
public class FourSumSolution {
    public static void main(String[] args) {
        new FourSumSolution().fourSum(new int[]{1,0,-1,0,-2,20}, 8);
    }
    public List<List<Integer>> fourSum(int[] nums, int target) {
        int len = nums.length;
        Arrays.sort(nums);
        Set<List<Integer>> set = new HashSet<>();
        List<List<Integer>> res = new ArrayList<>(); 

        for(int i = 0; i < len-3; i++){
            for(int j = i+1; j < len-2; j++){
                int left = j + 1;
                int right = len-1;
                long sum = nums[i] + nums[j];

                // List<Integer> tmp = new ArrayList<>();
                while (left < right) {
                    sum += nums[left] + nums[right];
                    if(sum == target){
                        set.add(Arrays.asList(nums[i],nums[j],nums[left],nums[right]));
                        sum -= nums[left] + nums[right];
                        right--;
                        
                    }else if(sum > target){
                        sum -= nums[left] + nums[right];
                        right--;
                    }else{
                        sum -= nums[left] + nums[right];
                        left++;
                    }
                }
            }
        }
        Iterator<List<Integer>> iterator= set.iterator();
        while (iterator.hasNext()) {
            res.add(iterator.next());
        }
        return res;
    }
}

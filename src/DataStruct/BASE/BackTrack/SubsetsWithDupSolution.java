package DataStruct.BackTrack;

import java.util.*;

import DataStruct.Window.longestSubarrayLimit;
/*
 * 90. 子集 II
中等
相关标签
相关企业
给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的 
子集
（幂集）。

解集 不能 包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。

 

示例 1：

输入：nums = [1,2,2]
输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
示例 2：

输入：nums = [0]
输出：[[],[0]]
 

提示：

1 <= nums.length <= 10
-10 <= nums[i] <= 10
 */

 /*
  * 
  [[4,4,1],[1],[],[4,4],[4],[4,4,4],[1,4],[4,1,4],[4,4,4,1,4],[4,4,1,4],[4,4,4,4],[4,4,4,1],[4,1]]
预期结果
  [[],[1],[1,4],[1,4,4],[1,4,4,4],[1,4,4,4,4],[4],[4,4],[4,4,4],[4,4,4,4]]
  */
public class SubsetsWithDupSolution {
    Set<List<Integer>> set = new HashSet<>();
    boolean[] isVisit;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        int len = nums.length;
        isVisit = new boolean[len];
        Arrays.sort(nums);
        traversal(nums, 0, new ArrayList<>());

        return new ArrayList<>(set);
    }

    public void traversal(int[] nums, int left, List<Integer> list){
        int len = nums.length;
        List<Integer> tmp = new ArrayList<>();
        for(int i = 0; i < list.size(); i++)
            tmp.add(list.get(i));
        set.add(tmp);
        // if(left == len-1){
        
        // }

        for(int i = left; i < len; i++){
            list.add(nums[i]);
            isVisit[i] = true;

            traversal(nums, i+1, list);

            list.remove(list.size() -1);
            isVisit[i] = false;
        }
        
        
    }

}

package DataStruct.BackTrack;

import java.util.ArrayList;

/*
 * 78. 子集
给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的
子集
（幂集）。

解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。

示例 1：

输入：nums = [1,2,3]
输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]

示例 2：

输入：nums = [0]
输出：[[],[0]]

 
 */


import java.util.*;
public class SubsetsSolution {
    public static void main(String[] args) {
        for(List<Integer> list : subsets(new int[]{1,2,3})){
            for(int cur : list)
                System.out.print(cur + " ");
            System.out.println();
        }
    }
    public static List<List<Integer>> subsets(int[] nums) {

        List<List<Integer>> list = new ArrayList<>();
        list.add(new ArrayList<>());


        for(int i = 0; i < nums.length; i++)
            add(nums, list, new ArrayList<>(), i);

        
        
        return list;
    }
    public static void add(int[] nums, List<List<Integer>> list, List<Integer> member, int start){
        member.add(nums[start]);
        int len = nums.length;
        start++;

        // list.add(member);
        add1(list, member);
        for(int i = start; i < len; i++){
            add(nums, list,  member, i);
        }
        for(int i = 0; i < member.size(); i++){
            if(member.get(i) == nums[start-1])
                member.remove(i);
        }
    }
    public static void add1(List<List<Integer>> list, List<Integer> member){
        List<Integer> copy = new ArrayList<>();
        for(int cur : member){
            copy.add(cur);
        }

        list.add(copy);
    }
}

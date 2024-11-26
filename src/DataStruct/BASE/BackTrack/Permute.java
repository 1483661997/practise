package DataStruct.BackTrack;

import java.util.ArrayList;
import java.util.List;

/*
 * 给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。

 

示例 1：

输入：nums = [1,2,3]
输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]

示例 2：

输入：nums = [0,1]
输出：[[0,1],[1,0]]

示例 3：

输入：nums = [1]
输出：[[1]]

 */
public class Permute {
    private boolean[] isVisited;
    List<List<Integer>> result;
    public static void main(String[] args) {
        int[] nums = {1,2,3};
        Permute permute = new Permute();
        List<List<Integer>> list = permute.permute(nums);
        for(List<Integer> i : list){
            for(int j : i){
                System.out.print(j);
            }
            System.out.println();
        }
    }
    public List<List<Integer>> permute(int[] nums) {
        int n = nums.length;
        result = new ArrayList<>();
        int len = nums.length;
        isVisited = new boolean[len];

        for(int i = 0; i < len; i++){
            isVisited[i] = true;
            List<Integer> list = new ArrayList<>();
            list.add(nums[i]);
            backTrack(nums, list);
            isVisited[i] = false;
        }
        return result;
    }


    public void backTrack(int[] nums, List<Integer> list){
        if(list.size() == nums.length){
            result.add(new ArrayList<>(list));
            return;
        }
        for(int i = 0; i < nums.length; i++){
            if(!isVisited[i]){
                isVisited[i] = true;
                list.add(nums[i]);
                backTrack(nums, list);
                isVisited[i] = false;
                list.remove(list.size()-1);
            }
        }

    }
    public static List<List<Integer>> permute1(int[] nums) {
        //回溯
        List<List<Integer>> list = new ArrayList<>();
        boolean[] isVisit = new boolean[nums.length];
        addOn(nums, isVisit, list, new ArrayList<>());

        return list;
    }
    public static void addOn(int[] nums, boolean[] isVisit, List<List<Integer>> list, List<Integer> member){
        int flag = 0;
        for(int i = 0; i < nums.length; i++){
            if(isVisit[i] == false){
                // System.out.println(nums[i]);
                member.add(nums[i]);
                isVisit[i] = true;
                flag = 1;
                addOn(nums, isVisit, list, member);
                if(member.size() >= 1){
                    member.remove(member.size()-1);     
                    isVisit[i] = false;
                }  
    
            }
        }
        if(flag == 0){
            List<Integer> tmp = new ArrayList<>();
            tmp.addAll(member);
            list.add(tmp);
            
            return ;
        }
 
    }
    

    
}

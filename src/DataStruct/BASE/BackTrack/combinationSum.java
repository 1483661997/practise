package DataStruct.BackTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import java.util.Collections;;
/*
 * 给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，
 * 找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。

candidates 中的 同一个 数字可以 无限制重复被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。 

对于给定的输入，保证和为 target 的不同组合数少于 150 个。

 

示例 1：

输入：candidates = [2,3,6,7], target = 7
输出：[[2,2,3],[7]]
解释：
2 和 3 可以形成一组候选，2 + 2 + 3 = 7 。注意 2 可以使用多次。
7 也是一个候选， 7 = 7 。
仅有这两种组合。

示例 2：

输入: candidates = [2,3,5], target = 8
输出: [[2,2,2,2],[2,3,3],[3,5]]

示例 3：

输入: candidates = [2], target = 1
输出: []

 */
public class combinationSum {

    //可以重复使用，但是不能乱序
    public static void main(String[] args) {
        int[] nums = {36,21,2,3,23,24,38,22,11,14,15,25,32,19,35,26,31,13,34,29,12,37,17,20,39,30,40,28,27,33};
        List<List<Integer>> list = combinationSum(nums, 35);
        // list = clean(list);

        for(List<Integer> i : list){
            for(int j : i){
                System.out.print(j);
            }
            System.out.println();
        }

        
    }
    
 public  static List<List<Integer>> combinationSum(int[] candidates, int target){
       List<List<Integer>> list = new ArrayList<>();
  
        addOn(candidates, list, new ArrayList<>(), target);

        //list清洗一下

        return clean(list);

        // return (list);
    }
    public static List<List<Integer>> clean(List<List<Integer>> list){
        Set<List<Integer>> set = new HashSet<>();
        for(List<Integer> tmp : list){

            Collections.sort(tmp);
            set.add(tmp);
        }

        return new ArrayList<>(set);

    }
    public static void addOn(int[] nums, List<List<Integer>> list, List<Integer> member, int target){
        int flag = 0;
        int sum = sum(member);
        if(sum > target) return;
        for(int i = 0; i < nums.length; i++){
            if(sum < target){
                // System.out.println(nums[i]);
                member.add(nums[i]);
            
                flag = 1;
                addOn(nums, list, member, target);
                if(member.size() >= 1){
                    member.remove(member.size()-1);     
                   
                }  
    
            }
        }
        if(flag == 0 && sum == target){
            List<Integer> tmp = new ArrayList<>();
            tmp.addAll(member);
            list.add(tmp);
            
            return ;
        }
        
 
    }
    public static int sum(List<Integer> list){
        int sum = 0;
        for(int i : list)
            sum += i;
        return sum;
    }
    
}

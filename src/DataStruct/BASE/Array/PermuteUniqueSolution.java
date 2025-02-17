package DataStruct.BASE.Array;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 47. 全排列 II
 * 已解答
 * 中等
 * 相关标签
 * 相关企业
 * 给定一个可包含重复数字的序列 nums ，按任意顺序 返回所有不重复的全排列。
 * 示例 1：
 * 输入：nums = [1,1,2]
 * 输出：
 * [[1,1,2],
 *  [1,2,1],
 *  [2,1,1]]
 * 示例 2：
 * 输入：nums = [1,2,3]
 * 输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
 */
public class PermuteUniqueSolution {
    private Set<List<Integer>> ans;
    public List<List<Integer>> permuteUnique(int[] nums) {
        ans = new HashSet<>();
        backTrack(nums, new boolean[nums.length], new ArrayList<>(), 0);
        return new ArrayList<>(ans);
    }

    public void backTrack(int[] nums, boolean[] isVisited, List<Integer> list, int len){
        if(len == nums.length){
            ans.add(new ArrayList<>(list));
            return;
        }
        for(int i = 0; i < nums.length; i++){
            if(!isVisited[i]){
                list.add(nums[i]);
                isVisited[i] = true;
                backTrack(nums, isVisited, list, len + 1);
                list.removeLast();
                isVisited[i] = false;
            }
        }
    }
}

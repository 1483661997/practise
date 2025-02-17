package DataStruct.BASE.Array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 90. 子集 II
 * 已解答
 * 中等
 * 给你一个整数数组 nums ，其中可能包含重复元素，请你返回该数组所有可能的
 * 子集（幂集）。
 * 解集 不能包含重复的子集。返回的解集中，子集可以按 任意顺序 排列。
 * 示例 1：
 * 输入：nums = [1,2,2]
 * 输出：[[],[1],[1,2],[1,2,2],[2],[2,2]]
 * 示例 2：
 * 输入：nums = [0]
 * 输出：[[],[0]]
 * 提示：
 * 1 <= nums.length <= 10
 * -10 <= nums[i] <= 10
 */
public class SubsetsWithDupSolution {
    List<List<Integer>> ans;
    List<Integer> tmp;
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        ans = new ArrayList<>();
        tmp = new ArrayList<>();
        Arrays.sort(nums);
        dfs(false, 0, nums);
        return ans;
    }
    public void dfs(boolean choosePre, int cur, int[] nums){
        if(cur == nums.length){
            ans.add(new ArrayList<>(tmp));
            return;
        }

        dfs(false, cur+1, nums);
        if (!choosePre && cur > 0 && nums[cur - 1] == nums[cur]) {
            return;
        }


        tmp.add(nums[cur]);
        dfs(true, cur+1, nums);
        tmp.removeLast();
    }
}

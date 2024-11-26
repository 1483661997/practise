package DataStruct.BASE.BackTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。

你可以按 任何顺序 返回答案。

 

示例 1：

输入：n = 4, k = 2
输出：
[
  [2,4],
  [3,4],
  [2,3],
  [1,2],
  [1,3],
  [1,4],
]

示例 2：

输入：n = 1, k = 1
输出：[[1]]

 */
public class combine {
    public static void main(String[] args) {
        List<List<Integer>> list = combine(4, 3);
        for(List<Integer> i: list){

            for(int j : i) 
                System.out.print(j + " ");
            System.out.println();
        }

    }
    public static List<List<Integer>> combine(int n, int k) {
        Set<List<Integer>> list = new HashSet<>();
        
        for(int i = 1; i <= n - k + 1; i++){
            combineInterval(i, n, list, k, new ArrayList<>());
        }

        return new ArrayList<>(list);
    }

    public static void combineInterval(int l, int r, Set<List<Integer>> list, int k, List<Integer> member){
        member.add(l);
        if(member.size() == k) {
            // for(int i : member)
                // System.out.print("* :" + i);
            // System.out.println();
            List<Integer> tmp = new ArrayList<>();
            tmp.addAll(member);
            list.add(tmp);
            member.remove(member.size()-1);
            return;
        }
        for(int i = l + 1; i <= r ; i++)
            combineInterval(i, r, list, k, member);
        member.remove(member.size() -1);
    }
}

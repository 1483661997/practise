package DataStruct.DFS;

import java.util.*;

/*
 * 113. 路径总和 II
给你二叉树的根节点 root 和一个整数目标和 targetSum ，找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。

叶子节点 是指没有子节点的节点。

 

示例 1：


输入：root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
输出：[[5,4,11,2],[5,8,4,5]]
示例 2：



输入：root = [1,2,3], targetSum = 5
输出：[]
示例 3：

输入：root = [1,2], targetSum = 0
输出：[]

 */
public class PathSumSolution {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
           this.left = left;
           this.right = right;
       }
   }
    public List<List<Integer>> list;
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        
        list = new ArrayList<>();
        path(root, targetSum, new ArrayList<>());
        return list;

    }
    
    public void path(TreeNode node, int targetSum, List<Integer> member){
        
        if(node == null) return;
        member.add(node.val);
        if(targetSum == node.val && node.left == null && node.right == null){
            List<Integer> res = new ArrayList<>();
            for(int i = 0; i < member.size(); i++)
                res.add(member.get(i));
            list.add(res);
             member.remove(member.size()-1);
            
            return ;
        }

        path(node.left, targetSum-node.val, member);

        
        path(node.right, targetSum-node.val, member);
        member.remove(member.size()-1);
    }

    
}

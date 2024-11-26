package DataStruct.Branch;

import java.util.Arrays;


public class ArrayToBST {
    public static void main(String[] args) {
        TreeNode node = sortedArrayToBST(new int[]{1,2,3,4,5});

        System.out.println(node.val);
    }
    public static TreeNode sortedArrayToBST(int[] nums) {
        if(nums.length == 0) return null;
        int len = nums.length / 2;
        TreeNode node = new TreeNode(nums[len]);
        node.left = sortedArrayToBST(Arrays.copyOfRange(nums, 0, len));
        node.right = sortedArrayToBST(Arrays.copyOfRange(nums, len+1, nums.length));
        return node;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val){ this.val = val;}
        TreeNode(int val, TreeNode left, TreeNode right){
            this.val = val;
            this.left = left;
            this.right = right;
        }
        
    }
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
}

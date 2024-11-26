package DataStruct.Tree;

public class IsBalancedSolution {
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

    public boolean isBalanced(TreeNode root) {
        if(root == null ) return true;

        return     Math.abs(depth(root.left) - depth(root.right)) <= 1 
                && isBalanced(root.left) 
                && isBalanced(root.right); 
    }
    public int depth(TreeNode root){
        if(root == null) return 0;
        return 1 + Math.max(depth(root.left), depth(root.right));
    }
}

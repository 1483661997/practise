package DataStruct.BASE.Tree;

/**
 * 510. 二叉搜索树中的中序后继 II
 * 给定一棵二叉搜索树和其中的一个节点 node ，找到该节点在树中的中序后继。如果节点没有中序后继，请返回 null 。
 * 一个节点 node 的中序后继是键值比 node.val 大所有的节点中键值最小的那个。
 * 你可以直接访问结点，但无法直接访问树。每个节点都会有其父节点的引用。节点 Node 定义如下：
 * class Node {
 *     public int val;
 *     public Node left;
 *     public Node right;
 *     public Node parent;
 * }
 */
public class InorderSuccessorSolution {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node parent;
    }
    public Node inorderSuccessor(Node node) {
        //自己的右子树
        if(node.right != null){
            node = node.right;
            while (node.left != null){
                node = node.left;
            }
            return node;
        }

        //父
       while (node.parent != null && node.parent.right == node) node = node.parent;
        return node.parent;
    }
}

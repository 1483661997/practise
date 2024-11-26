package DataStruct.BASE.Branch;
/*
// Definition for a QuadTree node.
class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;

    
    public Node() {
        this.val = false;
        this.isLeaf = false;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }
    
    public Node(boolean val, boolean isLeaf) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = null;
        this.topRight = null;
        this.bottomLeft = null;
        this.bottomRight = null;
    }
    
    public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
        this.val = val;
        this.isLeaf = isLeaf;
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }
}
*/
/*
 * 给你一个 n * n 矩阵 grid ，矩阵由若干 0 和 1 组成。请你用四叉树表示该矩阵 grid 。

你需要返回能表示矩阵 grid 的 四叉树 的根结点。

四叉树数据结构中，每个内部节点只有四个子节点。此外，每个节点都有两个属性：

    val：储存叶子结点所代表的区域的值。1 对应 True，0 对应 False。注意，当 isLeaf 为 False 时，你可以把 True 或者 False 赋值给节点，两种值都会被判题机制 接受 。
    isLeaf: 当这个节点是一个叶子结点时为 True，如果它有 4 个子节点则为 False 。

class Node {
    public boolean val;
    public boolean isLeaf;
    public Node topLeft;
    public Node topRight;
    public Node bottomLeft;
    public Node bottomRight;
}

我们可以按以下步骤为二维区域构建四叉树：

    如果当前网格的值相同（即，全为 0 或者全为 1），将 isLeaf 设为 True ，将 val 设为网格相应的值，并将四个子节点都设为 Null 然后停止。
    如果当前网格的值不同，将 isLeaf 设为 False， 将 val 设为任意值，然后如下图所示，将当前网格划分为四个子网格。
    使用适当的子网格递归每个子节点。

如果你想了解更多关于四叉树的内容，可以参考 wiki 。

四叉树格式：

你不需要阅读本节来解决这个问题。只有当你想了解输出格式时才会这样做。输出为使用层序遍历后四叉树的序列化形式，其中 null 表示路径终止符，其下面不存在节点。

它与二叉树的序列化非常相似。唯一的区别是节点以列表形式表示 [isLeaf, val] 。

如果 isLeaf 或者 val 的值为 True ，则表示它在列表 [isLeaf, val] 中的值为 1 ；如果 isLeaf 或者 val 的值为 False ，则表示值为 0 。

 

示例 1：

输入：grid = [[0,1],[1,0]]
输出：[[0,1],[1,0],[1,1],[1,1],[1,0]]
解释：此示例的解释如下：
请注意，在下面四叉树的图示中，0 表示 false，1 表示 True 。

 */
public class FourTree {
    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;
    
        
        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }
        
        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }
        
        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }
    public Node construct(int[][] grid) {
        // int len  = grid.length;
        // AlienWare
        Node node = new Node();
        constructTree(grid, node);
        return node;
        
        
    }
    public void constructTree(int[][] grid, Node node){
        if(grid.length == 1){
            node.val = grid[0][0] == 1 ? true : false;
            node.isLeaf = true;
            return ;
        }
        int len = grid.length /2;
        int topLeft = -1, topRight = -1, bottomLeft = -1, bottomRight = -1;
        int num = 0;
        
        // 0-len  0-len
        // 0-len  len+1 - length()
        //len+1 - length()  0-len
        //len+1 -length()  len+1 -length()


        //左上
        int[][] topLeftArr = new int[len][len];
        int[][] topRightArr = new int[len][len];
        int[][] bottomLeftArr = new int[len][len];
        int[][] bottomRightArr = new int[len][len];

        for(int i = 0; i < len;i++){
            for(int j = 0; j <len; j++){
                topLeftArr[i][j] = grid[i][j];
                if( grid[i][j] == 1) num++;
            }
        }     

        if(num == 0 || num == (len) * (len)){
            topLeft = num > 0 ? 1 : 0;
        }

        //右上
        num = 0;
        for(int i = 0; i < len;i++){
            for(int j = len; j < grid[0].length; j++){
                topRightArr[i][j-len] = grid[i][j]; 
                if( grid[i][j] == 1) num++;
            }
        }   
          
        if(num == 0 || num == (len) * (len)){
            topRight = num > 0 ? 1 : 0;
        }

        //左下
        num = 0;
        for(int i = len; i < grid.length;i++){
            for(int j = 0; j < len; j++){
                bottomLeftArr[i-len][j] = grid[i][j];
                if( grid[i][j] == 1) num++;
            }
        }     

        if(num == 0 || num == (len) * (len)){
            bottomLeft = num > 0 ? 1 : 0;
        }


        //右下
        num = 0;
        for(int i = len; i < grid.length;i++){
            for(int j = len; j <grid[0].length; j++){
                bottomRightArr[i - len][j - len] = grid[i][j];
                if( grid[i][j] == 1) num++;
            }
        }     
        if(num == 0 || num == (len) * (len)){
            bottomRight = num > 0 ? 1 : 0;
        }
        
        if(topLeft == 0  && topRight == 0 && bottomLeft == 0 && bottomRight == 0){
            node.val  = false;
            node.isLeaf = true;
        }else if( topLeft == 1  && topRight == 1 && bottomLeft == 1 && bottomRight == 1){
            node.val = true;
            node.isLeaf = true;
        }else{
            
            if(topLeft != -1) node.topLeft = new Node(topLeft == 1 ? true : false, true);
            else {
                node.topLeft = new Node();
                constructTree(topLeftArr, node.topLeft);
            }
    
            if(topRight != -1) node.topRight = new Node(topRight == 1 ? true : false, true);
            else {
                node.topRight = new Node();
                constructTree(topRightArr, node.topRight);
            }
            if(bottomLeft != -1) node.bottomLeft = new Node(bottomLeft == 1 ? true : false, true);
            else{
                node.bottomLeft = new Node();
                constructTree(bottomLeftArr, node.bottomLeft);
            }
    
            if(bottomRight != -1) node.bottomRight = new Node(bottomRight == 1 ? true : false, true);
            else{
                node.bottomRight = new Node();
                constructTree(bottomRightArr, node.bottomRight);
            }
    
        }

       
        

    }
}

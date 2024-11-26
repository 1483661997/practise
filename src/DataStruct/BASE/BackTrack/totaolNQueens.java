package DataStruct.BASE.BackTrack;
/*
 * 
 * n 皇后问题 研究的是如何将 n 个皇后放置在 n × n 的棋盘上，并且使皇后彼此之间不能相互攻击。

给你一个整数 n ，返回 n 皇后问题 不同的解决方案的数量。

 

示例 1：

输入：n = 4
输出：2
解释：如上图所示，4 皇后问题存在两个不同的解法。

示例 2：

输入：n = 1
输出：1

 

 */
public  class totaolNQueens {
    private static int count = 0;
    
    public static void main(String[] args) {
        // print("Holis", "123");
        // int[][] arr = new int[2][2];
        // ;
        System.out.println(totalNQueens(8));
    }
    
    public static int totalNQueens(int n) {
      
        count = 0;
        for(int i = 0; i < 1; i++){
            for(int j = 0; j < n; j++){
                int[][] arr = new int[n][n];
                int[][] isOccupy = new int[2][n];
                int[][] corner = new int[2][2 * n];
                if(arr[i][j] == 0){
                
                // System.out.println("//////////////////////////" + i + " " + j);
                
                safePosition(arr, n, i, j, isOccupy, corner);
                
            }
                    
            }
        }
        return count;
    }
    
    public static void safePosition(int[][] board, int num, int row , int col, int[][] isOccupy, int[][] corner){
        
        // System.out.println("*********" + row +" " +  col +" " +num) ;

        int c1 = row + col, c2 = board.length  - col + row;
        // System.out.println(c1 + " " +c2);
        if(corner[1][c2] == 1 || corner[0][c1] == 1) return;
        // System.out.println("对角");


        isOccupy[0][row] = 1;
        isOccupy[1][col] = 1;
        board[row][col] = 1;
        corner[1][c2] = 1;
        corner[0][c1] = 1;


        if(num == 1)  {
            // System.out.println("success");
            count++;
        }
       
        
        for(int i = row; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(isOccupy[0][i] == 0 && isOccupy[1][j] == 0 && board[i][j] == 0){
                   safePosition(board, num-1, i, j,isOccupy, corner);
                }
            }
        }
        board[row][col] = 0;
        isOccupy[0][row] = 0;
        isOccupy[1][col] = 0;
        corner[1][c2] = 0;
        corner[0][c1] = 0;

    }









    

    public static void print(String... strs){
        for(int i = 0; i < strs.length; i++){
            System.out.println(strs[i]);
        }
    }

    public enum weather{
        SPRING,WINTER;
    }
}

package DataStruct.BASE.BackTrack;
import java.util.ArrayList;

/*
 *  N 皇后
困难
相关标签
相关企业

按照国际象棋的规则，皇后可以攻击与之处在同一行或同一列或同一斜线上的棋子。

n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。

给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。

每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。

 

示例 1：

输入：n = 4
输出：[[".Q..","...Q","Q...","..Q."],["..Q.","Q...","...Q",".Q.."]]
解释：如上图所示，4 皇后问题存在两个不同的解法。

示例 2：

输入：n = 1
输出：[["Q"]]

 */
import java.util.*;
public class SolveNQueenSolution {
    public static void main(String[] args) {
        SolveNQueenSolution solution = new SolveNQueenSolution();
        for (List<String> list : solution.solveNQueens(4)) {
            for(String str :list)
                System.out.println(str);
            System.out.println("*********************");
        }
    }
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> list = new ArrayList<>();
        int[][] matrix = new int[n][n];
        //控制第一个旗子的位置
        for(int i = 0; i < n; i++){
                findPlaceToStay(list, matrix, 0, i, n, n);
        }
    
        return list;
    }

    public void findPlaceToStay(List<List<String>> list,int[][] matrix, int row, int col, int n, int num){
        System.out.println("坐标" + row + " " + col);
        if(num == 1){
            matrix[row][col] = 2;
            add(list, matrix);
            // System.out.println("///////////////////////////////");
        }
        int[][] store = new int[n][n];
        for(int i = 0; i < n; i++){

            for(int j = 0; j < n; j++){
                System.out.print(matrix[i][j] + " ");
                store[i][j] = matrix[i][j];
            }
            System.out.println();
            
        }
        
        matrix[row][col] = 2;
        int[] rowC = new int[]{-1,1, 0, 0, -1, -1, 1, 1};
        int[] colC = new int[]{ 0,0,-1, 1 , -1,  1,-1, 1};
        System.out.println("辐射");
        for(int i = 0; i < 8; i++){
            // System.out.println(i);
            int newRow = row + rowC[i];
            int newCol = col + colC[i];
            while(newRow >= 0 && newRow < n && newCol >= 0 && newCol < n){
                // System.out.print("*");
                matrix[newRow][newCol] = 1;
                newRow += rowC[i];
                newCol += colC[i];
            }     
        }
        for(int i = 0; i < n; i++){

            for(int j = 0; j < n; j++){
                System.out.print(matrix[i][j] + " ");
               
            }
            System.out.println();
            
        }
        // System.out.println("找"+row+1);
        for(int i = 0; i < n; i++){
            if(row < n - 1 && matrix[row+1][i] == 0){
                
                System.out.println("有可能");
                findPlaceToStay(list, matrix, row+1, i, n, num-1);
                        
            }
        }
        //还原
        System.out.println("还原");
        
        for(int i = 0; i < n; i++){

            for(int j = 0; j < n; j++){

                matrix[i][j] = store[i][j];
                System.out.print(store[i][j] +" ");

            }
            System.out.println();
        }

        // for(int i = rwo+1; i < n; i++){
        //     for(int j = 0; j < n; j++){
        //         if(matrix[i][j] == 0){
        //             findPlaceToStay(list, matrix, i, j, n, num-1);

        //         }
        //     }

        // }
    }

    public void add(List<List<String>> list, int[][] matrix){
        List<String>  res = new ArrayList<>();
        int len = matrix.length;
        for(int i = 0; i < len; i++){
            StringBuilder str = new StringBuilder();
            for(int j = 0; j < len; j++){
                if(matrix[i][j] == 2) str.append('Q');
                else str.append('.'); 
            }
            res.add(str.toString());
            
        }
        list.add(res);
    }
}

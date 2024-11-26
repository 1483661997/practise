package DataStruct.BASE.matrix;

import java.util.*;

/*
 * 搜索二维矩阵 II
 * 
编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：

    每行的元素从左到右升序排列。
    每列的元素从上到下升序排列。


示例 1：

输入：matrix = [ [1,4,7,11,15],
                [2,5,8,12,19],
                [3,6,9,16,22],
                [10,13,14,17,24],
                [18,21,23,26,30]], target = 5
输出：true

示例 2：

输入：matrix = [[1,4,7,11,15],[2,5,8,12,19],[3,6,9,16,22],[10,13,14,17,24],[18,21,23,26,30]], target = 20
输出：false

 

提示：

    m == matrix.length
    n == matrix[i].length
    1 <= n, m <= 300
    -109 <= matrix[i][j] <= 109
    每行的所有元素从左到右升序排列
    每列的所有元素从上到下升序排列
    -109 <= target <= 109


 */
public class SearchMatrixSolution {
    public static void main(String[] args) {
        System.out.println(searchMatrix(new int[][]{{1,4,7,11,15}, {2,5,8,12,19}, {3,6,9,16,22}}, 5));
    }
    public static boolean searchMatrix(int[][] matrix, int target) {
        if(matrix.length == 0) return false;
        int m  = matrix.length, n = matrix[0].length;   
        if(matrix[0][0] > target || matrix[m-1][n-1] < target) return false;
        boolean[][] isVisited = new boolean[m][n];
        int[] row = new int[]{-1, 1,  0, 0};
        int[] col = new int[]{ 0, 0, -1, 1};
        
        Queue<int[]> queue = new LinkedList<>();

        queue.add(new int[]{0,0});
        
        while(!queue.isEmpty()){
            int[] tmp = queue.poll();
            if(matrix[tmp[0]][tmp[1]] == target) return true;
            for(int i = 0; i < 4; i++){
                int newRow = tmp[0] +row[i];
                int newCol = tmp[1] +col[i];
                if(newRow >= 0 && newRow < m && newCol >= 0 && newCol < n && !isVisited[newRow][newCol] && matrix[newRow][newCol] <= target){
                   
                    queue.add(new int[]{newRow, newCol});
                    isVisited[newRow][newCol] = true;
                    
                }
            }
        }
        return false;

     }
}

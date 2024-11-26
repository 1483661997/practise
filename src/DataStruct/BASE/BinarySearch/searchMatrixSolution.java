package DataStruct.BinarySearch;

/*
 * 
 * 搜索二维矩阵
中等
相关标签
相关企业

给你一个满足下述两条属性的 m x n 整数矩阵：

    每行中的整数从左到右按非严格递增顺序排列。
    每行的第一个整数大于前一行的最后一个整数。

给你一个整数 target ，如果 target 在矩阵中，返回 true ；否则，返回 false 。

 

示例 1：

输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
输出：true

示例 2：

输入：matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 13
输出：false

 */
public class searchMatrixSolution {
    public boolean searchMatrix(int[][] matrix, int target) {
        
        int row = matrix.length;
        int col = matrix[0].length;
        if(target < matrix[0][0] && target > matrix[row - 1][col -1]) return false;
        int posX = 0;
        for(int i = 1; i < row; i++){
            if(matrix[i][0] <= target) posX = i;
        
        }

        for(int i = 0; i < col; i++){
            if(matrix[posX][i] == target) return true;
        }
        return false;

    }
}

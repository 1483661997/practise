package DataStruct.BASE.matrix;

/*
 * 59. 螺旋矩阵 II
中等
相关标签
相关企业
给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
示例 1：
输入：n = 3
输出：[[1,2,3],[8,9,4],[7,6,5]]
示例 2：
输入：n = 1
输出：[[1]]
提示：1 <= n <= 20
 */
public class GenerateMatrixSolution {
    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int top = 0, botoom = n-1, left = 0, right = n-1;
        int idx = 1;
        while(top < botoom && left < right){
            for(int i = left; i <= right; i++)
                matrix[top][i] = idx++;
            top++;

            for(int i = top; i <= botoom; i++)
                matrix[i][right] = idx++;
            right--;
            
            for(int i = right; i>= left; i--)
                matrix[botoom][i] = idx++;
            botoom--;

            for(int i = botoom; i >= top; i--)
                matrix[i][left] = idx++;
            left++;
        }

        return matrix;
    }   
}

package DataStruct.BASE.Matrix;

/*
 * 85. 最大矩形
给定一个仅包含 0 和 1 、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。

示例 1：


输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
输出：6
解释：最大矩形如上图所示。
示例 2：

输入：matrix = [["0"]]
输出：0
示例 3：

输入：matrix = [["1"]]
输出：1
 

提示：

rows == matrix.length
cols == matrix[0].length
1 <= row, cols <= 200
matrix[i][j] 为 '0' 或 '1'
 */
public class MaximalRectangleSolution {
    public int maximalRectangle(char[][] matrix) {
        int m = matrix.length, n = matrix[0].length; 
        int[][][] dps = new int[m+1][n+1][3];
        dps[0][0][0] = matrix[0][0] - '0';
        // for(int i = 1; i < m; i++){
        //     if(matrix[i][0] == '1'){
        //         if(dps[i-1][0][0] != 0){
        //             dps[i][0][0] = dps[i-1][0][0]+1;
        //             dps[i][0][1] = dps[i-1][0][1];
        //             dps[i][0][2] = dps[i-1][0][2];
        //         }else{
        //             dps[i][0][0] = 1;
        //             dps[i][0][1] = i;
        //             dps[i][0][2] = 0;

        //         }
                
        //     }
        // }
        for(int i = 1; i < n; i++){
            if(matrix[0][i] == '1'){
                if(dps[0][i-1][0] != 0){
                    dps[0][i][0] = dps[0][i-1][0] + 1;
                    dps[0][i][1] = dps[0][i-1][1];
                    dps[0][i][2] = dps[0][i-1][2];
                    
                }else{
                    dps[0][i][0] = 1;
                    dps[0][i][1] = 0;
                    dps[0][i][2] = i;
                }
            }
        }
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                if(matrix[i][j] == '1'){
                    // int leftTop = dps[i-1][j-1][0];
                    dps[i][j][0] = 1;
                    dps[i][j][1] = i;
                    dps[i][j][2] = j;
                    
                    if(dps[i-1][j][0] != 0){
                        int topX = dps[i-1][j][1];
                        int topY = dps[i-1][j][2];
                        int flag = 0;
                        for(int k = topY; k <= j; k++){
                            if(matrix[i][k] != '1') flag = 1;
                        }
                        int square = (i-topX+1) * (j-topY+1);
                        if(flag == 0 && square > dps[i][j][0]){
                            dps[i][j][0] = square;
                            dps[i][j][1] = topX;
                            dps[i][j][1] = topY;
                        }
                        
                    }

                    if(dps[i][j-1][0] != 0){
                        int leftX = dps[i][j-1][1];
                        int leftY = dps[i][j-1][2];
                        int flag = 0;
                        for(int k = leftX; k <= i; k++){
                            if(matrix[k][j] != '1') flag = 1;
                        }
                        int square = (i-leftX + 1)*(j-leftY+1);
                        if(flag == 0 && square > dps[i][j][0]){
                            dps[i][j][0] = square;
                            dps[i][j][1] = leftX;
                            dps[i][j][2] = leftY;
                       
                        }
                    }
                    


                    // int top = dps[i-1][j][0];
                    
                    
                }
            }
        }
        int max = 0;
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++) 
                if(dps[i][j][0] > max) max = dps[i][j][0];
                
        return max;
    }
}

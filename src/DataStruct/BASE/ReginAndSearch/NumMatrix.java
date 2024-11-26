package DataStruct.BASE.ReginAndSearch;

public class NumMatrix {
    //matrix[i][j]代表他的左上角的求和
    int[][] matrix;

    public NumMatrix(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        
        this.matrix = new int[row+1][col+1];

        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                this.matrix[i+1][j+1] = matrix[i][j] - this.matrix[i][j] + this.matrix[i][j+1] + this.matrix[i+1][j];
                System.out.println(this.matrix[i+1][j+1]);
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return matrix[row2+1][col2 + 1] + matrix[row1][col1 ] - matrix[row1][col2 + 1] - matrix[row2+1][col1 ] ;
        // int sum = 0;
        // for(int i = row1; i <= row2; i++){
        //     for(int j = col1; j <= col2; j++){
        //         sum += matrix[i][j];
        //     }
        // }
        // return sum;
    }

    public static void main(String[] args) {
        NumMatrix numMatrix = new NumMatrix(new int[][]{});

    }
}

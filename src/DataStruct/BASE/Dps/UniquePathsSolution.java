package DataStruct.Dps;


/*
 * 62. 不同路径
中等
相关标签
相关企业

一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。

机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。

问总共有多少条不同的路径？

 

示例 1：

输入：m = 3, n = 7
输出：28

示例 2：

输入：m = 3, n = 2
输出：3
解释：
从左上角开始，总共有 3 条路径可以到达右下角。
1. 向右 -> 向下 -> 向下
2. 向下 -> 向下 -> 向右
3. 向下 -> 向右 -> 向下

示例 3：

输入：m = 7, n = 3
输出：28

示例 4：

输入：m = 3, n = 3
输出：6

 

提示：

    1 <= m, n <= 100
    题目数据保证答案小于等于 2 * 109


 */
public class UniquePathsSolution {
    public static void main(String[] args) {
        UniquePathsSolution solution = new UniquePathsSolution();
        System.out.println(solution.uniquePaths(7, 3));
    }
    public int uniquePaths(int m, int n) {
        int[][] dps = new int[m][n];
        dps[0][0] = 1;
        
        int[] row = new int[]{-1, 0};
        int[] col = new int[]{0, -1,};

        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k < 2; k++){
                    int newRow = i + row[k];
                    int newCol = j + col[k];
                    if(newRow >= 0 && newRow < m && newCol >= 0 && newCol < n){
                        dps[i][j] += dps[newRow][newCol];   
                    }

                }
            }
        }
        return dps[m-1][n-1];
    }
}

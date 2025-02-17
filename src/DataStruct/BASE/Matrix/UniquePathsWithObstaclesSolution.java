package DataStruct.BASE.Matrix;

/**
 * 63. 不同路径 II
 * 已解答
 * 中等
 * 相关标签
 * 相关企业
 * 提示
 * 给定一个 m x n 的整数数组 grid。一个机器人初始位于 左上角（即 grid[0][0]）。
 * 机器人尝试移动到 右下角（即 grid[m - 1][n - 1]）。机器人每次只能向下或者向右移动一步。
 * 网格中的障碍物和空位置分别用 1 和 0 来表示。机器人的移动路径中不能包含 任何 有障碍物的方格。
 * 返回机器人能够到达右下角的不同路径数量。
 * 测试用例保证答案小于等于 2 * 109。
 */
public class UniquePathsWithObstaclesSolution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid[0][0] == 1) return 0;
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] dps = new int[m][n];
        dps[0][0] = 1;
        for(int i = 1; i < m; i++){
            if(obstacleGrid[i][0] == 1) dps[i][0] = 0;
            else dps[i][0] = dps[i-1][0];
        }
        for(int i = 1; i < n; i++){
            if(obstacleGrid[0][i] == 1) dps[0][i] = 0;
            else dps[0][i] = dps[0][i-1];
        }
        for(int i = 1; i  < m; i++){
            for(int j = 1; j < n; j++){
                if(obstacleGrid[i][j] == 1) continue;
                dps[i][j] = dps[i-1][j] + dps[i][j-1];
            }
        }

        return dps[m-1][n-1];
    }
}

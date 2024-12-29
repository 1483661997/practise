package DataStruct.Leetcode.Competition430;

/**
 * Q1. 使每一列严格递增的最少操作次数
 * 给你一个由 非负 整数组成的 m x n 矩阵 grid。
 * 在一次操作中，你可以将任意元素 grid[i][j] 的值增加 1。
 * 返回使 grid 的所有列 严格递增 所需的 最少 操作次数。
 * 示例 1：
 * 输入: grid = [[3,2],[1,3],[3,4],[0,1]]
 * 输出: 15
 * 解释:
 * 为了让第 0 列严格递增，可以对 grid[1][0] 执行 3 次操作，对 grid[2][0] 执行 2 次操作，对 grid[3][0] 执行 6 次操作。
 * 为了让第 1 列严格递增，可以对 grid[3][1] 执行 4 次操作。
 * 示例 2：
 * 输入: grid = [[3,2,1],[2,1,0],[1,2,3]]
 * 输出: 12
 * 解释:
 * 为了让第 0 列严格递增，可以对 grid[1][0] 执行 2 次操作，对 grid[2][0] 执行 4 次操作。
 * 为了让第 1 列严格递增，可以对 grid[1][1] 执行 2 次操作，对 grid[2][1] 执行 2 次操作。
 * 为了让第 2 列严格递增，可以对 grid[1][2] 执行 2 次操作。
 * 提示:
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * 0 <= grid[i][j] < 2500
 */
public class MinimumOperationsSolution {
    public static void main(String[] args) {
        System.out.println(new MinimumOperationsSolution().minimumOperations(new int[][]{{0,0},{0,0}}));
    }
    public int minimumOperations(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int res = 0;
        for(int i = 1; i < m ; i++){
            for(int j = 0; j  < n; j++){
                int tmp = grid[i-1][j];
                int cur = grid[i][j];
                if(tmp >= cur){
                    res += tmp - cur + 1;
                    grid[i][j] = tmp+1;
                }
            }
        }
        return res;
    }
}

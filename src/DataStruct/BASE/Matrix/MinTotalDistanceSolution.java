package DataStruct.BASE.Matrix;

import java.util.*;

/**
 * 296. 最佳的碰头地点
困难
给你一个 m x n  的二进制网格 grid ，其中 1 表示某个朋友的家所处的位置。返回 最小的 总行走距离 。
总行走距离 是朋友们家到碰头地点的距离之和。
我们将使用 曼哈顿距离 来计算，其中 distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y| 。
示例 1：
输入: grid = [[1,0,0,0,1],[0,0,0,0,0],[0,0,1,0,0]]
输出: 6 
解释: 给定的三个人分别住在(0,0)，(0,4) 和 (2,2):
     (0,2) 是一个最佳的碰面点，其总行走距离为 2 + 2 + 2 = 6，最小，因此返回 6。
示例 2:
输入: grid = [[1,1]]
输出: 1
提示:
m == grid.length
n == grid[i].length
1 <= m, n <= 200
grid[i][j] == 0 or 1.
grid 中 至少 有两个朋友
 */
public class MinTotalDistanceSolution {
    public static void main(String[] args) {
        System.out.println( new MinTotalDistanceSolution().minTotalDistance(
            new int[][]{{1,0,0,0,1},{0,0,0,0,0},{0,0,1,0,0}}
        ));
    }
    public int minTotalDistance(int[][] grid) {
        List<Integer> rows = new ArrayList<>();
        List<Integer> cols = new ArrayList<>();
        int m = grid.length, n = grid[0].length;
        for(int i = 0; i < m; i++){
            for(int j = 0; j  < n; j++){
                if(grid[i][j] == 1){
                    rows.add(i);
                }
            }
        }

        for(int j = 0; j < n; j++){
            for(int i = 0; i < m; i++){
                if(grid[i][j] == 1){
                    cols.add(j);
                }
            }
        }


        int row = rows.get(rows.size()/2);
        int col = cols.get(cols.size()/2);
        System.out.println(row + " " + col);

        return distanceX(col, cols) + distanceX(row, rows);
    }   
    
    public int distanceX(int x, List<Integer> list){
        int ans = 0;
        for(int i : list){
            ans += Math.abs(x-i);
        }
        return ans;
    }

}

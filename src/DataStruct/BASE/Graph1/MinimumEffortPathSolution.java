package DataStruct.BASE.Graph1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * 1631. 最小体力消耗路径
你准备参加一场远足活动。给你一个二维 rows x columns 的地图 heights ，其中 heights[row][col] 表示格子 (row, col) 的高度。
一开始你在最左上角的格子 (0, 0) ，且你希望去最右下角的格子 (rows-1, columns-1) （
注意下标从 0 开始编号）。你每次可以往 上，下，左，右 四个方向之一移动，你想要找到耗费 体力 最小的一条路径。
一条路径耗费的 体力值 是路径上相邻格子之间 高度差绝对值 的 最大值 决定的。
请你返回从左上角走到右下角的最小 体力消耗值 。

示例 1：
输入：heights = [[1,2,2],[3,8,2],[5,3,5]]
输出：2
解释：路径 [1,3,5,3,5] 连续格子的差值绝对值最大为 2 。
这条路径比路径 [1,2,2,2,5] 更优，因为另一条路径差值最大值为 3 。
示例 2：

输入：heights = [[1,2,3],[3,8,4],[5,3,5]]
输出：1
解释：路径 [1,2,3,4,5] 的相邻格子差值绝对值最大为 1 ，比路径 [1,3,5,3,5] 更优。
示例 3：


输入：heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
输出：0
解释：上图所示路径不需要消耗任何体力。

提示：

rows == heights.length
columns == heights[i].length
1 <= rows, columns <= 100
1 <= heights[i][j] <= 106
 */
public class MinimumEffortPathSolution {
    
    final int N = 100009;
    int[] parent = new int[N];
    int row ,col;
    
    void union(int a,int b){
        parent[find(a)] = find(b);
    }
    
    int find(int a){
        if(parent[a] != a) parent[a] = find(parent[a]);
        return parent[a]; 

    }
    
    boolean query(int a, int b){
        return find(a) == find(b);
    }

    public int minimumEffortPath(int[][] heights) {
        row = heights.length;
        col = heights[0].length;

        for(int i = 0; i < row * col; i++) parent[i] = i;
        
        List<int[]> edges = new ArrayList<>();
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++){
                int idx = getIndex(i, j);
                if(i + 1 < row){
                    int a = idx, b = getIndex(i+1, j);
                    int w = Math.abs(heights[i][j] - heights[i+1][j]);
                    edges.add(new int[]{a,b,w});
                }
                if(j + 1 < col){
                    int a = idx, b = getIndex(i, j+1);
                    int w = Math.abs(heights[i][j] - heights[i][j+1]);
                    edges.add(new int[]{a,b,w});
                }
            }
        }
        

        Collections.sort(edges, (a,b) -> a[2] - b[2]);

        int strat = 0, end = getIndex(row-1, col-1);
        for(int[] edge : edges){
            int a = edge[0], b= edge[1], w = edge[2];
            union(a, b);
            if(query(strat,end)) return w;
        }
        return 0;


        
        
    }

    public int getIndex(int a, int b){
        return a * col + b;
    }

  

}

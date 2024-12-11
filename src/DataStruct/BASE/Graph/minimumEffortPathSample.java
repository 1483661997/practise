package DataStruct.BASE.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class minimumEffortPathSample {
   // 为了处理并查集，我们需要一个足够大的数组
     int N = 10009;
     int[] p = new int[N]; // 并查集数组
     int row, col; // 网格的行数和列数
 
     // 并查集的 union 操作，用于合并两个集合
     void union(int a, int b) {
         p[find(a)] = find(b);
     }
 
     // 并查集的 query 操作，用于查询两个元素是否在同一个集合中
     boolean query(int a, int b) {
         return find(a) == find(b);
     }
 
     // 并查集的 find 操作，带路径压缩，用于找到元素的根节点
     int find(int x) {
         if (p[x] != x) p[x] = find(p[x]);
         return p[x];
     }
 
    public int minimumEffortPath1(int[][] heights) {
        row = heights.length;
        col = heights[0].length;

        // 初始化并查集
        for (int i = 0; i < row * col; i++) p[i] = i;

        // 预处理出所有的边
        // edge 存的是 [a, b, w]：代表从 a 到 b 的体力值为 w
        // 虽然我们可以往四个方向移动，但是只要对于每个点都添加「向右」和「向下」两条边的话，其实就已经覆盖了所有边了
        List<int[]> edges = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int idx = getIndex(i, j);
                if (i + 1 < row) {
                    int a = idx, b = getIndex(i + 1, j);
                    int w = Math.abs(heights[i][j] - heights[i + 1][j]);
                    edges.add(new int[]{a, b, w});
                }
                if (j + 1 < col) {
                    int a = idx, b = getIndex(i, j + 1);
                    int w = Math.abs(heights[i][j] - heights[i][j + 1]);
                    edges.add(new int[]{a, b, w});
                }
            }
        }

        // 根据权值 w 降序
        Collections.sort(edges, (a,b)->a[2]-b[2]);

        // 从「小边」开始添加，当某一条边别应用之后，恰好使用得「起点」和「结点」联通
        // 那么代表找到了「最短路径」中的「权重最大的边」
        int start = getIndex(0, 0), end = getIndex(row - 1, col - 1);
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], w = edge[2];
            union(a, b);
            if (query(start, end)) {
                return w;
            }
        }
        return 0; 
    }
    
    int getIndex(int x, int y) {
        return x * col  + y;
    }

}

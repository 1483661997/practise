package DataStruct.BASE.BFS;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 一个坐标可以从 -infinity 延伸到 +infinity 的 无限大的 棋盘上，你的 骑士 驻扎在坐标为 [0, 0] 的方格里。
骑士的走法和中国象棋中的马相似，走 “日” 字：即先向左（或右）走 1 格，再向上（或下）走 2 格；或先向左（或右）走 2 格，再向上（或下）走 1 格。
每次移动，他都可以按图示八个方向之一前进。
返回 骑士前去征服坐标为 [x, y] 的部落所需的最小移动次数 。本题确保答案是一定存在的。
 */

 public class MinKnightMovesSolution { 

    public int minKnightMoves1(int x, int y) {
        // 八个方向的偏移量
        int[][] offsets = {{1, 2}, {2, 1}, {2, -1}, {1, -2},
                {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};

        // - 我们不使用低效的 HashSet，而是使用 bitmap
        //     否则，我们将没有时间进行测试用例。
        // - 我们创建的位图足以覆盖所有可能的
        //     输入，根据问题的描述。
        boolean[][] visited = new boolean[607][607];

        Deque<int[]> queue = new LinkedList<>();
        queue.addLast(new int[]{0, 0});
        int steps = 0;

        while (queue.size() > 0) {
            int currLevelSize = queue.size();
            // 遍历当前层
            for (int i = 0; i < currLevelSize; i++) {
                int[] curr = queue.removeFirst();
                if (curr[0] == x && curr[1] == y) {
                    return steps;
                }

                for (int[] offset : offsets) {
                    int[] next = new int[]{curr[0] + offset[0], curr[1] + offset[1]};
                    // 把坐标赋值给 bitmap
                    if (!visited[next[0] + 302][next[1] + 302]) {
                        visited[next[0] + 302][next[1] + 302] = true;
                        queue.addLast(next);
                    }
                }
            }
            steps++;
        }
        // 继续下一层
        return steps;
    }


    public static void main(String[] args) {
        System.out.println(new MinKnightMovesSolution().minKnightMoves(25, -87));
    }   
    public int minKnightMoves(int x, int y) {
        boolean[][] visited = new boolean[617][617];
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0,0});
        int steps = 0;
        int flag = 0;
        visited[302][302] = true;
        
        while (!queue.isEmpty() && flag == 0) {
            int size = queue.size();
            while (size-- != 0) {
                int[] pos = queue.poll();
                int posX = pos[0], posY = pos[1];
                // System.out.println(posX + " " + posY);
                
                int[] row = new int[]{1, 2,  2,  1, -1, -2, -2, -1};
                int[] col = new int[]{2, 1, -1, -2,  2,  1, -1, -2};
                for(int i = 0; i < 8; i++){
                    int newRow = posX + row[i];
                    int newCol = posY + col[i];
                    if(newRow == x && newCol == y){
                        flag = 1;
                        break;
                    }
                    if(!visited[newRow+302][newCol+302]){
                        queue.offer(new int[]{newRow, newCol});
                        visited[newRow+302][newCol+302] = true;
                    }
                }
            }
            steps++;
            // System.out.println(steps+"/////");
        
        }
        return steps;
    }

       
}

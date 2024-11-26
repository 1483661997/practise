package DataStruct.BASE.BFS;

import java.util.*;
public class snakesAndLadders {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
    
    /*[[-1,-1],[-1,3]]
添加到测试用例
标准输出
1 0
2 1
3 1
 */
/* [[-1,-1,-1,-1,-1,-1],
    [-1,-1,-1,-1,-1,-1],
    [-1,-1,-1,-1,-1,-1],
    [-1,35,-1,-1,13,-1],
    [-1,-1,-1,-1,-1,-1],
    [-1,15,-1,-1,-1,-1]] */

    /*1 1
2 2
3 2
4 2
5 2
6 2
7 2
8 3
9 3
10 3
11 3
12 3
13 3
14 4
15 4
16 4
17 4
18 4
19 4
13 5
21 5
22 5
35 5
24 5
25 5
26 6
27 6
28 6
 */



/*
 * class Solution {
    public int minMutation(String startGene, String endGene, String[] bank) {

    }
}
 */
    class Solution1 {
        
        // public int minMutation(String startGene, String endGene, String[] bank) {

        // }
      
        public int snakesAndLadders1(int[][] board) {
            int n = board.length;
            boolean[] visited = new boolean[n * n + 1]; // 使用一维数组标记访问
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(1); // 从位置1开始
            visited[1] = true;
            int step = 0;
        
            while (!queue.isEmpty()) {
                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    int curr = queue.poll();
                    if (curr == n * n) return step; // 到达终点
        
                    for (int j = 1; j <= 6 && curr + j <= n * n; j++) { // 模拟掷骰子
                        int next = curr + j;
                        int value = getBoardValue(board, next); // 获取对应位置的值
                        if (value > 0) next = value; // 如果存在蛇或梯子，则跳转
        
                        if (!visited[next]) {
                            visited[next] = true;
                            queue.offer(next);
                        }
                    }
                }
                step++; // 完成一轮掷骰子后增加步数
            }
        
            return -1; // 无法到达终点
        }
        
        private int getBoardValue(int[][] board, int number) {
            int n = board.length;
            int row = (number - 1) / n;
            int col = (number - 1) % n;
            if (row % 2 == 1) { // 奇数行（从右向左）
                col = n - 1 - col;
            }
            row = n - 1 - row; // 从下到上
            return board[row][col];
        }

        
        
        public int snakesAndLadders(int[][] board) {
            int n = board.length;   // 获取方阵的边长
            int target = n * n;     // 获取方阵尺寸，也是最后要到达目的地
            Queue<int[]> queue = new LinkedList<>();   // 队列用于BFS，存放待搜索的方格编号和到达该方格时的最少移动数
            queue.offer(new int[]{1, 0});   // 初始{1,0}入队，表示起点1，0次移动
            boolean[][] visited = new boolean[n][n];   // 用于BFS过程中标记方格是否搜索过
            // BFS
            while(!queue.isEmpty()){
                int[] node = queue.poll();  // 弹出队首待搜索节点
                int curr = node[0], cnt = node[1];   // 获取当前搜索的方格宾浩和到达该方格的最少移动数
                cnt++;  // 移动数加1
                for(int i = curr + 1; i <= Math.min(target, curr + 6); i++){
                    // 枚举所有下一个可搜索且未搜索过的方格编号
                    int r = n-1 - (i-1) / n, c = (i-1) % n;     // 根据方格编号获取这个编号的行和列
                    c += (n-1 - 2*c) * ((n-1-r) & 1);       // 根据行数修正列数
                    if(visited[r][c])continue;  // 跳过搜索过的编号
                    visited[r][c] = true;       // 标记该编号已搜索
                    int next = board[r][c] == - 1 ? i : board[r][c];    // 如果这个编号所在的方格可以转移到其他格子，转移到对应编号；否则就是在当前编号
                    if(next == target)return cnt;   // 到达终点，直接返回最小移动数 
                    queue.offer(new int[]{next, cnt});  // 加入队列
                }
            }
            return -1;  // 退出循环说明没有到达目的地
        }
    }
    


    public int snakesAndLadders(int[][] board) {
        int num = board.length * board[0].length;
        int pos = 1;
        Queue<int[]> queue = new LinkedList<int[]>();
        boolean[][] visited = new boolean[board.length][board[0].length];
        // int step = 0;
        queue.add(new int[]{pos, 0});
        while(!queue.isEmpty()){
            int[] arr = queue.poll();
            int temp = arr[0], step = arr[1];
            step++;
            System.out.println(temp + " " + step);

            for(int i = temp + 1; i <= num && i <= temp + 6; i++){
                if(isVisit(visited, i)) continue;
                int posValue = visit(board, visited, i);

                int next = posValue == -1 ? i :posValue;
                if(next == num) return step;
                queue.add(new int[]{next, step});

            }
            
        }
        


        return -1;
    }
    private boolean isVisit(boolean[][] flag, int pos){


            
        int row = (pos - 1)/flag[0].length;
        int col = row * flag[0].length;
          
        if(row % 2 == 0){
            col = pos - col - 1;          
         }else{
             col = pos- col;
             col = flag[0].length - col;
        }
        row = flag[0].length - 1 - row;
        if(flag[row][col] == true) return true;
        flag[row][col] = true;
        return false;
    }
    private int visit(int[][] board,boolean[][] flag, int pos){
        int row = (pos - 1)/flag[0].length;
        int col = row * flag[0].length;
          
        if(row % 2 == 0){
            col = pos - col - 1;          
         }else{
             col = pos- col;
             col = flag[0].length - col;
        }
        row = flag[0].length  - 1 - row;     

        return board[row][col];
    }

   
  }



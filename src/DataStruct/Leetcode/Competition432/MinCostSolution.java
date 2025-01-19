package DataStruct.Leetcode.Competition432;

public class MinCostSolution {
    public int minCostBeautifulHouses(int n, int[][] cost) {

        int half = n / 2;
        final int INF = Integer.MAX_VALUE / 2; // 避免加法溢出
        
        int[][][] DP = new int[half+1][3][3];
        for (int i = 0; i <= half; i++) {
            for (int c1 = 0; c1 < 3; c1++) {
                for (int c2 = 0; c2 < 3; c2++) {
                    DP[i][c1][c2] = INF;
                }
            }
        }

        for (int c1 = 0; c1 < 3; c1++) {
            for (int c2 = 0; c2 < 3; c2++) {
                if (c1 != c2) {
                    DP[1][c1][c2] = cost[0][c1] + cost[n-1][c2];
                }
            }
        }

        for (int i = 1; i < half; i++) {
            for (int c1 = 0; c1 < 3; c1++) {
                for (int c2 = 0; c2 < 3; c2++) {
                    int prevCost = DP[i][c1][c2];
                    if (prevCost == INF) continue;

                    for (int c1Next = 0; c1Next < 3; c1Next++) {
                        if (c1Next == c1) continue;
                        for (int c2Next = 0; c2Next < 3; c2Next++) {
                            if (c2Next == c2) continue;
                            if (c1Next == c2Next) continue;

                            int newCost = prevCost
                                          + cost[i][c1Next]
                                          + cost[n - 1 - i][c2Next];
                            if (newCost < DP[i+1][c1Next][c2Next]) {
                                DP[i+1][c1Next][c2Next] = newCost;
                            }
                        }
                    }
                }
            }
        }

        int ans = INF;
        for (int c1 = 0; c1 < 3; c1++) {
            for (int c2 = 0; c2 < 3; c2++) {
                ans = Math.min(ans, DP[half][c1][c2]);
            }
        }

        return ans;
    }

    // 为了方便简单测试
    public static void main(String[] args) {
        MinCostSolution sol = new MinCostSolution();
        
        // 示例1
        int n1 = 4;
        int[][] cost1 = {
            {3,5,7},
            {6,2,9},
            {4,8,1},
            {7,3,5}
        };
        System.out.println(sol.minCostBeautifulHouses(n1, cost1));  
        // 期望输出: 9

        // 示例2
        int n2 = 6;
        int[][] cost2 = {
            {2,4,6},
            {5,3,8},
            {7,1,9},
            {4,6,2},
            {3,5,7},
            {8,2,4}
        };
        System.out.println(sol.minCostBeautifulHouses(n2, cost2));
        // 期望输出: 18
    }
    
}

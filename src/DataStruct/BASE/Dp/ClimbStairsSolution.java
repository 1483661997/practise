package DataStruct.BASE.Dp;

public class ClimbStairsSolution {
    public int climbStairs(int n) {
        if(n == 1 || n == 0) return 1;
        if(n == 2) return 2;
        int dp1 = 2, dp2= 1;
        for(int i = 3; i <= n; i++){
            int tmp = dp1+dp2;
            dp2 = dp1;
            dp1 = tmp;
        }
        return dp1;

    }
}

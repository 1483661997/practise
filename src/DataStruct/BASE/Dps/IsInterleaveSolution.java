package DataStruct.BASE.Dps;

/*
 * 交错字符串
中等
相关标签
相关企业

给定三个字符串 s1、s2、s3，请你帮忙验证 s3 是否是由 s1 和 s2 交错 组成的。

两个字符串 s 和 t 交错 的定义与过程如下，其中每个字符串都会被分割成若干 非空
子字符串
：

    s = s1 + s2 + ... + sn
    t = t1 + t2 + ... + tm
    |n - m| <= 1
    交错 是 s1 + t1 + s2 + t2 + s3 + t3 + ... 或者 t1 + s1 + t2 + s2 + t3 + s3 + ...

注意：a + b 意味着字符串 a 和 b 连接。

 

示例 1：

输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
输出：true

示例 2：

输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
输出：false

示例 3：

输入：s1 = "", s2 = "", s3 = ""
输出：true

 */
public class IsInterleaveSolution {
    public static void main(String[] args) {
        System.out.println(isInterleave("aabcc", "dbbca", "aadbbcbcac"));
    }
    public static boolean isInterleave2(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), t = s3.length();

        if (n + m != t) {
            return false;
        }

        boolean[][] f = new boolean[n + 1][m + 1];

        f[0][0] = true;
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {
                int p = i + j - 1;
                if (i > 0) {
                    f[i][j] = f[i][j] || (f[i - 1][j] && s1.charAt(i - 1) == s3.charAt(p));
                }
                if (j > 0) {
                    f[i][j] = f[i][j] || (f[i][j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }
        }
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                System.out.print(f[i][j]);
            }
            System.out.println();
        }
        return f[n][m];
    }
    public static boolean isInterleave(String s1, String s2, String s3) {
        int len1 = s1.length(), len2 = s2.length(), len3 = s3.length();
        int[][] dp = new int[len1+1][len2+1];
        //数量对不上直接return false
        if(len1 + len2 != len3) return false;
        
        //数量对上，判断是否交错
        dp[0][0] = 1;
 
        // 输入：s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
       
        for(int i = 0; i <= len1; i++){
            for(int j = 0; j <= len2; j++){
                // int flag = 0;
                if(i > 0 && s3.charAt(i+j - 1) == s1.charAt(i-1) && (dp[i-1][j] == 1)){
                    
                    dp[i][j] = 1;
              
                }
                if(j > 0 && s3.charAt(i+j - 1) == s2.charAt(j -1) && ((dp[i][j-1] == 1))){
                    System.out.print("***********");
                    dp[i][j] = 1;
            
                }
            }
        }
    
        for(int i = 0; i < len1; i++){
            for(int j = 0; j < len2; j++){
                System.out.print(dp[i][j]);
            }
            System.out.println();
        }
        return dp[len1][len2] == 0 ? false : true;

    }
}

package DataStruct.BASE.Stack;

/**
 * 32. 最长有效括号
 * 给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号
 * 子串
 * 的长度。
 * 示例 1：
 * 输入：s = "(()"
 * 输出：2
 * 解释：最长有效括号子串是 "()"
 * 示例 2：
 * 输入：s = ")()())"
 * 输出：4
 * 解释：最长有效括号子串是 "()()"
 * 示例 3：
 * 输入：s = ""
 * 输出：0
 * 提示：
 * 0 <= s.length <= 3 * 104
 * s[i] 为 '(' 或 ')'
 */
public class LongestValidParenthesesSolution {
    public static void main(String[] args) {
        System.out.println(new LongestValidParenthesesSolution().longestValidParentheses("()(())"));
    }
    public int longestValidParentheses(String s) {
        int len = s.length();
        int[] dp = new int[len];
        int max = 0;
        for(int i = 1; i < len; i++){
            if(s.charAt(i) == ')' ) {
                if (s.charAt(i - 1) == ')')
                    dp[i] = i - 2 >= 0 ? dp[i - 2] + 2 : 2;
                else if ((i - dp[i - 1] - 1 >= 0 && s.charAt(i - dp[i - 1] - 1) == '(')) {
                    dp[i] = dp[i - 1] + ((i - dp[i - 1]) >= 2 ? dp[i - dp[i - 1] - 2] : 0) + 2;
                }

            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }
}

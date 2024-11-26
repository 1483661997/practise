package DataStruct.Dp;

import java.util.*;

/*
 * 32. 最长有效括号

给你一个只包含 '(' 和 ')' 的字符串，找出最长有效（格式正确且连续）括号
子串
的长度。

 

示例 1：

输入：s = "(()"
输出：2
解释：最长有效括号子串是 "()"

示例 2：

输入：s = ")()())"
输出：4
解释：最长有效括号子串是 "()()"

示例 3：

输入：s = ""
输出：0

 

提示：

    0 <= s.length <= 3 * 104
    s[i] 为 '(' 或 ')'


 */
public class LongestValidParenthesesSolution {

    public static void main(String[] args) {
        LongestValidParenthesesSolution solution = new LongestValidParenthesesSolution();
        System.out.println(solution.longestValidParentheses("(()"));
    }
    
    // public int longestValidParentheses(String s) {
    //     int len = s.length();
    //     int[][] dp = new int[len][len];
    //     int max = 0;
    //     for(int i = 0; i < len; i++){
    //         for(int j = i + 1; j <= len; j++){
    //             if(isValid(s.substring(i,j)) && j - i > max){
    //                 System.out.println(s.substring(i,j));
    //                 max = j - i ;
    //             }
    //         }
    //     }
    //     return max;

    // }

    public int longestValidParentheses(String s) {
        
        Deque<Integer> stack = new LinkedList<>();
        stack.push(-1);
        int len = s.length();
        int max = 0;
        for(int i = 0; i < len; i++){
            if(s.charAt(i) == ')'){
                stack.pop();
                if(stack.isEmpty()){
                    stack.push(i);
                }else{
                    max = i - stack.peek() > max ? i - stack.peek() : max;
                }
            }else{
                stack.push(Integer.valueOf(i));
            }
        }

        return max;


    }

    public int longestValidParentheses1(String s) {

        LinkedList<Character> list = new LinkedList<>();
        int step = 0;
        int max = 0;
        for(char ch : s.toCharArray()){
            if(ch == '('){
                list.addLast(ch);
                // step++;
            }
            else if(!list.isEmpty() && list.getLast() == '('){
                list.removeLast();
                step += 2;
            }
            else step = 0;
            if(step > max) max = step;
        }
        
       return max;
        // return list.isEmpty();
    }
}

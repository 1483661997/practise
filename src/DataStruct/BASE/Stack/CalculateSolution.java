package DataStruct.BASE.Stack;

import DataStruct.BASE.Dp.ClimbStairsSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 224. 基本计算器
 * 给你一个字符串表达式 s ，请你实现一个基本计算器来计算并返回它的值。
 * 注意:不允许使用任何将字符串作为数学表达式计算的内置函数，比如 eval() 。
 * 示例 1：
 * 输入：s = "1 + 1"
 * 输出：2
 * 示例 2：
 * 输入：s = " 2-1 + 2 "
 * 输出：3
 * 示例 3：
 * 输入：s = "(1+(4+5+2)-3)+(6+8)"
 * 输出：23
 * 提示：
 * 1 <= s.length <= 3 * 105
 * s 由数字、'+'、'-'、'('、')'、和 ' ' 组成
 * s 表示一个有效的表达式
 * '+' 不能用作一元运算(例如， "+1" 和 "+(2 + 3)" 无效)
 * '-' 可以用作一元运算(即 "-1" 和 "-(2 + 3)" 是有效的)
 * 输入中不存在两个连续的操作符
 * 每个数字和运行的计算将适合于一个有符号的 32位 整数
 */


public class CalculateSolution {
    public static void main(String[] args) {
        CalculateSolution calculateSolution = new CalculateSolution();
        System.out.println(calculateSolution.calculate("(3)+1"));
    }

    //连续输入  323 + 432
    //带括号
    public int calculate(String s) {
        int res = 0;
        s= s.replace(" ", "");
        s= s.replace("+-", "-");
        s= s.replace("--", "+");

        Stack<Integer> digitStack = new Stack<>();
        Stack<Character> charStack = new Stack<>();


        List<String> list = rpn(s);
        for(String str : list){
            if(str.equals("+") || str.equals("-")){
                int num1 = digitStack.pop();
                int num2 = digitStack.pop();
                digitStack.push(str.equals("+") ? num2 + num1 : num2 - num1);
            }else{
                digitStack.push(Integer.valueOf(str));
            }
        }
        res = digitStack.peek();
        return res;
    }

    //连续数字
    public List<String> rpn(String s){
        Stack<Character> stack = new Stack<>();

        List<String> notation = new ArrayList<>();
        int pos = 0;
        int flag = 1;
        if(s.charAt(0) == '-'){
            notation.add("0");
        }
        while (pos < s.length()){
            char ch = s.charAt(pos);
            if(Character.isDigit(ch)){
                StringBuilder digit = new StringBuilder();
                while(pos < s.length() && Character.isDigit(s.charAt(pos))){
                    digit.append(s.charAt(pos));
                    pos++;
                }
                notation.add(digit.toString());
                pos--;
            }else if(stack.isEmpty() ){
                stack.push(ch);
            }else if(ch == ')') {
                while (stack.peek() != '('){
                    notation.add(stack.pop().toString());
                }
                stack.pop();
            }else if(ch == '('){

                stack.push(ch);
                if(pos+1 < s.length() && s.charAt(pos+1) == '-'){
                    notation.add("0");
                }
            }else{
                if(!stack.isEmpty() && stack.peek() != '('){
                    notation.add(stack.pop().toString());
                }
                stack.push(ch);
            }
            pos++;
        }

        while (!stack.isEmpty()){
            char ch = stack.pop();
            if(ch != '(' && ch != ')'){
                notation.add(String.valueOf(ch));
            }
        }
        return notation;
    }
}

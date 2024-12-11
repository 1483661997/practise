package DataStruct.BASE.regex;

/**
 * LCR 138. 有效数字
有效数字（按顺序）可以分成以下几个部分：
若干空格
一个 小数 或者 整数
（可选）一个 'e' 或 'E' ，后面跟着一个 整数
若干空格
小数（按顺序）可以分成以下几个部分：
（可选）一个符号字符（'+' 或 '-'）
下述格式之一：
至少一位数字，后面跟着一个点 '.'
至少一位数字，后面跟着一个点 '.' ，后面再跟着至少一位数字
一个点 '.' ，后面跟着至少一位数字
整数（按顺序）可以分成以下几个部分：
（可选）一个符号字符（'+' 或 '-'）
至少一位数字
部分有效数字列举如下：["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"]
部分无效数字列举如下：["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"]
给你一个字符串 s ，如果 s 是一个 有效数字 ，请返回 true 。
示例 1：
输入：s = "0"
输出：true
示例 2：
输入：s = "e"
输出：false
示例 3：
输入：s = "."
输出：false
 */
public class ValidNumberSolution {
    public static void main(String[] args) {
        ValidNumberSolution validNumberSolution = new ValidNumberSolution();
        System.out.println(validNumberSolution.validNumber(". 1"));
    }
    public boolean validNumber(String s) {
        s = s.trim();
        //整数 [+|-]?[0-9]+
        //小数 [+|-]?[0-9]*[.]?[0-9]*
        //    [+|-][[0-9]+. | [0-9]+.[0-9]+ | .[0-9]+]

        String regex1 = "[+|-]?(([0-9]+[.])|([.][0-9]+)|([0-9]+[.][0-9]+))";
        String regex2 = "[+|-]?[0-9]+";

        System.out.println(s.matches(regex1));
        System.out.println(s.matches(regex2));

        // String regex = "(("+regex1+")|("+ regex2+"))";
        String regex = "(("+regex1+")|("+ regex2+"))"+"([e|E]"+regex2+")?";
        System.out.println(regex);
        //(([+|-]?(([0-9]+.)|(.[0-9]+)|([0-9]+.[0-9]+)))|([+|-]?[0-9]+))([e|E][+|-]?[0-9]+)?
        return s.matches(regex);
        
    }
}

package DataStruct.BASE.String;

/*
 * 6. Z 字形变换
中等
相关标签
相关企业

将一个给定字符串 s 根据给定的行数 numRows ，以从上往下、从左到右进行 Z 字形排列。

比如输入字符串为 "PAYPALISHIRING" 行数为 3 时，排列如下：

P   A   H   N
A P L S I I G
Y   I   R


之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："PAHNAPLSIIGYIR"。

请你实现这个将字符串进行指定行数变换的函数：

string convert(string s, int numRows);
示例 1：

输入：s = "PAYPALISHIRING", numRows = 3
输出："PAHNAPLSIIGYIR"

示例 2：

输入：s = "PAYPALISHIRING", numRows = 4
输出："PINALSIGYAHRPI"
解释：
P     I    N
A   L S  I G
Y A   H R
P     I

示例 3：

输入：s = "A", numRows = 1
输出："A"
x 

 x1 + y1 = groupmember

 y1 - x1 = groupmember - 2 *x1
提示：

    1 <= s.length <= 1000
    s 由英文字母（小写和大写）、',' 和 '.' 组成
    1 <= numRows <= 1000


 */
public class ConvertSolution {
    public static void main(String[] args) {
        System.out.println(new ConvertSolution().convert("PAYPALISHIRING", 3));
    }
    
    public String convert(String s, int numRows) {
        StringBuilder str = new StringBuilder();
        int groupNum = 2 * numRows - 2;
        int len = s.length();
        //first row

        for(int j = 0; j < numRows; j++){
            for(int i = j; i < len; i += groupNum){
                int index = i + groupNum-2*(i%groupNum);
                str.append(s.charAt(i));
                if(j !=0 && j != numRows-1 && index < len){
                    str.append(s.charAt(index));
                }
            }
        }
    
        return str.toString();
    }
}

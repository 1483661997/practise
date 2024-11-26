package DataStruct.BASE.String;
public class ConvertToTitleSolution {
/*
 * 168. Excel Sheet Column Title
简单
相关标签
相关企业
Given an integer columnNumber, return its corresponding column title as it appears in an Excel sheet.

For example:

A -> 1
B -> 2
C -> 3
...
Z -> 26
AA -> 27
AB -> 28 
...
 

Example 1:

Input: columnNumber = 1
Output: "A"
Example 2:

Input: columnNumber = 28
Output: "AB"
Example 3:

Input: columnNumber = 701
Output: "ZY"
 

Constraints:

1 <= columnNumber <= 231 - 1
 */
    public static void main(String[] args) {
        System.out.println( new ConvertToTitleSolution().convertToTitle(701));
    }
    public String convertToTitle(int columnNumber) {
        StringBuffer sb = new StringBuffer();
        while(columnNumber > 0){
            
            int n = columnNumber % 26;

            if(n == 0){
                sb.append('Z');
                columnNumber--;
            }
            else sb.append((char)('A' + (char)(n-1)));
            columnNumber /= 26;
        }
        return sb.reverse().toString();
    }
}

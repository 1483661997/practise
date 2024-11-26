package DataStruct.BASE.String;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.*;

/*
 * 12. 整数转罗马数字
七个不同的符号代表罗马数字，其值如下：

符号	值
I	1
V	5
X	10
L	50
C	100
D	500
M	1000
罗马数字是通过添加从最高到最低的小数位值的转换而形成的。将小数位值转换为罗马数字有以下规则：

如果该值不是以 4 或 9 开头，请选择可以从输入中减去的最大值的符号，将该符号附加到结果，减去其值，然后将其余部分转换为罗马数字。
如果该值以 4 或 9 开头，使用 减法形式，表示从以下符号中减去一个符号，
例如 4 是 5 (V) 减 1 (I): IV ，9 是 10 (X) 减 1 (I)：IX。
仅使用以下减法形式：4 (IV)，9 (IX)，40 (XL)，90 (XC)，400 (CD) 和 900 (CM)。
只有 10 的次方（I, X, C, M）最多可以连续附加 3 次以代表 10 的倍数。你不能多次附加 5 (V)，50 (L) 或 500 (D)。
如果需要将符号附加4次，请使用 减法形式。
给定一个整数，将其转换为罗马数字。

示例 1：

输入：num = 3749

输出： "MMMDCCXLIX"

解释：

3000 = MMM 由于 1000 (M) + 1000 (M) + 1000 (M)
 700 = DCC 由于 500 (D) + 100 (C) + 100 (C)
  40 = XL 由于 50 (L) 减 10 (X)
   9 = IX 由于 10 (X) 减 1 (I)
注意：49 不是 50 (L) 减 1 (I) 因为转换是基于小数位
示例 2：

输入：num = 58

输出："LVIII"

解释：

50 = L
 8 = VIII
示例 3：

输入：num = 1994

输出："MCMXCIV"

解释：

1000 = M
 900 = CM
  90 = XC
   4 = IV
 

提示：

1 <= num <= 3999
 */
public class IntToRomanSolution {
    public static void main(String[] args) {
        System.out.println(new IntToRomanSolution().intToRoman(1994));
    }
    public String intToRoman(int num) {

        /*
         * I	1
V	5
X	10
L	50
C	100
D	500
M	1000
         */
       
        //  Map<Integer, String> map = Map.ofEntries(
            // Map.entry(1,"I"),
            // Map.entry(4,"IV"),
            // Map.entry(5,"V"),
            // Map.entry(9,"IX"),
            // Map.entry(10,"X"),
            // Map.entry(40,"XL"),
            // Map.entry(50,"L"),
            // Map.entry(90,"XC"),
            // Map.entry(100,"C"),
            // Map.entry(400,"CD"),
            // Map.entry(500,"D"),
            // Map.entry(900,"CM"),
            // Map.entry(1000,"M")
        // );
        
        
        StringBuilder str = new StringBuilder();
        List<String> list = new LinkedList<>();
        int bits = 1;
        while(num != 0){
            int n = num % 10;
            num /= 10;
            list.add(toRoman(n, bits));
            bits *= 10;    
            
        }
        for(int i = list.size() -1; i >= 0; i--)
            str.append(list.get(i));
        return str.toString();
    }
    public String toRoman(int num, int bits){

        /*
         * I	1
V	5
X	10
L	50
C	100
D	500
M	1000
         */
        switch (num) {
            case 1:
                switch (bits) {
                    case 1:
                        return "I";
                    case 10:
                        return "X";
                    case 100:
                        return "C";
                    case 1000:
                        return "M";
                    default:
                        break;
                }
                
                break;
            case 2:
                return toRoman(1, bits) + toRoman(1, bits);
                
            case 3:
                return toRoman(1, bits) + toRoman(1, bits)  + toRoman(1, bits);

            case 4:
                switch (bits) {
                    case 1:
                        return "IV";
                    case 10:
                        return "XL";
                    case 100:
                        return "CD";
                    default:
                        break;
            }
            case 5:
                switch (bits) {
                    case 1:
                        return "V";
                    case 10:
                        return "L";
                    case 100:
                        return "D";
                    default:
                        break;
                }
                
                break;
            case 6:
                return toRoman(5, bits) + toRoman(1, bits);
            case 7:
                return toRoman(5, bits) + toRoman(1, bits) + toRoman(1, bits);
            case 8:
                return toRoman(5, bits) + toRoman(1, bits) + toRoman(1, bits) + toRoman(1, bits);                
            case 9:
                switch (bits) {
                    case 1:
                        return "IX";
                    case 10:
                        return "XC";
                    case 100:
                        return "CM";                
                    default:
                        break;
                }
                    break;
            default:
                break;
        }
        return "";

    }
}

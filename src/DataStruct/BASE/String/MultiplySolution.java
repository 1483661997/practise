package DataStruct.String;

import java.math.BigInteger;

/*
 * 43. 字符串相乘
给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。

注意：不能使用任何内置的 BigInteger 库或直接将输入转换为整数。

 

示例 1:

输入: num1 = "2", num2 = "3"
输出: "6"

示例 2:

输入: num1 = "123", num2 = "456"
输出: "56088"

 

提示：

    1 <= num1.length, num2.length <= 200
    num1 和 num2 只能由数字组成。
    num1 和 num2 都不包含任何前导零，除了数字0本身。


 */
public class MultiplySolution {
    public static void main(String[] args) {
        System.out.println(new MultiplySolution().multiply("256117489511377083148593685533950561400363410418754703282767252221661609163404299", "61200496111643709081218550902198211480012378840070191147459688611759881218205422431757614"));
    }
    public String multiply(String num1, String num2) {
        char[] charArr1 = num1.toCharArray();
        char[] charArr2 = num2.toCharArray();
        int len1 = num1.length();
        int len2 = num2.length();
        BigInteger flag1 = BigInteger.valueOf(1);
        BigInteger flag2 = BigInteger.valueOf(1);
        BigInteger res = BigInteger.valueOf(0);
        BigInteger carry = BigInteger.valueOf(0);
        for(int i = len1 - 1; i >= 0; i--){
            BigInteger tmp = BigInteger.valueOf(0);
            flag2 = BigInteger.valueOf(1);            
            for(int j = len2 - 1; j >= 0 ; j--){
                // System.out.println("***********");
                BigInteger big = BigInteger.valueOf((charArr2[j] - '0') *( charArr1[i] - '0')).add(carry);
                // System.out.println(big);
                // big.remainder(10)
                carry = big.divide(BigInteger.valueOf(10));
                // System.out.println(carry);
                big = big.remainder(BigInteger.valueOf(10));
                // System.out.println(big);
               
                big  = big.multiply(flag2);
                // System.out.println(big);
               
                tmp = tmp.add( big);
                // System.out.println(tmp);
                flag2 = flag2.multiply(BigInteger.valueOf(10));
                // System.out.println("////////////////////");
            }
            if(!carry.equals(BigInteger.valueOf(0)) ) {
                tmp = tmp.add(carry.multiply(flag2));
                // System.out.println("final" + tmp);
                carry = BigInteger.valueOf(0);
            }
            // System.out.println(tmp);
            res = res.add(tmp.multiply(flag1));
            // res += tmp;

            flag1 = flag1.multiply(BigInteger.valueOf(10));
        }
        // System.out.println(res);
        return String.valueOf(res);

    }
}

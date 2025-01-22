package DataStruct.BASE.String;
import java.util.*;
/**
 * 2266. 统计打字方案数
中等
Alice 在给 Bob 用手机打字。数字到字母的 对应 如下图所示。
为了 打出 一个字母，Alice 需要 按 对应字母 i 次，i 是该字母在这个按键上所处的位置。
比方说，为了按出字母 's' ，Alice 需要按 '7' 四次。类似的， Alice 需要按 '5' 两次得到字母  'k' 。
注意，数字 '0' 和 '1' 不映射到任何字母，所以 Alice 不 使用它们。
但是，由于传输的错误，Bob 没有收到 Alice 打字的字母信息，反而收到了 按键的字符串信息 。
比方说，Alice 发出的信息为 "bob" ，Bob 将收到字符串 "2266622" 。
给你一个字符串 pressedKeys ，表示 Bob 收到的字符串，请你返回 Alice 总共可能发出多少种文字信息 。
由于答案可能很大，将它对 109 + 7 取余 后返回。
示例 1：
输入：pressedKeys = "22233"
输出：8
解释：
Alice 可能发出的文字信息包括：
"aaadd", "abdd", "badd", "cdd", "aaae", "abe", "bae" 和 "ce" 。
由于总共有 8 种可能的信息，所以我们返回 8 。
示例 2：
输入：pressedKeys = "222222222222222222222222222222222222"
输出：82876089
解释：
总共有 2082876103 种 Alice 可能发出的文字信息。
由于我们需要将答案对 109 + 7 取余，所以我们返回 2082876103 % (109 + 7) = 82876089 。

 */
public class CountTextsSolution {
    public int Mod = 1000000007;
    public static void main(String[] args) {
        //537551452
//        String str = "88888888888888888888888888888"+
//                     "99999999999999999999999999999"+
//                     "44444444444444444444444444444" +
//                     "88888888888888888888888888888" ;
//        System.out.println(new CountTextsSolution().countTexts(str));
//

        long longValue = 9223372036854775807L;  // Long.MAX_VALUE
        double doubleValue = longValue;

        long mod = 1000000007L;
        System.out.println("Long modulo: " + (longValue % mod));       // 正确的取余结果
        System.out.println("Double modulo: " + (doubleValue % mod));  // 可能不准确


    }
    public int countTexts(String pressedKeys) {
        int len = pressedKeys.length();
        long[] dp3 = new long[len < 3 ? 3 : len];
        long[] dp4 = new long[len < 4 ? 4 : len];
        
        dp3[0] = 1;
        dp3[1] = 2;
        dp3[2] = 4;
        dp4[0] = 1;
        dp4[1] = 2;
        dp4[2] = 4;
        dp4[3] = 8;

        for(int i = 3; i < len; i++){
            dp3[i] = (dp3[i-3] + dp3[i-2] + dp3[i-1]) % Mod;
        }
        for(int i = 4; i < len; i++){
            dp4[i] = (dp4[i-4] + dp4[i-3] + dp4[i-2] + dp4[i-1]) % Mod;
        }
        long ans = 1;
        int left = 0, right = 1;
        while (right < len) {
            while (right < len && pressedKeys.charAt(right) == pressedKeys.charAt(left)) {
                right++;
            }
            if(pressedKeys.charAt(left) == '7' || pressedKeys.charAt(left) == '9'){
                ans *= dp4[right-left-1];
                // System.out.println(right - left - 1);
                ans %= Mod;
            }else{
                ans *= dp3[right-left-1];
                ans %= Mod;

            }
            System.out.println(pressedKeys.charAt(left) + " " + ( right- left) + "//////");
            left = right;
        }
        


         return (int)ans;
    }
}

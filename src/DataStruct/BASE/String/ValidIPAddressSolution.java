package DataStruct.String;

/*
 * 468. 验证IP地址
给定一个字符串 queryIP。如果是有效的 IPv4 地址，返回 "IPv4" ；如果是有效的 IPv6 地址，返回 "IPv6" ；如果不是上述类型的 IP 地址，返回 "Neither" 。

有效的IPv4地址 是 “x1.x2.x3.x4” 形式的IP地址。 其中 0 <= xi <= 255 且 xi 不能包含 前导零。例如: “192.168.1.1” 、 “192.168.1.0” 为有效IPv4地址， 
“192.168.01.1” 为无效IPv4地址; “192.168.1.00” 、 “192.168@1.1” 为无效IPv4地址。

一个有效的IPv6地址 是一个格式为“x1:x2:x3:x4:x5:x6:x7:x8” 的IP地址，其中:

1 <= xi.length <= 4
xi 是一个 十六进制字符串 ，可以包含数字、小写英文字母( 'a' 到 'f' )和大写英文字母( 'A' 到 'F' )。
在 xi 中允许前导零。

例如 "2001:0db8:85a3:0000:0000:8a2e:0370:7334" 和 "2001:db8:85a3:0:0:8A2E:0370:7334" 是有效的 IPv6 地址，
而 "2001:0db8:85a3::8A2E:037j:7334" 和 "02001:0db8:85a3:0000:0000:8a2e:0370:7334" 是无效的 IPv6 地址。

 
示例 1：

输入：queryIP = "172.16.254.1"
输出："IPv4"
解释：有效的 IPv4 地址，返回 "IPv4"
示例 2：

输入：queryIP = "2001:0db8:85a3:0:0:8A2E:0370:7334"
输出："IPv6"
解释：有效的 IPv6 地址，返回 "IPv6"
示例 3：

输入：queryIP = "256.256.256.256"
输出："Neither"
解释：既不是 IPv4 地址，又不是 IPv6 地址
 
提示：

queryIP 仅由英文字母，数字，字符 '.' 和 ':' 组成。
 */
public class ValidIPAddressSolution {

    public static void main(String[] args) {
        System.out.println(


        new ValidIPAddressSolution().validIPAddress("2001:db8:85a3:0::8a2E:0370:7334")

        );
    }
    // String ipv4Pattern = "((2(5[0-5]|[0-4]\\d))|(1([0-9][0-9]))|[1-9][0-9]?|[0-9])(.((2(5[0-5]|[0-4]\\d))|(1([0-9][0-9]))|[1-9][0-9]?|[0-9])){3}";
    // String ipv6Pattern = "([0-9a-fA-F]{1,4})(:[0-9a-fA-F]{1,4}){7}";

    public String validIPAddress(String queryIP) {
        String regexIpv4 = "(2((5[0-5])|([0-4]\\d))|1\\d{2}|[1-9]?\\d)(\\.(2((5[0-5])|([0-4]\\d))|1\\d{2}|[1-9]?\\d)){3}";
        // String tmp = "\\.";

        //String regexIpv4 = " 2((5[0-5])|([0-4]\\d))|1\\d{2}|[1-9]?\\d";
        String regexIpv6 = "[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}";

        // System.out.println(".".matches(tmp));
        // System.out.println("2001:0db8:85a3:0:0:8A2E:0370:7334".matches(regexIpv6));
        return queryIP.matches(regexIpv4) ? "IPv4" : (queryIP.matches(regexIpv6) ? "IPv6" : "Neither");

    }
    public String validIPAddress1(String queryIP) {

        String[] str1 = queryIP.split("\\.",-1);
        String[] str2 = queryIP.split(":",-1);
        int len1 = str1.length, len2 = str2.length;
        
        if(len1 != 4 && len2 != 8) return "Neither";

        String res = "Neither";
        boolean flag = true;

        if(len1 == 4){
            for(int i = 0; i < 4; i++){
                flag &= isValid4(str1[i]);
                System.out.println(str1[i]);
            }
            if(flag) res = "IPv4";
            //str1没个数字都是合格的
        }else{
            //str2每个都是合格的
            for(int i = 0; i <8; i++){
                flag &= isValid6(str2[i]);

            }
            if(flag) res = "IPv6";


        }

        
        
       return res;
    }

    public boolean isValid4(String str){
        if(str == "") return false;
        if(str == "0") return true;
        int flag = 0;
        int res = 0;
        for(int i = 0; i < str.length(); i++){
            
            if(!Character.isDigit(str.charAt(i))) return false;
            if(Character.isDigit(str.charAt(i)) && str.charAt(i) != '0') flag = 1;
            if(flag == 0 && str.charAt(i) == '0') return false;
            res = res * 10 +str.charAt(i) - '0';
            if(res > 255) return false;

        }
        return true;
    }
    public boolean isValid6(String str){
        if(str.length() > 4 || str == "") return false;
        int len = str.length();
        for(int i = 0; i < len; i++){
            if(!Character.isDigit(str.charAt(i)) && (str.charAt(i) < 'a' || str.charAt(i) > 'f') && (str.charAt(i) < 'A' || str.charAt(i) > 'F' )) return false;
        }
        return true;
    }


}

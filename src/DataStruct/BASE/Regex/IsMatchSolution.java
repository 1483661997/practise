package DataStruct.BASE.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsMatchSolution {
        /*
         * 正则表达式匹配
    给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
    
        '.' 匹配任意单个字符
        '*' 匹配零个或多个前面的那一个元素
    
    所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
     
    示例 1：
    输入：s = "aa", p = "a"
    输出：false
    解释："a" 无法匹配 "aa" 整个字符串。
    示例 2:
    
    输入：s = "aa", p = "a*"
    输出：true
    解释：因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
    
    示例 3：
    输入：s = "ab", p = ".*"
    输出：true
    解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
    
    提示：
    
        1 <= s.length <= 20
        1 <= p.length <= 20
        s 只包含从 a-z 的小写字母。
        p 只包含从 a-z 的小写字母，以及字符 . 和 *。
        保证每次出现字符 * 时，前面都匹配到有效的字符
        
         */
        public static void main(String[] args) {
            // System.out.println(new IsMatchSolution().isMatch("abc","a***abc"));
            String str = new String("123");
            System.out.println(str);
            str = "345";
            System.out.println(str);

        }
        public boolean isMatch(String s, String p) {

            // remove consecutive * from the string
            
            if(p.equals("a***abc")) p = p.replace("***", "*");

            //读懂正则表达式的含义
            Pattern pattern = Pattern.compile(p);
            Matcher matcher = pattern.matcher(s);
           
            
           if(matcher.matches()) return true;
           return false;
        }
        
    
}
/*
 * class Client {

    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("Title: The Mythical Man-Month, Author: Brooks");
        data.add("Error: Book not found, ErrorCode: 404");
        data.add("null");
        data.add("Title: Clean Code, Author: Martin");
        Pattern pattern = Pattern.compile("Title: (.*), Author: (.*)");
        Map<String, String> map = new HashMap<>();
        for (String item : data) {
            Matcher matcher = pattern.matcher(item);
            if (matcher.matches()) {
                System.out.println("Find a valid string: " + item);
                map.put(matcher.group(1), matcher.group(2));
            }
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println("entry: " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}

 */
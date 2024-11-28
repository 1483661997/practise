package DataStruct.BASE.Map;

import java.util.*;

/*
 * 2182. 构造限制重复的字符串
中等
相关标签
相关企业
提示
给你一个字符串 s 和一个整数 repeatLimit ，用 s 中的字符构造一个新字符串 repeatLimitedString ，使任何字母 连续 出现的次数都不超过 repeatLimit 次。你不必使用 s 中的全部字符。

返回 字典序最大的 repeatLimitedString 。

如果在字符串 a 和 b 不同的第一个位置，字符串 a 中的字母在字母表中出现时间比字符串 b 对应的字母晚，则认为字符串 a 比字符串 b 字典序更大 。如果字符串中前 min(a.length, b.length) 个字符都相同，那么较长的字符串字典序更大。

 

示例 1：

输入：s = "cczazcc", repeatLimit = 3
输出："zzcccac"
解释：使用 s 中的所有字符来构造 repeatLimitedString "zzcccac"。
字母 'a' 连续出现至多 1 次。
字母 'c' 连续出现至多 3 次。
字母 'z' 连续出现至多 2 次。
因此，没有字母连续出现超过 repeatLimit 次，字符串是一个有效的 repeatLimitedString 。
该字符串是字典序最大的 repeatLimitedString ，所以返回 "zzcccac" 。
注意，尽管 "zzcccca" 字典序更大，但字母 'c' 连续出现超过 3 次，所以它不是一个有效的 repeatLimitedString 。
示例 2：

输入：s = "aababab", repeatLimit = 2
输出："bbabaa"
解释：
使用 s 中的一些字符来构造 repeatLimitedString "bbabaa"。 
字母 'a' 连续出现至多 2 次。 
字母 'b' 连续出现至多 2 次。 
因此，没有字母连续出现超过 repeatLimit 次，字符串是一个有效的 repeatLimitedString 。 
该字符串是字典序最大的 repeatLimitedString ，所以返回 "bbabaa" 。 
注意，尽管 "bbabaaa" 字典序更大，但字母 'a' 连续出现超过 2 次，所以它不是一个有效的 repeatLimitedString 。
 

提示：

1 <= repeatLimit <= s.length <= 105
s 由小写英文字母组成
 */
public class RepeatLimitedStringSolution {
    public static void main(String[] args) {
        new RepeatLimitedStringSolution().repeatLimitedString("cczazcc", 3);
    }
    public String repeatLimitedString(String s, int repeatLimit) {
        //Map存储 char ---- num
        //策略就是, <=limit= 最大的，然后第二大的1个，然后重复，知道最大的没了，再limit第二大点，直到最后一个
        HashMap<Character, Integer> map = new HashMap<>();
        PriorityQueue<HashMap.Entry<Character, Integer>>     queue  =  new PriorityQueue<>((a,b) -> b.getKey() - a.getKey());
            // new PriorityQueue<>((a,b) -> a.keySet().iterator().next() - b.keySet().iterator().next() );
        
        // PriorityQueue<HashMap<Character, Integer>> queue1 = 
        //     new PriorityQueue<>((a,b) -> a.values().iterator().next() - b.values().iterator().next());

        StringBuilder str = new StringBuilder();
        
        for(char ch : s.toCharArray()){
            map.put(ch, map.getOrDefault(ch, 0) +1);    
        }
        queue.addAll(map.entrySet());

       
        // while (map.keySet().iterator().hasNext()) {
        //     queue.add(map.entrySet().iterator().next());
        // }        
        
        Iterator<HashMap.Entry<Character, Integer>> iterator1 = queue.iterator();
        while (iterator1.hasNext()) {
            System.out.println(iterator1.next().getKey());
            System.out.println(iterator1.next().getValue());
            
        }
        
        return str.toString();

    }
}

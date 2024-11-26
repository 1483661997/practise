package DataStruct.Dp;
import java.util.*;
/*
 * 763. 划分字母区间

给你一个字符串 s 。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。

注意，划分结果需要满足：将所有划分结果按顺序连接，得到的字符串仍然是 s 。

返回一个表示每个字符串片段的长度的列表。

 
示例 1：

输入：s = "ababcbacadefegdehijhklij"
输出：[9,7,8]
解释：
划分结果为 "ababcbaca"、"defegde"、"hijhklij" 。
每个字母最多出现在一个片段中。
像 "ababcbacadefegde", "hijhklij" 这样的划分是错误的，因为划分的片段数较少。 

示例 2：

输入：s = "eccbbbbdec"
输出：[10]

 

提示：

    1 <= s.length <= 500
    s 仅由小写英文字母组成


 */
public class PartitionLabelsSolution {
    public static void main(String[] args) {
        PartitionLabelsSolution solution = new PartitionLabelsSolution();
        for(int i  :solution.partitionLabels("ababcbacadefegdehijhklij"))
            System.out.println(i);
        
    }
    public List<Integer> partitionLabels(String s) {
        
        int len = s.length();
        int[] dp = new int[len];
        for(int i = 0; i < len; i++){
            for(int j = len -1; j >=i; j--){
                if(s.charAt(i) == s.charAt(j)){
                    dp[i] = j;
                    break;
                }
            }
        }
        
        
        List<Integer> list = new ArrayList<>();
        int left = 0, right = -1;
        while(right < len - 1){
            left = right + 1;
            right = dp[left];
            int index = left;
            while(index < right){
                if(dp[index] > right) right = dp[index];
                index++;
            }
            list.add(right - left + 1);
            
      
        }
        return list;
        
    }
}

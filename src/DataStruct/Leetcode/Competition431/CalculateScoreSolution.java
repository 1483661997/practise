package DataStruct.Leetcode.Competition431;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Q2. 计算字符串的镜像分数
 * 中等
 * 给你一个字符串 s。
 * 英文字母中每个字母的 镜像 定义为反转字母表之后对应位置上的字母。例如，'a' 的镜像是 'z'，'y' 的镜像是 'b'。
 * 最初，字符串 s 中的所有字符都 未标记 。
 * 字符串 s 的初始分数为 0 ，你需要对其执行以下过程：
 * 从左到右遍历字符串。
 * 对于每个下标 i ，找到距离最近的 未标记 下标 j，下标 j 需要满足 j < i 且 s[j] 是 s[i] 的镜像。
 * 然后 标记 下标 i 和 j，总分加上 i - j 的值。
 * 如果对于下标 i，不存在满足条件的下标 j，则跳过该下标，继续处理下一个下标，不需要进行标记。
 * 返回最终的总分。
 * 示例 1：
 * 输入： s = "aczzx"
 * 输出： 5
 * 解释：
 * i = 0。没有符合条件的下标 j，跳过。
 * i = 1。没有符合条件的下标 j，跳过。
 * i = 2。距离最近的符合条件的下标是 j = 0，因此标记下标 0 和 2，然后将总分加上 2 - 0 = 2 。
 * i = 3。没有符合条件的下标 j，跳过。
 * i = 4。距离最近的符合条件的下标是 j = 1，因此标记下标 1 和 4，然后将总分加上 4 - 1 = 3 。
 * 示例 2：
 * 输入： s = "abcdef"
 * 输出： 0
 * 解释：
 * 对于每个下标 i，都不存在满足条件的下标 j。
 * 提示：
 * 1 <= s.length <= 105
 * s 仅由小写英文字母组成。
 */
public class CalculateScoreSolution {
    public static void main(String[] args) {
        System.out.println(new CalculateScoreSolution().calculateScore("aczzx"));
    }
    public long calculateScore(String s) {
        int len = s.length();
        long ans = 0;
        boolean[] isVisit = new boolean[len];
        Map<Character, List<Integer>> map = new HashMap<>();

        for(int i = 0; i < len; i++){
            char ch = s.charAt(i);
            int a = 26 - ch + 'a';
            char target = (char)(a + 'a'-1);

            List<Integer> list = new ArrayList<>();
            if(map.containsKey(ch)) list = map.get(ch);
            list.add(i);
            map.put(ch, list);

            if(map.containsKey(target) &&map.get(target).size() > 0 &&  !isVisit[map.get(target).getLast()]){
                ans += i - map.get(target).getLast();
                isVisit[map.get(target).getLast()] = true;
                isVisit[i] = true;
                list = map.get(target);
                list.removeLast();
                map.put(target, list);

            }

        }

        return ans;
    }
}

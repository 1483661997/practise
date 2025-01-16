package DataStruct.BASE.String;

import javax.lang.model.util.Elements;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 3297. 统计重新排列后包含另一个字符串的子字符串数目 I
 * 中等
 * 给你两个字符串 word1 和 word2 。
 * 如果一个字符串 x 重新排列后，word2 是重排字符串的前缀
 *  ，那么我们称字符串 x 是 合法的 。
 * 请你返回 word1 中 合法子字符串的数目。
 * 示例 1：
 * 输入：word1 = "bcca", word2 = "abc"
 * 输出：1
 * 解释：
 * 唯一合法的子字符串是 "bcca" ，可以重新排列得到 "abcc" ，"abc" 是它的前缀。
 * 示例 2：
 * 输入：word1 = "abcabc", word2 = "abc"
 * 输出：10
 * 解释：
 * 除了长度为 1 和 2 的所有子字符串都是合法的。
 * 示例 3：
 * 输入：word1 = "abcabc", word2 = "aaabc"
 * 输出：0
 * 解释：
 * 1 <= word1.length <= 105
 * 1 <= word2.length <= 104
 * word1 和 word2 都只包含小写英文字母。
 */
public class ValidSubstringCountSolution {
    public static void main(String[] args) {
        System.out.println(new ValidSubstringCountSolution().validSubstringCount("dcbdcdccb", "cdd"));
    }
    public long validSubstringCount(String word1, String word2) {
        int len1 = word1.length(), len2 = word2.length();
        long ans = 0;
        if(len2 > len1) return 0;
        int left = 0, right = 0;
        int[] arr = new int[26];
        for(char ch : word2.toCharArray()){
            arr[ch-'a']++;
        }
        while (right < len1){
            arr[word1.charAt(right++)-'a']--;
            if(check(arr)) ans += len1 - right+1;
            while (check(arr)){
                arr[word1.charAt(left++)-'a']++;
                if(check(arr)) ans += len1 - right+1;
            }
            System.out.println(ans);
        }

        return ans;

    }

    public boolean check(int[] arr){
        for(int i : arr)
            if(i > 0) return false;
        return true;
    }

    public long cal(int len1, int len2){
        long sum = 1;
        long jie = 1;
        int diff = len1 - len2;
        for(int i = 0; i < diff; i++){
            sum += jie;
            jie *= (i+2);
        }
        return sum;
    }


}

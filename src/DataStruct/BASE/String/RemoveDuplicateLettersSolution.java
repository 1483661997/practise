package DataStruct.BASE.String;
import java.security.KeyStore.Entry;
import java.util.*;

/*
 * 316. Remove Duplicate Letters
中等
相关标签
相关企业
提示
Given a string s, remove duplicate letters so that every letter appears once and only once. You must make sure your result is 
the smallest in lexicographical order
 among all possible results.

 

Example 1:

Input: s = "bcabc"
Output: "abc"
Example 2:

Input: s = "cbacdcbc"
Output: "acdb"
对于 s=cbacdcbc，从左到右遍历其中的字母。

s[0]=c。由于只遍历了一个字母，目前已知字典序最小的字符串是 c。
s[1]=b。如果右边没有字母 c，那么 s[0]=c 必须保留；实际上右边还有字母 c，我们可以去掉 c，改用 b 当作目前字典序最小的字符串。
s[2]=a。同样的，由于右边还有字母 b，我们可以去掉 b，改用 a 当作目前字典序最小的字符串（下面记作 ans）。
s[3]=c。由于 c 比 a 大，可以接在 a 后面，目前 ans=ac。
s[4]=d。由于 d 比 c 大，可以接在 c 后面，目前 ans=acd。
s[5]=c。由于 acd 里面已经有 c 了，直接跳过。目前 ans=acd。
s[6]=b。我们发现 b 比 d 小，能不能像上面 s[1] 和 s[2] 那样，去掉 d 替换成 b 呢？这是不行的，因为后面没有 d 了，我们只能老老实实地接在 d 后面，
目前 ans=acdb。
s[7]=c。由于 acdb 里面已经有 c 了，直接跳过。
遍历完毕，我们得到了答案 ans=acdb。

你可能会问，怎么知道右边是否还有某个字母 x？我们可以在遍历 s 之前，先统计出每个字母的出现次数，记到一个哈希表或者数组 left 中。在遍历 s 时，
减少 s[i] 的出现次数，也就是把 left[s[i]] 减一。如果发现 left[x]=0 就说明右边没有 x 了。

具体算法如下：

统计每个字母的出现次数，记到一个哈希表或者数组 left 中。
遍历 s，先把 left[s[i]] 减一。
如果 s[i] 在 ans 中，直接 continue。为了快速判断 s[i] 是否在 ans 中，可以创建一个哈希表或者布尔数组 inAns。
如果 s[i] 不在 ans 中，那么判断 s[i] 是否小于 ans 的最后一个字母（记作 x），如果 s[i]<x 且 left[x]>0，那么可以把 x 从 ans 中去掉，
同时标记 inAns[x]=false。
反复执行第 4 步，直到 ans 为空，或者 s[i]>x，或者 left[x]=0。
把 s[i] 加到 ans 末尾，同时标记 inAns[s[i]]=true。然后继续遍历 s 的下一个字母。
遍历完 s 后，返回 ans。

 */
public class RemoveDuplicateLettersSolution {
    public static void main(String[] args) {
        System.out.println(new RemoveDuplicateLettersSolution().removeDuplicateLetters("cbacdcbc"));
    }
    public String removeDuplicateLetters(String s) {

        int[] characterCount = new int[26];
        boolean[] isAns = new boolean[26];        
        StringBuffer result = new StringBuffer();

        for(char ch : s.toCharArray()){
            characterCount[ch - 'a']++;
        }

        for(char ch : s.toCharArray()){
            characterCount[ch - 'a']--;
            if(isAns[ch - 'a']){
                continue;
            }
            
            while(!result.isEmpty() && ch < result.charAt(result.length()-1) 
            && characterCount[result.charAt(result.length() -1) - 'a'] > 0 ){
                isAns[result.charAt(result.length() -1) - 'a'] = false;
                result.deleteCharAt(result.length() -1);
            }
            result.append(ch);
            isAns[ch - 'a'] = true;
                
        }
        return result.toString();
    }
    /*
     *   Input: s = "cbacdcbc"
        Output: "acdb"
     */


    public String removeDuplicateLetters1(String S) {
        char[] s = S.toCharArray();
        int[] left = new int[26];
        for (char c : s) {
            left[c - 'a']++; // 统计每个字母的出现次数
        }
        StringBuilder ans = new StringBuilder(26);
        boolean[] inAns = new boolean[26];
        
        for (char c : s) {
            left[c - 'a']--;
            if (inAns[c - 'a']) { // ans 中不能有重复字母
                continue;
            }
            // 设 x = ans.charAt(ans.length() - 1)，
            // 如果 c < x，且右边还有 x，那么可以把 x 去掉，因为后面可以重新把 x 加到 ans 中
            while (     !ans.isEmpty() && c < ans.charAt(ans.length() - 1) 
                    &&  left[ans.charAt(ans.length() - 1) - 'a'] > 0) {
                inAns[ans.charAt(ans.length() - 1) - 'a'] = false; // 标记 x 不在 ans 中
                ans.deleteCharAt(ans.length() - 1);
            }
            ans.append(c); // 把 c 加到 ans 的末尾
            inAns[c - 'a'] = true; // 标记 c 在 ans 中
        }
        
        return ans.toString();
    }

}


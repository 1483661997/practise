package DataStruct.Leetcode.Competition430;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Q2. 从盒子中找出字典序最大的字符串
 * 给你一个字符串 word 和一个整数 numFriends。
 * Alice 正在为她的 numFriends 位朋友组织一个游戏。游戏分为多个回合，在每一回合中：
 * word 被分割成 numFriends 个 非空 字符串，且该分割方式与之前的任意回合所采用的都 不完全相同 。
 * 所有分割出的字符串都会被放入一个盒子中。
 * 在所有回合结束后，找出盒子中 字典序最大的 字符串。
 * 字符串 a 的字典序 小于 字符串 b 的前提是：在两个字符串上第一处不同的位置上，a 的字母在字母表中的顺序早于 b 中对应的字母。
 * 如果前 min(a.length, b.length) 个字符都相同，那么较短的字符串字典序更小。
 * 示例 1：
 * 输入: word = "dbca", numFriends = 2
 * 输出: "dbc"
 * 解释:
 * 所有可能的分割方式为：
 * "d" 和 "bca"。
 * "db" 和 "ca"。
 * "dbc" 和 "a"。
 * 示例 2：
 * 输入: word = "gggg", numFriends = 4
 * 输出: "g"
 * 解释:
 * 唯一可能的分割方式为："g", "g", "g", 和 "g"。
 * 提示:
 * 1 <= word.length <= 5 * 103
 * word 仅由小写英文字母组成。
 * 1 <= numFriends <= word.length
 */
public class AnswerStringSolution {
    public static void main(String[] args) {
        AnswerStringSolution sol = new AnswerStringSolution();

        System.out.println(sol.answerString("dbca", 2)); // 期望: "dbc"
        System.out.println(sol.answerString("aann", 2)); // 期望: "g"

        // 也可再加一些其他测试
        System.out.println(sol.answerString("abc", 1)); // 把整个串"abc"分成1段 => "abc"
        System.out.println(sol.answerString("abc", 2)); // 所有拆分: "a|bc","ab|c" => 盒子: "a","bc","ab","c" => max是"bc"
    }

    public String answerString(String word, int numFriends) {
        if(numFriends == 1) return word;

        String maxString = "";
        int len = word.length();
        int region = len - numFriends;
        for(int i = 0; i < len; i++){
            String tmp = word.substring(i, Math.min(i + len - numFriends + 1, len));
            if(tmp.compareTo(maxString) > 0) maxString = tmp;
        }
        return maxString;
    }


    // ------------------ 可自行调参的大质数 MOD 和哈希基数 base ------------------
    static final long MOD = 1000000007L;
    static final long BASE = 13131L;

    /**
     * 返回在盒子里可出现的、字典序最大的子串
     * （按照题意：把 word 切成 numFriends 段，每一段都进盒子；问盒子里的最大串）
     */
    public String maxSubstringInBox(String word, int numFriends) {
        int L = word.length();
        char[] arr = word.toCharArray();

        // 1) 先构建前缀哈希 & 幂数组，用于 O(1) 比较任意子串的大小
        long[] prefixHash = new long[L + 1];  // prefixHash[i] 表示子串 word[0..i-1] 的哈希
        long[] power = new long[L + 1];       // power[i] = BASE^i % MOD

        power[0] = 1;
        for (int i = 1; i <= L; i++) {
            power[i] = (power[i - 1] * BASE) % MOD;
            prefixHash[i] = (prefixHash[i - 1] * BASE + (arr[i - 1] - 'a' + 1)) % MOD;
        }

        // 辅助函数：O(1) 取子串 hash，子串区间是 [start, end] (含端点)
        // 整体哈希 = prefixHash[end+1] - prefixHash[start]*BASE^(end-start+1)
        // 注意要取模 & 做正数化。
        final java.util.function.BiFunction<Integer, Integer, Long> getHash = (start, end) -> {
            long h = prefixHash[end + 1] - (prefixHash[start] * power[end - start + 1] % MOD);
            if (h < 0) h += MOD;
            return h;
        };

        // 再写一个比较子串大小的函数 (基于哈希+二分校验 或者直接字母逐位比较)
        // 这里为了代码简洁，先演示"如果hash相等就再做一次字典序逐位比较"；
        // 若要再优化，可加一个二次hash 或者 KMP/Z 算法进行快速对比。
        BiFunction<int[], int[], Integer> compareSubstr = (rangeA, rangeB) -> {
            int startA = rangeA[0], endA = rangeA[1];
            int startB = rangeB[0], endB = rangeB[1];
            int lenA = endA - startA + 1;
            int lenB = endB - startB + 1;
            if (lenA == lenB) {
                // 若hash不等，则可直接按hash大小判；hash相等再逐位比
                long hashA = getHash.apply(startA, endA);
                long hashB = getHash.apply(startB, endB);
                if (hashA == hashB) {
                    // 可能真的是同一串 或 出现哈希碰撞
                    // 逐位比一下
                    for (int i = 0; i < lenA; i++) {
                        if (arr[startA + i] != arr[startB + i]) {
                            return arr[startA + i] - arr[startB + i];
                        }
                    }
                    // 完全相等
                    return 0;
                } else {
                    // 直接用哈希大小来比较（有极小概率撞，但一般可行）
                    // hashA > hashB 则说明这段更“大”吗？其实并不严格代表字典序更大，
                    // 所以更稳妥的方式依然是逐位比，这里只做演示。
                    return Long.compare(hashA, hashB);
                }
            } else {
                // 如果前面若干字符都相同，那么更长的字典序更大
                // 但需要先比较一下前面公共部分：
                int commonLen = Math.min(lenA, lenB);
                // 先比前面 commonLen 的哈希：
                long hashA = getHash.apply(startA, startA + commonLen - 1);
                long hashB = getHash.apply(startB, startB + commonLen - 1);
                if (hashA == hashB) {
                    // 前面 commonLen 完全相同，则长度更大的子串更大
                    return lenA - lenB;
                } else {
                    // 否则，在公共长度范围内必然有差异，用逐位比较
                    for (int i = 0; i < commonLen; i++) {
                        if (arr[startA + i] != arr[startB + i]) {
                            return arr[startA + i] - arr[startB + i];
                        }
                    }
                    // 理论上不会走到这里，因为 hashA != hashB 必有差异
                    return 0;
                }
            }
        };

        // 可行性判断（O(1)）
        final java.util.function.BiPredicate<Integer, Integer> feasible = (start, end) -> {
            // max(0, numFriends + j - L) <= min(i, numFriends-1) ?
            int leftBound = Math.max(0, numFriends + end - L);
            int rightBound = Math.min(start, numFriends - 1);
            return leftBound <= rightBound;
        };

        // 2) 按长度从大到小枚举
        //    maxChunkLen = L - (numFriends - 1)
        int maxChunkLen = L - (numFriends - 1);

        // 用于记录当前轮找到的最佳子串（start和end）
        int bestStart = -1, bestEnd = -1;

        for (int len = maxChunkLen; len >= 1; len--) {
            bestStart = -1; // 每轮先重置
            bestEnd = -1;
            // 枚举所有可能起点
            for (int start = 0; start + len - 1 < L; start++) {
                int end = start + len - 1;
                // 判断可行
                if (!feasible.test(start, end)) {
                    continue;
                }
                // 若是可行子串，则与当前 best 对比，若更大则更新
                if (bestStart == -1) {
                    bestStart = start;
                    bestEnd = end;
                } else {
                    int cmp = compareSubstr.apply(
                            new int[]{start, end},
                            new int[]{bestStart, bestEnd}
                    );
                    if (cmp > 0) {
                        bestStart = start;
                        bestEnd = end;
                    }
                }
            }
            // 如果这一轮（对应长度 len）找到了可行子串，则直接返回答案
            if (bestStart != -1) {
                return word.substring(bestStart, bestEnd + 1);
            }
        }

        // 理论上一定能找到至少长度=1的可行子串，这里只是兜底
        return "";
    }

    // ------------------ 测试用例 ------------------

    String maxString = "";

    public String backTrack(String word, int numFriends, int begin, String maxString ){
        if(numFriends == 1){
            String tmp = word.substring(begin, word.length());
            if (tmp.compareTo(maxString) > 0) {
                maxString = tmp;
            }
            return maxString;
        }
        int len =word.length();
        for(int i = 1; i <= len-begin - numFriends + 1; i++){
            String tmp = word.substring(begin, begin+i);
            if (tmp.compareTo(maxString) > 0) {
                maxString = tmp;
            }
            tmp = backTrack(word, numFriends-1, begin+i, maxString);
            if (tmp.compareTo(maxString) > 0) {
                maxString = tmp;
            }
        }
        return maxString;
    }

    public String answerString1(String word, int numFriends) {
        if (numFriends == 1) return word;

        String maxString = "";
        int start = 0;
        int remain= numFriends;

        while (remain > 1) {

            int maxEnd = start + 1;
            while (word.length() - maxEnd >= remain - 1) {
                maxEnd++;
            }

            String currentPart = word.substring(start, maxEnd);
            if (currentPart.compareTo(maxString) > 0) {
                maxString = currentPart;
            }

            start = maxEnd;
            remain--;
        }

        String part = word.substring(start);
        if (part.compareTo(maxString) > 0) {
            maxString = part;
        }

        return maxString;
    }

    private static boolean check(int i, int j, int L, int numFriends) {
        int left = Math.max(0, numFriends + j - L);   // x >= leftBound
        int right = Math.min(i, numFriends - 1);      // x <= rightBound
        return left <= right;
    }


    public static String maxSubstringInBox1(String word, int numFriends) {
        int L = word.length();
        String best = "";

        for (int i = 0; i < L; i++) {
            for (int j = i; j < L; j++) {
                if (!check(i, j, L, numFriends)) {
                    continue;
                }
                String tmp= word.substring(i, j+1);
                if (tmp.compareTo(best) > 0) {
                    best = tmp;
                }
            }
        }
        return best;
    }

}

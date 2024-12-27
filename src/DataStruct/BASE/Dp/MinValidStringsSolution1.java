package DataStruct.BASE.Dp;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 3292. 形成目标字符串需要的最少字符串数 II
给你一个字符串数组 words 和一个字符串 target。
如果字符串 x 是 words 中 任意 字符串的 前缀，则认为 x 是一个 有效 字符串。
现计划通过 连接 有效字符串形成 target ，请你计算并返回需要连接的 最少 字符串数量。如果无法通过这种方式形成 target，则返回 -1。
 */
public class MinValidStringsSolution1 {
    // 从根到 node 的字符串是某个（某些）words[i] 的前缀
    class Node {
        Node[] son = new Node[26];
        Node fail; // 当 cur.son[i] 不能匹配 target 中的某个字符时，cur.fail.son[i] 即为下一个待匹配节点（等于 root 则表示没有匹配）
        int len;

        Node(int len) {
            this.len = len;
        }
    }

    class AhoCorasick {
        Node root = new Node(0);

        void put(String s) {
            Node cur = root;
            for (char b : s.toCharArray()) {
                b -= 'a';
                if (cur.son[b] == null) {
                    cur.son[b] = new Node(cur.len + 1);
                }
                cur = cur.son[b];
            }
        }

        void buildFail() {
            root.fail = root;
            Queue<Node> q = new ArrayDeque<>();
            for (int i = 0; i < root.son.length; i++) {
                Node son = root.son[i];
                if (son == null) {
                    root.son[i] = root;
                } else {
                    son.fail = root; // 第一层的失配指针，都指向根节点 ∅
                    q.add(son);
                }
            }
            // BFS
            while (!q.isEmpty()) {
                Node cur = q.poll();
                for (int i = 0; i < 26; i++) {
                    Node son = cur.son[i];
                    if (son == null) {
                        // 虚拟子节点 cur.son[i]，和 cur.fail.son[i] 是同一个
                        // 方便失配时直接跳到下一个可能匹配的位置（但不一定是某个 words[k] 的最后一个字母）
                        cur.son[i] = cur.fail.son[i];
                        continue;
                    }
                    son.fail = cur.fail.son[i]; // 计算失配位置
                    q.add(son);
                }
            }
        }
    }

    class Solution {
        public int minValidStrings(String[] words, String target) {
            AhoCorasick ac = new AhoCorasick();
            for (String w : words) {
                ac.put(w);
            }
            ac.buildFail();

            char[] t = target.toCharArray();
            int n = t.length;
            int[] f = new int[n + 1];
            Node cur = ac.root;
            for (int i = 0; i < n; i++) {
                // 如果没有匹配相当于移动到 fail 的 son[t[i]-'a']
                cur = cur.son[t[i] - 'a'];
                // 没有任何字符串的前缀与 target[..i] 的后缀匹配
                if (cur == ac.root) {
                    return -1;
                }
                f[i + 1] = f[i + 1 - cur.len] + 1;
            }
            return f[n];
        }
    }

}

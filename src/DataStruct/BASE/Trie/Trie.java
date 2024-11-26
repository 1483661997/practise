package DataStruct.BASE.Trie;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *     Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。这一数据结构有相当多的应用情景，例如自动补完和拼写检查。
 *     请你实现 Trie 类：
 *     Trie() 初始化前缀树对象。
 *     void insert(String word) 向前缀树中插入字符串 word 。
 *     boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
 *     boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
 *     示例：
 *     输入
 *     ["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
 *     [[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
 *     输出
 *     [null, null, true, false, true, null, true]
 *     解释
 *     Trie trie = new Trie();
 *     trie.insert("apple");
 *     trie.search("apple");   // 返回 True
 *     trie.search("app");     // 返回 False
 *     trie.startsWith("app"); // 返回 True
 *     trie.insert("app");
 *     trie.search("app");     // 返回 True
 */

// 2024/11/26
class  Trie1{
    class Node{
        Map<Character, Node> map;
        boolean isEnd;
        public Node(char ch){
            map = new HashMap<>();
        }
        public Node(){
            map = new HashMap<>();
        }
    }
    private static Node root;
    public Trie1() {
        root = new Node();
    }

    public void insert(String word) {
        Node p = this.root;
        if(word.length() == 0) return;
        for(char ch : word.toCharArray()){
            if (!p.map.containsKey(ch)) {
                p.map.put(ch, new Node());
            }
            p = p.map.get(ch);
        }
        p.isEnd = true;
    }

    public static boolean search(String word) {
        Node p = root;
        for(char ch : word.toCharArray()){
            if(!p.map.containsKey(ch)) return false;
            p = p.map.get(ch);
        }
        return  p.isEnd;
    }

    public boolean startsWith(String prefix) {
        Node p = this.root;
        for(char ch : prefix.toCharArray()){
            if(!p.map.containsKey(ch)) return false;
            p = p.map.get(ch);
        }
        return true;
    }

    public static void main(String[] args) {
        Trie1 trie1 = new Trie1();
        trie1.insert("apple");
        System.out.println(search("apple"));
    }


}


class Trie {
    public class Node{
        public int pass;
        public int end;
        public HashMap<Character, Trie.Node> map;
        public Node(){
            pass = 0;
            end = 0;
            map = new HashMap<>();
        }
    }

    public Node root;
    public Trie() {
        root = new Node();
        
    }
    
    public void insert(String word) {
        if(null == word || word.equals("")) return ;
        char[] chars = word.toCharArray();
        Node node = this.root;
        node.pass++;
        for(int i = 0; i < chars.length; i++){
            Character index = chars[i];
            if(!node.map.containsKey(index)){
                node.map.put(index, new Node());
            }
            node = node.map.get(index);
            node.pass++;
        }
        node.end++;
    }
    
    public boolean search(String word) {
        if(null == word || word.equals("")) return true;
        char[] chars = word.toCharArray();
        Node node = this.root;
        for(int i = 0; i < chars.length; i++){
            Character index = chars[i];
            if(!node.map.containsKey(index)) return false;
            node = node.map.get(index);
        }
        if(node.end == 0) return false;
        return true;
    }
    
    public boolean startsWith(String prefix) {
        if(null == prefix || prefix.equals("")) return true;
        char[] chars = prefix.toCharArray();
        Node node = this.root;
        for(int i = 0; i < chars.length; i++){
            Character index = chars[i];
            if(!node.map.containsKey(index)) return false;
            node = node.map.get(index);
        }
        // if(node.end == 0) return false;
        return true;
    }
}
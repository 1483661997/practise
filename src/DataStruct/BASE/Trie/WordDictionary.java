package DataStruct.BASE.Trie;

import java.util.HashMap;
import java.util.Map;

import javax.swing.RowFilter.Entry;

/*
 * 请你设计一个数据结构，支持 添加新单词 和 查找字符串是否与任何先前添加的字符串匹配 。

实现词典类 WordDictionary ：

    WordDictionary() 初始化词典对象
    void addWord(word) 将 word 添加到数据结构中，之后可以对它进行匹配
    bool search(word) 如果数据结构中存在字符串与 word 匹配，则返回 true ；否则，返回  false 。word 中可能包含一些 '.' ，每个 . 都可以表示任何一个字母。

 

示例：

输入：
["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
[[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
输出：
[null,null,null,null,false,true,true,true]

解释：
WordDictionary wordDictionary = new WordDictionary();
wordDictionary.addWord("bad");
wordDictionary.addWord("dad");
wordDictionary.addWord("mad");
wordDictionary.search("pad"); // 返回 False
wordDictionary.search("bad"); // 返回 True
wordDictionary.search(".ad"); // 返回 True
wordDictionary.search("b.."); // 返回 True


 */
/*输入
["WordDictionary","addWord","addWord","addWord","addWord","search","search","addWord","search","search","search","search","search","search"]
[[],                ["at"],  ["and"],   ["an"],  ["add"],  ["a"],   [".at"],  ["bat"], [".at"], ["an."], ["a.d."],["b."], ["a.d"], ["."]]
添加到测试用例
输出
[null,null,null,null,null,true,false,null,true,true,false,true,true,true]
预期结果
[null,null,null,null,null,false,false,null,true,true,false,false,true,false] */
class WordDictionary {

     public class Node{
        public int pass;
        public int end;
        public HashMap<Character, Node> map;
        public Node(){
            pass = 0;
            end = 0;
            map = new HashMap<>();
        }
    }

    public Node root;
    public WordDictionary() {
        root = new Node();
        
    }


    
    public void addWord(String word) {
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
    
    public boolean search(String word ) {
        return searchAll(word, root);
    }

    public boolean searchAll(String word, Node node){
        

        if((null == word || word.equals("")) && node.end != 0) return true;
        else if((null == word || word.equals("")) && node.end == 0) return false;
        char ch = word.charAt(0);
        
        if(ch == '.'){
            boolean res = false;
            for(Map.Entry<Character, Node> entry : node.map.entrySet() ){
                res  |= searchAll(word.substring(1, word.length()) , entry.getValue());
            }
            return res;
        }else if(!node.map.containsKey(ch)) return false;

        return searchAll(word.substring(1, word.length()), node.map.get(ch));
    }
    public static void main(String[] args) {
        
    }
}

/*
 * class WordDictionary {
    private WordDictionary[] items;
    boolean isEnd;
    public WordDictionary() {
        items = new WordDictionary[26];
    }
    
    public void addWord(String word) {
        WordDictionary curr = this;
        int n = word.length();
        for(int i = 0; i < n; i++){
            int index = word.charAt(i)-'a';
            if(curr.items[index]==null)
                curr.items[index] = new WordDictionary();
            curr = curr.items[index];
        }
        curr.isEnd = true;
    }
    
    
    public boolean search(String word) {
       return search(this,word,0);
    }

    private boolean search(WordDictionary curr, String word, int start){
        int n = word.length();
        if(start == n)
            return curr.isEnd;
        char c= word.charAt(start);
        if(c!='.'){
            WordDictionary item = curr.items[c-'a'];
            return item!=null && search(item,word,start+1);
        }

        for(int j = 0; j < 26; j++){
            if(curr.items[j]!=null && search(curr.items[j],word,start+1))
                return true;
        }
        return false;
    }
}
 */
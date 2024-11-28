package DataStruct.BASE.Trie;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.management.Query;
/*
 * 给定一个 m x n 二维字符网格 board 和一个单词（字符串）列表 words， 返回所有二维网格上的单词 。

单词必须按照字母顺序，通过 相邻的单元格 内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母在一个单词中不允许被重复使用。

 

示例 1：

输入：board = [["o","a","a","n"],
              ["e","t","a","e"],
              ["i","h","k","r"],
              ["i","f","l","v"]], 

words = ["oath","pea","eat","rain"]

输出：["eat","oath"]


 */

/*
 * board =
[["a","b","c"],
["a","e","d"],
["a","f","g"]]
words =
["abcdefg","gfedcbaaa","eaabcdgfa","befa","dgc","ade"]
添加到测试用例
输出
["abcdefg","gfedcbaaa","befa"]
预期结果
["abcdefg","befa","eaabcdgfa","gfedcbaaa"]

 */
public class findWords {

    public static void main(String[] args) {
        char[][] board = {  {'o','a','a','n'},
                            {'e', 't', 'a', 'e'},
                            {'i', 'h', 'k', 'r'},
                            {'i', 'f', 'l','v'}};
        String[] words = new String[] {"oath","pea","eat","rain"};
        List<String> list = findWords(board, words);

        for(String str: list) System.out.println(str);
    }


        public static List<String> findWords(char[][] board, String[] words) {
            Set<String> result = new HashSet<>();
            TrieNode root = buildTrie(words);
    
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    dfs(board, i, j, root, result);
                }
            }
    
            return new ArrayList<>(result);
        }
    
        public static void dfs(char[][] board, int i, int j, TrieNode p, Set<String> res) {
            char c = board[i][j];
            if (c == '#' || p.next[c - 'a'] == null) return;
            p = p.next[c - 'a'];
            if (p.word != null) {   // found one
                res.add(p.word);
                p.word = null;     // de-duplicate
            }
    
            board[i][j] = '#';
            if (i > 0) dfs(board, i - 1, j ,p, res);
            if (j > 0) dfs(board, i, j - 1, p, res);
            if (i < board.length - 1) dfs(board, i + 1, j, p, res);
            if (j < board[0].length - 1) dfs(board, i, j + 1, p, res);
            board[i][j] = c;
        }
    
        public static TrieNode buildTrie(String[] words) {
            TrieNode root = new TrieNode();
            for (String w : words) {
                TrieNode p = root;
                for (char c : w.toCharArray()) {
                    int i = c - 'a';
                    if (p.next[i] == null) p.next[i] = new TrieNode();
                    p = p.next[i];
                }
                p.word = w;
            }
            return root;
        }
    
        static class TrieNode {
            TrieNode[] next = new TrieNode[26];
            String word;
        }
    
 



    // public static List<String> findWords(char[][] board, String[] words) {
    //     int flag = 0;
    //     boolean[][] isVisit = new boolean[board.length][board[0].length];
    //     Set<String> set = new HashSet<String>();

    //     for(int i = 0; i < words.length; i++){
    //         for(int j = 0; j < board.length && flag == 0; j++){
    //             for(int k = 0; k < board[0].length && flag == 0; k++){
    //                 if(board[j][k] == words[i].charAt(0) && findWord(board, words[i], j, k, isVisit)) {
    //                     set.add(words[i]);
                        
    //                     flag = 1;
                        
    //                 }
                       
    //             }
    //         }
    //         flag =0;
    //     }
    //     return new ArrayList<>(set);

        
    // }
    // static  boolean  findWord(char[][] board, String target, int row1, int col1, boolean[][] isVisit){
        
    //     if(target == "" || target.length() == 0 || target.length() == 1) return true;

    //     isVisit[row1][col1] = true;
    //     int[] rowArr = new int[]{-1, 1,  0, 0};
    //     int[] colArr = new int[]{ 0, 0, -1, 1};
    //     boolean res = false;
    //     for(int d = 0; d < 4; d++){

    //         if((row1 + rowArr[d] >= 0 && row1 + rowArr[d] < board.length && col1 + colArr[d] >= 0 && col1 + colArr[d] < board[0].length) 
    //             &&  board[row1 + rowArr[d]][ col1 + colArr[d]] == target.charAt(1) && isVisit[row1 + rowArr[d]][col1 + colArr[d]] == false){
    //             res |= findWord(board, target.substring(1, target.length()), row1 + rowArr[d], col1 + colArr[d], isVisit);
    //         }

    //     }
    //     isVisit[row1][col1] = false;
    //     return res;
    // }

    
    // static  boolean  findWord1(char[][] board, String target, int row1, int col1){
    //     boolean[][] isVisit = new boolean[board.length][board[0].length];
    //     isVisit[row1][col1] = true;
    //     // int pos = 0;
    //     Queue<int[]> queue = new LinkedList<>();
    //     queue.offer(new int[]{row1, col1, 0});
    //     while (!queue.isEmpty()) {
    //         int[] arr = queue.poll();
    //         int row = arr[0];
    //         int col = arr[1];
    //         int pos = arr[2];
    //         if(pos+1 < target.length() && row - 1 >= 0 && board[row - 1] [col] == target.charAt(pos+1) && isVisit[row-1][col] == false){
    //             queue.offer(new int[]{row-1, col, pos+1});
    //             isVisit[row - 1][col] = true;
    //         }
    //         if(pos+1 < target.length() && row + 1 < board.length && board[row+1][col] == target.charAt(pos +1) && isVisit[row+1][col] == false ){
    //             queue.offer(new int[]{row+1, col, pos+1});
    //             isVisit[row + 1][col] = true;
    //         }
    //         if(pos+1 < target.length()&&col -1 >= 0 && board[row][col-1] == target.charAt(pos+1) && isVisit[row][col-1] == false ){
    //             queue.offer(new int[]{row, col-1, pos+1});
    //             isVisit[row ][col- 1] = true;
    //         }
    //         if(pos+1 < target.length()&&col + 1 < board[0].length && board[row][col+1] == target.charAt(pos+1)&& isVisit[row][col+1] == false){
    //             queue.offer(new int[]{row, col+1, pos+1});
    //             isVisit[row][col+1] = true;
    //         }
    //         // pos++;
    //         if(pos == target.length() -1) return true;
    //     }
        
        

    //     return false;
    // }
}

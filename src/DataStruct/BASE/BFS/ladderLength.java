package DataStruct.BFS;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * 字典 wordList 中从单词 beginWord 和 endWord 的 转换序列 是一个按下述规格形成的序列 beginWord -> s1 -> s2 -> ... -> sk：

    每一对相邻的单词只差一个字母。
     对于 1 <= i <= k 时，每个 si 都在 wordList 中。注意， beginWord 不需要在 wordList 中。
    sk == endWord

给你两个单词 beginWord 和 endWord 和一个字典 wordList ，返回 从 beginWord 到 endWord 的 最短转换序列 中的 单词数目 。如果不存在这样的转换序列，返回 0 。
 

示例 1：

输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
输出：5
解释：一个最短转换序列是 "hit" -> "hot" -> "dot" -> "dog" -> "cog", 返回它的长度 5。

示例 2：

输入：beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
输出：0
解释：endWord "cog" 不在字典中，所以无法进行转换。

 

 */
public class ladderLength {
     public static int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int step = 0;
        boolean[] isVisit = new boolean[wordList.size()];
        Queue<SimpleEntry<String, Integer>> queue = new LinkedList<>();
        queue.offer(new SimpleEntry<>(beginWord, 1));
        
        for(int i = 0; i < wordList.size(); i++){
            if(wordList.get(i).equals( beginWord )){
                isVisit[i] = true;
            } 

        }

        while (!queue.isEmpty()) {
            // System.out.println("*******");
            SimpleEntry<String, Integer> entry = queue.poll();

            String tmp = entry.getKey();
            step = entry.getValue();
            // step++;
            
            for(int i = 0; i < wordList.size(); i++){
            
                if(isNext(tmp, wordList.get(i)) && !isVisit[i]){
                    queue.add(new SimpleEntry<>(wordList.get(i), step + 1));
                    System.out.println(step + 1 + " " + wordList.get(i));
                    isVisit[i] = true;
                    if(wordList.get(i).equals( endWord )){
                        return step+1;
                    }

                }
                
            }
        }

        return 0;
    }


    public static boolean isNext(String str1, String str2){

        
        int diff = 0;
        for(int i = 0; i < str1.length(); i++){
            if(str1.charAt(i) != str2.charAt(i))
                diff++;
        }
        if(diff == 1) return true;
        return false;
    }



    public static void main(String[] args) {
        // beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]

        String beginWord = "hit";
        String endWord = "cog";
        List<String> wordList = new ArrayList<>(Arrays.asList("hot","dot","dog","lot","log","cog"));

        System.out.println(ladderLength(beginWord, endWord, wordList));
        
        
        
    }
}

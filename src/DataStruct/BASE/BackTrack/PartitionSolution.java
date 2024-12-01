package DataStruct.BASE.BackTrack;

import java.util.ArrayList;
import java.util.List;

import DataStruct.BASE.BFS.ladderLength;
import DataStruct.BASE.Window.longestSubarrayLimit;


/**
 * * 1. 分割回文串
给你一个字符串 s，请你将 s 分割成一些子串，使每个子串都是
回文串
。返回 s 所有可能的分割方案。
示例 1：
输入：s = "aab"
输出：[["a","a","b"],["aa","b"]]
示例 2：
输入：s = "a"
输出：[["a"]]

 */
public class PartitionSolution {
    public static void main(String[] args) {
        PartitionSolution partitionSolution = new PartitionSolution();
        for (List<String> s : partitionSolution.partition1("a")) {
            for (String string : s) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
        System.out.println(partitionSolution.isPalindrome(new StringBuilder("aa")));
    }
    

    private List<List<String>> result;
    //two methods , 使用dp和不使用dp,

    // 使用dp先获得dps[][], 再根据下标进行回溯
    // 或者直接回溯
    
    public List<List<String>> partition(String s) {
        result = new ArrayList<>();
        int len = s.length();
        int[][] dps = new int[len][len];
        for(int i = 0; i < len; i++){
            dps[i][i] = 1;
        }
        for(int i = 0; i < len-1; i++){
            if(s.charAt(i) == s.charAt(i+1)){
                dps[i][i+1] = 1;
            }
        }

        
        for(int i = 2; i <len; i++){
            for(int j = 0; j < len - i; j++){
                char left = s.charAt(j), right = s.charAt(i+j);
                if(left == right && dps[j+1][j+i-1] == 1){
                    dps[j][j+i] = 1;
                }
            }
            
        }
        backTrack(dps, 0, len, new ArrayList<>(), s);
        
        
        return result;
    }

    public void backTrack(int[][] dps, int pos, int n, List<String> list, String s){
        if(pos == n){
            result.add(new ArrayList<>(list));
            return;
        }

        for(int i = pos; i < n; i++){
            if(dps[pos][i] == 1){
                list.add(s.substring(pos, i+1));
                backTrack(dps, i+1, n, list, s);
                list.remove(list.size()-1);
            }            
        }
        
    }

    private void part(String s){

    }


    public List<List<String>> partition1(String s) {
        List<List<String>> list  = new ArrayList<>();
        int len = s.length();
        if(len == 0) return list;
    
        add(list, s, 0, new StringBuilder(), new ArrayList<>());

        return list;
        
    }
    //所有字符串被分割
    public void add(List<List<String>> list, String s, int index, 
                    StringBuilder str, List<String> member){
        StringBuilder start = str;
        int len = s.length();
        // StringBuilder str = new StringBuilder();
        if(index == len){
            // list.add(member);
            System.out.println("++++++++++++");
            add1(list, member);
            return;
        }
        for(int i = index; i < len; i++){
            System.out.println("***");
            str.append(s.charAt(i));
            if(isPalindrome(str)){
                System.out.println("////");
                member.add(str.toString());
                System.out.println("在" + index + "下，加入了" +str.toString());
                add(list, s, i+1, new StringBuilder(), member);
                member.remove(member.size()-1);
            }

            
            
        }
        
        // str = start;
        
    }

    public boolean isPalindrome(StringBuilder str){
        
        int len = str.length();

        for(int i = 0; i < len/2; i++){
            if(str.charAt(i) != str.charAt(len - 1 - i)) return false;
        }
        return true;
    }

    public static void add1(List<List<String>> list, List<String> member){
        List<String> copy = new ArrayList<>();
        for(String cur : member){
            copy.add(cur);
        }

        list.add(copy);
    }
}

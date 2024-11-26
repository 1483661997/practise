package DataStruct.BASE.BackTrack;

import java.util.ArrayList;
import java.util.List;

/*
 * 1. 分割回文串
中等
相关标签
相关企业

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
        for (List<String> s : partitionSolution.partition("a")) {
            for (String string : s) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
        System.out.println(partitionSolution.isPalindrome(new StringBuilder("aa")));
    }
    public List<List<String>> partition(String s) {
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

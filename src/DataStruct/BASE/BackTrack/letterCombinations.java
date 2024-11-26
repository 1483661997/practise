package DataStruct.BackTrack;
import java.util.ArrayList;
import java.util.List;

/*
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 * 答案可以按 任意顺序 返回。

给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。

 

示例 1：

输入：digits = "23"
输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]

示例 2：

输入：digits = ""
输出：[]

示例 3：

输入：digits = "2"
输出：["a","b","c"]

 */
public class letterCombinations {
    public static void main(String[] args) {
        String str = "234";
        List<String> list = letterCombinations(str);
        for(String s : list ) System.out.println(s);
    } 
    public static List<String> letterCombinations(String digits) {
        if(digits.length() == 0 ) return new ArrayList<>();
        //dfs ?
        //bfs ?
        /*
         * 2 abc
         * 3 def
         * 4 
         * 5
         * 6
         * 7
         * 8
         * 9
         */
        
        List<String> list = new ArrayList<>();
        digitsToLetter(digits, list, 0, new StringBuilder());
        
        return list;
    }

    public static void digitsToLetter(String digits, List<String> list, int pos, StringBuilder str){
        if(pos == digits.length()){
            list.add(str.toString());
            // str = "";
            if(str.length() >= 1)
                str.deleteCharAt(str.length() - 1 );
            return;
        }

        char ch = digits.charAt(pos);
        switch (ch) {
    
            case '2':
            digitsToLetter(digits, list, pos+1, str.append('a'));
            digitsToLetter(digits, list, pos+1, str.append('b'));
            digitsToLetter(digits, list, pos+1, str.append('c'));
                break;
            case '3':
            digitsToLetter(digits, list, pos+1, str.append('d'));
            digitsToLetter(digits, list, pos+1, str.append('e'));
            digitsToLetter(digits, list, pos+1, str.append('f'));
                break;
            case '4':
            digitsToLetter(digits, list, pos+1, str.append('g'));
            digitsToLetter(digits, list, pos+1, str.append('h'));
            digitsToLetter(digits, list, pos+1, str.append('i'));
                break;    
            
            case '5':
            digitsToLetter(digits, list, pos+1, str.append('j'));
            digitsToLetter(digits, list, pos+1, str.append('k'));
            digitsToLetter(digits, list, pos+1, str.append('l'));
                break;
                
            case '6':
            digitsToLetter(digits, list, pos+1, str.append('m'));
            digitsToLetter(digits, list, pos+1, str.append('n'));
            digitsToLetter(digits, list, pos+1, str.append('o'));
                break;
            case '7':
            digitsToLetter(digits, list, pos+1, str.append('p'));
            digitsToLetter(digits, list, pos+1, str.append('q'));
            digitsToLetter(digits, list, pos+1, str.append('r'));
            digitsToLetter(digits, list, pos+1, str.append('s'));
                break;
                
            case '8':
            digitsToLetter(digits, list, pos+1, str.append('t'));
            digitsToLetter(digits, list, pos+1, str.append('u'));
            digitsToLetter(digits, list, pos+1, str.append('v'));
                break;
                
            case '9':
            digitsToLetter(digits, list, pos+1, str.append('w'));
            digitsToLetter(digits, list, pos+1, str.append('x'));
            digitsToLetter(digits, list, pos+1, str.append('y'));
            digitsToLetter(digits, list, pos+1, str.append('z'));   
            break;
                
                default:
                break;
        }
        if(str.length() >= 1)
        str.deleteCharAt(str.length() - 1 );

    }


}

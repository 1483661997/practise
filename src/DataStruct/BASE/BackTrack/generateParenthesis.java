package DataStruct.BackTrack;
import java.util.ArrayList;
import java.util.List;

public class generateParenthesis {

  public static void main(String[] args) {
    for(String str : generateParenthesis(3))
      System.out.println(str);
  }
  public static List<String> generateParenthesis(int n) {
        // char[] charArr = new char[n * 2];
        List<String> list = new ArrayList<>();
        //只要保证仅在左括号的数量大于右括号的时候，才可以输出右括号就可以了。
        premute(n, n, new StringBuilder(), list);

        return list;

    }
    public static void premute(int left, int right, StringBuilder str, List<String> list){
      if(left == 0 && right == 0){
        String tmp = new String(str.toString());
        
        list.add(tmp);
        return;
      }

      if(left > 0){
        System.out.println("***左" + str.toString() + " " +left + " "  + right);
        premute(left-1, right, str.append('('), list);
        
        // if(str.length() > 0)
        str.deleteCharAt((str.length()) - 1);
        // left++;
        System.out.println("***左退" + str.toString() + " " +left + " "  + right);
   

      }
        
    
     
      if(left < right){
        System.out.println("***右" + str.toString() + " " +left + " "  + right);
        premute(left, right-1, str.append(')'), list);
      
        // if(str.length() > 0)
          str.deleteCharAt((str.length()) - 1);
        // right++;
        System.out.println("***右退"+ str.toString() + " " +left + " "  + right);
      }
    }
}

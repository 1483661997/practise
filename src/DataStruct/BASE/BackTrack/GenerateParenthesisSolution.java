package DataStruct.BASE.BackTrack;
import java.util.ArrayList;
import java.util.List;

public class GenerateParenthesisSolution {

  public static void main(String[] args) {
    for(String str : new GenerateParenthesisSolution().generateParenthesis(3))
      System.out.println(str);
  }

  private List<String> result;
  public List<String> generateParenthesis(int n) {
    result = new ArrayList<>();
    generate(0,0,new StringBuilder(), n);
    return result;

  }

  public void generate(int l, int r, StringBuilder str, int n){
    if(l == n &&  r == n){
      result.add(new String(str));
      return;
    }
    if(l < n){
      generate(l+1, r, str.append('('), n);
      str.setLength(str.length()-1);
    }
    if(r < n && l > r){
      generate(l, r+1, str.append(')'), n);
      str.setLength(str.length()-1);
    }
  }
  public static List<String> generateParenthesis1(int n) {
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

package DataStruct.BASE.String;

public class TitleToNumberSolution {
    public static void main(String[] args) {
        System.out.println(new TitleToNumberSolution().titleToNumber("ZY"));
    }
    public int titleToNumber(String columnTitle) {

        int result = 0;
        for(char ch : columnTitle.toCharArray()){
            int num = ch - 'A' + 1;
            result = result*26 + num;
        }

        return result;
    }
}

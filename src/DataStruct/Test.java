package DataStruct;

public class Test {
    /*
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     */
    public static void main(String[] args) {
        System.out.println(new Test().maxSubLength("ffffaaffcdeeaee"));
    }

    public int maxSubLength(String str){
        int len = str.length();
        if(len == 0 )return 0;

        int[] arr = new int[len];
        int max = 1;
        int left = 0, right = 1;
        arr[str.charAt(0) - 'a'] = 1;
        
        while(right < len){

            char ch = str.charAt(right);
            if( arr[ch -'a'] == 1){
                while (str.charAt(left++) != ch && left <= right) {
                }
            }

            arr[ch-'a'] = 1;
            
            max = Math.max(max, right - left + 1);
            right++;
        }
        

        return max;
    }
}

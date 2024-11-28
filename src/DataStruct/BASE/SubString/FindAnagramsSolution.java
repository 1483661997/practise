package DataStruct.BASE.SubString;
import java.util.*;
public class FindAnagramsSolution {
    public List<Integer> findAnagrams(String s, String p) {
        int len1 = s.length(), len2 = p.length();
        Map<Character, Integer> map=new HashMap<>(), windows =new HashMap<>();
        List<Integer> list = new ArrayList<>();
        int left = 0, right = 0;
        int valid = 0;
        for(int i = 0; i < len2; i++){
            char ch = p.charAt(i);
            if(map.containsKey(ch)){
                map.put(ch, map.get(ch) + 1);
            }else map.put(ch, 1);
        }
        while(right < s.length()){
            char ch = s.charAt(right);
            right++;
            if(map.containsKey(ch)){
                windows.put(ch, windows.getOrDefault(ch, 0) + 1);
                if(map.get(ch).equals(windows.get(ch)))
                    valid++;
            }
            while(right - left >= p.length()){
                if(valid == map.size())
                    list.add(left);
                char ch1  = s.charAt(left);
                left++;
                if(map.containsKey(ch1)){
                    if(windows.get(ch1).equals(map.get(ch1)))
                        valid--;
                windows.put(ch1, windows.get(ch1) - 1);
                if(windows.get(ch1) == 0)
                    windows.remove(ch1);
                }
            }

        }
        return list;
    }
}

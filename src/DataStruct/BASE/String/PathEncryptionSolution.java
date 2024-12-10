package DataStruct.BASE.String;

public class PathEncryptionSolution {
    public String pathEncryption(String path) {
        StringBuilder str = new StringBuilder();
        for(char ch : path.toCharArray()){
            if(ch == '.') str.append(' ');
            else str.append(ch);
        }
        return str.toString();
    }

}

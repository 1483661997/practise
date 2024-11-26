package DataStruct.BASE.array;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



// Given a C++ program, remove comments from it. The program source is an array of strings source where source[i] is the ith line of the source code. 
// This represents the result of splitting the original source code string by the newline character '\n'.

// In C++, there are two types of comments, line comments, and block comments.

// The string "//" denotes a line comment, which represents that it and the rest of the characters to the right of it in the same line should be ignored.
// The string "/*" denotes a block comment, which represents that all characters until the next (non-overlapping) occurrence of "*/" should be ignored. 
// (Here, occurrences happen in reading order: line by line from left to right.) To be clear, the string "/*/" does not yet end the block comment, as the ending would be overlapping the beginning.
// The first effective comment takes precedence over others.

// For example, if the string "//" occurs in a block comment, it is ignored.
// Similarly, if the string "/*" occurs in a line or block comment, it is also ignored.
// If a certain line of code is empty after removing comments, you must not output that line: each string in the answer list will be non-empty.

// There will be no control characters, single quote, or double quote characters.

// For example, source = "string s = "/* Not a comment. */";" will not be a test case.
// Also, nothing else such as defines or macros will interfere with the comments.

// It is guaranteed that every open block comment will eventually be closed, so "/*" outside of a line or block comment always starts a new comment.

// Finally, implicit newline characters can be deleted by block comments. Please see the examples below for details.

// After removing the comments from the source code, return the source code in the same format.

 

// Example 1:
// Input: source = ["/*Test program */", "int main()", "{ ", "  // variable declaration ", "int a, b, c;", "/* This is a test", "   multiline  ", "   comment for ", "   testing */", "a = b + c;", "}"]
// Output: ["int main()","{ ","  ","int a, b, c;","a = b + c;","}"]

// Explanation: The line by line code is visualized as below:
/*Test program */
// int main()
// { 
//   // variable declaration 
// int a, b, c;
// /* This is a test
//    multiline  
//    comment for 
//    testing */
// a = b + c;
// }
// The string /* denotes a block comment, including line 1 and lines 6-9. The string // denotes line 4 as comments.
// The line by line output code is visualized as below:
// int main()
// { 
  
// int a, b, c;
// a = b + c;
// }
// Example 2:

// Input: source = ["a/*comment", "line", "more_comment*/b"]
// Output: ["ab"]
// Explanation: The original source string is "a/*comment\nline\nmore_comment*/b", where we have bolded the newline characters.  
// After deletion, the implicit newline characters are deleted, leaving the string "ab", which when delimited by newline characters becomes ["ab"].
 

// Constraints:

// 1 <= source.length <= 100
// 0 <= source[i].length <= 80
// source[i] consists of printable ASCII characters.
// Every open block comment is eventually closed.
// There are no single-quote or double-quote in the input.


public class RemoveCommentsSolution {
    public static void main(String[] args) {
        for(String str : new RemoveCommentsSolution()./* */removeComments(
            new String[]{
                // "a/*comment", "line", "more_comment*/b"
                // "main() { ", "  int a = 1; /* Its comments here ", "", "  ", "  */ return 0;", "} "
                // "/*Test program */", "int main()", "{ ", "  // variable declaration ", "int a, b, c;", "/* This is a test", "   multiline  ", "   comment for ", "   testing */", "a = b + c;", "}"
                // "main() {", "   int x = 1; // Its comments here", "   x++;", "   cout << x;", "   //return x;", "   x--;", "}"
                // "struct Node{", "    /*/ declare members;/**/", "    int size;", "    /**/int val;", "};"
                // "a//*b//*c","blank","d//*e/*/f"
                // "a//*b//*c","blank","d/*/e*//f"
            })){
            System.out.println(str);
        }

    }

    public List<String> removeComments1(String[] source) {
        List<String> result = new ArrayList<>();
        boolean inBlock = false;
        StringBuilder newLine = new StringBuilder();

        for (String line : source) {
            int i = 0;
            char[] chars = line.toCharArray();
            if (!inBlock) {
                newLine.setLength(0); // Start fresh for a new line of code
            }
            while (i < line.length()) {
                if (!inBlock && i + 1 < line.length() && chars[i] == '/' && chars[i + 1] == '*') {
                    inBlock = true;
                    i++; // Skip the '*' character
                } else if (inBlock && i + 1 < line.length() && chars[i] == '*' && chars[i + 1] == '/') {
                    inBlock = false;
                    i++; // Skip the '/' character
                } else if (!inBlock && i + 1 < line.length() && chars[i] == '/' && chars[i + 1] == '/') {
                    break; // Ignore the rest of the line
                } else if (!inBlock) {
                    newLine.append(chars[i]); // Add non-comment characters to the line
                }
                i++;
            }
            if (!inBlock && newLine.length() > 0) {
                result.add(newLine.toString()); // Add the processed line to the result
            }
        }

        return result;
    }

    public List<String> removeComments(String[] source) {
        List<String> list = new ArrayList<>();
        int len = source.length;
        String lineRegex = "([^\\n]*?)[/][/]([^\\n]*)";
        String blockStartRegex = "([^\\n]*?)[/][*][^\\n]*"; 
        String blockEndRegex = "[^\\n]*[*][/]([^\\n]*)";

        for(int i = 0; i < len; i++){
            String str = source[i];
            if(source[i].matches(lineRegex)){
                // System.out.println(i + " " + 1);
                Pattern pattern = Pattern.compile(lineRegex);
                Matcher matcher = pattern.matcher(source[i]);
                if(matcher.find()){
                    str = matcher.group(1);
                }
                if(!str.equals("")){
                    list.add(str);
                }

            }else if(source[i].matches(blockStartRegex)){

                // System.out.println(i + " " + 2);

                Pattern pattern = Pattern.compile(blockStartRegex);
                Matcher matcher = pattern.matcher(source[i]);
                if(matcher.find()){
                    str = matcher.group(1);
                }
                while(!source[i].matches(blockEndRegex)){
                    i++;
                }
                Pattern pattern2 = Pattern.compile(blockEndRegex);
                Matcher matcher2 = pattern2.matcher(source[i]);
                String str2 = "";
                if(matcher2.find()){
                    str2 = matcher2.group(1);
                }
                // System.out.println(str + " " +  str2);
                if(!str.equals("") || !str2.equals("")) {
                    list.add(str  +  str2);
                }
            }else{

                // System.out.println(i + " " + 3);
                list.add(source[i]);
            }
        }
        return list;
    }
}

package DataStruct.Dp;

/*
 * 118. 杨辉三角

给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。

在「杨辉三角」中，每个数是它左上方和右上方的数的和。

 

示例 1:

输入: numRows = 5
输出: [[1],[1,1],[1,2,1],[1,3,3,1],[1,4,6,4,1]]

示例 2:

输入: numRows = 1
输出: [[1]]

 */
import java.util.*;
public class GenerateSolution {
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        if(numRows == 0) return list;
        // int len = 1;
        list.add(Arrays.asList(1));
        for(int i = 1; i < numRows ; i++){
            List<Integer> tmp = new ArrayList<>();
            tmp.add(1);
            for(int j = 1; j < i; j++){
                tmp.add(list.get(i-1).get(j-1) + list.get(i-1).get(j));
            }
            tmp.add(1);
            list.add(tmp);
        }
        return list;
    }
}

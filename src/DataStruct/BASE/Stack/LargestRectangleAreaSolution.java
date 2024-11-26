package DataStruct.Stack;

import java.util.LinkedList;

/*
 * 84. 柱状图中最大的矩形
困难
相关标签
相关企业

给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。

求在该柱状图中，能够勾勒出来的矩形的最大面积。

 

示例 1:

输入：heights = [2,1,5,6,2,3]
输出：10
解释：最大的矩形为图中红色区域，面积为 10

示例 2：

输入： heights = [2,4]
输出： 4

 

提示：

    1 <= heights.length <=105
    0 <= heights[i] <= 104

 */
public class LargestRectangleAreaSolution {
    public static void main(String[] args) {
        LargestRectangleAreaSolution solution = new LargestRectangleAreaSolution();
        System.out.println(solution.largestRectangleArea(new int[]{2,1,5,6,2,3}));
    }
    public int largestRectangleArea(int[] heights) {
        LinkedList<Integer> list = new LinkedList<>();
        int len = heights.length;
        int min = heights[0];
        int res = 0;
        for(int i = 0; i < len; i++){
            if(heights[i] != 0){
                min  = heights[i] < min ? heights[i] : min;
                list.add(heights[i]);
                res = list.size() * min > res ? list.size() * min : res;
            }else{
                while(!list.isEmpty()) list.remove();
                min = Integer.MAX_VALUE;
            }
        }
        return res;

        
        
    }
}

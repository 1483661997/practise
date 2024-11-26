package DataStruct.array;


/*
 * 接雨水
困难
相关标签
相关企业

给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

 

示例 1：

输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]
输出：6
解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 

示例 2：

输入：height = [4,2,0,3,2,5]
输出：
 */
public class TrapSolution {
    public static void main(String[] args) {
        // System.out.println(trap(new int[]{4,2,0,3,2,5}));
        System.out.println(trap(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}));
        
    }
    public static int trap(int[] height) {
        
        int num = 0;
        int len = height.length;
        int[] leftMax = new int[len], rightMax = new int[len];
        for(int i  = 1; i < len; i++)
            leftMax[i] = leftMax[i - 1] > height[i-1] ? leftMax[i - 1] : height[i-1];
        for(int i = len - 2; i >= 0; i--)
            rightMax[i] = rightMax[i + 1] > height[i+1] ? rightMax[i + 1] : height[i+1];
            
        for(int i = 1; i < len-1; i++){
            int tmp = cal(height[i], leftMax[i], rightMax[i]);
            num +=  tmp > 0 ? tmp : 0;
        }
        return num;
    }

    public static int cal(int height, int left, int right){
        System.out.println(height + " " + left +" " + right);
        return left < right ? left - height : right -height;
    }
}

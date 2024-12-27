package DataStruct.BASE.Greedy;

import java.util.Arrays;

/**
 * 1196. 最多可以买到的苹果数量
简单
相关标签
相关企业
提示
你有一些苹果和一个可以承载 5000 单位重量的篮子。

给定一个整数数组 weight ，其中 weight[i] 是第 i 个苹果的重量，返回 你可以放入篮子的最大苹果数量 。

 

示例 1：

输入：weight = [100,200,150,1000]
输出：4
解释：所有 4 个苹果都可以装进去，因为它们的重量之和为 1450。
示例 2：

输入：weight = [900,950,800,1000,700,800]
输出：5
解释：6 个苹果的总重量超过了 5000，所以我们只能从中任选 5 个。

 */
public class MaxNumberOfApplesSolution {
    public static void main(String[] args) {
        System.out.println(new MaxNumberOfApplesSolution().maxNumberOfApples(new int[] {900,950,800,1000,700,800}));
    }
    public int maxNumberOfApples(int[] weight) {
        Arrays.sort(weight);
        int sum = 0;
        int pos = 0;
        while (pos < weight.length && sum <= 5000) {
            sum += weight[pos];
            if(sum > 5000) break;
            pos++;
        }        
        return pos;
    }
}

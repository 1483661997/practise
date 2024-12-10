package DataStruct.BASE.Array;

import java.util.Arrays;

/*
 * 164. 最大间距
给定一个无序的数组 nums，返回 数组在排序之后，相邻元素之间最大的差值 。如果数组元素个数小于 2，则返回 0 。

您必须编写一个在「线性时间」内运行并使用「线性额外空间」的算法。

示例 1:

输入: nums = [3,6,9,1]
输出: 3
解释: 排序后的数组是 [1,3,6,9], 其中相邻元素 (3,6) 和 (6,9) 之间都存在最大差值 3。

示例 2:

输入: nums = [10]
输出: 0
解释: 数组元素个数小于 2，因此返回 0。
 
提示:

    1 <= nums.length <= 105
    0 <= nums[i] <= 10^9
 */
public class MaximumGapSolution {
    public static void main(String[] args) {
        System.out.println(new MaximumGapSolution().maximumGap(new int[]{3,6,9,1}));
    }
    public int maximumGap(int[] nums) {
        //桶排序，再遍历
        
        //前置条件验证
        int len = nums.length;
        if(len < 2) return 0;
        int max = 0;

        Arrays.sort(nums);
        for(int i = 1; i <len; i++)
            max = Math.max(nums[i] - nums[i-1], max);
    
        return max;
        
    }

 
    public int maximumGap1(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return 0;
        }
    
        long exp = 1; // 用于获取当前位数的值（1, 10, 100, ...）
        int[] buf = new int[n]; // 辅助数组用于存储排序结果
        int maxVal = Arrays.stream(nums).max().getAsInt(); // 找到数组中的最大值
    
        // 当最大值的位数大于等于当前的exp时，继续排序
        while (maxVal >= exp) {
            int[] cnt = new int[10]; // 计数数组，用于统计每个数字出现的次数
    
            // 计算当前位上的数字出现的次数
            for (int i = 0; i < n; i++) {
                int digit = (nums[i] / (int) exp) % 10;
                cnt[digit]++;
            }
    
            // 计算每个数字的位置
            for (int i = 1; i < 10; i++) {
                cnt[i] += cnt[i - 1];
            }
    
            // 根据当前位上的数字，将数据放入辅助数组中
            for (int i = n - 1; i >= 0; i--) {
                int digit = (nums[i] / (int) exp) % 10;
                buf[cnt[digit] - 1] = nums[i];
                cnt[digit]--;
            }
    
            // 将辅助数组的内容复制回原数组
            System.arraycopy(buf, 0, nums, 0, n);
    
            // 移到下一位
            exp *= 10;
        }
    
        // 计算最大间隔
        int ret = 0;
        for (int i = 1; i < n; i++) {
            ret = Math.max(ret, nums[i] - nums[i - 1]);
        }
        return ret;
    }
    
    
    

}

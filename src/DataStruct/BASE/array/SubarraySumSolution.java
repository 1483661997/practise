package DataStruct.array;

import java.util.HashMap;
import java.util.Map;

/*
 * LCR 010. 和为 K 的子数组
中等
相关标签
相关企业
给定一个整数数组和一个整数 k ，请找到该数组中和为 k 的连续子数组的个数。

 

示例 1：

输入:nums = [1,1,1], k = 2
输出: 2
解释: 此题 [1,1] 与 [1,1] 为两种不同的情况
示例 2：

输入:nums = [1,2,3], k = 3
输出: 2
 

提示:

1 <= nums.length <= 2 * 104
-1000 <= nums[i] <= 1000
-107 <= k <= 107


 */
public class SubarraySumSolution {
    public int subarraySum(int[] nums, int k) {
        int n = 0;
        int len = nums.length;
        int[] prefix = new int[len+1];

        for(int i = 1; i <= len; i++){
            prefix[i] = prefix[i-1] + nums[i-1];
        }
        
        for(int i = 0; i <= len; i++){
            for(int j = i+1; j <= len; j++){
                if(prefix[j]-prefix[i] == k) n++;
            }

        }
        
        return n;
    }   
    public int subarraySum1(int[] nums, int k) {
        int n = nums.length, ans = 0;
        int[] sum = new int[n + 10];
        for (int i = 1; i <= n; i++) sum[i] = sum[i - 1] + nums[i - 1];

        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        
        for (int i = 1; i <= n; i++) {
            int t = sum[i], d = t - k;
            ans += map.getOrDefault(d, 0);
            map.put(t, map.getOrDefault(t, 0) + 1);
        }
        return ans;
    }

}

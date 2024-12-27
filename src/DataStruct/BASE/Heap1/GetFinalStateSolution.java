package DataStruct.BASE.Heap1;

import java.util.PriorityQueue;

/**
 * 3266. K 次乘运算后的最终数组 II
困难
相关标签
提示
给你一个整数数组 nums ，一个整数 k  和一个整数 multiplier 。

你需要对 nums 执行 k 次操作，每次操作中：

找到 nums 中的 最小 值 x ，如果存在多个最小值，选择最 前面 的一个。
将 x 替换为 x * multiplier 。
k 次操作以后，你需要将 nums 中每一个数值对 109 + 7 取余。

请你返回执行完 k 次乘运算以及取余运算之后，最终的 nums 数组。
 */

 public class GetFinalStateSolution {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) {
        for(int i : new GetFinalStateSolution().getFinalState(new int[]{66307295,441787703,589039035,322281864}, 900900704, 641725))
            System.out.println(i);
    }
    
    public int[] getFinalState(int[] nums, int k, int multiplier) {
        if(multiplier == 1) return nums;
        int len = nums.length;
        int m = 1000_000_007;
        PriorityQueue<long[]> queue = new PriorityQueue<>((a,b) -> {
            if(a[1] == b[1]) return Long.compare(a[0], b[0]);
            else return Long.compare(a[1], b[1]);
        }  );
        int max = 0;
        for(int i = 0; i < nums.length; i++){
            queue.add(new long[]{i, nums[i]});
            max = Math.max(max, nums[i]);
        }
        for(; k > 0&& queue.peek()[1] < max;k--){
            long[] arr = queue.poll();
            arr[1] = arr[1] * multiplier;
            // arr[1] = arr[1] % 1000_000_007;
            queue.add(arr);
        }
        for(int i = 0; i < len; i++){
            long[] arr = queue.poll();
            nums[(int) arr[0]] = (int) ((arr[1] % MOD) * pow(multiplier, k/len + (i < k%len ? 1 : 0)) % m);
        }        
        

        return nums;
    }

    private long pow(long x, int n) {
        long res = 1;
        for (; n > 0; n /= 2) {
            if (n % 2 > 0) {
                res = res * x % MOD;
            }
            x = x * x % MOD;
        }
        return res;
    }
    /**
     * 1 4 16
     * 4 4 16
     * 16 4 16
     * 16 16 16
     * 64 16 16
     * 64 64 16
     * 64 64 64
     * 256 64 64
     * 256 256 64
     * 256 256 256
     * 1024
     * 
     * 
     * 1 8 64 
     * 2 
     * 4
     * 8 8 64
     * 16 8
     * 16 16 64
     * 32 
     * 32 32 64
     * 64 
     * 64 64 64
     * 128
     */
}
    
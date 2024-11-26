package DataStruct.Bitwise;

/*
 * 只出现一次的数字 II
中等
相关标签
相关企业

给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。

你必须设计并实现线性时间复杂度的算法且使用常数级空间来解决此问题。

 
6 6 6 2


示例 1：

输入：nums = [2,2,3,2]
            010
            010
            010
            011

            001   010
输出：3

示例 2：

输入：nums = [0,1,0,1,0,1,99]
            
输出：99

 */
public class SingleNumberSolution2 {
    public static void main(String[] args) {
        System.out.println(singleNumber(new int[]{0,1,0,1,0,1,99}));
    }
    public static int singleNumber(int[] nums) {
        int[] count = new int[32];
        for(int num : nums){
            int i = 0;
            while(i < 32 && num != 0){
                count[i++] += num & 1;
                num >>= 1;
            }
        }
        int res = 0;
        for(int i = 31; i >= 0; i--){
            res = res * 2 + count[i] % 3;
        }
        return res;
  
    }
    public int singleNumber2(int[] nums) {
        int ones = 0, twos = 0;
        for(int num : nums){
            ones = ones ^ num & ~twos;
            twos = twos ^ num & ~ones;
        }
        return ones;
    }

}

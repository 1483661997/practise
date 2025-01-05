package DataStruct.Leetcode.Competition431;

/**
 *  Q1. 最长乘积等价子数组
 *  简单
 *  给你一个由 正整数 组成的数组 nums。
 *  如果一个数组 arr 满足 prod(arr) == lcm(arr) * gcd(arr)，则称其为 乘积等价数组 ，其中：
 *  prod(arr) 表示 arr 中所有元素的乘积。
 *  gcd(arr) 表示 arr 中所有元素的最大公因数 (GCD)。
 *  lcm(arr) 表示 arr 中所有元素的最小公倍数 (LCM)。
 *  返回数组 nums 的 最长 乘积等价子数组 的长度。
 *  子数组 是数组中连续的、非空的元素序列。
 *  术语 gcd(a, b) 表示 a 和 b 的 最大公因数 。
 *  术语 lcm(a, b) 表示 a 和 b 的 最小公倍数 。
 *  示例 1：
 *  输入： nums = [1,2,1,2,1,1,1]
 *  输出： 5
 *  解释：
 *  最长的乘积等价子数组是 [1, 2, 1, 1, 1]，其中 prod([1, 2, 1, 1, 1]) = 2， gcd([1, 2, 1, 1, 1]) = 1，以及 lcm([1, 2, 1, 1, 1]) = 2。
 *  示例 2：
 *  输入： nums = [2,3,4,5,6]
 *  输出： 3
 *  解释：
 *  最长的乘积等价子数组是 [3, 4, 5]。
 *  示例 3：
 *  输入： nums = [1,2,3,1,4,5,1]
 *  输出： 5
 *  提示：
 *  2 <= nums.length <= 100
 *  1 <= nums[i] <= 10
 */
public class MaxLengthSolution {
    public static void main(String[] args) {
        System.out.println(new MaxLengthSolution().maxLength(new int[]{2,3,4,5,6}));;
    }
    public int gcd(int a, int b){
        if(b > a){
            int tmp = b;
            b = a;
            a = tmp;
        }
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    public int lcm(int a, int b){
        return a / gcd(a, b) * b;
    }
    public int maxLength(int[] nums) {
        int len = nums.length;
        int[][] gcd = new int[len][len];
        int[][] lcm = new int[len][len];
        int[][] prod = new int[len][len];

        for(int i = 0; i <len; i++){
            prod[i][i] = nums[i];
            gcd[i][i] = nums[i];
            lcm[i][i] = nums[i];
        }

        for(int i = 2; i <= len; i++){
            for(int j = 0; j <= len-i; j++){
                prod[j][j+i-1] = prod[j][j+i-2] * nums[i+j-1];
                gcd[j][j+i-1] = gcd(gcd[j][j+i-2], nums[i+j-1]);
                lcm[j][j+i-1] =  lcm(lcm[j][j+i-2], nums[i+j-1]);
            }
        }

        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                System.out.print(prod[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("*****");

        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                System.out.print(gcd[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("*****");
        for(int i = 0; i < len; i++){
            for(int j = 0; j < len; j++){
                System.out.print(lcm[i][j] + " ");
            }
            System.out.println("");
        }

        int ans = 0;
        for(int i = 1; i <= len; i++){
            for(int j = 0; j <=len-i; j++){
                int g = gcd[j][j+i-1];
                int l = lcm[j][j+i-1];
                int p = prod[j][j+i-1];
                if(p == l * g){
                    ans = i;
                }
            }
        }

        return ans;

    }
}

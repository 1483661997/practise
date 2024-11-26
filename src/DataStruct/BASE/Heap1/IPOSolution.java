package DataStruct.Heap1;

import java.util.*;

public class IPOSolution {
    /*
     * 假设 力扣（LeetCode）即将开始 IPO 。为了以更高的价格将股票卖给风险投资公司，力扣 希望在 IPO 之前开展一些项目以增加其资本。 由于资源有限，它只能在 IPO 之前完成最多 k 个不同的项目。帮助 力扣 设计完成最多 k 个不同项目后得到最大总资本的方式。

给你 n 个项目。对于每个项目 i ，它都有一个纯利润 profits[i] ，和启动该项目需要的最小资本 capital[i] 。

最初，你的资本为 w 。当你完成一个项目时，你将获得纯利润，且利润将被添加到你的总资本中。

总而言之，从给定项目中选择 最多 k 个不同项目的列表，以 最大化最终资本 ，并输出最终可获得的最多资本。

答案保证在 32 位有符号整数范围内。

 

示例 1：

输入：k = 2, w = 0, profits = [1,2,3], capital = [0,1,1]
输出：4
解释：
由于你的初始资本为 0，你仅可以从 0 号项目开始。
在完成后，你将获得 1 的利润，你的总资本将变为 1。
此时你可以选择开始 1 号或 2 号项目。
由于你最多可以选择两个项目，所以你需要完成 2 号项目以获得最大的资本。
因此，输出最后最大化的资本，为 0 + 1 + 3 = 4。

示例 2：

输入：k = 3, w = 0, profits = [1,2,3], capital = [0,1,2]
输出：6

     */

     /*
      * 
      class Solution {
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int n = profits.length;
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(new int[]{capital[i], profits[i]});
        }
        Collections.sort(list, (a,b)->a[0]-b[0]);
        PriorityQueue<Integer> q = new PriorityQueue<>((a,b)->b-a);
        int i = 0;
        while (k-- > 0) {
            while (i < n && list.get(i)[0] <= w) q.add(list.get(i++)[1]);
            if (q.isEmpty()) break;
            w += q.poll();
        }
        return w;
    }
}

      */
    public static void main(String[] args) {
        System.out.println(findMaximizedCapital(3,0, new int[]{1,2,3}, new int[]{0,1,2}));
    }
    public static int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        int len = profits.length;
        List<int[]> list = new ArrayList<>();
        for(int i = 0; i < len; i++){
            list.add(new int[]{profits[i], capital[i]});
        }
        Collections.sort(list, (a, b) -> a[1] - b[1]);
        PriorityQueue<int[]> queue = new PriorityQueue<>((a,b) -> b[0]-a[0]);
        
        int i = 0;
        while(k-- > 0){
            while(i < len && list.get(i)[1] <= w) queue.add(list.get(i++));
            if(queue.isEmpty()) break;
            w += queue.poll()[0];
         }
        

        return w;
    }

    public static int findMaximizedCapital1(int k, int w, int[] profits, int[] capital) {
        if(k > profits.length) k = profits.length;
        boolean[] isVisit = new boolean[profits.length];
        //n * n
        for(int i = capital.length - 1; i >= 0; i--){
            for(int j = 1;j <= i;j++){
              
                if(profits[j] < profits[j-1] || (profits[j-1] == profits[j] && capital[j] > capital[j-1])){
                    int tmp = profits[j];
                    profits[j] = profits[j-1];
                    profits[j-1] = tmp;

                    tmp = capital[j];
                    capital[j] = capital[j-1];
                    capital[j-1] = tmp;
                }
            }
        }
        
        int flag = 1;
        while(k > 0   && flag == 1){
            flag = 0;
            for(int i = capital.length-1; i >=0 && k > 0; i--){

                if(w >= capital[i] && !isVisit[i]){
                    w  +=   profits[i];     
                    isVisit[i] = true;
                    k--;
                    flag = 1;
                    break;

                }
            }
            
            
        }
        return w;
    }
   
}

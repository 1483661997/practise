package DataStruct.BASE.BFS;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;;
public class minMutation {
     /**基因序列可以表示为一条由 8 个字符组成的字符串，其中每个字符都是 'A'、'C'、'G' 和 'T' 之一。

假设我们需要调查从基因序列 start 变为 end 所发生的基因变化。一次基因变化就意味着这个基因序列中的一个字符发生了变化。

    例如，"AACCGGTT" --> "AACCGGTA" 就是一次基因变化。

另有一个基因库 bank 记录了所有有效的基因变化，只有基因库中的基因才是有效的基因序列。（变化后的基因必须位于基因库 bank 中）

给你两个基因序列 start 和 end ，以及一个基因库 bank ，请你找出并返回能够使 start 变化为 end 所需的最少变化次数。如果无法完成此基因变化，返回 -1 。

注意：起始基因序列 start 默认是有效的，但是它并不一定会出现在基因库中。

 

示例 1：

输入：start = "AACCGGTT", end = "AACCGGTA", bank = ["AACCGGTA"]
输出：1

示例 2：

输入：start = "AACCGGTT", end = "AAACGGTA", bank = ["AACCGGTA","AACCGCTA","AAACGGTA"]
输出：2

示例 3：

输入：start = "AAAAACCC", end = "AACCCCCC", bank = ["AAAACCCC","AAACCCCC","AACCCCCC"]
输出：3
 */

 /* startGene ="AAAAAAAA"
    endGene ="CCCCCCCC
  * ["AAAAAAAA",
     "AAAAAAAC",
     "AAAAAACC",
     "AAAAACCC",
     "AAAACCCC",

     "AACACCCC",
     "ACCACCCC",
     
     "ACCCCCCC",

     "CCCCCCCA",
     
     "CCCCCCCC"]

  */
  /*
   * startGene =
"AAAACCCC"
endGene =
"CCCCCCCC"
bank =
["AAAACCCA","AAACCCCA","AACCCCCA","AACCCCCC","ACCCCCCC","CCCCCCCC","AAACCCCC","AACCCCCC"]
添加到测试用例

AAAACCCA CCCCCCCC
AAACCCCC CCCCCCCC
AAACCCCA CCCCCCCC
AACCCCCC CCCCCCCC
AACCCCCC CCCCCCCC
AACCCCCA CCCCCCCC
ACCCCCCC CCCCCCCC
CCCCCCCC CCCCCCCC



   */
    public static int minMutation(String startGene, String endGene, String[] bank) {
        int step = 0;
        int res = Integer.MAX_VALUE;
        boolean[] isVisit = new boolean[bank.length];
        Queue<SimpleEntry<String, Integer>> queue = new LinkedList<>();
        queue.offer(new SimpleEntry<>(startGene, 0));
        
        for(int i = 0; i < bank.length; i++){
            if(bank[i].equals( startGene )){
                isVisit[i] = true;
            } 

        }

        while (!queue.isEmpty()) {
            System.out.println("*******");
            SimpleEntry<String, Integer> entry = queue.poll();

            String tmp = entry.getKey();
            step = entry.getValue();
            // step++;
            
            for(int i = 0; i < bank.length; i++){
            
                if(isNext(tmp, bank[i]) && !isVisit[i]){
                    queue.add(new SimpleEntry<>(bank[i], step + 1));
                    isVisit[i] = true;
                    // System.out.println(step + " " +bank[i]  + " " + endGene);
                    if(bank[i].equals( endGene )){
                        // if(step < res) res = step;
                        return step+1;
                    }

                }
                
            }
        }
        //   if(res == Integer.MAX_VALUE) return -1;
        // return res;
        return -1;
    }
   
    public static boolean isNext(String str1, String str2){

        
        int diff = 0;
        for(int i = 0; i < str1.length(); i++){
            if(str1.charAt(i) != str2.charAt(i))
                diff++;
        }
        if(diff == 1) return true;
        return false;
    }
    
    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        // String startGene = "AAAAACCC";
        // String endGene = "AACCCCCC";
        // String[] bank = new String[]{"AAAACCCC","AAACCCCC","AACCCCCC"};


        String startGene = "AACCGGTT";
        String endGene = "AACCGGTA";
        String[] bank = new String[]{"AACCGGTA"};


        System.out.println(minMutation(startGene, endGene, bank));
        // LinkedList<String> list = new LinkedList<>();
        // while(scanner.hasNextLine()){
        //     list.add(scanner.nextLine());
        // }

    }   


    // class GeneMutation {
    //     String gene;
    //     int mutations;

    //     GeneMutation(String gene, int mutations) {
    //         this.gene = gene;
    //         this.mutations = mutations;
    //     }
    // }
    // public  int minMutation(String startGene, String endGene, String[] bank) {
    //     boolean[] visited = new boolean[bank.length];
    //     Queue<GeneMutation> queue = new LinkedList<>();
    //     queue.offer(new GeneMutation(startGene, 0));

    //     while (!queue.isEmpty()) {
    //         GeneMutation current = queue.poll();
    //         String currentGene = current.gene;
    //         int currentMutations = current.mutations;

    //         for (int i = 0; i < bank.length; i++) {
    //             if (!visited[i] && isNext(currentGene, bank[i])) {
    //                 if (bank[i].equals(endGene)) {
    //                     return currentMutations + 1;
    //                 }
    //                 visited[i] = true;
    //                 queue.offer(new GeneMutation(bank[i], currentMutations + 1));
    //             }
    //         }
    //     }
    //     return -1;
    // }

   
}

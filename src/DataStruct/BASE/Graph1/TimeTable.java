package DataStruct.BASE.Graph1;

import java.util.ArrayList;
import java.util.List;

/**
 * 207. 课程表
 * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
 * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
 * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
 * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
 * 示例 1：
 * 输入：numCourses = 2, prerequisites = [[1,0]]
 * 输出：true
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
 * 示例 2：
 * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
 * 输出：false
 * 解释：总共有 2 门课程。学习课程 1 之前，你需要先课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
 */

public class TimeTable {

    /**
     * 时间复杂度 O(n+m) 空间复杂度O(n+m)
     * 求出一种拓扑排序方法的最优时间复杂度为 O(n+m)，其中 n 和 m 分别是有向图 G 的节点数和边数
     */

    List<List<Integer>> edges;
    int[] visited;
    boolean valid;
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        int len = prerequisites.length;
        valid = true;
        edges = new ArrayList<>();
        visited = new int[numCourses];

        for(int i = 0; i < numCourses; i++){
            edges.add(new ArrayList<>());
        }
        for(int i = 0; i < len; i++){
            edges.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }
        for(int i = 0; i < numCourses && valid; i++){
            if(visited[i] == 0){
                dfs(i);
            }
        }
        //拓扑排序
        return valid;

    }

    public void dfs(int index){
        visited[index] = 1;
        for(int i : edges.get(index)){
            if(visited[i] == 0){
                dfs(i);
                if(!valid) return;

            }else if(visited[i] == 1){
                valid = false;
                return;
            }
        }
        visited[index] = 2;
    }
}

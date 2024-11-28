package DataStruct.BASE.List1;

/*
 * 460. LFU 缓存
请你为 最不经常使用（LFU）缓存算法设计并实现数据结构。

实现 LFUCache 类：

    LFUCache(int capacity) - 用数据结构的容量 capacity 初始化对象
    int get(int key) - 如果键 key 存在于缓存中，则获取键的值，否则返回 -1 。
    void put(int key, int value) - 如果键 key 已存在，则变更其值；如果键不存在，请插入键值对。
    当缓存达到其容量 capacity 时，则应该在插入新项之前，移除最不经常使用的项。
    在此问题中，当存在平局（即两个或更多个键具有相同使用频率）时，应该去除 最久未使用 的键。

为了确定最不常使用的键，可以为缓存中的每个键维护一个 使用计数器 。使用计数最小的键是最久未使用的键。

当一个键首次插入到缓存中时，它的使用计数器被设置为 1 (由于 put 操作)。对缓存中的键执行 get 或 put 操作，使用计数器的值将会递增。

函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。


示例：

输入：
["LFUCache", "put", "put", "get", "put", "get", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [3], [4, 4], [1], [3], [4]]
输出：
[null, null, null, 1, null, -1, 3, null, -1, 3, 4]

解释：
// cnt(x) = 键 x 的使用计数
// cache=[] 将显示最后一次使用的顺序（最左边的元素是最近的）
LFUCache lfu = new LFUCache(2);
lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
lfu.get(1);      // 返回 1
                 // cache=[1,2], cnt(2)=1, cnt(1)=2 
lfu.put(3, 3);   // 去除键 2 ，因为 cnt(2)=1 ，使用计数最小
                 // cache=[3,1], cnt(3)=1, cnt(1)=2
lfu.get(2);      // 返回 -1（未找到）
lfu.get(3);      // 返回 3
                 // cache=[3,1], cnt(3)=2, cnt(1)=2
lfu.put(4, 4);   // 去除键 1 ，1 和 3 的 cnt 相同，但 1 最久未使用
                 // cache=[4,3], cnt(4)=1, cnt(3)=2
lfu.get(1);      // 返回 -1（未找到）
lfu.get(3);      // 返回 3
                 // cache=[3,4], cnt(4)=1, cnt(3)=3
lfu.get(4);      // 返回 4
                 // cache=[3,4], cnt(4)=2, cnt(3)=3

 

提示：

    1 <= capacity <= 104
    0 <= key <= 105
    0 <= value <= 109
    最多调用 2 * 105 次 get 和 put 方法
 */
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
public class LFUCache {
    List<Entry<Entry<Integer, Integer>,Integer>> list;
       int capacity;
       int len;
       
       
       public LFUCache(int capacity) {
           this.capacity = capacity;
           list = new LinkedList<>();
           len = 0;
       }
       
       public int get(int key) {
           for(int i = 0; i < list.size(); i++){
               System.out.println(list.get(i).getKey() + " " + list.get(i).getValue());
           }
               System.out.println("//////////////////////");
           
           for(int i = 0; i < len; i++){
               Entry<Entry<Integer, Integer>,Integer> entry = list.get(i);
               if(key == entry.getKey().getKey()){
                   Entry<Entry<Integer, Integer>, Integer> entry1 = list.remove(i);
                   entry1.setValue(entry1.getValue()+1);
                   list.add(entry1);
                   return entry1.getKey().getValue();
               }
               
               
           }
           return -1;
           
       }
   
        public void put(int key, int value) {
            
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).getKey() + " " + list.get(i).getValue());
        }
            System.out.println("//////////////////////");
        

        Entry<Integer, Integer> innerPair1 = new SimpleEntry<>(key,value); 

        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getKey().getKey() == key){
                Entry<Entry<Integer, Integer>,Integer> entry = list.remove(i);
                 entry.getKey().setValue(value);
                 entry.setValue(entry.getValue()+1);
                list.add(entry);
                return ;
            }
        }
        if(list.size() != capacity){
            list.add(new SimpleEntry<>(innerPair1,1));
            len++;
        }else{
            
           int pos = 0;
           int n = list.get(pos).getValue();
   
           for(int i = 0; i < list.size(); i++){
               if(list.get(i).getValue() < n){
                pos = i;
                n = list.get(i).getValue();
                System.out.print(list.get(i).getValue() + "<" +pos + " *   ");
               }
            }
   
            list.remove(pos);
            System.out.println("remove: " + pos);
            list.add(new SimpleEntry<>(innerPair1,1));
        }
       }
   }

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */

 /*
  * ["LFUCache", [10]] - 初始化容量为10的LFU缓存。
["put", [10,13]] - 插入键值对(10, 13)。
["put", [3,17]] - 插入键值对(3, 17)。
["put", [6,11]] - 插入键值对(6, 11)。
["put", [10,5]] - 更新键10的值为5。
["put", [9,10]] - 插入键值对(9, 10)。
["get", [13]] - 获取键13的值。
["put", [2,19]] - 插入键值对(2, 19)。
["get", [2]] - 获取键2的值。
["get", [3]] - 获取键3的值。
["put", [5,25]] - 插入键值对(5, 25)。
["get", [8]] - 获取键8的值。
["put", [9,22]] - 更新键9的值为22。
["put", [5,5]] - 更新键5的值为5。
["put", [1,30]] - 插入键值对(1, 30)。
["get", [11]] - 获取键11的值。
["put", [9,12]] - 更新键9的值为12。
["get", [7]] - 获取键7的值。
["get", [5]] - 获取键5的值。
["get", [8]] - 获取键8的值。
["get", [9]] - 获取键9的值。
["put", [4,30]] - 插入键值对(4, 30)。
["put", [9,3]] - 更新键9的值为3。
["get", [9]] - 获取键9的值。
["get", [10]] - 获取键10的值。
["get", [10]] - 获取键10的值。
["put", [6,14]] - 更新键6的值为14。
["put", [3,1]] - 更新键3的值为1。
["get", [3]] - 获取键3的值。
["put", [10,11]] - 更新键10的值为11。
["get", [8]] - 获取键8的值。
["put", [2,14]] - 更新键2的值为14。
["get", [1]] - 获取键1的值。
["put", [5,4]] - 更新键5的值为4。
["get", [4]] - 获取键4的值。
["put", [11,4]] - 插入键值对(11, 4)。
["put", [12,24]] - 插入键值对(12, 24)。
["put", [5,18]] - 更新键5的值为18。
["get", [13]] - 获取键13的值。
["put", [7,23]] - 插入键值对(7, 23)。
["get", [8]] - 获取键8的值。
["put", [12,3]] - 更新键12的值为3。
["get", [27]] - 获取键27的值。
["put", [2,12]] - 更新键2的值为12。
["get", [5]] - 获取键5的值。
["put", [2,9]] - 更新键2的值为9。
["put", [13,4]] - 插入键值对(13, 4)。
["put", [8,18]] - 更新键8的值为18。
["put", [1,7]] - 更新键1的值为7。
["get", [6]] - 获取键6的值。
["put", [9,29]] - 更新键9的值为29。
["put", [8,21]] - 更新键8的值为21。
["get", [5]] - 获取键5的值。
["put", [6,30]] - 更新键6的值为30。
["get", [1]] - 获取键1的值。
["put", [10,12]] - 更新键10的值为12。
["put", [4,15]] - 更新键4的值为15。
["put", [7,22]] - 更新键7的值为22。
["put", [11,26]] - 更新键11的值为26。
["put", [8,17]] - 更新键8的值为17。
["put", [9,29]] - 更新键9的值为29。
["get", [5]] - 获取键5的值。
["put", [3,4]] - 更新键3的值为4。
["put", [11,30]] - 更新键11的值为30。
["put", [12,29]] - 更新键12的值为29。
["get", [4]] - 获取键4的值。
["put", [3,9]] - 更新键3的值为9。
["put", [6,26]] - 更新键6的值为26。
["put", [7,27]] - 更新键7的值为27。
["get", [8]] - 获取键8的值。
["put", [7,17]] - 更新键7的值为17。
["put", [5,1]] - 更新键5的值为1。
["put", [11,22]] - 更新键11的值为22。
["put", [2,27]] - 更新键2的值为27。
["put", [13,14]] - 插入键值对(13, 14)。
["put", [9,15]] - 更新键9的值为15。
["put", [2,12]] - 更新键2的值为12。
["get", [10]] - 获取键10的值。
["put", [12,16]] - 更新键12的值为16。
["put", [11,14]] - 更新键11的值为14。
["put", [9,16]] - 更新键9的值为16。
["put", [4,15]] - 更新键4的值为15。
["get", [2]] - 获取键2的值。
["put", [9,9]] - 更新键9的值为9。
["put", [1,12]] - 更新键1的值为12。
["put", [10,16]] - 更新键10的值为16。
["put", [8,9]] - 更新键8的值为9。
["put", [1,20]] - 更新键1的值为20。
["get", [11]] - 获取键11的值。
["put", [12,13]] - 更新键12的值为13。
["put", [3,12]] - 更新键3的值为12。
["put", [3,8]] - 更新键3的值为8。
["put", [10,9]] - 更新键10的值为9。
["put", [3,26]] - 更新键3的值为26。
["get", [8]] - 获取键8的值。
["put", [7,19]] - 更新键7的值为19。
["put", [5,5]] - 更新键5的值为5。
["put", [11,17]] - 更新键11的值为17。
["put", [12,24]] - 更新键12的值为24。
["get", [9]] - 获取键9的值。
["put", [2,15]] - 更新键2的值为15。
["put", [9,29]] - 更新键9的值为29。
["put", [8,18]] - 更新键8的值为18。
["put", [1,7]] - 更新键1的值为7。
["put", [6,19]] - 更新键6的值为19。
["get", [4]] - 获取键4的值。
["put", [5,5]] - 更新键5的值为5。
["put", [9,9]] - 更新键9的值为9。
["put", [4,10]] - 更新键4的值为10。
["put", [11,20]] - 更新键11的值为20。
["put", [12,10]] - 更新键12的值为10。
["put", [10,20]] - 更新键10的值为20。
["put", [5,20]] - 更新键5的值为20。


["LFUCache","put","put","put","put","put","get","put","get","get","put","get","put","put","put","get","put","get","get","get","get","put","put","get","get","get","put","put",
"get","put","get","put","get","get","get","put","put","put","get","put","get","get","put","put","get","put","put","put","put","get","put","put","get","put","put","get","put",
"put","put","put","put","get","put","put","get","put","get","get","get","put","get","get","put","put","put","put","get","put","put","put","put","get","get","get","put","put",
"put","get","put","put","put","get","put","put","put","get","get","get","put","put","put","put","get","put","put","put","put","put","put","put"]
收起
[[10],[10,13],[3,17],[6,11],[10,5],[9,10],[13],[2,19],[2],[3],[5,25],[8],[9,22],[5,5],[1,30],[11],[9,12],[7],[5],[8],[9],[4,30],[9,3],[9],[10],[10],[6,14],[3,1],
[3],[10,11],[8],[2,14],[1],[5],[4],[11,4],[12,24],[5,18],

[13],[7,23],[8],[12],[3,27],[2,12],[5],[2,9],[13,4],[8,18],[1,7],[6],[9,29],[8,21],[5],[6,30],[1,12],[10],
[4,15],[7,22],[11,26],[8,17],[9,29],[5],[3,4],[11,30],[12],[4,29],[3],[9],[6],[3,4],[1],[10],[3,29],[10,28],[1,20],[11,13],[3],[3,12],[3,8],[10,9],[3,26],[8],[7],[5],
[13,17],[2,27],[11,15],[12],[9,19],[2,15],[3,16],[1],[12,17],[9,1],[6,19],[4],[5],[5],[8,1],[11,7],[5,2],[9,28],[1],[2,2],[7,4],[4,22],[7,24],[9,26],[13,28],[11,26]]
  */
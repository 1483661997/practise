package DataStruct.Branch;

import java.util.*;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
/*
 * 给你一个链表数组，每个链表都已经按升序排列。

请你将所有链表合并到一个升序链表中，返回合并后的链表。

 

示例 1：

输入：lists = [[1,4,5],[1,3,4],[2,6]]
输出：[1,1,2,3,4,4,5,6]
解释：链表数组如下：
[
  1->4->5,
  1->3->4,
  2->6
]
将它们合并到一个有序链表中得到。
1->1->2->3->4->4->5->6


 */

public class mergeKList {
     public ListNode mergeKLists(ListNode[] lists) {
      if(lists.length == 0 ) return new ListNode();
       Comparator<ListNode> comparator = new Comparator<ListNode>() {
            @Override
            public int compare(ListNode node1, ListNode node2) {
                return Integer.compare(node1.val, node2.val); // 按 val 升序排序
            }
        };

      int len = lists.length;
      PriorityQueue<ListNode> queue = new PriorityQueue<>(comparator);

      for(int i = 0; i < len; i++){
        if(lists[i] != null)
          queue.offer(lists[i]);
      }

      ListNode mergeKList = new ListNode();
      ListNode work = mergeKList;

      while(!queue.isEmpty()){
        ListNode tmp = queue.poll();
        work.next = tmp;
        work = work.next;
        if(tmp.next != null) queue.add(tmp.next);
      }
      work.next = null;
      return mergeKList.next;


    }
    public class ListNode {
      int val;
      ListNode next;
    ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}
}


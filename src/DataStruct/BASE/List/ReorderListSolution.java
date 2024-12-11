package DataStruct.BASE.List;

/*
 * 143. 重排链表
给定一个单链表 L 的头节点 head ，单链表 L 表示为：

L0 → L1 → … → Ln - 1 → Ln
请将其重新排列后变为：

L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。

示例 1：

输入：head = [1,2,3,4]
输出：[1,4,2,3]
示例 2：

输入：head = [1,2,3,4,5]
输出：[1,5,2,4,3]
 

提示：

链表的长度范围为 [1, 5 * 104]
1 <= node.val <= 1000
 */

 /*
  * 1 2 3 4 5
    1 5 2 3 4
    1 5 2 4 3
  */
public class ReorderListSolution {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public void reorderList(ListNode head) {
        ListNode last = head;
        ListNode p = head;
        last = getLast(last);
        System.out.println(last.val);
        while(p != last){
            ListNode tmp = p.next;
            p.next = last.next;
            p = p.next;
            p.next = tmp;
            p = p.next;
            last.next = null;
            
            last = p;
            last = getLast(last);

        }
    }

    public ListNode getLast(ListNode node){
        if(node == null || node.next == null || node.next.next == null) return node;
        return getLast(node.next);
    }
}

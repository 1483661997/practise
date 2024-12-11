package DataStruct.BASE.List;
/*
 * 83. 删除排序链表中的重复元素
简单
相关标签
相关企业

给定一个已排序的链表的头 head ， 删除所有重复的元素，使每个元素只出现一次 。返回 已排序的链表 。

 

示例 1：

输入：head = [1,1,2]
输出：[1,2]

示例 2：

输入：head = [1,1,2,3,3]
输出：[1,2,3]

 

提示：

    链表中节点数目在范围 [0, 300] 内
    -100 <= Node.val <= 100
    题目数据保证链表已经按升序 排列


 */
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
public class DeleteDuplicatesSolution {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public ListNode deleteDuplicates(ListNode head) {
        
        ListNode p = head;
        
        while(p != null){
            ListNode next = p.next;
            while(next != null && p.val == next.val){
                next = next.next;
            }
            p.next = next;
            
            p = next;
        }

        return head;

    }
}

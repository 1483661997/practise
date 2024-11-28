package DataStruct.BASE.List1;

public class GetIntersectionNodeSolution {
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
/** 相交链表
给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表不存在相交节点，返回 null 。

图示两个链表在节点 c1 开始相交：

题目数据 保证 整个链式结构中不存在环。

注意，函数返回结果后，链表必须 保持其原始结构 。

自定义评测：

评测系统 的输入如下（你设计的程序 不适用 此输入）：

    intersectVal - 相交的起始节点的值。如果不存在相交节点，这一值为 0
    listA - 第一个链表
    listB - 第二个链表
    skipA - 在 listA 中（从头节点开始）跳到交叉节点的节点数
    skipB - 在 listB 中（从头节点开始）跳到交叉节点的节点数

评测系统将根据这些输入创建链式数据结构，并将两个头节点 headA 和 headB 传递给你的程序。如果程序能够正确返回相交节点，那么你的解决方案将被 视作正确答案 。

 

示例 1：

输入：intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
输出：Intersected at '8'
解释：相交节点的值为 8 （注意，如果两个链表相交则不能为 0）。
从各自的表头开始算起，链表 A 为 [4,1,8,4,5]，链表 B 为 [5,6,1,8,4,5]。
在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。
— 请注意相交节点的值不为 1，因为在链表 A 和链表 B 之中值为 1 的节点 (A 中第二个节点和 B 中第三个节点) 是不同的节点。换句话说，它们在内存中指向两个不同的位置，而链表 A 和链表 B 中值为 8 的节点 (A 中第三个节点，B 中第四个节点) 在内存中指向相同的位置。

 

示例 2：

输入：intersectVal = 2, listA = [1,9,1,2,4], listB = [3,2,4], skipA = 3, skipB = 1
输出：Intersected at '2'
解释：相交节点的值为 2 （注意，如果两个链表相交则不能为 0）。
从各自的表头开始算起，链表 A 为 [1,9,1,2,4]，链表 B 为 [3,2,4]。
在 A 中，相交节点前有 3 个节点；在 B 中，相交节点前有 1 个节点。

示例 3：

输入：intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
输出：null
解释：从各自的表头开始算起，链表 A 为 [2,6,4]，链表 B 为 [1,5]。
由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
这两个链表不相交，因此返回 null 。

 

提示：

    listA 中节点数目为 m
    listB 中节点数目为 n
    1 <= m, n <= 3 * 104
    1 <= Node.val <= 105
    0 <= skipA <= m
    0 <= skipB <= n
    如果 listA 和 listB 没有交点，intersectVal 为 0
    如果 listA 和 listB 有交点，intersectVal == listA[skipA] == listB[skipB]

         */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = 0, lenB = 0;
        ListNode pointerA = headA, pointerB = headB; 
        while(pointerA != null){
            lenA++;
            pointerA = pointerA.next;
        }

        while(pointerB != null){
            lenB++;
            pointerB = pointerB.next;
        }
        pointerA = headA;
        pointerB = headB;
        if(lenA > lenB){
            int tmp = lenA - lenB;
            while(tmp--!=0) pointerA = pointerA.next;
        }else{
            int tmp = lenB - lenA;
            while(tmp--!=0) pointerB = pointerB.next;
        }

        while(pointerA !=null && pointerB != null){
            if(pointerA.equals(pointerB)) return pointerA;
            pointerA = pointerA.next;
            pointerB = pointerB.next;
        }
        return null;
        
    }

/** 反转链表
给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。


示例 1：

输入：head = [1,2,3,4,5]
输出：[5,4,3,2,1]

示例 2：

输入：head = [1,2]
输出：[2,1]

示例 3：

输入：head = []
输出：[]

 

提示：

    链表中节点的数目范围是 [0, 5000]
    -5000 <= Node.val <= 5000*/


    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode left = head, right = head.next, work = null;
        while (right != null) {
            left.next = work;
            work = left;
            left = right;
            right = right.next;
        }
        left.next = work;
        return left;
    }

    /*
     *  回文链表


给你一个单链表的头节点 head ，请你判断该链表是否为
回文链表
。如果是，返回 true ；否则，返回 false 。

 

示例 1：

输入：head = [1,2,2,1]
输出：true

示例 2：

输入：head = [1,2]
输出：false

 

提示：

    链表中节点数目在范围[1, 105] 内
    0 <= Node.val <= 9

     */
    public boolean isPalindrome(ListNode head) {
        int len = 1;
        ListNode pointer = head;
        ListNode head2 = new ListNode(pointer.val);
        pointer = pointer.next;

        ListNode pointer2 = head2;

        while(pointer != null){
            pointer2.next = new ListNode(pointer.val);
            pointer2 = pointer2.next;
            len++;
            pointer = pointer.next;
        }
        pointer2.next = null;
       

        head2 = reverseList(head2);
        pointer2 = head2;
        while(pointer2 != null){
            System.out.println(pointer2.val);
            pointer2 = pointer2.next;
        }
        pointer2 = head2;
        pointer = head;

        while(pointer != null){
            System.out.println(pointer.val);
            pointer = pointer.next;
        }

        pointer = head;

        System.out.println(len);
        // pointer2 = head2;
        for(int i = 0; i < len/2; i++){
            if(pointer.val != pointer2.val) return false;
            pointer = pointer.next;
            pointer2 = pointer2.next;
        }
        return true;
    }
    

    /*
     * 环形链表 II


给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。

如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。 为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。

不允许修改 链表。

 

示例 1：

输入：head = [3,2,0,-4], pos = 1
输出：返回索引为 1 的链表节点
解释：链表中有一个环，其尾部连接到第二个节点。

示例 2：

输入：head = [1,2], pos = 0
输出：返回索引为 0 的链表节点
解释：链表中有一个环，其尾部连接到第一个节点。

示例 3：

输入：head = [1], pos = -1
输出：返回 null
解释：链表中没有环。

 

提示：

    链表中节点的数目范围在范围 [0, 104] 内
    -105 <= Node.val <= 105
    pos 的值为 -1 或者链表中的一个有效索引

     */
    public ListNode detectCycle(ListNode head) {
        if(head == null) return null;
        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null ){
            slow = slow.next;
            fast = fast.next.next;
            if(slow.equals(fast)) return slow;
            
        }
        return null;
    }

    public boolean hasCycle(ListNode head) {
        if(head == null) return false;
        ListNode slow = head, fast = head;
        while(fast != null && fast.next != null ){
            
            slow = slow.next;
            fast = fast.next.next;
            if(slow.equals(fast)) return true;
        }
        return false;
    }

       /*
        * 排序链表
尝试过
中等
相关标签
相关企业

给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 。

 

示例 1：

输入：head = [4,2,1,3]
输出：[1,2,3,4]

示例 2：

输入：head = [-1,5,3,4,0]
输出：[-1,0,3,4,5]

示例 3：

输入：head = []
输出：[]

        */
    public ListNode sortList(ListNode head) {
        ListNode node = new ListNode();
        ListNode word = node;
        
        while(head != null){
            word = node;
            
            while (word.next!= null && word.next.val < head.val ) {
                word = word.next;
            }
            
            ListNode tmp = head.next;
            head.next = word.next;
            word.next = head;
            head = tmp;
        
        }


        return node.next;
    }

    // public ListNode mergeList(ListNode left, ListNode right){
        
    // }
    public ListNode sortList1(ListNode head) {

        if (head == null || head.next == null) return head;
        ListNode fast = head.next, slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode tmp = slow.next;
        slow.next = null;
        ListNode left = sortList(head);
        ListNode right = sortList(tmp);
        ListNode h = new ListNode(0);
        ListNode res = h;

        while (left != null && right != null) {
            if (left.val < right.val) {
                h.next = left;
                left = left.next;
            } else {
                h.next = right;
                right = right.next;
            }
            h = h.next;
        }
        h.next = left != null ? left : right;
        return res.next;

    }

    
}

package DataStruct.Branch;


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
  * ：head = [-1,5,3,4,0]
    输出：[-1,0,3,4,5]

  */
public class sortList {
    public static void main(String[] args) {

    }
    // public ListNode sortList(ListNode head) {
    //     ListNode node = new ListNode();
    //     ListNode word = node;
    //     while(head != null){
    //         word = node;
    //         while (word.next!= null && word.next.val < head.val ) {
    //             word = word.next;
    //         }
    //         //超时
    //         ListNode tmp = head.next;
    //         head.next = word.next;
    //         word.next = head;
    //         head = tmp;
            
    //     }

    //     return node.next;
    // }
    // 1 2 2 3 4   5 4 
    //1 2  2 4 5    3 4 5
    // 1 2 2 3 4  5    5 

    public void mergeKList(ListNode left1, ListNode right1, ListNode left2, ListNode right2){
        // ListNode tmp = left;
        if(right1.val < left1.val ){
            int tmp = right1.val;
            right1 .val = left1.val;
            left1.val = tmp;
        }
        
        ListNode left = left1, right = left2;
        while(left != right1 & right != right2){
            if(right.val < left.val){
                //插到前面来
                
            }
            left = left.next;
        }
        while(left != right1){
            
        }  
        while (right != right2) {
            
        }
        
        
        
        
    }
    //优化 如何优化
    public class ListNode {
           int val;
           ListNode next;
         ListNode() {}
           ListNode(int val) { this.val = val; }
           ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }
}

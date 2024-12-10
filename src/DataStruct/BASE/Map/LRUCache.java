package DataStruct.BASE.Map;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    class ListNode{
        ListNode next;
        ListNode prev;
        int val;
        int key;
        public ListNode(int key, int val){
            this.key = key;
            this.val = val;
        };
    }

    Map<Integer, ListNode> map;
    ListNode head;
    ListNode tail;
    int len;
    int capacity;
    public LRUCache(int capacity) {
        this.head = new ListNode(0, 0); // Dummy head
        this.tail = new ListNode(0, 0); // Dummy tail
        head.next = tail;
        tail.prev = head;
        this.capacity = capacity;
        len=0;
        map = new HashMap<>();
        this.capacity = capacity;
    }

    public int get(int key) {
        ListNode node = map.get(key);
        if (node == null) {
            return -1;
        }
        moveToHead(node);
        return node.val;
    }
    public void put(int key, int value) {
        ListNode node = map.get(key);
        if (node == null) {
            ListNode newNode = new ListNode(key, value);
            map.put(key, newNode);
            addToHead(newNode);
            if (map.size() > capacity) {
                // Remove the LRU entry
                ListNode tail = popTail();
                map.remove(tail.key);
            }
        } else {
            node.val = value;
            moveToHead(node);
        }
    }



    private void removeNode(ListNode node){
        // ListNode prev = node.prev;
        // prev.next = node.next;

        ListNode before = node.prev;
        ListNode after = node.next;
        before.next = after;
        after.prev = before;


    }

    private void moveToHead(ListNode node){
        removeNode(node);
        addToHead(node);
    }

    private void addToHead(ListNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
    private ListNode popTail() {
        ListNode res = tail.prev;
        removeNode(res);
        return res;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
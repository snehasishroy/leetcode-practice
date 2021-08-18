import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/lru-cache/
 * <p>
 * Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.
 * <p>
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity,
 * it should invalidate the least recently used item before inserting a new item.
 * <p>
 * The cache is initialized with a positive capacity.
 * <p>
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 * <p>
 * LRUCache cache = new LRUCache( 2 )
 * cache.put(1,1);
 * cache.put(2,2);
 * cache.get(1);       // returns 1
 * cache.put(3,3);    // evicts key 2
 * cache.get(2);       // returns -1 (not found)
 * cache.put(4,4);    // evicts key 1
 * cache.get(1);       // returns -1 (not found)
 * cache.get(3);       // returns 3
 * cache.get(4);       // returns 4
 */
public class LRUCache {
    int maxCapacity;
    DLL dummyHead, dummyTail; //acts as dummy nodes to quickly add to and remove from
    //structure is dummyHead -> most recently used -> second recently used -> third recently used .... least recently used -> dummyTail
    //when adding a new node, just add it after dummyHead
    //when removing the least recently used node, remove the node before dummyTail
    Map<Integer, DLL> map;

    /**
     * Approach: Doubly Linked List + HashMap, Maintain dummy head and tail pointers for easy pointer manipulation
     *
     * {@link FirstUniqueNumber}
     */
    public LRUCache(int capacity) {
        map = new HashMap<>(capacity);
        dummyHead = new DLL(-1, -1);
        dummyTail = new DLL(-1, -1);
        dummyHead.next = dummyTail; //init the dummy nodes
        dummyTail.prev = dummyHead;
        maxCapacity = capacity;
    }

    public int get(int key) {
        if (map.get(key) == null) {
            return -1;
        }
        DLL node = map.get(key);
        removeNode(node);
        addNodeAfterHead(node); //to make it most recently used
        return node.value;
    }

    private void addNodeAfterHead(DLL node) {
        //put the node in between head and head.next
        DLL next = dummyHead.next;
        node.next = next;
        node.prev = dummyHead;
        dummyHead.next = node;
        next.prev = node;
    }

    private void removeNode(DLL node) {
        //connect the prev with the next
        DLL previous = node.prev;
        DLL next = node.next;
        next.prev = previous;
        previous.next = next;
    }

    public void put(int key, int value) {
        DLL node = map.get(key);
        if (node != null) {
            //if the node already exists, we need to update the value and make it most recently used
            removeNode(node);
            addNodeAfterHead(node);
            node.value = value;
        } else if (map.size() < maxCapacity) {
            node = new DLL(key, value);
            addNodeAfterHead(node);
        } else if (map.size() == maxCapacity) { //cache is full, need to evict the least recently used node and then add the new node
            DLL oldest = dummyTail.prev;
            removeNode(oldest);
            map.remove(oldest.key); //critical to remove the oldest element from the map as well
            node = new DLL(key, value);
            addNodeAfterHead(node);
        }
        map.put(key, node); //insert into map
    }

    //doubly linked list
    // Why Doubly linked list? Because it supports splicing the list from arbitrarily nodes in O(1) time
    // After removing any random node, just connect the previous node with the next node
    private static class DLL {
        int key; //why store the key as well? in order to remove the key of the least recently used node from the map
        int value;
        DLL prev;
        DLL next;

        public DLL(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}

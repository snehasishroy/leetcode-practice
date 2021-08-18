import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * https://leetcode.com/problems/first-unique-number/
 *
 * You have a queue of integers, you need to retrieve the first unique integer in the queue.
 *
 * Implement the FirstUnique class:
 *
 * FirstUnique(int[] nums) Initializes the object with the numbers in the queue.
 * int showFirstUnique() returns the value of the first unique integer of the queue, and returns -1 if there is no such integer.
 * void add(int value) insert value to the queue.
 *
 * Input:
 * ["FirstUnique","showFirstUnique","add","showFirstUnique","add","showFirstUnique","add","showFirstUnique"]
 * [[[2,3,5]],[],[5],[],[2],[],[3],[]]
 * Output:
 * [null,2,null,2,null,3,null,-1]
 * Explanation:
 * FirstUnique firstUnique = new FirstUnique([2,3,5]);
 * firstUnique.showFirstUnique(); // return 2
 * firstUnique.add(5);            // the queue is now [2,3,5,5]
 * firstUnique.showFirstUnique(); // return 2
 * firstUnique.add(2);            // the queue is now [2,3,5,5,2]
 * firstUnique.showFirstUnique(); // return 3
 * firstUnique.add(3);            // the queue is now [2,3,5,5,2,3]
 * firstUnique.showFirstUnique(); // return -1
 *
 * Input:
 * ["FirstUnique","showFirstUnique","add","add","add","add","add","showFirstUnique"]
 * [[[7,7,7,7,7,7]],[],[7],[3],[3],[7],[17],[]]
 * Output:
 * [null,-1,null,null,null,null,null,17]
 * Explanation:
 * FirstUnique firstUnique = new FirstUnique([7,7,7,7,7,7]);
 * firstUnique.showFirstUnique(); // return -1
 * firstUnique.add(7);            // the queue is now [7,7,7,7,7,7,7]
 * firstUnique.add(3);            // the queue is now [7,7,7,7,7,7,7,3]
 * firstUnique.add(3);            // the queue is now [7,7,7,7,7,7,7,3,3]
 * firstUnique.add(7);            // the queue is now [7,7,7,7,7,7,7,3,3,7]
 * firstUnique.add(17);           // the queue is now [7,7,7,7,7,7,7,3,3,7,17]
 * firstUnique.showFirstUnique(); // return 17
 *
 * Input:
 * ["FirstUnique","showFirstUnique","add","showFirstUnique"]
 * [[[809]],[],[809],[]]
 * Output:
 * [null,809,null,-1]
 * Explanation:
 * FirstUnique firstUnique = new FirstUnique([809]);
 * firstUnique.showFirstUnique(); // return 809
 * firstUnique.add(809);          // the queue is now [809,809]
 * firstUnique.showFirstUnique(); // return -1
 *
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^8
 * 1 <= value <= 10^8
 * At most 50000 calls will be made to showFirstUnique and add.
 * </pre>
 */
public class FirstUniqueNumberOptimized {
    DLL head = new DLL(-1), tail = new DLL(-1); //dummy head and tail pointers, initialized with -1
    Map<Integer, DLL> map = new HashMap<>();

    /**
     * Approach: Similar to implementation of {@link LRUCache}, Use a doubly linked list and a hashmap
     * Head of the doubly linked list will point towards the first unique number, then second unique number and so on.
     * HashMap will maintain a mapping of number to node in the doubly linked list.
     *
     * In case a number is already present in the hashmap, this indicates that it's no longer unique hence need to delete the node from DLL.
     */
    public FirstUniqueNumberOptimized(int[] nums) {
        head.next = tail;
        tail.prev = head;
        for (int num : nums) {
            add(num);
        }
    }

    public int showFirstUnique() {
        return head.next.key;
    }

    public void add(int num) {
        if (map.containsKey(num)) {
            if (map.get(num) != null) { //null indicates that the DLL node has already been deleted and the key is no longer unique
                deleteNode(map.get(num));
                map.put(num, null); //set to null, alternatively could have used another set to indicate non-unique keys and delete the key from the map
            }
        } else {
            //unique number found
            DLL node = new DLL(num);
            addNode(node);
            map.put(num, node);
        }
    }

    //delete a node from DLL
    private void deleteNode(DLL node) {
        DLL previous = node.prev;
        DLL next = node.next;
        previous.next = next;
        next.prev = previous;
    }

    //add a node to the tail of the DLL
    private void addNode(DLL node) {
        DLL temp = tail.prev;
        temp.next = node;
        node.prev = temp;
        node.next = tail;
        tail.prev = node;
    }

    private static class DLL {
        DLL prev;
        DLL next;
        int key;
        DLL(int key) {
            this.key = key;
        }
    }
}

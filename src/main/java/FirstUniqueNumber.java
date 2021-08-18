import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

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
public class FirstUniqueNumber {
    Map<Integer, Integer> freq = new HashMap<>(); //{value -> freq}
    Map<Integer, Integer> firstOccurrence = new HashMap<>(); //{value -> index}
    TreeSet<Pair<Integer, Integer>> uniqueIndices = new TreeSet<>((o1, o2) -> Integer.compare(o1.getKey(), o2.getKey())); //{index, value}
    int index = 0;

    /**
     * Approach: Greedy, Since we need a way of ordering the unique numbers, we would need a treeSet.
     * Also since we need to find the frequency of occurrence, we would need a hashmap of frequency.
     * We would also need a way of deleting indices from the set in case a number is no longer unique, so we would need a way of mapping
     * a number to its first index
     *
     * Time ~195ms See {@link FirstUniqueNumberOptimized} for optimized implementation
     * {@link MaxFrequencyStack} {@link LRUCache} related problems
     */
    public FirstUniqueNumber(int[] nums) {
        for (int num : nums) {
            add(num);
        }
    }

    public int showFirstUnique() {
        if (uniqueIndices.isEmpty()) {
            return -1;
        }
        return uniqueIndices.first().getValue();
    }

    public void add(int num) {
        freq.put(num, freq.getOrDefault(num, 0) + 1);
        if (freq.get(num) == 1) { //unique number found
            uniqueIndices.add(new Pair<>(index, num));
            firstOccurrence.put(num, index);
        } else if (freq.get(num) == 2) { //a number is no longer unique, remove the index from treeSet
            int idx = firstOccurrence.get(num);
            uniqueIndices.remove(new Pair<>(idx, num));
        }
        index++;
    }
}

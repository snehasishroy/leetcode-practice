import javafx.util.Pair;

import java.util.*;

/**
 * https://leetcode.com/problems/maximum-frequency-stack/
 * <p>
 * Implement FreqStack, a class which simulates the operation of a stack-like data structure.
 * <p>
 * FreqStack has two functions:
 * <p>
 * push(int x), which pushes an integer x onto the stack.
 * pop(), which removes and returns the most frequent element in the stack.
 * If there is a tie for most frequent element, the element closest to the top of the stack is removed and returned.
 * <p>
 * Input:
 * ["FreqStack","push","push","push","push","push","push","pop","pop","pop","pop"],
 * [[],[5],[7],[5],[7],[4],[5],[],[],[],[]]
 * Output: [null,null,null,null,null,null,null,5,7,5,4]
 * Explanation:
 * After making six .push operations, the stack is [5,7,5,7,4,5] from bottom to top.  Then:
 * <p>
 * pop() -> returns 5, as 5 is the most frequent.
 * The stack becomes [5,7,5,7,4].
 * <p>
 * pop() -> returns 7, as 5 and 7 is the most frequent, but 7 is closest to the top.
 * The stack becomes [5,7,5,4].
 * <p>
 * pop() -> returns 5.
 * The stack becomes [5,7,4].
 * <p>
 * pop() -> returns 4.
 * The stack becomes [5,7].
 */
public class MaxFrequencyStack {
    Map<Integer, List<Integer>> map = new HashMap<>(); //map is required because priority queue does not supports finding an element
    //content of map needs to be in sync with priority queue so that we can perform lookup
    /**
     * Use priority queue to solve in logn time per operation.
     * Priority should be based on the size of no of occurrences, more occurrences should be given high priority
     * In case of similar no of occurrences, priority should be based on the timestamp of last occurrence of the current key.
     *
     * {@link MaxFrequencyStackHashMap} {@link MaxFrequencyStackTreeMap} {@link LRUCache} {@link FirstUniqueNumber}
     */
    PriorityQueue<Pair<Integer, List<Integer>>> pq = new PriorityQueue<>((o1, o2) -> { //key -> list of occurrence timestamps
        if (o1.getValue().size() == o2.getValue().size()) {
            //if frequency matches, break the tie by checking the last occurrence
            return Integer.compare(o2.getValue().get(o2.getValue().size() - 1), o1.getValue().get(o1.getValue().size() - 1));
        } else {
            return Integer.compare(o2.getValue().size(), o1.getValue().size());
        }
    });
    int timeStamp = 0; //monotonically increasing timestamp

    public MaxFrequencyStack() {

    }

    public void push(int x) {
        List<Integer> occurrences = map.computeIfAbsent(x, __ -> new ArrayList<>());
        if (!occurrences.isEmpty()) {
            pq.remove(new Pair<>(x, occurrences)); //remove the existing value present in the pq
        }
        occurrences.add(timeStamp++);
        //insert the updated value back in pq
        pq.add(new Pair<>(x, occurrences));
    }

    public int pop() {
        Pair<Integer, List<Integer>> top = pq.remove();
        if (top.getValue().size() == 1) {
            //remove from map if only one occurrence
            map.remove(top.getKey());
        } else {
            List<Integer> occurrences = map.get(top.getKey());
            occurrences.remove(occurrences.size() - 1); //reduce the no of occurrences
            pq.add(new Pair<>(top.getKey(), occurrences)); //add back the updated value in pq with reduced priority
        }
        return top.getKey();
    }
}

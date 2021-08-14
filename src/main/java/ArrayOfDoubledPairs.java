import java.util.TreeMap;

/**
 * <pre>
 * https://leetcode.com/problems/array-of-doubled-pairs/
 *
 * Given an array of integers arr of even length, return true if and only if it is possible to reorder it such that arr[2 * i + 1] = 2 * arr[2 * i] for every 0 <= i < len(arr) / 2.
 *
 * Input: arr = [3,1,3,6]
 * Output: false
 *
 * Input: arr = [2,1,2,6]
 * Output: false
 *
 * Input: arr = [4,-2,2,-4]
 * Output: true
 * Explanation: We can take two groups, [-2,-4] and [2,4] to form [-2,-4,2,4] or [2,4,-2,-4].
 *
 * Input: arr = [1,2,4,16,8,4]
 * Output: false
 *
 * Constraints:
 * 0 <= arr.length <= 3 * 10^4
 * arr.length is even.
 * -10^5 <= arr[i] <= 10^5
 * </pre>
 */
public class ArrayOfDoubledPairs {
    /**
     * Approach: Greedy, sort the input and try to match the smallest unmatched element with the required element
     * Care must be taken to handle negative elements as it must be matched with its half not the double.
     * i.e. -10 should be matched with -5 and 10 should be matched with 20
     *
     * Learnings: Read the fucking problem statement properly before attempting the problem. Two mistakes I made
     * 1. Thought that the array can be of odd length
     * 2. Need to pair [2*i] index with [2*i + 1] which was making the problem more difficult as it was creating a chain of dependent indices
     * We only care about pairs of adjacent numbers which do not cascade i.e. first adjacent pair is independent of next adjacent pair
     *
     * Was not able to solve this problem on my own :(
     *
     * {@link SplitArrayIntoConsecutiveSubsequences}
     */
    public boolean canReorderDoubled(int[] arr) {
        int n = arr.length;
        TreeMap<Integer, Integer> map = new TreeMap<>(); //greedily choose the smallest unmatched element every time
        for (int val : arr) {
            map.put(val, map.getOrDefault(val, 0) + 1);
        }
        while (!map.isEmpty()) {
            int smallest = map.firstKey();
            map.put(smallest, map.get(smallest) - 1);
            map.remove(smallest, 0);
            if (smallest < 0 && smallest % 2 != 0) { //handle cases when smallest element is -5, it can't be paired with
                return false;
            }
            int target = smallest < 0 ? smallest / 2 : 2 * smallest; //special case for negative numbers
            if (map.containsKey(target)) {
                map.put(target, map.get(target) - 1);
                map.remove(target, 0);
            } else {
                return false;
            }
        }
        return true;
    }
}

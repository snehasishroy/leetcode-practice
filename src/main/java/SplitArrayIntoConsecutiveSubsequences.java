import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/split-array-into-consecutive-subsequences/
 * <p>
 * Given an array nums sorted in ascending order, return true if and only if you can split it into 1 or more subsequences
 * such that each subsequence consists of consecutive integers and has length at least 3.
 * <p>
 * Input: [1,2,3,3,4,5]
 * Output: True
 * Explanation:
 * You can split them into two consecutive subsequences :
 * 1, 2, 3
 * 3, 4, 5
 * <p>
 * Input: [1,2,3,3,4,4,5,5]
 * Output: True
 * Explanation:
 * You can split them into two consecutive subsequences :
 * 1, 2, 3, 4, 5
 * 3, 4, 5
 * <p>
 * Input: [1,2,3,4,4,5]
 * Output: False
 */
public class SplitArrayIntoConsecutiveSubsequences {
    /**
     * Approach: Very tricky greedy solution, each number tries to build up the result in the least costly manner
     * Every number has two options, either start a new subsequence or be part of existing subsequence
     * Starting a new subsequence is a costly operation as it requires min 3 elements, so it's better if we try to add current
     * number to be part of existing subsequence first (if it can be added)
     * <p>
     * We use two hashmap, one for keeping track of frequency count (aka available or not),
     * and other which returns how many subsequences require a specific value
     * <p>
     * {@link DivideArrayInSetsOfKConsecutiveNumbers} {@link SmallestSubsequenceOfDistinctCharacters} {@link TaskScheduler}
     * {@link ReorganizeString} {@link NumberOfMatchingSubsequences} {@link ArrayOfDoubledPairs} related greedy problems
     */
    public boolean isPossible(int[] nums) {
        HashMap<Integer, Integer> frequencyMap = new HashMap<>();
        HashMap<Integer, Integer> needMap = new HashMap<>(); //3->1, indicates 1 subsequence needs 3
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        for (int i = 0; i < nums.length; i++) {
            if (frequencyMap.getOrDefault(nums[i], 0) > 0) { //if this number is not assigned
                //first check whether this no can be used to extend existing subsequence
                if (needMap.getOrDefault(nums[i], 0) > 0) {
                    needMap.put(nums[i], needMap.get(nums[i]) - 1); //fulfill the need
                    needMap.put(nums[i] + 1, needMap.getOrDefault(nums[i] + 1, 0) + 1); //put in a new need
                    frequencyMap.put(nums[i], frequencyMap.get(nums[i]) - 1); //decrement the frequency count
                } else {
                    //try to start a new subsequence by blocking min 3 numbers
                    int lookup = nums[i];
                    for (int j = 0; j < 3; j++) {
                        if (frequencyMap.getOrDefault(lookup + j, 0) > 0) {
                            frequencyMap.put(lookup + j, frequencyMap.get(lookup + j) - 1);
                        } else {
                            return false; //no not available, subsequence can't be formed
                        }
                    }
                    //put in a new need for the number that can tentatively extend this subsequence
                    needMap.put(lookup + 3, needMap.getOrDefault(lookup + 3, 0) + 1);
                }
            }
        }
        return true;
    }

    /**
     * Approach: Recursive solution with backtracking, Times out
     * My initial attempt in which I extended each subsequence as long as I can.
     * If the length of current subsequence >= 3, then extending the current subsequence is optional
     * So I optionally try to extend the current subsequence only if I can't divide the entire map
     * <p>
     * Don't forget to put the popped numbers back in the map, if current subsequence can't give valid result
     * This solution isn't memoizable and given the large input, I had very low confidence on this solution getting AC
     */
    public boolean isPossibleRecursive(int[] nums) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        return recur(map);
    }

    private boolean recur(TreeMap<Integer, Integer> map) {
        if (map.isEmpty()) {
            return true;
        }
        int base = map.firstKey();
        int incr = 0;
        ArrayList<Integer> popped = new ArrayList<>();
        while (true) {
            if (map.containsKey(base + incr)) {
                map.put(base + incr, map.get(base + incr) - 1);
                popped.add(base + incr);
                map.remove(base + incr, 0);
                if (incr >= 2 && recur(map)) {
                    return true;
                }
                incr++;
            } else {
                for (int pop : popped) {
                    map.put(pop, map.getOrDefault(pop, 0) + 1);
                }
                return false;
            }
        }
    }
}

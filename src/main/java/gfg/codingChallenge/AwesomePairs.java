package gfg.codingChallenge;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Geek is busy solving problems on Bitwise operators. Today while solving problems, he observed; something which intrigued him very much.
 * He discovered that there are some awesome pairs in a list of integers whose Bitwise AND is strictly greater than Bitwise XOR among them.
 * Given a list arr[] of N integers find the number of awesome pairs
 *
 * Input:
 * N = 4
 * arr[] = {6, 2, 5, 3}
 * Output: 2
 * Explanation:
 * Awesome pairs are (6, 5) and (2, 3).
 *
 * Input:
 * N = 3
 * arr[] = {1, 1, 1}
 * Output: 3
 * Explanation: All pairs are awesome.
 * </pre>
 */
public class AwesomePairs {
    /**
     * Approach: Tricky Bitwise, Bitwise AND will be greater than bitwise XOR for all the tuples with same highest one bit
     * ie. if two numbers have the same highest set bit, their "and" will always be greater than their "xor"
     */
    long pairs(int N, int[] arr) {
        Map<Integer, Integer> count = new HashMap<>(); //key -> integer representation of a number with only one bit set, value -> frequency of occurrence
        for (int val : arr) {
            int key = Integer.highestOneBit(val);
            count.put(key, count.getOrDefault(key, 0) + 1);
        }
        long res = 0;
        for (Integer value : count.values()) {
            long candidate = ((long) value * (value - 1)) / 2;
            res += candidate;
        }
        return res;
    }
}

package gfg.codingChallenge;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * https://practice.geeksforgeeks.org/contest/the-easiest-ever-coding-challenge
 * Count the number of substrings which contains equal number of lowercase and uppercase letters
 *
 * Input:
 * S = "gEEk"
 * Output: 3
 * Explanation: There are 3 substrings of the given string which satisfy the condition. They are "gE", "gEEk" and "Ek".
 *
 * Input:
 * S = "WomensDAY"
 * Output: 4
 * Explanation: There are 4 substrings of the given string which satisfy the condition. They are "Wo", "ensDAY", "nsDA" and "sD"
 * </pre>
 */
public class CountSubstringsWithEquals0And1 {
    /**
     * Approach: Prefix sum, consider lower case character as -1 and upper case character as 1, then the problem reduces to finding
     * no of subarrays with target sum of 0
     * Similar to https://leetcode.com/problems/contiguous-array/
     *
     * {@link CountBinarySubstrings}
     * TimeComplexity: O(n)
     */
    int countSubstringOptimized(String S) {
        int n = S.length();
        int curSum = 0;
        Map<Integer, Integer> map = new HashMap<>(); //prefixSum -> no of occurrences
        map.put(0, 1);
        int result = 0;
        for (int i = 0; i < n; i++) {
            char c = S.charAt(i);
            curSum += Character.isUpperCase(c) ? 1 : -1;
            if (map.containsKey(curSum)) { //if a subarray sum already exists with value = curSum, this indicates that the sum of elements between the last occurrence and current index is 0
                result += map.get(curSum);
            }
            map.put(curSum, map.getOrDefault(curSum, 0) + 1);
        }
        return result;
    }

    /**
     * Approach: Brute Force, Used this during the contest as size of string was 1000
     * TimeComplexity: O(n^2)
     */
    int countSubstringBruteForce(String S) {
        int n = S.length();
        int res = 0;
        for (int i = 0; i < n; i++) {
            int upper = 0, lower = 0;
            for (int j = i; j < n; j++) {
                char c = S.charAt(j);
                if (Character.isUpperCase(c)) {
                    upper++;
                } else {
                    lower++;
                }
                if (upper == lower) {
                    res++;
                }
            }
        }
        return res;
    }
}

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * https://leetcode.com/problems/longest-uncommon-subsequence-ii/
 *
 * Given an array of strings strs, return the length of the longest uncommon subsequence between them. If the longest uncommon subsequence does not exist, return -1.
 *
 * An uncommon subsequence between an array of strings is a string that is a subsequence of one string but not the others.
 *
 * Input: strs = ["aba","cdc","eae"]
 * Output: 3
 *
 * Input: strs = ["aaa","aaa","aa"]
 * Output: -1
 *
 * Constraints:
 * 2 <= strs.length <= 50
 * 1 <= strs[i].length <= 10
 * strs[i] consists of lowercase English letters.
 * </pre>
 */
public class LongestUncommonSubsequence2 {

    /**
     * Approach: Tricky greedy, Critical observation is that if a string has even a single character which is unique across all the strings,
     * the entire string can qualify as an uncommon subsequence because no other string will be equivalent to this string.
     * So we need to consider a string with every other string and see if it's a subsequence with any other string.
     * If it's not a subsequence with any other, then it can qualify as an uncommon subsequence.
     * If it's a subsequence with any other string, then it indicates it does not have a single different character.
     *
     * Was not able to solve this optimally on my own.
     *
     * {@link CarFleet} {@link ReconstructA2RowBinaryMatrix}
     */
    public int findLUSlengthGreedy(String[] strs) {
        int n = strs.length;
        int maxLength = -1;
        for (int i = 0; i < n; i++) {
            boolean isSubsequenceOfAnyString = false;
            for (int j = 0; j < n; j++) {
                if (j != i) {
                    if (isSubsequence(strs[i], strs[j])) {
                        isSubsequenceOfAnyString = true;
                        break;
                    }
                }
            }
            if (!isSubsequenceOfAnyString) { //candidate uncommon subsequence found
                maxLength = Math.max(maxLength, strs[i].length());
            }
        }
        return maxLength;
    }

    private boolean isSubsequence(String str, String key) { //returns true if str is a subsequence of key
        int idx1 = 0, idx2 = 0;
        int n1 = str.length(), n2 = key.length();
        while (idx1 < n1 && idx2 < n2) {
            char c1 = str.charAt(idx1);
            char c2 = key.charAt(idx2);
            if (c1 == c2) {
                idx1++;
                idx2++;
            } else {
                idx2++;
            }
        }
        return idx1 == n1;
    }

    /**
     * Approach: Brute force, Notice the max length of a string permissible -- 10, which can generate ~1000 subsequences. (2^n)
     * To generate a string from the recursion we would need to call toString() of StringBuilder() which would take additional O(n) time
     * Given 50 strings, total operations would be around 5*10^5, so brute force should work.
     *
     * Took me some time to arrive at brute force solution as I was trying to solve the problem optimally. But still glad to solve
     * this on my own.
     */
    public int findLUSlength(String[] strs) {
        Map<String, Integer> freq = new HashMap<>(); //string -> frequency
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.setLength(0);
            powerSet(str, freq, 0, sb);
        }
        int longest = -1;
        for (Map.Entry<String, Integer> entry : freq.entrySet()) {
            if (entry.getValue() == 1) { //keep track of the longest string with frequency of 1
                longest = Math.max(longest, entry.getKey().length());
            }
        }
        return longest;
    }

    private void powerSet(String str, Map<String, Integer> freq, int index, StringBuilder sb) {
        if (index == str.length()) {
            String candidate = sb.toString();
            freq.put(candidate, freq.getOrDefault(candidate, 0) + 1);
        } else {
            //every character has two options, either be included or be excluded
            sb.append(str.charAt(index));
            powerSet(str, freq, index + 1, sb);
            sb.deleteCharAt(sb.length() - 1);
            powerSet(str, freq, index + 1, sb);
        }
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

/**
 * <pre>
 * https://leetcode.com/problems/shortest-way-to-form-string/
 *
 * From any string, we can form a subsequence of that string by deleting some number of characters (possibly no deletions).
 *
 * Given two strings source and target, return the minimum number of subsequences of source such that their concatenation equals target. If the task is impossible, return -1.
 *
 * Input: source = "abc", target = "abcbc"
 * Output: 2
 * Explanation: The target "abcbc" can be formed by "abc" and "bc", which are subsequences of source "abc".
 *
 * Input: source = "abc", target = "acdbc"
 * Output: -1
 * Explanation: The target string cannot be constructed from the subsequences of source string due to the character "d" in target string.
 *
 * Input: source = "xyz", target = "xzyxz"
 * Output: 3
 * Explanation: The target string can be constructed as follows "xz" + "y" + "xz".
 *
 * Constraints:
 * Both the source and target strings consist of only lowercase English letters from "a"-"z".
 * The lengths of source and target string are between 1 and 1000.
 * </pre>
 */
public class ShortestWayToFormString {

    /**
     * Approach: DP, Instead of performing binary search to find an index greater than previousIndex, maintain a precomputed array that will
     * answer this query in O(1) time
     * e.g if 'a' occurs on {2,5,7} index and there are total 10 indices, flatten the occurrence into arr = {2,2,2,5,5,5,7,7,-1,-1,-1}
     * arr[0] = 2, indicates that first occurrence of 'a' >= 0th index is 2nd index.
     * arr[2] = 5, indicates that first occurrence of 'a' >= 2nd index is 5th index.
     * arr[7] = -1, indicates that 'a' does not occur on or after 7th index.
     *
     * Using this flattened array, we need not perform binary search and can reduce the query time to O(1)
     *
     * Very tricky but cool optimization, was able to think only up to the binary search optimization. Learnt something new.
     * Time Complexity: O(m + n) Space Complexity: O(26 * m)
     */
    public int shortestWayDP(String source, String target) {
        if (isImpossible(source, target)) {
            return -1;
        }
        int n = source.length();
        List<List<Integer>> indices = new ArrayList<>(26); //store the index of occurrence for each character
        for (int i = 0; i < 26; i++) {
            indices.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            char c = source.charAt(i);
            indices.get(c - 'a').add(i);
        }
        int[][] nextOccurrence = new int[26][n + 1];
        for (int[] ints : nextOccurrence) {
            Arrays.fill(ints, -1);
        }
        for (int i = 0; i < 26; i++) {
            List<Integer> allIndices = indices.get(i);
            int index = 0;
            //flatten the indices of a specific character
            for (int j = 0; j < allIndices.size(); j++) {
                int occurrence = allIndices.get(j);
                while (index <= occurrence) { //notice that we are flattening till the occurrence of index, this is important to handle when a char occurs at 0th index
                    nextOccurrence[i][index] = occurrence;
                    index++;
                }
                index = occurrence + 1;
            }
        }
        int count = 0, previousIndex = -1;
        for (char c : target.toCharArray()) {
            int nextIndex = nextOccurrence[c - 'a'][previousIndex + 1]; //find the index that contains 'c' strictly after previousIndex
            if (nextIndex == -1) {
                //need to start a new subsequence
                count++;
                nextIndex = nextOccurrence[c - 'a'][0]; //reset the search from the first occurrence of the character
            }
            previousIndex = nextIndex;
        }
        return count + 1; // + 1 for the last subsequence
    }

    /**
     * Approach: Binary search, Store all the occurrences of a particular character and perform binary search to find an index > last found index.
     * Time Complexity: O(n * log(m))
     * <p>
     * Most of the subsequence related problems are solved using two pointers, keeping track of required character or using binary search.
     */
    public int shortestWayBinarySearch(String source, String target) {
        if (isImpossible(source, target)) {
            return -1;
        }
        List<TreeSet<Integer>> indices = new ArrayList<>(26); //store the index of occurrence for each character
        for (int i = 0; i < 26; i++) {
            indices.add(new TreeSet<>());
        }
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            indices.get(c - 'a').add(i);
        }
        int count = 0, previousIndex = -1;
        for (char c : target.toCharArray()) {
            TreeSet<Integer> allIndices = indices.get(c - 'a');
            Integer greaterIndex = allIndices.higher(previousIndex); //find an index greater than previousIndex
            if (greaterIndex == null) { //if no valid index found, need to start a new subsequence from the first occurrence of the target character
                count++;
                greaterIndex = allIndices.first(); //reset with the first occurrence of the character
            }
            previousIndex = greaterIndex;
        }
        return count + 1; // + 1 for the last subsequence
    }

    /**
     * Approach: Two pointers, Try to find the character required at source to match with current character at target.
     * ie. if char at target is 'c', and current char at source is 'a', need to move the pointers until we find 'c'.
     * <p>
     * Time Complexity: O(mn) e.g source = "abczzzz", target = "zzzzz", you will iterate the source string completely 5 times
     * <p>
     * {@link NumberOfMatchingSubsequences} {@link SplitArrayIntoConsecutiveSubsequences} {@link IsSubsequence} {@link LongPressedName}
     */
    public int shortestWay(String source, String target) {
        if (isImpossible(source, target)) {
            return -1;
        }
        int srcIndex = 0, tgtIndex = 0, cnt = 0;
        while (true) {
            if (tgtIndex == target.length()) {
                break;
            } else if (srcIndex == source.length()) { //one subsequence found, reset and try another
                srcIndex = 0;
                cnt++;
            } else if (source.charAt(srcIndex) == target.charAt(tgtIndex)) {
                srcIndex++;
                tgtIndex++;
            } else {
                //this character at srcIndex needs to be deleted
                srcIndex++;
            }
        }
        return cnt + 1; // + 1 for the last subsequence
    }

    private boolean isImpossible(String source, String target) {
        boolean[] isPresent = new boolean[26];
        //first check whether all the chars of target are present in source or not
        for (char c : source.toCharArray()) {
            isPresent[c - 'a'] = true;
        }
        for (char c : target.toCharArray()) {
            if (!isPresent[c - 'a']) {
                return true;
            }
        }
        return false;
    }
}

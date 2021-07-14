import java.util.Arrays;

/**
 * <pre>
 * https://leetcode.com/problems/custom-sort-string/
 *
 * order and str are strings composed of lowercase letters. In order, no letter occurs more than once.
 *
 * order was sorted in some custom order previously. We want to permute the characters of str so that they match the order that order was sorted.
 * More specifically, if x occurs before y in order, then x should occur before y in the returned string.
 *
 * Return any permutation of str (as a string) that satisfies this property.
 *
 * Input:
 * order = "cba"
 * str = "abcd"
 * Output: "cbad"
 * Explanation:
 * "a", "b", "c" appear in order, so the order of "a", "b", "c" should be "c", "b", and "a".
 * Since "d" does not appear in order, it can be at any position in the returned string. "dcba", "cdba", "cbda" are also valid outputs.
 *
 * Note:
 * order has length at most 26, and no character is repeated in order.
 * str has length at most 200.
 * order and str consist of lowercase letters only.
 * </pre>
 */
public class CustomSortString {
    /**
     * Approach: Sort based on rank of characters. Counting sort can be used to reduce the time complexity to O(n)
     * Count the frequencies of char in T. Then iterate the S and place the characters as per their rank in the result StringBuilder().
     * Finally place the unranked characters that do not appear in S but appear in T.
     *
     * {@link RelativeRanks}
     */
    public String customSortString(String S, String T) {
        int[] rank = new int[26];
        Arrays.fill(rank, Integer.MAX_VALUE);
        for (int i = 0; i < S.length(); i++) {
            rank[S.charAt(i) - 'a'] = i; //update the rank of each characters
        }
        int n = T.length();
        Character[] chars = new Character[n]; //need Character[] instead of char[] because of
        for (int i = 0; i < n; i++) {
            chars[i] = T.charAt(i);
        }
        Arrays.sort(chars, (o1, o2) -> Integer.compare(rank[o1 - 'a'], rank[o2 - 'a'])); //sort based on rank of each character
        char[] result = new char[T.length()];
        for (int i = 0; i < n; i++) {
            result[i] = chars[i];
        }
        return new String(result);
    }
}

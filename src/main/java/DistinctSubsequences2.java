import java.util.Arrays;

/**
 * https://leetcode.com/problems/distinct-subsequences-ii/
 * <p>
 * Given a string S, count the number of distinct, non-empty subsequences of S .
 * <p>
 * Since the result may be large, return the answer modulo 10^9 + 7.
 * <p>
 * Input: "abc"
 * Output: 7
 * Explanation: The 7 distinct subsequences are "a", "b", "c", "ab", "ac", "bc", and "abc".
 * <p>
 * Input: "aba"
 * Output: 6
 * Explanation: The 6 distinct subsequences are "a", "b", "ab", "ba", "aa" and "aba".
 * <p>
 * Input: "aaa"
 * Output: 3
 * Explanation: The 3 distinct subsequences are "a", "aa" and "aaa".
 * <p>
 * Note:
 * S contains only lowercase letters.
 * 1 <= S.length <= 2000
 */
public class DistinctSubsequences2 {
    /**
     * Approach: DP state optimization, if there were no duplicates, and there are 10 subsequences ending at index i, there will be 20
     * subsequences ending at index i+1, because for char at index i+1, we can extend the previous 10 subsequences by adding char at index i+1
     * So dp[i] = 2*dp[i-1]
     * However duplicate chars makes things harder for everyone except my pp, because if we extend the previous 10 characters by adding cur character,
     * it will double count some of the subsequences, specifically the subsequences that were added by adding the previous occurrence of the same character
     * e.g consider ycaxa
     * for first a, it added 4 subsequences to the previous 4 subsequences ending at c {"", y, c, yc} by adding 'a' {a, ya, ca, yca}
     * now for second a, it can make the same 4 duplicate subsequences too. so we have to remove the no of subsequences ending before the
     * previous occurrence of the current character
     * dp[i] = 2*dp[i-1] - dp[last_occurrence-1]
     * It's a bit tricky to visualize but try to solve it on paper for "ycaxaa" to get a clearer picture
     *
     * {@link ArithmeticSlices2Subsequence} {@link RussianDollEnvelopes} {@link ArithmeticSlices}
     */
    public int distinctSubseqIIOptimized(String S) {
        int MOD = 1_000_000_007;
        int n = S.length();
        int[] dp = new int[n + 1];
        dp[0] = 1;
        int[] lastOccurrence = new int[26]; //tracks last occurrence of each character
        Arrays.fill(lastOccurrence, -1);
        for (int i = 1; i < n + 1; i++) {
            dp[i] = (2 * dp[i - 1]) % MOD;
            char c = S.charAt(i - 1);
            if (lastOccurrence[c - 'a'] != -1) { //if this character is a duplicate, remove the subsequences doubly counted
                dp[i] -= dp[lastOccurrence[c - 'a'] - 1]; //remember that we need the count of elements present before the last occurrence of this char hence the -1
                dp[i] = (dp[i] + MOD) % MOD; //always remember whenever performing subtraction on modulo, make sure to make it +ve
            }
            lastOccurrence[c - 'a'] = i;
        }
        return dp[n] - 1;//-1 to remove empty subsequence
    }

    /**
     * Approach: Similar to solving other subsequences problems by trying to extend previously computed result
     * Care must be taken to handle duplicate characters. This is where I got confused and lost the war, as I couldn't figure out
     * how to make sense of the duplicates
     * e.g "aba"
     * [0] -> 1 {a}
     * [1] -> 2 {b, a(b)}
     * [2] -> 3 {a(a) (default +1 will handle it), b(aa), ab(a)}
     * when comparing a char with previous char, if it's same skip it. it will still work because of default +1
     * for remaining chars, it will act as adding the repeated chars e.g baa
     * <p>
     * {@link RussianDollEnvelopes}
     */
    public int distinctSubseqII(String S) {
        int MOD = 1_000_000_007;
        int n = S.length();
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (S.charAt(i) != S.charAt(j)) {
                    dp[i] = (dp[j] + dp[i]) % MOD;
                }
            }
            res = (res + dp[i]) % MOD;
        }
        return res;
    }
}

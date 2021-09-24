/**
 * <pre>
 * https://leetcode.com/problems/break-a-palindrome/
 *
 * Given a palindromic string of lowercase English letters palindrome, replace exactly one character with any lowercase English letter
 * so that the resulting string is not a palindrome and that it is the lexicographically smallest one possible.
 *
 * Return the resulting string. If there is no way to replace a character to make it not a palindrome, return an empty string.
 *
 * A string a is lexicographically smaller than a string b (of the same length) if in the first position where a and b differ,
 * a has a character strictly smaller than the corresponding character in b. For example, "abcc" is lexicographically smaller than "abcd"
 * because the first position they differ is at the fourth character, and 'c' is smaller than 'd'.
 *
 * Input: palindrome = "abccba"
 * Output: "aaccba"
 * Explanation: There are many ways to make "abccba" not a palindrome, such as "zbccba", "aaccba", and "abacba".
 * Of all the ways, "aaccba" is the lexicographically smallest.
 *
 * Input: palindrome = "a"
 * Output: ""
 * Explanation: There is no way to replace a single character to make "a" not a palindrome, so return an empty string.
 *
 * Input: palindrome = "aa"
 * Output: "ab"
 *
 * Input: palindrome = "aba"
 * Output: "abb"
 *
 * Constraints:
 * 1 <= palindrome.length <= 1000
 * palindrome consists of only lowercase English letters.
 * </pre>
 */
public class BreakAPalindrome {
    /**
     * Approach: Ad-hoc greedy, Since we are allowed to do only one replacement of a palindrome to convert it into a non-palindrome,
     * check the first half of the string for any character other than 'a', if present replace it by 'a' and return
     *
     * If the first half only contains 'a', then the second half will also contain all 'a', as it's a palindrome,
     * so the smallest string possible with just one replacement would be to convert the last char to 'b'.
     * In case the string is of odd length, we can't switch the middle character to 'a' as it will make the string palindrome.
     * Similarly, if we try to switch the middle char to 'b' or 'c', then it will be still greater than converting the last char to 'b'
     *
     * I solved this question by considering three cases, even length, odd length and invalid cases and then went on to solve for each case.
     * Happy to solve this on my own.
     */
    public String breakPalindrome(String palindrome) {
        char[] res = palindrome.toCharArray();
        int n = res.length;
        if (n == 1) {
            return "";
        } else {
            for (int i = 0; i < n / 2; i++) { //check first half for any non 'a' character
                if (res[i] != 'a') {
                    res[i] = 'a';
                    return new String(res);
                }
            }
            res[n - 1] = 'b';
            return new String(res);
        }
    }
}

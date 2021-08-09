/**
 * <pre>
 * https://leetcode.com/problems/check-if-string-is-a-prefix-of-array/
 *
 * Given a string s and an array of strings words, determine whether s is a prefix string of words.
 *
 * A string s is a prefix string of words if s can be made by concatenating the first k strings in words for some positive k no larger than words.length.
 *
 * Return true if s is a prefix string of words, or false otherwise.
 *
 * Input: s = "iloveleetcode", words = ["i","love","leetcode","apples"]
 * Output: true
 * Explanation:
 * s can be made by concatenating "i", "love", and "leetcode" together.
 *
 * Input: s = "iloveleetcode", words = ["apples","i","love","leetcode"]
 * Output: false
 * Explanation:
 * It is impossible to make s using a prefix of arr.
 *
 * Constraints:
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 20
 * 1 <= s.length <= 1000
 * words[i] and s consist of only lowercase English letters.
 * </pre>
 */
public class CheckIfStringIsAPrefixOfArray {
    /**
     * Approach: Two pointers, need to make sure that the entire word is used as a prefix.
     *
     * {@link CheckArrayFormationThroughConcatenation}
     */
    public boolean isPrefixString(String s, String[] words) {
        int s_index = 0, words_index = 0;
        while (words_index < words.length) {
            for (char actual : words[words_index].toCharArray()) {
                if (s_index == s.length()) { //if s is finished but the current word isn't, then it's a partial prefix
                    return false;
                }
                int expected = s.charAt(s_index);
                if (actual != expected) { //characters mismatch
                    return false;
                }
                s_index++;
            }
            //once an entire word is finish, if the s is finished as well, then it's a valid prefix
            if (s_index == s.length()) {
                return true;
            }
            words_index++;
        }
        return false;
    }
}

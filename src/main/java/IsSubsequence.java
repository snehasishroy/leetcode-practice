/**
 * https://leetcode.com/problems/is-subsequence/
 * <p>
 * Given a string s and a string t, check if s is subsequence of t.
 * <p>
 * A subsequence of a string is a new string which is formed from the original string by deleting some (can be none) of
 * the characters without disturbing the relative positions of the remaining characters. (ie, "ace" is a subsequence of "abcde" while "aec" is not).
 * <p>
 * Follow up:
 * If there are lots of incoming S, say S1, S2, ... , Sk where k >= 1B, and you want to check one by one to see if T has its subsequence.
 * In this scenario, how would you change your code?
 */
public class IsSubsequence {
    /**
     * For the follow up question, create a map of character present in target to the list of indices they are present in
     * iterate the subsequence and for each character check if you can find an index in the previous map, with a value just greater (+1)
     * than the previous character index location in target
     * https://leetcode.com/problems/is-subsequence/discuss/87302/Binary-search-solution-for-follow-up-with-detailed-comments
     *
     * {@link LongPressedName} {@link ShortestWayToFormString}
     */
    public boolean isSubsequence(String subsequence, String target) {
        if (subsequence.isEmpty()) {
            return true;
        }
        if (subsequence.length() > target.length()) {
            return false;
        }
        int s_index = 0, t_index = 0;
        while (s_index < subsequence.length() && t_index < target.length()) {
            if (subsequence.charAt(s_index) == target.charAt(t_index)) {
                //if both the characters match, increment the indexes of both the strings
                s_index++;
                t_index++;
            } else {
                //keep looking at the next index of the target
                t_index++;
            }
        }
        return s_index == subsequence.length(); //verify whether all the characters of subsequence have been matched or not
    }
}

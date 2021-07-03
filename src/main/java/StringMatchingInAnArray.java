import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * https://leetcode.com/problems/string-matching-in-an-array/
 *
 * Given an array of string words. Return all strings in words which is substring of another word in any order.
 *
 * Input: words = ["mass","as","hero","superhero"]
 * Output: ["as","hero"]
 * Explanation: "as" is substring of "mass" and "hero" is substring of "superhero".
 * ["hero","as"] is also a valid answer.
 *
 * Input: words = ["leetcode","et","code"]
 * Output: ["et","code"]
 * Explanation: "et", "code" are substring of "leetcode".
 *
 * Input: words = ["blue","green","bu"]
 * Output: []
 *
 * Constraints:
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 30
 * words[i] contains only lowercase English letters.
 * It's guaranteed that words[i] will be unique.
 * </pre>
 */
public class StringMatchingInAnArray {
    /**
     * Approach: Brute Force. TimeComplexity: O(n^3) can be reduced to O(n^2) using rolling hash or KMP
     *
     * Can be optimized to O(n*w*w) where w is the max length of the word by using Tries --- by maintaining reverse indexes and counter
     * and performing search on them. In each trie node maintain a counter indicating how many times this prefix was found in the dictionary.
     *
     * ie. for given words {"mass", "as"}
     * Insert into trie all substrings starting from ith index for each word ie. for "mass", insert {"mass", "ass", "ss", "s"}
     * for "as", insert {"as", "s"}
     *
     * Now for each word, traverse the trie again and check the counter at each node. If at any node, counter is 1, no point in checking further.
     * If till the end of the word, counter is >=2, this means this word is present as a substring in another word.
     * Trie technique is similar to {@link PrefixAndSuffixSearch}
     * Was not able to think of trie solution on my own. Stumped!
     * Would be a great interview question.
     *
     * Although this question is marked as easy but it can be switched to medium (Rolling Hash implementation) or hard (Trie implementation) if constraints were strict.
     * {@link NumberOfMatchingSubsequences}
     */
    public List<String> stringMatching(String[] words) {
        int n = words.length;
        List<String> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && words[j].contains(words[i])) {
                    res.add(words[i]);
                    break;
                }
            }
        }
        return res;
    }
}
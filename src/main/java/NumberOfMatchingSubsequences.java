import java.util.*;

/**
 * https://leetcode.com/problems/number-of-matching-subsequences/
 * <p>
 * Given string S and a dictionary of words words, find the number of words[i] that is a subsequence of S.
 * <p>
 * Input:
 * S = "abcde"
 * words = ["a", "bb", "acd", "ace"]
 * Output: 3
 * Explanation: There are three words in words that are a subsequence of S: "a", "acd", "ace".
 * <p>
 * Note:
 * All words in words and S will only consists of lowercase letters.
 * The length of S will be in the range of [1, 50000].
 * The length of words will be in the range of [1, 5000].
 * The length of words[i] will be in the range of [1, 50].
 * <p>
 * Note:
 * All words in words and S will only consists of lowercase letters.
 * The length of S will be in the range of [1, 50000].
 * The length of words will be in the range of [1, 5000].
 * The length of words[i] will be in the range of [1, 50].
 */
public class NumberOfMatchingSubsequences {
    /**
     * Approach: Maintain a mapping of character to the words that are waiting for that character. Iterate string and for each character,
     * check which words can be satisfied, once the initial char is satisfied of a word, create a new mapping for the next character of the word.
     * e.g  ["a", "bb", "acd", "ace"], "a"->{"a","acd","ace"}, "b" -> {"bb"}
     * this mapping means if 'a' is found in the string, it can satisfy three words
     * once a is seen, now "a" is completely satisfied, "acd" and "ace" will need to wait for "c". So updated mappings are
     * "b" -> {"bb"}, "c" -> {"cd","ce"}  //"a" has been satisfied and there are no words that require a
     * <p>
     * {@link IsSubsequence} {@link MinimumWindowSubsequence} {@link LongestWordInDictionaryByDeleting} {@link SplitArrayIntoConsecutiveSubsequences} {@link StringMatchingInAnArray}
     * {@link ShortestWayToFormString}
     * This was asked in my google fourth interview round :)
     */
    public int numMatchingSubseqOptimized(String S, String[] words) {
        Map<Character, LinkedList<String>> map = new HashMap<>(); //mapping of character -> list of words waiting for the character
        for (String word : words) {
            map.computeIfAbsent(word.charAt(0), __ -> new LinkedList<>()).addLast(word);
        }
        int result = 0;
        for (char c : S.toCharArray()) {
            if (map.containsKey(c)) {
                LinkedList<String> candidates = map.get(c);
                map.remove(c); //need to remove current mapping as it might be possible that no words require this character
                while (!candidates.isEmpty()) {
                    String candidate = candidates.removeFirst();
                    if (candidate.length() == 1) { //if all characters of this word has been satisfied, a valid subsequence is found
                        result++;
                    } else {
                        String remaining = candidate.substring(1); //this step can be avoided if we maintain an object of {word, index}
                        map.computeIfAbsent(remaining.charAt(0), __ -> new LinkedList<>()).addLast(remaining);
                        //create a new mapping with the next character of the remaining substring
                    }
                }
            }
        }
        return result;
    }

    /**
     * Approach: Keep track of occurrences of each character in the target string. For each word, check if you can find an index > prev index
     * Not a super fast approach, but was my original implementation. Took ~150 ms
     * Checking whether each word is a subsequence separately using {@link IsSubsequence} times out !
     */
    public int numMatchingSubseq(String S, String[] words) {
        int res = 0;
        List<List<Integer>> indices = new ArrayList<>(); //list containing list of indices at which a char is present
        for (int i = 0; i < 26; i++) {
            indices.add(new ArrayList<>());
        }
        for (int i = 0; i < S.length(); i++) {
            indices.get(S.charAt(i) - 'a').add(i);
        }
        for (String word : words) {
            //check if you can any index greater than previousIndex for the current word
            if (recur(word, 0, -1, indices)) {
                res++;
            }
        }
        return res;
    }

    private boolean recur(String word, int wordIndex, int previousIndex, List<List<Integer>> indices) {
        if (wordIndex == word.length()) {
            return true;
        } else {
            char c = word.charAt(wordIndex);
            //sauce of the algorithm, use binary search to find an index >= (previousIndex+1)
            int candidateIndex = Collections.binarySearch(indices.get(c - 'a'), previousIndex + 1);
            if (candidateIndex < 0) {
                candidateIndex *= -1;
                candidateIndex -= 1;
            }
            if (candidateIndex == indices.get(c - 'a').size()) { //edge case, if candidate index is out of bounds
                return false;
            }
            //now index at candidateIndex would be the previousIndex
            return recur(word, wordIndex + 1, indices.get(c - 'a').get(candidateIndex), indices);
        }
    }
}

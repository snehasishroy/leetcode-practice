import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/prefix-and-suffix-search/
 * <p>
 * Design a special dictionary which has some words and allows you to search the words in it by a prefix and a suffix.
 * <p>
 * Implement the WordFilter class:
 * <p>
 * WordFilter(string[] words) Initializes the object with the words in the dictionary.
 * f(string prefix, string suffix) Returns the index of the word in the dictionary which has the prefix prefix and the suffix suffix.
 * If there is more than one valid index, return the largest of them. If there is no such word in the dictionary, return -1.
 * <p>
 * Input
 * ["WordFilter", "f"]
 * [[["apple"]], ["a", "e"]]
 * Output
 * [null, 0]
 * <p>
 * Explanation
 * WordFilter wordFilter = new WordFilter(["apple"]);
 * wordFilter.f("a", "e"); // return 0, because the word at index 0 has prefix = "a" and suffix = 'e".
 */
public class PrefixAndSuffixSearch {
    Trie prefixRoot = new Trie();
    Trie suffixRoot = new Trie();

    /**
     * <pre>
     * Approach: Maintain two tries, one that stores prefixes and other that stores suffixes.
     * As trie only stores prefixes, prior to storing suffixes, reverse the word and then store in suffix trie
     *
     * So when asked to find a pattern, find that prefix in prefix trie as well as the suffix in suffix trie.
     * Perform DFS on both the tries to find all the valid indexes that satisfies the query.
     *
     * Now we need to find the largest index amongst the common indexes between these two sets.
     * This can be achieved by finding set intersection between those two, which is a costly operation (linear)
     * Time: ~470 ms
     *
     * We can optimize it a bit by avoiding the DFS operation by storing set<integer> of valid indexes directly at trie nodes
     * This is similar to {@link DesignSearchAutocompleteSystem}
     * Then we can perform set intersection between two sets. It times out.
     * The reason is the size of set stored at the nodes would be much larger in the timed out code, as we are storing lot of redundant information per node.
     * ie. if words are repeated ie. "apple", "apple", we would store {0,1} per every node but we are interested only in the latest node.
     * Trick to solve this would be to avoid duplicates ie. use a hashmap of <string, integer>, value being the latest index.
     * Using this trick gives AC in similar time as the reverse index code, however during actual interviews, it's very important to explain the interviewers
     * the trade off in both the approaches, as the reverse indexes is very beneficial if no of find operations highly exceeds no of insert operations
     *
     * Another approach is to just maintain one trie e.g consider apple
     * Store words like "e{apple", "le{apple", "ple{apple", "pple{apple", "apple{apple" i.e. maintain a reverse index
     * So if you query for prefix = app and suffix = le, search for "le{app" in trie
     * Space required will be much larger than my current implementation but there is always a trade off
     *
     * This was asked in Google onsite interview
     * See {@link PrefixAndSuffixSearchOptimized} {@link StringMatchingInAnArray}
     * </pre>
     */
    public PrefixAndSuffixSearch(String[] words) {
        for (int i = 0; i < words.length; i++) {
            insertIntoTrie(words[i], prefixRoot, i);
            //reverse the word and insert into suffix trie
            String reversed = reverseWord(words[i]);
            insertIntoTrie(reversed, suffixRoot, i);
        }
    }

    private void insertIntoTrie(String word, Trie root, int idx) {
        Trie temp = root;
        for (char c : word.toCharArray()) {
            if (temp.letters[c - 'a'] == null) {
                temp.letters[c - 'a'] = new Trie();
            }
            temp = temp.letters[c - 'a'];
        }
        temp.index = idx;
    }

    /**
     * Get all indices that matches prefix as well as suffix
     * Since there can be a lot of indices, need to use set to find intersection quickly
     * If there are multiple indices, need to return the largest index, so find the max amongst the intersection
     */
    public int find(String prefix, String suffix) {
        Set<Integer> prefixIndices = new HashSet<>();
        Trie temp = prefixRoot;
        for (char c : prefix.toCharArray()) {
            if (temp.letters[c - 'a'] == null) {
                return -1;
            }
            temp = temp.letters[c - 'a'];
        }
        //start dfs from the trie node containing the last char of the prefix
        DFS(temp, prefixIndices);
        //we stored words in reverse order in suffix trie, so we need to reverse the provided suffix so that we can perform lookup
        String reversedSuffix = reverseWord(suffix);
        temp = suffixRoot;
        for (char c : reversedSuffix.toCharArray()) {
            if (temp.letters[c - 'a'] == null) {
                return -1;
            }
            temp = temp.letters[c - 'a'];
        }
        Set<Integer> suffixIndices = new HashSet<>();
        DFS(temp, suffixIndices);
        //find the intersection ie. common indices, linear time complexity
        prefixIndices.retainAll(suffixIndices);
        //find the largest index
        int largestIndex = Integer.MIN_VALUE;
        for (int prefixIndex : prefixIndices) {
            largestIndex = Math.max(prefixIndex, largestIndex);
        }
        return largestIndex;
    }

    private void DFS(Trie root, Set<Integer> indices) {
        if (root.index != -1) {
            //found a valid word, store it's index
            indices.add(root.index);
            //notice that we are not stopping the recursion here in order to consider words like "apple", "apples"
        }
        for (int i = 0; i < 26; i++) {
            if (root.letters[i] != null) {
                DFS(root.letters[i], indices);
            }
        }
    }

    private String reverseWord(String input) {
        char[] chars = input.toCharArray();
        int begin = 0, end = chars.length - 1;
        while (begin < end) {
            char temp = chars[begin];
            chars[begin] = chars[end];
            chars[end] = temp;
            begin++;
            end--;
        }
        return new String(chars);
    }

    private static class Trie {
        int index = -1;
        Trie[] letters = new Trie[26];
    }
}

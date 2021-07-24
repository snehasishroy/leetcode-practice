import java.util.*;

/**
 * https://leetcode.com/problems/word-ladder/
 * Given two words (beginWord and endWord), and a dictionary's word list, find the length of shortest transformation sequence from beginWord to endWord, such that:
 * <p>
 * Only one letter can be changed at a time.
 * Each transformed word must exist in the word list.
 * Note:
 * <p>
 * Return 0 if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * You may assume no duplicates in the word list.
 * You may assume beginWord and endWord are non-empty and are not the same.
 * <p>
 * Input:
 * beginWord = "hit",
 * endWord = "cog",
 * wordList = ["hot","dot","dog","lot","log","cog"]
 * <p>
 * Output: 5
 * Explanation: As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length 5.
 */
public class WordLadder {

    /**
     * Approach: Preprocess all the words in dictionary and map them to their generic term
     * i.e hot can be mapped to *ot, h*t, ho*
     * When doing a BFS, look up in the map for the finding the next nodes with the same generic key
     * i.e. if the current node in BFS is dot, look in map for key *ot, d*t, do*
     * You would need a visited set to avoid duplicates, but since we already have a map which we utilize to find out next nodes,
     * we can just remove the elements from the map so that we can never pick it up again
     * Learned this trick from {@link WordSearch2}
     * <p>
     * This preprocessing helps in quickly finding all the next level nodes without generating all possible combinations
     * and then checking whether it's valid or not
     *
     * {@link StringsDifferByOneCharacter} {@link WordLadder2}
     */
    public int ladderLengthOptimized(String beginWord, String endWord, List<String> wordList) {
        HashMap<String, Set<String>> options = new HashMap<>();
        for (String word : wordList) {
            for (int i = 0; i < word.length(); i++) {
                char[] chars = word.toCharArray();
                chars[i] = '*';
                options.computeIfAbsent(new String(chars), __ -> new HashSet<>()).add(word);
                //cool trick to init the hashset if empty else return the existing set and then add the word in one line
            }
        }
        return BFS(beginWord, endWord, options);
    }

    private int BFS(String beginWord, String endWord, HashMap<String, Set<String>> options) {
        ArrayDeque<String> queue = new ArrayDeque<>();
        queue.add(beginWord);
        int hops = 1; //need to init from 1 because even the start word is considered in the total hops
        while (!queue.isEmpty()) {
            int size = queue.size();
            hops++;
            for (int i = 0; i < size; i++) {
                String head = queue.remove();
                for (int index = 0; index < head.length(); index++) {
                    char[] chars = head.toCharArray();
                    chars[index] = '*';
                    Set<String> nextHop = options.getOrDefault(new String(chars), new HashSet<>());
                    for (String adjacent : nextHop) {
                        if (adjacent.equals(endWord)) {
                            return hops;
                        }
                        queue.add(adjacent);
                    }
                    nextHop.clear(); //avoid duplicates
                }
            }
        }
        return 0;
    }

    /**
     * My initial approach, but I chickened out during the implementation thinking that it's too complex for a medium problem
     * Then I found out that the problem itself is mis-categorized and should be hard
     * For each index, generate all the possible set of characters available to switch to.
     * <p>
     * Now at any step during BFS, for each indices, try generating a new string by replacing the value at that index to
     * all the available characters at that index. Then check whether the transformation is valid by checking whether it exists in dictionary
     * <p>
     * In order to avoid duplicates, we need a visited set but instead can just remove that word from dictionary to not consider it again.
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        List<Set<Character>> options = new ArrayList<>(); //set of characters available at all indices
        for (int i = 0; i < beginWord.length(); i++) {
            options.add(new HashSet<>());
        }
        for (String word : wordList) {
            for (int i = 0; i < word.length(); i++) {
                options.get(i).add(word.charAt(i));
            }
        }
        return BFS(beginWord, endWord, options, new HashSet<>(wordList));
    }

    private int BFS(String beginWord, String endWord, List<Set<Character>> options, Set<String> dictionary) {
        ArrayDeque<String> queue = new ArrayDeque<>();
        queue.add(beginWord);
        int hops = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            hops++;
            for (int i = 0; i < size; i++) {
                String head = queue.remove();
                for (int index = 0; index < beginWord.length(); index++) {
                    Set<Character> characters = options.get(index); //choices available
                    for (char c : characters) {
                        //try all possible choices and then check if valid word
                        char[] chars = head.toCharArray();
                        chars[index] = c;
                        String possibleWord = new String(chars);
                        if (possibleWord.equals(endWord)) {
                            return hops;
                        } else if (dictionary.contains(possibleWord)) {
                            queue.add(possibleWord);
                            dictionary.remove(possibleWord); //avoid duplicates
                        }
                    }
                }
            }
        }
        return 0;
    }
}

import java.util.*;

/**
 * <pre>
 * https://leetcode.com/problems/word-ladder-ii/
 *
 * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
 *
 * Every adjacent pair of words differs by a single letter.
 * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
 * sk == endWord
 * Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation sequences from beginWord to endWord, or an empty list if no such sequence exists.
 * Each sequence should be returned as a list of the words [beginWord, s1, s2, ..., sk].
 *
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
 * Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
 * Explanation: There are 2 shortest transformation sequences:
 * "hit" -> "hot" -> "dot" -> "dog" -> "cog"
 * "hit" -> "hot" -> "lot" -> "log" -> "cog"
 *
 * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log"]
 * Output: []
 * Explanation: The endWord "cog" is not in wordList, therefore there is no valid transformation sequence.
 *
 * Constraints:
 * 1 <= beginWord.length <= 5
 * endWord.length == beginWord.length
 * 1 <= wordList.length <= 1000
 * wordList[i].length == beginWord.length
 * beginWord, endWord, and wordList[i] consist of lowercase English letters.
 * beginWord != endWord
 * All the words in wordList are unique.
 * </pre>
 */
public class WordLadder2 {
    /**
     * Approach: BFS with pruning, Similar to {@link WordLadder}, maintain reverse indexes but we need to return all paths that lead to destination in shortest distance.
     * So prune the graph if the current path length exceeds the current distance achieved to reach target.
     *
     * Tricky thing to avoid duplicates is to clear all keys that are present on the same level, after populating all the nodes of the next level.
     * In {@link WordLadder} we remove the key as soon as we populate the nodes on the next level reachable from current node.
     * But in this problem we need to find out all the paths that leads to destination in shortest distance, so we remove the mapping after we are done with generating all the nodes
     * of next level.
     *
     * Without this pruning, we will get TLE. This also works because if we are able to currently reach nodes of next level in x distance, if we visit them again, the distance surely will be > x because of BFS,
     * so there is no point in keeping such nodes. Keeping them will only make the size of BFS queue larger.
     * Happy to solve it on my own.
     *
     * {@link CheapestFlightWithinKStop} {@link TilingARectangleWithFewestSquares}
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        HashMap<String, Set<String>> options = new HashMap<>(); //options -> words
        //if word is abc, create mappings {*bc->abc, a*c->abc, ab*->abc}
        for (String word : wordList) {
            for (int i = 0; i < word.length(); i++) {
                char[] chars = word.toCharArray();
                chars[i] = '*';
                options.computeIfAbsent(new String(chars), __ -> new HashSet<>()).add(word);
            }
        }
        return BFS(beginWord, endWord, options);
    }

    private List<List<String>> BFS(String beginWord, String endWord, HashMap<String, Set<String>> options) {
        List<List<String>> result = new ArrayList<>();
        ArrayDeque<Node> queue = new ArrayDeque<>();
        queue.add(new Node(beginWord, Arrays.asList(beginWord)));
        while (!queue.isEmpty()) {
            int size = queue.size();
            Set<String> mappingsToClear = new HashSet<>(); //keep track of mappings to clear after all nodes of next level are populated
            for (int i = 0; i < size; i++) {
                Node head = queue.remove();
                String curWord = head.currentWord;
                List<String> curPath = head.path;
                for (int index = 0; index < curWord.length(); index++) {
                    char[] chars = curWord.toCharArray();
                    chars[index] = '*';
                    String key = new String(chars);
                    mappingsToClear.add(key);
                    Set<String> nextHop = options.getOrDefault(key, new HashSet<>());
                    for (String adjacent : nextHop) { //nodes of next level
                        List<String> newPath = new ArrayList<>(curPath);
                        newPath.add(adjacent); //create a new path and add the new adjacent node to it
                        if (adjacent.equals(endWord)) {
                            if (result.isEmpty() || result.get(0).size() == newPath.size()) { //another path with same distance found
                                result.add(newPath);
                            } else if (result.get(0).size() > newPath.size()) { //current path is of shorter distance, remove the previous paths found
                                result.clear();
                                result.add(newPath);
                            }
                        } else if (result.isEmpty() || result.get(0).size() > newPath.size()) { //optional pruning, only proceed if current path is better than the path found
                            queue.add(new Node(adjacent, newPath));
                        }
                    }
                }
            }
            //remove mappings so that they can't be visited again. Will get TLE if not done
            for (String key : mappingsToClear) {
                options.remove(key);
            }
        }
        return result;
    }

    private static class Node {
        String currentWord;
        List<String> path; //sequence of all nodes visited in order to reach current word
        public Node(String currentWord, List<String> path) {
            this.currentWord = currentWord;
            this.path = path;
        }
    }
}

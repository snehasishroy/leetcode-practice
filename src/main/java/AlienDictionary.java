import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/alien-dictionary/ Premium
 * <p>
 * There is a new alien language which uses the latin alphabet. However, the order among letters are unknown to you.
 * You receive a list of non-empty words from the dictionary, where words are sorted lexicographically by the rules of this new language.
 * Derive the order of letters in this language.
 * <pre>
 * Input:
 * [
 *   "wrt",
 *   "wrf",
 *   "er",
 *   "ett",
 *   "rftt"
 * ]
 * Output: "wertf"
 *
 * Input:
 * [
 *   "z",
 *   "x"
 * ]
 * Output: "zx"
 *
 * Input:
 * [
 *   "z",
 *   "x",
 *   "z"
 * ]
 * Output: ""
 * Explanation: The order is invalid, so return "".
 * </pre>
 */
public class AlienDictionary {
    /**
     * Approach: Create a directed graph and perform a topological sort with cycle detection to detect invalid orderings
     * Provided words are sorted lexicographically i.e. the letters inside the word are not sorted only the words are.
     * So compare two words and find the first mismatch, create an edge from that mismatch.
     * <p>
     * Care must be taken to handle invalid cases like {"abcd", "abc"}
     * <p>
     * Also care must be taken to place characters at the end whose order have not been identified i.e {"ae", "afg"}
     * We can only confirm that e < f but we can't say anything about 'a' and 'g' so we can place them anywhere and return it
     * <p>
     * {@link CourseSchedule2} {@link CustomSortString}
     */
    boolean incorrectOrder;

    public String alienOrder(String[] words) {
        if (words.length == 0) {
            return "";
        }
        HashMap<Character, Set<Character>> graph = buildGraph(words);
        if (incorrectOrder) { //if input contains a longer word with prefix first than word with shorter prefix {"abcd", "abc"}
            return "";
        }
        Set<Character> present = new HashSet<>();
        //required to find all the characters present, will use them to place the present but not mapped characters, at the end
        for (String word : words) {
            for (char c : word.toCharArray()) {
                present.add(c);
            }
        }
        ArrayDeque<Character> stack = new ArrayDeque<>();
        boolean[] visited = new boolean[26];
        boolean[] toDo = new boolean[26]; //detects cycle in topological sort
        for (char c = 'a'; c <= 'z'; c++) {
            if (!graph.get(c).isEmpty()) {
                if (!topologicalSort(graph, stack, c, visited, toDo)) {
                    //cycle detected
                    return "";
                }
            }
        }
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            //create string from topological sort
            res.append(stack.pop());
        }
        for (char c = 'a'; c <= 'z'; c++) {
            if (present.contains(c) && !visited[c - 'a']) {
                //place characters that are present in the input but whose order can't be identified
                //can place these characters at any position
                res.append(c);
            }
        }
        return res.toString();
    }

    //returns false if cycle detected
    private boolean topologicalSort(HashMap<Character, Set<Character>> graph, ArrayDeque<Character> stack, char c, boolean[] visited, boolean[] toDo) {
        if (visited[c - 'a']) {
            return true;
        } else if (toDo[c - 'a']) { //found a node that is in processing, can happen only if it's a cycle
            return false;
        }
        toDo[c - 'a'] = true; //mark the node as under processing
        for (char neighbor : graph.get(c)) {
            if (!topologicalSort(graph, stack, neighbor, visited, toDo)) {
                return false;
            }
        }
        toDo[c - 'a'] = false; //mark the node as processed
        visited[c - 'a'] = true;
        stack.push(c); //push the node to stack only after its required dependencies are processed aka postorder traversal
        return true;
    }

    private HashMap<Character, Set<Character>> buildGraph(String[] words) {
        HashMap<Character, Set<Character>> graph = new HashMap<>(); //need a set to avoid duplicate edges
        for (char c = 'a'; c <= 'z'; c++) {
            graph.put(c, new HashSet<>());
        }
        //as per discuss solutions, we can do fine by just comparing adjacent words
        for (int i = 0; i < words.length - 1; i++) { //compare current word with lexicographically greater words to extract out the edges
            String smaller = words[i];
            for (int j = i + 1; j < words.length; j++) {
                String greater = words[j];
                boolean mismatchFound = false; //indicates whether a mismatch was found between two candidates
                for (int idx = 0; idx < Math.min(smaller.length(), greater.length()); idx++) {
                    if (smaller.charAt(idx) != greater.charAt(idx)) {
                        mismatchFound = true;
                        graph.get(smaller.charAt(idx)).add(greater.charAt(idx));
                        break;
                    }
                }
                if (!mismatchFound && smaller.length() > greater.length()) { //if no mismatch found, check for invalid ordering
                    incorrectOrder = true;
                    return new HashMap<>();
                }
            }
        }
        return graph;
    }
}

import java.util.HashMap;
import java.util.Map;

/**
 * Approach: https://leetcode.com/problems/map-sum-pairs/
 *
 * Implement the MapSum class:
 *
 * MapSum() Initializes the MapSum object.
 * void insert(String key, int val) Inserts the key-val pair into the map. If the key already existed, the original key-value pair will be overridden to the new one.
 * int sum(string prefix) Returns the sum of all the pairs' value whose key starts with the prefix.
 *
 * Input
 * ["MapSum", "insert", "sum", "insert", "sum"]
 * [[], ["apple", 3], ["ap"], ["app", 2], ["ap"]]
 * Output
 * [null, null, 3, null, 5]
 *
 * Explanation
 * MapSum mapSum = new MapSum();
 * mapSum.insert("apple", 3);
 * mapSum.sum("ap");           // return 3 (apple = 3)
 * mapSum.insert("app", 2);
 * mapSum.sum("ap");           // return 5 (apple + app = 3 + 2 = 5)
 *
 * Constraints:
 * 1 <= key.length, prefix.length <= 50
 * key and prefix consist of only lowercase English letters.
 * 1 <= val <= 1000
 * At most 50 calls will be made to insert and sum.
 */
public class MapSumPairs {
    Trie root = new Trie();

    /**
     * Approach: Trie, Keep the useful information directly in trie nodes. Tricky thing is to handle the update the value associated
     * with an existing key. For that, we can keep current value of the key in every trie node and decrement it before updating the total sum till this node.
     * Or we can keep a global map, and use that to find offset to apply to each node ie. if the key is "apple" and existing value is 10 and new value is 3,
     * so we have to apply an offset of -7 to each node in all the nodes lying in the path of "apple"
     *
     * Latter approach uses less memory.
     *
     * {@link DesignSearchAutocompleteSystem}
     */
    public MapSumPairs() {

    }

    public void insert(String key, int val) {
        Trie temp = root;
        for (char c : key.toCharArray()) {
            if (temp.children[c - 'a'] == null) {
                temp.children[c - 'a'] = new Trie();
            }
            temp = temp.children[c - 'a'];
            int existingValue = temp.keys.getOrDefault(key, 0); //find the existing value associated with the key
            temp.keys.put(key, val);
            temp.totalSum -= existingValue;
            temp.totalSum += val;
        }
    }

    public int sum(String prefix) {
        Trie temp = root;
        for (char c : prefix.toCharArray()) {
            if (temp.children[c - 'a'] == null) {
                return 0;
            }
            temp = temp.children[c - 'a'];
        }
        return temp.totalSum;
    }

    private static class Trie {
        Trie[] children = new Trie[26];
        int totalSum;
        Map<String, Integer> keys = new HashMap<>();
    }
}

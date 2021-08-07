import common.Node;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * https://leetcode.com/problems/n-ary-tree-level-order-traversal/
 *
 * Given an n-ary tree, return the level order traversal of its nodes' values.
 *
 * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
 *
 * Input: root = [1,null,3,2,4,null,5,6]
 * Output: [[1],[3,2,4],[5,6]]
 *
 * Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
 * Output: [[1],[2,3,4,5],[6,7,8,9,10],[11,12,13],[14]]
 *
 * Constraints:
 * The height of the n-ary tree is less than or equal to 1000
 * The total number of nodes is between [0, 10^4]
 * </pre>
 */
public class NaryTreeLevelOrderTraversal {
    /**
     * Approach: BFS
     */
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> res = new ArrayList<>();
        ArrayDeque<Node> queue = new ArrayDeque<>();
        if (root != null) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> currentLevel = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Node head = queue.remove();
                currentLevel.add(head.val);
                queue.addAll(head.children);
            }
            res.add(currentLevel);
        }
        return res;
    }
}

import common.TreeNode;

/**
 * <pre>
 * https://leetcode.com/problems/count-good-nodes-in-binary-tree/
 *
 * Given a binary tree root, a node X in the tree is named good if in the path from root to X there are no nodes with a value greater than X.
 *
 * Return the number of good nodes in the binary tree.
 *
 * Input: root = [3,1,4,3,null,1,5]
 * Output: 4
 * Explanation: Nodes in blue are good.
 * Root Node (3) is always a good node.
 * Node 4 -> (3,4) is the maximum value in the path starting from the root.
 * Node 5 -> (3,4,5) is the maximum value in the path
 * Node 3 -> (3,1,3) is the maximum value in the path.
 *
 * Input: root = [3,3,null,4,2]
 * Output: 3
 * Explanation: Node 2 -> (3, 3, 2) is not good, because "3" is higher than it.
 *
 * Input: root = [1]
 * Output: 1
 * Explanation: Root is considered as good.
 *
 * Constraints:
 * The number of nodes in the binary tree is in the range [1, 10^5].
 * Each node's value is between [-10^4, 10^4].
 * </pre>
 */
public class CountGoodNodesInBinaryTree {
    /**
     * Approach: Recursion, Keep track of the maximum value found in the path so far during recursion.
     * <p>
     * {@link HeightBalancedBinaryTree}
     */
    public int goodNodes(TreeNode root) {
        return recur(root, Integer.MIN_VALUE);
    }

    private int recur(TreeNode root, int maxValue) {
        if (root == null) {
            return 0;
        } else if (root.val < maxValue) {
            return recur(root.left, maxValue) + recur(root.right, maxValue); //pass the existing max value found so far
        } else {
            return 1 + recur(root.left, root.val) + recur(root.right, root.val); //current root is the new max value
        }
    }
}

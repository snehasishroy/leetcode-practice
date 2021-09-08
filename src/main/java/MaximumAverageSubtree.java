import common.TreeNode;
import javafx.util.Pair;

/**
 * <pre>
 * https://leetcode.com/problems/maximum-average-subtree/ Premium
 *
 * Given the root of a binary tree, return the maximum average value of a subtree of that tree. Answers within 10^-5 of the actual answer will be accepted.
 *
 * A subtree of a tree is any node of that tree plus all its descendants.
 *
 * The average value of a tree is the sum of its values, divided by the number of nodes.
 *
 * Input: root = [5,6,1]
 * Output: 6.00000
 * Explanation:
 * For the node with value = 5 we have an average of (5 + 6 + 1) / 3 = 4.
 * For the node with value = 6 we have an average of 6 / 1 = 6.
 * For the node with value = 1 we have an average of 1 / 1 = 1.
 * So the answer is 6 which is the maximum.
 *
 * Input: root = [0,null,1]
 * Output: 1.00000
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 10^4].
 * 0 <= Node.val <= 10^5
 * </pre>
 */
public class MaximumAverageSubtree {
    double maxAverage;

    /**
     * Approach: DFS, Do a postorder traversal and keep track of sum of current subtree and the count of nodes.
     * Use that to calculate average in each subtree.
     */
    public double maximumAverageSubtree(TreeNode root) {
        recur(root);
        return maxAverage;
    }

    private Pair<Integer, Integer> recur(TreeNode root) { // pair of sum of subtree, count of nodes
        if (root == null) {
            return new Pair<>(0, 0);
        } else {
            Pair<Integer, Integer> left = recur(root.left);
            Pair<Integer, Integer> right = recur(root.right);
            int sum = left.getKey() + right.getKey() + root.val;
            int count = left.getValue() + right.getValue() + 1;
            double curAverage = ((double) sum) / count;
            maxAverage = Math.max(maxAverage, curAverage); //update the max average found
            return new Pair<>(sum, count);
        }
    }
}

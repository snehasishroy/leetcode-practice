import common.TreeNode;

/**
 * https://leetcode.com/problems/equal-tree-partition/ Premium
 * <p>
 * Given a binary tree with n nodes, your task is to check if it's possible to partition the tree to two trees which have the equal sum of values
 * after removing exactly one edge on the original tree.
 * <p>
 * Input:
 * <pre>
 *     5
 *    / \
 *   10 10
 *     /  \
 *    2   3
 * </pre>
 * Output: True
 */
public class EqualTreePartition {
    boolean canPartition;

    /**
     * Approach: Two pass algorithm, first find the total sum and then at each parent, check if the subtree sum is half of the total sum.
     * If yes, that edge to the subtree can be cut. Care must be taken for the return value during partitioning.
     * <p>
     * In my initial implementation I returned 0 for null nodes, but since tree can contain subtree sum as 0, it incorrectly considered it as a valid partition
     * Hence returned null sum for null nodes
     * <p>
     * {@link alternate.PartitionZeroOneTree} {@link MaximumProductOfSplittedBinaryTree} for similar partition related problem
     */
    public boolean checkEqualTree(TreeNode root) {
        int totalSum = findTotalSum(root);
        if (totalSum % 2 == 1) {
            return false;
        }
        partitionTree(root, totalSum);
        return canPartition;
    }

    private Integer partitionTree(TreeNode root, int totalSum) {
        if (root == null) {
            return null; // to disambiguate between actual subtree sum of 0 vs null node
        } else {
            Integer leftSum = partitionTree(root.left, totalSum);
            Integer rightSum = partitionTree(root.right, totalSum);
            int curTreeSum = 0;
            curTreeSum += (leftSum == null ? 0 : leftSum);
            curTreeSum += (rightSum == null ? 0 : rightSum);
            if (leftSum != null && leftSum * 2 == totalSum) {
                canPartition = true;
            }
            if (rightSum != null && rightSum * 2 == totalSum) {
                canPartition = true;
            }
            return curTreeSum + root.val;
        }
    }

    private int findTotalSum(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return root.val + findTotalSum(root.left) + findTotalSum(root.right);
        }
    }
}

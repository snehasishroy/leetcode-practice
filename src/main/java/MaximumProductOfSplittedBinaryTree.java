import common.TreeNode;

/**
 * <pre>
 * https://leetcode.com/problems/maximum-product-of-splitted-binary-tree/
 *
 * Given the root of a binary tree, split the binary tree into two subtrees by removing one edge such that the product of the sums of the subtrees is maximized.
 * Return the maximum product of the sums of the two subtrees. Since the answer may be too large, return it modulo 10^9 + 7.
 * Note that you need to maximize the answer before taking the mod and not after taking it.
 *
 * Input: root = [1,2,3,4,5,6]
 * Output: 110
 * Explanation: Remove the red edge and get 2 binary trees with sum 11 and 10. Their product is 110 (11*10)
 *
 * Input: root = [1,null,2,3,4,null,null,5,6]
 * Output: 90
 * Explanation: Remove the red edge and get 2 binary trees with sum 15 and 6.Their product is 90 (15*6)
 *
 * Input: root = [2,3,9,10,7,8,6,5,4,11,1]
 * Output: 1025
 *
 * Input: root = [1,1]
 * Output: 1
 *
 * Constraints:
 * The number of nodes in the tree is in the range [2, 5 * 10^4].
 * 1 <= Node.val <= 10^4
 * </pre>
 */
public class MaximumProductOfSplittedBinaryTree {
    long maxProduct;

    /**
     * Approach: DP on trees, Keep track of the total sum of nodes in the tree. At each parent try to cut its child and keep track of the
     * maximum product achieved so far.
     *
     * {@link alternate.PartitionZeroOneTree} {@link EqualTreePartition}
     */
    public int maxProduct(TreeNode root) {
        int totalSum = sum(root);
        recur(root, totalSum);
        return (int) (maxProduct % 1_000_000_007);
    }

    private int sum(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return root.val + sum(root.left) + sum(root.right);
        }
    }

    private int recur(TreeNode root, int totalSum) {
        if (root == null) {
            return 0;
        } else {
            int leftSum = recur(root.left, totalSum);
            int rightSum = recur(root.right, totalSum);
            long candidate1 = (long) (totalSum - leftSum) * leftSum; //if left child is cut, what will be the result
            long candidate2 = (long) (totalSum - rightSum) * rightSum; //if right child is cut
            maxProduct = Math.max(maxProduct, Math.max(candidate1, candidate2));
            return leftSum + rightSum + root.val;
        }
    }
}

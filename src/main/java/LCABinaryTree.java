import common.TreeNode;

/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
 * <p>
 * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
 * <p>
 * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
 * Output: 3
 * Explanation: The LCA of nodes 5 and 1 is 3.
 */
public class LCABinaryTree {
    /**
     * Approach: Recursion
     * <p>
     * {@link FindDistanceInABinaryTree} {@link LCABinarySearchTree} {@link SmallestCommonRegion}
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return DFS(root, p, q);
    }

    private TreeNode DFS(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        } else {
            if (root.val == p.val || root.val == q.val) {
                return root;
            }
            TreeNode left = DFS(root.left, p, q);
            TreeNode right = DFS(root.right, p, q);
            if (left != null && right != null) {
                //left and right both reported seeing values, since p & q are unique, it means current node is the lca
                return root;
            } else {
                //only one subtree has value, return the one with non null values
                return left == null ? right : left;
            }
        }
    }
}

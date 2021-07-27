import common.TreeNode;

/**
 * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
 * <p>
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
 * <p>
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T
 * that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 */
public class LCABinarySearchTree {
    /**
     * Approach: Recursion, lca is the first node whose value lies between [p,q]. If p and q are both smaller than current node, lca will be present in the left subtree.
     * Otherwise it will be present in the right subtree
     *
     * {@link SmallestCommonRegion} {@link LCABinaryTree}
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val > q.val) { //handle edge case when p > q
            TreeNode temp = q;
            q = p;
            p = temp;
        }
        return search(root, p, q);
    }

    private TreeNode search(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        } else if (root.val >= p.val && root.val <= q.val) {
            return root;
        } else if (root.val > p.val && root.val > q.val) {
            return search(root.left, p, q);
        } else {
            return search(root.right, p, q);
        }
    }
}

import common.TreeNode;

/**
 * <pre>
 * https://leetcode.com/problems/binary-tree-pruning/
 *
 * Given the root of a binary tree, return the same tree where every subtree (of the given tree) not containing a 1 has been removed.
 *
 * A subtree of a node node is node plus every node that is a descendant of node.
 *
 * Input: root = [1,null,0,0,1]
 * Output: [1,null,0,null,1]
 * Explanation:
 * Only the red nodes satisfy the property "every subtree not containing a 1".
 * The diagram on the right represents the answer.
 *
 * Input: root = [1,0,1,0,0,0,1]
 * Output: [1,null,1,null,1]
 *
 * Input: root = [1,1,0,1,1,0,1,0]
 * Output: [1,1,0,1,1,null,1]
 *
 * Constraints:
 * The number of nodes in the tree is in the range [1, 200].
 * Node.val is either 0 or 1.
 * </pre>
 */
public class BinaryTreePruning {
    /**
     * Approach: Recursion, Remember the trick to solving tree questions is to either pass information to the child or get information from the child
     * Here since we have to delete node, deletion must be performed by the parent, hence it must need information from the child whether to delete it or not.
     * So child can return true (delete) if the current subtree at child contains one.
     *
     * {@link DeleteNodesAndReturnForest}
     */
    public TreeNode pruneTree(TreeNode root) {
        boolean isOnePresent = recur(root);
        return !isOnePresent ? null : root;
    }

    //returns true if the current subtree rooted at 'root' contains one
    private boolean recur(TreeNode root) {
        if (root == null) {
            return false;
        } else {
            boolean isOnePresentInLeft = recur(root.left);
            boolean isOnePresentInRight = recur(root.right);
            //delete the subtree not containing one's
            if (!isOnePresentInLeft) {
                root.left = null;
            }
            if (!isOnePresentInRight) {
                root.right = null;
            }
            //return true if one is either present in left/right or root node itself.
            return isOnePresentInLeft || isOnePresentInRight || root.val == 1;
        }
    }
}

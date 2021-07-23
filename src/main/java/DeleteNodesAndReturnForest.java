import common.TreeNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * https://leetcode.com/problems/delete-nodes-and-return-forest/
 * <p>
 * Given the root of a binary tree, each node in the tree has a distinct value.
 * <p>
 * After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).
 * <p>
 * Return the roots of the trees in the remaining forest.  You may return the result in any order.
 * <p>
 * Input: root = [1,2,3,4,5,6,7], to_delete = [3,5]
 * Output: [[1,2,null,4],[6],[7]]
 */
public class DeleteNodesAndReturnForest {
    /**
     * Approach: It was a bit tricky, i couldn't solve this question earlier, came back after 1 month or so with fresh set of eyes.
     * The trick was to understand that here we have to do things to children
     * 1. Consume their result (update the left and right pointer, in case children gets deleted)
     * 2. Pass them something (whether they are a potential root node)
     * <p>
     * If a current node is a potential root candidate, check if it needs to be deleted, if not then add it to result list
     * if yes, then it's children are potential root candidates.
     *
     * {@link BinaryTreePruning}
     */
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        HashSet<Integer> hashSet = new HashSet<>();
        for (int delete : to_delete) {
            hashSet.add(delete);
        }
        ArrayList<TreeNode> roots = new ArrayList<>();
        recur(root, roots, hashSet, true);
        return roots;
    }

    private TreeNode recur(TreeNode root, ArrayList<TreeNode> roots, HashSet<Integer> hashSet, boolean isRootCandidate) {
        if (root != null) {
            if (isRootCandidate && !hashSet.contains(root.val)) { //if current node is a root candidate and needs not to be deleted
                roots.add(root); //add it to the result
                root.left = recur(root.left, roots, hashSet, false); //it's children are not a potential root candidate
                root.right = recur(root.right, roots, hashSet, false);
                return root;
            } else if (hashSet.contains(root.val)) {
                //if current nodes needs to be deleted, it's children will become potential root candidate
                recur(root.left, roots, hashSet, true);
                recur(root.right, roots, hashSet, true);
                return null;
            } else {
                //remaining nodes, just iterate and update the left/right pointers
                root.left = recur(root.left, roots, hashSet, false);
                root.right = recur(root.right, roots, hashSet, false);
                return root;
            }
        } else {
            return null;
        }
    }
}

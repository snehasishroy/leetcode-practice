import common.TreeNode;

/**
 * <pre>
 * https://leetcode.com/problems/check-if-a-string-is-a-valid-sequence-from-root-to-leaves-path-in-a-binary-tree/ Premium
 *
 * Given a binary tree where each path going from the root to any leaf form a valid sequence, check if a given string is a valid sequence in such binary tree.
 *
 * We get the given string from the concatenation of an array of integers arr and the concatenation of all values of the nodes along a path results in a sequence in the given binary tree.
 *
 * Input: root = [0,1,0,0,1,0,null,null,1,0,0], arr = [0,1,0,1]
 * Output: true
 * Explanation:
 * The path 0 -> 1 -> 0 -> 1 is a valid sequence (green color in the figure).
 * Other valid sequences are:
 * 0 -> 1 -> 1 -> 0
 * 0 -> 0 -> 0
 *
 * Input: root = [0,1,0,0,1,0,null,null,1,0,0], arr = [0,0,1]
 * Output: false
 * Explanation: The path 0 -> 0 -> 1 does not exist, therefore it is not even a sequence.
 *
 * Input: root = [0,1,0,0,1,0,null,null,1,0,0], arr = [0,1,1]
 * Output: false
 * Explanation: The path 0 -> 1 -> 1 is a sequence, but it is not a valid sequence.
 *
 * Constraints:
 * 1 <= arr.length <= 5000
 * 0 <= arr[i] <= 9
 * Each node's value is between [0 - 9].
 * </pre>
 */
public class CheckIfAStringIsValidSequenceFromRootToLeavesPathInABinaryTree {
    /**
     * Approach: DFS, Compare the elements one-by-one by moving to next node using recursion.
     *
     * {@link DeleteNodesAndReturnForest} {@link RootToLeafPaths}
     */
    public boolean isValidSequence(TreeNode root, int[] arr) {
        return recur(root, arr, 0);
    }

    private boolean recur(TreeNode root, int[] arr, int index) {
        if (index == arr.length - 1) { //should be a leaf node
            return root != null && root.val == arr[index] && root.left == null && root.right == null;
        } else if (root == null) {
            return false;
        } else if (arr[index] != root.val) {
            return false;
        } else {
            return recur(root.left, arr, index + 1) || recur(root.right, arr, index + 1);
        }
    }
}

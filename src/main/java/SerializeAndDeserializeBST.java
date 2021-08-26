import common.TreeNode;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * https://leetcode.com/problems/serialize-and-deserialize-bst/
 * <p>
 * Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your serialization/deserialization algorithm should work.
 * You need to ensure that a binary search tree can be serialized to a string, and this string can be deserialized to the original tree structure.
 * <p>
 * The encoded string should be as compact as possible.
 * <p>
 * Input: root = [2,1,3]
 * Output: [2,1,3]
 */
public class SerializeAndDeserializeBST {
    /**
     * Approach: Encode tree by doing preorder traversal, No need of encoding null because we are going to leverage the bounds property of BST
     *
     * {@link SerializeAndDeserializeBinaryTree} {@link VerifyPreorderSerializationOfBinaryTree}
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        } else {
            return root.val + "," + serialize(root.left) + serialize(root.right);
        }
    }

    public TreeNode deserialize(String data) {
        if (data.isEmpty()) {
            return null;
        }
        String[] splitted = data.split(",");
        ArrayDeque<String> queue = new ArrayDeque<>(Arrays.asList(splitted));
        return buildTree(queue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Tricky solution: To quickly identify a value whether it is a part of current subtree or not, leverage property of BST that left subtree is < root.val
     * and right subtree is > root.val.
     * This helps in quickly filtering out nodes without presence of explicit null marker
     */
    private TreeNode buildTree(ArrayDeque<String> queue, int lowerBound, int upperBound) {
        if (queue.isEmpty()) {
            return null;
        }
        int val = Integer.parseInt(queue.peekFirst());
        if (val > lowerBound && val < upperBound) { //if the candidate fits into the provided subtree
            queue.remove();
            TreeNode root = new TreeNode(val);
            root.left = buildTree(queue, lowerBound, val);
            root.right = buildTree(queue, val, upperBound);
            return root;
        } else {
            return null;
        }
    }
}

import common.TreeNode;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * https://leetcode.com/problems/serialize-and-deserialize-binary-tree/
 * <p>
 * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work.
 * You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.
 */
public class SerializeAndDeserializeBinaryTree {
    /**
     * My initial approach: Encodes a tree to a single string. root (size of left subtree) left subtree (size of right subtree) right subtree
     *
     * This is a simpler solution: To store like root, root.left.val, root.left.left.val, root.left.right.val, root.right.val, null, null
     * While decoding we could simply split the string by ',' and create a queue from the resultant array
     * Queue will help to recursively fix the root element, then recurse for left & right respectively assigning the head of the queue
     * to the root.
     *
     * Similar question was asked during Joveo Onsite interview.
     *
     * {@link VerifyPreorderSerializationOfBinaryTree} {@link SerializeAndDeserializeBST}
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return "N";
        } else {
            return root.val + "," + serialize(root.left) + "," + serialize(root.right);
        }
    }

    // Decodes your encoded data to tree.
    // TIP: tree has negative elements too
    // first parse the root node, then recursively solve for left subtree
    // similarly recursively solve for right subtree
    public TreeNode deserialize(String data) {
        String[] splitted = data.split(",");
        ArrayDeque<String> queue = new ArrayDeque<>(Arrays.asList(splitted));
        return buildTree(queue);
    }

    private TreeNode buildTree(ArrayDeque<String> queue) {
        String val = queue.remove();
        if (val.equals("N")) {
            return null;
        } else {
            TreeNode root = new TreeNode(Integer.parseInt(val));
            root.left = buildTree(queue);
            root.right = buildTree(queue);
            return root;
        }
    }
}

/**
 * <pre>
 * https://leetcode.com/problems/verify-preorder-serialization-of-a-binary-tree/
 *
 * One way to serialize a binary tree is to use preorder traversal. When we encounter a non-null node, we record the node's value.
 * If it is a null node, we record using a sentinel value such as '#'.
 *
 * For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where '#' represents a null node.
 *
 * Given a string of comma-separated values preorder, return true if it is a correct preorder traversal serialization of a binary tree.
 *
 * It is guaranteed that each comma-separated value in the string must be either an integer or a character '#' representing null pointer.
 *
 * You may assume that the input format is always valid.
 *
 * For example, it could never contain two consecutive commas, such as "1,,3".
 * Note: You are not allowed to reconstruct the tree.
 *
 * Input: preorder = "9,3,4,#,#,1,#,#,2,#,6,#,#"
 * Output: true
 *
 * Input: preorder = "1,#"
 * Output: false
 *
 * Input: preorder = "9,#,#,1"
 * Output: false
 *
 * Constraints:
 * 1 <= preorder.length <= 10^4
 * preorder consist of integers in the range [0, 100] and '#' separated by commas ','.
 * </pre>
 */
public class VerifyPreorderSerializationOfBinaryTree {
    int index;

    /**
     * Approach: Recursion. Very similar to actually creating a Binary tree but the problem statement explicitly forbids to create a binary tree
     *
     * Alternatively we can try to solve this problem by counting the no of available slots i.e. initially there would be 1 slots available.
     * When we encounter an integer value, it consumes one slot, and we would additionally need 2 slots to fill left and right child.
     * When we encounter a '#' value, it consumes one slot.
     * In the end the slots should be 0 i.e. all the slots should have been consumed. Benefit of this approach is that it does not require additional space.
     *
     * {@link SerializeAndDeserializeBinaryTree} {@link ValidateStackSequences}
     */
    public boolean isValidSerialization(String preorder) {
        String[] splits = preorder.split(",");
        return isValid(splits) && index == splits.length; //verify whether all the indices have been matched
    }

    private boolean isValid(String[] splits) {
        if (index == splits.length) { //we are trying to create a child from a non-existent node
            return false;
        } else if (splits[index].equals("#")) { //leaf node found
            index++;
            return true;
        } else { //parent node found, check if left and right child are valid
            index++;
            boolean left = isValid(splits);
            boolean right = isValid(splits);
            return left && right;
        }
    }
}

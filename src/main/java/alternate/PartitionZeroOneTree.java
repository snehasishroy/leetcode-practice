package alternate;

import alternate.InvertedSubTree.Tree;

/**
 * https://binarysearch.io/problems/Partition-Zero-One-Trees
 * <p>
 * You are given a binary tree root containing values 0, 1 and 2. The root has at least one 0 node and one 1 node.
 * Consider an operation where we delete an edge in the tree and the tree becomes disjoint with two trees.
 * <p>
 * Return the number of ways we can delete one edge such that none of the two trees contain both a 0 node and a 1 node.
 * <p>
 * For example, given
 * <pre>
 *    0
 *   / \
 *  0   2
 *     / \
 *    1   1
 * </pre>
 * Return 1 since we can only delete the 0 to 2 edge.
 */
public class PartitionZeroOneTree {
    int result;

    /**
     * Approach: In case of partitioning problems, there is a clear pattern. Get the total first, then do a post order traversal
     * and let parent decide whether to cut the edge of left/right subtree
     * Here the criteria to cut the edge is if the subtree contains all the ones and no zeroes and vice versa
     *
     * {@link MaximumProductOfSplittedBinaryTree}
     */
    public int solve(Tree root) {
        Count total = iterate(root);
        cut(root, total);
        return result;
    }

    private Count cut(Tree root, Count total) {
        if (root == null) {
            return new Count(0, 0);
        } else {
            Count left = cut(root.left, total);
            Count right = cut(root.right, total);
            if (left.ones == 0 && left.zeroes == total.zeroes) {
                //if left subtree contains all the zeroes, cut the edge between current node and left subtree
                result++;
            } else if (left.zeroes == 0 && left.ones == total.ones) {
                //if left subtree contains all the ones
                result++;
            }
            if (right.ones == 0 && right.zeroes == total.zeroes) {
                //if right subtree contains all the zeroes
                result++;
            } else if (right.zeroes == 0 && right.ones == total.ones) {
                //if right subtree contains all the ones
                result++;
            }
            int zeroes = left.zeroes + right.zeroes + root.val == 0 ? 1 : 0;
            int ones = left.ones + right.ones + root.val == 1 ? 1 : 0;
            return new Count(zeroes, ones);
        }
    }

    private Count iterate(Tree root) {
        if (root == null) {
            return new Count(0, 0);
        } else {
            Count left = iterate(root.left);
            Count right = iterate(root.right);
            int zeroes = left.zeroes + right.zeroes + root.val == 0 ? 1 : 0;
            int ones = left.ones + right.ones + root.val == 1 ? 1 : 0;
            return new Count(zeroes, ones);
        }
    }

    private static class Count {
        int zeroes, ones;

        public Count(int zeroes, int ones) {
            this.zeroes = zeroes;
            this.ones = ones;
        }
    }
}

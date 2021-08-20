/**
 * <pre>
 * https://leetcode.com/problems/range-sum-query-mutable/
 *
 * Given an integer array nums, handle multiple queries of the following types:
 *
 * Update the value of an element in nums.
 * Calculate the sum of the elements of nums between indices left and right inclusive where left <= right.
 * Implement the NumArray class:
 *
 * NumArray(int[] nums) Initializes the object with the integer array nums.
 * void update(int index, int val) Updates the value of nums[index] to be val.
 * int sumRange(int left, int right) Returns the sum of the elements of nums between indices left and right inclusive (i.e. nums[left] + nums[left + 1] + ... + nums[right]).
 *
 * Input
 * ["NumArray", "sumRange", "update", "sumRange"]
 * [[[1, 3, 5]], [0, 2], [1, 2], [0, 2]]
 * Output
 * [null, 9, null, 8]
 *
 * Explanation
 * NumArray numArray = new NumArray([1, 3, 5]);
 * numArray.sumRange(0, 2); // return 1 + 3 + 5 = 9
 * numArray.update(1, 2);   // nums = [1, 2, 5]
 * numArray.sumRange(0, 2); // return 1 + 2 + 5 = 8
 *
 * Constraints:
 * 1 <= nums.length <= 3 * 10^4
 * -100 <= nums[i] <= 100
 * 0 <= index < nums.length
 * -100 <= val <= 100
 * 0 <= left <= right < nums.length
 * At most 3 * 10^4 calls will be made to update and sumRange.
 * </pre>
 */
public class RangeSumQueryMutable {
    int[] tree;
    int n;
    /**
     * Approach: Segment tree
     *
     * Refer to https://leetcode.com/articles/a-recursive-approach-to-segment-trees-range-sum-queries-lazy-propagation/
     */
    public RangeSumQueryMutable(int[] nums) {
        n = nums.length;
        tree = new int[4 * n]; //size of segment tree, required to make a complete binary tree. Empty nodes are filled with 0
        buildTree(nums, 0, 0, n - 1);
    }

    public void update(int index, int val) {
        updateTree(index, 0, 0, n - 1, val);
    }

    public int sumRange(int left, int right) {
        return query(left, right, 0, n - 1, 0);
    }

    /**
     * Builds the tree in a bottom up manner i.e. first assign the leaf nodes, then recursively assign the parent nodes
     * During iteration, always need to keep two things in sync, current range [low, high] and current tree index
     * Whenever moving to a new range, update the tree index as well
     *
     * Time Complexity: O(n) We visit each leaf of the segment tree once
     * This process is similar to MergeSort, but merge sort time complexity is O(nlogn) because of the linear time complexity of merge function.
     * Here merge function is of constant time.
     */
    private void buildTree(int[] nums, int treeIndex, int low, int high) {
        if (low == high) { //leaf node reached
            tree[treeIndex] = nums[low];
        } else {
            int mid = low + (high - low) / 2;
            buildTree(nums, 2 * treeIndex + 1, low, mid); //build left subtree
            buildTree(nums, 2 * treeIndex + 2, mid + 1, high); //build right subtree
            tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]); //build the parent by looking at the results from left and right subtree
        }
    }

    /**
     * Updates the tree in a bottom up manner i.e. first update the leaf node and then keep on recursively updating the parent with the new sum.
     * This process discards half of the range every time i.e. it either goes in the left child or in the right child.
     *
     * Time Complexity: O(logn) as the segment tree is a complete binary tree
     * This is similar to binary search where we discard half of the data every time.
     */
    private void updateTree(int updateIndex, int treeIndex, int low, int high, int value) {
        if (low == high) { //target leaf node found, this node contains the value stored at updateIndex
            tree[treeIndex] = value;
        } else {
            int mid = low + (high - low) / 2;
            if (updateIndex <= mid) { //visit left child
                updateTree(updateIndex, 2 * treeIndex + 1, low, mid, value);
            } else { //visit right child
                updateTree(updateIndex, 2 * treeIndex + 2, mid + 1, high, value);
            }
            tree[treeIndex] = merge(tree[2 * treeIndex + 1], tree[2 * treeIndex + 2]); //update the parent
        }
    }

    /**
     * Find the segment that matches the given range i.e. if you need data for [10,15] range and current segment is [1,6] it can't satisfy the query. No point in traversing its subtree.
     * If the current segment is [1,20] it can completely satisfy the query, so return whatever value is stored in this node.
     * If the current segment is [8,12] we need to traverse both the left and right subtree and merge their result.
     *
     * Time Complexity: O(logn) as in the wost case we will traverse till a leaf node, and since the tree is height balanced time complexity will be O(logn)
     */
    private int query(int left, int right, int tree_low, int tree_high, int tree_index) {
        if (tree_high < left || right < tree_low) { //this segment can't answer this query
            return 0;
        } else if (tree_low >= left && tree_high <= right) { //this segment lies completely within the query
            return tree[tree_index];
        } else { //partial overlap, query the results from left and right child and merge them
            int mid = tree_low + (tree_high - tree_low) / 2;
            int leftResult = query(left, right, tree_low, mid, 2 * tree_index + 1);
            int rightResult = query(left, right, mid + 1, tree_high, 2 * tree_index + 2);
            return merge(leftResult, rightResult);
        }
    }

    private int merge(int leftChild, int rightChild) {
        return leftChild + rightChild;
    }
}

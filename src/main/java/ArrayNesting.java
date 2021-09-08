/**
 * https://leetcode.com/problems/array-nesting/
 *
 * A zero-indexed array A of length N contains all integers from 0 to N-1. Find and return the longest length of set S,
 * where S[i] = {A[i], A[A[i]], A[A[A[i]]], ... } subjected to the rule below.
 *
 * Suppose the first element in S starts with the selection of element A[i] of index = i, the next element in S should be A[A[i]],
 * and then A[A[A[i]]]… By that analogy, we stop adding right before a duplicate element occurs in S.
 *
 * Input: A = [5,4,0,3,1,6,2]
 * Output: 4
 * Explanation:
 * A[0] = 5, A[1] = 4, A[2] = 0, A[3] = 3, A[4] = 1, A[5] = 6, A[6] = 2.
 *
 * One of the longest S[K]:
 * S[0] = {A[0], A[5], A[6], A[2]} = {5, 6, 2, 0}
 *
 * Note:
 * N is an integer within the range [1, 20,000].
 * The elements of A are all distinct.
 * Each element of A is an integer within the range [0, N-1].
 */
public class ArrayNesting {
    /**
     * Approach: DP on Graphs, It took me some time to solve this problem but if you actually dry run the examples provided, you will
     * notice that the input array will make a graph which contains cycles.
     * So the problem is reduced to finding the longest cyclic path which can be solved using DFS.
     *
     * {@link LongestIncreasingPathInMatrix} {@link ParallelCourses} {@link NestedListWeightSum}
     */
    public int arrayNesting(int[] nums) {
        int n = nums.length;
        boolean[] visited = new boolean[n];
        int maxLength = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                //if a node is not visited, start DFS to find the longest cyclic path
                int len = recur(visited, nums, i);
                maxLength = Math.max(maxLength, len);
            }
        }
        return maxLength;
    }

    private int recur(boolean[] visited, int[] nums, int index) {
        if (visited[index]) {
            return 0;
        } else {
            visited[index] = true;
            return 1 + recur(visited, nums, nums[index]);
        }
    }
}

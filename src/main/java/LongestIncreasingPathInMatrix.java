/**
 * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
 * <p>
 * Given an integer matrix, find the length of the longest increasing path.
 * <p>
 * From each cell, you can either move to four directions: left, right, up or down. You may NOT move diagonally or move outside of the boundary
 * <p>
 * Input: nums =
 * <pre>
 * [
 *   [9,9,4],
 *   [6,6,8],
 *   [2,1,1]
 * ]
 * </pre>
 * Output: 4
 * Explanation: The longest increasing path is [1, 2, 6, 9].
 */
public class LongestIncreasingPathInMatrix {
    /**
     * Approach: Need to perform DFS by considering each point as the starting point of the path i.e the minimum point.
     * Once recursion is done, cache the results, as you will visit the same element again and again so we can reuse the
     * max length computed during the previous iterations.
     * <p>
     * As the problem asks for strictly increasing path in a matrix, we get a DAG, but we change the problem statement to
     * non-decreasing path, we can get cycles. That would mean we would have to visit all paths to find the result and it won't be
     * memoizable
     * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/discuss/78376/If-we-change-%22increasing%22-to-%22non-decreasing%22
     *
     * {@link ArrayNesting}
     */
    int[] x_offset = new int[]{0, 1, 0, -1};
    int[] y_offset = new int[]{1, 0, -1, 0};

    public int longestIncreasingPath(int[][] matrix) {
        int m = matrix.length;
        if (m == 0) {
            return 0;
        }
        int n = matrix[0].length;
        int maxLength = 0;
        int[][] memoize = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int curLength = DFS(matrix, i, j, m, n, memoize);
                maxLength = Math.max(curLength, maxLength);
            }
        }
        return maxLength;
    }

    private int DFS(int[][] matrix, int row, int col, int max_row, int max_col, int[][] memoize) {
        if (memoize[row][col] != 0) {
            return memoize[row][col];
        }
        int currentIncreasingPath = 0;
        for (int i = 0; i < 4; i++) {
            int new_row = row + x_offset[i];
            int new_col = col + y_offset[i];
            //Do DFS only if we find a longer path
            if (new_row >= 0 && new_row < max_row && new_col >= 0 && new_col < max_col && matrix[new_row][new_col] > matrix[row][col]) {
                currentIncreasingPath = Math.max(currentIncreasingPath, DFS(matrix, new_row, new_col, max_row, max_col, memoize));
            }
        }
        return memoize[row][col] = 1 + currentIncreasingPath;
    }
}

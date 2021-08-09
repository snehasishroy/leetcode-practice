/**
 * <pre>
 * https://leetcode.com/problems/count-negative-numbers-in-a-sorted-matrix/
 *
 * Given a m x n matrix grid which is sorted in non-increasing order both row-wise and column-wise, return the number of negative numbers in grid.
 *
 * Input: grid = [[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]
 * Output: 8
 * Explanation: There are 8 negatives number in the matrix.
 *
 * Input: grid = [[3,2],[1,0]]
 * Output: 0
 *
 * Input: grid = [[1,-1],[-1,-1]]
 * Output: 3
 *
 * Input: grid = [[-1]]
 * Output: 1
 *
 * Constraints:
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 * -100 <= grid[i][j] <= 100
 *
 * Follow up: Could you find an O(n + m) solution?
 * </pre>
 */
public class CountNegativeNumbersInSortedMatrix {
    /**
     * Approach: Two pointers, problems related to matrix sorted in both rows and cols can be solved using either two pointers or binary search.
     * Start from top right cell, if value is negative, all values under that column will be negative as well since columns are sorted in decreasing order.
     * Now move to adjacent left cell.
     *
     * If cell value is positive, move down to find a lower value, there is no point in going left as the values will be even higher on the left.
     * So need to go down to find a lower value.
     *
     * {@link FindKClosestElements}
     */
    public int countNegatives(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int r = 0, c = cols - 1;
        int res = 0;
        while (r < rows && c >= 0) {
            if (grid[r][c] >= 0) {
                r++;
            } else  {
                res += (rows - r);
                c--;
            }
        }
        return res;
    }
}

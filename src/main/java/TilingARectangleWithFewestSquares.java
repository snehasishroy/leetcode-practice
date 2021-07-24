/**
 * https://leetcode.com/problems/tiling-a-rectangle-with-the-fewest-squares/
 * <p>
 * Given a rectangle of size n x m, find the minimum number of integer-sided squares that tile the rectangle.
 * <p>
 * Input: n = 2, m = 3
 * Output: 3
 * Explanation: 3 squares are necessary to cover the rectangle.
 * 2 (squares of 1x1)
 * 1 (square of 2x2)
 * <p>
 * Input: n = 5, m = 8
 * Output: 5
 * <p>
 * Input: n = 11, m = 13
 * Output: 6
 * <p>
 * Constraints:
 * 1 <= n <= 13
 * 1 <= m <= 13
 */
public class TilingARectangleWithFewestSquares {
    int minSquaresPlaced = Integer.MAX_VALUE;

    /**
     * Approach: Backtracking with pruning. Such low constraints gives an hint towards backtracking solution
     * Try to place a square of size 1,2... min(m,n) on a free cell and keep track of min no of squares required to fill all the cells
     * using backtracking
     * <p>
     * Pruning is required to avoid TLE
     * If you start placing the largest possible square first, rather than smallest possible square, runtime decreases from 450ms to 5ms
     * Because by placing the largest square first, you prune a lot of branches during recursion
     * <p>
     * {@link PathWithMaximumGold} {@link SudokuSolver} {@link PartitionKEqualSumSubsets} {@link MinDistanceToTypeUsingTwoFingers} {@link WordLadder2}
     */
    public int tilingRectangle(int n, int m) {
        int[][] grid = new int[n][m];
        recur(grid, 0, 0, 0);
        return minSquaresPlaced;
    }

    private void recur(int[][] grid, int row, int col, int squaresPlacedSoFar) {
        int n = grid.length;
        int m = grid[0].length;
        if (row == n) { //all rows have been filled
            minSquaresPlaced = Math.min(minSquaresPlaced, squaresPlacedSoFar);
        } else if (col == m) { //all cols of current row has been filled, move to next row
            recur(grid, row + 1, 0, squaresPlacedSoFar);
        } else if (grid[row][col] == 1) { //current cell is already filled
            recur(grid, row, col + 1, squaresPlacedSoFar);
        } else if (squaresPlacedSoFar < minSquaresPlaced) { //pruning is critical
            for (int size = Math.min(n, m); size >= 0; size--) { //try placing a square of largest size first
                if (row + size < n && col + size < m && isSafe(grid, row, col, size + 1)) { //if it's safe to fill
                    place(grid, row, col, size + 1, 1); //place square
                    recur(grid, row, col + 1, squaresPlacedSoFar + 1);
                    place(grid, row, col, size + 1, 0); //backtrack by resetting placed squares
                }
            }
        }
    }

    private boolean isSafe(int[][] grid, int row, int col, int bounds) {
        for (int i = row; i < row + bounds; i++) {
            for (int j = col; j < col + bounds; j++) {
                if (grid[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void place(int[][] grid, int row, int col, int bounds, int val) {
        for (int i = row; i < row + bounds; i++) {
            for (int j = col; j < col + bounds; j++) {
                grid[i][j] = val;
            }
        }
    }
}

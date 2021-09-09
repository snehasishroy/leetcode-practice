/**
 * <pre>
 * https://leetcode.com/problems/number-of-islands/
 *
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
 * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
 *
 * Input: grid = [
 *   ["1","1","1","1","0"],
 *   ["1","1","0","1","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","0","0","0"]
 * ]
 * Output: 1
 *
 * Input: grid = [
 *   ["1","1","0","0","0"],
 *   ["1","1","0","0","0"],
 *   ["0","0","1","0","0"],
 *   ["0","0","0","1","1"]
 * ]
 * Output: 3
 *
 * Constraints:
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * grid[i][j] is '0' or '1'.
 * </pre>
 */
public class NumberOfIslands {
    int[] x_offset = new int[]{0, 1, 0, -1};
    int[] y_offset = new int[]{1, 0, -1, 0};

    /**
     * Approach: DFS
     *
     * {@link CountSubIslands} {@link FindAllGroupsOfFarmland}
     */
    public int numIslands(char[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    dfs(grid, i, j);
                    res++;
                }
            }
        }
        return res;
    }

    private void dfs(char[][] grid, int row, int col) {
        grid[row][col] = '0'; //mark the cell to 0 in order to not visit it again, acts as a visited cell
        for (int i = 0; i < x_offset.length; i++) {
            int new_row = row + x_offset[i];
            int new_col = col + y_offset[i];
            if (isValid(new_row, new_col, grid.length, grid[0].length) && grid[new_row][new_col] == '1') {
                dfs(grid, new_row, new_col);
            }
        }
    }

    private boolean isValid(int new_rol, int new_col, int max_row, int max_col) {
        return new_rol >= 0 && new_rol < max_row && new_col >= 0 && new_col < max_col;
    }
}


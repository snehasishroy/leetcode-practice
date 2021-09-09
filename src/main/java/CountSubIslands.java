import java.util.HashSet;

/**
 * <pre>
 * https://leetcode.com/problems/count-sub-islands/
 *
 * You are given two m x n binary matrices grid1 and grid2 containing only 0's (representing water) and 1's (representing land).
 * An island is a group of 1's connected 4-directionally (horizontal or vertical). Any cells outside of the grid are considered water cells.
 *
 * An island in grid2 is considered a sub-island if there is an island in grid1 that contains all the cells that make up this island in grid2.
 *
 * Return the number of islands in grid2 that are considered sub-islands.
 *
 * Input: grid1 = [[1,1,1,0,0],[0,1,1,1,1],[0,0,0,0,0],[1,0,0,0,0],[1,1,0,1,1]], grid2 = [[1,1,1,0,0],[0,0,1,1,1],[0,1,0,0,0],[1,0,1,1,0],[0,1,0,1,0]]
 * Output: 3
 * Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
 * The 1s colored red in grid2 are those considered to be part of a sub-island. There are three sub-islands.
 *
 * Input: grid1 = [[1,0,1,0,1],[1,1,1,1,1],[0,0,0,0,0],[1,1,1,1,1],[1,0,1,0,1]], grid2 = [[0,0,0,0,0],[1,1,1,1,1],[0,1,0,1,0],[0,1,0,1,0],[1,0,0,0,1]]
 * Output: 2
 * Explanation: In the picture above, the grid on the left is grid1 and the grid on the right is grid2.
 * The 1s colored red in grid2 are those considered to be part of a sub-island. There are two sub-islands.
 *
 * Constraints:
 * m == grid1.length == grid2.length
 * n == grid1[i].length == grid2[i].length
 * 1 <= m, n <= 500
 * grid1[i][j] and grid2[i][j] are either 0 or 1.
 * </pre>
 */
public class CountSubIslands {
    int[] x_offset = new int[]{0, 1, 0, -1};
    int[] y_offset = new int[]{1, 0, -1, 0};

    /**
     * Approach: Two pass DFS, first mark all the cells that are part of the same island with the same id.
     * Then during the second pass check whether a cell which is part of an island in grid2 has a counterpart in grid1 which is an island.
     * If it's not, that entire island in grid2 would become invalid because the problem statement mentions that all the cells part of an island in grid2 should
     * have a counterpart in grid1.
     *
     * {@link FindAllGroupsOfFarmland} {@link NumberOfIslands}
     */
    public int countSubIslands(int[][] grid1, int[][] grid2) {
        int m = grid1.length;
        int n = grid1[0].length;
        int id = 2;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] == 1) {
                    DFS(grid2, i, j, id++);
                }
            }
        }
        int totalIslands = id - 2;
        var invalidIslands = new HashSet<Integer>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid2[i][j] > 1 && grid1[i][j] == 0) { //if cell in grid2 is part of an island but cell in grid1 isn't, the island in grid2 is invalid
                    invalidIslands.add(grid2[i][j]);
                }
            }
        }
        return totalIslands - invalidIslands.size(); //return the number of valid islands in grid2
    }

    private void DFS(int[][] grid, int r, int c, int val) {
        grid[r][c] = val;
        for (int i = 0; i < 4; i++) {
            int new_r = r + x_offset[i];
            int new_c = c + y_offset[i];
            if (new_r >= 0 && new_r < grid.length && new_c >= 0 && new_c < grid[0].length && grid[new_r][new_c] == 1) {
                DFS(grid, new_r, new_c, val);
            }
        }
    }
}

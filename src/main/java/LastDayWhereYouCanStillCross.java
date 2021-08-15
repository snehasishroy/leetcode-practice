/**
 * <pre>
 * https://leetcode.com/problems/last-day-where-you-can-still-cross/
 *
 * There is a 1-based binary matrix where 0 represents land and 1 represents water. You are given integers row and col representing the number of rows and columns in the matrix, respectively.
 *
 * Initially on day 0, the entire matrix is land. However, each day a new cell becomes flooded with water.
 * You are given a 1-based 2D array cells, where cells[i] = [ri, ci] represents that on the ith day, the cell on the rith row and cith column (1-based coordinates) will be covered with water (i.e., changed to 1).
 *
 * You want to find the last day that it is possible to walk from the top to the bottom by only walking on land cells.
 * You can start from any cell in the top row and end at any cell in the bottom row. You can only travel in the four cardinal directions (left, right, up, and down).
 *
 * Return the last day where it is possible to walk from the top to the bottom by only walking on land cells.
 *
 * Input: row = 2, col = 2, cells = [[1,1],[2,1],[1,2],[2,2]]
 * Output: 2
 * Explanation: The above image depicts how the matrix changes each day starting from day 0.
 * The last day where it is possible to cross from top to bottom is on day 2.
 *
 * Input: row = 2, col = 2, cells = [[1,1],[1,2],[2,1],[2,2]]
 * Output: 1
 * Explanation: The above image depicts how the matrix changes each day starting from day 0.
 * The last day where it is possible to cross from top to bottom is on day 1.
 *
 * Input: row = 3, col = 3, cells = [[1,2],[2,1],[3,3],[2,2],[1,1],[1,3],[2,3],[3,2],[3,1]]
 * Output: 3
 * Explanation: The above image depicts how the matrix changes each day starting from day 0.
 * The last day where it is possible to cross from top to bottom is on day 3.
 *
 * Constraints:
 * 2 <= row, col <= 2 * 10^4
 * 4 <= row * col <= 2 * 10^4
 * cells.length == row * col
 * 1 <= r_i <= row
 * 1 <= c_i <= col
 * All the values of cells are unique.
 * </pre>
 */
public class LastDayWhereYouCanStillCross {
    int[] x_offset = new int[]{0, 1, 0, -1};
    int[] y_offset = new int[]{1, 0, -1, 0};

    /**
     * Approach: Union Find + Dummy node, First mark all the cells in the grid as blocked. Then iterate from the last day and start unblocking the cells
     * as the day passes. The first day when the first row and the last row gets connected, is the result.
     *
     * How to find whether the first and last row are connected?
     * 1. Use a set of parent of all cells in first row. Repeat the same process for cells in second row. Do a set intersection ~1500 ms. My original approach in the contest.
     * 2. Use a set of parent of all cells in first row. Check if any parent of cells in second row is present in the set or not. ~400 ms.
     * 3. Use a dummy node to connect cells in first row and a different dummy node to connect cells in last row. If both the dummy nodes have the same parent, then
     * they are connected. ~18 ms
     *
     * Alternative solution would be to use BFS + Binary Search, it will be a bit suboptimal but easier to code.
     *
     * Really happy to solve this during the contest. I was not able to solve any medium question in this contest but was able to solve easy + hard :D
     *
     * {@link PathWithMaximumMinimumValue} {@link SwimInRisingWater} {@link OptimizeWaterDistributionInAVillage}
     */
    public int latestDayToCross(int row, int col, int[][] cells) {
        int n = row * col + 2; //n-1, n-2 are dummy nodes
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        int[][] grid = new int[row][col];
        for (int i = 0; i < cells.length; i++) { //block all the cells with land
            int x = cells[i][0] - 1; //1 based indexing
            int y = cells[i][1] - 1;
            grid[x][y] = 1;
        }
        for (int i = cells.length - 1; i >= 0; i--) { //iterate from the reverse
            int x = cells[i][0] - 1;
            int y = cells[i][1] - 1;
            grid[x][y] = 0; //unblock cell
            for (int j = 0; j < 4; j++) {
                int new_x = x + x_offset[j];
                int new_y = y + y_offset[j];
                if (new_x >= 0 && new_x < row && new_y >= 0 && new_y < col && grid[new_x][new_y] == 0) {
                    //If the neighbour is valid and is unblocked as well, make them part of the same component
                    union(x * col + y, new_x * col + new_y, parent);
                }
            }
            if (x == 0) { //if the cell is part of first row, union it with the dummy node
                union(n - 1, y, parent);
            }
            if (x == row - 1) { //if the cell is part of last row, union it with the dummy node
                union(n - 2, x * col + y, parent);
            }
            if (find(n - 1, parent) == find(n - 2, parent)) { //if both dummy nodes have the same parent, they are connected
                return i;
            }
        }
        return 0;
    }

    private int find(int idx, int[] parent) {
        if (parent[idx] == idx) {
            return idx;
        } else {
            return parent[idx] = find(parent[idx], parent);
        }
    }

    private void union(int idx1, int idx2, int[] parent) {
        int root1 = find(idx1, parent);
        int root2 = find(idx2, parent);
        if (root1 != root2) {
            parent[root1] = root2;
        }
    }
}

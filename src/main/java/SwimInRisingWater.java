import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/swim-in-rising-water/
 * <p>
 * On an N x N grid, each square grid[i][j] represents the elevation at that point (i,j).
 * <p>
 * Now rain starts to fall. At time t, the depth of the water everywhere is t. You can swim from a square to another 4-directionally adjacent square
 * if and only if the elevation of both squares individually are at most t. You can swim infinite distance in zero time.
 * Of course, you must stay within the boundaries of the grid during your swim.
 * <p>
 * You start at the top left square (0, 0). What is the least time until you can reach the bottom right square (N-1, N-1)?
 * <p>
 * Input: [[0,2],[1,3]]
 * Output: 3
 * Explanation:
 * At time 0, you are in grid location (0, 0).
 * You cannot go anywhere else because 4-directionally adjacent neighbors have a higher elevation than t = 0.
 * You cannot reach point (1, 1) until time 3.
 * When the depth of water is 3, we can swim anywhere inside the grid.
 * <p>
 * Input: [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
 * Output: 16
 * Explanation:
 * <pre>
 *  0  1  2  3  4
 * 24 23 22 21  5
 * 12 13 14 15 16
 * 11 17 18 19 20
 * 10  9  8  7  6
 * </pre>
 * The final route is marked in bold.
 * We need to wait until time 16 so that (0, 0) and (4, 4) are connected.
 * <p>
 * Note:
 * 2 <= N <= 50.
 * grid[i][j] is a permutation of [0, ..., N*N - 1].
 */
public class SwimInRisingWater {
    int[] x_offsets = new int[]{0, 1, 0, -1};
    int[] y_offsets = new int[]{1, 0, -1, 0};

    /**
     * Approach: Minimize the maximum value present in the path
     * Multiple approaches can be used to solve the problem, Binary Search, Priority Queue or Union Find
     * <p>
     * {@link PathWithMaximumMinimumValue} {@link PathWithMinimumEffort} {@link PathWithMaximumGold} {@link MinimumCostToMakeAtLeastOneValidPathInAGrid}
     * {@link LastDayWhereYouCanStillCross}
     */
    public int swimInWater(int[][] grid) {
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.currentVal, o2.currentVal)); //we chose the node with smallest current value
        pq.add(new Node(0, 0, grid[0][0], grid[0][0]));
        grid[0][0] = -1;
        while (!pq.isEmpty()) {
            Node head = pq.poll();
            if (head.x == grid.length - 1 && head.y == grid[0].length - 1) { //target node reached, we must have reached it
                //by choosing the path with the smallest maximum value
                return head.maxValInPath;
            } else {
                for (int i = 0; i < 4; i++) {
                    int new_row = head.x + x_offsets[i];
                    int new_col = head.y + y_offsets[i];
                    if (new_row >= 0 && new_row < grid.length && new_col >= 0 && new_col < grid[0].length && grid[new_row][new_col] >= 0) {
                        pq.add(new Node(new_row, new_col, grid[new_row][new_col], Math.max(head.maxValInPath, grid[new_row][new_col])));
                        grid[new_row][new_col] = -1;
                    }
                }
            }
        }
        return 0;
    }

    private static class Node {
        int x;
        int y;
        int currentVal; //redundant, used only in comparator
        int maxValInPath;

        public Node(int x, int y, int currentVal, int maxValInPath) {
            this.x = x;
            this.y = y;
            this.currentVal = currentVal;
            this.maxValInPath = maxValInPath;
        }
    }
}

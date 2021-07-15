import javafx.util.Pair;

import java.util.ArrayDeque;

/**
 * <pre>
 * https://leetcode.com/problems/nearest-exit-from-entrance-in-maze/
 *
 * You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls (represented as '+').
 * You are also given the entrance of the maze, where entrance = [entrance_row, entrance_col] denotes the row and column of the cell you are initially standing at.
 *
 * In one step, you can move one cell up, down, left, or right. You cannot step into a cell with a wall, and you cannot step outside the maze.
 * Your goal is to find the nearest exit from the entrance. An exit is defined as an empty cell that is at the border of the maze. The entrance does not count as an exit.
 *
 * Return the number of steps in the shortest path from the entrance to the nearest exit, or -1 if no such path exists.
 *
 * Input: maze = [["+","+",".","+"],[".",".",".","+"],["+","+","+","."]], entrance = [1,2]
 * Output: 1
 * Explanation: There are 3 exits in this maze at [1,0], [0,2], and [2,3].
 * Initially, you are at the entrance cell [1,2].
 * - You can reach [1,0] by moving 2 steps left.
 * - You can reach [0,2] by moving 1 step up.
 * It is impossible to reach [2,3] from the entrance.
 * Thus, the nearest exit is [0,2], which is 1 step away.
 *
 * Input: maze = [["+","+","+"],[".",".","."],["+","+","+"]], entrance = [1,0]
 * Output: 2
 * Explanation: There is 1 exit in this maze at [1,2].
 * [1,0] does not count as an exit since it is the entrance cell.
 * Initially, you are at the entrance cell [1,0].
 * - You can reach [1,2] by moving 2 steps right.
 * Thus, the nearest exit is [1,2], which is 2 steps away.
 *
 * Input: maze = [[".","+"]], entrance = [0,0]
 * Output: -1
 * Explanation: There are no exits in this maze.
 *
 * Constraints:
 * maze.length == m
 * maze[i].length == n
 * 1 <= m, n <= 100
 * maze[i][j] is either '.' or '+'.
 * entrance.length == 2
 * 0 <= entrance_row < m
 * 0 <= entrance_col < n
 * entrance will always be an empty cell.
 * </pre>
 */
public class NearestExitFromEntranceInMaze {
    int[] dirX = new int[]{0, 1, 0, -1};
    int[] dirY = new int[]{1, 0, -1, 0};

    /**
     * Approach: BFS, start BFS from the entrance till we find the first exit.
     *
     * Graph has cycles with edges having equal weight, so BFS is the natural choice here.
     * Remember if the graph was cyclic with unequal weights, Djikstra will be applicable.
     * During the contest, I went in the reverse direction. I thought the problem to be similar as {@link SurroundedRegions} and performed the BFS
     * from outer nodes (exits). It did work but the code was a bit lengthy, which potentially cost me a better rank.
     */
    public int nearestExit(char[][] maze, int[] entrance) {
        int m = maze.length;
        int n = maze[0].length;
        maze[entrance[0]][entrance[1]] = 'x'; //mark the cell as visited
        ArrayDeque<Pair<Integer, Integer>> queue = new ArrayDeque<>();
        queue.add(new Pair<>(entrance[0], entrance[1]));
        int distance = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            distance++;
            for (int i = 0; i < size; i++) {
                Pair<Integer, Integer> head = queue.remove();
                for (int j = 0; j < 4; j++) {
                    int new_x = head.getKey() + dirX[j];
                    int new_y = head.getValue() + dirY[j];
                    if (new_x >= 0 && new_x < m && new_y >= 0 && new_y < n && maze[new_x][new_y] == '.') { //if cell is valid and empty
                        if (new_x == 0 || new_x == m - 1 || new_y == 0 || new_y == n - 1) { //found an exit
                            return distance;
                        }
                        maze[new_x][new_y] = 'x'; //always remember to mark the cell as visited first before adding it to queue
                        //this allows to not add duplicate nodes in the queue, lesson learnt from solving RottingOranges.
                        queue.add(new Pair<>(new_x, new_y));
                    }
                }
            }
        }
        return -1;
    }
}

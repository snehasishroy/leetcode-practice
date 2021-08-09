/**
 * <pre>
 * https://leetcode.com/problems/check-if-move-is-legal/
 *
 * You are given a 0-indexed 8 x 8 grid board, where board[r][c] represents the cell (r, c) on a game board.
 * On the board, free cells are represented by '.', white cells are represented by 'W', and black cells are represented by 'B'.
 *
 * Each move in this game consists of choosing a free cell and changing it to the color you are playing as (either white or black).
 * However, a move is only legal if, after changing it, the cell becomes the endpoint of a good line (horizontal, vertical, or diagonal).
 *
 * A good line is a line of three or more cells (including the endpoints) where the endpoints of the line are one color,
 * and the remaining cells in the middle are the opposite color (no cells in the line are free). You can find examples for good lines in the figure below:
 *
 * Given two integers rMove and cMove and a character color representing the color you are playing as (white or black),
 * return true if changing cell (rMove, cMove) to color color is a legal move, or false if it is not legal.
 *
 * Input: board = [[".",".",".","B",".",".",".","."],[".",".",".","W",".",".",".","."],[".",".",".","W",".",".",".","."],
 * [".",".",".","W",".",".",".","."],["W","B","B",".","W","W","W","B"],[".",".",".","B",".",".",".","."],
 * [".",".",".","B",".",".",".","."],[".",".",".","W",".",".",".","."]], rMove = 4, cMove = 3, color = "B"
 * Output: true
 * Explanation: '.', 'W', and 'B' are represented by the colors blue, white, and black respectively, and cell (rMove, cMove) is marked with an 'X'.
 * The two good lines with the chosen cell as an endpoint are annotated above with the red rectangles.
 *
 * Input: board = [[".",".",".",".",".",".",".","."],[".","B",".",".","W",".",".","."],[".",".","W",".",".",".",".","."],
 * [".",".",".","W","B",".",".","."],[".",".",".",".",".",".",".","."],[".",".",".",".","B","W",".","."],
 * [".",".",".",".",".",".","W","."],[".",".",".",".",".",".",".","B"]], rMove = 4, cMove = 4, color = "W"
 * Output: false
 * Explanation: While there are good lines with the chosen cell as a middle cell, there are no good lines with the chosen cell as an endpoint.
 *
 * Constraints:
 * board.length == board[r].length == 8
 * 0 <= rMove, cMove < 8
 * board[rMove][cMove] == '.'
 * color is either 'B' or 'W'.
 * </pre>
 */
public class CheckIfMoveIsLegal {
    int[] x_offset = new int[]{0, 1, 0, -1, -1, 1, 1, -1};
    int[] y_offset = new int[]{1, 0, -1, 0, 1, 1, -1, -1};

    /**
     * Approach: Simulation, Start iterating from the given r,c in 8 directions until we find a blank cell or a cell with same color.
     * There should be at least one intermediate cell with different color in between.
     *
     * During the contest I wrongly assumed that the endpoint must always end at the border and hence got WA. I was unable to get a working solution
     * even after getting the test case that was failing since I was unable to see the valid endpoint. Was able to solve only one question in this contest.
     * Feels bad man :(
     *
     * {@link alternate.ToeplitzMatrix} {@link EliminateMaximumNumberOfMonsters}
     */
    public boolean checkMove(char[][] board, int r, int c, char color) {
        for (int i = 0; i < x_offset.length; i++) { //move in 8 directions and check if any of them results in a legal move
            if (isLegal(board, r, c, x_offset[i], y_offset[i], color)) {
                return true;
            }
        }
        return false;
    }

    private boolean isLegal(char[][] board, int r, int c, int x_offset, int y_offset, int color) {
        r += x_offset;
        c += y_offset;
        int moves = 0;
        while (r < 8 && r >= 0 && c < 8 && c >= 0) {
            if (board[r][c] == '.') { //empty cell found
                return false;
            }
            if (board[r][c] == color) { //cell with same color found, check if there are at least one intermediate cells in between
                return moves >= 1;
            }
            r += x_offset;
            c += y_offset;
            moves++;
        }
        return false;
    }
}

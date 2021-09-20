/**
 * https://leetcode.com/problems/design-tic-tac-toe/ Premium
 * <p>
 * Assume the following rules are for the tic-tac-toe game on an n x n board between two players:
 * <p>
 * A move is guaranteed to be valid and is placed on an empty block.
 * Once a winning condition is reached, no more moves are allowed.
 * A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
 * <p>
 * Follow up:
 * Could you do better than O(n2) per move() operation?
 * <pre>
 * Input
 * ["TicTacToe", "move", "move", "move", "move", "move", "move", "move"]
 * [[3], [0, 0, 1], [0, 2, 2], [2, 2, 1], [1, 1, 2], [2, 0, 1], [1, 0, 2], [2, 1, 1]]
 * Output
 * [null, 0, 0, 0, 0, 0, 0, 1]
 *
 * Explanation
 * TicTacToe ticTacToe = new TicTacToe(3);
 * Assume that player 1 is "X" and player 2 is "O" in the board.
 * ticTacToe.move(0, 0, 1); // return 0 (no one wins)
 * |X| | |
 * | | | |    // Player 1 makes a move at (0, 0).
 * | | | |
 *
 * ticTacToe.move(0, 2, 2); // return 0 (no one wins)
 * |X| |O|
 * | | | |    // Player 2 makes a move at (0, 2).
 * | | | |
 *
 * ticTacToe.move(2, 2, 1); // return 0 (no one wins)
 * |X| |O|
 * | | | |    // Player 1 makes a move at (2, 2).
 * | | |X|
 *
 * ticTacToe.move(1, 1, 2); // return 0 (no one wins)
 * |X| |O|
 * | |O| |    // Player 2 makes a move at (1, 1).
 * | | |X|
 *
 * ticTacToe.move(2, 0, 1); // return 0 (no one wins)
 * |X| |O|
 * | |O| |    // Player 1 makes a move at (2, 0).
 * |X| |X|
 *
 * ticTacToe.move(1, 0, 2); // return 0 (no one wins)
 * |X| |O|
 * |O|O| |    // Player 2 makes a move at (1, 0).
 * |X| |X|
 *
 * ticTacToe.move(2, 1, 1); // return 1 (player 1 wins)
 * |X| |O|
 * |O|O| |    // Player 1 makes a move at (2, 1).
 * |X|X|X|
 * </pre>
 * <p>
 * Constraints:
 * 2 <= n <= 100
 * player is 1 or 2.
 * 1 <= row, col <= n
 * (row, col) are unique for each different call to move.
 * At most n^2 calls will be made to move.
 */
public class DesignTicTacToe {
    int n;
    int[][] rows, cols, diagonal, antiDiagonal;

    /**
     * Approach: Maintain reverse index or lookup table, Instead of actually placing the specific character and then checking whether the current row, col and diagonals
     * are filled with same character, keep track of the count of characters placed per player.
     * <p>
     * Count needs to be tracked per row, per col, per diagonal and anti-diagonal. A bit of optimization can be performed for
     * diagonal, as only the longest diagonal can contribute to the result, instead of tracking all the diagonals, tracking just
     * two count variables for diagonal and anti-diagonal should be sufficient
     * <p>
     * Time Complexity: O(1)
     * <p>
     * {@link ValidSudoku} {@link NQueen} {@link LongestLineOfConsecutiveOneInMatrix} {@link DiagonalTraverse} related problems
     */
    public DesignTicTacToe(int n) {
        this.n = n;
        rows = new int[n][3];
        cols = new int[n][3];
        diagonal = new int[2 * n - 1][3]; //there are a total of 2n - 1 diagonals
        antiDiagonal = new int[2 * n - 1][3]; //top right to bottom left direction
    }

    /**
     * Player {player} makes a move at ({row}, {col}).
     *
     * @param row    The row of the board.
     * @param col    The column of the board.
     * @param player The player, can be either 1 or 2.
     * @return The current winning condition, can be either:
     * 0: No one wins.
     * 1: Player 1 wins.
     * 2: Player 2 wins.
     */
    public int move(int row, int col, int player) {
        rows[row][player]++;
        cols[col][player]++;
        antiDiagonal[row + col][player]++;
        int diagonalId = row - col;
        if (diagonalId < 0) {
            diagonalId *= -1;
            diagonalId += (n - 1);
        }
        diagonal[diagonalId][player]++;
        if (rows[row][player] == n || cols[col][player] == n || diagonal[diagonalId][player] == n || antiDiagonal[row + col][player] == n) {
            return player;
        } else {
            return 0;
        }
    }
}

/**
 * <pre>
 * https://leetcode.com/problems/find-winner-on-a-tic-tac-toe-game/
 *
 * Tic-tac-toe is played by two players A and B on a 3 x 3 grid.
 *
 * Here are the rules of Tic-Tac-Toe:
 *
 * Players take turns placing characters into empty squares (" ").
 * The first player A always places "X" characters, while the second player B always places "O" characters.
 * "X" and "O" characters are always placed into empty squares, never on filled ones.
 * The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
 * The game also ends if all squares are non-empty.
 * No more moves can be played if the game is over.
 * Given an array moves where each element is another array of size 2 corresponding to the row and column of the grid where they mark their respective character in the order in which A and B play.
 *
 * Return the winner of the game if it exists (A or B), in case the game ends in a draw return "Draw", if there are still movements to play return "Pending".
 *
 * You can assume that moves is valid (It follows the rules of Tic-Tac-Toe), the grid is initially empty and A will play first.
 *
 * Input: moves = [[0,0],[2,0],[1,1],[2,1],[2,2]]
 * Output: "A"
 * Explanation: "A" wins, he always plays first.
 * "X  "    "X  "    "X  "    "X  "    "X  "
 * "   " -> "   " -> " X " -> " X " -> " X "
 * "   "    "O  "    "O  "    "OO "    "OOX"
 *
 * Input: moves = [[0,0],[1,1],[0,1],[0,2],[1,0],[2,0]]
 * Output: "B"
 * Explanation: "B" wins.
 * "X  "    "X  "    "XX "    "XXO"    "XXO"    "XXO"
 * "   " -> " O " -> " O " -> " O " -> "XO " -> "XO "
 * "   "    "   "    "   "    "   "    "   "    "O  "
 *
 * Input: moves = [[0,0],[1,1],[2,0],[1,0],[1,2],[2,1],[0,1],[0,2],[2,2]]
 * Output: "Draw"
 * Explanation: The game ends in a draw since there are no moves to make.
 * "XXO"
 * "OOX"
 * "XOX"
 *
 * Input: moves = [[0,0],[1,1]]
 * Output: "Pending"
 * Explanation: The game has not finished yet.
 * "X  "
 * " O "
 * "   "
 * Constraints:
 * 1 <= moves.length <= 9
 * moves[i].length == 2
 * 0 <= moves[i][j] <= 2
 * There are no repeated elements on moves.
 * moves follow the rules of tic tac toe.
 * </pre>
 */
public class FindWinnerOnATicTacToeGame {
    /**
     * Approach: Keep track of the moves made by a player for each row, column, diagonal and anti-diagonal. If the no of moves equals n
     * for any condition, that player has won the game.
     *
     * {@link DesignTicTacToe}
     */
    public String tictactoe(int[][] moves) {
        int n = 3;
        int[][] rows = new int[3][2]; //row[i][j] denotes the no of chars placed by jth player on ith row
        int[][] cols = new int[3][2];
        //although we are interested only in the primary diagonal and anti-diagonal, which can be tracked by just two variables
        //using an array makes the code generic and allows us to keep track of all the diagonals
        //There can be total 2 * n - 1 diagonals, but half of the id's of the diagonal can be negative, so we have to turn them positive
        //Diagonal ids from left to right (r-c) -> 2, 1, 0, -1, -2
        //we can turn -1, -2 positive by taking their absolute value and incrementing it by (n - 1)
        int[][] diagonal = new int[2 * n - 1][2];
        int[][] antiDiagonal = new int[2 * n - 1][2];
        for (int i = 0; i < moves.length; i++) {
            int playerId = i % 2;
            int r = moves[i][0];
            int c = moves[i][1];
            rows[r][playerId]++;
            cols[c][playerId]++;
            int diagonalId = r - c;
            if (diagonalId < 0) {
                diagonalId *= -1;
                diagonalId += n - 1;
            }
            diagonal[diagonalId][playerId]++;
            antiDiagonal[r + c][playerId]++;
            if (rows[r][playerId] == n || cols[c][playerId] == n || diagonal[diagonalId][playerId] == n || antiDiagonal[r + c][playerId] == n) {
                return playerId == 0 ? "A" : "B";
            }
        }
        return moves.length == 9 ? "Draw" : "Pending";
    }
}

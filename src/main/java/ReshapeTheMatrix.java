/**
 * <pre>
 * https://leetcode.com/problems/reshape-the-matrix/
 *
 * In MATLAB, there is a handy function called reshape which can reshape an m x n matrix into a new one with a different size r x c keeping its original data.
 *
 * You are given an m x n matrix mat and two integers r and c representing the row number and column number of the wanted reshaped matrix.
 *
 * The reshaped matrix should be filled with all the elements of the original matrix in the same row-traversing order as they were.
 *
 * If the reshape operation with given parameters is possible and legal, output the new reshaped matrix; Otherwise, output the original matrix.
 *
 * Input: mat = [[1,2],[3,4]], r = 1, c = 4
 * Output: [[1,2,3,4]]
 *
 * Input: mat = [[1,2],[3,4]], r = 2, c = 4
 * Output: [[1,2],[3,4]]
 *
 * Constraints:
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 100
 * -1000 <= mat[i][j] <= 1000
 * 1 <= r, c <= 300
 * </pre>
 */
public class ReshapeTheMatrix {
    /**
     * Approach: Two pointers, Keep two pointers in the original matrix and fill the new matrix greedily
     */
    public int[][] matrixReshape(int[][] mat, int r, int c) {
        int m = mat.length;
        int n = mat[0].length;
        if ((r * c) != (m * n)) { //if number of elements present does not match the target
            return mat;
        } else {
            int[][] reshaped = new int[r][c];
            int mat_i = 0, mat_j = 0; //two pointers in the original matrix
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    reshaped[i][j] = mat[mat_i][mat_j];
                    mat_j++;
                    if (mat_j == n) { //end of column reached
                        mat_j = 0; //reset to first column
                        mat_i++; //move to next row
                    }
                }
            }
            return reshaped;
        }
    }
}

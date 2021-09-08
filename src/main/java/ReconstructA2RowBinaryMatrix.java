import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * https://leetcode.com/problems/reconstruct-a-2-row-binary-matrix/
 *
 * Given the following details of a matrix with n columns and 2 rows :
 *
 * The matrix is a binary matrix, which means each element in the matrix can be 0 or 1.
 * The sum of elements of the 0-th(upper) row is given as upper.
 * The sum of elements of the 1-st(lower) row is given as lower.
 * The sum of elements in the i-th column(0-indexed) is colsum[i], where colsum is given as an integer array with length n.
 * Your task is to reconstruct the matrix with upper, lower and colsum.
 *
 * Return it as a 2-D integer array.
 *
 * If there are more than one valid solution, any of them will be accepted.
 * If no valid solution exists, return an empty 2-D array.
 *
 * Input: upper = 2, lower = 1, colsum = [1,1,1]
 * Output: [[1,1,0],[0,0,1]]
 * Explanation: [[1,0,1],[0,1,0]], and [[0,1,1],[1,0,0]] are also correct answers.
 *
 * Input: upper = 2, lower = 3, colsum = [2,2,1,1]
 * Output: []
 *
 * Input: upper = 5, lower = 5, colsum = [2,1,2,0,1,0,1,2,0,1]
 * Output: [[1,1,1,0,1,0,0,1,0,0],[1,0,1,0,0,0,1,1,0,1]]
 *
 * Constraints:
 * 1 <= colsum.length <= 10^5
 * 0 <= upper, lower <= colsum.length
 * 0 <= colsum[i] <= 2
 * </pre>
 */
public class ReconstructA2RowBinaryMatrix {
    /**
     * Approach: Reconstruction Problem, Tricky Greedy, two pass solution. When the colsum of a column is 2, then we must put 1 at both the rows.
     * However, if the colsum is 1, then we can put 1 at first row or at last row depending on the current count of upper and lower.
     *
     * In greedy reconstruction problems, try to first fill what is known and does not have any choice.
     *
     * {@link FurthestBuildingYouCanReach} {@link CarFleet}
     */
    public List<List<Integer>> reconstructMatrixGreedy(int upper, int lower, int[] colsum) {
        int maxOnes = upper + lower;
        int allowedOnes = 0;
        for (int val : colsum) {
            allowedOnes += val;
        }
        if (allowedOnes != maxOnes) { //check if (upper + lower) == sum of all the elements in colSum
            return new ArrayList<>();
        } else {
            List<List<Integer>> matrix = new ArrayList<>();
            int n = colsum.length;
            for (int i = 0; i < 2; i++) {
                List<Integer> list = new ArrayList<>();
                for (int j = 0; j < n; j++) {
                    list.add(0);
                }
                matrix.add(list);
            }
            for (int i = 0; i < n; i++) {
                if (colsum[i] == 2) { //place 1 at both the rows
                    matrix.get(0).set(i, 1);
                    matrix.get(1).set(i, 1);
                    upper--;
                    lower--;
                    if (upper < 0 || lower < 0) { //if it's not possible to place both the 1's, return an empty matrix
                        return new ArrayList<>();
                    }
                }
            }
            for (int i = 0; i < n; i++) {
                if (colsum[i] == 1) { //greedily try to place 1 at row having the highest allowed count of 1's
                    if (upper > lower) {
                        matrix.get(0).set(i, 1);
                        upper--;
                    } else if (lower > 0) {
                        matrix.get(1).set(i, 1);
                        lower--;
                    } else { //not possible to place 1 as both counts have exhausted
                        return new ArrayList<>();
                    }
                }
            }
            return matrix;
        }
    }

    /**
     * Approach: Recursion, Modified Knapsack problem, if colsum[index] is 1, we have two options, either set the first row to 1 or the last row to 1
     * So we use recursion to try both the options and move to next index.
     * TimeComplexity: 2^n  Leads to TLE as value of n is 10^5
     *
     * If we try to memoize, no of states would be lower * upper * index == n^3, which is again suboptimal given input constraints.
     *
     * {@link QueueReconstructionByHeight} {@link TwoCityScheduling}
     */
    public List<List<Integer>> reconstructMatrixRecursive(int upper, int lower, int[] colsum) {
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < colsum.length; j++) {
                list.add(0);
            }
            matrix.add(list);
        }
        boolean isPossible = recur(matrix, upper, lower, colsum, 0);
        return isPossible ? matrix : new ArrayList<>();
    }

    private boolean recur(List<List<Integer>> matrix, int upper, int lower, int[] colsum, int index) {
        if (upper < 0 || lower < 0) {
            return false;
        } else if (index == colsum.length) {
            return upper == 0 && lower == 0; //verify all the 1's have been placed or not
        } else if (colsum[index] == 0) {
            return recur(matrix, upper, lower, colsum, index + 1);
        } else if (colsum[index] == 2) { //need to place 1 at both the rows
            matrix.get(0).set(index, 1);
            matrix.get(1).set(index, 1);
            return recur(matrix, upper - 1, lower - 1, colsum, index + 1);
        } else {
            //try setting 1 at first row
            matrix.get(0).set(index, 1);
            boolean isPossible = recur(matrix, upper - 1, lower, colsum, index + 1);
            if (isPossible) {
                return true;
            }
            //try setting 1 at last row
            matrix.get(0).set(index, 0); //unset
            matrix.get(1).set(index, 1);
            return recur(matrix, upper, lower - 1, colsum, index + 1);
        }
    }
}

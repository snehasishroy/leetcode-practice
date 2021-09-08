/**
 * <pre>
 * https://leetcode.com/problems/range-addition-ii/
 *
 * You are given an m x n matrix M initialized with all 0's and an array of operations ops, where ops[i] = [ai, bi] means M[x][y] should be incremented by one for all 0 <= x < ai and 0 <= y < bi.
 *
 * Count and return the number of maximum integers in the matrix after performing all the operations.
 *
 * Input: m = 3, n = 3, ops = [[2,2],[3,3]]
 * Output: 4
 * Explanation: The maximum integer in M is 2, and there are four of it in M. So return 4.
 *
 * Input: m = 3, n = 3, ops = [[2,2],[3,3],[3,3],[3,3],[2,2],[3,3],[3,3],[3,3],[2,2],[3,3],[3,3],[3,3]]
 * Output: 4
 *
 * Input: m = 3, n = 3, ops = []
 * Output: 9
 *
 * Constraints:
 * 1 <= m, n <= 4 * 10^4
 * 0 <= ops.length <= 10^4
 * ops[i].length == 2
 * 1 <= ai <= m
 * 1 <= bi <= n
 * </pre>
 */
public class RangeAddition2 {
    /**
     * Approach: Tricky greedy, Problem reduces to finding the common rectangular overlap between all the rectangles originating from [0,0]
     * Critical observation is to keep track of the smallest height and the smallest width separately, area of which will return the common overlap.
     *
     * Initially I solved it by sorting the ops[] and returning the area of the smallest rectangle, but it gave WA as the entire area of the
     * smallest rectangle need not be the intersection between all the rectangles. It can be a partial overlap as well.
     *
     * {@link RangeAddition} {@link RectangleOverlap} {@link EmployeeFreeTime} {@link BestMeetingPoint}
     */
    public int maxCount(int m, int n, int[][] ops) {
        int minX = m, minY = n;
        for (int[] op : ops) {
            minX = Math.min(minX, op[0]);
            minY = Math.min(minY, op[1]);
        }
        return minX * minY;
    }
}

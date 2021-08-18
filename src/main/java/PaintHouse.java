/**
 * https://leetcode.com/problems/paint-house/ Premium
 * <p>
 * There is a row of n houses, where each house can be painted one of three colors: red, blue, or green.
 * The cost of painting each house with a certain color is different. You have to paint all the houses such that no two adjacent houses have the same color.
 * <p>
 * The cost of painting each house with a certain color is represented by a n x 3 cost matrix.
 * For example, costs[0][0] is the cost of painting house 0 with the color red; costs[1][2] is the cost of painting house 1 with color green, and so on...
 * Find the minimum cost to paint all houses.
 * <p>
 * Input: costs = [[17,2,17],[16,16,5],[14,3,19]]
 * Output: 10
 * Explanation: Paint house 0 into blue, paint house 1 into green, paint house 2 into blue.
 * Minimum cost: 2 + 5 + 3 = 10.
 */
public class PaintHouse {
    /**
     * Approach: Need to try all the combinations and store the minimum of all of them at any index
     * Took me a while to convert the recursive solution into top down memoization
     * I remembered ericto's hint to solve DP problem, what information is required at each state.
     * Here we need the house index and color being painted at that index.
     *
     * {@link PaintFence} {@link PaintHouse2}
     */
    public int minCost(int[][] costs) {
        int n = costs.length;
        int[][] memoized = new int[n][3];
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 3; i++) { //try painting the first house with r, b, g
            res = Math.min(res, recur(costs, 0, i, memoized));
        }
        return res;
    }

    private int recur(int[][] costs, int idx, int color, int[][] memoized) {
        if (idx == costs.length) {
            return 0;
        }
        if (memoized[idx][color] != 0) {
            return memoized[idx][color];
        }
        int result = Integer.MAX_VALUE;
        for (int i = 1; i <= 2; i++) { //try generating the new colors for the next house
            int new_color = (color + i) % 3;
            result = Math.min(result, costs[idx][color] + recur(costs, idx + 1, new_color, memoized));
        }
        return memoized[idx][color] = result;
    }
}

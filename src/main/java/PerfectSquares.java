import java.util.Arrays;

/**
 * https://leetcode.com/problems/perfect-squares/
 * <p>
 * Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.
 * <p>
 * Input: n = 12
 * Output: 3
 * Explanation: 12 = 4 + 4 + 4.
 * <p>
 * Input: n = 13
 * Output: 2
 * Explanation: 13 = 4 + 9.
 */
public class PerfectSquares {
    /**
     * This is similar to the optimized code in {@link WordBreak1}
     *
     * {@link EggDropWith2EggsAndNFloors}
     */
    public int numSquaresUsingBFS(int n) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i * i <= n; i++) {
            dp[i * i] = 1; //this are the nodes reachable in 1 hop
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; i + j * j <= n; j++) {
                dp[i + j * j] = Math.min(dp[i + j * j], dp[i] + 1); // from the current node, mark the next nodes reachable in 1 hop
            }
        }
        return dp[n];
    }

    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            double sqrt = Math.sqrt(i);
            if (sqrt == (int) sqrt) {
                dp[i] = 1; //perfect square
            } else {
                int min = Integer.MAX_VALUE;
                //minimum count can be obtained by just adding 1 to the min squares count of smaller perfect squares
                //PS: I tried iterating from 1 to mid of i, and getting the min but it was way slower
                for (int j = 1; j * j <= i; j++) {
                    min = Math.min(min, 1 + dp[i - j * j]);
                }
                dp[i] = min;
            }
        }
        return dp[n];
    }
}

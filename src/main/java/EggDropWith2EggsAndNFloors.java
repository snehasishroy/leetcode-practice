/**
 * <pre>
 * https://leetcode.com/problems/egg-drop-with-2-eggs-and-n-floors/
 *
 * You are given two identical eggs and you have access to a building with n floors labeled from 1 to n.
 *
 * You know that there exists a floor f where 0 <= f <= n such that any egg dropped at a floor higher than f will break, and any egg dropped at or below floor f will not break.
 *
 * In each move, you may take an unbroken egg and drop it from any floor x (where 1 <= x <= n). If the egg breaks, you can no longer use it.
 * However, if the egg does not break, you may reuse it in future moves.
 *
 * Return the minimum number of moves that you need to determine with certainty what the value of f is.
 *
 * Input: n = 2
 * Output: 2
 * Explanation: We can drop the first egg from floor 1 and the second egg from floor 2.
 * If the first egg breaks, we know that f = 0.
 * If the second egg breaks but the first egg didn't, we know that f = 1.
 * Otherwise, if both eggs survive, we know that f = 2.
 *
 * Input: n = 100
 * Output: 14
 * Explanation: One optimal strategy is:
 * - Drop the 1st egg at floor 9. If it breaks, we know f is between 0 and 8. Drop the 2nd egg starting
 *   from floor 1 and going up one at a time to find f within 7 more drops. Total drops is 1 + 7 = 8.
 * - If the 1st egg does not break, drop the 1st egg again at floor 22. If it breaks, we know f is between 9
 *   and 21. Drop the 2nd egg starting from floor 10 and going up one at a time to find f within 12 more
 *   drops. Total drops is 2 + 12 = 14.
 * - If the 1st egg does not break again, follow a similar process dropping the 1st egg from floors 34, 45,
 *   55, 64, 72, 79, 85, 90, 94, 97, 99, and 100.
 * Regardless of the outcome, it takes at most 14 drops to determine f.
 *
 * Constraints:
 * 1 <= n <= 1000
 * </pre>
 */
public class EggDropWith2EggsAndNFloors {
    /**
     * Approach: Maths, find the x, such that (x * (x + 1) / 2) >= n
     *
     * I was able to solve this by seeing the pattern in the second example, the gap in the second example shows the pattern
     * as 1,2,3,4,5,6,7,8,9,10,11,12,13,14 in reverse order
     *
     * {@link BulbSwitcher}
     */
    public int twoEggDrop(int n) {
        double temp = Math.sqrt(1 + (8 * n)) - 1.0; //root of quadratic equation
        return (int) Math.ceil(temp / 2.0);
    }

    /**
     * Approach: DP, Every ith floor has two options, if the egg breaks or if it does not.
     * If it breaks, then we have (i - 1) floor remaining and 1 eggs
     * If it does not break, we have (n - i) floor remaining with 2 eggs
     *
     * For generic solution with N floors and K eggs, refer to {@link SuperEggDrop}
     * {@link PerfectSquares}
     */
    public int twoEggDropDP(int n) {
        int[] dp = new int[n + 1];
        return recur(n, dp);
    }

    private int recur(int n, int[] dp) {
        if (n < 1) {
            return 0;
        } else if (dp[n] != 0) {
            return dp[n];
        } else {
            int minDrops = Integer.MAX_VALUE;
            for (int i = 1; i <= n; i++) {
                int eggBreaks = i - 1; //if one egg breaks at ith floor, we are left with 1 egg and worst case we have to check total i-1 floor
                int eggDoesNotBreak = recur(n - i, dp); //if egg does not break, then we are left with 2 eggs and n-i floor, so this problem can be recursively solved
                int worstCase = 1 + Math.max(eggBreaks, eggDoesNotBreak); //we have to take the worst possible case
                minDrops = Math.min(minDrops, worstCase); //for each floor we have to keep track of the smallest worst possible case
            }
            return dp[n] = minDrops;
        }
    }
}
/**
 * <pre>
 * https://leetcode.com/problems/super-egg-drop/
 *
 * You are given k identical eggs and you have access to a building with n floors labeled from 1 to n.
 *
 * You know that there exists a floor f where 0 <= f <= n such that any egg dropped at a floor higher than f will break, and any egg dropped at or below floor f will not break.
 *
 * Each move, you may take an unbroken egg and drop it from any floor x (where 1 <= x <= n). If the egg breaks, you can no longer use it. However, if the egg does not break, you may reuse it in future moves.
 *
 * Return the minimum number of moves that you need to determine with certainty what the value of f is.
 *
 * Input: k = 1, n = 2
 * Output: 2
 * Explanation:
 * Drop the egg from floor 1. If it breaks, we know that f = 0.
 * Otherwise, drop the egg from floor 2. If it breaks, we know that f = 1.
 * If it does not break, then we know f = 2.
 * Hence, we need at minimum 2 moves to determine with certainty what the value of f is.
 *
 * Input: k = 2, n = 6
 * Output: 3
 * Example 3:
 *
 * Input: k = 3, n = 14
 * Output: 4
 *
 * Constraints:
 * 1 <= k <= 100
 * 1 <= n <= 10^4
 * </pre>
 */
public class SuperEggDrop {
    /**
     * <pre>
     * Approach: DP + Binary Search, Example of DP State Optimization Problems
     * In order to reduce time complexity, either reduce states or reduce transition time.
     * We will try to reduce transition time in this case by applying binary search.
     *
     * f(k,n) = min(x) 1<x<n max(f(k-1, x-1), f(k, n-x)) == max (t1, t2)
     * Critical observation is f(k, n) is increasing on n by fixing k i.e. more the floors, higher will be the no of steps required
     * Notice t1 is increasing function as we iteratively increase x from 1 to n in the iterative loop
     * and t2 is decreasing function as N-X decreases as we increase x
     *
     * So f(k,n) when plotted looks something like
     * \      / (t1 is increasing function)
     *  \    /
     *   \  /
     *    \/  (t1 == t2 at result)
     *    /\
     *   /  \
     *  /    \
     * /      \ (t2 is decreasing function)
     *
     * max(t1, t2) is the upper half i.e. it first decreases then increases. We need to find the inflection point using binary search.
     * So when t1 > t2, we know we are in the second half, otherwise we know we are in the first half.
     *
     * This is similar to finding pivot in {@link FindInMountainArray} but we are working on two functions instead of one.
     *
     * This will reduce the time complexity to O(k*n*log(n))
     * </pre>
     */
    public int superEggDropOptimized(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        return recurBinarySearch(k, n, dp);
    }

    private int recurBinarySearch(int k, int n, int[][] dp) {
        if (k < 1 || n < 1) { //if no eggs or no floor remaining, return 0
            return 0;
        } else if (k == 1) { //if 1 egg remaining, we have to test all the floors one by one
            return n;
        } else if (dp[k][n] != 0) {
            return dp[k][n];
        }
        int low = 1, high = n, minSteps = Integer.MAX_VALUE;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int t1 = recurBinarySearch(k - 1, mid - 1, dp);
            int t2 = recurBinarySearch(k, n - mid, dp);
            if (t1 > t2) { //second half, need to decrease mid
                high = mid - 1;
            } else {
                low = mid + 1;
            }
            int worstCase = 1 + Math.max(t1, t2);
            minSteps = Math.min(minSteps, worstCase);
        }
        return dp[k][n] = minSteps;
    }

    /**
     * Approach: DP, Given n floors and k eggs, if we pick ith floor, there are two options
     * 1. If egg breaks, then we are left with k-1 eggs and i-1 floors
     * 2. If egg does not break, then we are left with k eggs and n-i floors.
     * Use recursion to solve similar subproblem.
     *
     * TimeComplexity: O(K*N*N) TLE due to constraints
     *
     * {@link EggDropWith2EggsAndNFloors}
     */
    public int superEggDrop(int k, int n) {
        int[][] dp = new int[k + 1][n + 1];
        return recur(k, n, dp);
    }

    private int recur(int k, int n, int[][] dp) {
        if (k < 1 || n < 1) {
            return 0;
        } else if (k == 1) {
            return n;
        } else if (dp[k][n] != 0) {
            return dp[k][n];
        }
        int minSteps = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            int eggBreaks = recur(k - 1, i - 1, dp);
            int eggDoesNotBreak = recur(k, n - i, dp);
            int worstCase = 1 + Math.max(eggBreaks, eggDoesNotBreak);
            minSteps = Math.min(minSteps, worstCase);
        }
        return dp[k][n] = minSteps;
    }
}

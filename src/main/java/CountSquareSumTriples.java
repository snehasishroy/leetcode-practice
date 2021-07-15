/**
 * <pre>
 * https://leetcode.com/problems/count-square-sum-triples/
 *
 * A square triple (a,b,c) is a triple where a, b, and c are integers and a^2 + b^2 = c^2.
 *
 * Given an integer n, return the number of square triples such that 1 <= a, b, c <= n.
 *
 * Input: n = 5
 * Output: 2
 * Explanation: The square triples are (3,4,5) and (4,3,5).
 *
 * Input: n = 10
 * Output: 4
 * Explanation: The square triples are (3,4,5), (4,3,5), (6,8,10), and (8,6,10).
 *
 * Constraints:
 * 1 <= n <= 250
 * </pre>
 */
public class CountSquareSumTriples {
    /**
     * Approach: Fix a & b, check if the target c lies within n and is an integer. This step can be either performed using Math.sqrt() operation
     * or maintaining a precomputed hashset or a boolean array which keeps track of whether an integer is a perfect square.
     *
     * {@link ValidTriangleNumber}
     */
    public int countTriples(int n) {
        int res = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                int sum = (i * i) + (j * j);
                int target = (int) Math.sqrt(sum);
                if (target <= n && (target * target == sum)) {
                    res++;
                }
            }
        }
        return res;
    }
}

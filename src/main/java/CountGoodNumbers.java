/**
 * <pre>
 * https://leetcode.com/problems/count-good-numbers/
 *
 * A digit string is good if the digits (0-indexed) at even indices are even and the digits at odd indices are prime (2, 3, 5, or 7).
 *
 * For example, "2582" is good because the digits (2 and 8) at even positions are even and the digits (5 and 2) at odd positions are prime.
 * However, "3245" is not good because 3 is at an even index but is not even.
 * Given an integer n, return the total number of good digit strings of length n. Since the answer may be large, return it modulo 109 + 7.
 *
 * A digit string is a string consisting of digits 0 through 9 that may contain leading zeros.
 *
 * Input: n = 1
 * Output: 5
 * Explanation: The good numbers of length 1 are "0", "2", "4", "6", "8".
 *
 * Input: n = 4
 * Output: 400
 *
 * Input: n = 50
 * Output: 564908303
 *
 * Constraints:
 * 1 <= n <= 10^15
 * </pre>
 */
public class CountGoodNumbers {
    /**
     * Approach: Modular exponentiation. Such high constraints clearly indicates use of O(logn) or O(1) time complexity solution
     * Given n=10, solution would be
     * first digit 5 choices (0,2,4,6,8) (leading zeroes are allowed)
     * second digit 4 choices (2,3,5,7)
     * third digit 5 choices
     * ....
     * so result would be 5*4*5*4*5*4*5*4*5*4 = (5^5) * (4^5)
     *
     * If we simply multiply them, time complexity would be O(n), so we would need fast exponentiation by squaring
     * Made a mistake of accidentally casting the power to int during the contest, costing me a WA and a lot of time debugging it.
     * Still happy to solve it on my own.
     *
     * https://www.geeksforgeeks.org/modular-exponentiation-power-in-modular-arithmetic/
     *
     * {@link CalculateExponentInLogn}
     */
     private int power(long x, long y, int mod) { //calculate (x^y) % mod
        long res = 1; // Initialize result
        while (y > 0) {
            // If y is odd, multiply x with result
            if ((y & 1) != 0) {
                res = (res * x) % mod;
            }
            // y must be even now
            y = y >> 1;
            x = (x * x) % mod;
        }
        return (int) res;
    }

    public int countGoodNumbers(long n) {
        int mod = 1_000_000_007;
        long res1 = power(5, (long) Math.ceil(n / 2.0), mod);
        long res2 = power(4, n / 2, mod);
        return (int) ((res1 * res2) % mod);
    }
}

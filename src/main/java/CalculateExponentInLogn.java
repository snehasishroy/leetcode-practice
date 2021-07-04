/**
 * https://leetcode.com/problems/powx-n/
 * <p>
 * Implement pow(x, n), which calculates x raised to the power n (xn).
 * <p>
 * Example 1:
 * <p>
 * Input: 2.00000, 10
 * Output: 1024.00000
 * Example 2:
 * <p>
 * Input: 2.10000, 3
 * Output: 9.26100
 */
public class CalculateExponentInLogn {
    /**
     * Modular exponentiation using squaring
     *
     * {@link CountGoodNumbers}
     */
    public double myPow(double x, int n) {
        if (n < 0) {
            x = 1D / x;
            if (n == Integer.MIN_VALUE) { //stupid case for handling edge case
                n = Integer.MAX_VALUE;
                return myPow(x * x, n / 2);
                // original value was even but after switching it to int_max, it becomes odd, so can't let it go through original code flow
            } else {
                n *= -1;
            }
        }
        if (n == 0) {
            return 1;
        }
        if (x == 1) {
            return x;
        }
        if (n % 2 == 0) {
            return myPow(x * x, n / 2);
        } else {
            return x * myPow(x * x, (n - 1) / 2);
        }
    }
}

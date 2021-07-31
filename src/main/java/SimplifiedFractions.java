import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * https://leetcode.com/problems/simplified-fractions/
 *
 * Given an integer n, return a list of all simplified fractions between 0 and 1 (exclusive) such that the denominator is less-than-or-equal-to n. The fractions can be in any order.
 *
 * Input: n = 2
 * Output: ["1/2"]
 * Explanation: "1/2" is the only unique fraction with a denominator less-than-or-equal-to 2.
 *
 * Input: n = 3
 * Output: ["1/2","1/3","2/3"]
 *
 * Input: n = 4
 * Output: ["1/2","1/3","1/4","2/3","3/4"]
 * Explanation: "2/4" is not a simplified fraction because it can be simplified to "1/2".
 *
 * Input: n = 1
 * Output: []
 *
 * Constraints:
 * 1 <= n <= 100
 * </pre>
 */
public class SimplifiedFractions {
    /**
     * Approach: Leverage GCD to find simple fractions ie. simple fractions exists when GCD of numerator and denominator is 1
     *
     * {@link gfg.codingChallenge.GCD1}
     */
    public List<String> simplifiedFractions(int n) {
        List<String> res = new ArrayList<>();
        for (int denominator = 2; denominator <= n; denominator++) {
            for (int numerator = 1; numerator < denominator; numerator++) {
                if (gcd(numerator, denominator) == 1) {
                    res.add(numerator + "/" + denominator);
                }
            }
        }
        return res;
    }

    /**
     * Refer https://www.khanacademy.org/computing/computer-science/cryptography/modarithmetic/a/the-euclidean-algorithm
     *
     * Recursive function is : GCD(a, b) = GCD(b, remainder)
     */
    private int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b); //euclidean algorithm
    }
}

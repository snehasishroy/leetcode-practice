/**
 * https://leetcode.com/problems/bulb-switcher/
 * <p>
 * There are n bulbs that are initially off. You first turn on all the bulbs, then you turn off every second bulb.
 * <p>
 * On the third round, you toggle every third bulb (turning on if it's off or turning off if it's on).
 * For the ith round, you toggle every i bulb. For the nth round, you only toggle the last bulb.
 * <p>
 * Return the number of bulbs that are on after n rounds.
 * <p>
 * Input: n = 3
 * Output: 1
 * Explanation: At first, the three bulbs are [off, off, off].
 * After the first round, the three bulbs are [on, on, on].
 * After the second round, the three bulbs are [on, off, on].
 * After the third round, the three bulbs are [on, off, off].
 * So you should return 1 because there is only one bulb is on.
 * <p>
 * Constraints:
 * 0 <= n <= 109
 */
public class BulbSwitcher {
    /**
     * Approach: Maths, Constraints indicates either a o(1) solution or o(logn) solution
     * Only the indices that are flipped odd number of times would still be on
     * The number which has odd number of divisors would be such indices
     * Such numbers are perfect squares e.g. 16 {1,2,4,8,16}
     * <p>
     * Primes have even no of divisors e.g. 7 {1,7}
     * Other non primes have divisors in pairs e.g 15 {1,3,5,15}
     * <p>
     * {@link MinimumNumberOfKConsecutiveBitFlips} {@link EggDropWith2EggsAndNFloors}
     */
    public int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }
}

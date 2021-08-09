import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * <pre>
 * https://leetcode.com/problems/remove-stones-to-minimize-the-total/
 *
 * You are given a 0-indexed integer array piles, where piles[i] represents the number of stones in the ith pile, and an integer k. You should apply the following operation exactly k times:
 *
 * Choose any piles[i] and remove floor(piles[i] / 2) stones from it.
 * Notice that you can apply the operation on the same pile more than once.
 *
 * Return the minimum possible total number of stones remaining after applying the k operations.
 *
 * Input: piles = [5,4,9], k = 2
 * Output: 12
 * Explanation: Steps of a possible scenario are:
 * - Apply the operation on pile 2. The resulting piles are [5,4,5].
 * - Apply the operation on pile 0. The resulting piles are [3,4,5].
 * The total number of stones in [3,4,5] is 12.
 *
 * Input: piles = [4,3,6,7], k = 3
 * Output: 12
 * Explanation: Steps of a possible scenario are:
 * - Apply the operation on pile 3. The resulting piles are [4,3,3,7].
 * - Apply the operation on pile 4. The resulting piles are [4,3,3,4].
 * - Apply the operation on pile 0. The resulting piles are [2,3,3,4].
 * The total number of stones in [2,3,3,4] is 12.
 *
 * Constraints:
 * 1 <= piles.length <= 10^5
 * 1 <= piles[i] <= 10^4
 * 1 <= k <= 10^5
 * </pre>
 */
public class RemoveStonesToMinimizeTheTotal {
    /**
     * Approach: Counting/Bucket sort, Instead of using a priority queue to find the largest element, use a counting sort algorithm to
     * keep track of the largest element.
     * Time Complexity: O(max) ~4ms
     */
    public int minStoneSumOptimized(int[] piles, int k) {
        int[] freq = new int[10001];
        int max = 0, totalStones = 0;
        for (int pile : piles) {
            freq[pile]++;
            max = Math.max(pile, max);
            totalStones += pile;
        }
        for (int i = max; i >= 1 && k > 0; i--) { //go down from highest to lowest
            int multiples = freq[i];
            if (multiples == 0) {
                continue;
            }
            int allowed = Math.min(k, multiples); //how many are we allowed to pick
            int remainingStones = (i + 1) / 2; //Math.ceil(i/2)
            freq[remainingStones] += allowed; //increase the frequency of the stones remaining
            totalStones -= (i - remainingStones) * allowed; //update the sum
            k -= allowed;
        }
        return totalStones;
    }

    /**
     * Approach: Greedy, Remove the maximum number of stones possible at each operation in order to minimize the no of stones remaining
     * TimeComplexity: O(klogn) ~400ms
     *
     * {@link EliminateMaximumNumberOfMonsters}
     */
    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        int totalStones = 0;
        for (int pile : piles) {
            totalStones += pile;
            pq.add(pile);
        }
        while (k > 0 && !pq.isEmpty()) {
            int highest = pq.remove();
            int remaining = highest - (highest / 2); //alternatively use (highest + 1)/2
            totalStones -= (highest - remaining); //update the sum by remove the no of stones popped
            pq.add(remaining);
            k--;
        }
        return totalStones;
    }
}

import java.util.Arrays;

/**
 * https://leetcode.com/problems/russian-doll-envelopes/
 * <p>
 * You have a number of envelopes with widths and heights given as a pair of integers (w, h). One envelope can fit into another if and only if
 * both the width and height of one envelope is greater than the width and height of the other envelope.
 * <p>
 * What is the maximum number of envelopes can you Russian doll? (put one inside other)
 * <p>
 * Note:
 * Rotation is not allowed.
 * <p>
 * Input: [[5,4],[6,4],[6,7],[2,3]]
 * Output: 3
 * Explanation: The maximum number of envelopes you can Russian doll is 3 ([2,3] => [5,4] => [6,7]).
 * <p>
 * Constraints:
 * 1 <= envelopes.length <= 5000
 * envelopes[i].length == 2
 * 1 <= wi, hi <= 10^4
 */
public class RussianDollEnvelopes {
    /**
     * Approach: Extension of finding LIS in 2D array
     * Here both width and height of previous element should be < width and height of current element
     *
     * {@link TheNumberOfWeakCharactersInTheGame} {@link DistinctSubsequences2} {@link BestTeamWithNoConflicts} {@link IncreasingSubsequences}
     * {{@link LargestDivisibleSubset} {@link MaximumLengthOfPairChain} {@link MinimumNumberOfRemovalsToMakeMountainArray}
     */
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes.length <= 1) {
            return envelopes.length;
        }
        //increasing sort because problem statement states we can pick any set of envelopes, rather than picking only subsequences
        Arrays.sort(envelopes, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                return Integer.compare(o1[1], o2[1]);
            } else {
                return Integer.compare(o1[0], o2[0]);
            }
        });
        int n = envelopes.length;
        int[] result = new int[n];
        Arrays.fill(result, 1);
        int maxCount = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (envelopes[j][0] < envelopes[i][0] && envelopes[j][1] < envelopes[i][1]) {
                    //check if previous element can fit into current element, if yes, utilize the result already computed for smaller element
                    result[i] = Math.max(result[i], result[j] + 1);
                }
            }
            maxCount = Math.max(maxCount, result[i]);
        }
        return maxCount;
    }
}

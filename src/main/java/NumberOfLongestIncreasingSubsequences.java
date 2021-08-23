import java.util.Arrays;

/**
 * https://leetcode.com/problems/number-of-longest-increasing-subsequence/
 * <p>
 * Given an integer array nums, return the number of longest increasing subsequences.
 * <p>
 * Input: nums = [1,3,5,4,7]
 * Output: 2
 * Explanation: The two longest increasing subsequences are [1, 3, 4, 7] and [1, 3, 5, 7].
 * <p>
 * Input: nums = [2,2,2,2,2]
 * Output: 5
 * Explanation: The length of longest continuous increasing subsequence is 1, and there are 5 subsequences' length is 1, so output 5.
 */
public class NumberOfLongestIncreasingSubsequences {
    /**
     * Approach: Variant of longest increasing subsequence, apart from storing the length of the lis ending at index i, need to store the count of
     * subsequences which has length = lis ending at index i
     * <p>
     * Initially I stored all count of lis ending at any index, ie. a 2D DP for any index i, what is the count of lis of length 1, 2, 3 .. n
     * So if any a[i] < a[j], we can carry forward the count of let's say length 2 of i to length 3 of j
     * But it timed out
     * <p>
     * Then I thought why we need to store all the length, we are interested only in the longest increasing subsequence length, so why not store only that.
     * <p>
     * Took me some time to implement it but happy to implement it on my own.
     *
     * {@link NumberOfWaysToArriveAtADestination} similar problem but on graphs
     */
    public int findNumberOfLIS(int[] nums) {
        int[] lis = new int[nums.length]; //max length of lis ending at index i
        int[] count = new int[nums.length]; //no of subsequences with length = lis[i]
        Arrays.fill(lis, 1);
        Arrays.fill(count, 1);
        int longestLIS = 1;
        for (int i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    int curLength = lis[j] + 1;
                    if (curLength > lis[i]) { //if a longer increasing subsequence found, update the lis ending at i and reset the count as well
                        //if there were 3 subsequences with length 5, now we can have 3 subsequences with length 6
                        longestLIS = Math.max(longestLIS, curLength);
                        lis[i] = curLength;
                        count[i] = count[j];
                    } else if (curLength == lis[i]) {
                        //that's the most trickiest part, in case a lis with length similar to the current lis length is found, increment the count
                        count[i] += count[j];
                    }
                }
            }
        }
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            if (lis[i] == longestLIS) {
                result += count[i];
            }
        }
        return result;
    }
}

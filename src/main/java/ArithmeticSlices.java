/**
 * <pre>
 * https://leetcode.com/problems/arithmetic-slices/
 *
 * An integer array is called arithmetic if it consists of at least three elements and if the difference between any two consecutive elements is the same.
 *
 * For example, [1,3,5,7,9], [7,7,7,7], and [3,-1,-5,-9] are arithmetic sequences.
 * Given an integer array nums, return the number of arithmetic subarrays of nums.
 *
 * Input: nums = [1,2,3,4]
 * Output: 3
 * Explanation: We have 3 arithmetic slices in nums: [1, 2, 3], [2, 3, 4] and [1,2,3,4] itself.
 *
 * Input: nums = [1]
 * Output: 0
 *
 * Constraints:
 * 1 <= nums.length <= 5000
 * -1000 <= nums[i] <= 1000
 * </pre>
 */
public class ArithmeticSlices {
    /**
     * Approach: Maths on subarrays, need to count the length of the longest subarray containing all valid arithmetic progressions
     * i.e. for [1,2,3,4,8,12,16], we first need to find the first biggest slice of [1,2,3,4], then [4,8,12,16]
     * for a slice, total subarrays are n*(n+1)/2, but we need to exclude subarrays of length 1 and 2, so the total subarrays will be (n-2)*(n-1)/2
     *
     * So the task is reduced to finding the length of the longest slice and applying the formula to find the total no of slices
     * {@link DistinctSubsequences2}
     */
    public int numberOfArithmeticSlices(int[] A) {
        int n = A.length;
        if (n <= 2) {
            return 0;
        }
        int prevDiff = A[1] - A[0];
        int index = 2;
        int res = 0;
        while (index < n) {
            int temp = index;
            while (index < n && A[index] - A[index - 1] == prevDiff) {
                index++;
            }
            int length = index - temp + 2; //+2 because we are already starting 2 positions ahead of the start of the slice
            if (length >= 3) {
                res += ((length - 2) * (length - 1)) / 2;
            }
            if (index < n) {
                //reset the diff to be the diff of the next slice
                prevDiff = A[index] - A[index - 1];
            }
            index++;
        }
        return res;
    }
}

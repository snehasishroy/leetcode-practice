import java.util.Arrays;

/**
 * <pre>
 * https://leetcode.com/problems/valid-triangle-number/
 *
 * Given an integer array nums, return the number of triplets chosen from the array that can make triangles if we take them as side lengths of a triangle.
 *
 * Input: nums = [2,2,3,4]
 * Output: 3
 * Explanation: Valid combinations are:
 * 2,3,4 (using the first 2)
 * 2,3,4 (using the second 2)
 * 2,2,3
 *
 * Input: nums = [4,2,3,4]
 * Output: 4
 *
 * Constraints:
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 1000
 * </pre>
 */
public class ValidTriangleNumber {
    /**
     * Approach: Very tricky Two pointers, in a triangle sum of any two sides should be greater than the third side
     * ie. a+b>c, a+c>b, b+c>a
     * Now considering a<b<c, 2nd and 3rd condition automatically gets satisfied, only thing remains is to validate a+b>c or not.
     * This condition is very similar to {@link ThreeSumSmaller} where we find no of triplets such that a+b<c.
     * So this problem can be rephrased as ThreeSumGreater.
     *
     * There are a couple of two pointers solutions to solve this.
     * 1. Fix a (i=i) and b (j=i+1), find the largest c (k=i+2) that satisfies the condition. All the value in between j and k, can be part of the result set.
     * Now increment j and restart the search from k, that failed the check in previous loop.
     * This part is critical in ensuring the time complexity is O(n^2) as we don't reset the k back to j+1.
     *
     * 2. Alternative is to fix c (k=n-1) and start searching for a (i=0) and b (j=k-1) that satisfies the constraint.
     * If found, all the values between i and j, can be part of the result set. Decrement j and search again.
     * If a + b < c, then increment i. This approach is exactly reverse of {@link ThreeSumSmaller} and much simpler then previous approach.
     *
     * Was not able to reduce the time complexity to O(n^2) on my own.
     *
     * {@link CountSquareSumTriples}
     */
    public int triangleNumberOptimized(int[] nums) {
        Arrays.sort(nums);
        int res = 0, n = nums.length;
        for (int c = n - 1; c >= 0; c--) {
            int a = 0, b = c - 1;
            while (a < b) {
                if (nums[a] + nums[b] > nums[c]) {
                    //if we fix b and c, all numbers between a and b index can be paired to give a valid triplet.
                    res += (b - a);
                    //now this b is done, reduce b
                    b--;
                } else {
                    //sum is <= target, need to increase the current sum, increment a
                    a++;
                }
            }
        }
        return res;
    }

    /**
     * Approach: Binary Search, Need to find triplets such that a+b>c, a>=b>=c, so sort the array, fix a and b, find the largest index i with value < (a+b)
     * Now we can pick any number between b's index and i to be paired with a and b
     * TimeComplexity: O(n^2 logn)
     */
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int candidate = nums[i] + nums[j];
                int lowerIndex = lowerKey(nums, candidate, j + 1);
                if (lowerIndex > j) {
                    res += (lowerIndex - j);
                }
            }
        }
        return res;
    }

    //return the largest index with value < target
    private int lowerKey(int[] nums, int target, int start) {
        int low = start, high = nums.length - 1, ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] >= target) {
                high = mid - 1;
            } else {
                ans = mid;
                low = mid + 1;
            }
        }
        return ans;
    }
}

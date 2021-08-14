/**
 * https://leetcode.com/problems/find-peak-element/
 * <p>
 * A peak element is an element that is greater than its neighbors.
 * <p>
 * Given an input array nums, where nums[i] ≠ nums[i+1], find a peak element and return its index.
 * <p>
 * The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.
 * <p>
 * You may imagine that nums[-1] = nums[n] = -∞.
 * <p>
 * Input: nums = [1,2,3,1]
 * Output: 2
 * Explanation: 3 is a peak element and your function should return the index number 2.
 * <p>
 * Input: nums = [1,2,1,3,5,6,4]
 * Output: 1 or 5
 * Explanation: Your function can return either index number 1 where the peak element is 2,
 * or index number 5 where the peak element is 6.
 */
public class FindPeakElement {
    /**
     * Approach: Followed errichto binary search template of reducing the problem to prefix/suffix of T and F
     * <p>
     * Always consider cases like 0,1,2,3 or 3,2,1,0 while thinking of edge cases
     * <p>
     * Find a peak in 2D array is trickier and uses divide and conquer https://www.geeksforgeeks.org/find-peak-element-2d-array/
     * {@link SearchInRotatedSortedArray} {@link FindMinimumInRotatedSortedArray2} {@link FindKClosestElements}
     * {@link FindInMountainArray} {@link PeakIndexInMountainArray}
     */
    public int findPeakElement(int[] nums) {
        int low = 0, high = nums.length - 1, ans = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid == 0 || nums[mid - 1] < nums[mid]) { //special case for 0th index -- we are on an increasing path and since the array contains peak, if we go right we will find one
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }
}

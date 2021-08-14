/**
 * <pre>
 * https://leetcode.com/problems/peak-index-in-a-mountain-array/
 *
 * Let's call an array arr a mountain if the following properties hold:
 *
 * arr.length >= 3
 * There exists some i with 0 < i < arr.length - 1 such that:
 * arr[0] < arr[1] < ... arr[i-1] < arr[i]
 * arr[i] > arr[i+1] > ... > arr[arr.length - 1]
 * Given an integer array arr that is guaranteed to be a mountain, return any i such that arr[0] < arr[1] < ... arr[i - 1] < arr[i] > arr[i + 1] > ... > arr[arr.length - 1].
 *
 * Input: arr = [0,1,0]
 * Output: 1
 *
 * Input: arr = [0,2,1,0]
 * Output: 1
 *
 * Input: arr = [0,10,5,2]
 * Output: 1
 *
 * Input: arr = [3,4,5,1]
 * Output: 2
 *
 * Input: arr = [24,69,100,99,79,78,67,36,26,19]
 * Output: 2
 *
 * Constraints:
 * 3 <= arr.length <= 10^4
 * 0 <= arr[i] <= 10^6
 * arr is guaranteed to be a mountain array.
 *
 * Follow up: Finding the O(n) is straightforward, could you find an O(log(n)) solution?
 * </pre>
 */
public class PeakIndexInMountainArray {
    /**
     * Approach: Binary Search
     *
     * {@link FindInMountainArray} {@link SuperEggDrop} {@link FindPeakElement}
     */
    public int peakIndexInMountainArray(int[] arr) {
        int low = 0, high = arr.length - 1, ans = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (mid == 0 || arr[mid] > arr[mid - 1]) { //could have
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }
}

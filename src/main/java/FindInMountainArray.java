/**
 * https://leetcode.com/problems/find-in-mountain-array/
 * <p>
 * You may recall that an array A is a mountain array if and only if:
 * <p>
 * A.length >= 3
 * There exists some i with 0 < i < A.length - 1 such that:
 * A[0] < A[1] < ... A[i-1] < A[i]
 * A[i] > A[i+1] > ... > A[A.length - 1]
 * Given a mountain array mountainArr, return the minimum index such that mountainArr.get(index) == target.  If such an index doesn't exist, return -1.
 * <p>
 * You can't access the mountain array directly.  You may only access the array using a MountainArray interface:
 * <p>
 * MountainArray.get(k) returns the element of the array at index k (0-indexed).
 * MountainArray.length() returns the length of the array.
 * <p>
 * Input: array = [1,2,3,4,5,3,1], target = 3
 * Output: 2
 * Explanation: 3 exists in the array, at index=2 and index=5. Return the minimum index, which is 2.
 * <p>
 * Input: array = [0,1,2,4,2,1], target = 3
 * Output: -1
 * Explanation: 3 does not exist in the array, so we return -1.
 */
public class FindInMountainArray {
    /**
     * Approach: 3 Binary Searches, first binary search to find the peak element
     * Then apply binary search in either of the increasing partition or decreasing partition
     * Take care of the indices using binary search
     * <p>
     * {@link FindPeakElement} {@link FindMinimumInRotatedSortedArray2} {@link SuperEggDrop}
     */
    public int findInMountainArray(int target, MountainArray arr) {
        int low = 0, high = arr.length() - 1, res = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            //alternatively we could have compared mid with mid-1 and mid+1 but comparing with mid+1 would be redundant as comparing with the adjacent left element would suffice
            if (mid == 0 || arr.get(mid) > arr.get(mid - 1)) { //if (mid == 0 || mid == arr.length() - 1 || (arr.get(mid) > arr.get(mid - 1) && arr.get(mid) < arr.get(mid + 1))) {
                res = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        low = 0;
        high = res;
        //increasing portion of the mountain
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int candidate = arr.get(mid);
            if (candidate == target) {
                return mid;
            } else if (candidate < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        low = res + 1;
        high = arr.length() - 1;
        //decreasing portion of the mountain
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int candidate = arr.get(mid);
            if (candidate == target) {
                return mid;
            } else if (candidate < target) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return -1;
    }

    private interface MountainArray {
        int get(int index);

        int length();
    }
}

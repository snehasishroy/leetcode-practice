/**
 * <pre>
 * https://leetcode.com/problems/range-addition/ Premium
 *
 * You are given an integer length and an array updates where updates[i] = [startIdxi, endIdxi, inci].
 *
 * You have an array arr of length length with all zeros, and you have some operation to apply on arr.
 * In the ith operation, you should increment all the elements arr[startIdxi], arr[startIdxi + 1], ..., arr[endIdxi] by inci.
 *
 * Return arr after applying all the updates.
 *
 * Input: length = 5, updates = [[1,3,2],[2,4,3],[0,2,-2]]
 * Output: [-2,0,3,5,3]
 *
 * Input: length = 10, updates = [[2,4,6],[5,6,8],[1,9,-4]]
 * Output: [0,-4,2,2,2,4,4,-4,-4,-4]
 *
 * Constraints:
 * 1 <= length <= 10^5
 * 0 <= updates.length <= 10^4
 * 0 <= startIdxi <= endIdxi < length
 * -1000 <= inci <= 1000
 * </pre>
 */
public class RangeAddition {
    /**
     * Approach: Prefix Sum, Instead of incrementing all the values within a range, just increment the start index with inc/decrement value
     * and the (end + 1) index with negative of inc/decrement value.
     * <p>
     * When asked to print the modified array, just do a prefix sum
     * <p>
     * {@link experience.directi.DSA.txt} related problem
     */
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] arr = new int[length];
        for (int[] update : updates) {
            int startIndex = update[0];
            int endIndex = update[1];
            int inc = update[2];
            arr[startIndex] += inc;
            if (endIndex + 1 < length) {
                arr[endIndex + 1] -= inc;
            }
        }
        int prefixSum = 0;
        for (int i = 0; i < length; i++) {
            prefixSum += arr[i];
            arr[i] = prefixSum;
        }
        return arr;
    }
}

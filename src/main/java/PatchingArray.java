/**
 * <pre>
 * https://leetcode.com/problems/patching-array/
 *
 * Given a sorted integer array nums and an integer n, add/patch elements to the array such that any number in the range [1, n] inclusive can be formed by the sum of some elements in the array.
 *
 * Return the minimum number of patches required.
 *
 * Input: nums = [1,3], n = 6
 * Output: 1
 * Explanation:
 * Combinations of nums are [1], [3], [1,3], which form possible sums of: 1, 3, 4.
 * Now if we add/patch 2 to nums, the combinations are: [1], [2], [3], [1,3], [2,3], [1,2,3].
 * Possible sums are 1, 2, 3, 4, 5, 6, which now covers the range [1, 6].
 * So we only need 1 patch.
 *
 * Input: nums = [1,5,10], n = 20
 * Output: 2
 * Explanation: The two patches can be [2, 4].
 *
 * Input: nums = [1,2,2], n = 5
 * Output: 0
 *
 * Constraints:
 * 1 <= nums.length <= 1000
 * 1 <= nums[i] <= 10^4
 * nums is sorted in ascending order.
 * 1 <= n <= 2^31 - 1
 * </pre>
 */
public class PatchingArray {
    /**
     * Approach: Tricky Greedy. Critical observation is to understand that given a range which covers all the interval between [1,k]
     * if we see the next number to be >k+1, it will result in a disjoint covered interval
     * e.g. given [1,2,3,10], upon seeing 1, we can cover [1,1]
     * upon seeing 2, we can cover [1,3]
     * upon seeing 3, we can cover [1,6]
     * upon seeing 10, we can cover [1,6], [10,16]
     * but the interval between [7,9] is uncovered, in order to cover it we will need to add/patch some numbers. If we patch 8 or 9, we won't be able to
     * cover 7, so the smallest number required is 7.
     * Upon patching 7, we can cover [1,13] and then when we encounter 10, we can cover [1,23]
     *
     * So the idea is to keep track of the sum of the highest subset sum possible. If a number is > (highestSubsetSum + 1), it indicates a disjoint covered interval.
     * So we have to patch in a number which should be (highestSubsetSum + 1) because no other number can cover all the gaps.
     * Upon patching it, the highestSubsetSum should be updated as well.
     *
     * Take care to break out of the loop as soon as the target is reached.
     *
     * Very happy to solve this hard problem on my own :) It's very important to understand the root of an algorithm to solve its derivative problems.
     *
     * https://www.youtube.com/watch?v=1h2eFrNFSSw
     * https://stackoverflow.com/a/21078133/1206152
     *
     * {@link alternate.SmallestImpossibleSubsetSum} {@link MaximumNumberOfConsecutiveValuesYouCanMake}
     */
    public int minPatches(int[] nums, int target) {
        int n = nums.length;
        long highestSubsetSum = 0;
        int patched = 0;
        for (int i = 0; i < n; i++) { //nums is already sorted
            if (nums[i] > highestSubsetSum + 1) { //disjoint interval found, need to patch in number
                while (nums[i] > highestSubsetSum + 1 && highestSubsetSum < target) { //stop patching as soon as the target is reached or gap is covered
                    highestSubsetSum += (highestSubsetSum + 1);
                    patched++;
                }
            }
            highestSubsetSum += nums[i]; //update the range covered by including the current number
            if (highestSubsetSum >= target) {
                break;
            }
        }
        //even after processing all the numbers in array, target might not have been reached e.g. [1] target=100
        //this loop handles it, it's exactly similar to the loop above.
        while (highestSubsetSum < target) {
            highestSubsetSum += (highestSubsetSum + 1);
            patched++;
        }
        return patched;
    }
}

import java.util.Arrays;

/**
 * https://leetcode.com/problems/3sum-smaller/
 * <p>
 * Given an array of n integers nums and an integer target, find the number of index triplets i, j, k with 0 <= i < j < k < n
 * that satisfy the condition nums[i] + nums[j] + nums[k] < target.
 * <p>
 * Could you solve it in O(n2) runtime?
 * <p>
 * Input: nums = [-2,0,1,3], target = 2
 * Output: 2
 * Explanation: Because there are two triplets which sums are less than 2:
 * [-2,0,1]
 * [-2,0,3]
 */
public class ThreeSumSmaller {
    /**
     * Approach: If the problem was to find tuples with sum < target, we can leverage binary search as well as two pointers to find such tuples
     * [1,2,3,5,8] target = 7
     * 1 + 8 = 9 > 7, reduce high
     * 1 + 5 = 6 < 7, this means that we have found 3 valid tuples (1,2), (1,3), (1,5), increment low
     * 2 + 5 = 7, reduce high
     * 2 + 3 = 5 < 7, one valid tuple (2, 3) increment low
     * terminate
     * <p>
     * Repeat this process by iteratively fixing each number and reducing the target sum accordingly
     * <p>
     * Was unable to think about incrementing the low after finding a valid target, to continue finding more tuples
     * TimeComplexity: n^2
     *
     * {@link ValidTriangleNumber} for ThreeSumGreater problem
     */
    public int threeSumSmallerOptimized(int[] nums, int target) {
        Arrays.sort(nums);
        int result = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            int targetSum = target - nums[i];
            int low = i + 1, high = nums.length - 1;
            while (low < high) {
                int candidateSum = nums[low] + nums[high];
                if (candidateSum < targetSum) {
                    result += (high - low);
                    low++;
                } else {
                    high--;
                }
            }
        }
        return result;
    }

    /**
     * Approach: Iteratively fix one number and then the problem reduces to finding two elements whose sum is < target sum
     * The subproblem could again be solved by fixing another element and finding the index of largest number < target
     * Could leverage binary search to find the largest number smaller than target value
     * TimeComplexity: n^2 log(n)
     * <p>
     * Took me a lot of time to think about this and then got WA due to the behaviour of Arrays.binarySearch()
     * Arrays.binarySearch() returns the index of number >= target but we are interested in number < target
     * It could have worked if there were no duplicates, then (idx - 1) would have been our candidate but if input array contains duplicate
     * we would have to loop backwards to verify and find the smaller candidate
     * <p>
     * It's better to write custom binary search than dealing with all these edge cases
     * {@link ThreeSum} {@link ThreeSumClosest} related problem
     */
    public int threeSumSmaller(int[] nums, int target) {
        if (nums.length < 3) {
            return 0;
        }
        Arrays.sort(nums);
        int result = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                int curSum = nums[i] + nums[j];
                int maxNum = target - curSum;
                int idx = binarySearch(nums, maxNum, j + 1);
                if (idx != -1) { //update result only if idx was found
                    result += (idx - j);
                }
            }
        }
        return result;
    }

    /**
     * Returns index of largest number smaller than maxVal
     * Follows erichto binary search template
     * T T T T F F F F
     * Need to find the last T
     */
    private int binarySearch(int[] nums, int maxVal, int start) {
        int low = start, high = nums.length - 1, res = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (nums[mid] < maxVal) { //T found, mark it and look for a higher T
                res = mid;
                low = mid + 1;
            } else { //F found, look for T in left partition
                high = mid - 1;
            }
        }
        return res;
    }
}

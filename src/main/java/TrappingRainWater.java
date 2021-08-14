/**
 * https://leetcode.com/problems/trapping-rain-water/
 * <p>
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
 * <p>
 * The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped.
 * <p>
 * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 */
public class TrappingRainWater {
    /**
     * Approach: Keep track of the max from the left and right in an array, Gives linear time complexity
     * <p>
     * Can also be done using stack solution similar to {@link LargestRectangleInHistogram}
     *
     * {@link FlipStringToMonotoneIncreasing}
     */
    public int trapOptimized(int[] height) {
        int n = height.length;
        if (n <= 1) {
            return 0;
        }
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        leftMax[0] = height[0];
        rightMax[n - 1] = height[n - 1];
        for (int i = 1; i < n; i++) {
            if (height[i] > leftMax[i - 1]) {
                leftMax[i] = height[i];
            } else {
                leftMax[i] = leftMax[i - 1];
            }
        }
        for (int i = n - 2; i >= 0; i--) {
            if (height[i] > rightMax[i + 1]) {
                rightMax[i] = height[i];
            } else {
                rightMax[i] = rightMax[i + 1];
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += (Math.min(leftMax[i], rightMax[i]) - height[i]);
        }
        return ans;
    }

    /**
     * Approach: Brute force, need to find the contribution of each bar and sum it up for the result
     * Each bar can contribute only till the minimum of the largest leftmost and rightmost bar.
     */
    public int trap(int[] height) {
        if (height.length <= 1) {
            return 0;
        }
        int ans = 0;
        for (int i = 1; i < height.length - 1; i++) {
            int left = Integer.MIN_VALUE;
            for (int j = 0; j < i; j++) {
                left = Math.max(left, height[j]);
            }
            int right = Integer.MIN_VALUE;
            for (int j = i + 1; j < height.length; j++) {
                right = Math.max(right, height[j]);
            }
            if (Math.min(left, right) > height[i]) {
                ans += Math.min(left, right) - height[i];
            }
        }
        return ans;
    }
}

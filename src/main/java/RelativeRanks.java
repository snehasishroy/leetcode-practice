import java.util.Arrays;

/**
 * https://leetcode.com/problems/relative-ranks/
 * <p>
 * Given scores of N athletes, find their relative ranks and the people with the top three highest scores,
 * who will be awarded medals: "Gold Medal", "Silver Medal" and "Bronze Medal".
 * <p>
 * Input: [5, 4, 3, 2, 1]
 * Output: ["Gold Medal", "Silver Medal", "Bronze Medal", "4", "5"]
 * Explanation: The first three athletes got the top three highest scores, so they got "Gold Medal", "Silver Medal" and "Bronze Medal".
 * For the left two athletes, you just need to output their relative ranks according to their scores.
 * <p>
 * Note:
 * N is a positive integer and won't exceed 10,000.
 * All the scores of athletes are guaranteed to be unique.
 */
public class RelativeRanks {
    /**
     * Approach: Use sorting to find relative ranks of each index
     *
     * Can use bucket sort to optimize
     * {@link CustomSortString}
     */
    public String[] findRelativeRanks(int[] nums) {
        int n = nums.length;
        Integer[] arr = new Integer[n]; //Integer[] instead of int[] because custom comparator can be used only on Object not primitives
        for (int i = 0; i < n; i++) { //I could have used pair but this seemed way cooler
            arr[i] = i;
        }
        Arrays.sort(arr, (o1, o2) -> Integer.compare(nums[o2], nums[o1])); //this is a bit tricky, using the indices as a key in array to sort in descending order
        String[] res = new String[n];
        for (int i = 0; i < n; i++) {
            int idx = arr[i]; //find the index whose rank is i
            if (i == 0) {
                res[idx] = "Gold Medal"; //update the rank at that index
            } else if (i == 1) {
                res[idx] = "Silver Medal";
            } else if (i == 2) {
                res[idx] = "Bronze Medal";
            } else {
                res[idx] = Integer.toString(i + 1);
            }
        }
        return res;
    }
}

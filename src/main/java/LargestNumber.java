import java.util.Arrays;

/**
 * https://leetcode.com/problems/largest-number/
 * <p>
 * Given a list of non negative integers, arrange them such that they form the largest number.
 * <p>
 * Input: [10,2]
 * Output: "210"
 * <p>
 * Input: [3,30,34,5,9]
 * Output: "9534330"
 * <p>
 * Constraints:
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 109
 */
public class LargestNumber {
    /**
     * Approach: To make the largest number, we can't simply sort the input in descending order and concatenate i.e. {34,30,9,5,3}
     * As ericto mentioned, consider the state of problem at ith index, if we are given the largest possible number before ith index, how can we extend the result?
     * <p>
     * I initially thought that we should concatenate the ith string to the largest number so far at the end or at the beginning and compare them
     * but this won't give correct results for all the cases.
     * <p>
     * The correct way would be to insert the ith string at all the possible places, and compare the largest string found so far.
     * <p>
     * I then got lost while trying to think about the ways to cleanly implement the solution. Then I realized that we need not actually insert the ith string
     * and compare, we can just simulate the comparison by "bubble sorting" i.e during the comparison stage, don't compare the numbers numerically
     * compare them by creating strings and finding whether concatenating it at the beginning leads a greater result or concatenating it at the end.
     */
    public String largestNumber(int[] nums) {
        int n = nums.length;
        String[] input = new String[n];
        for (int i = 0; i < n; i++) {
            input[i] = Integer.toString(nums[i]);
        }
        for (int i = 1; i < n; i++) { //insertion sort, visualize it as sorting a deck of cards manually
            //insert the out of order card in place by shifting the cards
            String key = input[i];
            int j = i - 1;
            while (j >= 0 && (key + input[j]).compareTo(input[j] + key) > 0) {
                //this loop terminates when we see the first input[j] placed correctly, no need of looking further
                input[j + 1] = input[j];
                j--;
            }
            input[j + 1] = key;
        }
        StringBuilder res = new StringBuilder();
        for (String num : input) {
            res.append(num);
        }
        String candidate = res.toString();
        for (int i = 0; i < candidate.length(); i++) { //tricky, remove all the leading zeroes
            if (candidate.charAt(i) != '0') {
                return candidate.substring(i);
            }
        }
        return "0";
    }

    /**
     * Approach: Use a custom comparator function for sorting
     *
     * {@link CustomSortString}
     */
    public String largestNumberSimplified(int[] nums) {
        int n = nums.length;
        String[] input = new String[n];
        for (int i = 0; i < n; i++) {
            input[i] = Integer.toString(nums[i]);
        }
        Arrays.sort(input, (o1, o2) -> {
            String pre = o1 + o2;
            String post = o2 + o1;
            return -pre.compareTo(post); //notice the negative sign since we are interested in descending sort.
        });
        StringBuilder res = new StringBuilder();
        for (String num : input) {
            res.append(num);
        }
        String candidate = res.toString();
        for (int i = 0; i < candidate.length(); i++) { //tricky, remove all the leading zeroes
            if (candidate.charAt(i) != '0') {
                return candidate.substring(i);
            }
        }
        return "0";
    }
}
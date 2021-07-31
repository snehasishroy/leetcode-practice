import java.util.Arrays;

/**
 * https://leetcode.com/problems/form-largest-integer-with-digits-that-add-up-to-target/
 * <p>
 * Given an array of integers cost and an integer target. Return the maximum integer you can paint under the following rules:
 * <p>
 * The cost of painting a digit (i+1) is given by cost[i] (0 indexed).
 * The total cost used must be equal to target.
 * Integer does not have digits 0.
 * Since the answer may be too large, return it as string.
 * <p>
 * If there is no way to paint any integer given the condition, return "0".
 * <p>
 * Input: cost = [4,3,2,5,6,7,2,5,5], target = 9
 * Output: "7772"
 * Explanation:  The cost to paint the digit '7' is 2, and the digit '2' is 3. Then cost("7772") = 2*3+ 3*1 = 9. You could also paint "977", but "7772" is the largest number.
 */
public class FormLargestNumberWithDigitsThatSumUpToTarget {

    String res = "0";

    /**
     * Approach: {@link CombinationSum4} is similar problem
     * The crux to solving the problem is to understand the intermediate steps i.e if cost of using digit 2 is 5 and largest number possible with cost 8 is 982
     * how can i use that to build solution for cost 10? i can append 2 to 982 and get a possible candidate 2982
     */
    public String largestNumberTopDown(int[] cost, int target) {
        String[] memoization = new String[target + 1];
        return recur(cost, target, memoization); //recurse down from target to 0
    }

    private String recur(int[] cost, int target, String[] memoization) {
        if (target < 0) {
            return null;
        } else if (target == 0) {
            return "";
        }
        if (memoization[target] != null) {
            return memoization[target];
        }
        String res = "0";
        for (int i = 0; i < 9; i++) {
            String result = recur(cost, target - cost[i], memoization);
            if (result != null && !result.equals("0")) { //if a valid result is reached by picking i+1 digit, we got a possible candidate for current target
                String candidate = (i + 1) + result; //order of concatenation does not really matter
                //because consider target 3, it can be reached from 1 + 2 and 2 + 1
                //we will consider both of them and the max candidate will be stored
                if (candidate.length() == res.length() && candidate.compareTo(res) > 0) { //compare them via compareTo() only if the length of strings match
                    res = candidate;
                } else if (candidate.length() > res.length()) { //if candidate has more digits than current result
                    res = candidate;
                }
            }
        }
        return memoization[target] = res;
    }

    /**
     * Approach: Recursion + Top down memoization, similar to above solution but used {index, targetSum} as DP states
     * Runtime is similar but consumes a lot of memory
     *
     * After thinking a while, I realized that this again comes under DP State optimization, as the index does not really play a big part here.
     * Because an index can be used multiple times, and even if a target sum is achievable by multiple combinations of indices, if we choose the
     * largest digit first and if we memoize the string achieved, even if we get the same target sum again, it will be achieved by smaller numbers.
     * Only caveat is that after picking a digit, we need to restart the process back from the greatest digit ie. last index.
     *
     * Came up with this solution on my own during virtual contest.
     */
    public String largestNumberTopDownAlternate(int[] cost, int target) {
        String[][] dp = new String[cost.length][target + 1];
        for (String[] strings : dp) {
            Arrays.fill(strings, "0");
        }
        String res = recur(cost, cost.length - 1, target, dp); //iterate from the largest digit first
        return res == null ? "0" : res;
    }

    private String recur(int[] cost, int index, int target, String[][] dp) {
        if (target < 0) {
            return null;
        } else if (target == 0) {
            return "";
        } else if (index == -1) {
            return null;
        } else if (dp[index][target] == null || !dp[index][target].equals("0")) {
            return dp[index][target];
        }
        //notice we are not decrementing the index after picking as the same index can be picked multiple times
        String pick = recur(cost, index, target - cost[index], dp);
        String skip = recur(cost, index - 1, target, dp);
        if (pick != null) {
            pick = (index + 1) + pick;
        }
        if (pick != null && skip != null) {
            //find the largest number and return it
            if (pick.length() > skip.length()) {
                return dp[index][target] = pick;
            } else if (pick.length() < skip.length()) {
                return dp[index][target] = skip;
            } else {
                if (pick.compareTo(skip) > 0) {
                    return dp[index][target] = pick;
                } else {
                    return dp[index][target] = skip;
                }
            }
        } else {
            return dp[index][target] = (pick == null) ? skip : pick;
        }
    }

    /**
     * Approach: DP state optimization of previous approach
     */
    public String largestNumberStateOptimization(int[] cost, int target) {
        String[] dp = new String[target + 1];
        Arrays.fill(dp, "0");
        String res = recur(cost, cost.length - 1, target, dp);
        return res == null ? "0" : res;
    }

    private String recur(int[] cost, int index, int target, String[]dp) {
        if (target < 0) {
            return null;
        } else if (target == 0) {
            return "";
        } else if (index == -1) {
            return null;
        } else if (dp[target] == null || !dp[target].equals("0")) {
            return dp[target];
        }
        String pick = recur(cost, cost.length - 1, target - cost[index], dp); //restart the search from the largest digit i.e. last index
        String skip = recur(cost, index - 1, target, dp);
        if (pick != null) {
            pick = (index + 1) + pick;
        }
        if (pick != null && skip != null) {
            if (pick.length() > skip.length()) {
                return dp[target] = pick;
            } else if (pick.length() < skip.length()) {
                return dp[target] = skip;
            } else {
                if (pick.compareTo(skip) > 0) {
                    return dp[target] = pick;
                } else {
                    return dp[target] = skip;
                }
            }
        } else {
            return dp[target] = (pick == null) ? skip : pick;
        }
    }

    /**
     * Approach: My initial recursion solution, it can't be converted to top down DP because
     * 1. we are starting from 0
     * 2. we are not using the results of the recursion in a memoization friendly maanner
     */
    public String largestNumberRecursion(int[] cost, int target) {
        recur(cost, target, "", 0);
        return res;
    }

    private void recur(int[] cost, int target, String candidate, int currentCost) {
        if (currentCost > target) {
            return;
        }
        if (currentCost == target) {
            if (candidate.length() == res.length() && candidate.compareTo(res) > 0) {
                res = candidate;
            } else if (candidate.length() > res.length()) {
                res = candidate;
            }
        }
        for (int i = 0; i < 9; i++) {
            recur(cost, target, candidate + (i + 1), currentCost + cost[i]);
        }
    }
}

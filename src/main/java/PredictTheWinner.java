import java.util.Arrays;

/**
 * https://leetcode.com/problems/predict-the-winner/
 * <p>
 * Given an array of scores that are non-negative integers. Player 1 picks one of the numbers from either end of the array followed by the player 2
 * and then player 1 and so on. Each time a player picks a number, that number will not be available for the next player.
 * This continues until all the scores have been chosen. The player with the maximum score wins.
 * <p>
 * Given an array of scores, predict whether player 1 is the winner. You can assume each player plays to maximize his score.
 * <p>
 * Input: [1, 5, 2]
 * Output: False
 * Explanation: Initially, player 1 can choose between 1 and 2.
 * If he chooses 2 (or 1), then player 2 can choose from 1 (or 2) and 5. If player 2 chooses 5, then player 1 will be left with 1 (or 2).
 * So, final score of player 1 is 1 + 2 = 3, and player 2 is 5.
 * Hence, player 1 will never be the winner and you need to return False.
 * <p>
 * Input: [1, 5, 233, 7]
 * Output: True
 * Explanation: Player 1 first chooses 1. Then player 2 have to choose between 5 and 7. No matter which number player 2 choose, player 1 can choose 233.
 * Finally, player 1 has more score (234) than player 2 (12), so you need to return True representing player1 can win.
 */
public class PredictTheWinner {
    /**
     * Approach: Extension of the first version but instead of considering a recursion state for second player using findMax flag
     * Directly recur for the possible state of the board after player2 has made its choice
     * <p>
     * Reference MIT video: https://www.youtube.com/watch?v=Tw1k46ywN6E&feature=youtu.be&t=3650
     * https://leetcode.com/problems/predict-the-winner/discuss/96829/DP-O(n2)-+-MIT-OCW-solution-explanation/174870
     * <p>
     * This solution can be memoized but I am leaving it for now for brevity
     */
    public boolean PredictTheWinnerMIT(int[] nums) {
        if (nums.length <= 2) {
            return true;
        }
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }
        int firstPlayerMaxScore = recur(nums, 0, nums.length - 1);
        int secondPlayerMaxScore = totalSum - firstPlayerMaxScore;
        return firstPlayerMaxScore >= secondPlayerMaxScore;
    }

    private int recur(int[] nums, int left, int right) {
        if (left > right) {
            return 0;
        } else {
            //this is the crux of the solution, forget about handling recursion for second player
            // instead directly jump to the board available after the second player has made it's choices
            // there will be two options left for the first player.
            // but we know that second player plays optimally, so that state will yield the minimum score for player1
            // hence we need to take min, this reduced the code a lot because we directly jump ahead
            int pickLeft = nums[left] + Math.min(recur(nums, left + 2, right), recur(nums, left + 1, right - 1));
            int pickRight = nums[right] + Math.min(recur(nums, left + 1, right - 1), recur(nums, left, right - 2));
            return Math.max(pickLeft, pickRight);
        }
    }

    /**
     * Approach: Use MaxiMin algorithm, need to maximize the score of first player and minimize the score of next player
     * Each player can either pick from the left or pick from the right.
     * <p>
     * Get the max score achievable by the first player and see the score of the second player (total score - first player score)
     * Return true if first player score >= second player score
     * <p>
     * Reference video https://www.youtube.com/watch?v=CZdG83-Nt0s
     */
    public boolean PredictTheWinnerMaxiMin(int[] nums) {
        if (nums.length <= 2) {
            return true;
        } else {
            int totalSum = 0;
            for (int num : nums) {
                totalSum += num;
            }
            int[][] memoized = new int[nums.length][nums.length];
            for (int[] ints : memoized) {
                Arrays.fill(ints, -1);
            }
            int firstPlayerMaxScore = recur(nums, 0, nums.length - 1, true, memoized);
            int secondPlayerMaxScore = totalSum - firstPlayerMaxScore;
            return firstPlayerMaxScore >= secondPlayerMaxScore;
        }
    }

    //if findMax is true, player1 is playing, else player2 is playing
    //need to find max score for player1 when player1 is playing
    //when player2 is playing, player1 will get the min score if player2 plays optimally
    private int recur(int[] nums, int left, int right, boolean findMax, int[][] memoized) {
        if (left == right) {
            if (findMax) {
                return nums[left];
            } else {
                return 0;
            }
        } else if (memoized[left][right] != -1) { //IMPORTANT TO NOTE HERE THAT WE ARE NOT MEMOIZING THE findMax because
            //a combination of (left, right) is unique to each player, and it can't happen that two players have the same state
            //In StoneGame2 we have to memoize the boolean flag as well, because two players can share the same state, hence flag was
            //necessary to disambiguate
            return memoized[left][right];
        } else {
            if (findMax) {
                //first player can either pick from the left or from the right and get the remaining score from recursion
                int pickLeft = nums[left] + recur(nums, left + 1, right, false, memoized);
                int pickRight = nums[right] + recur(nums, left, right - 1, false, memoized);
                return memoized[left][right] = Math.max(pickLeft, pickRight);
            } else {
                //we are interested only in the score of the first player, so if you look closely, we have not added the nums[left] or nums[right]
                //to the recursion result, because if we had added it, then it would have become the second player score
                //which would have cascaded incorrect result to the parent
                //instead assume that the second player will make the correct choice and hence first player will be left with the minimum of the choices left
                //i.e. second player will either pick from the left, then first player will be left with (left + 1, right)
                //or from the right, then first player will be left with (left, right - 1)
                //since we know that second player will play optimally and will try to maximise his result, first player will get only the minimum result
                //hence we need to take min of the choices
                return memoized[left][right] = Math.min(recur(nums, left + 1, right, true, memoized), recur(nums, left, right - 1, true, memoized));
            }
        }
    }
}

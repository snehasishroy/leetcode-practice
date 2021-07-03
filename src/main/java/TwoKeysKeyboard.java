/**
 * https://leetcode.com/problems/2-keys-keyboard/
 * <p>
 * Initially on a notepad only one character 'A' is present. You can perform two operations on this notepad for each step:
 * <p>
 * Copy All: You can copy all the characters present on the notepad (partial copy is not allowed).
 * Paste: You can paste the characters which are copied last time.
 * <p>
 * Given a number n. You have to get exactly n 'A' on the notepad by performing the minimum number of steps permitted. Output the minimum number of steps to get n 'A'.
 * <p>
 * Input: 3
 * Output: 3
 * Explanation:
 * Initially, we have one character 'A'.
 * In step 1, we use Copy All operation.
 * In step 2, we use Paste operation to get 'AA'.
 * In step 3, we use Paste operation to get 'AAA'.
 * <p>
 * Note:
 * The n will be in the range [1, 1000].
 */
public class TwoKeysKeyboard {
    /**
     * Approach: Tricky DP, consider each index as the point in which we perform "copy all" and then use that to update all indices
     * that can be generated using a "paste" operation ie. if we have currently 10 words in the screen in x operations, if we copy and paste
     * we can get 20 words in x+2 operations, 30 words in x+3 operations...
     * <p>
     * Happy to solve this on my own.
     * <p>
     * {@link MinimumNumberOfRemovalsToMakeMountainArray} {@link BestTeamWithNoConflicts} {@link PerfectSquares} {@link MinDistanceToTypeUsingTwoFingers}
     * {@link gfg.codingChallenge.TypeIt}
     */
    public int minSteps(int n) {
        if (n == 1) {
            return 0;
        }
        int[] steps = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            steps[i] = i; //in the worst case, we would paste a single word everytime
        }
        for (int i = 2; i <= n; i++) {
            int curSteps = steps[i] + 1; //+1 for copy
            int paste = 1;
            //if we perform copy all at ith word, we can generate 2*i, 3*i, 4*i .... words
            for (int nextStep = i * 2; nextStep <= n; nextStep += i) {
                steps[nextStep] = Math.min(steps[nextStep], curSteps + paste);
                paste++; //increment the paste operation required
            }
        }
        return steps[n];
    }
}

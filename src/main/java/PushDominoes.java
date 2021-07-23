/**
 * <pre>
 * https://leetcode.com/problems/push-dominoes/
 *
 * There are n dominoes in a line, and we place each domino vertically upright. In the beginning, we simultaneously push some of the dominoes either to the left or to the right.
 *
 * After each second, each domino that is falling to the left pushes the adjacent domino on the left. Similarly, the dominoes falling to the right push their adjacent dominoes standing on the right.
 *
 * When a vertical domino has dominoes falling on it from both sides, it stays still due to the balance of the forces.
 *
 * For the purposes of this question, we will consider that a falling domino expends no additional force to a falling or already fallen domino.
 *
 * You are given a string dominoes representing the initial state where:
 * dominoes[i] = 'L', if the ith domino has been pushed to the left,
 * dominoes[i] = 'R', if the ith domino has been pushed to the right, and
 * dominoes[i] = '.', if the ith domino has not been pushed.
 * Return a string representing the final state.
 *
 * Input: dominoes = "RR.L"
 * Output: "RR.L"
 * Explanation: The first domino expends no additional force on the second domino.
 *
 * Input: dominoes = ".L.R...LR..L.."
 * Output: "LL.RR.LLRRLL.."
 *
 * Constraints:
 * n == dominoes.length
 * 1 <= n <= 10^5
 * dominoes[i] is either 'L', 'R', or '.'.
 * </pre>
 */
public class PushDominoes {
    /**
     * Approach: Tricky DP, Keep track of distance from nearest R (present on the left) and the nearest L (present on the right)
     * The one which is closest will decide the direction of the current domino. This is due to the fact that problem statement specifies
     * that a falling domino does not exert additional force to a falling domino ie. forces does not compound.
     *
     * Very happy to solve this on my own. Initially I was thinking of leveraging prefix sum but then realized that the forces do not compound.
     *
     * Alternative way to solve this problem would be to consider that there are only 4 possibilities that we have to solve for
     * L.....R (Do nothing)
     * R.....L (Two pointers to populate, handle even and odd indices)
     * R.....R (Fill all intermediate indices with R)
     * L.....L (Fill all intermediate indices with L)
     *
     * {@link ShortestDistanceToTargetColor} {@link ShortestDistanceToACharacter}
     */
    public String pushDominoes(String dominoes) {
        int n = dominoes.length();
        char[] res = dominoes.toCharArray();
        int[] nearestL = new int[n];
        int[] nearestR = new int[n];
        //popular distance from nearest R
        int prevR = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) { //use left to right pass to find nearest distance of 'R' from an index
            if (res[i] == 'R') {
                prevR = i;
            } else if (res[i] == '.') {
                nearestR[i] = prevR;
            } else {
                //if 'L' found, it will block the force of 'R', so we have to reset the last seen index of 'R'
                prevR = Integer.MAX_VALUE;
            }
        }
        int prevL = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) { //use right to left pass to find nearest distance of 'L' from an index
            if (res[i] == 'L') {
                prevL = i;
            } else if (res[i] == '.') {
                nearestL[i] = prevL;
            } else {
                //if 'R' found, reset the last seen index of 'L'
                prevL = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < n; i++) {
            if (res[i] == '.') {
                int leftDistance = Math.abs(nearestL[i] - i);
                int rightDistance = Math.abs(nearestR[i] - i);
                //if 'L' is near, current domino will fall in left direction and vice versa
                //nothing will happen, if both are at equal distance
                if (leftDistance < rightDistance) {
                    res[i] = 'L';
                } else if (leftDistance > rightDistance) {
                    res[i] = 'R';
                }
            }
        }
        return new String(res);
    }
}

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <pre>
 * https://leetcode.com/problems/shortest-distance-to-target-color/ Premium
 *
 * You are given an array colors, in which there are three colors: 1, 2 and 3.
 *
 * You are also given some queries. Each query consists of two integers i and c, return the shortest distance between the given index i and the target color c.
 * If there is no solution return -1.
 *
 * Input: colors = [1,1,2,1,3,2,2,3,3], queries = [[1,3],[2,2],[6,1]]
 * Output: [3,0,3]
 * Explanation:
 * The nearest 3 from index 1 is at index 4 (3 steps away).
 * The nearest 2 from index 2 is at index 2 itself (0 steps away).
 * The nearest 1 from index 6 is at index 3 (3 steps away).
 *
 * Input: colors = [1,2], queries = [[0,3]]
 * Output: [-1]
 * Explanation: There is no 3 in the array.
 *
 * Constraints:
 * 1 <= colors.length <= 5*10^4
 * 1 <= colors[i] <= 3
 * 1 <= queries.length <= 5*10^4
 * queries[i].length == 2
 * 0 <= queries[i][0] < colors.length
 * 1 <= queries[i][1] <= 3
 * </pre>
 */
public class ShortestDistanceToTargetColor {
    /**
     * Approach: DP, Solve it similar to non overlapping problems by running two passes in left and right direction.
     * Imagine solving the problem when you are asked to find shortest distance from only one color.
     * Once solved, create a 2D array to store left/right distance from multiple colors.
     * TimeComplexity: O(n) ~26ms
     *
     * Initially I solved using BinarySearch approach but after seeing runtime distribution graph, realized that my code is not that optimized.
     * Then I thought of this multi pass solution. Happy to think about this approach on my own.
     * <p>
     * {@link ShortestWordDistance} {@link ShortestDistanceToACharacter} {@link FindTwoNonOverlappingSubarrayWithTargetSum} {@link PushDominoes}
     */
    public List<Integer> shortestDistanceColor(int[] colors, int[][] queries) {
        int n = colors.length;
        var leftDistance = new int[n + 2][4]; //n+2 to avoid if else checks for boundary conditions of 0 and n-1, 4 to avoid reducing the color by 1
        var rightDistance = new int[n + 2][4];
        for (int[] left : leftDistance) {
            Arrays.fill(left, Integer.MAX_VALUE);
        }
        for (int[] right : rightDistance) {
            Arrays.fill(right, Integer.MAX_VALUE);
        }
        //left to right pass
        for (int i = 0; i < n; i++) {
            int curColor = colors[i];
            for (int j = 1; j <= 3; j++) {
                if (j == curColor) { //for the current color distance is 0
                    leftDistance[i + 1][j] = 0;
                } else {
                    //if the jth color was already discovered in the left, then the distance to jth color would be now +1
                    leftDistance[i + 1][j] = (leftDistance[i][j] == Integer.MAX_VALUE) ? Integer.MAX_VALUE : leftDistance[i][j] + 1; //carefully notice the indices
                }
            }
        }
        //right to left pass
        for (int i = n - 1; i >= 0; i--) {
            int curColor = colors[i];
            for (int j = 1; j <= 3; j++) {
                if (j == curColor) {
                    rightDistance[i + 1][j] = 0;
                } else {
                    rightDistance[i + 1][j] = (rightDistance[i + 2][j] == Integer.MAX_VALUE) ? Integer.MAX_VALUE : rightDistance[i + 2][j] + 1;
                }
            }
        }
        var res = new ArrayList<Integer>();
        for (int[] query : queries) {
            var left = leftDistance[query[0] + 1][query[1]];
            var right = rightDistance[query[0] + 1][query[1]];
            int min = Math.min(left, right);
            res.add(min == Integer.MAX_VALUE ? -1 : min); //compute the min distance from the color either in left or right direction
        }
        return res;
    }

    /**
     * Approach: Binary Search, store all the occurrence of a color in a list. For each query, find the first index >= target index in the list of indices.
     * TimeComplexity: q * logn ~67ms
     * This problem reduces to {@link FindKClosestElements} with k == 1
     */
    public List<Integer> shortestDistanceColorBinarySearch(int[] colors, int[][] queries) {
        var ones = new ArrayList<Integer>();
        var twos = new ArrayList<Integer>();
        var threes = new ArrayList<Integer>();
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == 1) {
                ones.add(i);
            } else if (colors[i] == 2) {
                twos.add(i);
            } else {
                threes.add(i);
            }
        }
        var res = new ArrayList<Integer>();
        for (int[] query : queries) {
            int targetIndex = query[0];
            int targetColor = query[1];
            if (targetColor == 1) {
                res.add(lowerBound(targetIndex, ones));
            } else if (targetColor == 2) {
                res.add(lowerBound(targetIndex, twos));
            } else {
                res.add(lowerBound(targetIndex, threes));
            }
        }
        return res;
    }

    private int lowerBound(int targetIndex, List<Integer> list) {
        if (list.isEmpty()) {
            return -1;
        }
        //alternatively Collections.binarySearch() could have been used as well. Do remember to convert its result back to a positive value if its < 0
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (list.get(mid) == targetIndex) {
                return 0; //we have to return the distance between the targetIndex and mid which is 0
            } else if (list.get(mid) < targetIndex) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        if (low == 0) { //if targetIndex is the smallest value
            return Math.abs(list.get(0) - targetIndex);
        } else if (low == list.size()) { //if targetIndex is the largest value
            return Math.abs(list.get(list.size() - 1) - targetIndex);
        } else if (low - 1 >= 0 && Math.abs(list.get(low - 1) - targetIndex) < Math.abs(list.get(low) - targetIndex)) { //check whether value at (low-1) is closer to target than value at low
            return Math.abs(list.get(low - 1) - targetIndex);
        } else {
            return Math.abs(list.get(low) - targetIndex); //value at low is closer
        }
    }
}

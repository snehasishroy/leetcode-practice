import java.util.ArrayList;
import java.util.Collections;

/**
 * https://leetcode.com/problems/best-meeting-point/
 * <p>
 * A group of two or more people wants to meet and minimize the total travel distance. You are given a 2D grid of values 0 or 1,
 * where each 1 marks the home of someone in the group. The distance is calculated using Manhattan Distance, where distance(p1, p2) = |p2.x - p1.x| + |p2.y - p1.y|.
 *
 * <pre>
 * 1 - 0 - 0 - 0 - 1
 * |   |   |   |   |
 * 0 - 0 - 0 - 0 - 0
 * |   |   |   |   |
 * 0 - 0 - 1 - 0 - 0
 * </pre>
 * Output: 6
 * <p>
 * Explanation: Given three people living at (0,0), (0,4), and (2,2):
 * The point (0,2) is an ideal meeting point, as the total travel distance
 * of 2+2+2=6 is minimal. So return 6.
 */
public class BestMeetingPoint {
    /**
     * Approach: Need to find the median of 2D locations of all persons as median minimizes the sum of absolute differences
     * <p>
     * Initially I tried to solve it similar to {@link ShortestDistanceFromAllBuildings} by doing BFS one by one from locations of
     * all people but it timed out
     * <p>
     * To find the median, quick select can be used whose average complexity is O(n) instead of sorting O(nlogn)
     * <p>
     * {@link ShortestDistanceFromAllBuildings} {@link ShortestBridge} {@link MinimumMovesToEqualArrayElements2} {@link RangeAddition2}
     */
    public int minTotalDistance(int[][] grid) {
        //separately calculate median of x and y by separately storing x and y indices
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> y = new ArrayList<>();
        int m = grid.length;
        int n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    x.add(i);
                    y.add(j);
                }
            }
        }
        Collections.sort(x); //Using quick select can optimize the run time by not relying on sort to find the median
        Collections.sort(y);
        int x_median = x.get(x.size() / 2);
        int y_median = y.get(y.size() / 2);
        int res = 0;
        for (int i = 0; i < x.size(); i++) {
            res += (Math.abs(x.get(i) - x_median)) + (Math.abs(y.get(i) - y_median));
        }
        return res;
    }
}

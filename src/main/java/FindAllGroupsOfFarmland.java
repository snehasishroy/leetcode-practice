import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * https://leetcode.com/problems/find-all-groups-of-farmland/
 *
 * You are given a 0-indexed m x n binary matrix land where a 0 represents a hectare of forested land and a 1 represents a hectare of farmland.
 *
 * To keep the land organized, there are designated rectangular areas of hectares that consist entirely of farmland. These rectangular areas are called groups.
 * No two groups are adjacent, meaning farmland in one group is not four-directionally adjacent to another farmland in a different group.
 *
 * land can be represented by a coordinate system where the top left corner of land is (0, 0) and the bottom right corner of land is (m-1, n-1).
 * Find the coordinates of the top left and bottom right corner of each group of farmland.
 * A group of farmland with a top left corner at (r1, c1) and a bottom right corner at (r2, c2) is represented by the 4-length array [r1, c1, r2, c2].
 *
 * Return a 2D array containing the 4-length arrays described above for each group of farmland in land.
 * If there are no groups of farmland, return an empty array. You may return the answer in any order.
 *
 * Input: land = [[1,0,0],[0,1,1],[0,1,1]]
 * Output: [[0,0,0,0],[1,1,2,2]]
 * Explanation:
 * The first group has a top left corner at land[0][0] and a bottom right corner at land[0][0].
 * The second group has a top left corner at land[1][1] and a bottom right corner at land[2][2].
 *
 * Input: land = [[1,1],[1,1]]
 * Output: [[0,0,1,1]]
 * Explanation:
 * The first group has a top left corner at land[0][0] and a bottom right corner at land[1][1].
 *
 * Input: land = [[0]]
 * Output: []
 * Explanation:
 * There are no groups of farmland.
 *
 * Constraints:
 * m == land.length
 * n == land[i].length
 * 1 <= m, n <= 300
 * land consists of only 0's and 1's.
 * Groups of farmland are rectangular in shape.
 * </pre>
 */
public class FindAllGroupsOfFarmland {
    /**
     * Approach: DFS, Two phase approach.
     * First mark all the cells that are part of the same farmland with a distinct id.
     * Then revisit the grid and keep track of first and last occurrence of a specific id
     *
     * {@link NumberOfIslands} {@link CountSubIslands}
     */
    public int[][] findFarmland(int[][] land) {
        int m = land.length, n = land[0].length;
        int id = 2;
        //first pass, mark all the cells that are part of the same group with the same id
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (land[i][j] == 1) {
                    mark(land, i, j, id++);
                }
            }
        }
        //second pass, keep track of first and last occurrence of a specific id
        Map<Integer, Pair<Integer, Integer>> first = new HashMap<>(); //id -> {x,y}
        Map<Integer, Pair<Integer, Integer>> last = new HashMap<>(); //id -> {x,y}
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (land[i][j] != 0) {
                    int curId = land[i][j];
                    if (!first.containsKey(curId)) { //update the first occurrence of an id only once
                        first.put(curId, new Pair<>(i, j));
                    }
                    last.put(curId, new Pair<>(i, j)); //keep overriding the last occurrence of an id
                }
            }
        }
        int[][] res = new int[first.size()][4]; //transform the output as per requirement, could have done this directly in the second pass
        int index = 0;
        for (Map.Entry<Integer, Pair<Integer, Integer>> entry : first.entrySet()) {
            id = entry.getKey();
            res[index][0] = entry.getValue().getKey();
            res[index][1] = entry.getValue().getValue();
            Pair<Integer, Integer> lastSeen = last.get(id);
            res[index][2] = lastSeen.getKey();
            res[index][3] = lastSeen.getValue();
            index++;
        }
        return res;
    }

    private void mark(int[][] land, int r, int c, int id) {
        if (r < land.length && c < land[0].length && land[r][c] == 1) {
            land[r][c] = id;
            mark(land, r, c + 1, id); //next column
            mark(land, r + 1, c, id); //next row
        }
    }
}

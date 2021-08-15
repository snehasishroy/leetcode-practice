import javafx.util.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/path-with-maximum-minimum-value/
 * <p>
 * Given a matrix of integers A with R rows and C columns, find the maximum score of a path starting at [0,0] and ending at [R-1,C-1].
 * <p>
 * The score of a path is the minimum value in that path.  For example, the value of the path 8 →  4 →  5 →  9 is 4.
 * <p>
 * A path moves some number of times from one visited cell to any neighbouring unvisited cell in one of the 4 cardinal directions (north, east, west, south).
 * <p>
 * Input: [[5,4,5],[1,2,6],[7,4,6]]
 * Output: 4
 * Explanation:
 * The path with the maximum score is highlighted in yellow.
 * <p>
 * Input: [[2,2,1,2,2,2],[1,2,2,2,1,2]]
 * Output: 2
 * <p>
 * Input: [[3,4,6,3,4],[0,2,1,1,7],[8,8,3,2,7],[3,2,4,9,8],[4,1,2,0,0],[4,6,5,4,3]]
 * Output: 3
 * <p>
 * Note:
 * 1 <= R, C <= 100
 * 0 <= A[i][j] <= 10^9
 */
public class PathWithMaximumMinimumValue {
    int[] x_offsets = new int[]{0, 1, 0, -1};
    int[] y_offsets = new int[]{1, 0, -1, 0};

    /**
     * Approach: Use Priority Queue to pick the path with largest value first, keeping track of smallest value occurred in the path
     * Took ~83 ms
     * <p>
     * Initially I went ahead with DFS + Memoization but got WA for the sample inputs
     * Realized later that memoization won't work because memoized answer depends upon the path that visited the cell first time
     * <pre>
     *    5
     * 5--6--10
     *    3
     * </pre>
     * If we reach 6 cell from right, result stored at 6 would be 5
     * If we reach 6 cell from top, result stored at 6 would be 6
     * <p>
     * Later during thought process, I thought of binary search as it involves finding maximum of minimum and as we already know
     * that binary search can help in finding maximum of minimum
     * <p>
     * {@link LongestIncreasingPathInMatrix} does not suffer from cycles as it asks for strictly increasing path, a child node
     * can't ever go back to its parent node
     * <p>
     * {@link SplitArrayLargestSum} {@link PacificAtlanticWaterFlow} {@link LastDayWhereYouCanStillCross}
     */
    public int maximumMinimumPathGreedy(int[][] A) {
        //pq of row,col sorted on decreasing values in A
        PriorityQueue<Node> pq = new PriorityQueue<>(((o1, o2) -> Integer.compare(A[o2.x][o2.y], A[o1.x][o1.y])));
        pq.add(new Node(0, 0, A[0][0]));
        int m = A.length;
        int n = A[0].length;
        boolean[][] visited = new boolean[m][n];
        visited[0][0] = true;
        while (!pq.isEmpty()) {
            Node head = pq.remove(); //pq ensures we choose the path with the maximum value first
            if (head.x == m - 1 && head.y == n - 1) { //if we reach the target node, this means we did it by choosing the path with the largest values
                return head.minValue;
            } else {
                for (int i = 0; i < 4; i++) {
                    int new_row = head.x + x_offsets[i];
                    int new_col = head.y + y_offsets[i];
                    if (new_row >= 0 && new_row < m && new_col >= 0 && new_col < n && !visited[new_row][new_col]) {
                        visited[new_row][new_col] = true;
                        pq.add(new Node(new_row, new_col, Math.min(head.minValue, A[new_row][new_col]))); //keep track of the smallest value seen in the path
                    }
                }
            }
        }
        return -1; //not possible
    }

    /**
     * Approach: Binary search approach, check if a path exists with mid as the min value. If yes, recurse in the right part
     * If no, recurse in the left part
     * <p>
     * Fastest took ~17 ms
     */
    public int maximumMinimumPathBinarySearch(int[][] A) {
        int low = 0, high = Math.min(A[0][0], A[A.length - 1][A[0].length - 1]), ans = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            boolean[][] visited = new boolean[A.length][A[0].length];
            visited[0][0] = true;
            if (pathExistsWithMinValue(A, 0, 0, mid, visited)) { //find a path with even higher value if path with mid as min value exists
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    private boolean pathExistsWithMinValue(int[][] a, int row, int col, int minVal, boolean[][] visited) {
        if (row == a.length - 1 && col == a[0].length - 1) {
            return true;
        } else {
            for (int i = 0; i < 4; i++) {
                int new_row = row + x_offsets[i];
                int new_col = col + y_offsets[i];
                if (new_row >= 0 && new_row < a.length && new_col >= 0 && new_col < a[0].length && !visited[new_row][new_col]
                        && a[new_row][new_col] >= minVal) { //only choose path with values >= minVal
                    visited[new_row][new_col] = true;
                    if (pathExistsWithMinValue(a, new_row, new_col, minVal, visited)) {
                        return true;
                    }
                }
            }
            //no such path exists
            return false;
        }
    }

    /**
     * Approach: Solve using union find. Since we have to connect source and target node, eventually they will become part of the same component
     * We will greedily pick up cells with largest value first and see whether picking it can merge source and target together
     * This approach is very similar to Kruskal's algorithm to generate MST
     * <p>
     * Slowest, took ~131ms
     */
    public int maximumMinimumPathUnionFind(int[][] A) {
        int m = A.length;
        int n = A[0].length;
        int[] parent = new int[m * n]; //we flatten 2D indices into 1D
        for (int i = 0; i < parent.length; i++) {
            parent[i] = i;
        }
        ArrayList<Pair<Integer, Integer>> list = new ArrayList<>(); //list of indices of x and y
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                list.add(new Pair<>(i, j));
            }
        }
        //sort the list based on descending order of values present in A.
        list.sort(((o1, o2) -> Integer.compare(A[o2.getKey()][o2.getValue()], A[o1.getKey()][o1.getValue()])));
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < list.size(); i++) { //iterate from largest to smallest value
            Pair<Integer, Integer> index = list.get(i);
            visited[index.getKey()][index.getValue()] = true;
            for (int j = 0; j < 4; j++) {
                int new_row = index.getKey() + x_offsets[j];
                int new_col = index.getValue() + y_offsets[j];
                //please note that we are performing union only if the neighbour was already visited
                if (new_row >= 0 && new_row < m && new_col >= 0 && new_col < n && visited[new_row][new_col]) {
                    union(new_row * n + new_col, index.getKey() * n + index.getValue(), parent);
                }
            }
            //if this cell caused the source and target to be part of the same component, this is the highest value possible
            if (find(0, parent) == find(m * n - 1, parent)) {
                return A[index.getKey()][index.getValue()];
            }
        }
        return -1; //not possible as source and target are always connected
    }

    private int find(int cell, int[] parent) {
        if (parent[cell] == cell) {
            return cell;
        } else {
            return parent[cell] = find(parent[cell], parent);
        }
    }

    private void union(int cell1, int cell2, int[] parent) {
        int root1 = find(cell1, parent); //please make sure that you find the correct parent by calling find(), do not refer to parent[cell1] directly
        //took me a hour to find this stupid bug
        int root2 = find(cell2, parent);
        if (root1 != root2) {
            parent[root1] = root2;
        }
    }

    private static class Node {
        int x;
        int y;
        int minValue;

        public Node(int x, int y, int minValue) {
            this.x = x;
            this.y = y;
            this.minValue = minValue;
        }
    }
}

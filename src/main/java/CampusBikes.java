import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/campus-bikes/ Premium
 * <p>
 * On a campus represented as a 2D grid, there are N workers and M bikes, with N <= M. Each worker and bike is a 2D coordinate on this grid.
 * <p>
 * Our goal is to assign a bike to each worker. Among the available bikes and workers, we choose the (worker, bike)
 * pair with the shortest Manhattan distance between each other, and assign the bike to that worker.
 * If there are multiple (worker, bike) pairs with the same shortest Manhattan distance, we choose the pair with the smallest worker index;
 * if there are multiple ways to do that, we choose the pair with the smallest bike index).
 * We repeat this process until there are no available workers.
 * <p>
 * The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.
 * <p>
 * Return a vector ans of length N, where ans[i] is the index (0-indexed) of the bike that the i-th worker is assigned to.
 * <p>
 * Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
 * Output: [1,0]
 * Explanation:
 * Worker 1 grabs Bike 0 as they are closest (without ties), and Worker 0 is assigned Bike 1. So the output is [1, 0].
 * <p>
 * Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
 * Output: [0,2,1]
 * Explanation:
 * Worker 0 grabs Bike 0 at first. Worker 1 and Worker 2 share the same distance to Bike 2, thus Worker 1 is assigned to Bike 2,
 * and Worker 2 will take Bike 1. So the output is [0,2,1].
 */
public class CampusBikes {
    /**
     * Approach: Assignment problem, Greedy, Calculate distance between all pairs of workers and bikes (n^2) and sort them.
     * Initially I implemented a priority queue solution which gave TLE, then pushed all distances pair to an array and directly sorted it which got AC.
     * During sort take care to resolve conflicts, took a couple of attempts for me to figure it out during my initial implementation.
     * Start assigning bikes greedily and skip already assigned workers/bikes
     * <p>
     * Learnings -- Notice the range of the inputs, can use bucket sort if smaller range.
     *
     * TODO Apply Simple Stable Marriage problem i.e Gale–Shapley algorithm to optimize
     * <p>
     * <strike>
     * This greedy approach is wrong and doesn't work for all test cases
     * Raised a bug here https://github.com/LeetCode-Feedback/LeetCode-Feedback/issues/4294
     * Test case that fails for greedy [[0,0],[5,0]], [[4,0],[7,0]]
     * </strike>
     * This problem does not ask to do the assignment in the shortest cost possible, it just asks to assign the bike to the nearest worker possible.
     * So this greedy solution works fine but if it had been the other case, we would have needed a different algorithm.
     * <p>
     * {@link MaximumCompatibilityScoreSum} related assignment problem
     */
    public int[] assignBikesBucketSort(int[][] workers, int[][] bikes) {
        List<List<Pair<Integer, Integer>>> distances = new ArrayList<>(2001); //worker index, bike index
        for (int i = 0; i < 2001; i++) {
            distances.add(new ArrayList<>());
        }
        boolean[] assignedBikes = new boolean[bikes.length];
        boolean[] assignedWorkers = new boolean[bikes.length];
        int[] result = new int[workers.length];
        //to avoid sort use counting sort and push indices in the expected order to avoid sorting again
        //the {worker, bike} for same distance should be sorted based on the worker index first, then bike index
        //this can be handled if we insert them at the correct order i.e if we iterate over workers in ascending order first,
        //then iterate bike indexes, we can avoid sorting!
        for (int i = 0; i < workers.length; i++) { //iterate workers in ascending order, as workers have higher priority over bike index
            int[] worker = workers[i];
            for (int j = 0; j < bikes.length; j++) { //iterate bikes in ascending order, as bikes has second priority
                int distance = Math.abs(bikes[j][1] - worker[1]) + Math.abs(bikes[j][0] - worker[0]);
                distances.get(distance).add(new Pair<>(i, j));
            }
        }
        int assigned = 0;
        int index = 0;
        while (assigned < workers.length) {
            List<Pair<Integer, Integer>> candidates = distances.get(index++);
            for (Pair<Integer, Integer> candidate : candidates) {
                if (!assignedBikes[candidate.getValue()] && !assignedWorkers[candidate.getKey()]) { //greedy assignment
                    assigned++;
                    result[candidate.getKey()] = candidate.getValue();
                    assignedBikes[candidate.getValue()] = true;
                    assignedWorkers[candidate.getKey()] = true;
                }
            }
        }
        return result;
    }
}

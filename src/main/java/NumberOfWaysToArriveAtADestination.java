import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <pre>
 * https://leetcode.com/problems/number-of-ways-to-arrive-at-destination/
 *
 * You are in a city that consists of n intersections numbered from 0 to n - 1 with bi-directional roads between some intersections.
 * The inputs are generated such that you can reach any intersection from any other intersection and that there is at most one road between any two intersections.
 *
 * You are given an integer n and a 2D integer array roads where roads[i] = [ui, vi, timei] means that there is a road between intersections ui and vi that takes timei minutes to travel.
 * You want to know in how many ways you can travel from intersection 0 to intersection n - 1 in the shortest amount of time.
 *
 * Return the number of ways you can arrive at your destination in the shortest amount of time. Since the answer may be large, return it modulo 10^9 + 7.
 *
 * Input: n = 7, roads = [[0,6,7],[0,1,2],[1,2,3],[1,3,3],[6,3,3],[3,5,1],[6,5,1],[2,5,1],[0,4,5],[4,6,2]]
 * Output: 4
 * Explanation: The shortest amount of time it takes to go from intersection 0 to intersection 6 is 7 minutes.
 * The four ways to get there in 7 minutes are:
 * - 0 ➝ 6
 * - 0 ➝ 4 ➝ 6
 * - 0 ➝ 1 ➝ 2 ➝ 5 ➝ 6
 * - 0 ➝ 1 ➝ 3 ➝ 5 ➝ 6
 *
 * Input: n = 2, roads = [[1,0,10]]
 * Output: 1
 * Explanation: There is only one way to go from intersection 0 to intersection 1, and it takes 10 minutes.
 *
 * Constraints:
 * 1 <= n <= 200
 * n - 1 <= roads.length <= n * (n - 1) / 2
 * roads[i].length == 3
 * 0 <= ui, vi <= n - 1
 * 1 <= timei <= 10^9
 * ui != vi
 * There is at most one road connecting any two intersections.
 * You can reach any intersection from any other intersection.
 * </pre>
 */
public class NumberOfWaysToArriveAtADestination {

    /**
     * Approach: DP on Graphs, Modified Djikstra algorithm.
     * Apart from storing the min distance required to reach a node, need another array that stores the number of ways that are possible
     * to reach a node in the min cost i.e. when you reach a node with the current cost as the minimum cost, number of ways to reach that node
     * would equal the number of ways to reach its parent node.
     *
     * Similarly, if the current cost to reach this node equals the min cost already present, we have to increment the number of ways to reach that node.
     *
     * Received WA in the contest because I initialized the dist[] array with Integer.MAX_VALUE instead of Long.MAX_VALUE.
     * Could have received a way better rank in the contest if I had got AC :)
     *
     * {@link AllPathsFromSourceToTarget} {@link NumberOfLongestIncreasingSubsequences}
     */
    public int countPaths(int n, int[][] roads) {
        int MOD = 1_000_000_007;
        long[] dist = new long[n];
        long[] ways = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        ways[0] = 1; //base case, number of ways to reach the start node
        PriorityQueue<long[]> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1], o2[1])); //node_id, cost, parent_id
        pq.add(new long[]{0, 0, 0});
        List<List<Pair<Integer, Integer>>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            graph.get(road[0]).add(new Pair<>(road[1], road[2]));
            graph.get(road[1]).add(new Pair<>(road[0], road[2]));
        }
        long minCost = Long.MAX_VALUE;
        while (!pq.isEmpty()) {
            long[] head = pq.poll();
            int parentId = (int) head[0];
            for (Pair<Integer, Integer> neighbour : graph.get(parentId)) {
                long new_distance = head[1] + neighbour.getValue();
                if (dist[neighbour.getKey()] > new_distance) { //djikstra, visit this edge only if it reduces the min distance
                    dist[neighbour.getKey()] = new_distance;
                    ways[neighbour.getKey()] = ways[parentId] % MOD; //reset the number of ways to reach this node
                    pq.add(new long[]{neighbour.getKey(), new_distance, parentId});
                } else if (dist[neighbour.getKey()] == new_distance) {
                    //this is the most tricky part, if current distance equals the existing min distance, we have to increment the total number of ways to reach this node
                    //however we don't want to push this node again into the priority queue as previously some parent node already visited this node earlier
                    //if we push it again, we will process redundant nodes and get TLE
                    //I was able to solve this part by actually dry running the sample test cases and walking through the example
                    ways[neighbour.getKey()] += ways[parentId];
                    ways[neighbour.getKey()] %= MOD;
                }
            }
        }
        return (int) (ways[n - 1]); //return the number of ways to reach last node
    }
}

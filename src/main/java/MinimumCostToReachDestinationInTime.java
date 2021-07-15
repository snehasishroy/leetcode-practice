import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * <pre>
 * https://leetcode.com/problems/minimum-cost-to-reach-destination-in-time/
 *
 * There is a country of n cities numbered from 0 to n - 1 where all the cities are connected by bi-directional roads.
 * The roads are represented as a 2D integer array edges where edges[i] = [xi, yi, timei] denotes a road between cities xi and yi that takes timei minutes to travel.
 * There may be multiple roads of differing travel times connecting the same two cities, but no road connects a city to itself.
 *
 * Each time you pass through a city, you must pay a passing fee. This is represented as a 0-indexed integer array passingFees of length n where passingFees[j]
 * is the amount of dollars you must pay when you pass through city j.
 *
 * In the beginning, you are at city 0 and want to reach city n - 1 in maxTime minutes or less.
 * The cost of your journey is the summation of passing fees for each city that you passed through at some moment of your journey (including the source and destination cities).
 *
 * Given maxTime, edges, and passingFees, return the minimum cost to complete your journey, or -1 if you cannot complete it within maxTime minutes.
 *
 * Input: maxTime = 30, edges = [[0,1,10],[1,2,10],[2,5,10],[0,3,1],[3,4,10],[4,5,15]], passingFees = [5,1,2,20,20,3]
 * Output: 11
 * Explanation: The path to take is 0 -> 1 -> 2 -> 5, which takes 30 minutes and has $11 worth of passing fees.
 *
 * Input: maxTime = 29, edges = [[0,1,10],[1,2,10],[2,5,10],[0,3,1],[3,4,10],[4,5,15]], passingFees = [5,1,2,20,20,3]
 * Output: 48
 * Explanation: The path to take is 0 -> 3 -> 4 -> 5, which takes 26 minutes and has $48 worth of passing fees.
 * You cannot take path 0 -> 1 -> 2 -> 5 since it would take too long.
 *
 * Input: maxTime = 25, edges = [[0,1,10],[1,2,10],[2,5,10],[0,3,1],[3,4,10],[4,5,15]], passingFees = [5,1,2,20,20,3]
 * Output: -1
 * Explanation: There is no way to reach city 5 from city 0 within 25 minutes.
 *
 * Constraints:
 * 1 <= maxTime <= 1000
 * n == passingFees.length
 * 2 <= n <= 1000
 * n - 1 <= edges.length <= 1000
 * 0 <= xi, yi <= n - 1
 * 1 <= timei <= 1000
 * 1 <= passingFees[j] <= 1000
 * The graph may contain multiple edges between two nodes.
 * The graph does not contain self loops.
 * </pre>
 */
public class MinimumCostToReachDestinationInTime {
    /**
     * Approach: Djikstra, Graph is weighted, so BFS can't be applied.
     * For Djikstra, we need to maintain two states, cost and time. Visit an edge only if it relaxes any of this condition.
     * Keep the priority queue ordered based on the primary requirement ie. minimum cost.
     *
     * During the contest, I solved this problem by referring to previously solved {@link CheapestFlightWithinKStop}
     * Glad to have solved this problem during the contest. Solving this problem made me finish the contest at rank 228; my best finish ever :)
     */
    public int minCost(int maxTime, int[][] edges, int[] passingFees) {
        int n = passingFees.length;
        List<List<Pair<Integer, Integer>>> graph = new ArrayList<>(); //{node -> {node, time}}
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            graph.get(edge[0]).add(new Pair<>(edge[1], edge[2]));
            graph.get(edge[1]).add(new Pair<>(edge[0], edge[2]));
        }
        int[] cost = new int[n];
        int[] time = new int[n];
        Arrays.fill(cost, Integer.MAX_VALUE);
        Arrays.fill(time, Integer.MAX_VALUE);
        cost[0] = passingFees[0];
        time[0] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.cost, o2.cost)); //sort by cost
        pq.add(new Node(0, 0, cost[0]));
        while (!pq.isEmpty()) {
            Node head = pq.poll();
            if (head.id == n - 1) { //destination reached, since priority queue is sorted based on cost, it's guaranteed to be visited with min cost possible
                return head.cost;
            }
            for (Pair<Integer, Integer> neighbor : graph.get(head.id)) {
                int new_cost = passingFees[neighbor.getKey()] + head.cost;
                int new_time = neighbor.getValue() + head.time;
                //if time required is within constraint and this edge relaxes any of the conditions (smaller time or smaller cost), visit this edge
                if (new_time <= maxTime && ((cost[neighbor.getKey()] > new_cost) || (time[neighbor.getKey()] > new_time))) {
                    cost[neighbor.getKey()] = new_cost;
                    time[neighbor.getKey()] = new_time;
                    pq.add(new Node(neighbor.getKey(), new_time, new_cost));
                }
            }
        }
        return -1;
    }

    private static class Node {
        public int id;
        public int time;
        public int cost;
        public Node(int id, int time, int cost) {
            this.id = id;
            this.time = time;
            this.cost = cost;
        }
    }
}

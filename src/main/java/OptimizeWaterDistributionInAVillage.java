import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/optimize-water-distribution-in-a-village/
 * <p>
 * There are n houses in a village. We want to supply water for all the houses by building wells and laying pipes.
 * <p>
 * For each house i, we can either build a well inside it directly with cost wells[i], or pipe in water from another well to it.
 * The costs to lay pipes between houses are given by the array pipes, where each pipes[i] = [house1, house2, cost] represents
 * the cost to connect house1 and house2 together using a pipe. Connections are bidirectional.
 * <p>
 * Find the minimum total cost to supply water to all houses.
 * <p>
 * Input: n = 3, wells = [1,2,2], pipes = [[1,2,1],[2,3,1]]
 * Output: 3
 * Explanation:
 * The image shows the costs of connecting houses using pipes.
 * The best strategy is to build a well in the first house with cost 1 and connect the other houses to it with cost 2 so the total cost is 3.
 * <p>
 * <p>
 * Constraints:
 * 1 <= n <= 10000
 * wells.length == n
 * 0 <= wells[i] <= 10^5
 * 1 <= pipes.length <= 10000
 * 1 <= pipes[i][0], pipes[i][1] <= n
 * 0 <= pipes[i][2] <= 10^5
 * pipes[i][0] != pipes[i][1]
 */
public class OptimizeWaterDistributionInAVillage {
    /**
     * Approach: Create a new hidden node that is connected to all the villages with cost wells[i] and then create a MST
     * <p>
     * In my initial thought process, I knew I have to create a MST but didn't know which well to pick from, so I greedily chose
     * the well with lowest cost and then tried to create MST until the cost to connect pipe > placing a well
     * My plan was to try to create MST from each unvisited well, I don't know whether it will work or not !
     * <p>
     * To see kruskal implementation for MST which is based on union find algorithm, refer to {@link ConnectingCitiesWithMinCost}
     * {@link MinimumCostToConnectSticks} related minimum spanning tree problem
     * {@link LastDayWhereYouCanStillCross} related hidden node problem
     */
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        boolean[] visited = new boolean[n + 1];
        List<List<Pair<Integer, Integer>>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] pipe : pipes) {
            graph.get(pipe[0]).add(new Pair<>(pipe[1], pipe[2])); //bidirectional edges
            graph.get(pipe[1]).add(new Pair<>(pipe[0], pipe[2]));
        }
        for (int i = 0; i < n; i++) { //create a hidden node with index 0 and connect all the remaining nodes with cost wells[i]
            graph.get(0).add(new Pair<>(i + 1, wells[i]));
            graph.get(i + 1).add(new Pair<>(0, wells[i]));
        }
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.getValue(), o2.getValue())); //pair of index -> distance
        pq.add(new Pair<>(0, 0));
        int totalCost = 0;
        int nodesVisited = 0; //use this as an optimization to break early from the while loop
        while (!pq.isEmpty() && nodesVisited < n + 1) {
            Pair<Integer, Integer> head = pq.remove();
            int nodeId = head.getKey();
            int curCost = head.getValue();
            if (!visited[nodeId]) {
                visited[nodeId] = true;
                nodesVisited++;
                totalCost += curCost; //increment the total cost
                for (Pair<Integer, Integer> neighbour : graph.get(nodeId)) {
                    if (!visited[neighbour.getKey()]) { //always check whether the neighbour is visited or not before pushing to PQ
                        pq.add(new Pair<>(neighbour.getKey(), neighbour.getValue()));
                    }
                }
            }
        }
        return totalCost;
    }
}

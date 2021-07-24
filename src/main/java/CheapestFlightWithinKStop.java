import javafx.util.Pair;

import java.util.*;

/**
 * https://leetcode.com/problems/cheapest-flights-within-k-stops/
 * <p>
 * There are n cities connected by m flights. Each flight starts from city u and arrives at v with a price w.
 * <p>
 * Now given all the cities and flights, together with starting city src and the destination dst,
 * your task is to find the cheapest price from src to dst with up to k stops. If there is no such route, output -1.
 * <p>
 * Input:
 * n = 3, edges = [[0,1,100],[1,2,100],[0,2,500]]
 * src = 0, dst = 2, k = 1
 * Output: 200
 * <p>
 * The cheapest price from city 0 to city 2 with at most 1 stop costs 200
 * <p>
 * Constraints:
 * 1 <= n <= 100
 * 0 <= flights.length <= (n * (n - 1) / 2)
 * flights[i].length == 3
 * 0 <= fromi, toi < n
 * fromi != toi
 * 1 <= pricei <= 104
 * There will not be any multiple flights between two cities.
 * 0 <= src, dst, k < n
 * src != dst
 */
public class CheapestFlightWithinKStop {

    /**
     * Approach: Greedy, Since the graph is a weighted graph, leverage Djikstra to find the lowest cost to reach a target node from source node.
     * The trick is to relax the djikstra condition to not process a node if current distance to reach node > distance present already for node
     * because the current distance might be higher but it might have shorter hops. Hence add all the nodes whose hops are within bounds
     * <p>
     * This solution adds another parameter per node similar to what we learnt earlier in {@link ShortestPathInGridWithObstacleElimination} but
     * this gives TLE because the constraints in the linked problem are much relaxed than this problem.
     */
    public int findCheapestPriceUsingDjikstraTLE(int n, int[][] flights, int src, int dst, int K) {
        List<List<Pair<Integer, Integer>>> graph = buildGraph(n, flights); //pair of city, cost
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.currentDistance, o2.currentDistance));
        pq.add(new Node(src, 0, -1));
        int[][] cost = new int[n][K + 1]; //add another state per node
        for (int[] rows : cost) {
            Arrays.fill(rows, Integer.MAX_VALUE);
        }
        cost[src][0] = 0;
        while (!pq.isEmpty()) {
            Node head = pq.remove();
            if (head.cityID == dst) {
                //priority queue ensures that the current distance would be the cheapest to reach destination
                return head.currentDistance;
            }
            for (Pair<Integer, Integer> adjacentCity : graph.get(head.cityID)) {
                int new_hops = head.currentHops + 1;
                int new_cost = head.currentDistance + adjacentCity.getValue();
                if (new_hops <= K && cost[adjacentCity.getKey()][new_hops] > new_cost) { //if hops are valid and this hop reduces the existing cost
                    pq.add(new Node(adjacentCity.getKey(), new_cost, new_hops));
                }
            }
        }
        return -1;
    }

    /**
     * <pre>
     * Approach: Greedy, Instead of maintaining nested states (2D) per node, maintain a separate cost array and hops array per node.
     * Visit a node only if visiting current edge reduces either the cost of that node or reaches that node in lesser hops.
     * This drastically reduces the no of states that are pushed in PriorityQueue.
     *
     * Thing to note here is you have to keep the information at each node in a consistent state ie. if you reach a node in less hops but with more distance
     * you will still have to update the distance to the current info.
     * Do a dry run on this graph
     *               a
     *          50 /   \ 10
     *           b _20_ c
     *        5 /
     *        d
     * </pre>
     * {@link ShortestPathInGridWithObstacleElimination} {@link MinimumCostToReachDestinationInTime}
     */
    public int findCheapestPriceUsingDjikstraOptimized(int n, int[][] flights, int src, int dst, int K) {
        List<List<Pair<Integer, Integer>>> graph = buildGraph(n, flights); //pair of city, cost
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.currentDistance, o2.currentDistance));
        pq.add(new Node(src, 0, -1));
        //maintain separate states per node instead of nested (2D) array
        int[] cost = new int[n];
        int[] stops = new int[n];
        Arrays.fill(cost, Integer.MAX_VALUE);
        Arrays.fill(stops, Integer.MAX_VALUE);
        cost[src] = 0;
        stops[src] = -1;
        while (!pq.isEmpty()) {
            Node head = pq.remove();
            if (head.cityID == dst) {
                //priority queue ensures that the current distance would be the cheapest to reach destination
                return head.currentDistance;
            }
            for (Pair<Integer, Integer> adjacentCity : graph.get(head.cityID)) {
                int new_hops = head.currentHops + 1;
                int new_cost = head.currentDistance + adjacentCity.getValue();
                if (new_hops <= K && (cost[adjacentCity.getKey()] > new_cost || stops[adjacentCity.getKey()] > new_hops)) {
                    //if visiting this edge relaxes any of the conditions for node, update the cost and stops irrespective of the current minimum
                    //ie. if new_hops < cur_hops but new_cost > cur_cost, still update the cost to a higher value.
                    cost[adjacentCity.getKey()] = new_cost;
                    stops[adjacentCity.getKey()] = new_hops;
                    pq.add(new Node(adjacentCity.getKey(), new_cost, new_hops));
                }
            }
        }
        return -1;
    }

    /**
     * {@link PathWithMaxProbability} for similar question
     * For finding shortest path in a unweighted graph, BFS is the way to go but since this is a weighted graph, BFS needs to traverse the entire graph
     * in order to return the shortest distance to reach a node
     * <p>
     * Also in order to prune the contents of the queue, add a node to the queue only if the distance to reach a node > current distance
     * {@link WordLadder2}
     */
    public int findCheapestPriceUsingBFS(int n, int[][] flights, int src, int dst, int K) {
        List<List<Pair<Integer, Integer>>> graph = buildGraph(n, flights);
        //node id -> (cost to reach current node, hops to reach)
        ArrayDeque<Pair<Integer, Pair<Integer, Integer>>> queue = new ArrayDeque<>();
        queue.add(new Pair<>(src, new Pair<>(0, -1)));
        int result = Integer.MAX_VALUE;
        int[] distance = new int[n]; //used for pruning
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[src] = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Pair<Integer, Pair<Integer, Integer>> head = queue.remove();
                if (head.getKey() == dst && head.getValue().getKey() < result) {
                    //Critical to not return from here because this is BFS, BFS on weighted graph will not guarantee that the first time
                    // we reach destination node is the shortest distance, we need to traverse the entire graph
                    result = head.getValue().getKey();
                }
                for (Pair<Integer, Integer> adjacent : graph.get(head.getKey())) {
                    int curDistance = adjacent.getValue() + head.getValue().getKey();
                    int currentHops = head.getValue().getValue() + 1;
                    if (distance[adjacent.getKey()] > curDistance && currentHops <= K) {
                        //pruning, critical to avoid TLE
                        //don't proceed if no of hops exceed permissible value and already a smaller path exists to reach adjacent node
                        queue.add(new Pair<>(adjacent.getKey(), new Pair<>(curDistance, currentHops)));
                        distance[adjacent.getKey()] = curDistance;
                    }
                }
            }
        }
        return result == Integer.MAX_VALUE ? -1 : result;
    }

    private List<List<Pair<Integer, Integer>>> buildGraph(int n, int[][] flights) {
        List<List<Pair<Integer, Integer>>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] flight : flights) {
            graph.get(flight[0]).add(new Pair<>(flight[1], flight[2]));
        }
        return graph;
    }

    private static class Node {
        int cityID;
        int currentDistance;
        int currentHops;

        public Node(int cityID, int currentDistance, int currentHops) {
            this.cityID = cityID;
            this.currentDistance = currentDistance;
            this.currentHops = currentHops;
        }
    }
}

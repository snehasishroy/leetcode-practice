import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/all-paths-from-source-to-target/
 * <p>
 * Given a directed, acyclic graph of N nodes.  Find all possible paths from node 0 to node N-1, and return them in any order.
 * <p>
 * The graph is given as follows:  the nodes are 0, 1, ..., graph.length - 1.  graph[i] is a list of all nodes j for which the edge (i, j) exists.
 * <p>
 * Input: [[1,2], [3], [3], []]
 * Output: [[0,1,3],[0,2,3]]
 * Explanation: The graph looks like this:
 * <pre>
 * 0--->1
 * |    |
 * v    v
 * 2--->3
 * </pre>
 * There are two paths: 0 -> 1 -> 3 and 0 -> 2 -> 3.
 */
public class AllPathsFromSourceToTarget {
    /**
     * Approach: Backtracking, perform DFS at each path, keeping track of nodes visited so far
     * TimeComplexity: 2^N * N, Everytime we add a node to the graph, number of paths would double, very similar to how
     * subsequences are counted {@link DistinctSubsequences}
     *
     * {@link NumberOfWaysToArriveAtADestination} follow up question
     */
    public List<List<Integer>> allPathsSourceTarget(int[][] edges) {
        List<List<Integer>> graph = buildGraph(edges);
        List<List<Integer>> paths = new ArrayList<>();
        //visited array isn't required because problem statement mentions that the graph has no cycles
        DFS(graph, paths, new ArrayList<>(), 0, edges.length - 1);
        return paths;
    }

    private void DFS(List<List<Integer>> graph, List<List<Integer>> paths, ArrayList<Integer> currentPath, int currentIndex, int targetIndex) {
        if (currentIndex == targetIndex) { //reached target node
            currentPath.add(currentIndex);
            paths.add(new ArrayList<>(currentPath));
            currentPath.remove(currentIndex);
        } else {
            currentPath.add(currentIndex);
            for (int adjacentNode : graph.get(currentIndex)) {
                DFS(graph, paths, currentPath, adjacentNode, targetIndex);
            }
            currentPath.remove(currentPath.size() - 1); //backtrack
        }

    }

    private List<List<Integer>> buildGraph(int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < edges.length; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < edges.length; i++) {
            List<Integer> edgesForNode = graph.get(i);
            for (int j = 0; j < edges[i].length; j++) {
                edgesForNode.add(edges[i][j]);
            }
        }
        return graph;
    }
}

import java.util.*;

/**
 * <pre>
 * https://leetcode.com/problems/smallest-common-region/ Premium
 *
 * You are given some lists of regions where the first region of each list includes all other regions in that list.
 * Naturally, if a region X contains another region Y then X is bigger than Y. Also by definition a region X contains itself.
 * Given two regions region1, region2, find out the smallest region that contains both of them.
 *
 * If you are given regions r1, r2 and r3 such that r1 includes r3, it is guaranteed there is no r2 such that r2 includes r3.
 *
 * It's guaranteed the smallest region exists.
 *
 * Input:
 * regions = [["Earth","North America","South America"],
 * ["North America","United States","Canada"],
 * ["United States","New York","Boston"],
 * ["Canada","Ontario","Quebec"],
 * ["South America","Brazil"]],
 * region1 = "Quebec",
 * region2 = "New York"
 * Output: "North America"
 *
 * Constraints:
 * 2 <= regions.length <= 10^4
 * region1 != region2
 * All strings consist of English letters and spaces with at most 20 letters.
 * </pre>
 */
public class SmallestCommonRegion {

    /**
     * Approach: Model it as a graph problem, smallest common region will be the LCA of two nodes.
     *
     * {@link LCABinaryTree}
     */
    public String findSmallestRegionLCA(List<List<String>> regions, String region1, String region2) {
        Map<String, List<String>> graph = new HashMap<>(); //parent --> child
        String rootRegion = ""; //need to start the traversal from the root node
        Map<String, Integer> indegree = new HashMap<>(); //root node will have indegree of 0
        for (List<String> region : regions) {
            List<String> subregions = graph.computeIfAbsent(region.get(0), __ -> new ArrayList<>());
            for (String subregion : region.subList(1, region.size())) {
                subregions.add(subregion);
                indegree.put(subregion, 1);
            }
        }
        for (String region : graph.keySet()) {
            if (!indegree.containsKey(region)) { //if the region does not have a parent, it's a root node
                rootRegion = region;
            }
        }
        return lca(rootRegion, graph, region1, region2);
    }

    private String lca(String root, Map<String, List<String>> graph, String region1, String region2) {
        if (root.equals(region1) || root.equals(region2)) {
            return root;
        } else {
            int count = 0;
            String candidate = null;
            for (String subregion : graph.getOrDefault(root, new ArrayList<>())) {
                String result = lca(subregion, graph, region1, region2);
                if (result != null) {
                    candidate = result;
                    count++;
                }
            }
            //if this node has 2 subregions under it, then definitely this node is the LCA
            //if this node has only 1 subregion under it, then it might be the LCA itself, so just forward it as is to the parent
            if (count == 0) {
                return null;
            } else if (count == 1) {
                return candidate;
            } else {
                return root;
            }
        }
    }

    /**
     * Approach: Find LCA by creating the graph by maintaining child --> parent relationships. Then store all the parents of a region
     * in a set. Repeat the process for another region, first node to be the common parents of both nodes will be the result.
     */
    public String findSmallestRegion(List<List<String>> regions, String region1, String region2) {
        HashMap<String, String> map = new HashMap<>();
        for (List<String> item : regions) {
            String parent = item.get(0);
            for (int i = 1; i < item.size(); i++) {
                map.put(item.get(i), parent); //store parent for each region
            }
        }
        HashSet<String> candidates = new HashSet<>(); //create a set of parents for the region1
        while (region1 != null) {
            candidates.add(region1);
            region1 = map.get(region1);
        }
        //iteratively walk the parent graph for region2, first region present in both region1 and region2 is the smallest common region
        while (!candidates.contains(region2)) {
            region2 = map.get(region2);
        }
        return region2;
    }
}

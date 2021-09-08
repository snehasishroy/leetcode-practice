import java.util.List;

/**
 * https://leetcode.com/problems/nested-list-weight-sum/ Premium
 * <p>
 * Given a nested list of integers, return the sum of all integers in the list weighted by their depth.
 * <p>
 * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
 * <p>
 * Input: [[1,1],2,[1,1]]
 * Output: 10
 * Explanation: Four 1's at depth 2, one 2 at depth 1.
 * <p>
 * Input: [1,[4,[6]]]
 * Output: 27
 * Explanation: One 1 at depth 1, one 4 at depth 2, and one 6 at depth 3; 1 + 4*2 + 6*3 = 27.
 */
public class NestedListWeightSum {

    /**
     * Approach: DFS
     *
     * {@link ArrayNesting}
     */
    public int depthSum(List<NestedInteger> nestedList) {
        return DFS(nestedList, 1);
    }

    private int DFS(List<NestedInteger> list, int depth) {
        int res = 0;
        for (NestedInteger child : list) {
            if (!child.getList().isEmpty()) { //If current child is a list, recur
                res += DFS(child.getList(), depth + 1);
            } else if (child.getInteger() != null) { //else if current child is an int, directly evaluate the weight
                //don't use simple else statement, because test cases contains inputs like [[],[],[]]
                res += (child.getInteger() * depth);
            }
        }
        return res;
    }

    private interface NestedInteger {
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        boolean isInteger();

        // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        Integer getInteger();

        // Set this NestedInteger to hold a single integer.
        void setInteger(int value);

        // Set this NestedInteger to hold a nested list and adds a nested integer to it.
        void add(NestedInteger ni);

        // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        List<NestedInteger> getList();
    }
}

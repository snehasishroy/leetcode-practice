import java.util.List;

/**
 * <pre>
 * https://leetcode.com/problems/nested-list-weight-sum-ii/ Premium
 *
 * You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements may also be integers or other lists.
 *
 * The depth of an integer is the number of lists that it is inside of. For example, the nested list [1,[2,2],[[3],2],1] has each integer's value set to its depth.
 * Let maxDepth be the maximum depth of any integer.
 *
 * The weight of an integer is maxDepth - (the depth of the integer) + 1.
 *
 * Return the sum of each integer in nestedList multiplied by its weight.
 *
 * Input: nestedList = [[1,1],2,[1,1]]
 * Output: 8
 * Explanation: Four 1's with a weight of 1, one 2 with a weight of 2.
 * 1*1 + 1*1 + 2*2 + 1*1 + 1*1 = 8
 *
 * Input: nestedList = [1,[4,[6]]]
 * Output: 17
 * Explanation: One 1 at depth 3, one 4 at depth 2, and one 6 at depth 1.
 * 1*3 + 4*2 + 6*1 = 17
 *
 * Constraints:
 * 1 <= nestedList.length <= 50
 * The values of the integers in the nested list is in the range [-100, 100].
 * The maximum depth of any integer is less than or equal to 50.
 * </pre>
 */
public class NestedListWeightSum2 {
    /**
     * Approach: 2 Pass DFS solution, first pass to get the maximum depth, second pass to calculate the weight sum as per the formula
     * <p>
     * {@link NestedListWeightSum}
     */
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int maxDepth = getMaxDepth(nestedList);
        return getWeightSum(nestedList, maxDepth, 1);
    }

    private int getWeightSum(List<NestedInteger> nestedList, int maxDepth, int curDepth) {
        int weightSum = 0;
        for (NestedInteger item : nestedList) {
            if (!item.isInteger()) {
                weightSum += getWeightSum(item.getList(), maxDepth, curDepth + 1);
            } else {
                weightSum += ((maxDepth - curDepth + 1) * item.getInteger());
            }
        }
        return weightSum;
    }

    private int getMaxDepth(List<NestedInteger> nestedList) {
        int maxDepth = 1;
        for (NestedInteger item : nestedList) {
            if (!item.isInteger()) {
                maxDepth = Math.max(maxDepth, 1 + getMaxDepth(item.getList()));
            }
        }
        return maxDepth;
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

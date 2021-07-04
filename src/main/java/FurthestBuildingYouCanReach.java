import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/furthest-building-you-can-reach/
 * <p>
 * You are given an integer array heights representing the heights of buildings, some bricks, and some ladders.
 * <p>
 * You start your journey from building 0 and move to the next building by possibly using bricks or ladders.
 * <p>
 * While moving from building i to building i+1 (0-indexed),
 * <p>
 * If the current building's height is greater than or equal to the next building's height, you do not need a ladder or bricks.
 * If the current building's height is less than the next building's height, you can either use one ladder or (h[i+1] - h[i]) bricks.
 * Return the furthest building index (0-indexed) you can reach if you use the given ladders and bricks optimally.
 * <p>
 * Input: heights = [4,2,7,6,9,14,12], bricks = 5, ladders = 1
 * Output: 4
 * Explanation: Starting at building 0, you can follow these steps:
 * - Go to building 1 without using ladders nor bricks since 4 >= 2.
 * - Go to building 2 using 5 bricks. You must use either bricks or ladders because 2 < 7.
 * - Go to building 3 without using ladders nor bricks since 7 >= 6.
 * - Go to building 4 using your only ladder. You must use either bricks or ladders because 6 < 9.
 * It is impossible to go beyond building 4 because you do not have any more bricks or ladders.
 * <p>
 * Input: heights = [4,12,2,7,3,18,20,3,19], bricks = 10, ladders = 2
 * Output: 7
 * <p>
 * Input: heights = [14,3,19,3], bricks = 17, ladders = 0
 * Output: 3
 * <p>
 * Constraints:
 * 1 <= heights.length <= 105
 * 1 <= heights[i] <= 106
 * 0 <= bricks <= 109
 * 0 <= ladders <= heights.length
 */
public class FurthestBuildingYouCanReach {
    /**
     * Approach: The large constraints discourage the use of memoization or backtracking, so only greedy seems viable option
     * The trick is to go back in time and correct the mistake, rather than always making correct choices.
     * Since ladders here is very useful as it can cover infinite distances, we must use it while jumping over the largest
     * distances so that we get the largest amount of bricks back
     * Time Complexity: nlogn
     * <p>
     * In my initial implementation, I tried to exhaust all the bricks first and then used ladders iteratively, this gave WA
     * <p>
     * Another way to solve this would be to leverage binary search to find max reachable index
     * An index is reachable if we keep track of all the jumps required and then sort them in ascending order.
     * Then we try to use bricks on the smaller jumps until we can. We try to use ladders for the bigger jumps.
     * If we can reach the end index using the available bricks and ladders, we are done.
     * We then try to increase the index, if index is unreachable, we decrement the index
     * <p>
     * TimeComplexity: logn * O(nlogn)
     * {@link GasStations} {@link MinNumberOfTapsToOpenToWaterGarden} {@link EliminateMaximumNumberOfMonsters} related tricky greedy problems
     */
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        int index = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder()); //stores the bricks used during jumping in descending order
        while (index < heights.length - 1) {
            int diff = heights[index + 1] - heights[index];
            if (diff > 0) { //if we have to jump
                if (bricks >= diff) { //if we have more bricks available than required jump, simply use bricks
                    bricks -= diff;
                    pq.add(diff); //update the pq
                } else if (ladders > 0) { // if we don't have bricks but we have ladders
                    ladders--; //use one ladder at the place which will give the max no of bricks back
                    //where to put ladder, options are either at current place or at some previous place
                    pq.add(diff); //critical to add this also to pq, it indicates placing it at current place
                    bricks -= diff;
                    int largestBricksUsed = pq.remove(); //place the ladder at the position which gives the largest amount of bricks back
                    //largestBricksUsed can either be current diff or some previous diff, hence we have to push diff first
                    bricks += largestBricksUsed; //increment the bricks available
                } else { //neither we have bricks nor we have ladders
                    break;
                }
            }
            index++;
        }
        return index;
    }
}

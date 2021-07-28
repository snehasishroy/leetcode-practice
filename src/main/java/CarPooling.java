import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/car-pooling/
 * <p>
 * You are driving a vehicle that has capacity empty seats initially available for passengers.  The vehicle only drives east (ie. it cannot turn around and drive west.)
 * <p>
 * Given a list of trips, trip[i] = [num_passengers, start_location, end_location] contains information about the i-th trip:
 * the number of passengers that must be picked up, and the locations to pick them up and drop them off.
 * The locations are given as the number of kilometers due east from your vehicle's initial location.
 * <p>
 * Return true if and only if it is possible to pick up and drop off all passengers for all the given trips.
 * <p>
 * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
 * Output: false
 */
public class CarPooling {

    /**
     * {@link MeetingsRoom2} is similar problem
     * Approach: Greedy, keep track of the persons hopping in and dropping out at any moment, current capacity should not exceed max capacity
     * <p>
     * Solved it on my own this time, it's very critical to understand that if you copy the solution of other users without understanding why they used
     * a specific DS, you won't be able to solve similar questions in the future.
     * <p>
     * I tried to solve MeetingsRoom2 after this problem, couldn't solve it without using counting sort as used in this approach because I could not
     * understand why the author used PriorityQueue instead of just comparing it with the previous interval
     *
     * {@link DescribeThePainting} {@link EmployeeFreeTime}
     */
    public boolean carPooling(int[][] trips, int capacity) {
        int[] count = new int[1001]; //max distance of the trip is 1000
        for (int[] trip : trips) {
            count[trip[1]] += trip[0]; //person hopping in
            count[trip[2]] -= trip[0]; //person dropping out
        }
        int currentCapacity = 0;
        for (int cnt : count) {
            currentCapacity += cnt;
            if (currentCapacity > capacity) {
                return false;
            }
        }
        return true;
    }

    /**
     * Previous solution used a array to keep track of people coming in and out but it's not scalable for long sized input.
     * We can use map to do the counting.
     */
    public boolean carPoolingGeneral(int[][] trips, int capacity) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int[] trip : trips) {
            map.put(trip[1], map.getOrDefault(trip[1], 0) + trip[0]);
            map.put(trip[2], map.getOrDefault(trip[2], 0) - trip[0]);
        }
        int currentCapacity = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            currentCapacity += entry.getValue();
            if (currentCapacity > capacity) {
                return false;
            }
        }
        return true;
    }
}

import java.util.Arrays;

/**
 * https://leetcode.com/problems/car-fleet/solution/
 * <p>
 * N cars are going to the same destination along a one lane road.  The destination is target miles away.
 * <p>
 * Each car i has a constant speed speed[i] (in miles per hour), and initial position position[i] miles towards the target along the road.
 * <p>
 * A car can never pass another car ahead of it, but it can catch up to it, and drive bumper to bumper at the same speed.
 * <p>
 * The distance between these two cars is ignored - they are assumed to have the same position.
 * <p>
 * A car fleet is some non-empty set of cars driving at the same position and same speed.  Note that a single car is also a car fleet.
 * <p>
 * If a car catches up to a car fleet right at the destination point, it will still be considered as one car fleet.
 * <p>
 * How many car fleets will arrive at the destination?
 * <p>
 * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
 * Output: 3
 * Explanation:
 * The cars starting at 10 and 8 become a fleet, meeting each other at 12.
 * The car starting at 0 doesn't catch up to any other car, so it is a fleet by itself.
 * The cars starting at 5 and 3 become a fleet, meeting each other at 6.
 * Note that no other cars meet these fleets before the destination, so the answer is 3.
 * <p>
 * Note:
 * 0 <= N <= 10 ^ 4
 * 0 < target <= 10 ^ 6
 * 0 < speed[i] <= 10 ^ 6
 * 0 <= position[i] < target
 * All initial positions are different.
 */
public class CarFleet {
    /**
     * Approach: Tricky greedy solution, keep track of time required by each car. If a car starts at a later position and it requires 6 sec to reach target,
     * if a car starting before it takes 3 sec to reach target, they will become part of the same fleet.
     * If the car starting before it took 10 sec to reach target, it can never catch it and will have to form it's own fleet.
     * <p>
     * I was able to come up with the idea that it requires sorting by position but I was not able to come up with the idea of how to count fleets
     * <p>
     * {@link FurthestBuildingYouCanReach} {@link MergeTripletsToFormTargetTriplet} {@link EliminateMaximumNumberOfMonsters}
     * {@link ReconstructA2RowBinaryMatrix} {@link LongestUncommonSubsequence2} related tricky greedy problems
     */
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        Car[] cars = new Car[n];
        for (int i = 0; i < n; i++) {
            cars[i] = new Car(position[i], (target - position[i]) / (double) speed[i]);
        }
        Arrays.sort(cars, (o1, o2) -> Integer.compare(o1.position, o2.position)); //sort by position
        int fleets = 0;
        double currentSlowestCar = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (cars[i].time > currentSlowestCar) {
                //if current car takes more time than the slowest car of previous fleet, it can't merge with the previous fleet
                //and it has to start it's own fleet
                fleets++;
                currentSlowestCar = cars[i].time;
            }
        }
        return fleets;
    }

    private static class Car {
        int position;
        double time;

        public Car(int position, double time) {
            this.position = position;
            this.time = time;
        }
    }
}

import common.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/employee-free-time/
 * <p>
 * We are given a list schedule of employees, which represents the working time for each employee.
 * <p>
 * Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.
 * <p>
 * Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.
 * <p>
 * (Even though we are representing Intervals in the form [x, y], the objects inside are Intervals, not lists or arrays.
 * For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and schedule[0][0][0] is not defined).
 * Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.
 * <p>
 * Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
 * Output: [[3,4]]
 * Explanation: There are a total of three employees, and all common
 * free time intervals would be [-inf, 1], [3, 4], [10, inf].
 * We discard any intervals that contain inf as they aren't finite.
 * <p>
 * Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
 * Output: [[5,6],[7,9]]
 */
public class EmployeeFreeTime {
    /**
     * Approach: Prefix sum, Range Interval Problem, Line sweep algorithm by incrementing/decrementing a counter similar to {@link MeetingsRoom2}
     * {@link RemoveInterval}
     * <p>
     * I have to see the hint to implement the problem, Initially I was thinking of maintaining a list of free time for each employee and then
     * gradually comparing each employees free time to filter the common free time for all of them, it was a very complicated implementation.
     * TimeComplexity: nlogn, n is the total number of intervals
     * <p>
     * RunTime can be reduced if you use priority queue and push all the intervals to pq, and merge overlapping intervals.
     * Check if the previous interval and current interval are disjoint, if yes, the gap is the free interval
     * Refer to the priority queue solution in {@link IntervalListIntersections}
     *
     * {@link DescribeThePainting}
     */
    public List<Interval> employeeFreeTimeUsingLineSweep(List<List<Interval>> schedule) {
        TreeMap<Integer, Integer> map = new TreeMap<>(); //mapping of time -> counter (+1, -1)
        for (List<Interval> intervals : schedule) {
            for (Interval interval : intervals) {
                map.put(interval.start, map.getOrDefault(interval.start, 0) + 1);
                map.put(interval.end, map.getOrDefault(interval.end, 0) - 1);
            }
        }
        ArrayList<Interval> result = new ArrayList<>();
        int start = -1, runningCounter = 0;
        for (Map.Entry<Integer, Integer> entrySet : map.entrySet()) {
            if (start != -1) {
                //if start of free time has been identified, this represent the end of common free time
                //all good things must come to an end
                result.add(new Interval(start, entrySet.getKey()));
                start = -1;
            }
            runningCounter += entrySet.getValue();
            if (runningCounter == 0) {
                //running counter of 0 indicates that no one is working at this time, so everyone is free, need to mark the start of the free time
                start = entrySet.getKey();
            }
        }
        return result;
    }
}

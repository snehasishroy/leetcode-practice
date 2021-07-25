import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/meeting-rooms-ii/ Premium
 *
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * find the minimum number of conference rooms required.
 *
 * Input: intervals = [[0,30],[5,10],[15,20]]
 * Output: 2
 *
 * Input: intervals = [[7,10],[2,4]]
 * Output: 1
 *
 * Constraints:
 * 1 <= intervals.length <= 10^4
 * 0 <= start_i < end_i <= 10^6
 */
public class MeetingsRoom2 {

    /**
     * Approach: It's critical to understand why we chose priority queue instead of simply comparing the start time of current interval with
     * end time of previous interval e.g {2,8},{5,15},{10,20}
     * we need to remove all intervals ending before the start of current interval, we can do that by priority queue.
     * <p>
     * {@link CarPooling} {@link TheNumberOfTheSmallestUnoccupiedChair}
     */
    public int minMeetingRoomsPriorityQueue(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> Integer.compare(o1[0], o2[0])); //sort the array based on start time
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
        //sort the pq based on end time, as we are interested in finding meetings that end before the current meeting starts
        int maxRooms = 0;
        for (int i = 0; i < intervals.length; ++i) {
            while (!pq.isEmpty() && pq.peek()[1] <= intervals[i][0]) {
                //if the current interval starts after any finished meeting, remove the finished meeting
                pq.poll();
            }
            pq.add(intervals[i]);
            maxRooms = Math.max(maxRooms, pq.size());
        }
        return maxRooms;
    }

    /**
     * Approach: Line Sweep algorithm, very commonly used for interval related problems
     * <p>
     * {@link EmployeeFreeTime} {@link IntervalListIntersections} related problems
     */
    public int minMeetingRoomsLineSweep(int[][] intervals) {
        int max = 0, cur = 0;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int[] interval : intervals) {
            map.put(interval[0], map.getOrDefault(interval[0], 0) + 1);
            map.put(interval[1], map.getOrDefault(interval[1], 0) - 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            cur += entry.getValue();
            max = Math.max(cur, max);
        }
        return max;
    }
}

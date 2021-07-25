import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * <pre>
 * https://leetcode.com/problems/the-number-of-the-smallest-unoccupied-chair/
 *
 * There is a party where n friends numbered from 0 to n - 1 are attending. There is an infinite number of chairs in this party that are numbered from 0 to infinity.
 * When a friend arrives at the party, they sit on the unoccupied chair with the smallest number.
 *
 * For example, if chairs 0, 1, and 5 are occupied when a friend comes, they will sit on chair number 2.
 * When a friend leaves the party, their chair becomes unoccupied at the moment they leave. If another friend arrives at that same moment, they can sit in that chair.
 *
 * You are given a 0-indexed 2D integer array times where times[i] = [arrival_i, leaving_i], indicating the arrival and leaving times of the ith friend respectively,
 * and an integer targetFriend. All arrival times are distinct.
 *
 * Return the chair number that the friend numbered targetFriend will sit on.
 *
 * Input: times = [[1,4],[2,3],[4,6]], targetFriend = 1
 * Output: 1
 * Explanation:
 * - Friend 0 arrives at time 1 and sits on chair 0.
 * - Friend 1 arrives at time 2 and sits on chair 1.
 * - Friend 1 leaves at time 3 and chair 1 becomes empty.
 * - Friend 0 leaves at time 4 and chair 0 becomes empty.
 * - Friend 2 arrives at time 4 and sits on chair 0.
 * Since friend 1 sat on chair 1, we return 1.
 *
 * Input: times = [[3,10],[1,5],[2,6]], targetFriend = 0
 * Output: 2
 * Explanation:
 * - Friend 1 arrives at time 1 and sits on chair 0.
 * - Friend 2 arrives at time 2 and sits on chair 1.
 * - Friend 0 arrives at time 3 and sits on chair 2.
 * - Friend 1 leaves at time 5 and chair 0 becomes empty.
 * - Friend 2 leaves at time 6 and chair 1 becomes empty.
 * - Friend 0 leaves at time 10 and chair 2 becomes empty.
 * Since friend 0 sat on chair 2, we return 2.
 *
 * Constraints:
 * n == times.length
 * 2 <= n <= 10^4
 * times[i].length == 2
 * 1 <= arrival_i < leaving_i <= 10^5
 * 0 <= targetFriend <= n - 1
 * Each arrival_i time is distinct.
 * </pre>
 */
public class TheNumberOfTheSmallestUnoccupiedChair {
    /**
     * Approach: Greedy, Tricky range interval problem. This seems to be a combination of two problems.
     * {@link MeetingsRoom2} for finding the people that leaves before current person arrives
     * and {@link SeatReservationManager} for assigning the smallest seat possible
     *
     * During the contest I remember that I solved similar seat allocation problem before, so I search for "Seat" and voila, I found
     * the related problem. Got a WA because I was not doing proper seat allocation, still happy to solve it during the contest.
     */
    public int smallestChair(int[][] times, int targetFriend) {
        int n = times.length;
        Node[] nodes = new Node[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new Node(times[i][0], times[i][1], i, 0);
        }
        TreeSet<Integer> availableSeats = new TreeSet<>(); //ordered set of all the seats available, priority queue can also be used
        for (int i = 0; i <= n; i++) {
            availableSeats.add(i);
        }
        Arrays.sort(nodes, (o1, o2) -> Integer.compare(o1.start, o2.start)); //sort the array by start time
        PriorityQueue<Node> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.end, o2.end));
        //pq is sorted by end time, as we are interested in finding which persons will leave before the current person arrives
        for (int i = 0; i < n; i++) {
            while (!pq.isEmpty() && pq.peek().end <= nodes[i].start) {
                int freedSeat = pq.poll().seat; //if the person leaves before the current person arrives, we can reuse his seat.
                availableSeats.add(freedSeat);
            }
            //find the first smallest seat available that can be assigned
            int availableSeat = availableSeats.first();
            availableSeats.remove(availableSeat); //block it from being further assigned
            if (nodes[i].index == targetFriend) {
                return availableSeat;
            }
            nodes[i].seat = availableSeat;
            pq.add(nodes[i]);
        }
        return -1;
    }

    private static class Node {
        int start;
        int end;
        int index;
        int seat;

        public Node(int start, int end, int index, int seat) {
            this.start = start;
            this.end = end;
            this.index = index;
            this.seat = seat;
        }
    }
}

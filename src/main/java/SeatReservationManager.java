import java.util.TreeSet;

/**
 * https://leetcode.com/problems/seat-reservation-manager/
 *
 * <pre>
 * Design a system that manages the reservation state of n seats that are numbered from 1 to n.
 *
 * Implement the SeatManager class:
 *
 * SeatManager(int n) Initializes a SeatManager object that will manage n seats numbered from 1 to n. All seats are initially available.
 * int reserve() Fetches the smallest-numbered unreserved seat, reserves it, and returns its number.
 * void unreserve(int seatNumber) Unreserves the seat with the given seatNumber.
 *
 * Input
 * ["SeatManager", "reserve", "reserve", "unreserve", "reserve", "reserve", "reserve", "reserve", "unreserve"]
 * [[5], [], [], [2], [], [], [], [], [5]]
 * Output
 * [null, 1, 2, null, 2, 3, 4, 5, null]
 *
 * Explanation
 * SeatManager seatManager = new SeatManager(5); // Initializes a SeatManager with 5 seats.
 * seatManager.reserve();    // All seats are available, so return the lowest numbered seat, which is 1.
 * seatManager.reserve();    // The available seats are [2,3,4,5], so return the lowest of them, which is 2.
 * seatManager.unreserve(2); // Unreserve seat 2, so now the available seats are [2,3,4,5].
 * seatManager.reserve();    // The available seats are [2,3,4,5], so return the lowest of them, which is 2.
 * seatManager.reserve();    // The available seats are [3,4,5], so return the lowest of them, which is 3.
 * seatManager.reserve();    // The available seats are [4,5], so return the lowest of them, which is 4.
 * seatManager.reserve();    // The only available seat is seat 5, so return 5.
 * seatManager.unreserve(5); // Unreserve seat 5, so now the available seats are [5].
 *
 * Constraints:
 * 1 <= n <= 10^5
 * 1 <= seatNumber <= n
 * For each call to reserve, it is guaranteed that there will be at least one unreserved seat.
 * For each call to unreserve, it is guaranteed that seatNumber will be reserved.
 * At most 10^5 calls in total will be made to reserve and unreserve.
 * </pre>
 */
public class SeatReservationManager {
    TreeSet<Integer> set = new TreeSet<>();

    /**
     * Approach: Maintain an ordered set containing all the available seats for reservation. When asked to reserve, remove the
     * smallest element from set in O(logn). Similarly when asked to unreserve, add back the seat in the set in O(logn)
     * <p>
     * {@link DesignPhoneDirectory} {@link ExamRoom} {@link MaximiseDistanceToClosestPerson} {@link TheNumberOfTheSmallestUnoccupiedChair}
     */
    public SeatReservationManager(int n) {
        for (int i = 1; i <= n; i++) {
            set.add(i);
        }
    }

    public int reserve() {
        int val = set.first();
        set.remove(val);
        return val;
    }

    public void unreserve(int seatNumber) {
        set.add(seatNumber);
    }
}

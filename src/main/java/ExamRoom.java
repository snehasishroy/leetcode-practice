import java.util.TreeSet;

/**
 * https://leetcode.com/problems/exam-room/submissions/
 * <p>
 * In an exam room, there are N seats in a single row, numbered 0, 1, 2, ..., N-1.
 * <p>
 * When a student enters the room, they must sit in the seat that maximizes the distance to the closest person.
 * If there are multiple such seats, they sit in the seat with the lowest number.  (Also, if no one is in the room, then the student sits at seat number 0.)
 * <p>
 * Return a class ExamRoom(int N) that exposes two functions: ExamRoom.seat() returning an int representing what seat the student sat in,
 * and ExamRoom.leave(int p) representing that the student in seat number p now leaves the room.
 * It is guaranteed that any calls to ExamRoom.leave(p) have a student sitting in seat p.
 * <p>
 * Input: ["ExamRoom","seat","seat","seat","seat","leave","seat"], [[10],[],[],[],[],[4],[]]
 * Output: [null,0,9,4,2,null,5]
 * Explanation:
 * ExamRoom(10) -> null
 * seat() -> 0, no one is in the room, then the student sits at seat number 0.
 * seat() -> 9, the student sits at the last seat number 9.
 * seat() -> 4, the student sits at the last seat number 4.
 * seat() -> 2, the student sits at the last seat number 2.
 * leave(4) -> null
 * seat() -> 5, the student sits at the last seat number 5.
 */
public class ExamRoom {
    TreeSet<Integer> set = new TreeSet<>();
    int N;

    public ExamRoom(int N) {
        this.N = N;
    }

    /**
     * Approach: Keep track of the occupied seats by placing the occupied seats in a treeset.
     * TreeSet will allow them to be quickly find occupied seats even if we remove any arbitrarily seats.
     * <p>
     * When asked to find the best unoccupied seat, it would be the middle between two occupied seats. Keep track of the max difference
     * between two occupied seat by iterating over all occupied seats.
     * <p>
     * Two edge cases to consider: The distance between 0 and first occupied seat and distance between n-1 and last occupied seat.
     *
     * {@link TheNumberOfTheSmallestUnoccupiedChair} {@link SeatReservationManager}
     */
    public int seat() {
        if (set.isEmpty()) {
            set.add(0);
            return 0;
        } else {
            int prev = set.first();
            int maxGap = (prev - 0); //distance between first occupied seat and 0
            int index = 0;
            for (int current : set) {
                int currentGap = (current - prev) / 2; //difference between two occupied seats
                if (currentGap > maxGap) { //found a bigger gap
                    maxGap = currentGap;
                    index = prev + currentGap; //update the index
                }
                prev = current;
            }
            if ((N - 1 - prev) > maxGap) { //distance between last occupied seat and n-1
                index = N - 1;
            }
            set.add(index);
            return index;
        }
    }

    public void leave(int p) {
        set.remove(p);
    }
}

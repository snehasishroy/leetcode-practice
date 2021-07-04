import java.util.Arrays;

/**
 * <pre>
 * https://leetcode.com/problems/online-election/
 *
 * In an election, the i-th vote was cast for persons[i] at time times[i].
 *
 * Now, we would like to implement the following query function: TopVotedCandidate.q(int t) will return the number of the person that was leading the election at time t.
 *
 * Votes cast at time t will count towards our query.  In the case of a tie, the most recent vote (among tied candidates) wins.
 *
 * Input: ["TopVotedCandidate","q","q","q","q","q","q"], [[[0,1,1,0,0,1,0],[0,5,10,15,20,25,30]],[3],[12],[25],[15],[24],[8]]
 * Output: [null,0,1,1,0,0,1]
 * Explanation:
 * At time 3, the votes are [0], and 0 is leading.
 * At time 12, the votes are [0,1,1], and 1 is leading.
 * At time 25, the votes are [0,1,1,0,0,1], and 1 is leading (as ties go to the most recent vote.)
 * This continues for 3 more queries at time 15, 24, and 8.
 *
 * Note:
 * 1 <= persons.length = times.length <= 5000
 * 0 <= persons[i] <= persons.length
 * times is a strictly increasing array with all elements in [0, 10^9].
 * TopVotedCandidate.q is called at most 10000 times per test case.
 * TopVotedCandidate.q(int t) is always called with t >= times[0].
 * </pre>
 */
public class OnlineElection {
    /**
     * Approach: Binary Search, Maintain the current leader at times[i], now for a given time, perform binary search on times[]
     * to find an index with a value >= t
     */
    int[] currentLeader; //currentLeader[i] will indicate leader at times[i] time
    int[] times;

    public OnlineElection(int[] persons, int[] times) {
        int n = persons.length;
        currentLeader = new int[n];
        int[] votes = new int[n];
        int leaderId = 0, leaderVotes = 0;
        for (int i = 0; i < n; i++) {
            votes[persons[i]]++; //count vote
            if (votes[persons[i]] >= leaderVotes) { // = because in case of tie, the most recent vote wins
                leaderVotes = votes[persons[i]];
                leaderId = persons[i]; //found a new leader
            }
            currentLeader[i] = leaderId; //mark the current leader at times[i] time
        }
        this.times = times;
    }

    public int q(int t) {
        int index = Arrays.binarySearch(times, t);
        if (index < 0) {
            index *= -1;
            index -= 1;
        }
        int n = times.length;
        if (index == n) { //if t > all the existing time
            return currentLeader[n - 1];
        } else if (times[index] == t) { //in case t exists in times[]
            return currentLeader[index];
        } else { //t does not exists, return the leader at the previous recorded time.
            return currentLeader[index - 1];
        }
    }
}

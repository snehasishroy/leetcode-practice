import java.util.Arrays;

/**
 * <pre>
 * There is a survey that consists of n questions where each question's answer is either 0 (no) or 1 (yes).
 *
 * The survey was given to m students numbered from 0 to m - 1 and m mentors numbered from 0 to m - 1.
 * The answers of the students are represented by a 2D integer array students where students[i] is an integer array that contains the answers of the ith student (0-indexed).
 * The answers of the mentors are represented by a 2D integer array mentors where mentors[j] is an integer array that contains the answers of the jth mentor (0-indexed).
 *
 * Each student will be assigned to one mentor, and each mentor will have one student assigned to them.
 * The compatibility score of a student-mentor pair is the number of answers that are the same for both the student and the mentor.
 *
 * For example, if the student's answers were [1, 0, 1] and the mentor's answers were [0, 0, 1], then their compatibility score is 2 because only the second and the third answers are the same.
 * You are tasked with finding the optimal student-mentor pairings to maximize the sum of the compatibility scores.
 *
 * Given students and mentors, return the maximum compatibility score sum that can be achieved.
 *
 * Input: students = [[1,1,0],[1,0,1],[0,0,1]], mentors = [[1,0,0],[0,0,1],[1,1,0]]
 * Output: 8
 * Explanation: We assign students to mentors in the following way:
 * - student 0 to mentor 2 with a compatibility score of 3.
 * - student 1 to mentor 0 with a compatibility score of 2.
 * - student 2 to mentor 1 with a compatibility score of 3.
 * The compatibility score sum is 3 + 2 + 3 = 8.
 *
 * Input: students = [[0,0],[0,0],[0,0]], mentors = [[1,1],[1,1],[1,1]]
 * Output: 0
 * Explanation: The compatibility score of any student-mentor pair is 0.
 *
 * Constraints:
 * m == students.length == mentors.length
 * n == students[i].length == mentors[j].length
 * 1 <= m, n <= 8
 * students[i][k] is either 0 or 1.
 * mentors[j][k] is either 0 or 1.
 * </pre>
 */
public class MaximumCompatibilityScoreSum {
    /**
     * Approach: Backtracking, Assignment problem, try to generate all permutations of assignment and check the maximum score achievable.
     * TimeComplexity : O(n!) Since n <= 8, it works.
     * During the contest, I solved it using greedy algorithm by generating all pair wise scores and pushing it in a priority queue.
     * This approach gave WA for some of the test cases https://leetcode.com/problems/maximum-compatibility-score-sum/discuss/1360885/why-greedy-give-wrong-answer
     * <p>
     * I applied greedy because I applied the same trick in {@link CampusBikes}. During the contest, then I looked at the constraint and decided to brute force it.
     * Eventually it passed but it left me confused because CampusBikes is a similar assignment problem and if greedy worked there, it should have worked here as well.
     * Then upon looking in discuss, found that a person gave a counter example of where greedy fails in {@link CampusBikes}.
     * Have raised an issue with LeetCode, most likely their whole approach is wrong.
     */
    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        int m = students.length;
        int[][] scores = getAllScores(students, mentors);
        return recur(0, m, scores, new boolean[m]);
    }

    private int recur(int studentId, int max, int[][] scores, boolean[] isMentorAssigned) {
        if (studentId == max) {
            return 0;
        }
        int score = 0;
        for (int i = 0; i < max; i++) { //try assigning this student to all mentors one by one and keep track of max score possible
            if (!isMentorAssigned[i]) {
                isMentorAssigned[i] = true;
                int curScore = scores[studentId][i] + recur(studentId + 1, max, scores, isMentorAssigned);
                score = Math.max(score, curScore);
                isMentorAssigned[i] = false; //backtrack
            }
        }
        return score;
    }

    private int[][] getAllScores(int[][] students, int[][] mentors) {
        int m = students.length;
        int n = students[0].length;
        int[][] scores = new int[m][m]; //compatibility score of {student_id, mentor_id}
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                int score = 0;
                for (int k = 0; k < n; k++) {
                    if (students[i][k] == mentors[j][k]) {
                        score++;
                    }
                }
                scores[i][j] = score;
            }
        }
        return scores;
    }

    /**
     * Approach: DP + Bitmasking, Since n <= 8, we can represent them in an int.
     *
     * After reading some posts in discuss, realized that we don't need to have a 2D DP to represent all states, int[] mask is sufficient
     * Still not able to fully prove it, but after doing some dry runs on paper, seems like it.
     * Another example of DP State optimization
     */
    public int maxCompatibilitySumBitMasking(int[][] students, int[][] mentors) {
        int m = students.length;
        int[][] scores = getAllScores(students, mentors);
        int[][] dp = new int[m][(1 << m) + 1];
        for (int[] rows : dp) {
            Arrays.fill(rows, -1);
        }
        return recur(0, m, scores, 0, dp);
    }

    private int recur(int studentId, int max, int[][] scores, int mask, int[][] dp) {
        if (studentId == max) {
            return 0;
        } else if (dp[studentId][mask] != -1) {
            return dp[studentId][mask];
        }
        int score = 0;
        for (int i = 0; i < max; i++) { //try assigning this student to all mentors one by one and keep track of max score possible
            if ((mask & (1 << i)) == 0) {
                int curScore = scores[studentId][i] + recur(studentId + 1, max, scores, mask | (1 << i), dp);
                score = Math.max(score, curScore);
            }
        }
        return dp[studentId][mask] = score;
    }
}
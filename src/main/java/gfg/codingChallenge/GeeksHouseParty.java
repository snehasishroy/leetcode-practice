package gfg.codingChallenge;

import java.util.ArrayList;

/**
 * <pre>
 * Geek organized a house party to which he invited n friends.
 * The waiter at the party was strictly instructed that the amount of drink in each friend's glass should be exactly same and bottle containing m litres of drink should be empty after he had served all his friends.
 * The waiter was all set to take on the task but he had a problem. Before he started serving, all the friends had some amount of drink in their glasses already.
 * Formally speaking, ith friend had arr[i] amount of drink already in his glass.
 *
 * Can you help the waiter find out if it is possible for him to distribute the drink to the guests in accordance with the conditions set by Geek.
 * If it is possible then find the amount of drink that needs to  be served to each of his friend (correct upto 6 decimal places), otherwise return list containing only on element ie (-1.000000)
 *
 * Input:
 * n = 5 , m = 50
 * arr[] = {1, 2, 3, 4, 5}
 * Output: {12.000000, 11.000000, 10.000000,
 * 9.000000, 8.000000}
 * Explanation: The waiter needs to distribute 50 litres of drink to the guests. After he is done, all glasses will contain 13 litres of drink each.
 *
 * Input:
 * n = 2 , m = 2
 * arr[] = {1, 100}
 * Output: {-1.000000}
 * Explanation: It is not possible to fill equal amount in all glasses.
 * </pre>
 */
public class GeeksHouseParty {
    /**
     * Approach: Greedy, In the end, all the people should have same amount of drink, so keep track of the existing drink present and the drink that needs
     * to be distributed. Every people must have (Total amount of drink / no of peoples) drink.
     * <p>
     * However if any person already has a drink > target quantity, we can't achieve the desired target.
     */
    ArrayList<Double> equalFill(int n, int m, int[] arr) {
        long sum = 0;
        int max = 0;
        for (int val : arr) {
            sum += val;
            max = Math.max(max, val);
        }
        long total = sum + m;
        double target = (double) total / n;
        if (target < max) {
            ArrayList<Double> res = new ArrayList<>();
            res.add(-1D);
            return res;
        } else {
            ArrayList<Double> res = new ArrayList<>();
            for (int val : arr) {
                res.add(target - val);
            }
            return res;
        }
    }
}

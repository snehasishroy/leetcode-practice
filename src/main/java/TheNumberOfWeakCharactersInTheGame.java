import java.util.Arrays;

/**
 * <pre>
 * https://leetcode.com/problems/the-number-of-weak-characters-in-the-game/
 *
 * You are playing a game that contains multiple characters, and each of the characters has two main properties: attack and defense.
 * You are given a 2D integer array properties where properties[i] = [attacki, defensei] represents the properties of the ith character in the game.
 *
 * A character is said to be weak if any other character has both attack and defense levels strictly greater than this character's attack and defense levels.
 * More formally, a character i is said to be weak if there exists another character j where attackj > attacki and defensej > defensei.
 *
 * Return the number of weak characters.
 *
 * Input: properties = [[5,5],[6,3],[3,6]]
 * Output: 0
 * Explanation: No character has strictly greater attack and defense than the other.
 *
 * Input: properties = [[2,2],[3,3]]
 * Output: 1
 * Explanation: The first character is weak because the second character has a strictly greater attack and defense.
 *
 * Input: properties = [[1,5],[10,4],[4,3]]
 * Output: 1
 * Explanation: The third character is weak because the second character has a strictly greater attack and defense.
 *
 * Constraints:
 * 2 <= properties.length <= 10^5
 * properties[i].length == 2
 * 1 <= attacki, defensei <= 10^5
 * </pre>
 */
public class TheNumberOfWeakCharactersInTheGame {
    /**
     * Approach: Binary Search + Tricky Greedy + Two Pass, This problem seemed so simple but was a bit tricky to implement.
     *
     * During the contest, I used segment tree instead of a suffix sum array to find the maximum defense between two indices.
     * Later when discussing the problem with Shashwat, I realized that we are not querying max between any two indices but always between [i, n]
     * where i is changing and n is fixed.
     * So we can simply iterate from the end and keep track of the largest defense found between current index and the last index.
     *
     * Now iterate the array and for each attack, find the first index with attack > current attack using binary search.
     * Use the suffix array created in the previous pass to find the max defense between that index and the last element.
     *
     * Still glad to solve this one during the contest :)
     *
     * {@link RussianDollEnvelopes}
     */
    public int numberOfWeakCharacters(int[][] nums) {
        int n = nums.length;
        Arrays.sort(nums, (o1, o2) -> Integer.compare(o1[0], o2[0]));
        int[] maxDefense = new int[n]; //maxDefense[i] indicates the max defense from [i...n]
        int max = 0;
        for (int i = n - 1; i >= 0; i--) { //right to left
            max = Math.max(max, nums[i][1]);
            maxDefense[i] = max;
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            int key = nums[i][0], low = 0, high = n - 1, ans = -1;
            //binary search to find the first index with attack > key
            //F F F T T T T
            //need to find the first T
            while (low <= high) {
                int mid = (low + high) / 2;
                if (nums[mid][0] > key) {
                    ans = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            if (ans == -1) { //no index found with greater attack
                continue;
            }
            max = maxDefense[ans];
            if (max > nums[i][1]) { //check if the defense is greater than current defense
                res++;
            }
        }
        return res;
    }
}
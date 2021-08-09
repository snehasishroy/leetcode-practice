import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/check-array-formation-through-concatenation/
 * <p>
 * You are given an array of distinct integers arr and an array of integer arrays pieces, where the integers in pieces are distinct.
 * Your goal is to form arr by concatenating the arrays in pieces in any order. However, you are not allowed to reorder the integers in each array pieces[i].
 * <p>
 * Return true if it is possible to form the array arr from pieces. Otherwise, return false.
 * <p>
 * Input: arr = [85], pieces = [[85]]
 * Output: true
 * <p>
 * Input: arr = [15,88], pieces = [[88],[15]]
 * Output: true
 * Explanation: Concatenate [15] then [88]
 * <p>
 * Input: arr = [49,18,16], pieces = [[16,18,49]]
 * Output: false
 * Explanation: Even though the numbers match, we cannot reorder pieces[0].
 * <p>
 * Input: arr = [91,4,64,78], pieces = [[78],[4,64],[91]]
 * Output: true
 * Explanation: Concatenate [91] then [4,64] then [78]
 * <p>
 * Input: arr = [1,3,5,7], pieces = [[2,4,6,8]]
 * Output: false
 * <p>
 * Constraints:
 * The integers in arr are distinct.
 * The integers in pieces are distinct (i.e., If we flatten pieces in a 1D array, all the integers in this array are distinct).
 */
public class CheckArrayFormationThroughConcatenation {
    /**
     * Approach: Keep track of leading value of each piece and while iterating the required transformation, check which piece
     * can satisfy the current required value. Once piece is found, need to make sure that the entire piece matches the required values
     * ie. no partial piece allowed. Since the values are distinct, it works !
     * Initially I coded for partial piece using map of integer to linkedlist, got WA :D
     * <p>
     * {@link NumberOfMatchingSubsequences} {@link CheckIfStringIsAPrefixOfArray}
     */
    public boolean canFormArray(int[] arr, int[][] pieces) {
        Map<Integer, int[]> map = new HashMap<>(); //value of the first piece -> entire piece
        for (int[] piece : pieces) {
            map.put(piece[0], piece);
        }
        int index = 0;
        while (index < arr.length) {
            int required = arr[index];
            if (map.containsKey(required)) {
                int[] values = map.get(required);
                for (int value : values) { //need to make sure the entire piece gets fulfilled
                    if (value != arr[index]) { //no need of checking index < arr.length because constraints specify they would be of equal length
                        return false;
                    }
                    index++;
                }
            } else {
                //no piece can satisfy the required value
                return false;
            }
        }
        return true;
    }
}

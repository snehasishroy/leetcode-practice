import java.util.ArrayDeque;

/**
 * https://leetcode.com/problems/validate-stack-sequences/
 * <p>
 * Given two sequences pushed and popped with distinct values, return true if and only if this could have been the result of a sequence of
 * push and pop operations on an initially empty stack.
 * <p>
 * Input: pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
 * Output: true
 * Explanation: We might do the following sequence:
 * push(1), push(2), push(3), push(4), pop() -> 4,
 * push(5), pop() -> 5, pop() -> 3, pop() -> 2, pop() -> 1
 * <p>
 * Input: pushed = [1,2,3,4,5], popped = [4,3,5,1,2]
 * Output: false
 * Explanation: 1 cannot be popped before 2.
 */
public class ValidateStackSequences {
    /**
     * Approach: It's a yes/no question and as ericto mentioned earlier, greedy can work fine for yes/no question
     * So greedily try to check whether the current head of the stack matches the first index that needs to be popped.
     * If yes, keep popping until the constraint satisfies
     * <p>
     * Very happy to solve it on my own
     *
     * {@link VerifyPreorderSerializationOfBinaryTree}
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        if (pushed.length == 0 && popped.length == 0) {
            return true;
        }
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int pushed_index = 0, popped_index = 0;
        while (pushed_index < pushed.length) {
            stack.push(pushed[pushed_index++]);
            while (!stack.isEmpty() && popped_index < popped.length && stack.peek() == popped[popped_index]) {
                //check if the top of the stack can be popped or not
                stack.pop();
                popped_index++;
            }
        }
        return popped_index == popped.length;
    }
}

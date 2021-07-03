/**
 * https://leetcode.com/problems/sentence-screen-fitting/
 * <p>
 * Given a rows x cols screen and a sentence represented by a list of non-empty words, find how many times the given sentence can be fitted on the screen.
 * <p>
 * A word cannot be split into two lines.
 * The order of words in the sentence must remain unchanged.
 * Two consecutive words in a line must be separated by a single space.
 * Total words in the sentence won't exceed 100.
 * Length of each word is greater than 0 and won't exceed 10.
 * 1 ≤ rows, cols ≤ 20,000.
 * <p>
 * Input:
 * rows = 2, cols = 8, sentence = ["hello", "world"]
 * Output:
 * 1
 * Explanation:
 * hello---
 * world---
 * <p>
 * Input:
 * rows = 4, cols = 5, sentence = ["I", "had", "apple", "pie"]
 * Output:
 * 1
 * Explanation:
 * I-had
 * apple
 * pie-I
 * had--
 */
public class SentenceScreenFitting {
    /**
     * Approach: Very tricky optimization but a good learning nevertheless to solve questions related to finding number of repetitions
     * There are multiple tricks involved
     * <p>
     * 1. Imagine the sentence to be infinitely concatenated e.g. if input is {abc, def, ghi} consider the sentence as
     * "abc def ghi abc def ghi abc def ghi......"
     * Now we try to fit this infinite sentence in our rows*cols grid matrix and want to know how many times we can completely fit it
     * <p>
     * 2. For previous sentence the length of one repetition would be 12 (including the last space), now imagine an increasing index
     * that wraps around after reaching the last index, ie. after reaching 11, it would wrap around to 12,13,14,15....
     * If that index is at 14, how many times the sentence has been repeated? 14/12 = 1
     * If that index is at 12, how many times the sentence has been repeated? 12/12 = 1
     * If that index is at 25, how many times the sentence has been repeated? 25/12 = 2
     * <p>
     * 3. Now try to move the index by cols pointer, ie. try to place the max permissible characters on a row.
     * Please note that the index at any time would be pointing to potential start of next row.
     * a. If the character pointed by index is a space, this means that we are trying to start a new row with space, which is non optimal
     * and we can simply skip the index to next char.
     * b. If the character pointed by index is a character, this means that potentially we are trying to split a word into two lines
     * because "abc def", consider index at 3 -> d, it's safe to start new row with d
     * but if index was at 4 -> e, we can place 'd' in the current row, we have to move the index to the start of the current word
     * in order to place the entire word in the next row.
     *
     * {@link FindTheStudentThatWilReplaceTheChalk} {@link StringMatchingInAnArray}
     */
    public int wordsTypingOptimized(String[] sentence, int rows, int cols) {
        for (String word : sentence) { //if any word is longer than the no of columns, we can't fit the sentence
            if (word.length() > cols) {
                return 0;
            }
        }
        String concat = String.join(" ", sentence); //infinite sentence
        concat += " "; //adding space to the end of the last character is also important because new sentence also needs a space
        int current_index = 0; //current_index can wrap around
        int length_of_sentence = concat.length();
        for (int i = 0; i < rows; i++) {
            current_index += cols;
            if (concat.charAt(current_index % length_of_sentence) == ' ') { //% to handle wrap around
                //if trying to start the new row with a space, move the index to the start of the next word
                current_index++;
            } else {
                while (concat.charAt((current_index - 1) % length_of_sentence) != ' ') {
                    //if trying to split a word into two rows, move the index to start of the current word
                    current_index--;
                }
            }
        }
        //since current index can be wrapped around, it can indicate how many times we have completely visited the original sentence
        return current_index / length_of_sentence;
    }

    /**
     * Approach: Greedy brute force, First check if all the words can be safely placed in any row by checking word length
     * Then try to place current word in the current row. If yes increment the current valid column.
     * If no, try placing the word in the next row.
     * Keep track of how many times we can completely place all the words.
     */
    public int wordsTyping(String[] sentence, int rows, int cols) {
        for (String word : sentence) {
            if (word.length() > cols) {
                return 0;
            }
        }
        int cur_row = 0, cur_col = 0;
        int times = 0;
        while (true) {
            for (String word : sentence) {
                if (cur_col + word.length() <= cols) { //if current word can be placed in the current row
                    cur_col += word.length();
                    cur_col += 1; //space required between words present in same row
                } else {
                    if (cur_row + 1 == rows) { //check if new row is not possible, if we are on the last row
                        return times;
                    }
                    cur_row++;
                    cur_col = 0;
                    cur_col += word.length();
                    cur_col += 1; //space required
                }
            }
            times++; //if all words have been placed, increment the count
        }
    }
}

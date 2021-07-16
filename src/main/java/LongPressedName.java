import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * https://leetcode.com/problems/long-pressed-name/
 *
 * Your friend is typing his name into a keyboard. Sometimes, when typing a character c, the key might get long pressed, and the character will be typed 1 or more times.
 *
 * You examine the typed characters of the keyboard. Return True if it is possible that it was your friends name, with some characters (possibly none) being long pressed.
 *
 * Input: name = "alex", typed = "aaleex"
 * Output: true
 * Explanation: 'a' and 'e' in 'alex' were long pressed.
 *
 * Input: name = "saeed", typed = "ssaaedd"
 * Output: false
 * Explanation: 'e' must have been pressed twice, but it wasn't in the typed output.
 *
 * Input: name = "leelee", typed = "lleeelee"
 * Output: true
 *
 * Input: name = "laiden", typed = "laiden"
 * Output: true
 * Explanation: It's not necessary to long press any character.
 *
 * Constraints:
 * 1 <= name.length <= 1000
 * 1 <= typed.length <= 1000
 * name and typed contain only lowercase English letters.
 * </pre>
 */
public class LongPressedName {
    /**
     * Approach: Two pointers, Very similar to {@link IsSubsequence}
     * Compare the characters of name and typed one by one, in case the characters don't match, the character in typed must be a repeated character.
     * If not, the string is not a long pressed version of the name
     *
     * Was not able to think this optimized solution on my own.
     *
     * Time Complexity: O(n) Space Complexity: O(1)
     */
    public boolean isLongPressedNameOptimized(String name, String typed) {
        int nameIndex = 0, typedIndex = 0;
        while (nameIndex < name.length() && typedIndex < typed.length()) {
            if (name.charAt(nameIndex) == typed.charAt(typedIndex)) {
                nameIndex++;
                typedIndex++;
            } else if (typedIndex > 0 && typed.charAt(typedIndex) == typed.charAt(typedIndex - 1)) { //if characters don't match, character at typedIndex ideally should have repeated
                typedIndex++;
            } else {
                return false;
            }
        }
        //if there are unmatched characters in typed, ensure that they are same
        while (typedIndex < typed.length() && typed.charAt(typedIndex) == typed.charAt(typedIndex - 1)) {
            typedIndex++;
        }
        return nameIndex == name.length() && typedIndex == typed.length(); //all characters have been matched
    }

    /**
     * Approach: Get the run length encoding for both the string and compare them at the end by checking the character and its frequency.
     *
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */
    public boolean isLongPressedName(String name, String typed) {
        var nameEncoding = getEncoding(name);
        var typedEncoding = getEncoding(typed);
        if (nameEncoding.size() != typedEncoding.size()) {
            return false;
        } else {
            int index = 0;
            while (index < nameEncoding.size()) {
                char c1 = nameEncoding.get(index).getKey();
                char c2 = typedEncoding.get(index).getKey();
                int freq1 = nameEncoding.get(index).getValue();
                int freq2 = typedEncoding.get(index).getValue();
                if (c1 != c2 || freq1 > freq2) { //mismatch found
                    return false;
                }
                index++;
            }
            return true;
        }
    }

    private List<Pair<Character, Integer>> getEncoding(String word) {
        List<Pair<Character, Integer>> encoding = new ArrayList<>(); //{character, frequency}
        int index = 0, n = word.length();
        while (index < n) {
            char c = word.charAt(index);
            int temp = index;
            while (temp < n && word.charAt(temp) == c) {
                temp++;
            }
            encoding.add(new Pair<>(c, temp - index));
            index = temp;
        }
        return encoding;
    }
}

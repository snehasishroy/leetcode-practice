package gfg.codingChallenge;

/**
 * <pre>
 * Geek has a list of integers and a value key. He is trying to find the mean of
 *
 * The element just less than or equal to the given key and the element just greater than or equal to the given key
 *
 * If either of them does not exist, take the value as -1. Both the values may even be the same.
 * He has recently discovered the concept of Binary Search Tree. He is curious to see if he can use BST to solve this problem. Can you help him?
 *
 * The mean of two numbers x and y is defined as ceil((x+y)/2)
 * For example,
 * if the numbers are 2 and 3 then mean is ceil(5/2) = ceil(2.5) = 3
 * Here ceil(x) denotes the smallest integer that is not smaller than x.
 *
 *      3
 *     /  \
 *    1    5
 * key = 2
 * Output:2
 * Here 1 is just less than 2 and 3 is just greater than 2.
 * Required mean is (1+3)/2 = 2.
 *
 *   2
 *    \
 *     5
 *    /  \
 *  3     7
 * key = 5
 * Output: 5
 * Here we take both keys equal to given key i.e 5 , thus mean = (5 + 5)/2 = 5</span>
 * </pre>
 */
public class MeanBST {
    /**
     * Approach: Recursion, use recursion to find the first node >= key (ceil) and first node <= key (floor)
     * If not found, use -1
     */
    int mean(Node root, int key) {
        int lower = floor(root, key);
        int higher = ceil(root, key);
        return (int) Math.ceil((lower + higher) / 2.0);
    }

    private int floor(Node root, int key) {
        if (root == null) {
            return -1;
        } else if (root.data == key) {
            return root.data;
        } else if (root.data > key) {
            return floor(root.left, key);
        } else {
            //current node's value is smaller than key, so current node is a candidate
            //try getting a larger value that is closer to key, if not found return the current node value
            //if a larger value is found, return it, as it will be more closer to key
            int remaining = floor(root.right, key);
            if (remaining != -1) {
                return remaining;
            } else {
                return root.data;
            }
        }
    }

    private int ceil(Node root, int key) {
        if (root == null) {
            return -1;
        } else if (root.data == key) {
            return root.data;
        } else if (root.data < key) {
            return ceil(root.right, key);
        } else {
            //same logic as above
            int remaining = ceil(root.left, key);
            if (remaining != -1) {
                return remaining;
            } else {
                return root.data;
            }
        }
    }

    private static class Node {
        int data;
        Node left;
        Node right;

        Node(int data) {
            this.data = data;
            left = null;
            right = null;
        }
    }
}

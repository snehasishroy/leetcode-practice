import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * https://leetcode.com/problems/operations-on-tree/
 *
 * You are given a tree with n nodes numbered from 0 to n - 1 in the form of a parent array parent where parent[i] is the parent of the ith node.
 * The root of the tree is node 0, so parent[0] = -1 since it has no parent. You want to design a data structure that allows users to lock, unlock, and upgrade nodes in the tree.
 *
 * The data structure should support the following functions:
 *
 * Lock: Locks the given node for the given user and prevents other users from locking the same node. You may only lock a node if the node is unlocked.
 * Unlock: Unlocks the given node for the given user. You may only unlock a node if it is currently locked by the same user.
 * Upgrade: Locks the given node for the given user and unlocks all of its descendants. You may only upgrade a node if all 3 conditions are true:
 * 1. The node is unlocked,
 * 2. It has at least one locked descendant (by any user), and
 * 3. It does not have any locked ancestors.
 *
 * Implement the LockingTree class:
 * LockingTree(int[] parent) initializes the data structure with the parent array.
 * lock(int num, int user) returns true if it is possible for the user with id user to lock the node num, or false otherwise. If it is possible, the node num will become locked by the user with id user.
 * unlock(int num, int user) returns true if it is possible for the user with id user to unlock the node num, or false otherwise. If it is possible, the node num will become unlocked.
 * upgrade(int num, int user) returns true if it is possible for the user with id user to upgrade the node num, or false otherwise. If it is possible, the node num will be upgraded.
 *
 * Input
 * ["LockingTree", "lock", "unlock", "unlock", "lock", "upgrade", "lock"]
 * [[[-1, 0, 0, 1, 1, 2, 2]], [2, 2], [2, 3], [2, 2], [4, 5], [0, 1], [0, 1]]
 * Output
 * [null, true, false, true, true, true, false]
 *
 * Explanation
 * LockingTree lockingTree = new LockingTree([-1, 0, 0, 1, 1, 2, 2]);
 * lockingTree.lock(2, 2);    // return true because node 2 is unlocked.
 *                            // Node 2 will now be locked by user 2.
 * lockingTree.unlock(2, 3);  // return false because user 3 cannot unlock a node locked by user 2.
 * lockingTree.unlock(2, 2);  // return true because node 2 was previously locked by user 2.
 *                            // Node 2 will now be unlocked.
 * lockingTree.lock(4, 5);    // return true because node 4 is unlocked.
 *                            // Node 4 will now be locked by user 5.
 * lockingTree.upgrade(0, 1); // return true because node 0 is unlocked and has at least one locked descendant (node 4).
 *                            // Node 0 will now be locked by user 1 and node 4 will now be unlocked.
 * lockingTree.lock(0, 1);    // return false because node 0 is already locked.
 *
 * Constraints:
 * n == parent.length
 * 2 <= n <= 2000
 * 0 <= parent[i] <= n - 1 for i != 0
 * parent[0] == -1
 * 0 <= num <= n - 1
 * 1 <= user <= 10^4
 * parent represents a valid tree.
 * At most 2000 calls in total will be made to lock, unlock, and upgrade.
 * </pre>
 */
public class OperationsOnTree {
    int[] lockedBy;
    int[] parent;
    Map<Integer, List<Integer>> tree;

    /**
     * Approach: Ad-hoc implementation, create a tree and keep track of who has locked a node.
     * Upgrade() is the trickiest function to implement, just check what the problem statement wants us to check. At most, we will traverse
     * the entire tree in upgrade()
     * <p>
     * {@link LockBinaryTree}
     */
    public OperationsOnTree(int[] parent) {
        int n = parent.length;
        lockedBy = new int[n];
        this.parent = parent;
        tree = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (parent[i] == -1) {
                continue;
            }
            tree.computeIfAbsent(parent[i], __ -> new ArrayList<>()).add(i);
        }
    }

    public boolean lock(int num, int user) {
        if (lockedBy[num] == 0) {
            lockedBy[num] = user;
            return true;
        } else {
            return false;
        }
    }

    public boolean unlock(int num, int user) {
        if (lockedBy[num] == user) {
            lockedBy[num] = 0;
            return true;
        } else {
            return false;
        }
    }

    public boolean upgrade(int num, int user) {
        if (lockedBy[num] != 0) { //node should be unlocked
            return false;
        }
        if (!anyDescendantLocked(num)) { //at least one descendant should be locked
            return false;
        }
        if (anyAncestorLocked(num)) {
            return false;
        }
        lockedBy[num] = user;
        unlockAllDescendants(num);
        return true;
    }

    //Recursively unlock all descendants of a node
    private void unlockAllDescendants(int num) {
        for (int child : tree.getOrDefault(num, new ArrayList<>())) {
            lockedBy[child] = 0;
            unlockAllDescendants(child);
        }
    }

    //DFS to check whether any descendant is locked
    private boolean anyDescendantLocked(int num) {
        if (lockedBy[num] != 0) { //node is locked
            return true;
        } else {
            for (int child : tree.getOrDefault(num, new ArrayList<>())) {
                if (anyDescendantLocked(child)) {
                    return true;
                }
            }
            return false;
        }
    }

    //walk upwards using the parent array to check any ancestor is locked
    private boolean anyAncestorLocked(int num) {
        if (num == -1) {
            return false;
        } else if (lockedBy[num] != 0) {
            return true;
        } else {
            return anyAncestorLocked(parent[num]);
        }
    }
}

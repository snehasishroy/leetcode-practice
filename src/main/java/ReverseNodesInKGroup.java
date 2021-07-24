import common.ListNode;

/**
 * <pre>
 * https://leetcode.com/problems/reverse-nodes-in-k-group/
 *
 * Given a linked list, reverse the nodes of a linked list k at a time and return its modified list.
 *
 * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a multiple of k then left-out nodes, in the end, should remain as it is.
 *
 * You may not alter the values in the list's nodes, only nodes themselves may be changed.
 *
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [2,1,4,3,5]
 *
 * Input: head = [1,2,3,4,5], k = 3
 * Output: [3,2,1,4,5]
 *
 * Input: head = [1,2,3,4,5], k = 1
 * Output: [1,2,3,4,5]
 *
 * Input: head = [1], k = 1
 * Output: [1]
 *
 * Constraints:
 * The number of nodes in the list is in the range sz.
 * 1 <= sz <= 5000
 * 0 <= Node.val <= 1000
 * 1 <= k <= sz
 *
 * Follow-up: Can you solve the problem in O(1) extra memory space?
 * </pre>
 */
public class ReverseNodesInKGroup {
    /**
     * Approach: 3 Pointers, First reverse list of size k, then append the reversed nodes to a dummy tail.
     * Restart the process from the head of the (k + 1)th node.
     * Care must be taken to handle the case when there are less than k nodes in a block. No need to reverse the linked list in this case.
     *
     * {@link ReverseLinkedList}
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummyHead = new ListNode(), dummyTail = dummyHead; //dummy pointers to append the result
        while (head != null) { //head points to the starting node of the current block
            int index = 0;
            ListNode dummyNode = new ListNode(), tail = dummyNode; //dummy pointers to handle current block
            while (index < k && head != null) {
                tail.next = head;
                head = head.next;
                tail = tail.next;
                index++;
            }
            tail.next = null;
            ListNode newHead; //points to the new head of the current block
            if (index == k) { //only reverse the current block if it contains k nodes
                newHead = reverseLinkedList(dummyNode.next);
            } else {
                newHead = dummyNode.next;
            }
            //append the current block to the result
            while (newHead != null) {
                dummyTail.next = newHead;
                newHead = newHead.next;
                dummyTail = dummyTail.next;
            }
        }
        return dummyHead.next;
    }

    private ListNode reverseLinkedList(ListNode head) {
        ListNode prev = null, cur = head, next;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}

/*
You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example 1:
Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.

Constraints:
The number of nodes in each linked list is in the range [1, 100].
0 <= Node.val <= 9
It is guaranteed that the list represents a number that does not have leading zeros.
*/

import java.util.ArrayList;
import java.math.BigInteger;

class Solution {
    private void fillList(ArrayList<Integer> arr, ListNode ll) {
        do {
            arr.add(ll.val);
            ll = ll.next;
        } while (ll != null);
    }

    private String getSum(ArrayList<Integer> arr1, ArrayList<Integer> arr2) {
        String str1 = "";
        String str2 = "";
        BigInteger val;

        for (int i = arr1.size() - 1; i >= 0; i--) {
            str1 += arr1.get(i);
        }

        for (int i = arr2.size() - 1; i >= 0; i--) {
            str2 += arr2.get(i);
        }

        //val = Long.parseLong(str1, 10) + Long.parseLong(str2, 10);
        val = new BigInteger(str1, 10).add(new BigInteger(str2, 10));


        return val.toString(10);
    }

    private ListNode createNode(String str) {
        ListNode prevNode = null;
        int ch = 0;

        while (ch < str.length()) {
            if (prevNode != null) {
                prevNode = new ListNode(Character.getNumericValue(str.charAt(ch)), prevNode);
            } else {
                prevNode = new ListNode(Character.getNumericValue(str.charAt(ch)));
            }
            ch++;
        }

        return prevNode;
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        String sum;
        ListNode result;

        fillList(list1, l1);
        fillList(list2, l2);

        sum = getSum(list1, list2);
        result = createNode(sum);

        return result;
    }
}
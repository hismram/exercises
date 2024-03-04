/*
Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
You may assume that each input would have exactly one solution, and you may not use the same element twice.
You can return the answer in any order.

Example:
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].

Constraints:
2 <= nums.length <= 104
-109 <= nums[i] <= 109
-109 <= target <= 109
Only one valid answer exists.
*/

import java.util.HashMap;
import java.util.Map;

class Solution {
    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer[]> map = new HashMap<>();
        int[] result = new int[2];

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                Integer[] idxs = map.get(nums[i]);
                Integer[] buffer = new Integer[idxs.length + 1];

                for (int j = 0; j < idxs.length; j++) {
                    buffer[j] = idxs[j];
                }

                buffer[buffer.length - 1] = i;
                map.put(nums[i], buffer);

            } else {
                map.put(nums[i], new Integer[]{i});
            }

        }

        for (int key:  map.keySet()) {
            if (map.containsKey(target - key)) {
                Integer[] idxs = map.get(target - key);

                if (idxs.length > 1) {
                    result[0] = idxs[0];
                    result[1] = idxs[1];
                } else {
                    result[0] = map.get(key)[0];
                    result[1] = idxs[0];
                }
            }
        }

        return result;
    }
}
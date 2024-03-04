import java.util.HashMap;

public class Solution {
    public int numIdenticalPairs(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int pairs = 0;

        for (int num : nums) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }

        for (int i: map.keySet()) {
            pairs += calcPairs(map.get(i));
        }

        return pairs;
    }

    private int calcPairs(int count) {
        return count * (count - 1) / 2;
    }

    public static void main(String[] args) {                         // 4 * (4 - 1) / 2
        System.out.println(new Solution().numIdenticalPairs(new int[]{1,1,1,1, 2, 3 ,5}));
    }
}
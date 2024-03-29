// Given an integer x, return true if x is a palindrome, and false otherwise.
//Constraints: -2**31 <= x <= 2**31 - 1
class Solution {
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        
        String s = String.valueOf(x);
        
        for (int i = 0, j = s.length() - 1; i <= j; i++, j--) {
            if (s.charAt(i) != s.charAt(j)) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        
        
        while (true) {
            System.out.println(isPalindrome(scanner.nextInt()));
        }
    }
}
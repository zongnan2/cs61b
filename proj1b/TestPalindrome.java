import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();
    static CharacterComparator offByN = new OffByN(5);

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        boolean result0 = palindrome.isPalindrome("");
        boolean result1 = palindrome.isPalindrome("a");
        boolean result2 = palindrome.isPalindrome("racecar");
        boolean result3 = palindrome.isPalindrome("Racecar");
        assertTrue(result0);
        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
    }

    @Test
    public void testIsPalindrome2() {
        boolean result0 = palindrome.isPalindrome("", offByOne);
        boolean result1 = palindrome.isPalindrome("a", offByOne);
        boolean result2 = palindrome.isPalindrome("flake",offByOne);
        boolean result3 = palindrome.isPalindrome("abhgf", offByN);
        assertTrue(result0);
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
    }

}
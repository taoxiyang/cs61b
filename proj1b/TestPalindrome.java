import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {

    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

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
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("caac"));
        assertTrue(palindrome.isPalindrome("CaAc"));
        assertTrue(palindrome.isPalindrome("cac"));
        assertTrue(palindrome.isPalindrome("aaabbbbbaaa"));
        assertTrue(palindrome.isPalindrome("Cac"));
        assertFalse(palindrome.isPalindrome("caca"));
        assertFalse(palindrome.isPalindrome("cat"));
    }

    @Test
    public void testIsPalindromeCC() {
        CharacterComparator offByOne = new OffByOne();
        assertTrue(palindrome.isPalindrome("a",offByOne));
        assertTrue(palindrome.isPalindrome("",offByOne));
        assertTrue(palindrome.isPalindrome("caac",offByOne));
        assertTrue(palindrome.isPalindrome("CaAc",offByOne));
        assertTrue(palindrome.isPalindrome("cac",offByOne));
        assertTrue(palindrome.isPalindrome("aaabbbbbaaa",offByOne));
        assertTrue(palindrome.isPalindrome("Cac",offByOne));
        assertFalse(palindrome.isPalindrome("caca",offByOne));
        assertFalse(palindrome.isPalindrome("cat",offByOne));

        assertTrue(palindrome.isPalindrome("flake",offByOne));
    }


}

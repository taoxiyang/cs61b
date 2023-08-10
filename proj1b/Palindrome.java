/**
 * @author qiushui
 * @Date 2023/8/10
 */
public class Palindrome {

    public Deque<Character> wordToDeque(String word){
        Deque<Character> deque = new ArrayDeque<>();
        for(Character c : word.toCharArray()){
            deque.addLast(c);
        }
        return deque;
    }

    public boolean isPalindrome(String word){
        Deque<Character> deque = wordToDeque(word.toLowerCase());
        while (deque.size() > 1){
            if(!deque.removeFirst().equals(deque.removeLast())){
                return false;
            }
        }
        return true;
    }

    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> deque = wordToDeque(word.toLowerCase());
        while (deque.size() > 1){
            if(!cc.equalChars(deque.removeFirst(),deque.removeLast())){
                return false;
            }
        }
        return true;
    }

}

public class Palindrome {
    private boolean recIsPalindrome(Deque<Character> a) {
        if(a.size() == 0 || a.size() == 1) {
            return true;
        }
        Character front = a.removeFirst();
        Character back = a.removeLast();
        if(!front.equals(back)) {
            return false;
        }
        return recIsPalindrome(a);
    }

    private boolean recIsOBOPalindrome(Deque<Character> a, CharacterComparator b) {
        if(a.size() == 0 || a.size() == 1) {
            return true;
        }
        Character front = a.removeFirst();
        Character back = a.removeLast();
        if(!b.equalChars(front,back)) {
            return false;
        }
        return recIsOBOPalindrome(a,b);
    }

    public Deque<Character> wordToDeque(String word) {
        Deque<Character> charDeque = new ArrayDeque<>();
        for(int i = 0;i<word.length();i++) {
            charDeque.addLast(word.charAt(i));
        }
        return charDeque;
    }

    public boolean isPalindrome(String word) {
        if(word == null) {
            return true;
        }
        Deque<Character> palDeque = wordToDeque(word);
        return recIsPalindrome(palDeque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if(word == null) {
            return true;
        }
        Deque<Character> oboDeque = wordToDeque(word);
        return recIsOBOPalindrome(oboDeque, cc);
    }
}

import java.util.*;

public class MyTrieSet implements TrieSet61B{

    private Node root;


    public MyTrieSet() {
        this.root = new Node(null,false);
    }

    private static class Node{
        private Map<Character, Node> map;
        private boolean isKey;

        private Node(Character c, boolean b) {
            this.map = new HashMap<Character, Node>();
            this.isKey = b;
        }

    }

    /**
     * Clears all items out of Trie
     */
    @Override
    public void clear() {
        Node curr = root;
        curr.map.clear();
    }

    /**
     * Returns true if the Trie contains KEY, false otherwise
     */
    @Override
    public boolean contains(String key) {
        if (key == null || key.length() < 1) {
            return false;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }
        return curr.isKey == true;
    }

    /**
     * Inserts string KEY into Trie
     */
    @Override
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node curr = root;
        for (int i = 0, n = key.length(); i < n; i++) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(c, false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    /** Returns a list of all words that start with PREFIX */
    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node start = root;
        for (int i = 0, n = prefix.length(); i < n; i++) {
            char c = prefix.charAt(i);
            if (!start.map.containsKey(c)) {
                throw new NoSuchElementException("prefix not exist!");
            }
            start = start.map.get(c);
        }
        List<String> x = new ArrayList<>();
        for(Character key: start.map.keySet()) {
            colHelp(prefix+key.toString(), x, start.map.get(key));
        }
        return x;
    }

    /*private List<String> collect(Node start) {
        List<String> x = new ArrayList<>();
        for(Character key: start.map.keySet()) {
            colHelp(key.toString(), x, start.map.get(key));
        }
        return x;
    }*/

    private void colHelp(String s, List<String> x, Node n) {
        if(n.isKey) {
            x.add(s);
        }
        for(Character key: n.map.keySet()) {
            colHelp(s+key, x, n.map.get(key));
        }
    }

    /**
     * Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    @Override
    public String longestPrefixOf(String key) {
        return null;
    }

    public static void main(String[] args) {
        MyTrieSet t = new MyTrieSet();
        t.add("hello");
        t.add("hi");
        t.add("help");
        t.add("zebra");
        System.out.print(t.contains("hello"));
        t.keysWithPrefix("he");
    }

}

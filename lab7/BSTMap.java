import edu.princeton.cs.algs4.BST;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private KV_BST bst;
    private int size;


    private class KV_BST {
        K key;
        V val;
        KV_BST left;
        KV_BST right;

        KV_BST(K key, V val, KV_BST left, KV_BST right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
            size++;
        }

        KV_BST insert(KV_BST T, K key, V val) {
            if(T == null) {
                return new KV_BST(key, val, null, null);
            }
            if(key.compareTo(T.key) < 0) {
                T.left = insert(T.left, key, val);
            }
            else if(key.compareTo(T.key) > 0) {
                T.right = insert(T.right, key, val);
            } else if(key.compareTo(T.key) == 0) {
                T.val = val;
            }
            return T;
        }

        KV_BST find(KV_BST T, K key) {
            if(T == null) {
                return null;
            }
            if(key.equals(T.key)) {
                return T;
            } else if(key.compareTo(T.key) < 0) {
                return find(T.left, key);
            } else {
                return find(T.right, key);
            }
        }

        void printInOrder(KV_BST T) {
            if(T == null) {
                return;
            } else {
                printInOrder(T.left);
                System.out.println("Key: " + T.key + " Val: " + T.val);
                printInOrder(T.right);
            }
        }
    }

    public void printInOrder() {
        bst.printInOrder(bst);
    }


    @Override
    public void clear() {
        size = 0;
        bst = null;
    }

    @Override
    public boolean containsKey(K key) {
        if(bst == null) {
            return false;
        }
        return bst.find(bst, key)!=null;
    }

    @Override
    public V get(K key) {
        if(bst == null) {
            return null;
        }
        KV_BST lookUp = bst.find(bst, key);
        if(lookUp == null) {
            return null;
        }
        return lookUp.val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if(bst == null) {
            bst = new KV_BST(key, value, null, null);
        } else {
            bst.insert(bst, key, value);
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Method not supported");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Method not supported");
    }
}

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

public class MyHashMap <K, V> implements Map61B<K, V> {
    private int initialSize;
    private double loadFactor;
    private HashSet<K> storeKeys;
    private V[] bucket;


    public MyHashMap() {
        initialSize = 16;
        loadFactor = 0.75;
        bucket = (V[]) new Object[initialSize];
        storeKeys = new HashSet<>();
    }

    public MyHashMap(int initialSize) {
        this.initialSize = initialSize;
        loadFactor = 0.75;
        bucket = (V[]) new Object[initialSize];
        storeKeys = new HashSet<>();
    }
    public MyHashMap(int initialSize, double loadFactor) {
        this.initialSize = initialSize;
        this.loadFactor = loadFactor;
        bucket = (V[]) new Object[initialSize];
        storeKeys = new HashSet<>();
    }

    @Override
    public void clear() {
        storeKeys.clear();
        bucket = (V[]) new Object[initialSize];
    }

    @Override
    public boolean containsKey(K key) {
        return storeKeys.contains(key);
    }

    @Override
    public V get(K key){
        if(hash(key) == -1 || !storeKeys.contains(key)) {
            return null;
        }
        return bucket[hash(key)];
    }

    @Override
    public int size() {
        if(storeKeys == null) {
            return 0;
        }
        return storeKeys.size();
    }

    @Override
    public void put(K key, V value) {
        if(storeKeys.size() >= initialSize*loadFactor) {
            resize();
        }
        storeKeys.add(key);
        bucket[hash(key)] = value;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = storeKeys;
        return keySet;
    }

    @Override
    public V remove(K key){
        throw new UnsupportedOperationException("Remove Not Implemented");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Remove Not Implemented");
    }

    @Override
    public Iterator<K> iterator() {
        return new MyHashMapIter();
    }

    private int hash(K key) {
        if(key == null) {
            return -1;
        } else {
            return Math.floorMod(key.hashCode(), initialSize);
        }
    }

    private void resize() {
        LinkedList<V> temp = new LinkedList<>();
        for(K item: storeKeys) {
            temp.add(get(item));
        }
        initialSize = initialSize*2;
        bucket = (V[]) new Object[initialSize];
        for(K item: storeKeys) {
            bucket[hash(item)] = temp.removeFirst();
        }
    }


//    @Override
//    public int hashCode() {
//        int h = 0;
//        if(h == 0 && this.length()>0)
//    }


    private class MyHashMapIter implements Iterator<K> {
        private K[] keySet;

        public MyHashMapIter() {
            keySet = (K[]) storeKeys.toArray();
        }

        @Override
        public boolean hasNext() {
            return keySet.length != 0;
        }

        @Override
        public K next() {
            K[] keySetCopy = (K[]) new Object[keySet.length-1];
            K ret = keySet[0];
            for(int i=1;i<keySet.length; i++) {
                keySetCopy[i-1] = keySet[i];
            }
            keySet = keySetCopy;
            return ret;
        }
    }

    public static void main(String[] args) {
        MyHashMap<String, Integer> b = new MyHashMap<String, Integer>();
    }
}

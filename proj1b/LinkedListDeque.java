/**
 * LinkedListDeque stores item in type T as a linked list.
 * @author Zongnan Chen
 */
public class LinkedListDeque<T> implements Deque<T>{
    /**
     * Nested class Node that contains the inner
     * structure of the LinkedListDeque.
     */
    private class Node {
        private Node prev;
        private T currentItem;
        private Node next;
        private Node(T item) {
            currentItem = item;
            prev = null;
            next = null;
        }

    }

    private Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new Node(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

        Node p = other.sentinel;
        Node q = sentinel;
        while(!p.next.equals(other.sentinel)) {
            p = p.next;
            Node curNode = new Node(p.currentItem);
            curNode.prev = q;
            q.next = curNode;
            sentinel.prev = curNode;
            curNode.next = sentinel;
            q = q.next;
        }
    }
    @Override
    public void addFirst(T item) {
        Node curNode = new Node(item);
        curNode.next = sentinel.next;
        sentinel.next.prev = curNode;
        sentinel.next = curNode;
        curNode.prev = sentinel;
        size += 1;
    }
    @Override
    public void addLast(T item) {
        Node curNode = new Node(item);
        curNode.prev = sentinel.prev;
        sentinel.prev.next = curNode;
        sentinel.prev = curNode;
        curNode.next = sentinel;
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        Node p = sentinel;
        while(!p.next.equals(sentinel)) {
            System.out.print(p.next.currentItem + " ");
            p = p.next;
        }
        System.out.println();
    }
    @Override
    public T removeFirst() {
        if(size == 0) {
            return null;
        }
        Node temp = sentinel.next;
        sentinel.next = temp.next;
        temp.next.prev = sentinel;
        temp.prev = null;
        temp.next = null;
        return temp.currentItem;
    }
    @Override
    public T removeLast() {
        if(size == 0) {
            return null;
        }
        Node temp = sentinel.prev;
        sentinel.prev = temp.prev;
        temp.prev.next = sentinel;
        temp.prev = null;
        temp.next = null;
        return temp.currentItem;
    }
    @Override
    public T get(int index) {
        Node p = sentinel;
        for(int i=0; i<=index;i++) {
            p = p.next;
        }
        return p.currentItem;
    }

    public T getRecursive(int index) {
        if(index == 0) {
            return this.sentinel.next.currentItem;
        }
        LinkedListDeque temp = new LinkedListDeque(this);
        temp.removeFirst();
        return (T) temp.getRecursive(index-1);
    }

}

import javax.swing.*;
import java.lang.reflect.Array;

public class ArrayDeque<T> {
    private T[] elements;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        elements = (T[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        elements = (T[]) new Object[other.elements.length];
        System.arraycopy(other.elements,0,elements,0,elements.length);
        size = other.size;
        nextLast = other.nextLast;
        nextFirst = other.nextFirst;
    }

    public void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        System.arraycopy(elements,0,temp,0,nextLast);
        int rearSize = size-nextLast+1;
        nextFirst = temp.length - rearSize;
        System.arraycopy(elements,elements.length-rearSize,temp,nextFirst,rearSize);
        elements = temp;
    }

    public void checksize() {
        if(elements.length < 16) {
            return;
        }
        double dsize = size;
        double ratio = dsize/elements.length;
        if(ratio <= 0.25) {
            resize(elements.length/2);
        }
    }

    public void addFirst(T item) {
        if(nextFirst == nextLast) {
            resize(elements.length*2);
        }
        elements[nextFirst] = item;
        if(nextFirst == 0) {
            nextFirst = elements.length-1;
        } else {
            nextFirst -= 1;
        }
        size += 1;
    }

    public void addLast(T item) {
        if(nextFirst == nextLast) {
            resize(elements.length*2);
        }
        elements[nextLast] = item;
        if(nextLast == elements.length-1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        size += 1;
    }

    public boolean isEmpty() {
        if(size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for(int i=0;i<elements.length;i++) {
            System.out.println(elements[i]);
        }
        System.out.println();
    }

    public T removeFirst() {
        checksize();
        if(nextFirst == elements.length-1) {
            nextFirst = 0;
        } else {
            nextFirst += 1;
        }
        T returnValue = elements[nextFirst];
        elements[nextFirst] = null;
        size -= 1;
        return returnValue;
    }

    public T removeLast() {
        checksize();
        if(nextLast == 0) {
            nextLast = elements.length - 1;
        } else {
            nextLast -= 1;
        }
        T returnValue = elements[nextLast];
        elements[nextLast] = null;
        size -= 1;
        return returnValue;
    }

    public T get(int index) {
        return elements[index];
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> exampleArray = new ArrayDeque<>();
        exampleArray.addFirst(45);
        exampleArray.addLast(5);
        exampleArray.addLast(10);
        exampleArray.addFirst(40);
        exampleArray.addLast(15);
        exampleArray.addFirst(35);
        exampleArray.addLast(20);
        exampleArray.addFirst(30);
        System.out.println(exampleArray.get(2));
    }
}

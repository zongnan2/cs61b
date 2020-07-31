import javax.swing.*;
import java.lang.reflect.Array;

public class ArrayDeque<T> implements Deque<T>{
    private T[] elements;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        elements = (T[]) new Object[8];
        size = 0;
        nextFirst = elements.length-1;
        nextLast = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        elements = (T[]) new Object[other.elements.length];
        System.arraycopy(other.elements,0,elements,0,elements.length);
        size = other.size;
        nextLast = other.nextLast;
        nextFirst = other.nextFirst;
    }

    private void resize(int capacity) {
        T[] temp = (T[]) new Object[capacity];
        int index;
        if(nextFirst == elements.length-1) {
            index = 0;
        } else {
            index = nextFirst+1;
        }
        int i = 0;
        while(index != nextLast) {
            temp[i] = elements[index];
            i++;
            if(index == elements.length-1) {
                index = 0;
            } else {
                index ++;
            }
        }

        //System.arraycopy(elements,0,temp,0,nextLast);
        //int rearSize = size-nextLast+1;
        //nextFirst = temp.length - rearSize;
        //System.arraycopy(elements,elements.length-rearSize,temp,nextFirst,rearSize);
        nextFirst = temp.length-1;
        nextLast = size;
        elements = temp;

    }

    private void checksize() {
        if(elements.length < 16) {
            return;
        }
        double dsize = size;
        double ratio = dsize/elements.length;
        if(ratio <= 0.25) {
            resize(elements.length/2);
        }
    }
    @Override
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
    @Override
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
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        for(int i=0;i<elements.length;i++) {
            System.out.println(elements[i]);
        }
        System.out.println();
    }
    @Override
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
    @Override
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
    @Override
    public T get(int index) {
        return elements[index];
    }

}

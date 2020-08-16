package es.datastructur.synthesizer;
import java.util.Iterator;
import java.util.NoSuchElementException;

//TODO: Make sure to that this class and all of its methods are public
//TODO: Make sure to add the override tag for all overridden methods
//TODO: Make sure to make this class implement BoundedQueue<T>

public class ArrayRingBuffer<T> implements BoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Variable for the fillCount. */
    private int fillCount;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Return the capacity of the array.
     */
    @Override
    public int capacity() {
        return rb.length;
    }

    /**
     * Return the fillCount of the array.
     */
    @Override
    public int fillCount() {
        return fillCount;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     */
    @Override
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update
        //       last.
        if(fillCount == rb.length) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        if(last == rb.length-1) {
            last = 0;
        } else {
            last++;
        }
        fillCount++;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and
        //       update first.
        if(fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T temp = rb[first];
        rb[first] = null;
        if(first == rb.length-1) {
            first = 0;
        } else {
            first++;
        }
        fillCount--;
        return temp;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        // TODO: Return the first item. None of your instance variables should
        //       change.
        if(fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    /**
     * Return true if item exists in the Buffer
     */
    public boolean contains(T x) {
        int index = first;
        int count = fillCount;
        while(index != last || count != 0) {
            if(x == rb[index]) {
                return true;
            }
            if(index == rb.length-1) {
                index = 0;
            } else {
                index++;
            }
            count--;
        }
        return false;
    }

    /**
     * Implementing the iterator method and the sub-nested class of bufferIterator
     * that support hasNext and Next methods.
     */

    @Override
    public Iterator<T> iterator() {
        return new bufferIterator(first, last, fillCount);
    }

    private class bufferIterator implements Iterator<T> {
        private int startIndex;
        private int endIndex;
        private int count;

        public bufferIterator(int startIndex, int endIndex, int count) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.count = count;
        }
        @Override
        public boolean hasNext() {
            if(startIndex == endIndex && count ==0) {
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            T returnBuffer = rb[startIndex];
            if(startIndex == rb.length-1) {
                startIndex = 0;
            } else {
                startIndex++;
            }
            count--;
            return returnBuffer;
        }
    }

    /**
     * Implement the equals method which returns true if
     * two ArrayRingBuffer has exactly same values.
     */
    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }
        if(other.getClass() != this.getClass()) {
            return false;
        }
        ArrayRingBuffer<T> o = (ArrayRingBuffer<T>) other;
        if(o.capacity() != this.capacity()) {
            return false;
        }
        for(T item:this) {
            if(!o.contains(item)) {
                return false;
            }
        }
        return true;
    }

    // TODO: When you get to part 4, implement the needed code to support
    //       iteration and equals.
}
    // TODO: Remove all comments that say TODO when you're done.

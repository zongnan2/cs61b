package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void capacityTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        int actual = arb.capacity();
        int expected = 10;
        assertEquals(actual,expected);
    }

    @Test
    public void fillCountTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        arb.enqueue(5);
        arb.enqueue(10);
        int actual = arb.fillCount();
        int expected = 2;
        assertEquals(actual,expected);
    }

    @Test
    public void enqueueTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(3);
        arb.enqueue(5);
        arb.enqueue(10);
        arb.enqueue(15);
        arb.dequeue();
        arb.enqueue(20);
        int actual = arb.fillCount();
        int expected = 3;
        assertEquals(actual,expected);
    }

    @Test
    public void dequeTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(3);
        arb.enqueue(5);
        arb.enqueue(10);
        arb.enqueue(15);
        arb.dequeue();
        arb.enqueue(20);
        arb.dequeue();
        arb.dequeue();
        arb.enqueue(25);
        int actual = arb.dequeue();
        int expected = 20;
        assertEquals(actual,expected);
    }

    @Test
    public void iteratorTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(3);
        arb.enqueue(5);
        arb.enqueue(10);
        arb.enqueue(15);
        for(int i : arb) {
            System.out.println(i);
        }
    }

    @Test
    public void exceptionTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(1);
        arb.enqueue(5);
        arb.dequeue();
        //arb.dequeue();
    }

    @Test
    public void equalsTest() {
        ArrayRingBuffer<Integer> a = new ArrayRingBuffer(3);
        ArrayRingBuffer<Integer> b = new ArrayRingBuffer(3);
        a.enqueue(5);
        a.enqueue(10);
        a.enqueue(15);
        b.enqueue(5);
        b.enqueue(10);
        b.enqueue(14);
        boolean actual = a.equals(b);
        boolean expected = false;
        assertEquals(actual,expected);
    }

    @Test
    public void containsTest() {
        ArrayRingBuffer<Integer> a = new ArrayRingBuffer(3);
        a.enqueue(5);
        a.enqueue(10);
        a.enqueue(15);
        a.dequeue();
        boolean actual1 = a.contains(15);
        boolean expected1 = true;
        assertEquals(actual1,expected1);
        boolean actual2 = a.contains(5);
        boolean expected2 = false;
        assertEquals(actual1,expected1);
    }
}

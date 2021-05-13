import edu.princeton.cs.algs4.Queue;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> ints = new Queue<Integer>();
        ints.enqueue(7);
        ints.enqueue(6);
        ints.enqueue(5);
        ints.enqueue(4);
        ints.enqueue(3);
        ints.enqueue(2);
        ints.enqueue(1);
        QuickSort.quickSort(ints);
        assertTrue(isSorted(ints));
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> ints = new Queue<Integer>();
        ints.enqueue(7);
        ints.enqueue(6);
        ints.enqueue(5);
        ints.enqueue(4);
        ints.enqueue(3);
        ints.enqueue(2);
        ints.enqueue(1);
        MergeSort.mergeSort(ints);
        assertTrue(isSorted(ints));
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}

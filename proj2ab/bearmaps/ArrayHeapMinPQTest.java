package bearmaps;

import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    // Assume add works
    @Test
    public void arrayHeapContainsTest() {
        ArrayHeapMinPQ<String> alphabet = new ArrayHeapMinPQ<>();
        assertFalse(alphabet.contains("America"));
        alphabet.add("America",0);
        assertTrue(alphabet.contains("America"));
    }

    // Assume add works
    @Test
    public void arrayHeapGetSmallestTest() {
        ArrayHeapMinPQ<String> alphabet = new ArrayHeapMinPQ<>();
        alphabet.add("America",0);
        alphabet.add("Bangladesh", 1);
        alphabet.add("China",2);
        assertEquals(alphabet.getSmallest(),"America");
    }

    // Assume add works
    @Test
    public void arrayHeapRemoveSmallestTest() {
        ArrayHeapMinPQ<String> alphabet = new ArrayHeapMinPQ<>();
        alphabet.add("America",0);
        alphabet.add("Bangladesh", 1);
        alphabet.add("China",2);
        alphabet.add("Denmark",3);
        alphabet.add("England",4);
        alphabet.add("Finland",5);
        alphabet.add("Germany",6);
        assertEquals(alphabet.removeSmallest(),"America");
    }

    // Assume add works
    @Test
    public void arrayHeapChangePriorityTest() {
        ArrayHeapMinPQ<String> alphabet = new ArrayHeapMinPQ<>();
        alphabet.add("America",0);
        alphabet.add("Bangladesh", 1);
        alphabet.add("China",2);
        alphabet.add("Denmark",3);
        alphabet.add("England",4);
        alphabet.add("Finland",5);
        alphabet.add("Germany",6);
        alphabet.changePriority("America",7);
        assertEquals(alphabet.getSmallest(),"Bangladesh");
    }


}

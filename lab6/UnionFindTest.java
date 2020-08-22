import org.junit.Test;
import static org.junit.Assert.*;


public class UnionFindTest {

    @Test
    public void findTest() {
        UnionFind example = new UnionFind(5);
        int actual = example.find(1);
        int expected = 1;
        assertEquals(actual,expected);
    }

    @Test
    public void unionTest() {
        UnionFind example = new UnionFind(5);
        example.union(0,1);
        example.union(2,3);
        example.union(0,3);
        example.find(0);
    }
}

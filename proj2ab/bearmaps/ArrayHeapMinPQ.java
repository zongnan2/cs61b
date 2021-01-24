package bearmaps;
import java.util.ArrayList;
import java.util.NoSuchElementException;

/** A correct implementation of the ExtrinsicMinPQ interface
 *  with contains, getSmallest, size, changePriority methods
 *  run in O(log(n)) time, and add, removeSmallest methods
 *  run in O(log(n)) time on average.
 * @author zongnanchen
 */
public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T>{
    private ArrayList<PriorityNode> items;

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
    }

    @Override
    public void add(T item, double priority){
        PriorityNode newNode = new PriorityNode(item, priority);
        if(items.contains(newNode)) {
            throw new IllegalArgumentException("item already exist");
        }
        items.add(newNode);
        rise(newNode);
    }

    private int parentIndex(int curIndex) {
        return (curIndex-1)/2;
    }

    private void rise(PriorityNode p) {
        while(items.indexOf(p) > 0) {
            int parIndex = parentIndex(items.indexOf(p));
            int curIndex = items.indexOf(p);
            if(p.priority<items.get(parIndex).priority) {
                PriorityNode temp = items.get(parIndex);
                items.remove(curIndex);
                items.remove(parIndex);
                items.add(parIndex,p);
                items.add(curIndex,temp);
            } else {
                break;
            }
        }
    }

    @Override
    public boolean contains(T item){
        PriorityNode check = new PriorityNode(item, 0);
        if(items.contains(check)) {
            return true;
        }
        return false;
    }

    @Override
    public T getSmallest(){
        if(items.size() == 0) {
            throw new NoSuchElementException("The Array is empty!");
        }
        return items.get(0).item;
    }

    @Override
    public T removeSmallest(){
        if(items.size() == 0) {
            throw new NoSuchElementException("The Array is empty!");
        }
        PriorityNode lastNode = items.remove(items.size()-1);
        PriorityNode smallestNode = items.remove(0);
        items.add(0,lastNode);
        sink(lastNode);
        return smallestNode.item;
    }

    private void sink(PriorityNode p) {
        while(items.indexOf(p)<=(items.size()-2)/2) {
            int curIndex = items.indexOf(p);
            int leftChild = curIndex*2+1;
            int rightChild = curIndex*2+2;
            int candidateIndex = 0;
            if(items.get(leftChild).priority<items.get(rightChild).priority) {
                candidateIndex = leftChild;
            } else {
                candidateIndex = rightChild;
            }
            if(items.get(candidateIndex).priority<p.priority) {
                PriorityNode candidateNode = items.get(candidateIndex);
                items.remove(candidateIndex);
                items.remove(curIndex);
                items.add(curIndex,candidateNode);
                items.add(candidateIndex,p);
            } else {
                break;
            }
        }
    }

    @Override
    public int size(){
        return items.size();
    }

    @Override
    public void changePriority(T item, double priority){
        PriorityNode temp = new PriorityNode(item,priority);
        if(!items.contains(temp)) {
            throw new NoSuchElementException("Element not exist!");
        }
        int indexTarget = items.indexOf(temp);
        double oldPriority = items.get(indexTarget).priority;
        items.remove(indexTarget);
        items.add(indexTarget,temp);
        if(oldPriority<priority) {
            sink(items.get(indexTarget));
        } else {
            rise(items.get(indexTarget));
        }
    }

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item=e;
            this.priority=p;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }


        @Override
        public int compareTo(PriorityNode o) {
            if(o == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), o.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

    public static void main(String[] args) {
        ArrayHeapMinPQ<String> alphabet = new ArrayHeapMinPQ<>();
        alphabet.add("America",0);
        alphabet.add("Bangladesh", 1);
        alphabet.add("China",2);
        alphabet.add("Denmark",3);
        alphabet.add("England",4);
        alphabet.add("Finland",5);
        alphabet.add("Germany",6);
        alphabet.removeSmallest();
    }
}

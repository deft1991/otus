import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DIYarrayList<T> implements List<T> {

    private static final String THIS_OPERATION_DONT_SUPPORTS = "This operation don`t supports";

    private final int DEFAULT_CAPACITY = 16;

    private Object[] array;

    public Object[] getArray() {
        return array;
    }

    private int capacity;
    private int growTimes;

    private int currentIdx;
    private int size;

    public DIYarrayList() {
        this.array = new Object[DEFAULT_CAPACITY];
        this.capacity = DEFAULT_CAPACITY;
        this.size = DEFAULT_CAPACITY;
    }

    public DIYarrayList(final int initalSize) {
        this.array = new Object[initalSize];
        this.capacity = initalSize;
        this.size = initalSize;
    }

    public boolean add(T t) {
        checkCapacity(currentIdx + 1);
        this.array[currentIdx++] = t;
        return true;
    }

    private void checkCapacity(int i) {
        if (i > capacity) {
            growTimes++;
            grow();
        }
    }

    private void grow() {
        int prev = capacity;
        this.capacity = capacity + prev * 2 / growTimes;
        this.array = Arrays.copyOf(array, capacity);
    }

    public int size() {
        return array.length;
    }

    public T set(int index, T element) {
        checkCapacity(index);
        T oldElem = (T) array[index];
        array[index] = element;
        return oldElem;
    }

    public ListIterator<T> listIterator() {
        return new MyIterator(0) ;
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException(THIS_OPERATION_DONT_SUPPORTS);
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException(THIS_OPERATION_DONT_SUPPORTS);
    }

    public Iterator<T> iterator() {
        throw new UnsupportedOperationException(THIS_OPERATION_DONT_SUPPORTS);
    }

    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException(THIS_OPERATION_DONT_SUPPORTS);
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException(THIS_OPERATION_DONT_SUPPORTS);
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException(THIS_OPERATION_DONT_SUPPORTS);
    }

    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException(THIS_OPERATION_DONT_SUPPORTS);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException(THIS_OPERATION_DONT_SUPPORTS);
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(THIS_OPERATION_DONT_SUPPORTS);
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(THIS_OPERATION_DONT_SUPPORTS);
    }

    public void clear() {

    }

    public T get(int index) {
        return null;
    }

    public void add(int index, T element) {

    }

    public T remove(int index) {
        return null;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator<T> listIterator(int index) {
        return null;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    private class MyIterator implements ListIterator<T> {

        int cursor;
        int lastRet = -1;

        public MyIterator(int i) {
            super();
            cursor = i;
        }

        @Override public boolean hasNext() {
            return cursor != size;
        }

        @Override public T next() {
            int i = cursor;
            if (i > size()) {
                throw new NoSuchElementException();
            }
            cursor++;
            lastRet = i;
            return (T) array[i];
        }

        @Override public boolean hasPrevious() {
            return false;
        }

        @Override public T previous() {
            return null;
        }

        @Override public int nextIndex() {
            return 0;
        }

        @Override public int previousIndex() {
            return 0;
        }

        @Override public void remove() {

        }

        @Override public void set(T t) {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }
            DIYarrayList.this.set(lastRet, t);
        }

        @Override public void add(T t) {

        }
    }
}

package SkipList_Project.SkipList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;

public class SkipListSet<T extends Comparable<T>> implements SortedSet<T> {
    int size = 0;
    Iterator<T> iterator;
    private class SkipListSetIterator<T extends Comparable<T>> implements Iterator<T> {

        @Override
        public boolean hasNext() {
    
            return false;
        }
    
        @Override
        public T next() {
    
            return null;
        }

        @Override
        public void remove() {

        }        
    }
    
    private class SkipListSetItem<T extends Comparable<T>>
    {
        T payload;
        SkipListSetItem next;
        int height = 1;
        int maxHeight = 4;
        SkipListSetItem()
        {

        }
        SkipListSetItem(T input)
        {
            payload = input;
            height = changeHeight();
        }

        // for debugging purposes, creates an item with specified height
        SkipListSetItem(T input, int height)
        {
            payload = input;
            this.height = height;
        }
        
        public SkipListSetItem createHead()
        {
            return new SkipListSetItem();
        }

        public int changeHeight()
        {
            Random r = new Random(System.currentTimeMillis());
            int height = 0;
            while (height < maxHeight)
            {
                height++;
                if (r.nextInt(2) == 0)
                {
                    break;
                }
            }
            return height;
        }

        public boolean equals(SkipListSetItem compare)
        {
            return (payload.equals(compare.payload));
        }
    }

    public void reBalance()
    {

    }
    // Sorted set functions
    @Override
    public T first() {

        return null;
    }

    @Override
    public T last() {

        return null;
    }

    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<T> headSet(T toElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SortedSet<T> tailSet(T fromElement) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    // Set functions
    @Override
    public boolean add(T e) {

        return false;
    }
    
    @Override
    public boolean addAll(Collection<? extends T> c) {
        return false;
    }
    
    @Override
    public void clear() {

    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c)
        {
            boolean res = contains(o);
            if (!res)
            {
                return false;
            }            
        }
        return true;
    }

    public boolean equals (Object o)
    {
        if (!(o instanceof Collection))
        {
            System.out.println("error, passing non collection into set equals");
        }
        return (hashCode() == o.hashCode());
    }

    public int hashCode()
    {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return (size() == 0);
    }

    @Override
    public Iterator<T> iterator() {
        return iterator;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] toArray() {

        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {

        return null;
    }   
}
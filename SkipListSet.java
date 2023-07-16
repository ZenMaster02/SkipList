package SkipList_Project.SkipList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;

public class SkipListSet<T extends Comparable<T>> implements SortedSet<T> {
    int size = 0;
    SkipListSetItem<T> lowestHead = new SkipListSetItem<T>();
    class SkipListSetIterator<T extends Comparable<T>> implements Iterator<T> {
        SkipListSetItem<T> current;
        SkipListSetIterator(SkipListSet<T> set)
        {
            current = (SkipListSetItem<T>)set.lowestHead;
        }
        @Override
        public boolean hasNext() {
    
            return current != null;
        }
    
        @Override
        public T next() {
            T data = current.getPayload();
            current = current.getNext();
            return data;
        }
        // implement later
        // @Override
        // public void remove() {

        // }        
    }
    
    class SkipListSetItem<T extends Comparable<T>>
    {
        T payload = null;
        SkipListSetItem<T> next = null;
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
        
        public SkipListSetItem<T> createHead()
        {
            return new SkipListSetItem<T>();
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

        public T getPayload()
        {
            return payload;
        }

        public SkipListSetItem<T> getNext()
        {
            return next;
        }
    }

    public void reBalance()
    {

    }

    SkipListSetItem<T> getHead()
    {
        return lowestHead;
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
    // no duplicates 1 level
    @Override
    public boolean add(T e) {
        SkipListSetItem<T> current = lowestHead;
        while (current.next != null)
        {
            current = current.next;
        }
        current.next = new SkipListSetItem<T>(e);
        return true;
    }
    
    // simplistic
    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T item : c)
        {
            if (!add(item))
            {
                return false;
            }
        }
        return true;
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
        return new SkipListSetIterator<T>(this);
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
package SkipList_Project.SkipList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.SortedSet;

public class SkipListSet<T extends Comparable<T>> implements SortedSet<T> {
    int size;
    // highest level
    int level;
    int maxLevel;
    Random rand = new Random(System.currentTimeMillis());
    SkipListSetItem<T> head;

    public SkipListSet()
    {
        level = -1;
        maxLevel = 4;
        head = new SkipListSetItem<>(null, 0);
        size = 0;
    }

    class SkipListSetIterator<E extends Comparable<T>> implements Iterator<T> {
        SkipListSetItem<T> current;
        SkipListSetIterator(SkipListSet<T> set)
        {
            current = head;
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }
    
        @Override
        public T next() {
            if (current == head)
            {
                if (current.forward.get(0) != null)
                {
                    current = current.forward.get(0);
                }
            }
            T data = current.payload();
            System.out.print("Height: " + (current.height()) + " Payload:");
            current = current.forward.get(0);
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
        ArrayList<SkipListSetItem<T>> forward = new ArrayList<>();
        private int height;
        SkipListSetItem(T input, int height)
        {
            this.height = height;
            payload = input;
            forward = new ArrayList<SkipListSetItem<T>>(height + 1);
            // creates null pointers through the list
            for (int i = 0; i <= height; i++)
            {
                forward.add(i, null);
            }
        }
        
        public boolean equals(SkipListSetItem<T> compare)
        {
            return (payload.equals(compare.payload));
        }

        public T payload()
        {
            return payload;
        }
        
        public int height()
        {
            return height;
        }
        public void printForward()
        {
            System.out.print("[");
            for (SkipListSetItem<T> item : forward)
            {
                if (item != null)
                {
                    System.out.print(item.payload().toString() + ", ");
                }
                else
                {
                    System.out.print("null ");
                }
            }
            System.out.print("]\n");
        }

    }

    public void reBalance()
    {
        // int newLevel = randomLevel();
        // // if level is the heighest level
        // if (newLevel > level)
        // {   
        //     adjustHead(newLevel);
        // }        
        // for (int i = 1; i <= size; i++)
        // {

        // }
        // SkipListSetItem<> nowEditing = head;
        
        // for (int i = 1; i <= level; i++)
        // {
            
        // }
    }

    private int randomLevel()
    {
        int level = 0;
        // max level is chosen based on binary log of the amount of nodes
        double log2size = Math.log(size)/Math.log(2);
        if (log2size > maxLevel)
            maxLevel = (int) Math.floor(log2size);

        int temp = rand.nextInt() % 2;
        while (true)
        {
            temp = Math.abs(rand.nextInt()) % 2;
            level++;
            if (temp == 1 || level == maxLevel)
            {
                break;
            }
            
        }
        return level;
    }

    boolean find(T item)
    {
        SkipListSetItem<T> current = head;
        // goes from the highest level down
        if (head.forward.isEmpty() || head.forward.get(0) == null)
        {
            return false;
        }
        for (int i = level-1; i >= 0; i--)
        {
            // continues the search until it is greater than searched for element
            while ((current.forward.get(i) != null) 
                    && (current.forward.get(i).payload().compareTo(item) < 0))
            {
                current = current.forward.get(i);
            }
        }
        // get the lowest version of the node
        current = current.forward.get(0);
        // if the payload is the same as the searched item, it is in the list
        if ((current != null) && (current.payload().compareTo(item) == 0))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    SkipListSetItem<T> getHead()
    {
        return head;
    }

    private void adjustHead(int newLevel)
    {
        SkipListSetItem<T> temp = head;
        SkipListSetItem<T> newHead = new SkipListSetItem<T>(null, newLevel);
        // copying the pointers from the old head to the new head
        for (int i = 0; i <= level; i++)
        {
            newHead.forward.set(i,temp.forward.get(i));
        }
        level = newLevel;
        head = newHead;
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
        // if element is in the set, return false and dont do anything
        if (find(e))
        {
            return false;
        }
        int newLevel = randomLevel();
        // if level is the heighest level
        if (newLevel > level)
        {   
            adjustHead(newLevel);
        }
        
        // temporary array list that holds the pointers to the next lowest node
        ArrayList<SkipListSetItem<T>> updateArrayList = new ArrayList<>(level+1);
        for (int i = 0; i <= level; i++)
        {
            updateArrayList.add(i, null);
        }
        SkipListSetItem<T> current = head;
        // goes from the highest level down and copies 
        for (int i = level; i >= 0; i--)
        {   
            // continues the search until it is greater than searched for element
            while ((current.forward.get(i) != null) && (current.forward.get(i).payload().compareTo(e) < 0))
            {
                current = current.forward.get(i);   
            } 
            updateArrayList.set(i,current);
        }
        SkipListSetItem<T> newItem = new SkipListSetItem<T>(e, newLevel);
        // puts the new item into 
        for (int i = 0; i <= newLevel; i++)
        {
            newItem.forward.set(i, updateArrayList.get(i).forward.get(i));
            updateArrayList.get(i).forward.set(i, newItem);
        }
        size++;


        return true;
    }
    
    // simplistic
    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T item : c)
        {
            add(item);
        }
        return true;
    }
    
    @Override
    public void clear() {
        head = new SkipListSetItem<>(null, 0);
        level = -1;
        size = 0;
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
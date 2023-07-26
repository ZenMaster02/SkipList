package SkipList_Project.SkipList;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.Arrays;

public class SkipListSet <T extends Comparable<T>> implements SortedSet<T> {
    int size;
    // highest level
    int level;
    int maxLevel;
    Random rand = new Random(System.currentTimeMillis());
    SkipListSetItem<T> head;

    public SkipListSet()
    {
        resetList();
    }
    private void resetList()
    {
        level = -1;
        maxLevel = 4;
        head = new SkipListSetItem<>(null, 0);
        size = 0;
    }
    private class SkipListSetIterator<E extends Comparable<T>> implements Iterator<T> {
        private SkipListSetItem<T> current;
        SkipListSetIterator(SkipListSet<T> set)
        {
            current = set.getHead();
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
            // System.out.print("Height: " + (current.height()) + " Payload:");
            current = current.forward.get(0);
            return data;
        }
        // removes current node and moves to next one
        // @Override
        // public void remove() {
        //     SkipListSetItem<T> temp = current;
        //     current = current.forward.get(0);
        //     remove(temp.payload());
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

    public void printSkipList()
    {
        for (int i = level; i >= 0; i--)
        {
            System.out.print("Level: " + i + " ");
            SkipListSetItem<T> current = head;
            for (int j = 0; j <= size(); j++)
            {
                if (current.payload() == null)
                {
                    System.out.print("head\t");
                }
                else if (current.height() < i)
                {
                    System.out.print("\t");
                }
                else
                {
                    System.out.print(current.payload() + "\t");
                }
                current = current.forward.get(0);
            }
            System.out.println();
        }
    }
    // basically just makes a whole new skiplist with the previous values
    public void reBalance()
    {
        ArrayList<T> list = new ArrayList<>();
        for(Object o : this)
        {
            list.add((T) o);
        }
        resetList();
        double log2size = Math.log(size)/Math.log(2);
        if (log2size > maxLevel)
            maxLevel = (int) Math.floor(log2size);

        addAll(list);
    }

    private int randomLevel()
    {
        int level = 0;
        // max level is chosen based on binary log of the amount of nodes
        // amount needed till next rebalance is doubled
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
        if (head == null ||head.forward.get(0) == null)
        {
            return null;
        }
        return head.forward.get(0).payload();

    }

    @Override
    public T last() {
        T current = null;
        for(Object o : this)
        {
            current = (T)o;
        }
        return current;
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
        boolean fin = false;
        for (T item : c)
        {
            boolean res = true;
            res = add(item);
            if (res == true)
            {
                fin = true;
            }
        }
        return fin;
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
        return find((T)o);
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
        if (!(o instanceof Set))
        {
            System.out.println("error, passing non set into set equals");
        }
        return (hashCode() == o.hashCode());
    }

    public int hashCode()
    {
        int h = 0;
        Iterator iter = iterator();
        while (iter.hasNext())
        {
            T item = iter.next();
            if (item != null)
            {
                h += item.hashCode();
            }
        }
        return h;
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
        
        if (o == null)
        {
            return false;
        }
        if (!contains(o))
            return false;
        if (size() == 1)
        {
            resetList();
            return true;
        }

        // temporary array list that holds the pointers to the node before the target for removal node
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
            while ((current.forward.get(i) != null) && (current.forward.get(i).payload().compareTo((T)o) < 0))
            {
                current = current.forward.get(i);   
            } 
            updateArrayList.set(i,current);
        }
        SkipListSetItem<T> delNode = current.forward.get(0);
        int delHeight = delNode.height();
        for (int i = 0; i <= level; i++)
        {
            if (i <= delHeight)
            {
                updateArrayList.get(i).forward.set(i, delNode.forward.get(i));
            }
        }
        size--;
        
        return true;
    }

    // removes all objects in a passed in collection
    // returns false if every single remove attempt failed, otherwise true
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean fin = false;
        for (Object o : c)
        {
            boolean res = add(o);
            if (res == true)
            {
                fin = true;
            }
        }
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
        ArrayList<Object> list = new ArrayList<>();
        for(Object o : this)
        {
            list.add(o);
        }
        return list.toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        ArrayList<T> list = new ArrayList<>();
        for(Object o : this)
        {
            list.add((T) o);
        }
        return list.toArray(a);  
    }   
}
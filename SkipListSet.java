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
    private int size;
    // highest level
    private int level;
    private int maxLevel;
    private Random rand = new Random(System.currentTimeMillis());
    private SkipListSetItem<T> head;

    public SkipListSet()
    {
        resetList();
    }
    private void resetList()
    {
        level = -1;
        maxLevel = 8;
        head = new SkipListSetItem<>(null, 0);
        size = 0;
    }
    private class SkipListSetIterator<E extends Comparable<T>> implements Iterator<T> {
        private SkipListSetItem<T> current;
        private T lastReturn = null;
        private SkipListSet set;
        SkipListSetIterator(SkipListSet<T> set)
        {
            this.set = set;
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
            lastReturn = data;
            // System.out.print("Height: " + (current.height()) + " Payload:");
            current = current.forward.get(0);
            return data;
        }
        // removes the last return value
        @Override
        public void remove() {
            set.remove(lastReturn);
        }        
    }
    
    class SkipListSetItem<T extends Comparable<T>>
    {
        private T payload = null;
        private ArrayList<SkipListSetItem<T>> forward = new ArrayList<>();
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

        public T payload()
        {
            return payload;
        }
        
        public int height()
        {
            return height;
        }

        public void changeHeight(int height)
        {
            this.height = height;
            SkipListSetItem<T> next = forward.get(0);
            forward = new ArrayList<SkipListSetItem<T>>(height + 1);
            // creates null pointers through the list
            forward.add(0, next);
            for (int i = 1; i <= height; i++)
            {
                forward.add(i, null);
            }
            // if height is bigger than current level, increases the level
            if (height > level)
            {
                level = height;
            }
        }
    
        // public void printForward()
        // {
        //     System.out.print("[");
        //     for (SkipListSetItem<T> item : forward)
        //     {
        //         if (item != null)
        //         {
        //             System.out.print(item.payload().toString() + ", ");
        //         }
        //         else
        //         {
        //             System.out.print("null ");
        //         }
        //     }
        //     System.out.print("]\n");
        // }

    }

    // debugging printing code delete later
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
        double log2size = Math.log(size)/Math.log(2);
        maxLevel = 8;
        if (log2size > maxLevel)
            maxLevel = (int) Math.floor(log2size);
        SkipListSetItem<T> current = head.forward.get(0);
        SkipListSetItem<T> [] lastItem = new SkipListSetItem[maxLevel+1];
        SkipListSetItem<T> newHead =  new SkipListSetItem<>(null, maxLevel);
        newHead.forward.set(0, current);
        for (int i = 0; i < lastItem.length; i++)
        {
            lastItem[i] = newHead;
        }
        for (int i = 0; i < size; i++)
        {
            current.changeHeight(randomLevel());           
        
            for (int j = 1; j <= current.height(); j++)
            {
                lastItem[j].forward.set(j, current);
                lastItem[j] = current;
            }
            current = current.forward.get(0);
        }
        int curLevel = level;
        for (int i = curLevel; i > 0; i--)
        {
            if (newHead.forward.get(i) == null)
            {
                // shrinks head if there is a null row
                adjustHead(i-1, newHead);   
            }
            else
            {
                break;
            }
        }
        head = newHead;
    }

    private int randomLevel()
    {
        int level = 0;
        // max level is chosen based on binary log of the amount of nodes
        // amount needed till next rebalance is doubled
        
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

    private void traverseSkipList(T e, ArrayList<SkipListSetItem<T>> updateArrayList)
    {
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
    }

    private boolean find(T item)
    {
        if (isEmpty())
        {
            return false;
        }
        SkipListSetItem<T> current = head;
        // goes from the highest level down
        if (head.forward.isEmpty() || head.forward.get(0) == null)
        {
            return false;
        }
        ArrayList<SkipListSetItem<T>> arrayList = new ArrayList<>(level+1);
        for (int i = 0; i <= level; i++)
        {
            arrayList.add(i, null);
        }
        traverseSkipList(item, arrayList);
        // get the lowest version of the node
        current = arrayList.get(0).forward.get(0);
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

    private SkipListSetItem<T> getHead()
    {
        return head;
    }

    private SkipListSetItem<T> adjustHead(int newLevel, SkipListSetItem<T> head)
    {
        SkipListSetItem<T> temp = head;
        SkipListSetItem<T> newHead = new SkipListSetItem<T>(null, newLevel);
        // copying the pointers from the old head to the new head
        for (int i = 0; i <= level; i++)
        {
            if (i > newLevel)
                break;
            newHead.forward.set(i,temp.forward.get(i));
        }
        level = newLevel;
        return newHead;
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
        double log2size = Math.log(size)/Math.log(2);
        if (log2size > maxLevel)
            maxLevel = (int) Math.ceil(log2size);

        int newLevel = randomLevel();
        // if level is the heighest level
        if (newLevel > level)
        {   
            head = adjustHead(newLevel, head);
        }
        
        // temporary array list that holds the pointers to the next lowest node
        ArrayList<SkipListSetItem<T>> updateArrayList = new ArrayList<>(level+1);
        for (int i = 0; i <= level; i++)
        {
            updateArrayList.add(i, null);
        }
        traverseSkipList(e, updateArrayList);
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
        resetList();
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        try
        {
            return find((T)o);
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
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
        boolean val = false;
        try
        {
            if (!(o instanceof Set))
            {
                throw new Exception("Must pass in Set");
            }
            return (hashCode() == o.hashCode());
        }
        catch (Exception e)
        {
            System.out.println(e);
            return false;
        }
        
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
        T e = null;
        try
        {
            e = (T)o;
            if (e == null)
            {
                return false;
            }
            if (!contains(e))
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
            traverseSkipList(e, updateArrayList);
            SkipListSetItem<T> delNode = updateArrayList.get(0).forward.get(0);
            int delHeight = delNode.height();
            SkipListSetItem<T> delHead = head;
            for (int i = 0; i <= level; i++)
            {
                if (i < delHeight)
                {
                    // sets latest node on each levels next node to be the next nodes of the deleted node
                    SkipListSetItem<T> delNodeNext = delNode.forward.get(i);
                    SkipListSetItem<T> prev = updateArrayList.get(i);
                    if (prev == null)
                    {
                        System.out.println("Prev is null at " + i);
                    }
                    else
                        updateArrayList.get(i).forward.set(i, delNodeNext);
                }
            }
            size--;
            
            return true;
        }
        catch (Exception exception)
        {
            System.out.println(exception);
            return false;
        }
        size--;
    
        for (int i = level; i > 0; i--)
        {
            if (delHeight >= i && head.forward.get(i) == null)
            {
                head = adjustHead(i-1, head);
            }
            else
            {
                break;
            }
        }
        return true;

    }

    // removes all objects in a passed in collection
    // returns false if every single remove attempt failed, otherwise true
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean fin = false;
        for (Object o : c)
        {
            boolean res = remove(o);
            if (res == true)
            {
                fin = true;
            }
        }
        return fin;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean fin = false;
        for (Object o : this)
        {
            boolean res = false;
            if (c.contains(o))
            {
                continue;
            }
            res = remove(o);
            if (res == true)
            {
                fin = true;
            }
        }
        return fin;

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
        try
        {
            for(Object o : this)
            {
                list.add((T) o);
            }
            return list.toArray(a);  
        }
        catch (Exception e)
        {
            System.out.println(e);
            return list.toArray(a);  
        }
    }   
}
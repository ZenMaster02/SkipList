package SkipList_Project.SkipList;
import java.util.*;
public class Test {
    public static void main(String [] args)
    {
        SkipListSet<Integer> set = new SkipListSet<>();
        set.add(1);
        set.getHead().printForward();
        set.add(3);
        set.getHead().printForward();
        set.add(2);
        set.getHead().printForward();
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext())
        {
            Integer i = iterator.next();
            System.out.println(i);
        }
    }

}

package SkipList_Project.SkipList;
import java.util.*;
public class Test {
    public static void main(String [] args)
    {
        SkipListSet<Integer> set = new SkipListSet<>();
        set.add(1);
        set.add(2);
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext())
        {
            Integer i = iterator.next();
            System.out.println(i);
        }
    }

}

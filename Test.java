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
        set.getHead().forward.get(0).printForward();
        set.add(2);
        set.getHead().printForward();
        List<Integer> entries = Arrays.asList(8,6,7,5,3,0,9);
        set.addAll(entries);
        set.getHead().printForward();
        System.out.println(set.size());
        Object [] a = set.toArray();
        for (int i = 0; i < a.length; i++)
        {
            System.out.print(a[i] + " ");
        }
        System.out.println();
        Iterator<Integer> iterator = set.iterator();
        while (iterator.hasNext())
        {
            Integer i = iterator.next();
            System.out.println(i);
        }
        // SkipListSet<String> set = new SkipListSet<>();
        // List<String> entries = Arrays.asList("a", "z", "q", "r", "F");
        // set.addAll(entries);
        // String [] a = set.toArray(new String[0]);
        // for (int i = 0; i < a.length; i++)
        // {
        //     System.out.print(a[i] + " ");
        // }

    }

}

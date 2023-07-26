package SkipList_Project.SkipList;
import java.util.*;

public class Test {
    public static void main(String [] args)
    {
        SkipListSet<Integer> set = new SkipListSet<>();
        set.add(1);
        // set.getHead().printForward();
        set.add(3);
        // set.getHead().printForward();
        // set.getHead().forward.get(0).printForward();
        set.add(2);
        // set.getHead().printForward();
        List<Integer> entries = Arrays.asList(5,3,0,9, 4, 6, 8);
        set.addAll(entries);
        // set.getHead().printForward();
        System.out.println("Size: " + set.size());
        // Object [] a = set.toArray();
        // for (int i = 0; i < a.length; i++)
        // {
        //     System.out.print(a[i] + " ");
        // }
        // System.out.println();
        // SkipListSet<String> set = new SkipListSet<>();
        // List<String> entries = Arrays.asList("Roses", "are", "red", "violets", "are", "blue", "covfefe");
        // set.addAll(entries);
        // // String [] a = set.toArray(new String[0]);
        // // for (int i = 0; i < a.length; i++)
        // // {
        // //     System.out.print(a[i] + " ");
        // // }
        System.out.println();
        set.printSkipList();        
        Iterator<Integer> iter = set.iterator();
        while (iter.hasNext())
        {
            Integer i = iter.next();

            if (i % 2 == 1)
            {
                iter.remove();
            }
        }
        set.printSkipList();        

        // Set<Integer> set2 = new HashSet<>();
        // set2.addAll(Arrays.asList(1,2,3,8,6,7,5,9));
        // System.out.println((set2.equals(set)));

    }

}

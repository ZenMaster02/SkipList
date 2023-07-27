package SkipList_Project.SkipList;
import java.util.*;

public class Test {
    public static void main(String [] args)
    {
        SkipListSet<Integer> set = new SkipListSet<>();
        List<Integer> entries = Arrays.asList(5,3,0,9, 4, 6, 8, 1, 2, 3, 4, 10, 11, 12, 13);
        set.addAll(entries);
        System.out.println("Size: " + set.size());
        // System.out.println("Find 14: " + set.contains(14));
        // System.out.println("Find 7: " + set.contains(7));
        // System.out.println("Find 0: " + set.contains(0));
        // System.out.println("Find 13:" + set.contains(13));
        // System.out.println("Find 9:" + set.contains(9));

        set.printSkipList();
        set.reBalance();
        System.out.println();
        set.printSkipList();        

        Set<Integer> set2 = new HashSet<>();
        set2.addAll(Arrays.asList(0, 1, 2, 3, 5, 8, 13));
        System.out.println((set2.equals(set)));
        set.retainAll(set2);
        set.printSkipList();
        System.out.println((set2.equals(set)));
    }

}

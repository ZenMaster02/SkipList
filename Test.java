package SkipList_Project.SkipList;
import java.util.*;

public class Test {
    public static void main(String [] args)
    {
        SkipListSet<Integer> set = new SkipListSet<>();
        List<Integer> entries = Arrays.asList(5,3,0,9, 4, 6, 8, 1, 2, 3, 4, 10, 11, 12, 13);
        set.addAll(entries);
        System.out.println("Size: " + set.size());
        set.printSkipList();
        set.reBalance();
        System.out.println();
        set.printSkipList();        

        // Set<Integer> set2 = new HashSet<>();
        // set2.addAll(Arrays.asList(1,2,3,8,6,7,5,9));
        // System.out.println((set2.equals(set)));
    }

}

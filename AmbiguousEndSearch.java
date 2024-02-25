import java.util.ArrayList;
public class AmbiguousEndSearch {

    private static final boolean debug = true;

    private static void debug(String output) {
        if (debug)
            System.out.println(output);
    }

    /**
     * @param <T>
     * @param list
     * @param target
     * @param index
     * @return
     */
    
    //feel free to make as many functions you need.
    // hint: use item1.compareTo(item2) to compare values of two items of type T

    // a helper function to compare two items:
     private static <T extends Comparable<? super T>>
    int checkIndex(SortedList<T> list, T target, int index) {
        T item = list.get(index);
        debug(String.format("%d: %s\n", index, item));
        if (item == null) return 1;
        return item.compareTo(target);
    }
    
    public static <T extends Comparable<? super T>>
    int locateItem(SortedList<T> list, T target)
    {
       int length = SortedListLeng(list);
       return BinarySearch(list, target, length);

    }

    private static <T extends Comparable<? super T>> int BinarySearch(SortedList<T> list,T target, int length){
        int min = 0; 
        int max = length; 
        while (max >= min){
            int minmax = (min+max)/2;
            if (list.get(minmax) == null) break;
            if (list.get(minmax).compareTo(target) == 0) return minmax;
            else if (list.get(minmax).compareTo(target) < 0){ 
                min = minmax+1;
            }
            else {
                max = minmax -1;
            }

        }
        return -1; 
    }

    private static <T extends Comparable<? super T>> int SortedListLeng (SortedList<T> list){
        if (list.get(0) == null) return -1;
        if (list.get(1) == null) return 1; 
        int min, max, minmax; 
        min = 0;
        max = 1; 
        boolean nullCheckOne = false;

        while (!nullCheckOne){
            min = max;
            max = max*2;
            if (list.get(max) == null){
                nullCheckOne = true; 
            }
        }
        while (min <= max){
            if ((list.get(max+1) == null && list.get(max) != null))  return (max);
            if (list.get(max) == null && list.get(max-1) != null) return (max - 1);
            minmax = (min + max)/2;
            if (list.get(minmax) == null){
                max = minmax - 1; 
            }
            else {
                min = minmax; 
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        
        System.out.println("Test Cases: \n");
        System.out.println("\nOdd array length test");
        ArrayList<Integer> a = new ArrayList<Integer>();
        for (int x = 0; x < 100; x += 2){
            a.add(x);
    }
        SortedList<Integer> list = new SortedList<Integer>(a);

        int position = locateItem(list,50);
        int queries = list.getCount();

        System.out.println("Position = " + position);
        System.out.println("Queries = " + queries);
        System.out.println("\nEven array length test");
        ArrayList<Integer> b = new ArrayList<Integer>();
        for (int x = 0; x < 102; x += 2){
            b.add(x);
    }
        SortedList<Integer> listb = new SortedList<Integer>(b);

        int position1 = locateItem(listb,50);
        int queries1 = listb.getCount();

        System.out.println("Position = " + position1);
        System.out.println("Queries = " + queries1);

        System.out.println("\nOut of range target(above)");
        ArrayList<Integer> c = new ArrayList<Integer>();
        for (int x = 0; x < 102; x += 2){
            c.add(x);
    }
        SortedList<Integer> listc = new SortedList<Integer>(c);

        int position2 = locateItem(listc,110);
        int queries2 = listc.getCount();

        System.out.println("Position = " + position2);
        System.out.println("Queries = " + queries2);

        System.out.println("\nOut of range target(negative)");
        ArrayList<Integer> d = new ArrayList<Integer>();
        for (int x = 0; x < 102; x += 2){
            d.add(x);
    }
        SortedList<Integer> listd = new SortedList<Integer>(d);

        int position3 = locateItem(listd,110);
        int queries3 = listd.getCount();

        System.out.println("Position = " + position3);
        System.out.println("Queries = " + queries3);

        System.out.println("\nOut of range target(odd num)");
        ArrayList<Integer> e = new ArrayList<Integer>();
        for (int x = 0; x < 102; x += 2){
            e.add(x);
    }
        SortedList<Integer> liste = new SortedList<Integer>(e);

        int position4 = locateItem(liste,65);
        int queries4 = liste.getCount();

        System.out.println("Position = " + position4);
        System.out.println("Queries = " + queries4);

        System.out.println("\nStart of array");
        ArrayList<Integer> f = new ArrayList<Integer>();
        for (int x = 0; x < 102; x += 2){
            f.add(x);
    }
        SortedList<Integer> listf = new SortedList<Integer>(f);

        int position5 = locateItem(listf,0);
        int queries5 = listf.getCount();

        System.out.println("Position = " + position5);
        System.out.println("Queries = " + queries5);

        System.out.println("\nEnd of array");
        ArrayList<Integer> g = new ArrayList<Integer>();
        for (int x = 0; x < 102; x += 2){
            g.add(x);
    }
        SortedList<Integer> listg = new SortedList<Integer>(g);

        int position6 = locateItem(listg,100);
        int queries6 = listg.getCount();

        System.out.println("Position = " + position6);
        System.out.println("Queries = " + queries6);

        System.out.println("\nSize one array, target is in");
        ArrayList<Integer> h = new ArrayList<Integer>();
        for (int x = 0; x < 2; x += 2){
            h.add(x);
    }
        SortedList<Integer> listh = new SortedList<Integer>(h);

        int position7 = locateItem(listh,0);
        int queries7 = listh.getCount();

        System.out.println("Position = " + position7);
        System.out.println("Queries = " + queries7);

        System.out.println("\nLarge Array (effiency test");
        ArrayList<Integer> i = new ArrayList<Integer>();
        for (int x = 0; x < 2000000; x += 2){
            i.add(x);
    }
        SortedList<Integer> listi = new SortedList<Integer>(i);

        int position8 = locateItem(listi,420420);
        int queries8 = listi.getCount();

        System.out.println("Position = " + position8);
        System.out.println("Queries = " + queries8);

        System.out.println("\nEmpty Array Test");
        ArrayList<Integer> j = new ArrayList<Integer>();
        for (int x = 0; x < 0; x += 2){
            j.add(x);
    }
        SortedList<Integer> listj = new SortedList<Integer>(j);

        int position9 = locateItem(listj,420420);
        int queries9 = listj.getCount();

        System.out.println("Position = " + position9);
        System.out.println("Queries = " + queries9);

        System.out.println("\nTarget Appears Twice");
        ArrayList<Integer> k = new ArrayList<Integer>();
        for (int x = 0; x < 50; x += 2){
            k.add(x);
            k.add(x);
    }
        SortedList<Integer> listk = new SortedList<Integer>(k);

        int position10 = locateItem(listk,20);
        int queries10 = listk.getCount();

        System.out.println("Position = " + position10);
        System.out.println("Queries = " + queries10);

        


        //for testing, make a SortedList and observe position (for correctness) and queries (for speed)

    }

}

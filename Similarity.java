import java.util.*;

public class Similarity {

    public static <E> Map<Integer, Map<Integer, Double>> jaccard(ArrayList<Set<E>> setList) {

        HashMap<Integer, Map<Integer, Double>> R = new HashMap<Integer, Map<Integer, Double>>();
        Map<E, List<Integer>> countMap = new HashMap<E, List<Integer>>();
        
        int n = setList.size();
        //System.out.println("This is setList size: " + n);
        for (int i = 0; i < n; i++) { // Get the list of set indices for the current element, creating a new list if necessary
            Set<E> currentSet = setList.get(i);
            for (E element : currentSet) {
                List<Integer> setIndices = countMap.get(element);
                //System.out.println("This is setIndices before add: " + setIndices);
                if (setIndices == null) {
                    setIndices = new ArrayList<>();
                    countMap.put(element, setIndices);
                }
                setIndices.add(i);
                //System.out.println("This is setIndices after add: " + setIndices);
            }
        }
//  CountMap contains a mapping from each element to a list of sets it appears in
        for (Map.Entry<E, List<Integer>> entry : countMap.entrySet()) {
            List<Integer> setIndices = entry.getValue();
            //System.out.println("This is value that was pulled: " + setIndices);
            int m = setIndices.size();
            for (int i = 0; i < m; i++) {
                for (int j = i + 1; j < m; j++) {
                    //System.out.println("This is current j value: " + j);
                    int setIndex1 = setIndices.get(i);
                    int setIndex2 = setIndices.get(j);
                    
                    Map<Integer, Double> innerMap = R.get(setIndex1);
                    if (innerMap == null) {
                        innerMap = new HashMap<>();
                        R.put(setIndex1, innerMap);
                    }
                    Double prevValue = innerMap.get(setIndex2);
                    innerMap.put(setIndex2, (prevValue == null ? 0 : prevValue) + 1);
                }
            }
        }
// returnMap contains counts of common elements for each pair of sets
        for (Map.Entry<Integer, Map<Integer, Double>> entry : R.entrySet()) {
            int setIndex1 = entry.getKey();
            //System.out.println("This is key: " + setIndex1);
            Set<E> set1 = setList.get(setIndex1);
            for (Map.Entry<Integer, Double> innerEntry : entry.getValue().entrySet()) {
                int setIndex2 = innerEntry.getKey();
                //System.out.println("This is inner entry size after add: " + innerEntry);
                Set<E> set2 = setList.get(setIndex2);
                double intersectionSize = innerEntry.getValue();
                //System.out.println("This is intersection size after add: " + intersectionSize);
                double unionSize = set1.size() + set2.size() - intersectionSize; // Compute the size of the union of these two sets
                double jaccardIndex = (unionSize == 0) ? 0.0 : intersectionSize / unionSize; // Compute the Jaccard index for these two sets
                entry.getValue().put(setIndex2, jaccardIndex);
            }
        }
        
        return R;
    }

   
    public static void main(String[] args) {

        ArrayList<Set<Integer>> setList = new ArrayList<Set<Integer>>();
        for (int i = 0; i < 10000; i+=2) {
            HashSet<Integer> set1 = new HashSet<Integer>();
            HashSet<Integer> set2 = new HashSet<Integer>();
            set1.add(i * 10);
            set2.add(i * 10);
            for (int j = 1; j < 5; j++) {
                set1.add(i*10+j);
                set2.add(i*10+j+5);
            }
            setList.add(set1);
            setList.add(set2);
        }

        System.out.println(jaccard(setList));

    }
    
}


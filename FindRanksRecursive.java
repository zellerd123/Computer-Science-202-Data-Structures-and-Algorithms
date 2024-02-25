import java.util.*;


public class FindRanksRecursive{
    private static final boolean debug = false;

    private static void debug(String output) {
        if (debug)
            System.out.println(output);
    }


    //Finds the students at given ranks for a list of ranks, returning an array of students
    public static ArrayList<Student> find_students(StudentList class1, StudentList class2, int [] ranks) {

        int length1 = class1.size(); 
        int length2 = class2.size();
        int ranklen = ranks.length;
        ArrayList<Student> finalList = new ArrayList<Student>();
        for (int i = 0; i < ranklen; i++){ //iterates through each rank in the list of ranks
            int k = ranks[i]; 
            int index1 = 0;
            int index2 = 0;
            //int debug2Counter = 0;
            //System.out.println("This is K at the start of each iteration: " + k);
            while (2 > 1){ //equation that always is true, will break out if hits the target
              //Note to self: k currently stuck on 2. Figure it out
              //  System.out.println("This is K in the loop: " + k);
              //  System.out.println("This is length1: " + length1);
              //  System.out.println("This is length2: " + length2);
               // System.out.println("This is index1: " + index1);
               // System.out.println("This is index2: " + index2);
               /*  if (k == 2){
                    debug2Counter++;
                    if (debug2Counter >= 10){
                        break;
                    }
                } */
                if ((length1 == 0 && length2 == 0)|| k > (length1 + length2)){ //base case check
                    finalList.add(null);
                    break; 
                }
                if (index1 == length1){ //adds the student from other class when hit, because could no longer be in class, works for zero case as well
                    //if (class2.get(index2 + k -1).getScore() == class1.get(length1 - 1).getScore()){
                       // finalList.add(class1.get(length1-1));
                   // }
                   // else{
                        finalList.add(class2.get(index2 + k -1)); //because k shrinks as the indexes grow, this will ensure its the right number
                   // }
                    break;
                }
                if (index2 == length2){ //same as index1 concept but for index2
                    finalList.add(class1.get(index1 + k -1));
                    break;
                }
                if (k == 1){ //k will eventually equal one as k is constantly incremented downwards
                    if(class1.get(index1).compareTo(class2.get(index2)) <= 0){ //compare to function to see which student to add
                        finalList.add(class1.get(index1)); //wants to add the higher student
                        break;
                    }
                    else{
                        finalList.add(class2.get(index2));
                        break;
                    }
                   
                }
                int half = k/2; //divides k by 2 to get the middle of the sublist
               // System.out.println("This is the half: " + half);
                //int minTest = Math.min(index2 + half, length2 );
               // System.out.println("This is the min test: " + minTest);
                int rebaseIndex1 = Math.min(index1 + half, length1 )-1; //subtracts 1 to make sure no out of bounds +
                int rebaseIndex2 = Math.min(index2 + half, length2)-1; // helps the system iterate downwards properly
               // System.out.println("This is new index1: " + new_index1);
               // System.out.println("This is new index2: " + new_index2);
                int val1 = class1.get(rebaseIndex1).getScore();
                int val2 = class2.get(rebaseIndex2).getScore();
                if (val1 > val2){ //checks which val is greater to see which k value and index to change
                    k -= (rebaseIndex1 - index1 + 1); //includes the +1 to ensure k will always be lowered by atleast 1
                   // System.out.println("This is k after subtracting: " + k);
                    index1 = rebaseIndex1 + 1; //creates the same increase in index to reflect the change in k
                }
                else{ //same as above but for the other side
                    k -= (rebaseIndex2 - index2 + 1);
                    index2 = rebaseIndex2 + 1; 
                }
            }
        }
        return finalList;
      
    }


    public static void main(String [] args){
        //System.out.println("Hello This is a test");
        Random rng = new Random();
        int bound = 100; // max score
        

        // Example test case
        ArrayList<Student> c1 = new ArrayList<Student>();

        for(int x = 0; x < 32; x +=2){
            String name = "c1-" + x;
            int score = rng.nextInt(bound);
            //int score = x;
            Student s = new Student(name, score);
            c1.add(s);
        }

        StudentList class1 = new StudentList(c1);



        ArrayList<Student> c2 = new ArrayList<Student>();


        for(int x = 1; x < 33; x +=2){
            String name = "c2-" + x;
             int score = rng.nextInt(bound);
            //int score = x;
            Student s = new Student(name, score);
            c2.add(s);
        }

        c2.add(new Student("c2-17", 2));

        StudentList class2 = new StudentList(c2);

        debug(class1.toString());
        debug("");
        debug(class2.toString());

        int[] ranks = {1,2,4};

        ArrayList<Student> students = find_students(class1, class2, ranks);

        int max_len = class1.size() + class2.size();

        debug("max_len " + max_len);
        debug("class1 size " + class1.size());
        debug("class2 size " + class2.size());

        debug(students.toString());

        System.out.println(" Test Case 1: Normal test");
        ArrayList<Student> c3 =new ArrayList<>();
        c3.add(new Student("c2", 95));
        c3.add(new Student("c94", 91));
        c3.add(new Student("c93", 90));
        StudentList class3 = new StudentList(c3);
        ArrayList<Student> c4 = new ArrayList<>();
        c4.add(new Student("c9", 87));
        c4.add(new Student("c55", 82));
        c4.add(new Student("c7", 70));
        StudentList class4 = new StudentList(c4);
        System.out.println(find_students(class3, class4, new int[]{1, 3, 5, 6}));

        System.out.println("Test Case 2: One class empty");
         ArrayList<Student> c5 = new ArrayList<Student>();
         c5.add(new Student("c19", 89));
         c5.add(new Student("c5", 83));
         c5.add(new Student("c47", 71));
         StudentList class5 = new StudentList(c5);
         ArrayList<Student> c6 = new ArrayList<>();
         StudentList class6 = new StudentList(c6);
          System.out.println(find_students(class5, class6, new int[]{1, 2, 3}));

        System.out.println("Test Case 3: Students have same scores");
         ArrayList<Student> c7 = new ArrayList<Student>();
         c7.add(new Student("c19", 89));
         c7.add(new Student("c21", 89));
         StudentList class7 = new StudentList(c7);
         ArrayList<Student> c8 = new ArrayList<Student>();
         c8.add(new Student("c4", 89));
         c8.add(new Student("c55", 89));
         StudentList class8 = new StudentList(c8);
         System.out.println(find_students(class7, class8, new int[]{1, 2, 3, 4}));

         System.out.println("Test Case 4: Rank beyond total num of students");
         ArrayList<Student> c9 = new ArrayList<Student>();
         c7.add(new Student("c19", 59));
         StudentList class9 = new StudentList(c9);
         ArrayList<Student> c10 = new ArrayList<Student>();
         c8.add(new Student("c4", 49));
         StudentList class10 = new StudentList(c10);
          System.out.println(find_students(class9, class10, new int[]{5}));
    }

        
}


// TO DEBUG:

// Code doesn't work in the end-of-list cases when elements have the same score. 


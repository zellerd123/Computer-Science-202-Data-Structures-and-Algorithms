import java.util.*;


public class FindRanks{
    private static final boolean debug = false;

    private static void debug(String output) {
        if (debug)
            System.out.println(output);
    }


    public static ArrayList<Student> find_students1(StudentList class1, StudentList class2, int [] ranks) {
        int j = 0;
        int i = 0;
        int k = 1;
        ArrayList<Student>  studentList = new ArrayList();
        for (int q = 0; q<=ranks[q]; q=q+1){
            if (ranks[q]<= class1.size()+class2.size()){
                while (k<ranks[q] &&  i<class1.size() && j<class2.size()){
                    if (class1.get(i).getScore() < class2.get(j).getScore()){
                        k = k+1;
                        if (k==ranks[q]){
                            studentList.add(class2.get(j));
                        }
                        j = j+1;
                    } else {
                        k = k+1;
                        if (k == ranks[q]){
                            studentList.add(class1.get(i));
                        }
                        i = i+1;
                    }
                    
        
                }
                if (i>class1.size()){
                    while (k!=ranks[q]){
                        j = j+1;
                    }
                    studentList.add(class2.get(j));
                }
                if (j<class2.size()){
                    while(k!=ranks[q]){
                        i = i+1;
                    }
                    studentList.add(class1.get(i));
                }
            }
        }
        
        return studentList;
    }



    public static ArrayList<Student> find_students2(StudentList class1, StudentList class2, int [] ranks) {

        // Write code here

        return null;
    }

    public static void main(String [] args){
        Random rng = new Random();
        int bound = 100; // max score
        

        // Example test case
        ArrayList<Student> c1 = new ArrayList<Student>();

        for(int x = 0; x < 10; x +=1){
            String name = "c1-" + x;
            int score = rng.nextInt(bound);
            Student s = new Student(name, score);
            c1.add(s);
        }

        StudentList class1 = new StudentList(c1);



        ArrayList<Student> c2 = new ArrayList<Student>();


        for(int x = 10; x < 25; x +=1){
            String name = "c2-" + x;
            int score = rng.nextInt(bound);
            Student s = new Student(name, score);
            c2.add(s);
        }

        StudentList class2 = new StudentList(c2);

        int [] ranks = {1,3,10,15,20,100, 2,4,6,8};
        debug(class1.toString());
        debug("");
        debug(class2.toString());

        int queries1 = StudentList.getCount();
        ArrayList<Student> students1 = find_students1(class1, class2, ranks);
        int queries2 = StudentList.getCount();
        System.out.println("class1:" + class1);
        System.out.println("class2:" + class2);
        System.out.println("ranks: " + ranks.toString());
        System.out.println("# queries find_students1: " + (queries2-queries1));

        
        ArrayList<Student> students2 = find_students2(class1, class2, ranks);
        int queries3 = StudentList.getCount();
        System.out.println("# queries find_students2: " + (queries3-queries2));

        debug(students1.toString());
        debug(students1.toString());


        // Add additional test cases here
    }

        
}
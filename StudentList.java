import java.util.*;

public class StudentList
{
    // the items
    private ArrayList<Student> items;

    // the counter
    private static int queries = 0; //same var for all instances

    // Empty student list
    public StudentList() {
        this(new ArrayList<Student>());
    }

    // Constructor from an arraylist
    public StudentList(ArrayList<Student> arr) {
        this.items = new ArrayList<Student>();
        for(Student x: arr){
            items.add(x);
        }

        // Collections.sort(items, Collections.reverseOrder());
        Collections.sort(items);
        // this.items = arr;
    }

    public String toString() {
        String str = "";
        for(Student s: items){
            str+=s.toString()+"\n";
        }
        return str;
    }

    // query the data structure
    public Student get(int index) {
        queries++;
        return items.get(index);
    }

    // add to the end of the list
    public void add(Student s){
       items.add(s);
    }


    // get size
    public int size(){
        return items.size();
    }

    public static int getCount() { 
        return queries; 
    }
    public static void resetCount(){ 
        queries = 0; 
    }

    public static void main(String[] args){

        Random rng = new Random();
        int bound = 100; // max score
        
        ArrayList<Student> c1 = new ArrayList<Student>();
        for(int x = 0; x < 10; x +=1){
            String name = "c1 student" + x;
            int score = rng.nextInt(bound);
            Student s = new Student(name, score);
            System.out.println(s);
            c1.add(s);
        }
        System.out.println();

        StudentList class1 = new StudentList(c1);

        Student student1 = class1.get(0);
        System.out.println(student1);
        System.out.println("# queries" + StudentList.getCount());
        Student student2 = class1.get(1);
        System.out.println(student2);
        System.out.println("# queries" + StudentList.getCount());


     }


}

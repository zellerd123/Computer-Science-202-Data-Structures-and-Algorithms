import java.util.*;

public class Student implements Comparable<Student>{
    private String name;
    private int score;

    public Student(String name, int score){
        this.name = name; 
        this.score = score;
    }

    public int getScore(){
        return this.score;
    }

    public String getName(){
        return this.name;
    }

    @Override 
    public int compareTo(Student student2){
        if (this.getScore() > student2.getScore()){
            return -1; // student1 has higher rank
        } 
        else if(this.getScore() < student2.getScore()){
            return 1; // student2 has higher rank
        }
        else {
            return -this.getName().compareTo(student2.getName());
        }
        
    }

    @Override public String toString() {
        return "(" + this.name + "," + this.score + ")";
    }

    public static void main(String[] args){
        String name = "1";
        // int score = 5;
        // int score2 = 7;
        // int score3 = 
        Student student1 = new Student("A", 5);
        Student student2 = new Student("B", 7);
        Student student3 = new Student("C", 2);
        Student student4 = new Student("D", 5);
        System.out.println(student1.compareTo(student1));
        System.out.println(student1.compareTo(student2));
        System.out.println(student1.compareTo(student3));
        System.out.println(student1.compareTo(student4));

        System.out.println(student1);
    }

   

}
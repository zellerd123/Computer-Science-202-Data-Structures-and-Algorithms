import java.util.*;
import java.io.*;

public class PrettyPrint {

    public static double calcSlack(int current_length, int line_length, SlackFunctor sf) {
        return sf.f(line_length - current_length);
    }

     public static List<Integer> splitWords(int[] lengths, int L, SlackFunctor sf) {
        int len = lengths.length; 
        ArrayList<Double> slacks = new ArrayList<Double>(lengths.length+1);
        ArrayList<Integer> breaks = new ArrayList<Integer>(lengths.length+1);
        slacks.add(0.0); //starting slack is always zero
        breaks.add(-1); //added this to have both in same for loop
        for (int i = 0; i < len + 1; i++){ //adds the values for slacks and breaks so they can be modified later
            slacks.add(Double.MAX_VALUE);
            breaks.add(-1);
        }
        
        for (int size : lengths){ //too large for program check 
            if (size > L){
                return null; 
            }
        }

        for (int i = 0; i < len; i++) { //first for loop to go through the entire list
            int sumOfLengths = 0; 
            for (int j = i; j >= 0; j--) { //second loop checks downwards from the searched words less than i
                //System.out.println("This is J: " + j);
                sumOfLengths += lengths[j]; // adds the length of each new word on each loop
                if (sumOfLengths > L) break; //breaks out if the word overreaches length
                double currSlack = L - sumOfLengths; //grabs the current slack 
                //System.out.println("This is the currstack");
                double slackMult = (j > 0) ? sf.f((int)currSlack) + slacks.get(j-1) : sf.f((int)currSlack);//if its greater than zero increments in stack, if zero adds it
    
                if (slackMult < slacks.get(i)) { //sets the new slack amount if this method is better than the current
                        slacks.set(i, slackMult); 
                        breaks.set(i, j);//also sets the break so can be put into the output array 
                        //System.out.println("this is slacks: " + slacks);
                        //System.out.println("This is breaks: " + breaks);
                }
                
                if (j > 0) sumOfLengths++;//adds 1 to account for spaces
            }
        }
    
    
    

        LinkedList<Integer> output = new LinkedList<>(); 
        int i = len -1;
        while (i >= 0) { //goes through the list, starting with the last breaks value
            //System.out.println("This is the i value: " + i);
            output.addFirst(i); //and going down to the first. If breaks calls a negative value, the function ends. 
            i = breaks.get(i) - 1; //That is how the base case holds. 
        }

        return output;
    }

    public static List<Integer> greedy_print(int[] lengths, int L, SlackFunctor sf) {

        /*  Greedy implementation
            provided in starter file

            It just places words on lines and inserts line breaks whenever the length
            would exceed L.
        */

        ArrayList<Integer> breaks = new ArrayList<Integer>();
        int current_length = 0;
        int current_end = -1;
        for (int word: lengths) {
            if (word > L) {
                return null;
            }

            if (current_length > 0 && current_length + 1 + word > L) {
                breaks.add(current_end);
                current_length = 0;
            }

            if (current_length > 0)
                current_length++;
            current_length += word;

            current_end++;
        }

        breaks.add(current_end);

        return breaks;
    }
    

    public static String help_message() {
        return
            "Usage: java PrettyPrint line_length [inputfile] [outputfile]\n" +
            "  line_length (required): an integer specifying the maximum length for a line\n" +
            "  inputfile (optional): a file from which to read the input text (stdin if a hyphen or omitted)\n" +
            "  outputfile (optional): a file in which to store the output text (stdout if omitted)"
            ;
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Error: required argument line_length missing");
            System.err.println(help_message());
            System.exit(1);
            return;
        }

        int line_length;
        try {
            line_length = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("Error: could not find integer argument line_length");
            System.err.println(help_message());
            System.exit(2);
            return;
        }

        Scanner input = null;
        PrintStream output = null;
        if (args.length < 2) {
            input = new Scanner(System.in);
        }
        else if (args.length >= 2) {
            if (args[1].equals("-")) {
                input = new Scanner(System.in);
            } else {
                try {
                    input = new Scanner(new File(args[1]));
                } catch (FileNotFoundException e) {
                    System.err.println("Error: could not open input file");
                    System.err.println(help_message());
                    System.exit(3);
                    return;
                }
            }
        }

        if (args.length >= 3) {
            try {
                output = new PrintStream(args[2]);
            } catch (FileNotFoundException e) {
                System.err.println("Error: could not open output file");
                System.err.println(help_message());
                input.close();
                System.exit(4);
                return;
            }
        } else {
            output = System.out;
        }

        ArrayList<String> words = new ArrayList<String>();
        while (input.hasNext()) {
            words.add(input.next());
        }
        input.close();

        int[] lengths = new int[words.size()];
        for (int i = 0; i < words.size(); i++) {
            lengths[i] = words.get(i).length();
        }
        
        List<Integer> breaks = splitWords(lengths, line_length,
            new SlackFunctor() {
                public double f(int slack) { return slack * slack; }
            });

        System.out.println(breaks);

        if (breaks != null) {
            int current_word = 0;
            for (int next_break : breaks) {
                while (current_word < next_break) {
                    output.print(words.get(current_word++));
                    output.print(" ");
                }
                output.print(words.get(current_word++));
                output.println();
            }

            output.close();
            System.exit(0);
            return;
    
        } else {
            System.err.println("Error: formatting impossible; an input word exceeds line length");
            output.close();
            System.exit(5);
            return;
        }
    }
}

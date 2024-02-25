import java.io.*;
import java.util.*;

public class Trie implements java.io.Serializable{
    
    // node class
    private static class TrieNode implements java.io.Serializable{
        boolean isWord;
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    }

    // root node
    private TrieNode root = new TrieNode();

    // method to implement
    public HashMap<String, Integer> suggestions(String target, int dist)
    {
        HashMap<String, Integer> words = new HashMap<String, Integer>(); //initializes the 4 extra variables needed for the recursion
        String prefix = ""; //creation of new words
        int index = 0; //current point on target word
        int currTolerence = 0; //number of changes already made
       
        words = suggestionsHelper(root, target, prefix, index, dist, currTolerence, words);//Calls the recursive function with the other needed variables

        return words;
    }

    public HashMap<String, Integer> suggestionsHelper(TrieNode node, String target, String prefix, int index, int dist, int currTolerence, HashMap<String, Integer> words){
        if (node == null || currTolerence>dist){ //base case
            return words;
        } 
       
        //Other base case for when end of word is hit
        if (index == target.length()){ //because it recurses once more after hitting word, important for it to be target.length() not target.length()-1

             if (node.isWord){
                if (words.containsKey(prefix)){
                    //System.out.println("Word was hit here, the tolerence is: " + currTolerence);
                    if (currTolerence < words.get(prefix)){ //checks to see if word was already added with a lower tolerence before adding
                        words.put(prefix, currTolerence); //if new tolerence is lower, adds that
                    }
                } 
                else{
                    words.put(prefix, currTolerence); //if word not already added, adds it
                }
                for (Character child: node.children.keySet()){ //recursive call below grabs all words at end of word within allocated distance max
                    suggestionsHelper(node.children.get(child), target, prefix+child, index, dist, currTolerence+1, words);//because index isn't increased, this can run until currtolerence reaches distance
                }   
            } 
        }
    
        if (index < target.length()){ //normal recursive runthrough
            char c = target.charAt(index); //grabs the current c value
            if (node.children.keySet().contains(c)){ //if the child has the value, calls the function with increased index & prefix with no change in tolerence
                suggestionsHelper(node.children.get(c), target, prefix+c, index+1, dist, currTolerence, words);
            }
            
            for (Character child: node.children.keySet()){ //next case, goes through all the other children
                if (child != c){ //swaps the letter and moves to the next character, shouldn't do this for c since c will not have a tolerence increase
                    //System.out.println("This is the value of the tolerence if != c: " + currTolerence);
                    suggestionsHelper(node.children.get(child), target, prefix+child, index+1, dist, currTolerence+1, words);
                }
                if((index +1 < target.length()) && child == target.charAt(index+1)){ //checks for a deletion, adds the next value if this is the case and increases index by 2 to reflect this skip
                    //System.out.println("This is the value of the tolerence for potential deletion: " + currTolerence);
                    //System.out.println("This is the value of the prefix for potential deletion: " + prefix);
                    //System.out.println("This is the value of the target for potential deletion: " + target);
                    suggestionsHelper(node.children.get(child), target, prefix+target.charAt(index+1), index+2, dist, currTolerence+1, words);
                }//checks for an insertion. By keeping the index the same but moving onto the next, effectively checks if a new character was inserted, essentially the inverse of the above function
                suggestionsHelper(node.children.get(child), target, prefix+child, index, dist, currTolerence+1, words);
               
            }   
        }     
        return words; 
    }

    //

    // method to add a string
    public boolean add(String s) {
        s = s.trim().toLowerCase();

        TrieNode current = root;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                TrieNode child = current.children.get(c);
                if (child == null) {
                    child = new TrieNode();
                    current.children.put(c, child);
                }
                current = child;
            }
        }

        if (current.isWord)
            return false;
        
        current.isWord = true;
        return true;
    }

    // method to check if a string has been added
    public boolean contains(String s) {
        s = s.trim().toLowerCase();

        TrieNode current = root;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLowerCase(c)) {
                TrieNode child = current.children.get(c);
                if (child == null) {
                    return false;
                }
                current = child;
            }
        }

        return current.isWord;
    }

    // empty constructor
    public Trie() {
        super();
    }

    // constructor to add words from a stream, like standard input
    public Trie(InputStream source) {
        Scanner scan = new Scanner(source);
        addWords(scan);
        scan.close();
    }

    // constructor to add words from a file
    public Trie(String filename) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(filename));
        addWords(scan);
        scan.close();
    }

    // helper function to add words from a scanner
    private void addWords(Scanner scan) {
        while (scan.hasNext()) {
            add(scan.next());
        }
    }

    public Iterable<String> getAll() {
        ArrayList<String> all = new ArrayList<String>();
        getAll(root, "", all);
        return all;
    }

    private void getAll(TrieNode root, String prefix, ArrayList<String> all) {
        if (root == null) return;

        if (root.isWord)
            all.add(prefix);

        for (char c : root.children.keySet()) {
            getAll(root.children.get(c), prefix + c, all);
        }
    }

    public static void emptyTest() {
        Trie dictionary = new Trie();
        try (FileOutputStream fos = new FileOutputStream("empty_trie_input.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           oos.writeObject(dictionary);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream("empty_trie.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           HashMap<String, Integer> all = dictionary.suggestions("colgate", 3);
           oos.writeObject(all);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }

    public static void noneTest() {
        Trie dictionary = new Trie();
        dictionary.add("spring");
        dictionary.add("summer");
        dictionary.add("winter");
        dictionary.add("autumn");
        try (FileOutputStream fos = new FileOutputStream("no_suggestions_input.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           oos.writeObject(dictionary);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream("no_suggestions.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           HashMap<String, Integer> all = dictionary.suggestions("university", 5);
           oos.writeObject(all);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }

    public static void exactMatchTest() {
        Trie dictionary = new Trie();
        dictionary.add("spring");
        dictionary.add("summer");
        dictionary.add("winter");
        dictionary.add("autumn");
        try (FileOutputStream fos = new FileOutputStream("exact_match_only_input.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           oos.writeObject(dictionary);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream("exact_match_only.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           HashMap<String, Integer> all = dictionary.suggestions("spring", 3);
           oos.writeObject(all);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }

    public static void only2Test() {
        Trie dictionary = new Trie();
        dictionary.add("fall");
        dictionary.add("fill");
        dictionary.add("pump");
        dictionary.add("call");
        dictionary.add("all");
        dictionary.add("pal");
        dictionary.add("pale");
        dictionary.add("pail");
        dictionary.add("water");
        dictionary.add("watch");
        dictionary.add("fullest");
        try (FileOutputStream fos = new FileOutputStream("only_dist2_suggestions_input.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           oos.writeObject(dictionary);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream("only_dist2_suggestions.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           HashMap<String, Integer> all = dictionary.suggestions("pull", 3);
           oos.writeObject(all);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }

    public static void zeroThreeTest() {
        Trie dictionary = new Trie();
        dictionary.add("everest");
        dictionary.add("averse");
        dictionary.add("ovens");
        dictionary.add("verse");
        dictionary.add("ever");
        dictionary.add("roar");
        dictionary.add("events");
        dictionary.add("tee");
        dictionary.add("tie");
        dictionary.add("verdant");
        dictionary.add("everywhere");
        dictionary.add("everybody");
        dictionary.add("seem");
        dictionary.add("severance");
        dictionary.add("roast");
        try (FileOutputStream fos = new FileOutputStream("dist0_3_suggestions_input.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           oos.writeObject(dictionary);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream("dist0_3_suggestions.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           HashMap<String, Integer> all = dictionary.suggestions("ever", 4);
           oos.writeObject(all);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
    }

    public static void web2algorithm() {
        Trie dictionary = null;
        try {
            dictionary = new Trie("ospd.txt");
        } catch (FileNotFoundException e) {
        } finally {
            System.gc();
        }
        try (FileOutputStream fos = new FileOutputStream("ospd_input.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           oos.writeObject(dictionary);
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream("ospd_algorithm.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           HashMap<String, Integer> all = dictionary.suggestions("algorithm", 3);
           oos.writeObject(all);
        } catch (IOException ex) {
           ex.printStackTrace();
        }

    }


    public static void web2program() {
        Trie dictionary = null;
        try {
            dictionary = new Trie("ospd.txt");
        } catch (FileNotFoundException e) {
        } finally {
            System.gc();
        }
        try (FileOutputStream fos = new FileOutputStream("ospd_program.dat");
           ObjectOutputStream oos = new ObjectOutputStream(fos)) {
           HashMap<String, Integer> all = dictionary.suggestions("program", 5);
           oos.writeObject(all); 
        } catch (IOException ex) {
           ex.printStackTrace();
        }

    }

    // main function for testing
    public static void main(String[] args) {
        Trie.emptyTest();
        Trie.noneTest();
        Trie.exactMatchTest();
        Trie.only2Test();
        Trie.zeroThreeTest();
        Trie.web2algorithm();
        Trie.web2program();


    }

}

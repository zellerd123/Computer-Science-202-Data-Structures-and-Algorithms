import java.util.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;


//Class created for storing the x & y value, direction for passing through, and the value in the node
class Position{
    int x, y, value;
    String direction;

    public Position(int x, int y, int value, String direction){
        this.x = x;
        this.y = y; 
        this.value = value;
        this.direction = direction;
    }
}

public class NumberMaze {

    //This algorithm works by using a breadth first search. A 2D boolean array is used to keep track of visited nodes.
    // The Position class I created keeps track of the x/y coords, value, and stored direction of each node in the BFS queue.
    //by storing in a Queue with the position class, the space used at any given time will only be the size of the queue & the boolean array
    public static String solveMaze(int[][] maze) {
        int length = maze.length;  
        boolean[][] visited = new boolean[length][length]; //keeps track of which nodes have already been visited to avoid loops and repeats
        Queue<Position> queue = new LinkedList<>(); //creates a queue to facilitate BFS
        queue.offer(new Position(0, 0, maze[0][0], "")); //adds the first point
        visited[0][0] = true; //marks the first node as visited
        while (!queue.isEmpty()){ //loops until there is no further nodes to be visited OR fastest path has been found
            Position current = queue.poll(); //grabs first position in the queue. By storing this as a class, we have access to current x, y coordinate, its value, and direction
            // By having it stored as a class in a queue, we can avoid having a lengthy list of stored variables, saving space and time spent confused on how to organize
            if (current.x == length -1 && current.y == length - 1){ //checks to make sure at end
                //System.out.println("This is curr direction " + current.direction);
                return current.direction;
            }
            String[] directionStrings = {"D", "R", "U", "L"}; //variable that stores the direction strings
            int[][] directions = {{current.value, 0}, {0, current.value}, {-current.value, 0}, {0, -current.value}}; //adds each x/y coordinate switch so don't have to do call 4 times in the loop
            for (int i = 0; i < directions.length; i++){
                int newColumn = current.x + directions[i][0]; //grabs new column by adding shift
                int newRow = current.y + directions[i][1]; //grabs new row same method
                if (newColumn >= 0 && newRow >= 0 && newColumn < length && newRow < length && !visited[newColumn][newRow]){ //ensures columns are not out of bounds and then sees if visited
                    visited[newColumn][newRow] = true; //if not visited, marks as now visited
                    queue.offer(new Position(newColumn, newRow, maze[newColumn][newRow], current.direction + directionStrings[i]));//adds position to queue, core of BFS
                }

            }
        }
        return ""; //returns null if no path is found
    }


    private static int[][] makeRandomMaze(int n) {
        Random rng = new Random();
        int[][] maze = new int[n][n];

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                maze[i][j] = rng.nextInt(2*n/3+1);
        
        maze[n-1][n-1] = 0;

        return maze;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        
        int[][] maze = makeRandomMaze(n);
        String sol = solveMaze(maze);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%2d ", maze[i][j]);
            }
            System.out.println();
        }

        System.out.printf("Solution: %s\n", sol);
    }
}

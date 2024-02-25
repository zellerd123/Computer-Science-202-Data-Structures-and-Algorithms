import java.lang.reflect.MalformedParametersException;

public class Program7 {

    public static Location findHill(ElevationGrid grid) {
        //your code goes here. Feel free to use helper functions
        if (grid != null){ //check to make sure grid is parsed properly
            return findHillRecursiveAttempt2(grid, 0, grid.height() -1);
        }
        return null;
    }
    private static Location findHillRecursiveAttempt2(ElevationGrid grid, int firstRow, int LastRow) {
        if (firstRow > LastRow){ //recursive basecase for if a hill is not found
            System.out.println("Hill not found");
            return null; 
        }
            
        int midRow = (firstRow + LastRow) / 2; //core of divide and conquer
        Location highestRow = findHighestRow(grid, midRow); //grabs the position of the highest value in the row
        if (grid.checkBigger(highestRow)) return highestRow; //if this is a hill we are done

        if (midRow - 1 >= 0 && grid.compare(midRow, highestRow.getCol(), midRow - 1, highestRow.getCol()) < 0){
            return findHillRecursiveAttempt2(grid, firstRow, midRow - 1); //if upper is less than it goes down otherwise
        }  
        else return findHillRecursiveAttempt2(grid, firstRow + 1, LastRow);//it goes up
    }

    public static Location findHighestRow(ElevationGrid grid, int row){//finds the highest row
        int highestCol = 0;
        double maxVal = grid.zoeiomwc(row, 0);//uses the method inside elevationgrid to avoid using compare
        //System.out.println("This is grid width: " + grid.width());
        for (int i = 1; i < grid.width(); i++) {
            double currVal = grid.zoeiomwc(row, i);
            if (currVal > maxVal) {
                maxVal = currVal;
                highestCol = i;
            }
        }
        return new Location(row, highestCol);
    }
/* 


    COULD NOT GET THE CODE TO WORK PERFECTLY WHEN NARROWING IT INTO QUADRANTS. THE SYSTEMETIC NARROWING IN THIS METHOD
    WAS VERY DIFFICULT TO DEBUG, INSTEAD IN ATTEMPT #2 OPTED TO USE JUST DIVIDING INTO HALFS

    public static Location findHillRecursiveAttempt1(ElevationGrid grid, int firstCol, int lastCol, int firstRow, int lastRow){
        if (firstCol > lastCol || firstRow > lastRow){
            System.out.println("Hill not found");
            return new Location(0, 0); 
        }
        if (firstCol == lastCol && firstRow == lastRow){
            return new Location(firstRow, firstCol);
        }
        int midColumn = (firstCol + lastCol)/2;
        int midRow = (firstRow + lastRow)/2; 
        //System.out.println("Entering highestRow call");
        Location highestRow = findHighestRow(grid, midColumn, firstRow);
        if (highestRow == null) {
            System.out.println("Highest row location is null");
            return null;
        }
        //System.out.println("This is the row: " + highestRow.getRow() + "This is the column: " + highestRow.getCol());
        //System.out.println("Leaving highestRow call");
        if (grid.checkBigger(highestRow)){
            return highestRow;
        }
        Location highestCol = findHighestCol(grid, midRow, firstCol);
        if (highestCol == null) {
            System.out.println("Highest column location is null");
            return null;
        }
        if (grid.checkBigger(highestCol)){
            return highestCol;
        }
        int compareResult = (grid.compare(highestRow.getRow(), highestRow.getCol(), highestCol.getRow(), highestCol.getCol()));
        if (compareResult > 0){
            if (midRow - 1 >= 0 && grid.compare(midRow, highestRow.getCol(), midRow -1, highestRow.getCol()) < 0) {
                //
                if (highestRow.getCol() < midColumn){
                    return findHillRecursively(grid, firstCol, midColumn - 1, firstRow, midRow-1);
                }
                else{
                    return findHillRecursively(grid, midColumn, midColumn - 1, midRow+1, lastRow);
                }
            } 
            else{
                if (highestRow.getCol() < midColumn){
                    return findHillRecursively(grid, midColumn + 1, lastCol, firstRow, midRow-1);
                }
                else{
                    return findHillRecursively(grid, midColumn + 1, lastCol, midRow+1, lastRow);
                }
            }
           
        }
        else{
            if (midRow - 1 >= 0 && grid.compare(midRow, highestCol.getCol(), midRow-1, highestCol.getCol()) < 0) {
                if (highestCol.getRow() < midRow){
                    return findHillRecursively(grid, firstCol, midColumn-1, firstRow, midRow - 1);
                }
                else{
                    return findHillRecursively(grid, midColumn+1, lastCol, firstRow, midRow - 1);
                }
               
            } 
            else{
                if (highestCol.getRow() < midRow){
                    return findHillRecursively(grid, firstCol, midColumn-1, midRow + 1, lastRow);
                }
                else{
                    return findHillRecursively(grid, midColumn+1, lastCol, midRow + 1, lastRow);
                }
            }   
        }

    }

    public static Location findHighestCol(ElevationGrid grid, int column, int row){
        
        int highestRow = 0;
         /* 
        if (grid.checkBigger(new Location(highestRow, column))){
            return new Location(highestRow, column);
        }
        
        
        //System.out.println("This is grid height: " + grid.height());
        double maxVal = grid.zoeiomwc(0, column);
        for (int i = row; i < grid.height(); i++) {
            double currVal = grid.zoeiomwc(i, column);
            if (currVal > maxVal) {
                highestRow = i;
                 
                if (grid.checkBigger(new Location(highestRow, column))){
                    System.out.println("This is the highest row: " + highestRow);
                    return new Location(highestRow, column);
                }
                
            }
        }
        
        return new Location(highestRow, column);
    }
    /* 
    public static Location findHighestRow(ElevationGrid grid, int row, int col){
        int highestCol = 0;
         /* 
        if (grid.checkBigger(new Location(row, highestCol))){
            return new Location(row, highestCol);
        }
        
        double maxVal = grid.zoeiomwc(row, 0);
        //System.out.println("This is grid width: " + grid.width());
        for (int i = col; i < grid.width(); i++) {
            double currVal = grid.zoeiomwc(row, i);
            if (currVal > maxVal) {
                highestCol = i;
                /* 
                if (grid.checkBigger(new Location(row, highestCol))){
                    return new Location(row, highestCol);
                }
                
                
            }
        }
        return new Location(row, highestCol);
    }
        */ 
    
    private static double[][] testGrid =
    {
        { 1.0, 2.0, 3.0 },
        { 6.0, 5.0, 4.0 },
        { 7.0, 8.0, 9.0 }
    };


    public static void main(String[] args) {
        int height = 5, width = 5;
        if (args.length > 0)
            height = Integer.parseInt(args[0]);

        if (args.length > 1)
            width = Integer.parseInt(args[1]);

        long seed = System.currentTimeMillis();
        if (args.length > 2)
            seed = Long.parseLong(args[2]);

        ElevationGrid grid;
        if (height == -1)
            grid = new ElevationGrid(testGrid);
        else
            grid = new ElevationGrid(height, width, seed);

        Location loc = findHill(grid);
        int comps = grid.comparisons();

        System.out.printf("Grid:\n%s\n\n", grid);
        System.out.printf("Seed used: %d\n", grid.seed());
        System.out.printf("Location found: (%d, %d)\n", loc.getRow(), loc.getCol());
        System.out.printf("Number of comparisons used: %d\n", comps);
    }

}

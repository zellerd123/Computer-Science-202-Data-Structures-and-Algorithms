import java.util.*;

public class ElevationGrid {

    private double[][] grid;
    private int height, width;
    private int comps = 0;
    private long seed;

    /* Initialize the grid to a given size using the current time as the RNG seed */
    public ElevationGrid(int height, int width) {
        this(height, width, System.currentTimeMillis());
    }

    /* Initialize the grid to a given size using a given RNG seed
       (useful to reproduce an example) */
    public ElevationGrid(int height, int width, long seed) {
        Random rng = new Random(seed);
        this.height = height;
        this.width = width;
        this.grid = new double[height][width];
        this.seed = seed;

        Set<Double> values = new HashSet<Double>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Double value = null;
                while (true) {
                    value = rng.nextDouble() * 10.0;
                    if (!values.contains(value))
                        break;
                }
                grid[i][j] = value;
            }
        }
        values.clear();
        values = null;
        System.gc();

        int curRow = 0;
        int curCol = 0;
        int bound = height*width;
        for (int i = 1; i < bound; i++) {
            int pos = i + rng.nextInt(bound-i);
            int pullRow = pos / width;
            int pullCol = pos % width;
            double temp = grid[curRow][curCol];
            grid[curRow][curCol] = grid[pullRow][pullCol];
            grid[pullRow][pullCol] = temp;
            if (++curCol == width) {
                curCol = 0;
                curRow++;
            }
        }
    }

    /* Initialize the grid to a given size using
       a given 2D array from which to copy numbers */
    public ElevationGrid(double[][] init) {
        this.height = init.length;
        this.width = (init.length > 0) ? init[0].length : 0;
        this.grid = new double[height][];
        this.seed = -1;

        for (int i = 0; i < height; i++) {
            grid[i] = Arrays.copyOf(init[i], init[i].length);
        }
    }

    /* Returns the result of comparing grid positions (row1, col1) and (row2, col2)
        - returns 0 if positions are equal
        - returns 1 if grid[row1][col1] > grid[row2][col2]
        - returns -1 if grid[row1][col1] < grid[row2][col2]
    */
    public int compare(int row1, int col1, int row2, int col2) {
        this.comps++;
        double diff = this.grid[row1][col1] - this.grid[row2][col2];
        if (diff < 0.0) return -1;
        else if (diff > 0.0) return 1;
        else return 0;
    }

    /* Returns the number of comparison operations used so far. */
    public int comparisons() {
        return this.comps;
    }

    /* Returns the RNG seed used to initialize the grid,
        or -1 if the grid was initialized directly from another 2D array. */
    public long seed() {
        return this.seed;
    }

    /* Returns the height (number of rows, length of first dimension) of the grid. */
    public int height() {
        return this.height;
    }

    /* Returns the width (number of columns, length of second dimension) of the grid. */
    public int width() {
        return this.width;
    }

    /* Helper function to print the grid nicely. */
    private void addPrintedRow(StringBuilder s, String prefix, String suffix, int i, String format) {
        s.append(prefix);
        s.append("[");
        for (int j = 0; j < width; j++)
            s.append(String.format(format, grid[i][j]));
        s.append("]");
        s.append(suffix);
    }

    /* Returns a nicely formatted representation of the grid, useful for printing.
       Number of lines == size. */
    public String toString() {
        if (height == 0) {
            return "[ ]";
        }

        String format = "%8.4f";
        StringBuilder s = new StringBuilder();

        if (height == 1) {
            addPrintedRow(s, "[ ", " ]\n", 0, format);
        }
        else {
            addPrintedRow(s, "[ ", "\n", 0, format);
            for (int i = 1; i < height-1; i++)
                addPrintedRow(s, "  ", "\n", i, format);
            addPrintedRow(s, "  ", " ]", height-1, format);
        }

        return s.toString();
    }

    /* Verifies that the given location is strictly bigger than its neighbors, obeying boundary conditions.
        NOTE: adds to the comparison count.
       Returns true if so.
       Returns false if not, or if the location is invalid (out of bounds).
    */
    public boolean checkBigger(Location loc) {
        int row = loc.getRow(); int col = loc.getCol();
        if (row < 0 || row >= height || col < 0 || col >= width)
            return false;
        
        if (row > 0)
            if (compare(row, col, row-1, col) <= 0)
                return false;
        
        if (row < height-1)
            if (compare(row, col, row+1, col) <= 0)
                return false;
        
        if (col > 0)
            if (compare(row, col, row, col-1) <= 0)
                return false;
        
        if (col < width-1)
            if (compare(row, col, row, col+1) <= 0)
                return false;

        return true;
    }

    public double zoeiomwc(int row, int col) {
        return grid[row][col];
    }
}

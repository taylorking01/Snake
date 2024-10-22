package apple;

/**
 * This class represents an Apple object in the Snake game, 
 * with a specific position on the game grid.
 */
public class Apple {
    private int x; // Column of the apple on the grid
    private int y; // Row of the apple on the grid

    /**
     * Constructs an Apple object at a specified position on the grid.
     * 
     * @param x the x-coordinate (column) where the apple is placed
     * @param y the y-coordinate (row) where the apple is placed
     * 
     * This constructor initializes an apple at the given (x, y) position
     * on the game grid, representing where the apple will appear.
     */
    public Apple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the x-coordinate (column) of the apple's position.
     * 
     * @return the x-coordinate of the apple on the grid
     * 
     * This method returns the column position of the apple on the game grid.
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the y-coordinate (row) of the apple's position.
     * 
     * @return the y-coordinate of the apple on the grid
     * 
     * This method returns the row position of the apple on the game grid.
     */
    public int getY() {
        return y;
    }
}

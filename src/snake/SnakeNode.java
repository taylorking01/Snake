package snake;

/**
 * The SnakeNode class represents a single segment of the snake in the Snake game.
 * Each SnakeNode contains an (x, y) position and a reference to the next segment in the snake.
 */
public class SnakeNode {
    private int x;              // The x-coordinate of the snake segment
    private int y;              // The y-coordinate of the snake segment
    private SnakeNode next;     // Reference to the next SnakeNode in the linked list

    /**
     * Constructs a SnakeNode at the specified (x, y) position.
     * 
     * @param x the x-coordinate of this snake node
     * @param y the y-coordinate of this snake node
     * 
     * This constructor initializes a snake node at the given coordinates and
     * sets the next node to null.
     */
    public SnakeNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.next = null;
    }

    /**
    /* Public methods follow
    /**/

    /**
     * Gets the x-coordinate of this snake node.
     * 
     * @return the x-coordinate of the snake node
     * 
     * This method returns the horizontal position of this node on the game grid.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of this snake node.
     * 
     * @return the y-coordinate of the snake node
     * 
     * This method returns the vertical position of this node on the game grid.
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the next node in the snake's linked list.
     * 
     * @return the next SnakeNode in the snake's body
     * 
     * This method returns the next node that follows this segment of the snake.
     * If there is no next segment, it returns null.
     */
    public SnakeNode getNext() {
        return next;
    }

    /**
     * Sets the next node in the snake's linked list.
     * 
     * @param next the next SnakeNode to link to this node
     * 
     * This method links this node to another SnakeNode, effectively extending the snake's body.
     */
    public void setNext(SnakeNode next) {
        this.next = next;
    }
}

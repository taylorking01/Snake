package snake;

/**
 * The SnakeLinkedList class manages the linked list structure of the snake in the game.
 * It handles the snake's movement, growth, and direction changes.
 */
public class SnakeLinkedList {
    private SnakeNode head;          // The head node of the snake (front of the snake)
    private SnakeNode tail;          // The tail node of the snake (end of the snake)
    private Direction direction;     // The current direction of the snake's movement
    private boolean shouldGrow = false;  // Flag to track whether the snake should grow
    private int length;              // The current length of the snake

    /**
     * Constructs a SnakeLinkedList with an initial position for the snake's head.
     * 
     * @param startX the x-coordinate of the snake's initial head position
     * @param startY the y-coordinate of the snake's initial head position
     * 
     * This constructor initializes the snake with a single head node at the specified coordinates
     * and sets the initial direction to the right.
     */
    public SnakeLinkedList(int startX, int startY) {
        // Initialize the snake with a head segment at the given position
        head = new SnakeNode(startX, startY);
        tail = head;
        direction = new Direction(Direction.Dir.RIGHT);  // Initialize moving to the right
        length = 1;  // Initial length of the snake
    }

    /**
    /* Private methods follow
    /**/

    /**
     * Removes the tail of the snake to simulate movement by shrinking the snake's body.
     * 
     * This method traverses the snake's linked list and removes the current tail node,
     * effectively shrinking the snake by one segment.
     */
    private void removeTail() {
        SnakeNode current = head;
        while (current.getNext() != tail) {
            current = current.getNext();
        }
        current.setNext(null);  // Remove the tail node
        tail = current;  // Update the new tail

        // Update length
        length--;
    }

    /**
    /* Public methods follow
    /**/

    /**
     * Moves the snake by creating a new head node in the current direction and removing the tail node.
     * 
     * This method simulates the snake's movement by adding a new head node at the next position
     * based on the current direction. If the snake should grow, the tail is not removed.
     */
    public void move() {
        int newX = head.getX();
        int newY = head.getY();

        // Calculate the new head position based on the current direction
        switch (direction.getCurrentDirection()) {
            case UP:
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                newX++;
                break;
        }

        // Create a new head node and attach it to the front of the snake
        SnakeNode newHead = new SnakeNode(newX, newY);
        newHead.setNext(head);
        head = newHead;

        // Remove the tail unless the snake should grow
        if (!shouldGrow) {
            removeTail();
        } else {
            shouldGrow = false;  // Reset the grow flag after the snake grows
        }

        // Update the snake's length
        length++;
    }

    /**
     * Gets the current direction of the snake's movement.
     * 
     * @return the current direction of the snake as a Direction.Dir enum
     * 
     * This method returns the direction in which the snake is currently moving.
     */
    public Direction.Dir getCurrentDirection() {
        return direction.getCurrentDirection();
    }

    /**
     * Changes the direction of the snake's movement.
     * 
     * @param newDirection the new direction to set for the snake
     * 
     * This method changes the direction of the snake's movement, provided the new direction
     * is not a reverse of the current direction.
     */
    public void changeDirection(Direction.Dir newDirection) {
        direction.changeDirection(newDirection);
    }

    /**
     * Sets the flag to grow the snake on the next move.
     * 
     * This method sets a flag that will cause the snake to grow by one segment when it moves next.
     */
    public void grow() {
        shouldGrow = true;
    }

    /**
     * Gets the head node of the snake.
     * 
     * @return the head node of the snake
     * 
     * This method returns the head of the snake, which is the front segment of the snake's body.
     */
    public SnakeNode getHead() {
        return head;
    }

    /**
     * Gets the current length of the snake.
     * 
     * @return the length of the snake as an integer
     * 
     * This method returns the total number of segments that make up the snake's body.
     */
    public int getLength() {
        return length;
    }
}

package snake;

import java.util.List;
import java.util.Set;

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
    private boolean hasVision;		 // Whether the snake has vision
    private SnakeVision vision;		 // The vision object

    /**
     * Constructs a SnakeLinkedList with an initial position for the snake's head.
     *
     * @param startX the x-coordinate of the snake's initial head position
     * @param startY the y-coordinate of the snake's initial head position
     * @param hasVision whether the snake has vision capabilities
     *
     * This constructor initializes the snake with a single head node at the specified coordinates
     * and sets the initial direction to the right.
     */
    public SnakeLinkedList(int startX, int startY, boolean hasVision) {
        // Initialize the snake with a head segment at the given position
        head = new SnakeNode(startX, startY);
        tail = head;
        direction = new Direction(Direction.Dir.RIGHT);  // Initialize moving to the right
        length = 1;  // Initial length of the snake
        this.hasVision = hasVision;
        

        if (hasVision) {
            initializeVision();
        }
    }

    /**
    /* Private methods follow
    /**/
    
    /*
     * Initializes the SnakeVision object if vision enabled
     */
    private void initializeVision() {
        vision = new SnakeVision(); 
    }
    
    /**
     * Used to reset the position of a snake, without creating a new brain
     * @param startX
     * @param startY
     * @param initialDirection
     */
    public void reset(int startX, int startY, Direction.Dir initialDirection, int gridRows, int gridCols, int appleX, int appleY, Set<Position> snakeBodyPositions) {
        // Set head to the new starting position
        this.head = new SnakeNode(startX, startY);
        this.tail = this.head;         // Reset tail to head (single segment)
        this.direction = new Direction(initialDirection); // Set initial direction
        this.length = 1;               // Reset length to 1
        this.shouldGrow = false;       // Reset grow flag

        // Reinitialize vision if it was enabled
        if (this.hasVision && this.vision != null) {
            this.vision.updateVisionData(head, direction, gridRows, gridCols, appleX, appleY, snakeBodyPositions);
        }
    }



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
     *
     * @param appleX the x-coordinate of the apple
     * @param appleY the y-coordinate of the apple
     * @param gridRows the total number of rows in the grid
     * @param gridCols the total number of columns in the grid
     * @param snakeBodyPositions a set of positions occupied by the snake's body
     */
    public void move(int appleX, int appleY, int gridRows, int gridCols, Set<Position> snakeBodyPositions) {
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

        // Update vision data if vision is enabled
        if (hasVision && vision != null) {
            vision.updateVisionData(head, direction, gridRows, gridCols, appleX, appleY, snakeBodyPositions);
        }
    }

    /**
     * Initializes the vision data based on the current snake state.
     *
     * @param appleX the x-coordinate of the apple
     * @param appleY the y-coordinate of the apple
     * @param gridRows the total number of rows in the grid
     * @param gridCols the total number of columns in the grid
     * @param snakeBodyPositions a set of positions occupied by the snake's body
     */
    public void initializeVisionData(int appleX, int appleY, int gridRows, int gridCols, Set<Position> snakeBodyPositions) {
        if (hasVision && vision != null) {
            vision.updateVisionData(head, direction, gridRows, gridCols, appleX, appleY, snakeBodyPositions);
        }
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

    /**
     * Retrieves the vision data array representing surroundings.
     *
     * @return an integer array with elements indicating wall (0), snake body (1), empty (2), or apple (3)
     */
    public int[] getVisionData() {
        return hasVision && vision != null ? vision.getVisionArray() : new int[0];
    }
}

package snake;

public class SnakeLinkedList {
    private SnakeNode head;
    private SnakeNode tail;
    private Direction direction;
    private boolean shouldGrow = false; // Flag to track whether the snake should grow
    private int length; // Track the length of the snake

    public SnakeLinkedList(int startX, int startY) {
        // Initialize the snake with a head segment at given position
        head = new SnakeNode(startX, startY);
        tail = head;
        direction = new Direction(Direction.Dir.RIGHT); // Initialize moving to the right
        length = 1; // Initial length of the snake
    }

    // Method to move the snake by adding a new head and removing the tail
    public void move() {
        int newX = head.getX();
        int newY = head.getY();

        // Calculate new head position based on current direction
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

        SnakeNode newHead = new SnakeNode(newX, newY);
        newHead.setNext(head);
        head = newHead;

        // Remove the tail unless the snake should grow
        if (!shouldGrow) {
            removeTail();
        } else {
            shouldGrow = false; // Reset the grow flag after growing
        }

        // Update length
        length++;
    }

    // Method to retrieve the current direction
    public Direction.Dir getCurrentDirection() {
        return direction.getCurrentDirection();
    }

    // Method to change the direction of the snake using a string input
    public void changeDirection(Direction.Dir newDirection) {
        direction.changeDirection(newDirection);
    }

    // Remove the tail of the snake to simulate movement
    private void removeTail() {
        SnakeNode current = head;
        while (current.getNext() != tail) {
            current = current.getNext();
        }
        current.setNext(null);
        tail = current;

        // Update length
        length--;
    }

    // Set the flag to grow the snake on the next move
    public void grow() {
        shouldGrow = true;
    }

    // Method to get the head of the snake
    public SnakeNode getHead() {
        return head;
    }

    // Method to get the length of the snake
    public int getLength() {
        return length;
    }
}

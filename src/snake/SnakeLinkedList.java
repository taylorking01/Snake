package snake;

public class SnakeLinkedList {
    private SnakeNode head;
    private SnakeNode tail;
    private Direction direction;

    public SnakeLinkedList(int startX, int startY) {
        // Initialize the snake with a head segment at given position
        head = new SnakeNode(startX, startY);
        tail = head;
        direction = new Direction(Direction.Dir.RIGHT); // Initialize moving to the right
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

        removeTail(); // Remove the tail, unless we want to grow the snake
    }

    // Method to change the direction of the snake using a string input
    public void changeDirection(String newDirection) {
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
    }

    public void grow() {
        // Called when the snake eats food, don't remove the tail here
    }

    // Method to get the head of the snake
    public SnakeNode getHead() {
        return head;
    }
}

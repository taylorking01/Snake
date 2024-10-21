package snake;

public class SnakeNode {
    private int x, y;
    private SnakeNode next;

    public SnakeNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.next = null;
    }

    // Getter for x
    public int getX() {
        return x;
    }

    // Getter for y
    public int getY() {
        return y;
    }

    // Getter for next
    public SnakeNode getNext() {
        return next;
    }

    // Setter for next (optional, depending on how you handle the list)
    public void setNext(SnakeNode next) {
        this.next = next;
    }
}

package snake;

public class SnakeNode {
    public int x, y;
    public SnakeNode next;
    
    public SnakeNode(int x, int y) {
        this.x = x;
        this.y = y;
        this.next = null;
    }
}

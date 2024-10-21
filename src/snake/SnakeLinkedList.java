package snake;

public class SnakeLinkedList {
	private SnakeNode head;
	private SnakeNode tail;
	
	public SnakeLinkedList(int startX, int startY) {
		//Initialize the snake with a head segment at given position
		head = new SnakeNode(startX, startY);
		tail = head;
	}
	
	//Method to add new segment to tail of the snake
	public void addSegment(int x, int y) {
		SnakeNode newSegment = new SnakeNode(x, y);
		tail.next = newSegment;
		tail = newSegment;
	}
	
	//Method to get head of snake
	public SnakeNode getHead() {
		return head;
	}
	
	//Method to move the snake by adding a new head and removing the tail
	public void move(int newX, int newY) {
		SnakeNode newHead = new SnakeNode(newX, newY);
		newHead.next = head;
		head = newHead;
		
		//Remove the tail
		SnakeNode current = head;
		while (current.next != tail) {
			current = current.next;
		}
		current.next = null;
		tail = current;
	}
}

package snake;

public class SnakeLinkedList {
	private SnakeNode head;
	private SnakeNode tail;
	private Direction currentDirection;
	
	public enum Direction {
		UP, DOWN, LEFT, RIGHT
	}
	
	public SnakeLinkedList(int startX, int startY) {
		//Initialize the snake with a head segment at given position
		head = new SnakeNode(startX, startY);
		tail = head;
		currentDirection = Direction.RIGHT; //Make random eventually
	}
	
	//Method to move the snake by adding a new head and removing the tail
	public void move() {
		int newX = head.x;
		int newY = head.y;
		
		//Calculate new head position based on current direction
		switch(currentDirection) {
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
		newHead.next = head;
		head = newHead;
		
		removeTail(); //Remove the tail, unless we want to grow the snake
	}
	
	//Method to change the direction of the snake
	public void changeDirection(Direction newDirection) {
		//Prevent snake from reversing on itself
		if ((currentDirection == Direction.UP && newDirection != Direction.DOWN) ||
	        (currentDirection == Direction.DOWN && newDirection != Direction.UP) ||
	        (currentDirection == Direction.LEFT && newDirection != Direction.RIGHT) ||
	        (currentDirection == Direction.RIGHT && newDirection != Direction.LEFT)) {
			
	        currentDirection = newDirection;
		}
	}
	
	//Remove the tail of the snake to simulate movement
	private void removeTail() {
		SnakeNode current = head;
		while (current.next != tail) {
			current = current.next;
		}
		current.next = null;
		tail = current;
	}
	
	public void grow() {
		//Called when snake eats food
		//Do not remove tail here
	}
	
	//Method to get head of snake
	public SnakeNode getHead() {
		return head;
	}
}

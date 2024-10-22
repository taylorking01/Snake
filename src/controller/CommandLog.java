package controller;

import snake.Direction;
import java.util.LinkedList;
import java.util.Queue;

public class CommandLog {
	private Queue<Direction.Dir> commandQueue;
	
	public CommandLog() {
        commandQueue = new LinkedList<>();
    }
	
	// Add a new direction command to the log
    public void addCommand(Direction.Dir direction) {
        commandQueue.add(direction);
    }
    
    // Retrieve and remove the next command from the log, if available
    public Direction.Dir getNextCommand(Direction.Dir currentDirection) {
        // Only return the next command if it's not reversing the current direction
        if (!commandQueue.isEmpty()) {
            Direction.Dir nextDirection = commandQueue.peek();
            if (!isReversing(currentDirection, nextDirection)) {
                return commandQueue.poll();  // Remove and return the next command
            } else {
                commandQueue.poll();  // Invalid command, discard it
            }
        }
        return currentDirection;  // Continue with the current direction if no valid command
    }
    
    // Check if the new direction is a reverse of the current one
    private boolean isReversing(Direction.Dir current, Direction.Dir next) {
        return (current == Direction.Dir.UP && next == Direction.Dir.DOWN) ||
               (current == Direction.Dir.DOWN && next == Direction.Dir.UP) ||
               (current == Direction.Dir.LEFT && next == Direction.Dir.RIGHT) ||
               (current == Direction.Dir.RIGHT && next == Direction.Dir.LEFT);
    }
}

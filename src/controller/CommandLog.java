package controller;

import snake.Direction;
import snake.SnakeNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class CommandLog {
    private Queue<Direction.Dir> commandQueue; // Commands waiting to be executed
    private List<CommandEntry> commandLog;     // All executed commands

    public CommandLog() {
        commandQueue = new LinkedList<>();
        commandLog = new ArrayList<>();
    }

    public void addCommand(Direction.Dir direction, int[] position) {
        // Add this condition to check if the new direction is different from the last logged command
        if (!commandLog.isEmpty()) {
            CommandEntry lastEntry = commandLog.get(commandLog.size() - 1);
            if (lastEntry.getDirection() == direction) {
                return;  // If the direction is the same as the last logged one, ignore it
            }
        }
        commandQueue.add(direction);
        commandLog.add(new CommandEntry(direction, position[0], position[1]));
    }



 // Retrieve and remove the next command from the queue, if available
    public Direction.Dir getNextCommand(Direction.Dir currentDirection, SnakeNode head) {
        if (!commandQueue.isEmpty()) {
            Direction.Dir nextDirection = commandQueue.peek();
            if (!isReversing(currentDirection, nextDirection)) {
                commandQueue.poll();  // Remove the command from the queue only once
                commandLog.add(new CommandEntry(nextDirection, head.getX(), head.getY())); // Log the executed command
                return nextDirection;  // Return the next command
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

    // Print the command log for debugging or analysis purposes
    public void printCommandLog() {
        System.out.println("Command Log History:");
        for (CommandEntry entry : commandLog) {
            System.out.println(entry);
        }
    }
}

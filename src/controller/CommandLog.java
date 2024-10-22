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
        if (!commandLog.isEmpty()) {
            CommandEntry lastEntry = commandLog.get(commandLog.size() - 1);
            if (lastEntry.getDirection() == direction) {
                System.out.println("Duplicate direction ignored.");
                return;  // Ignore if the direction is the same as the last logged one
            }
        }
        commandQueue.add(direction);
        commandLog.add(new CommandEntry(direction, position[0], position[1]));
        System.out.println("Added direction: " + direction + " at position: " + position[0] + ", " + position[1]);
    }


 // Retrieve and remove the next command from the queue, if available
    public Direction.Dir getNextCommand(Direction.Dir currentDirection, SnakeNode head) {
        if (!commandQueue.isEmpty()) {
            Direction.Dir nextDirection = commandQueue.peek();
            if (!isReversing(currentDirection, nextDirection)) {
                commandQueue.poll();  // Remove the command from the queue once processed
                commandLog.add(new CommandEntry(nextDirection, head.getX(), head.getY()));
                return nextDirection;
            } else {
                commandQueue.poll();  // Remove invalid command
            }
        }
        return currentDirection;  // Continue in the current direction if no valid command
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
    
    public static void main(String[] args) {
        // Initialize CommandLog
        CommandLog commandLog = new CommandLog();

        // Simulate adding directions with different positions
        int[] position1 = {5, 4};
        int[] position2 = {3, 2};
        int[] position3 = {1, 6};

        // Add commands to the log
        commandLog.addCommand(Direction.Dir.UP, position1);
        commandLog.addCommand(Direction.Dir.LEFT, position2);
        commandLog.addCommand(Direction.Dir.DOWN, position3);

        // Add duplicate direction to test the duplicate check
        commandLog.addCommand(Direction.Dir.DOWN, position3); // Should be ignored
        
        // Add a valid new direction
        int[] position4 = {7, 8};
        commandLog.addCommand(Direction.Dir.RIGHT, position4);
        
        // Print the command log history
        commandLog.printCommandLog();

        // Simulate getting the next command and print it
        SnakeNode fakeSnakeNode = new SnakeNode(7, 8); // Simulate the snake's head at position (7, 8)
        Direction.Dir currentDirection = Direction.Dir.UP;
        Direction.Dir nextCommand = commandLog.getNextCommand(currentDirection, fakeSnakeNode);
        System.out.println("Next command: " + nextCommand);
    }

}

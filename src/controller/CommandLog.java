package controller;

import snake.Direction;
import snake.SnakeNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

public class CommandLog {
    private Queue<Direction.Dir> commandQueue;        // Commands waiting to be processed
    private List<CommandEntry> executedCommandLog;    // Commands that have been executed

    public CommandLog() {
        commandQueue = new LinkedList<>();
        executedCommandLog = new ArrayList<>();
    }

    // Method to enqueue a command when the user inputs it
    public void enqueueCommand(Direction.Dir direction) {
        commandQueue.add(direction);
        System.out.println("Enqueued direction: " + direction);
    }

    // Method to retrieve and execute the next valid command
    public Direction.Dir getNextValidCommand(Direction.Dir currentDirection, SnakeNode head) {
        while (!commandQueue.isEmpty()) {
            Direction.Dir nextDirection = commandQueue.peek();
            if (!isReversing(currentDirection, nextDirection)) {
                commandQueue.poll();  // Remove the command from the queue
                // Log the executed command with the head's position at the time of execution
                executedCommandLog.add(new CommandEntry(nextDirection, head.getX(), head.getY()));
                return nextDirection;
            } else {
                // Invalid command (reversing direction), discard it
                commandQueue.poll();
            }
        }
        // No valid commands; continue moving in the current direction
        return currentDirection;
    }

    // Check if the new direction is the reverse of the current one
    private boolean isReversing(Direction.Dir current, Direction.Dir next) {
        return (current == Direction.Dir.UP && next == Direction.Dir.DOWN) ||
               (current == Direction.Dir.DOWN && next == Direction.Dir.UP) ||
               (current == Direction.Dir.LEFT && next == Direction.Dir.RIGHT) ||
               (current == Direction.Dir.RIGHT && next == Direction.Dir.LEFT);
    }

    // Print the log of executed commands
    public void printExecutedCommands() {
        System.out.println("Executed Command Log:");
        for (CommandEntry entry : executedCommandLog) {
            System.out.println(entry);
        }
    }
    
    public static void main(String[] args) {
        // Initialize CommandLog
        CommandLog commandLog = new CommandLog();

        // Simulate adding directions
        commandLog.enqueueCommand(Direction.Dir.UP);
        commandLog.enqueueCommand(Direction.Dir.LEFT);
        commandLog.enqueueCommand(Direction.Dir.DOWN);
        commandLog.enqueueCommand(Direction.Dir.DOWN); // Will be enqueued again

        // Simulate the snake's head node
        SnakeNode fakeSnakeNode = new SnakeNode(5, 5);
        Direction.Dir currentDirection = Direction.Dir.RIGHT;

        // Simulate executing commands
        while (currentDirection != null) {
            currentDirection = commandLog.getNextValidCommand(currentDirection, fakeSnakeNode);
            System.out.println("Next direction: " + currentDirection);
            // Break the loop if no change in direction (for testing)
            if (commandLog.commandQueue.isEmpty()) {
                break;
            }
        }

        // Print the executed command log
        commandLog.printExecutedCommands();
    }

}

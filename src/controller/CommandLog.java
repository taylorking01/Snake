package controller;

import snake.Direction;
import snake.SnakeNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;

/**
 * The CommandLog class manages the queue of movement commands for the snake in the Snake game.
 * It stores commands that are waiting to be processed and logs commands that have been executed.
 */
public class CommandLog {
    private Queue<Direction.Dir> commandQueue;        // Queue of commands waiting to be processed
    private List<CommandEntry> executedCommandLog;    // List of commands that have been executed

    /**
     * Constructs a CommandLog object.
     * Initializes the command queue and the list for storing executed commands.
     */
    public CommandLog() {
        commandQueue = new LinkedList<>();
        executedCommandLog = new ArrayList<>();
    }

    /**
    /* Private methods follow
    /**/

    /**
     * Checks if the new direction is a reverse of the current one.
     *
     * @param current the current direction of the snake
     * @param next the next direction being considered
     * @return true if the next direction is the reverse of the current direction, otherwise false
     *
     * This method prevents the snake from reversing direction into itself.
     */
    private boolean isReversing(Direction.Dir current, Direction.Dir next) {
        return (current == Direction.Dir.UP && next == Direction.Dir.DOWN) ||
               (current == Direction.Dir.DOWN && next == Direction.Dir.UP) ||
               (current == Direction.Dir.LEFT && next == Direction.Dir.RIGHT) ||
               (current == Direction.Dir.RIGHT && next == Direction.Dir.LEFT);
    }

    /**
    /* Public methods follow
    /**/

    /**
     * Enqueues a command when the user inputs a direction.
     *
     * @param direction the direction of the snake's movement input by the player
     *
     * This method adds the inputted direction to the command queue for processing.
     */
    public void enqueueCommand(Direction.Dir direction) {
        commandQueue.add(direction);
        System.out.println("Enqueued direction: " + direction);
    }

    /**
     * Retrieves and executes the next valid command from the command queue.
     * If the next direction is a reverse of the current direction, it will be discarded.
     *
     * @param currentDirection the current direction of the snake
     * @param head the snake's head node, used to log its position
     * @return the next valid direction for the snake to move
     *
     * This method checks the command queue for the next valid movement command, executes it,
     * and logs the command with the snake's current position. If no valid command is found, 
     * the snake continues moving in its current direction.
     */
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

    /**
     * Prints the log of executed commands.
     *
     * This method displays the list of all commands that have been executed,
     * including the direction and the position of the snake's head when the command was executed.
     */
    public void printExecutedCommands() {
        System.out.println("Executed Command Log:");
        for (CommandEntry entry : executedCommandLog) {
            System.out.println(entry);
        }
    }

    /**
     * Main method for testing the CommandLog class.
     * Simulates adding directions, executing commands, and printing the command log.
     *
     * @param args command-line arguments (not used)
     */
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

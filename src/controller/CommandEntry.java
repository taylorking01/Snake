package controller;

import snake.Direction;

/**
 * The CommandEntry class represents an entry of a movement command
 * in the Snake game. It stores the direction the snake moved and
 * the x and y coordinates of the snake's head when the command was executed.
 */
public class CommandEntry {
    private Direction.Dir direction;  // The direction in which the snake moved
    private int x;  // The x-coordinate (column) of the snake's head at the time of the move
    private int y;  // The y-coordinate (row) of the snake's head at the time of the move

    /**
     * Constructs a CommandEntry object with the specified direction and position.
     *
     * @param direction the direction in which the snake moved
     * @param x the x-coordinate (column) of the snake's head when the move was made
     * @param y the y-coordinate (row) of the snake's head when the move was made
     *
     * This constructor initializes a command entry that logs the snake's movement direction
     * and the position of the snake's head when the command was executed.
     */
    public CommandEntry(Direction.Dir direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the direction of the command.
     *
     * @return the direction of the snake's movement
     *
     * This method returns the direction in which the snake moved during the command.
     */
    public Direction.Dir getDirection() {
        return direction;
    }

    /**
     * Gets the x-coordinate of the command.
     *
     * @return the x-coordinate of the snake's head when the command was executed
     *
     * This method returns the x-coordinate (column) of the snake's head at the time of the move.
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the command.
     *
     * @return the y-coordinate of the snake's head when the command was executed
     *
     * This method returns the y-coordinate (row) of the snake's head at the time of the move.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns a string representation of the command entry.
     *
     * @return a string describing the movement command, including the direction and position
     *
     * This method provides a string representation in the format "Moved {direction} at ({x}, {y})".
     */
    @Override
    public String toString() {
        return "Moved " + direction + " at (" + x + ", " + y + ")";
    }
}

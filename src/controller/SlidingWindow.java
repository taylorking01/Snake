package controller;

import snake.Direction;
import java.util.LinkedList;

public class SlidingWindow {
    private final int windowSize = 10;
    private LinkedList<Direction.Dir> moves;

    public SlidingWindow() {
        moves = new LinkedList<>();
    }

    /**
     * Adds a new move to the sliding window.
     *
     * @param move the new move direction to add
     */
    public void addMove(Direction.Dir move) {
        if (moves.size() >= windowSize) {
            moves.removeFirst();
        }
        moves.addLast(move);
    }

    /**
     * Retrieves the moves in the sliding window as an array.
     *
     * @return an array of the last 10 moves
     */
    public Direction.Dir[] getMoves() {
        return moves.toArray(new Direction.Dir[0]);
    }

    /**
     * Encodes the moves into a numerical input array for the neural network.
     *
     * @return an array of integers representing the moves
     */
    public double[] getInputArray() {
        double[] input = new double[windowSize];
        int index = 0;
        for (Direction.Dir move : moves) {
            input[index++] = encodeMove(move);
        }
        // If moves are less than window size, pad with zeros
        while (index < windowSize) {
            input[index++] = 0.0;
        }
        return input;
    }

    /**
     * Encodes a move direction into a numerical value.
     *
     * @param move the move direction
     * @return the encoded value
     */
    private double encodeMove(Direction.Dir move) {
        switch (move) {
            case LEFT:
                return -1.0;
            case RIGHT:
                return 1.0;
            case UP:
            case DOWN:
                return 0.0; // Assuming FORWARD is represented as 0
            default:
                return 0.0;
        }
    }

    /**
     * Clears the sliding window.
     */
    public void reset() {
        moves.clear();
    }
}

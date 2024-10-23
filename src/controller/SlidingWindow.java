package controller;

import snake.Direction;

import java.util.LinkedList;

public class SlidingWindow {
    private final int windowSize = 10;  // Fixed size of the sliding window
    private LinkedList<MoveEntry> moves;

    public SlidingWindow() {
        moves = new LinkedList<>();
    }

    public void addMove(Direction.Dir direction, int x, int y) {
        if (moves.size() >= windowSize) {
            moves.removeFirst();  // Remove the oldest move
        }
        moves.addLast(new MoveEntry(direction, x, y));  // Add the new move
    }

    public LinkedList<MoveEntry> getMoves() {
        return new LinkedList<>(moves);  // Return a copy
    }

    public double[] getEncodedMoves() {
        double[] encodedMoves = new double[windowSize * 3];  // 3 options: LEFT, RIGHT, FORWARD

        int index = 0;
        for (MoveEntry move : moves) {
            double[] encodedDirection = encodeDirection(move.getDirection());
            System.arraycopy(encodedDirection, 0, encodedMoves, index, 3);
            index += 3;
        }

        // Pad remaining slots with zeros if necessary
        while (index < encodedMoves.length) {
            encodedMoves[index++] = 0.0;
        }

        return encodedMoves;
    }

    private double[] encodeDirection(Direction.Dir direction) {
        double[] encoding = new double[3];  // LEFT, RIGHT, FORWARD
        switch (direction) {
            case LEFT:
                encoding[0] = 1.0;
                break;
            case RIGHT:
                encoding[1] = 1.0;
                break;
            case UP:
            case DOWN:
                encoding[2] = 1.0;
                break;
            default:
                break;
        }
        return encoding;
    }

    public void clear() {
        moves.clear();
    }
}

package controller;

import snake.Direction;

public class CommandEntry {
    private Direction.Dir direction;
    private int x;
    private int y;

    public CommandEntry(Direction.Dir direction, int x, int y) {
        this.direction = direction;
        this.x = x;
        this.y = y;
    }

    public Direction.Dir getDirection() {
        return direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Moved " + direction + " at (" + x + ", " + y + ")";
    }
}

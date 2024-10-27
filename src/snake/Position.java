package snake;

import java.util.Objects;

/**
 * Represents a position on the grid with x and y coordinates.
 */
public class Position {
    private final int x;
    private final int y;

    /**
     * Constructs a Position with the specified coordinates.
     *
     * @param x the x-coordinate (column)
     * @param y the y-coordinate (row)
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x-coordinate of this position.
     *
     * @return the x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of this position.
     *
     * @return the y-coordinate
     */
    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;
        return x == position.x &&
               y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

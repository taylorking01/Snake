package snake;

public class Direction {
    public enum Dir {
        UP, DOWN, LEFT, RIGHT
    }

    private Dir currentDirection;

    public Direction(Dir initialDirection) {
        this.currentDirection = initialDirection;
    }

    // Method to change direction based on a Dir type
    public void changeDirection(Dir newDirection) {
        if (!isReversing(newDirection)) {
            currentDirection = newDirection;
        }
    }

    // Getter for the current direction
    public Dir getCurrentDirection() {
        return currentDirection;
    }

    // Prevent snake from reversing
    private boolean isReversing(Dir newDirection) {
        return (currentDirection == Dir.UP && newDirection == Dir.DOWN) ||
               (currentDirection == Dir.DOWN && newDirection == Dir.UP) ||
               (currentDirection == Dir.LEFT && newDirection == Dir.RIGHT) ||
               (currentDirection == Dir.RIGHT && newDirection == Dir.LEFT);
    }
}

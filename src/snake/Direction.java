package snake;

/**
 * The Direction class manages the direction in which the snake moves in the game.
 * It prevents the snake from reversing direction, ensuring smooth gameplay.
 */
public class Direction {

    /**
     * Enum representing the four possible movement directions.
     */
    public enum Dir {
        UP, DOWN, LEFT, RIGHT
    }

    private Dir currentDirection;  // The current direction of the snake

    /**
     * Constructs a Direction object with an initial direction.
     * 
     * @param initialDirection the starting direction for the snake
     * 
     * This constructor sets the initial direction of the snake when the game begins.
     */
    public Direction(Dir initialDirection) {
        this.currentDirection = initialDirection;
    }

    /**
    /* Private methods follow
    /**/

    /**
     * Checks if the new direction is the reverse of the current direction.
     * 
     * @param newDirection the new direction being considered
     * @return true if the new direction is a reverse of the current direction, otherwise false
     * 
     * This method prevents the snake from reversing into itself. It ensures that
     * if the snake is moving up, it cannot suddenly move down, and so on for the other directions.
     */
    private boolean isReversing(Dir newDirection) {
        return (currentDirection == Dir.UP && newDirection == Dir.DOWN) ||
               (currentDirection == Dir.DOWN && newDirection == Dir.UP) ||
               (currentDirection == Dir.LEFT && newDirection == Dir.RIGHT) ||
               (currentDirection == Dir.RIGHT && newDirection == Dir.LEFT);
    }

    /**
    /* Public methods follow
    /**/

    /**
     * Changes the direction of the snake, provided the new direction is not a reverse.
     * 
     * @param newDirection the new direction to set for the snake
     * 
     * This method changes the snake's movement direction unless the new direction is a reverse
     * of the current direction, in which case the change is ignored to prevent the snake from colliding with itself.
     */
    public void changeDirection(Dir newDirection) {
        if (!isReversing(newDirection)) {
            currentDirection = newDirection;
        }
    }

    /**
     * Gets the current movement direction of the snake.
     * 
     * @return the current direction of the snake
     * 
     * This method returns the current direction the snake is moving in, represented by the Dir enum.
     */
    public Dir getCurrentDirection() {
        return currentDirection;
    }
}

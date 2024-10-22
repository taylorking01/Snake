package snake;

public class Direction {
    public enum Dir {
        UP, DOWN, LEFT, RIGHT
    }

    private Dir currentDirection;

    public Direction(Dir initialDirection) {
        this.currentDirection = initialDirection;
    }

    // Method to change direction based on a string
    public void changeDirection(String newDirection) {
        switch (newDirection.toUpperCase()) {
            case "UP":
                if (currentDirection != Dir.DOWN) {  // Prevent reversing
                    currentDirection = Dir.UP;
                }
                break;
            case "DOWN":
                if (currentDirection != Dir.UP) {
                    currentDirection = Dir.DOWN;
                }
                break;
            case "LEFT":
                if (currentDirection != Dir.RIGHT) {
                    currentDirection = Dir.LEFT;
                }
                break;
            case "RIGHT":
                if (currentDirection != Dir.LEFT) {
                    currentDirection = Dir.RIGHT;
                }
                break;
            default:
                System.out.println("Invalid direction! Keeping current direction.");
        }
    }

    // Getter for the current direction
    public Dir getCurrentDirection() {
        return currentDirection;
    }
}

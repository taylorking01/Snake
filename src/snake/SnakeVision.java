package snake;

import java.util.Arrays;
import java.util.Set;

/**
 * The SnakeVision class manages the vision data for the snake, determining the contents
 * of squares surrounding the snake's head based on its direction and position.
 */
public class SnakeVision {
    private int[] visionArray;   // Array storing vision data for nearby squares
    private boolean showVision;  // Controls whether vision data should be displayed

    /**
     * Constructs a SnakeVision object with default settings.
     */
    public SnakeVision() {
        this.visionArray = new int[5];  // Representing 5 surrounding squares
        this.showVision = false;  // Default to hidden
    }

    /**
     * Updates the vision array based on the snake's head position and direction.
     *
     * @param head                The snake's head node
     * @param direction           Current direction of the snake
     * @param gridRows            Total rows in the grid (for boundary check)
     * @param gridCols            Total columns in the grid (for boundary check)
     * @param appleX              The x-coordinate of the apple
     * @param appleY              The y-coordinate of the apple
     * @param snakeBodyPositions  A set of positions occupied by the snake body
     */
    public void updateVisionData(SnakeNode head, Direction direction, int gridRows, int gridCols, int appleX, int appleY, Set<Position> snakeBodyPositions) {
        // Reset vision array
        Arrays.fill(visionArray, 2);  // Default all vision squares to "empty" (2)

        // Define vision square offsets based on the current direction
        int[][] visionOffsets = determineVisionOffsets(direction);

        // Apply the vision squares based on contents (wall, snake body, apple, or empty)
        for (int i = 0; i < visionOffsets.length; i++) {
            int visionY = head.getY() + visionOffsets[i][0];
            int visionX = head.getX() + visionOffsets[i][1];
            Position visionPos = new Position(visionX, visionY);

            // Debugging statements to trace vision positions
            System.out.println("Checking vision square " + i + ": (" + visionX + "," + visionY + ")");

            if (visionY < 0 || visionY >= gridRows || visionX < 0 || visionX >= gridCols) {
                // Mark as wall if out of bounds
                visionArray[i] = 0;
                System.out.println("Detected Wall at: (" + visionX + "," + visionY + ")");
            } else if (visionX == appleX && visionY == appleY) {
                // Mark as apple if it matches apple coordinates
                visionArray[i] = 3;
                System.out.println("Detected Apple at: (" + visionX + "," + visionY + ")");
            } else if (snakeBodyPositions.contains(visionPos)) {
                // Mark as snake body if part of the snake occupies the position
                visionArray[i] = 1;
                System.out.println("Detected Snake Body at: (" + visionX + "," + visionY + ")");
            } else {
                // Remains as empty (2)
                visionArray[i] = 2;
                System.out.println("Detected Empty at: (" + visionX + "," + visionY + ")");
            }
        }

        // Optionally, print the entire vision array for debugging
        System.out.println("Vision Array: " + Arrays.toString(visionArray));
    }

    /**
     * Determines the vision square offsets based on the snake's current direction.
     *
     * @param direction The current direction of the snake
     * @return A 2D array of offsets for the vision squares
     */
    private int[][] determineVisionOffsets(Direction direction) {
        switch (direction.getCurrentDirection()) {
            case UP:
                return new int[][]{
                    {-1, 0},  // Directly in front
                    {-1, -1}, // Left diagonal
                    {-1, 1},  // Right diagonal
                    {0, -1},  // Left side
                    {0, 1}    // Right side
                };
            case DOWN:
                return new int[][]{
                    {1, 0},   // Directly in front
                    {1, -1},  // Left diagonal
                    {1, 1},   // Right diagonal
                    {0, -1},  // Left side
                    {0, 1}    // Right side
                };
            case LEFT:
                return new int[][]{
                    {0, -1},  // Directly in front
                    {-1, -1}, // Left diagonal
                    {1, -1},  // Right diagonal
                    {-1, 0},  // Left side
                    {1, 0}    // Right side
                };
            case RIGHT:
                return new int[][]{
                    {0, 1},   // Directly in front
                    {-1, 1},  // Left diagonal
                    {1, 1},   // Right diagonal
                    {-1, 0},  // Left side
                    {1, 0}    // Right side
                };
            default:
                return new int[0][0]; // Fallback for undefined directions
        }
    }

    /**
     * Retrieves the vision data array representing surroundings.
     *
     * @return int[] Array indicating wall (0), snake body (1), empty (2), or apple (3)
     */
    public int[] getVisionArray() {
        return visionArray;
    }

    /**
     * Toggles the visibility of vision data (for display purposes).
     *
     * @param showVision Boolean to enable or disable vision visibility
     */
    public void setShowVision(boolean showVision) {
        this.showVision = showVision;
    }

    /**
     * Checks whether vision data should be displayed.
     *
     * @return true if vision data is set to be displayed, otherwise false
     */
    public boolean isShowVision() {
        return showVision;
    }
}

package arena;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import snake.SnakeLinkedList;
import snake.SnakeNode;
import snake.SnakeLinkedList.Direction;

import java.util.HashMap;
import java.util.Map;

public class Arena {
    private final int rows = 16;
    private final int cols = 16;
    private final Map<String, Rectangle> tileMap = new HashMap<>(); // To keep track of tiles for easier updates

    private GridPane grid;
    private SnakeLinkedList snake;

    public Arena(Scene scene) {
        grid = new GridPane();
        snake = new SnakeLinkedList(cols / 2, rows / 2); // Initialize snake at center
        initializeGrid(scene);
    }

    private void initializeGrid(Scene scene) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Rectangle tile = new Rectangle();

                // Bind the tile size to the smaller dimension of the scene
                tile.widthProperty().bind(Bindings.min(scene.widthProperty().multiply(0.7).divide(cols),
                                                       scene.heightProperty().multiply(0.7).divide(rows)));
                tile.heightProperty().bind(tile.widthProperty()); // Keep the tile square
                tile.setFill(Color.DARKGRAY);

                // Store each tile with its grid position as key (e.g., "0,0" -> tile at (0,0))
                tileMap.put(r + "," + c, tile);
                grid.add(tile, c, r);
            }
        }

        // After initializing the grid, draw the initial state of the snake
        updateSnakeDisplay();
    }

    // Update grid with the current snake state
    private void updateSnakeDisplay() {
        clearGrid(); // First, clear the grid to reset it
        SnakeNode current = snake.getHead();

        // Traverse through the snake linked list and update the grid
        while (current != null) {
            String position = current.y + "," + current.x; // Key format is "row,col"
            Rectangle tile = tileMap.get(position);

            if (tile != null) {
                tile.setFill(Color.LIMEGREEN); // Snake body color
            }

            current = current.next; // Move to the next part of the snake
        }
    }

    // Clear the entire grid (reset all tiles to their default color)
    private void clearGrid() {
        for (Rectangle tile : tileMap.values()) {
            tile.setFill(Color.DARKGRAY); // Reset to the default grid color
        }
    }

    // Update the game state (move the snake and update display)
    public void update() {
        snake.move();         // Move the snake
        updateSnakeDisplay(); // Update the grid based on the new snake position
        checkCollisions();    // Check for wall or self-collisions
    }

    // Method to change the snake's direction
    public void changeSnakeDirection(Direction direction) {
        snake.changeDirection(direction);
    }

    // Check if the snake has collided with walls or itself
    private void checkCollisions() {
        SnakeNode head = snake.getHead();

        // Wall collision detection
        if (head.x < 0 || head.x >= cols || head.y < 0 || head.y >= rows) {
            // Handle game over or restart logic here
            System.out.println("Game Over! Snake hit the wall.");
        }

        // Self-collision detection
        SnakeNode current = head.next;
        while (current != null) {
            if (head.x == current.x && head.y == current.y) {
                System.out.println("Game Over! Snake hit itself.");
                break;
            }
            current = current.next;
        }
    }

    public GridPane getGrid() {
        return grid;
    }
}

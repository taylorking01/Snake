package arena;

import snake.SnakeLinkedList;
import snake.SnakeNode;
import snake.Direction;
import apple.Apple;

import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Represents the game arena for the Snake game. The arena is an 8x8 grid
 * where the snake moves, apples are placed, and the game logic is processed.
 */
public class Arena {
    private final int rows = 8;  // Number of rows in the arena grid
    private final int cols = 8;  // Number of columns in the arena grid
    private final int totalCells = rows * cols;  // Total number of cells in the grid
    private final Map<String, Rectangle> tileMap = new HashMap<>();  // Stores grid tiles for easy access
    private GridPane grid;  // The grid structure for the game arena
    private SnakeLinkedList snake;  // Snake object managing the snake's movement
    private Apple apple;  // The current apple object in the game

    /**
     * Constructs an Arena object and initializes the grid and the game components.
     *
     * @param scene the scene of the game, used to bind grid elements to the window size
     *
     * This constructor initializes the snake and apple and sets up the visual grid for the game.
     */
    public Arena(Scene scene) {
        grid = new GridPane();
        resetGame();  // Initialize the snake and apple when the game starts
        initializeGrid(scene);  // Create the grid based on the scene
    }

    /**
    /* Private methods follow
    /**/

    /**
     * Initializes the grid for the arena, creating a grid of tiles and binding them
     * to the scene's size.
     *
     * @param scene the scene of the game, used for binding grid elements to window size
     *
     * This method generates the grid, adjusting each tile's size relative to the scene,
     * and assigns colors for the empty grid tiles.
     */
    private void initializeGrid(Scene scene) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Rectangle tile = new Rectangle();

                // Bind the tile size to the smaller dimension of the scene
                tile.widthProperty().bind(Bindings.min(scene.widthProperty().multiply(0.7).divide(cols),
                                                       scene.heightProperty().multiply(0.7).divide(rows)));
                tile.heightProperty().bind(tile.widthProperty());
                tile.setFill(Color.DARKGRAY);

                // Store each tile with its grid position as key (e.g., "0,0" -> tile at (0,0))
                tileMap.put(r + "," + c, tile);
                grid.add(tile, c, r);
            }
        }

        updateSnakeDisplay();  // Initial display of the snake
        updateAppleDisplay();  // Initial display of the apple
    }

    /**
     * Updates the visual display of the snake on the grid by coloring the grid cells occupied by the snake.
     *
     * The grid is first cleared, then the snake's body is displayed in green.
     */
    private void updateSnakeDisplay() {
        clearGrid();  // Reset the grid before updating
        SnakeNode current = snake.getHead();

        // Traverse through the snake linked list and update the grid
        while (current != null) {
            String position = current.getY() + "," + current.getX();
            Rectangle tile = tileMap.get(position);
            if (tile != null) {
                tile.setFill(Color.LIMEGREEN);  // Snake body color
            }
            current = current.getNext();
        }
    }

    /**
     * Updates the visual display of the apple on the grid by coloring the grid cell occupied by the apple.
     *
     * The grid cell where the apple is located is displayed in red.
     */
    private void updateAppleDisplay() {
        String position = apple.getY() + "," + apple.getX();
        Rectangle tile = tileMap.get(position);
        if (tile != null) {
            tile.setFill(Color.RED);  // Apple color
        }
    }

    /**
     * Clears the entire grid, resetting all tiles to their default color.
     *
     * This method is used to refresh the grid before updating the snake and apple positions.
     */
    private void clearGrid() {
        for (Rectangle tile : tileMap.values()) {
            tile.setFill(Color.DARKGRAY);  // Reset to the default grid color
        }
    }

    /**
     * Checks if the snake's head has collided with the apple (i.e., they share the same grid position).
     *
     * @return true if the snake's head is on the same position as the apple, otherwise false
     *
     * This method is used to determine if the snake has eaten the apple.
     */
    private boolean checkAppleCollision() {
        SnakeNode head = snake.getHead();
        return head.getX() == apple.getX() && head.getY() == apple.getY();
    }

    /**
     * Generates a new apple in a random position that is not occupied by the snake.
     *
     * The apple's position is generated randomly within the grid, and this method ensures the apple
     * does not appear on the snake's body.
     */
    private void generateApple() {
        Random random = new Random();
        int x, y;

        do {
            x = random.nextInt(cols);
            y = random.nextInt(rows);
        } while (isSnakeAtPosition(x, y));  // Ensure the apple isn't placed on the snake

        apple = new Apple(x, y);
    }

    /**
     * Checks if the snake is occupying a specific position in the grid.
     *
     * @param x the x-coordinate (column) of the grid position
     * @param y the y-coordinate (row) of the grid position
     * @return true if the snake is occupying the given position, otherwise false
     *
     * This method is used to ensure apples do not spawn on the snake's body.
     */
    private boolean isSnakeAtPosition(int x, int y) {
        SnakeNode current = snake.getHead();
        while (current != null) {
            if (current.getX() == x && current.getY() == y) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
    /* Public methods follow
    /**/

    /**
     * Resets the game by re-initializing the snake and apple.
     *
     * This method places the snake back at the center of the grid and generates a new apple.
     */
    public void resetGame() {
        snake = new SnakeLinkedList(cols / 2, rows / 2);  // Reset the snake at the center
        generateApple();  // Generate a new apple
        updateSnakeDisplay();
        updateAppleDisplay();
    }

    /**
     * Updates the game state, moving the snake and checking for collisions with the apple.
     *
     * If the snake eats the apple, it grows, and a new apple is generated. The method also checks
     * if the player has won by filling the entire arena with the snake.
     */
    public void update() {
        snake.move();
        if (checkAppleCollision()) {
            snake.grow();

            // Check if the snake has filled the entire arena (win condition)
            if (snake.getLength() == totalCells) {
                return;  // Trigger win condition or handle it externally
            }

            generateApple();  // Generate a new apple if the player hasn't won yet
        }
        updateSnakeDisplay();
        updateAppleDisplay();
    }

    /**
     * Gets the head node of the snake.
     *
     * @return the head SnakeNode of the snake
     *
     * This method is useful for checking the snake's position and handling movement or collisions.
     */
    public SnakeNode getSnakeHead() {
        return snake.getHead();
    }

    /**
     * Retrieves the current direction of the snake.
     *
     * @return the current direction of the snake as a Direction.Dir enum
     *
     * This method is used to query the snake's current movement direction.
     */
    public Direction.Dir getCurrentDirection() {
        return snake.getCurrentDirection();
    }

    /**
     * Changes the snake's direction based on the input.
     *
     * @param direction the new direction to set for the snake
     *
     * This method is used to modify the snake's movement direction during gameplay.
     */
    public void changeSnakeDirection(Direction.Dir direction) {
        snake.changeDirection(direction);
    }

    /**
     * Checks if the snake has collided with a wall or itself.
     *
     * @return true if a collision occurred, otherwise false
     *
     * This method checks both wall collisions (snake hitting the edges of the grid) and self-collisions
     * (snake running into its own body).
     */
    public boolean checkCollisions() {
        SnakeNode head = snake.getHead();
        // Wall collision
        if (head.getX() < 0 || head.getX() >= cols || head.getY() < 0 || head.getY() >= rows) {
            return true;
        }
        // Self-collision
        SnakeNode current = head.getNext();
        while (current != null) {
            if (head.getX() == current.getX() && head.getY() == current.getY()) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Checks if the player has won by filling the entire arena with the snake.
     *
     * @return true if the snake occupies all grid cells, otherwise false
     *
     * This method determines if the game has been won by checking if the snake has reached the maximum length.
     */
    public boolean checkWinCondition() {
        return snake.getLength() == totalCells;  // Win if the snake occupies all cells
    }

    /**
     * Retrieves the visual GridPane of the arena for display purposes.
     *
     * @return the GridPane representing the game arena
     *
     * This method returns the arena's grid, which can be used to add to a scene or modify further.
     */
    public GridPane getGrid() {
        return grid;
    }

    /**
     * Retrieves the current position of the snake's head as an array.
     *
     * @return an integer array representing the [x, y] position of the snake's head
     *
     * This method provides easy access to the current position of the snake's head for collision detection or movement.
     */
    public int[] getHeadPosition() {
        SnakeNode head = snake.getHead();
        return new int[]{head.getX(), head.getY()};
    }
}

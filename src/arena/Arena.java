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
 * The Arena class represents the game grid where the Snake game takes place.
 * It manages the snake, apple, and their interactions within the game.
 * The grid is initialized, updated, and rendered in this class.
 */
public class Arena {
    private final int rows = 8; // Number of rows in the grid
    private final int cols = 8; // Number of columns in the grid
    private final int totalCells = rows * cols; // Total number of cells in the grid
    private final Map<String, Rectangle> tileMap = new HashMap<>(); // Map to store grid tiles
    private GridPane grid; // The visual representation of the game grid
    private SnakeLinkedList snake; // The snake object in the game
    private Apple apple; // The apple object in the game

    /**
     * Constructs an Arena object that initializes the game grid and snake/apple entities.
     * 
     * @param scene The scene in which the arena will be rendered. This is used for setting up the grid display.
     * 
     * This constructor prepares the game grid, sets the snake and apple in their initial positions, and binds
     * the grid size to the scene size for responsive scaling.
     */
    public Arena(Scene scene) {
        grid = new GridPane();
        resetGame(); // Initialize the snake and apple when the game starts
        initializeGrid(scene);
    }
    
    /**
     * Resets the game by initializing the snake at the center of the grid and generating a new apple.
     * 
     * This method is typically called at the start of a game or after a game over, and resets the state of the grid.
     */
    public void resetGame() {
        snake = new SnakeLinkedList(cols / 2, rows / 2); // Reset the snake at center
        generateApple(); // Generate a new apple
        updateSnakeDisplay();
        updateAppleDisplay();
    }

    /**
     * Initializes the game grid visually by setting up a grid of tiles.
     * 
     * @param scene The scene in which the grid will be displayed. Used to bind tile sizes for responsive scaling.
     * 
     * This method sets up a GridPane with Rectangle tiles, which are resized based on the scene's dimensions.
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

        // After initializing the grid, draw the initial state of the snake and apple
        updateSnakeDisplay();
        updateAppleDisplay();
    }

    /**
     * Updates the display of the snake on the grid by setting the tiles occupied by the snake to a specific color.
     * 
     * This method clears the grid first and then re-draws the snake's position, coloring its body on the grid.
     */
    private void updateSnakeDisplay() {
        clearGrid(); // Reset the grid before updating
        SnakeNode current = snake.getHead();

        // Traverse through the snake linked list and update the grid
        while (current != null) {
            String position = current.getY() + "," + current.getX();
            Rectangle tile = tileMap.get(position);
            if (tile != null) {
                tile.setFill(Color.LIMEGREEN); // Snake body color
            }
            current = current.getNext();
        }
    }

    /**
     * Updates the display of the apple on the grid by setting the apple's tile to a specific color.
     * 
     * This method identifies the current position of the apple and colors its corresponding tile on the grid.
     */
    private void updateAppleDisplay() {
        String position = apple.getY() + "," + apple.getX();
        Rectangle tile = tileMap.get(position);
        if (tile != null) {
            tile.setFill(Color.RED); // Apple color
        }
    }

    /**
     * Clears the entire grid by resetting all tiles to the default color.
     * 
     * This method is used before updating the snake's position to ensure that no old snake positions remain visible.
     */
    private void clearGrid() {
        for (Rectangle tile : tileMap.values()) {
            tile.setFill(Color.DARKGRAY); // Reset to the default grid color
        }
    }
    
    /**
     * Checks if the snake's head is at the same position as the apple.
     * 
     * @return true if the snake's head is on the same tile as the apple, false otherwise.
     */
    private boolean checkAppleCollision() {
        SnakeNode head = snake.getHead();
        return head.getX() == apple.getX() && head.getY() == apple.getY();
    }

    /**
     * Updates the game state by moving the snake, checking for collisions, and potentially growing the snake.
     * 
     * This method handles the core game loop logic, such as moving the snake, checking if the snake ate the apple,
     * and checking for win conditions.
     */
    public void update() {
        snake.move();
        if (checkAppleCollision()) {
            snake.grow();
            
            // Check if the snake has filled the entire arena (win condition)
            if (snake.getLength() == totalCells) {
                // Trigger the win condition
                return;
            }

            generateApple();  // Generate a new apple if the player hasn't won yet
        }
        updateSnakeDisplay();
        updateAppleDisplay();
    }

    /**
     * Retrieves the current head of the snake.
     * 
     * @return The SnakeNode representing the head of the snake.
     */
    public SnakeNode getSnakeHead() {
        return snake.getHead();
    }
    
    /**
     * Retrieves the current direction of the snake.
     * 
     * @return The current direction in which the snake is moving.
     */
    public Direction.Dir getCurrentDirection() {
        return snake.getCurrentDirection();
    }

    /**
     * Changes the snake's direction to the specified direction.
     * 
     * @param direction The new direction in which the snake should move.
     * 
     * This method ensures that the snake's movement direction is updated without allowing a reversal.
     */
    public void changeSnakeDirection(Direction.Dir direction) {
        snake.changeDirection(direction);
    }

    /**
     * Checks if the snake has collided with itself or a wall.
     * 
     * @return true if the snake collides with a wall or itself, false otherwise.
     * 
     * This method ensures that the game is aware of when the snake has died, either from hitting a wall
     * or its own body.
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
     * Checks if the player has won the game by filling the entire arena with the snake.
     * 
     * @return true if the snake occupies all cells in the arena, false otherwise.
     * 
     * The win condition occurs when the snake has filled every cell in the grid.
     */
    public boolean checkWinCondition() {
        return snake.getLength() == totalCells; // Win if the snake occupies all cells
    }

    /**
     * Generates a new apple in a random position that is not occupied by the snake.
     * 
     * This method ensures that the new apple is placed in an empty cell on the grid.
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
     * Checks if the snake is occupying a specific position.
     * 
     * @param x The x-coordinate (column) to check.
     * @param y The y-coordinate (row) to check.
     * @return true if the snake occupies the given position, false otherwise.
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
     * Retrieves the GridPane object representing the game grid.
     * 
     * @return The GridPane used to visually display the arena.
     */
    public GridPane getGrid() {
        return grid;
    }

    /**
     * Retrieves the position of the snake's head as an array.
     * 
     * @return An array of two integers representing the (x, y) coordinates of the snake's head.
     */
    public int[] getHeadPosition() {
        SnakeNode head = snake.getHead();
        return new int[]{head.getX(), head.getY()};
    }
}

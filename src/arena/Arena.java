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

public class Arena {
    private final int rows = 8;
    private final int cols = 8;
    private final int totalCells = rows * cols; // Total number of cells in the grid
    private final Map<String, Rectangle> tileMap = new HashMap<>();
    private GridPane grid;
    private SnakeLinkedList snake;
    private Apple apple;

    public Arena(Scene scene) {
        grid = new GridPane();
        resetGame(); // Initialize the snake and apple when the game starts
        initializeGrid(scene);
    }
    
    // Reset the game by re-initializing the snake and apple
    public void resetGame() {
        snake = new SnakeLinkedList(cols / 2, rows / 2); // Reset the snake at center
        generateApple(); // Generate a new apple
        updateSnakeDisplay();
        updateAppleDisplay();
    }

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

    private void updateAppleDisplay() {
        String position = apple.getY() + "," + apple.getX();
        Rectangle tile = tileMap.get(position);
        if (tile != null) {
            tile.setFill(Color.RED); // Apple color
        }
    }

    // Clear the entire grid (reset all tiles to their default color)
    private void clearGrid() {
        for (Rectangle tile : tileMap.values()) {
            tile.setFill(Color.DARKGRAY); // Reset to the default grid color
        }
    }
    
    // Check if the snake's head is at the same position as the apple
    private boolean checkAppleCollision() {
        SnakeNode head = snake.getHead();
        return head.getX() == apple.getX() && head.getY() == apple.getY();
    }

    public void update() {
        snake.move();
        if (checkAppleCollision()) {
            snake.grow();
            
            // Check if the snake has filled the entire arena (win condition)
            if (snake.getLength() == totalCells) {
                // Trigger the win condition
                //System.out.println("Congratulations, You Win!");
                // Possibly stop the game here or handle it in the game controller
                return;
            }

            generateApple();  // Generate a new apple if the player hasn't won yet
        }
        updateSnakeDisplay();
        updateAppleDisplay();
    }

    
    public SnakeNode getSnakeHead() {
        return snake.getHead();
    }
    
    // Method to get the current direction of the snake
    public Direction.Dir getCurrentDirection() {
        return snake.getCurrentDirection();
    }

    public void changeSnakeDirection(Direction.Dir direction) {
        snake.changeDirection(direction);
    }

    // Check if the snake collides with itself or a wall
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

    // Check if the player has won by filling the entire arena with the snake
    public boolean checkWinCondition() {
        return snake.getLength() == totalCells; // Win if the snake occupies all cells
    }

    // Generate a new apple in a random position not occupied by the snake
    private void generateApple() {
        Random random = new Random();
        int x, y;

        do {
            x = random.nextInt(cols);
            y = random.nextInt(rows);
        } while (isSnakeAtPosition(x, y));  // Ensure the apple isn't placed on the snake

        apple = new Apple(x, y);
    }

    // Check if the snake is occupying a specific position
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

    public GridPane getGrid() {
        return grid;
    }

    // Method to get the head position of the snake
    public int[] getHeadPosition() {
        SnakeNode head = snake.getHead();
        return new int[]{head.getX(), head.getY()};
    }
}

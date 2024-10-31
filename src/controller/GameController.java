package controller;

import arena.Arena;
import apple.Apple;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import snake.Direction;
import snake.Position;
import snake.SnakeLinkedList;
import snake.SnakeNode;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * The GameController class manages the Snake game logic, including handling user input,
 * controlling the snake's movements, managing the game loop, and interacting directly with the Arena.
 */
public class GameController {
    private final Arena arena;                // The arena where the snake moves
    private SnakeLinkedList snake;            // The snake object
    private Apple apple;                      // The current apple in the game
    private final CommandLog commandLog;      // Logs commands and tracks the snake's movement
    private Timeline gameLoop;                // The game loop timeline
    private boolean isRunning;                // Indicates if the game is currently running
    private final int gameSpeedMillis;        // Interval for the game loop in milliseconds

    /**
     * Constructs a GameController object that manages the Snake game.
     * 
     * @param arena the game arena where the snake moves
     * @param gameSpeedMillis the speed of the game in milliseconds per tick
     */
    public GameController(Arena arena, int gameSpeedMillis) {
        this.arena = arena;
        this.gameSpeedMillis = gameSpeedMillis;
        this.commandLog = new CommandLog();
        this.isRunning = false;
        this.snake = null;
        this.apple = null;

        // Initialize the game loop timeline
        this.gameLoop = new Timeline(new KeyFrame(Duration.millis(gameSpeedMillis), e -> gameLoopTick()));
        this.gameLoop.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the Snake game by initializing the snake and apple, and starting the game loop.
     */
    public void startGame() {
        if (isRunning) return;

        isRunning = true;

        // Initialize snake at the center of the arena
        int startX = arena.getCols() / 2;
        int startY = arena.getRows() / 2;
        snake = new SnakeLinkedList(startX, startY, false); // 'false' indicates vision is disabled

        // Get positions occupied by the snake
        Set<Position> snakeBodyPositions = getSnakeBodyPositions();

        // Place the first apple
        generateApple(snakeBodyPositions);

        // Update the arena display
        updateArenaDisplay();

        // Start the game loop
        gameLoop.play();
    }

    /**
     * Pauses the Snake game by pausing the game loop.
     */
    public void pauseGame() {
        if (!isRunning) return;
        gameLoop.pause();
        isRunning = false;
    }

    /**
     * Resumes the Snake game by resuming the game loop.
     */
    public void resumeGame() {
        if (isRunning) return;
        gameLoop.play();
        isRunning = true;
    }

    /**
     * Stops the Snake game by stopping the game loop and displaying a message.
     *
     * @param message the message to display upon game termination
     */
    public void stopGame(String message) {
        if (!isRunning) return;
        gameLoop.stop();
        isRunning = false;
        System.out.println(message);
        commandLog.printExecutedCommands();
        // Optionally, display a JavaFX Alert dialog with the message
    }

    /**
     * The main game loop tick.
     * This method is called at each interval defined by the Timeline.
     * It handles moving the snake, checking for collisions, and updating the arena display.
     */
    private void gameLoopTick() {
        if (!isRunning) return;

        // Get the current direction of the snake
        Direction.Dir currentDirection = snake.getCurrentDirection();
        SnakeNode headNode = snake.getHead();

        // Fetch the next valid direction from the command log
        Direction.Dir newDirection = commandLog.getNextValidCommand(currentDirection, headNode);
        snake.changeDirection(newDirection);

        // Move the snake
        snake.move(apple.getX(), apple.getY(), arena.getRows(), arena.getCols(), getSnakeBodyPositions());

        // Check for collisions (walls or self)
        if (checkCollision()) {
            stopGame("Game Over!");
            return;
        }

        // Check if the snake has eaten the apple
        if (snake.getHead().getX() == apple.getX() && snake.getHead().getY() == apple.getY()) {
            snake.grow(); // Grow the snake

            // Generate a new apple in a random unoccupied position
            Set<Position> snakeBodyPositions = getSnakeBodyPositions();
            generateApple(snakeBodyPositions);
        }

        // Update the arena's visual representation
        updateArenaDisplay();

        // Optional: Check for win condition where snake fills the entire arena
        if (snake.getLength() == arena.getRows() * arena.getCols()) {
            stopGame("Congratulations! You won!");
        }
    }

    /**
     * Sets up user input handling by adding an event filter to the scene.
     * This captures key presses and enqueues corresponding movement commands.
     *
     * @param scene the Scene instance to handle user input
     */
    public void setupInputHandling(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case UP:
                    commandLog.enqueueCommand(Direction.Dir.UP);
                    break;
                case DOWN:
                    commandLog.enqueueCommand(Direction.Dir.DOWN);
                    break;
                case LEFT:
                    commandLog.enqueueCommand(Direction.Dir.LEFT);
                    break;
                case RIGHT:
                    commandLog.enqueueCommand(Direction.Dir.RIGHT);
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * Generates a new apple at a random position not occupied by the snake.
     *
     * @param snakeBodyPositions the set of positions occupied by the snake's body
     */
    private void generateApple(Set<Position> snakeBodyPositions) {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(arena.getCols());
            y = random.nextInt(arena.getRows());
        } while (isSnakeAtPosition(x, y, snakeBodyPositions));
        apple = new Apple(x, y);
    }

    /**
     * Checks if the snake occupies a given position.
     *
     * @param x the x-coordinate (column)
     * @param y the y-coordinate (row)
     * @param snakeBodyPositions the set of positions occupied by the snake's body
     * @return true if the snake is at (x, y), false otherwise
     */
    private boolean isSnakeAtPosition(int x, int y, Set<Position> snakeBodyPositions) {
        return snakeBodyPositions.contains(new Position(x, y));
    }

    /**
     * Retrieves the positions occupied by the snake's body (excluding the head).
     *
     * @return a set of Positions occupied by the snake's body
     */
    private Set<Position> getSnakeBodyPositions() {
        Set<Position> positions = new HashSet<>();
        SnakeNode current = snake.getHead().getNext(); // Exclude head
        while (current != null) {
            positions.add(new Position(current.getX(), current.getY()));
            current = current.getNext();
        }
        return positions;
    }

    /**
     * Updates the Arena grid display with the current snake and apple positions.
     */
    private void updateArenaDisplay() {
        // Clear the arena grid to prepare for the new frame
        arena.clearGrid();

        // Update snake display
        SnakeNode current = snake.getHead();
        boolean isHead = true;
        while (current != null) {
            Rectangle tile = arena.getTile(current.getX(), current.getY());
            if (tile != null) {
                tile.setFill(isHead ? Color.DARKGREEN : Color.LIMEGREEN);
                isHead = false; // Only the first segment is the head
            }
            current = current.getNext();
        }

        // Update apple display
        Rectangle appleTile = arena.getTile(apple.getX(), apple.getY());
        if (appleTile != null) {
            appleTile.setFill(Color.RED);
        }
    }

    /**
     * Checks for collisions with walls or the snake itself.
     *
     * @return true if a collision is detected, false otherwise
     */
    private boolean checkCollision() {
        SnakeNode head = snake.getHead();

        // Wall collision
        if (head.getX() < 0 || head.getX() >= arena.getCols() ||
            head.getY() < 0 || head.getY() >= arena.getRows()) {
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
     * Checks if the game is currently running.
     *
     * @return true if the game is running, false otherwise
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Prints the game outcome to the console.
     * 
     * @param output the message to be printed (e.g., "Game Over" or "Congratulations! You won!")
     */
    public void printGameOutput(String output) {
        System.out.println(output);
    }
}

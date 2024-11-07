package arena;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import snake.Direction;
import snake.Position;
import snake.SnakeNode;
import ui.GameListener;

import java.util.Set;

import apple.Apple;

/**
 * The ClassicMode class represents the classic game mode of the Snake game.
 * It defines the game rules, manages the game loop, and utilizes the GameController to manage game objects.
 */
public class ClassicMode {
    private final GameController gameController;    // The game controller managing game objects
    private final Arena arena;                      // The arena where the snake moves
    private final Scene scene;                      // The JavaFX scene for handling user input
    private final int gameSpeedMillis;              // The speed of the game in milliseconds per tick
    private Timeline gameLoop;                      // The game loop timeline
    private boolean isRunning;                      // Indicates if the game is currently running
    private final GameListener gameListener;        // Listener for game events
    private int applesEaten;                        // Counter for apples eaten

    /**
     * Constructs a ClassicMode object.
     *
     * @param arena           the Arena instance for rendering the game grid
     * @param scene           the Scene instance for handling user input
     * @param gameSpeedMillis the speed of the game in milliseconds per tick
     * @param listener        the GameListener to notify upon game events
     */
    public ClassicMode(Arena arena, Scene scene, int gameSpeedMillis, GameListener listener) {
        this.arena = arena;
        this.scene = scene;
        this.gameSpeedMillis = gameSpeedMillis;
        this.gameController = new GameController(arena);
        this.gameListener = listener;
        this.applesEaten = 0;
        setupInputHandling(scene);
        initializeGameLoop();
    }

    /**
     * Initializes the game loop timeline.
     */
    private void initializeGameLoop() {
        this.gameLoop = new Timeline(new KeyFrame(Duration.millis(gameSpeedMillis), e -> gameLoopTick()));
        this.gameLoop.setCycleCount(Timeline.INDEFINITE);
        this.isRunning = false;
    }

    /**
     * Starts the classic mode game by initializing game objects and starting the game loop.
     */
    public void startGame() {
        if (isRunning) return;

        isRunning = true;
        applesEaten = 0; // Reset apple counter when starting a new game
        System.out.println("Game Started.");

        // Initialize snake and apple
        gameController.initializeSnake(false);
        gameController.generateApple();
        System.out.println("Snake and apple initialized.");

        // Update the arena display
        updateArenaDisplay();

        // Start the game loop
        gameLoop.play();
    }

    /**
     * Stops the classic mode game by stopping the game loop and notifying the listener.
     *
     * @param message the message to display upon game termination
     */
    public void stopGame(String message) {
        if (!isRunning) return;
        gameLoop.stop();
        isRunning = false;
        System.out.println(message);

        // Notify the listener that the game has ended
        if (gameListener != null) {
            gameListener.onGameOver(message);
        }

        // Optionally, display a JavaFX Alert dialog with the message
    }

    /**
     * The main game loop tick.
     * This method is called at each interval defined by the Timeline.
     * It handles moving the snake, checking for collisions, and updating the arena display.
     */
    private void gameLoopTick() {
        if (!isRunning) return;

        // Move the snake based on the current direction
        gameController.moveSnake();
        System.out.println("Snake moved.");

        // Check for collisions (walls or self)
        if (checkCollision()) {
            stopGame("Game Over!");
            return;
        }

        // Check if the snake has eaten the apple
        SnakeNode head = gameController.getSnakeHead();
        Apple apple = gameController.getApple();
        if (head.getX() == apple.getX() && head.getY() == apple.getY()) {
            gameController.growSnake(); // Grow the snake
            applesEaten++; // Increment apple counter
            gameController.generateApple(); // Generate a new apple
            System.out.println("Apple eaten. Snake grown. Total apples eaten: " + applesEaten);
            gameListener.onAppleEaten(applesEaten); // Notify listener
            updateArenaDisplay(); // Update display after eating apple
        } else {
            // Update the arena's visual representation only if apple wasn't eaten
            updateArenaDisplay();
        }

        // Check for win condition where snake fills the entire arena
        if (gameController.getSnakeLength() == arena.getRows() * arena.getCols()) {
            stopGame("Congratulations! You won!");
        }
    }

    /**
     * Sets up user input handling by adding an event filter to the scene.
     * This captures key presses and changes the snake's direction accordingly.
     *
     * @param scene the Scene instance to handle user input
     */
    private void setupInputHandling(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            Direction.Dir newDirection = null;
            switch (event.getCode()) {
                case UP:
                    newDirection = Direction.Dir.UP;
                    break;
                case DOWN:
                    newDirection = Direction.Dir.DOWN;
                    break;
                case LEFT:
                    newDirection = Direction.Dir.LEFT;
                    break;
                case RIGHT:
                    newDirection = Direction.Dir.RIGHT;
                    break;
                default:
                    break;
            }
            if (newDirection != null) {
                gameController.changeDirection(newDirection);
            }
        });
    }

    /**
     * Updates the Arena grid display with the current snake and apple positions.
     */
    private void updateArenaDisplay() {
        // Clear the arena grid to prepare for the new frame
        arena.clearGrid();

        // Update snake display
        SnakeNode current = gameController.getSnakeHead();
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
        Apple apple = gameController.getApple();
        if (apple != null) {
            Rectangle appleTile = arena.getTile(apple.getX(), apple.getY());
            if (appleTile != null) {
                appleTile.setFill(Color.RED);
            }
        }

        // Debugging Output
        System.out.println("Arena updated with snake and apple positions.");
    }

    /**
     * Checks for collisions with walls or the snake itself.
     *
     * @return true if a collision is detected, false otherwise
     */
    private boolean checkCollision() {
        SnakeNode head = gameController.getSnakeHead();

        // Wall collision
        if (head.getX() < 0 || head.getX() >= arena.getCols() ||
            head.getY() < 0 || head.getY() >= arena.getRows()) {
            return true;
        }

        // Self-collision
        Set<Position> snakeBodyPositions = gameController.getSnakeBodyPositions();
        Position headPos = new Position(head.getX(), head.getY());
        if (snakeBodyPositions.contains(headPos)) {
            return true;
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
}

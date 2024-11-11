package arena;

import controller.GameController;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import snake.Direction;
import snake.Direction.Dir;
import snake.SnakeNode;
import ui.GameListener;
import ui.PRAPage;
import apple.Apple;

/**
 * The PRA class represents the Progressive Reinforcement Agent mode of the Snake game.
 * It autonomously controls the snake, manages training states (start, pause, resume),
 * and handles respawning the snake upon death or timer expiration.
 */
public class PRA implements GameListener {
    private final GameController gameController;    // Manages the snake, apple, and interactions
    private final Arena arena;                      // The arena where the snake interacts
    private final Scene scene;                      // The JavaFX scene for UI interactions
    private final int gameSpeedMillis;              // Game speed in milliseconds per tick
    private Timeline gameLoop;                      // Timeline controlling the game loop
    private boolean isRunning;                      // Indicates if the training is currently running
    private boolean isPaused;                       // Indicates if the training is currently paused
    private int applesEaten;                        // Counter for apples eaten by the snake
    private Direction.Dir snakeDirection;           // Current direction of the snake
    private PRAPage praPage;                        // Reference to the PRAPage for UI updates

    /**
     * Constructs a PRA object.
     *
     * @param arena           the Arena instance for rendering the game grid
     * @param scene           the Scene instance for handling UI interactions
     * @param gameSpeedMillis the speed of the game in milliseconds per tick
     * @param praPage         the PRAPage instance for UI updates
     */
    public PRA(Arena arena, Scene scene, int gameSpeedMillis, PRAPage praPage) {
        this.arena = arena;
        this.scene = scene;
        this.gameSpeedMillis = gameSpeedMillis;
        this.gameController = new GameController(arena);
        this.praPage = praPage;
        this.applesEaten = 0;
        this.isRunning = false;
        this.isPaused = false;
        this.snakeDirection = Direction.Dir.RIGHT; // Initial direction

        setupGameLoop();
        initializeGame();
    }

    /**
     * Initializes the game by setting up the snake and apple.
     */
    private void initializeGame() {
        try {
            gameController.initializeSnake(true);
            
            gameController.generateApple();
            applesEaten = 0;
            updateArenaDisplay();
            praPage.updateAppleCounter(applesEaten);
            System.out.println("Game initialized: Snake and apple placed.");
        } catch (Exception e) {
            handleException("Error initializing game: " + e.getMessage());
        }
    }

    /**
     * Sets up the game loop timeline.
     * The game loop handles moving the snake and processing game logic.
     */
    private void setupGameLoop() {
        this.gameLoop = new Timeline(new KeyFrame(Duration.millis(gameSpeedMillis), e -> {
            try {
                gameLoopTick();
            } catch (Exception ex) {
                handleException("Error during game loop tick: " + ex.getMessage());
            }
        }));
        this.gameLoop.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the training by starting the game loop.
     */
    public void startTraining() {
        if (isRunning) {
            System.out.println("Training is already running.");
            return;
        }

        isRunning = true;
        isPaused = false;
        gameLoop.play();
        praPage.updateTrainButton("Halt Training");
        System.out.println("Training started.");
    }

    /**
     * Pauses the training by stopping the game loop.
     */
    public void pauseTraining() {
        if (!isRunning) {
            System.out.println("Training is not running. Cannot pause.");
            return;
        }
        if (isPaused) {
            System.out.println("Training is already paused.");
            return;
        }

        isPaused = true;
        gameLoop.pause();
        praPage.updateTrainButton("Resume Training");
        System.out.println("Training paused.");
    }

    /**
     * Resumes the training by restarting the game loop.
     */
    public void resumeTraining() {
        if (!isRunning) {
            System.out.println("Training is not running. Cannot resume.");
            return;
        }
        if (!isPaused) {
            System.out.println("Training is not paused. Cannot resume.");
            return;
        }

        isPaused = false;
        gameLoop.play();
        praPage.updateTrainButton("Halt Training");
        System.out.println("Training resumed.");
    }

    /**
     * Toggles the training state between start, pause, and resume.
     * This method should be called when the "Train Agent" button is clicked.
     */
    public void toggleTraining() {
        if (!isRunning) {
            startTraining();
        } else if (isPaused) {
            resumeTraining();
        } else {
            pauseTraining();
        }
    }

    /**
     * The main game loop tick.
     * Moves the snake autonomously and handles interactions.
     */
    private void gameLoopTick() {
        if (!isRunning || isPaused) return;
        
        int[] a = gameController.getVisionData();
        for (int i = 0; i < a.length; i++) {
        	System.out.print(a[i]);
        }
        System.out.println("Above");
        
        gameController.changeDirection(Dir.DOWN); //Whatever direction returned by neural network
        
        gameController.moveSnake();

        

        // Check for collisions
        if (checkCollision()) {
            stopGame("Snake collided! Respawning...");
            return;
        }

        // Check if the snake has eaten the apple
        SnakeNode head = gameController.getSnakeHead();
        Apple apple = gameController.getApple();
        if (head.getX() == apple.getX() && head.getY() == apple.getY()) {
            gameController.growSnake();
            applesEaten++;
            gameController.generateApple();
            onAppleEaten();
        }

        // Update the arena display
        updateArenaDisplay();
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
            System.out.println("Collision detected: Wall hit.");
            return true;
        }

        // Self-collision
        boolean collision = gameController.checkSelfCollision();
        if (collision) {
            System.out.println("Collision detected: Snake hit itself.");
        }
        return collision;
    }

    /**
     * Stops the game loop and handles respawning the snake.
     *
     * @param message the message to display upon game termination
     */
    private void stopGame(String message) {
        gameLoop.stop();
        isRunning = false;
        isPaused = false;
        System.out.println(message);

        // Notify the listener (PRAPage) about the game over
        Platform.runLater(() -> {
            praPage.showGameOverAlert(message);
        });

        // Use PauseTransition for non-blocking delay before respawning
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> respawnSnake());
        pause.play();
    }

    /**
     * Respawns the snake in the arena without creating a new instance.
     * Resets the game state and resumes training.
     */
    private void respawnSnake() {
        try {
            initializeGame();
            if (!isPaused) {
                gameLoop.play();
                isRunning = true;
                praPage.updateTrainButton("Halt Training");
                System.out.println("Snake respawned and training resumed.");
            }
        } catch (Exception e) {
            handleException("Error respawning snake: " + e.getMessage());
        }
    }

    /**
     * Handles actions when an apple is eaten.
     * Updates the apple counter in the UI.
     */
    private void onAppleEaten() {
        try {
            praPage.updateAppleCounter(applesEaten);
            System.out.println("Apple eaten. Total apples eaten: " + applesEaten);
        } catch (Exception e) {
            handleException("Error updating apple counter: " + e.getMessage());
        }
    }

    /**
     * Updates the arena grid to reflect the current snake and apple positions.
     */
    private void updateArenaDisplay() {
        try {
            // Clear the arena grid
            arena.clearGrid();

            // Update snake display
            SnakeNode current = gameController.getSnakeHead();
            boolean isHead = true;
            while (current != null) {
                arena.getTile(current.getX(), current.getY()).setFill(isHead ? Color.DARKGREEN : Color.LIMEGREEN);
                isHead = false;
                current = current.getNext();
            }

            // Update apple display
            Apple apple = gameController.getApple();
            if (apple != null) {
                arena.getTile(apple.getX(), apple.getY()).setFill(Color.RED);
            }
        } catch (Exception e) {
            handleException("Error updating arena display: " + e.getMessage());
        }
    }

    /**
     * Updates the snake's direction.
     * This method is kept for compatibility but is not used since the snake is controlled autonomously.
     *
     * @param newDirection the new direction for the snake
     */
    public void setSnakeDirection(Direction.Dir newDirection) {
        // Not used in PRA as the snake is controlled autonomously
    }

    /**
     * Stops the training and the game loop.
     */
    public void haltTraining() {
        if (!isRunning) {
            System.out.println("Training is not running. Nothing to halt.");
            return;
        }

        gameLoop.stop();
        isRunning = false;
        isPaused = false;
        praPage.updateTrainButton("Train Agent");
        System.out.println("Training halted.");
    }

    /**
     * Resets the training by respawning the snake and resetting counters.
     */
    public void resetTraining() {
        haltTraining();
        try {
            initializeGame();
            applesEaten = 0;
            praPage.updateAppleCounter(applesEaten);
            praPage.updateTrainButton("Train Agent");
            System.out.println("Training reset.");
        } catch (Exception e) {
            handleException("Error resetting training: " + e.getMessage());
        }
    }

    /**
     * Called when the game is over. Handles the game over event by showing an alert and respawning the snake.
     *
     * @param message the message associated with the game end
     */
    @Override
    public void onGameOver(String message) {
        Platform.runLater(() -> {
            praPage.showGameOverAlert(message);
            stopGame(message);
        });
    }

    /**
     * Called when an apple is eaten by the snake. Updates the apple counter.
     *
     * @param appleCount the total number of apples eaten so far
     */
    @Override
    public void onAppleEaten(int appleCount) {
        Platform.runLater(() -> {
            praPage.updateAppleCounter(appleCount);
        });
    }

    /**
     * Returns whether the training is currently running.
     *
     * @return true if training is running, false otherwise
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Returns whether the training is currently paused.
     *
     * @return true if training is paused, false otherwise
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Handles exceptions by logging them and optionally showing an alert.
     *
     * @param message the exception message
     */
    private void handleException(String message) {
        System.err.println(message);
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}

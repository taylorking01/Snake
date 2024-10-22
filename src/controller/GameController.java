package controller;

import arena.Arena;
import snake.Direction;
import snake.SnakeNode;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class GameController {
    private Arena arena;
    private Timeline timeline;
    private boolean isRunning;
    private CommandLog commandLog;  // Command log to store direction inputs

    public GameController(Arena arena) {
        this.arena = arena;
        this.isRunning = false;
        this.commandLog = new CommandLog();  // Initialize the command log

        // Initialize game timeline (animation loop)
        timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
    }

    // Method to start the game
    public void startGame() {
        if (!isRunning) {
            isRunning = true;
            commandLog = new CommandLog();  // Reset the command log for the new game
            arena.resetGame(); // Reset the arena when starting a new game
            timeline.play();  // Start the game loop
        }
    }

    // Method to stop the game
    public void stopGame() {
        timeline.stop();
        isRunning = false;
        commandLog.printCommandLog(); // Print the command log when the game ends
    }

    // Method to control the snake's direction based on key press
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                commandLog.addCommand(Direction.Dir.UP);
                break;
            case DOWN:
                commandLog.addCommand(Direction.Dir.DOWN);
                break;
            case LEFT:
                commandLog.addCommand(Direction.Dir.LEFT);
                break;
            case RIGHT:
                commandLog.addCommand(Direction.Dir.RIGHT);
                break;
            default:
                break;
        }
    }

    // Game loop to update the arena and move the snake
    private void gameLoop() {
        // Get the next command from the log, if available, and move the snake
        Direction.Dir currentDirection = arena.getCurrentDirection();
        SnakeNode head = arena.getSnakeHead();  // Get the head of the snake
        Direction.Dir newDirection = commandLog.getNextCommand(currentDirection, head);
        arena.changeSnakeDirection(newDirection);
        arena.update();  // Move snake and update grid

        if (arena.checkCollisions()) {
            stopGame();  // Stop the game if a collision occurs
        }
    }
}

package controller;

import arena.Arena;
import snake.Direction;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class GameController {
    private Arena arena;
    private Timeline timeline;
    private boolean isRunning;

    public GameController(Arena arena) {
        this.arena = arena;
        this.isRunning = false;

        // Initialize game timeline (animation loop)
        timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE); // Run indefinitely
    }

    // Method to start the game
    public void startGame() {
        if (!isRunning) {
            isRunning = true;
            arena.resetGame(); // Reset the arena when starting a new game
            timeline.play();  // Start the game loop
        }
    }

    // Method to stop the game
    public void stopGame() {
        timeline.stop();
        isRunning = false;
    }

    // Method to control the snake's direction based on key press
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                arena.changeSnakeDirection("UP");
                break;
            case DOWN:
                arena.changeSnakeDirection("DOWN");
                break;
            case LEFT:
                arena.changeSnakeDirection("LEFT");
                break;
            case RIGHT:
                arena.changeSnakeDirection("RIGHT");
                break;
            default:
                break;
        }
    }

    // Game loop to update the arena and move the snake
    private void gameLoop() {
        arena.update();
        if (arena.checkCollisions()) {
            stopGame(); // Stop the game on collision
            System.out.println("Game Over! Press 'Start Game' to play again.");
        }
    }

}

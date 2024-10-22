package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import snake.Direction;
import snake.SnakeNode;
import arena.Arena;

public class GameController {
    private Arena arena;
    private Timeline timeline;
    private boolean isRunning;
    private CommandLog commandLog;
    private boolean keyProcessed;

    public GameController(Arena arena) {
        this.arena = arena;
        this.isRunning = false;
        this.commandLog = new CommandLog();
        this.keyProcessed = false;

        // Initialize game timeline (animation loop)
        timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void startGame() {
        if (!isRunning) {
            isRunning = true;
            commandLog = new CommandLog();
            keyProcessed = false;
            arena.resetGame();
            timeline.play();
        }
    }
    
    public void printGameOutput(String output) {
    	System.out.println(output);
    }

    public void stopGame(String output) {
        if (isRunning) {
            timeline.stop();
            isRunning = false;
            printGameOutput(output);  // Print game outcome
            commandLog.printExecutedCommands();  // Print the executed command log
        }
    }

    public void handleKeyPress(KeyEvent event) {
        if (keyProcessed) return;  // Ignore if key is already processed in this frame

        switch (event.getCode()) {
            case UP:
                commandLog.enqueueCommand(Direction.Dir.UP);
                keyProcessed = true;  // Mark key as processed
                break;
            case DOWN:
                commandLog.enqueueCommand(Direction.Dir.DOWN);
                keyProcessed = true;
                break;
            case LEFT:
                commandLog.enqueueCommand(Direction.Dir.LEFT);
                keyProcessed = true;
                break;
            case RIGHT:
                commandLog.enqueueCommand(Direction.Dir.RIGHT);
                keyProcessed = true;
                break;
            default:
                break;
        }
    }

    private void gameLoop() {
        if (!isRunning) return; // Exit if the game is already over

        keyProcessed = false;  // Reset the flag to allow new keypresses in the next frame

        // Get the current direction of the snake
        Direction.Dir currentDirection = arena.getCurrentDirection();
        SnakeNode headNode = arena.getSnakeHead();

        // Fetch the next valid command
        Direction.Dir newDirection = commandLog.getNextValidCommand(currentDirection, headNode);

        // Change the snake's direction and update the arena
        arena.changeSnakeDirection(newDirection);
        arena.update();

        // Stop game if a collision occurs
        if (arena.checkCollisions()) {
            stopGame("Game Over!");
        } 
        // Stop game if win condition is met
        else if (arena.checkWinCondition()) {
            stopGame("Congratulations, You Win!");
        }
    }

}

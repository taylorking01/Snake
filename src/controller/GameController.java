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
            printGameOutput(output);
            //commandLog.printCommandLog();
        }
    }

    public void handleKeyPress(KeyEvent event) {
        if (keyProcessed) return;

        switch (event.getCode()) {
            case UP:
                commandLog.addCommand(Direction.Dir.UP, arena.getHeadPosition());
                keyProcessed = true;
                break;
            case DOWN:
                commandLog.addCommand(Direction.Dir.DOWN, arena.getHeadPosition());
                keyProcessed = true;
                break;
            case LEFT:
                commandLog.addCommand(Direction.Dir.LEFT, arena.getHeadPosition());
                keyProcessed = true;
                break;
            case RIGHT:
                commandLog.addCommand(Direction.Dir.RIGHT, arena.getHeadPosition());
                keyProcessed = true;
                break;
            default:
                break;
        }
    }

    private void gameLoop() {
        if (!isRunning) return; // Exit if the game is already over

        keyProcessed = false;  // Reset this after every game loop iteration

        Direction.Dir currentDirection = arena.getCurrentDirection();
        SnakeNode headNode = arena.getSnakeHead();
        Direction.Dir newDirection = commandLog.getNextCommand(currentDirection, headNode);
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

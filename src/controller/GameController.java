package controller;

import snake.Direction;
import snake.SnakeNode;
import arena.Arena;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

/**
 * The GameController class manages the Snake game logic, including handling user input,
 * controlling the snake's movements, and managing the game loop.
 */
public class GameController {
    private Arena arena;                // The arena where the snake moves
    private Timeline timeline;          // The game loop timeline
    private boolean isRunning;          // Indicates if the game is currently running
    private CommandLog commandLog;      // Logs commands and tracks the snake's movement
    private boolean keyProcessed;       // Prevents multiple key inputs in the same frame

    /**
     * Constructs a GameController object that manages the Snake game.
     * 
     * @param arena the game arena where the snake moves
     * 
     * This constructor initializes the game timeline and prepares the game controller to handle
     * game state and user input.
     */
    public GameController(Arena arena) {
        this.arena = arena;
        this.isRunning = false;
        this.commandLog = new CommandLog();
        this.keyProcessed = false;

        // Initialize the game timeline (animation loop)
        timeline = new Timeline(new KeyFrame(Duration.millis(300), e -> gameLoop()));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
    /* Private methods follow
    /**/

    /**
     * The main game loop that handles the snake's movement, collisions, and game state updates.
     * 
     * This method is repeatedly called by the timeline, controlling the game's flow,
     * processing input commands, and checking for win or loss conditions.
     */
    private void gameLoop() {
        if (!isRunning) return; // Exit if the game is not running

        keyProcessed = false;  // Reset flag to allow new key presses

        // Get the current direction of the snake
        Direction.Dir currentDirection = arena.getCurrentDirection();
        SnakeNode headNode = arena.getSnakeHead();

        // Fetch the next valid command from the command log
        Direction.Dir newDirection = commandLog.getNextValidCommand(currentDirection, headNode);

        // Update the snake's direction and the arena's state
        arena.changeSnakeDirection(newDirection);
        arena.update();

        // Check for collisions (end game if collision occurs)
        if (arena.checkCollisions()) {
            stopGame("Game Over!");
        }
        // Check for win condition (end game if snake fills the arena)
        else if (arena.checkWinCondition()) {
            stopGame("Congratulations, You Win!");
        }
    }

    /**
    /* Public methods follow
    /**/

    /**
     * Starts the Snake game and resets the game state.
     * 
     * If the game is not already running, this method starts the game, resets the command log,
     * and begins the game timeline (animation loop).
     */
    public void startGame() {
        if (!isRunning) {
            isRunning = true;
            commandLog = new CommandLog();  // Reset the command log
            keyProcessed = false;
            arena.resetGame();  // Reset the arena state
            timeline.play();  // Start the game loop
        }
    }

    /**
     * Stops the Snake game and prints the game result and command log.
     * 
     * @param output the game outcome message ("Game Over" or "You Win!")
     * 
     * This method stops the game timeline, prints the result, and displays the log of all commands executed.
     */
    public void stopGame(String output) {
        if (isRunning) {
            timeline.stop();  // Stop the game loop
            isRunning = false;
            printGameOutput(output);  // Print game outcome
            commandLog.printExecutedCommands();  // Print the executed command log
        }
    }

    /**
     * Handles key press events from the user to control the snake.
     * 
     * @param event the key event triggered by the user input
     * 
     * This method listens for directional input (UP, DOWN, LEFT, RIGHT) and enqueues commands
     * to change the snake's movement direction accordingly. Each key press is processed only once per frame.
     */
    public void handleKeyPress(KeyEvent event) {
        if (keyProcessed) return;  // Ignore if the key has already been processed in this frame

        switch (event.getCode()) {
            case UP:
                commandLog.enqueueCommand(Direction.Dir.UP);
                keyProcessed = true;
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

    /**
     * Prints the game outcome to the console.
     * 
     * @param output the message to be printed (e.g., "Game Over" or "Congratulations, You Win!")
     * 
     * This method is used to print the result of the game to the console.
     */
    public void printGameOutput(String output) {
        System.out.println(output);
    }
}

package arena;

import controller.GameController;
import javafx.scene.Scene;

/**
 * The ClassicMode class represents the classic game mode of the Snake game.
 * It configures and utilizes the GameController to manage game logic.
 */
public class ClassicMode {
    private final GameController gameController;    // The game controller managing game logic
    private final Arena arena;                      // The arena where the snake moves
    private final Scene scene;                      // The JavaFX scene for handling user input
    private final int gameSpeedMillis;              // The speed of the game in milliseconds per tick

    /**
     * Constructs a ClassicMode object.
     *
     * @param arena the Arena instance for rendering the game grid
     * @param scene the Scene instance for handling user input
     * @param gameSpeedMillis the speed of the game in milliseconds per tick
     */
    public ClassicMode(Arena arena, Scene scene, int gameSpeedMillis) {
        this.arena = arena;
        this.scene = scene;
        this.gameSpeedMillis = gameSpeedMillis;
        this.gameController = new GameController(arena, gameSpeedMillis);
        this.gameController.setupInputHandling(scene);
    }

    /**
     * Starts the classic mode game.
     */
    public void startGame() {
        gameController.startGame();
    }

    /**
     * Pauses the classic mode game.
     */
    public void pauseGame() {
        gameController.pauseGame();
    }

    /**
     * Resumes the classic mode game.
     */
    public void resumeGame() {
        gameController.resumeGame();
    }

    /**
     * Stops the classic mode game with a message.
     *
     * @param message the message to display upon game termination
     */
    public void stopGame(String message) {
        gameController.stopGame(message);
    }

    /**
     * Checks if the game is currently running.
     *
     * @return true if the game is running, false otherwise
     */
    public boolean isRunning() {
        return gameController.isRunning();
    }
}

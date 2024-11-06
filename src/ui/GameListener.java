package ui;

/**
 * The GameListener interface defines a callback method that is invoked when the game ends.
 */
public interface GameListener {
    /**
     * Called when the game ends.
     *
     * @param message the message associated with the game end (e.g., "Game Over!", "You Won!")
     */
    void onGameOver(String message);
}

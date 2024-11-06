package ui;

/**
 * The GameListener interface defines callback methods that are invoked during game events.
 */
public interface GameListener {
    /**
     * Called when the game ends.
     *
     * @param message the message associated with the game end (e.g., "Game Over!", "You Won!")
     */
    void onGameOver(String message);
    
    /**
     * Called when an apple is eaten by the snake.
     *
     * @param appleCount the total number of apples eaten so far
     */
    void onAppleEaten(int appleCount);
}

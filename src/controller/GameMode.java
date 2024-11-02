package controller;

/**
 * The GameMode interface defines the contract for different game modes in the Snake game.
 * Each game mode implements this interface to provide its specific game logic and configurations.
 */
public interface GameMode {
    
    /**
     * Invoked on each game loop tick to perform game-specific actions.
     *
     * @param controller the GameController instance managing the game.
     */
    void onTick(GameController controller);
    
    /**
     * Invoked when a collision is detected in the game.
     *
     * @param controller the GameController instance managing the game.
     */
    void onCollision(GameController controller);
    
    /**
     * Invoked when a snake eats an apple.
     *
     * @param controller the GameController instance managing the game.
     * @param snakeId the identifier of the snake that ate the apple.
     */
    void onAppleEaten(GameController controller, int snakeId);
    
    /**
     * Checks if the win condition has been met.
     *
     * @param controller the GameController instance managing the game.
     * @return true if the win condition is satisfied, false otherwise.
     */
    boolean checkWinCondition(GameController controller);
    
    /**
     * Invoked when the win condition is met.
     *
     * @param controller the GameController instance managing the game.
     */
    void onWin(GameController controller);
    
    /**
     * Gets the number of snakes in the game.
     *
     * @return the number of snakes.
     */
    int getNumSnakes();
    
    /**
     * Gets the number of apples in the game.
     *
     * @return the number of apples.
     */
    int getNumApples();
    
    /**
     * Calculates the game duration based on arena size.
     *
     * @param rows the number of rows in the arena.
     * @param cols the number of columns in the arena.
     * @return the game duration in seconds.
     */
    int calculateGameTime(int rows, int cols);
}

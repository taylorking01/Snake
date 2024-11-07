package controller;

import arena.Arena;
import apple.Apple;
import snake.Direction;
import snake.Position;
import snake.SnakeLinkedList;
import snake.SnakeNode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

/**
 * The GameController class manages the Snake game objects, such as the snake and the apple.
 * It provides methods to manipulate these objects but does not handle game loops or control flows.
 */
public class GameController {
    private final Arena arena;                // The arena where the snake moves
    private SnakeLinkedList snake;            // The snake object
    private Apple apple;                      // The current apple in the game
    private final Queue<Direction.Dir> directionQueue; // Queue to hold direction changes

    /**
     * Constructs a GameController object that manages the Snake game objects.
     *
     * @param arena the game arena where the snake moves
     */
    public GameController(Arena arena) {
        this.arena = arena;
        this.snake = null;
        this.apple = null;
        this.directionQueue = new LinkedList<>();
    }

    /**
     * Initializes the snake at the center of the arena.
     */
    public void initializeSnake(boolean hasVision) {
        int startX = arena.getCols() / 2;
        int startY = arena.getRows() / 2;
        snake = new SnakeLinkedList(startX, startY, hasVision); // 'false' indicates vision is disabled
    }

    /**
     * Generates a new apple at a random position not occupied by the snake.
     */
    public void generateApple() {
        if (snake == null) {
            throw new IllegalStateException("Snake has not been initialized.");
        }

        Set<Position> snakeBodyPositions = getSnakeBodyPositions();
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(arena.getCols());
            y = random.nextInt(arena.getRows());
        } while (isSnakeAtPosition(x, y, snakeBodyPositions));
        apple = new Apple(x, y);
    }

    /**
     * Changes the snake's direction by adding it to the queue.
     *
     * @param direction the new direction to set
     */
    public void changeDirection(Direction.Dir direction) {
        if (snake == null) {
            throw new IllegalStateException("Snake has not been initialized.");
        }
        // Prevent reversing directly
        if (!isOppositeDirection(direction, snake.getCurrentDirection())) {
            directionQueue.offer(direction);
        }
    }

    /**
     * Moves the snake based on the current (or pending) direction.
     * This method should be called once per game tick.
     */
    public void moveSnake() {
        if (snake == null) {
            throw new IllegalStateException("Snake has not been initialized.");
        }
        // Apply the next direction from the queue if available
        if (!directionQueue.isEmpty()) {
            Direction.Dir nextDir = directionQueue.poll();
            if (!isOppositeDirection(nextDir, snake.getCurrentDirection())) {
                snake.changeDirection(nextDir);
            }
        }
        snake.move(apple.getX(), apple.getY(), arena.getRows(), arena.getCols(), getSnakeBodyPositions());
    }

    /**
     * Grows the snake by one segment.
     */
    public void growSnake() {
        if (snake == null) {
            throw new IllegalStateException("Snake has not been initialized.");
        }
        snake.grow();
    }

    /**
     * Retrieves the positions occupied by the snake's body (excluding the head).
     *
     * @return a set of Positions occupied by the snake's body
     */
    public Set<Position> getSnakeBodyPositions() {
        Set<Position> positions = new HashSet<>();
        SnakeNode current = snake.getHead().getNext(); // Exclude head
        while (current != null) {
            positions.add(new Position(current.getX(), current.getY()));
            current = current.getNext();
        }
        return positions;
    }

    /**
     * Checks if the snake occupies a given position.
     *
     * @param x                   the x-coordinate (column)
     * @param y                   the y-coordinate (row)
     * @param snakeBodyPositions  the set of positions occupied by the snake's body
     * @return true if the snake is at (x, y), false otherwise
     */
    public boolean isSnakeAtPosition(int x, int y, Set<Position> snakeBodyPositions) {
        return snakeBodyPositions.contains(new Position(x, y));
    }

    /**
     * Retrieves the current apple.
     *
     * @return the current Apple object
     */
    public Apple getApple() {
        return apple;
    }

    /**
     * Retrieves the snake's head.
     *
     * @return the head SnakeNode
     */
    public SnakeNode getSnakeHead() {
        return snake.getHead();
    }

    /**
     * Retrieves the current direction of the snake.
     *
     * @return the current Direction.Dir
     */
    public Direction.Dir getCurrentDirection() {
        return snake.getCurrentDirection();
    }

    /**
     * Retrieves the length of the snake.
     *
     * @return the length of the snake
     */
    public int getSnakeLength() {
        return snake.getLength();
    }

    /**
     * Retrieves all positions occupied by the snake.
     *
     * @return a set of Positions occupied by the snake
     */
    public Set<Position> getAllSnakePositions() {
        Set<Position> positions = new HashSet<>();
        SnakeNode current = snake.getHead();
        while (current != null) {
            positions.add(new Position(current.getX(), current.getY()));
            current = current.getNext();
        }
        return positions;
    }

    /**
     * Helper method to check if two directions are opposites.
     *
     * @param newDir     the new direction
     * @param currentDir the current direction
     * @return true if opposite, false otherwise
     */
    private boolean isOppositeDirection(Direction.Dir newDir, Direction.Dir currentDir) {
        return (newDir == Direction.Dir.UP && currentDir == Direction.Dir.DOWN) ||
               (newDir == Direction.Dir.DOWN && currentDir == Direction.Dir.UP) ||
               (newDir == Direction.Dir.LEFT && currentDir == Direction.Dir.RIGHT) ||
               (newDir == Direction.Dir.RIGHT && currentDir == Direction.Dir.LEFT);
    }

    public boolean checkSelfCollision() {
        if (snake == null) {
            throw new IllegalStateException("Snake has not been initialized.");
        }

        SnakeNode head = snake.getHead();
        int headX = head.getX();
        int headY = head.getY();

        SnakeNode current = head.getNext(); // Start checking from the node after the head
        while (current != null) {
            if (current.getX() == headX && current.getY() == headY) {
                return true; // Collision detected
            }
            current = current.getNext();
        }

        return false; // No collision
    }
    
    // In GameController.java
    public int[] getVisionData() {
        if (snake != null) {
            return snake.getVisionData();
        }
        return new int[0]; // Return an empty array if the snake is not initialized
    }
}

package ai;

import snake.SnakeLinkedList;
import snake.Direction;
import controller.SlidingWindow;

public class AISnake extends SnakeLinkedList {
    private NeuralNetwork brain;
    private SlidingWindow slidingWindow;
    private double fitness;

    public AISnake(int startX, int startY) {
        super(startX, startY);
        brain = new NeuralNetwork(10, new int[]{7}, 3); // Adjust layers as needed
        slidingWindow = new SlidingWindow();
        fitness = 0.0;
    }

    public AISnake(int startX, int startY, NeuralNetwork brain) {
        super(startX, startY);
        this.brain = brain;
        slidingWindow = new SlidingWindow();
        fitness = 0.0;
    }

    /**
     * Decides the next move based on the neural network's output.
     */
    public void decideNextMove() {
        double[] input = slidingWindow.getInputArray();
        double[] output = brain.forward(input);
        int actionIndex = argMax(output);

        Direction.Dir currentDirection = getCurrentDirection();
        Direction.Dir newDirection = determineNewDirection(currentDirection, actionIndex);
        changeDirection(newDirection);
    }

    /**
     * Determines the new direction based on the action index.
     *
     * @param currentDirection the current direction of the snake
     * @param actionIndex      the index of the action chosen by the network
     * @return the new direction
     */
    private Direction.Dir determineNewDirection(Direction.Dir currentDirection, int actionIndex) {
        // actionIndex: 0 - LEFT, 1 - RIGHT, 2 - FORWARD
        switch (actionIndex) {
            case 0:
                return turnLeft(currentDirection);
            case 1:
                return turnRight(currentDirection);
            case 2:
                return currentDirection;
            default:
                return currentDirection;
        }
    }

    private int argMax(double[] array) {
        int index = 0;
        double max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                index = i;
                max = array[i];
            }
        }
        return index;
    }

    private Direction.Dir turnLeft(Direction.Dir currentDirection) {
        switch (currentDirection) {
            case UP:
                return Direction.Dir.LEFT;
            case DOWN:
                return Direction.Dir.RIGHT;
            case LEFT:
                return Direction.Dir.DOWN;
            case RIGHT:
                return Direction.Dir.UP;
            default:
                return currentDirection;
        }
    }

    private Direction.Dir turnRight(Direction.Dir currentDirection) {
        switch (currentDirection) {
            case UP:
                return Direction.Dir.RIGHT;
            case DOWN:
                return Direction.Dir.LEFT;
            case LEFT:
                return Direction.Dir.UP;
            case RIGHT:
                return Direction.Dir.DOWN;
            default:
                return currentDirection;
        }
    }

    // Getter and setter for the brain (neural network)
    public NeuralNetwork getBrain() {
        return brain;
    }

    public void setBrain(NeuralNetwork brain) {
        this.brain = brain;
    }

    // Fitness methods
    public void addFitness(double value) {
        fitness += value;
    }

    public double getFitness() {
        return fitness;
    }

    // Update sliding window
    public void updateSlidingWindow(Direction.Dir move) {
        slidingWindow.addMove(move);
    }

    public void resetSlidingWindow() {
        slidingWindow.reset();
    }
}

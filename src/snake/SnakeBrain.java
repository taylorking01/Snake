package snake;

import ai.NeuralNetwork;
import ai.Layer;
import ai.ActivationFunction;

public class SnakeBrain {
    private NeuralNetwork network;

    /**
     * Constructs the SnakeBrain with a predefined neural network architecture.
     * The network has three layers: input -> hidden1 -> hidden2 -> output.
     */
    public SnakeBrain() {
        // Initialize neural network structure
        network = new NeuralNetwork();

        // Define the neural network architecture
        network.addLayer(new Layer(15, 9, ActivationFunction.relu)); // Input to Hidden Layer 1
        network.addLayer(new Layer(9, 6, ActivationFunction.relu));  // Hidden Layer 1 to Hidden Layer 2
        network.addLayer(new Layer(6, 3, ActivationFunction.softmax)); // Hidden Layer 2 to Output Layer
    }

    /**
     * Processes the snake's environment data and returns the best decision index.
     * 
     * @param inputData an array of doubles representing the environment state:
     *                  - -10 = Wall
     *                  - -3 = Snake Body
     *                  - 5 = Clear
     *                  - 10 = Apple
     * @return an integer representing the index of the highest-probability action
     */
    public int decideNextMove(double[] inputData) {
        // Perform a forward pass through the network with the provided input data
        double[] outputLayerOutputs = network.forward(inputData);

        // Print output layer probabilities for debugging
        System.out.println("Output Layer Probabilities:");
        for (double output : outputLayerOutputs) {
            System.out.println(output);
        }

        // Determine the action with the highest probability
        int decisionIndex = network.getHighestProbabilityIndex();
        System.out.println("The highest probability is at index " + decisionIndex + 
                           " with a probability of " + outputLayerOutputs[decisionIndex]);
        return decisionIndex;
    }
}

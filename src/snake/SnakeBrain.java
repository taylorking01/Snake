package snake;

import ai.NeuralNetwork;
import ai.Layer;
import ai.ActivationFunction;
import ai.LossFunction;

public class SnakeBrain {
    private NeuralNetwork network;

    public SnakeBrain() {
        // Initialize the neural network with MSE as the loss function
        network = new NeuralNetwork(LossFunction.meanSquaredError);

        // Define the neural network architecture
        network.addLayer(new Layer(15, 9, ActivationFunction.relu)); // Input to Hidden Layer 1
        network.addLayer(new Layer(9, 6, ActivationFunction.relu));  // Hidden Layer 1 to Hidden Layer 2
        network.addLayer(new Layer(6, 3, ActivationFunction.softmax)); // Hidden Layer 2 to Output Layer
    }

    public int decideNextMove(double[] inputData) {
        double[] outputLayerOutputs = network.forward(inputData);

        System.out.println("Output Layer Probabilities:");
        for (double output : outputLayerOutputs) {
            System.out.println(output);
        }

        int decisionIndex = network.getHighestProbabilityIndex();
        System.out.println("The highest probability is at index " + decisionIndex +
                           " with a probability of " + outputLayerOutputs[decisionIndex]);
        return decisionIndex;
    }

    public double calculateLoss(double[] targetOutput, double[] predictedOutput) {
        double loss = network.calculateLoss(targetOutput, predictedOutput);
        System.out.println("Calculated Loss (MSE): " + loss);
        return loss;
    }

    public static void main(String[] args) {
        SnakeBrain snakeBrain = new SnakeBrain();

        double[] inputData = {5, -10, -10, -10, 5, -3, 5, 10, -3, 5, -10, 5, -10, -3, 10};
        double[] targetOutput = {0.0, 1.0, 0.0};

        int decision = snakeBrain.decideNextMove(inputData);
        double[] predictedOutput = snakeBrain.network.forward(inputData);

        snakeBrain.calculateLoss(targetOutput, predictedOutput);
    }
}

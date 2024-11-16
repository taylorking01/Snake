package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class NeuralNetwork {
    private List<Layer> layers;
    private BiFunction<double[], double[], Double> lossFunction;

    // Constructor: Initializes the network architecture and loss function
    public NeuralNetwork(BiFunction<double[], double[], Double> lossFunction) {
        this.layers = new ArrayList<>();
        this.lossFunction = lossFunction;
    }

    // Adds a layer to the network
    public void addLayer(Layer layer) {
        layers.add(layer);
    }

    // Performs a forward pass through the entire network
    public double[] forward(double[] inputData) {
        double[] outputs = inputData;
        for (Layer layer : layers) {
            layer.forward(outputs);
            outputs = layer.getOutputs();
        }
        return outputs;
    }

    // Calculates the loss between target values and network predictions
    public double calculateLoss(double[] yTrue, double[] yPred) {
        return lossFunction.apply(yTrue, yPred);
    }

    // Finds the index of the highest probability in the final output layer
    public int getHighestProbabilityIndex() {
        Layer outputLayer = layers.get(layers.size() - 1); // Last layer in the network
        return outputLayer.getHighestProbabilityIndex();
    }

    public static void main(String[] args) {
        // Example usage: Create a neural network with specific architecture

        // Define the neural network structure and loss function
        NeuralNetwork network = new NeuralNetwork(LossFunction.crossEntropyLoss);

        // Add layers to the network (input -> hidden1 -> hidden2 -> output)
        network.addLayer(new Layer(15, 9, ActivationFunction.relu)); // Input to Hidden Layer 1
        network.addLayer(new Layer(9, 6, ActivationFunction.relu));  // Hidden Layer 1 to Hidden Layer 2
        network.addLayer(new Layer(6, 3, ActivationFunction.softmax)); // Hidden Layer 2 to Output Layer

        // Input data representing the environment (0=Wall, 1=Snake Body, 2=Clear, 3=Apple)
        double[] inputData = {1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0};

        // Target output for supervised learning
        double[] targetOutput = {0.0, 1.0, 0.0}; // Example target (class probabilities)

        // Perform a forward pass through the network
        double[] predictedOutput = network.forward(inputData);

        // Calculate the loss between predictions and the target output
        double loss = network.calculateLoss(targetOutput, predictedOutput);

        // Print final output probabilities
        System.out.println("Output Layer Probabilities:");
        for (double output : predictedOutput) {
            System.out.println(output);
        }

        // Print loss
        System.out.println("Loss: " + loss);

        // Get the highest probability index and value
        int decisionIndex = network.getHighestProbabilityIndex();
        System.out.println("The highest probability is at index " + decisionIndex +
                           " with a probability of " + predictedOutput[decisionIndex]);
    }
}

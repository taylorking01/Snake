package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class NeuralNetwork {
    private List<Layer> layers;
    //private Function<double[], double[]> lossFunction;


    // Constructor: Initializes the network architecture by adding layers
    public NeuralNetwork() {
        layers = new ArrayList<>();
        //this.lossFunction = lossFunction;
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

    // Finds the index of the highest probability in the final output layer
    public int getHighestProbabilityIndex() {
        Layer outputLayer = layers.get(layers.size() - 1); // Last layer in the network
        return outputLayer.getHighestProbabilityIndex();
    }

    public static void main(String[] args) {
        // Example usage: Create a neural network with specific architecture

        // Define the neural network structure
        NeuralNetwork network = new NeuralNetwork();

        // Add layers to the network (input -> hidden1 -> hidden2 -> output)
        network.addLayer(new Layer(15, 9, ActivationFunction.relu)); // Input to Hidden Layer 1
        network.addLayer(new Layer(9, 6, ActivationFunction.relu));  // Hidden Layer 1 to Hidden Layer 2
        network.addLayer(new Layer(6, 3, ActivationFunction.softmax)); // Hidden Layer 2 to Output Layer

        // Create input data representing the environment (0=Wall, 1=Snake Body, 2=Clear, 3=Apple)
        double[] inputData = {1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0};

        // Perform a forward pass through the network
        double[] outputLayerOutputs = network.forward(inputData);

        // Print final output probabilities
        System.out.println("Output Layer Probabilities:");
        for (double output : outputLayerOutputs) {
            System.out.println(output);
        }

        // Get the highest probability index and value
        int decisionIndex = network.getHighestProbabilityIndex();
        System.out.println("The highest probability is at index " + decisionIndex + " with a probability of " + outputLayerOutputs[decisionIndex]);
    }
}

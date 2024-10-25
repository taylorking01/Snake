package ai;

import java.util.Random;
import java.util.function.Function;

public class Layer {
    private double[] inputs;
    private double[][] weights;
    private double[] biases;
    private Function<double[], double[]> activationFunction;
    private double[] outputs;

    // Constructor: Initializes weights, biases, and sets the activation function
    public Layer(int inputSize, int outputSize, Function<double[], double[]> activationFunction) {
        this.weights = new double[outputSize][inputSize];
        this.biases = new double[outputSize];
        this.activationFunction = activationFunction;
        initializeWeightsAndBiases();
    }

    // Initializes weights and biases with random values
    private void initializeWeightsAndBiases() {
        Random rand = new Random();
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                weights[i][j] = rand.nextInt(3) + 1; // Random values between 1 and 3
            }
            biases[i] = 1; // Set all biases to 1
        }
    }

    // Forward pass through the layer
    public void forward(double[] inputs) {
        this.inputs = inputs;
        double[] weightedSums = new double[biases.length];

        for (int i = 0; i < weights.length; i++) {
            double sum = 0.0;
            for (int j = 0; j < inputs.length; j++) {
                sum += weights[i][j] * inputs[j];
            }
            sum += biases[i]; // Add bias to the weighted sum
            weightedSums[i] = sum;
        }

        // Apply the activation function
        outputs = activationFunction.apply(weightedSums);
    }

    // Returns the output of the layer after activation
    public double[] getOutputs() {
        return outputs;
    }

    // Finds the index of the highest probability in the output layer
    public int getHighestProbabilityIndex() {
        double maxOutput = outputs[0];
        int maxIndex = 0;

        for (int i = 1; i < outputs.length; i++) {
            if (outputs[i] > maxOutput) {
                maxOutput = outputs[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static void main(String[] args) {
        // Example setup for the Layer class with ActivationFunction

        // Create an input environment (0=Wall, 1=Snake Body, 2=Clear, 3=Apple)
        double[] inputData = {1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0};

        // Construct layers
        Layer inputLayer = new Layer(15, 9, ActivationFunction.relu); // Input to hidden layer
        Layer hiddenLayer1 = new Layer(9, 6, ActivationFunction.relu); // Hidden layer 1 to hidden layer 2
        Layer hiddenLayer2 = new Layer(6, 3, ActivationFunction.softmax); // Hidden layer to output layer

        // Forward pass through the layers
        inputLayer.forward(inputData);
        double[] hiddenLayer1Outputs = inputLayer.getOutputs();

        hiddenLayer1.forward(hiddenLayer1Outputs);
        double[] hiddenLayer2Outputs = hiddenLayer1.getOutputs();

        hiddenLayer2.forward(hiddenLayer2Outputs);
        double[] outputLayerOutputs = hiddenLayer2.getOutputs();

        // Print final output probabilities
        System.out.println("Output Layer Probabilities:");
        for (double output : outputLayerOutputs) {
            System.out.println(output);
        }

        // Get the highest probability index and value
        int decisionIndex = hiddenLayer2.getHighestProbabilityIndex();
        System.out.println("The highest probability is at index " + decisionIndex + " with a probability of " + outputLayerOutputs[decisionIndex]);
    }
}

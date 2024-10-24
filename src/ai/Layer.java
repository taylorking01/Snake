package ai;

import java.util.Random;
import java.util.function.Function;

public class Layer {
    private double[] inputs;
    private double[][] weights;
    private double[] biases;
    private Function<double[], double[]> activationFunction;
    private double[] outputs;

    public Layer(int inputSize, int outputSize, Function<double[], double[]> activationFunction) {
        this.weights = new double[outputSize][inputSize];
        this.biases = new double[outputSize];
        this.activationFunction = activationFunction;
        initializeWeightsAndBiases();
    }

    private void initializeWeightsAndBiases() {
        Random rand = new Random(); // Random generator for integer values
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                // Assign random integer weights in the range [-5, 5]
                weights[i][j] = rand.nextInt(3) - 1; // Range -5 to 5
            }
            biases[i] = 1; // Set all biases to 1
        }
    }


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

    public double[] getOutputs() {
        return outputs;
    }

    // Activation functions
    public static Function<double[], double[]> relu = x -> {
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            result[i] = Math.max(0, x[i]); // ReLU: max(0, x)
        }
        return result;
    };

    public static Function<double[], double[]> softmax = x -> {
        // Find max value for numerical stability (to prevent overflow in exponentiation)
        double max = x[0];
        for (int i = 1; i < x.length; i++) {
            if (x[i] > max) {
                max = x[i];
            }
        }

        // Compute exponentials and sum
        double[] expValues = new double[x.length];
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            expValues[i] = Math.exp(x[i] - max); // Subtract max for numerical stability
            sum += expValues[i];
        }

        // Normalize the exponentials to get probabilities
        for (int i = 0; i < expValues.length; i++) {
            expValues[i] /= sum;
        }

        return expValues;
    };
    
    public static void main(String[] args) {
        // Initialize random number generator
        Random random = new Random();

        // Generate 5 random values for the environment (0 for wall, 1 for snake body, 2 for clear, 3 for apple)
        int[] environment = new int[5];
        for (int i = 0; i < environment.length; i++) {
            environment[i] = random.nextInt(4); // Random values between 0 and 3
        }

        // Prepare input for the 15 neurons: 3 sets of 5 values (walls, snake, apple)
        double[] inputData = new double[15];
        
        for (int i = 0; i < 5; i++) {
            // Set the first 5 neurons to represent walls
            inputData[i] = (environment[i] == 0) ? 1.0 : 0.0; // If it's a wall, set it to 1, otherwise 0

            // Set the next 5 neurons to represent snake body
            inputData[i + 5] = (environment[i] == 1) ? 1.0 : 0.0; // If it's snake body, set it to 1, otherwise 0

            // Set the final 5 neurons to represent apple
            inputData[i + 10] = (environment[i] == 3) ? 1.0 : 0.0; // If it's an apple, set it to 1, otherwise 0
        }

        // Print out the environment for debugging
        System.out.println("Generated environment: ");
        for (int i = 0; i < environment.length; i++) {
            System.out.println("Square " + i + ": " + environment[i] + " (0=Wall, 1=Snake Body, 2=Clear, 3=Apple)");
        }

        // Construct layers
        Layer inputLayer = new Layer(15, 9, Layer.relu); // Input to Hidden Layer 1 (15 -> 9)
        Layer hiddenLayer1 = new Layer(9, 6, Layer.relu); // Hidden Layer 1 to Hidden Layer 2 (9 -> 6)
        Layer hiddenLayer2 = new Layer(6, 3, Layer.softmax); // Hidden Layer 2 to Output Layer (6 -> 3)

        // Forward pass through the layers
        inputLayer.forward(inputData);
        double[] hiddenLayer1Outputs = inputLayer.getOutputs(); // Outputs of first hidden layer

        hiddenLayer1.forward(hiddenLayer1Outputs);
        double[] hiddenLayer2Outputs = hiddenLayer1.getOutputs(); // Outputs of second hidden layer

        hiddenLayer2.forward(hiddenLayer2Outputs);
        double[] outputLayerOutputs = hiddenLayer2.getOutputs(); // Final output

        // Output the results (softmax probabilities)
        System.out.println("Output Layer Probabilities:");
        for (double output : outputLayerOutputs) {
            System.out.println(output);
        }
        
        // Find and display the largest output
        double maxOutput = outputLayerOutputs[0];
        int maxIndex = 0;
        for (int i = 1; i < outputLayerOutputs.length; i++) {
            if (outputLayerOutputs[i] > maxOutput) {
                maxOutput = outputLayerOutputs[i];
                maxIndex = i;
            }
        }

        System.out.println("The largest output is at index " + maxIndex + " with a value of " + maxOutput);
    }}

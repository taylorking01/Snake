package ai;

import java.util.Random;

public class NeuralNetwork {
    private Layer[] layers; // Array of layers

    /**
     * Constructs a NeuralNetwork with the specified architecture.
     *
     * @param inputSize   the size of the input layer
     * @param hiddenSizes an array representing the sizes of hidden layers
     * @param outputSize  the size of the output layer
     */
    public NeuralNetwork(int inputSize, int[] hiddenSizes, int outputSize) {
        int numLayers = hiddenSizes.length + 1;
        layers = new Layer[numLayers];

        // Input to first hidden layer
        layers[0] = new Layer(inputSize, hiddenSizes[0]);

        // Hidden layers
        for (int i = 1; i < hiddenSizes.length; i++) {
            layers[i] = new Layer(hiddenSizes[i - 1], hiddenSizes[i]);
        }

        // Last hidden layer to output layer
        layers[numLayers - 1] = new Layer(hiddenSizes[hiddenSizes.length - 1], outputSize);
    }

    /**
     * Performs a forward pass through the network.
     *
     * @param input the input vector
     * @return the output vector
     */
    public double[] forward(double[] input) {
        double[] output = input;
        for (int i = 0; i < layers.length - 1; i++) {
            output = layers[i].forward(output);
        }
        // Output layer with softmax activation
        output = softmax(layers[layers.length - 1].forward(output));
        return output;
    }

    /**
     * Softmax activation function.
     *
     * @param input the input vector
     * @return the softmax output vector
     */
    private double[] softmax(double[] input) {
        double[] output = new double[input.length];
        double max = Double.NEGATIVE_INFINITY;
        for (double v : input) {
            if (v > max) max = v;
        }
        double sum = 0.0;
        for (int i = 0; i < input.length; i++) {
            output[i] = Math.exp(input[i] - max);
            sum += output[i];
        }
        for (int i = 0; i < output.length; i++) {
            output[i] /= sum;
        }
        return output;
    }

    // Methods for genetic algorithm (mutation, crossover, cloning)

    /**
     * Clones the neural network.
     *
     * @return a new NeuralNetwork with the same weights and biases
     */
    public NeuralNetwork clone() {
        NeuralNetwork clone = new NeuralNetwork(0, new int[]{}, 0); // Empty constructor
        clone.layers = new Layer[this.layers.length];
        for (int i = 0; i < this.layers.length; i++) {
            Layer layer = this.layers[i];
            Layer layerClone = new Layer(layer.getInputSize(), layer.getOutputSize());
            layerClone.setWeights(deepCopy(layer.getWeights()));
            layerClone.setBiases(layer.getBiases().clone());
            clone.layers[i] = layerClone;
        }
        return clone;
    }

    /**
     * Performs mutation on the neural network's weights and biases.
     *
     * @param mutationRate the probability of each weight/bias being mutated
     */
    public void mutate(double mutationRate) {
        Random rand = new Random();
        for (Layer layer : layers) {
            double[][] weights = layer.getWeights();
            double[] biases = layer.getBiases();

            for (int i = 0; i < weights.length; i++) {
                for (int j = 0; j < weights[i].length; j++) {
                    if (rand.nextDouble() < mutationRate) {
                        weights[i][j] += rand.nextGaussian() * 0.1; // Small Gaussian noise
                    }
                }
            }

            for (int i = 0; i < biases.length; i++) {
                if (rand.nextDouble() < mutationRate) {
                    biases[i] += rand.nextGaussian() * 0.1;
                }
            }
        }
    }

    /**
     * Performs crossover with another neural network.
     *
     * @param partner the other neural network
     * @return a new NeuralNetwork offspring
     */
    public NeuralNetwork crossover(NeuralNetwork partner) {
        NeuralNetwork child = new NeuralNetwork(0, new int[]{}, 0); // Empty constructor
        child.layers = new Layer[this.layers.length];
        Random rand = new Random();

        for (int i = 0; i < this.layers.length; i++) {
            Layer layer = this.layers[i];
            Layer partnerLayer = partner.layers[i];
            Layer childLayer = new Layer(layer.getInputSize(), layer.getOutputSize());

            double[][] weights = layer.getWeights();
            double[][] partnerWeights = partnerLayer.getWeights();
            double[][] childWeights = new double[weights.length][weights[0].length];

            double[] biases = layer.getBiases();
            double[] partnerBiases = partnerLayer.getBiases();
            double[] childBiases = new double[biases.length];

            // Single-point crossover
            int crossoverPoint = rand.nextInt(weights.length * weights[0].length);
            int count = 0;
            for (int j = 0; j < weights.length; j++) {
                for (int k = 0; k < weights[j].length; k++) {
                    if (count < crossoverPoint) {
                        childWeights[j][k] = weights[j][k];
                    } else {
                        childWeights[j][k] = partnerWeights[j][k];
                    }
                    count++;
                }
            }

            // Biases crossover
            for (int j = 0; j < biases.length; j++) {
                childBiases[j] = (rand.nextBoolean()) ? biases[j] : partnerBiases[j];
            }

            childLayer.setWeights(childWeights);
            childLayer.setBiases(childBiases);
            child.layers[i] = childLayer;
        }
        return child;
    }

    // Utility method for deep copying 2D arrays
    private double[][] deepCopy(double[][] original) {
        if (original == null) {
            return null;
        }
        final double[][] result = new double[original.length][];
        for (int r = 0; r < original.length; r++) {
            result[r] = original[r].clone();
        }
        return result;
    }
}

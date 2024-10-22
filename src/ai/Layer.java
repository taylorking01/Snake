package ai;

import java.util.Random;

public class Layer {
    private int inputSize;
    private int outputSize;
    private double[][] weights; // [inputSize][outputSize]
    private double[] biases;    // [outputSize]

    /**
     * Constructs a Layer with the specified input and output sizes.
     *
     * @param inputSize  the size of the input vector
     * @param outputSize the number of neurons in the layer
     */
    public Layer(int inputSize, int outputSize) {
        this.setInputSize(inputSize);
        this.setOutputSize(outputSize);
        weights = new double[inputSize][outputSize];
        biases = new double[outputSize];
        initializeWeights();
    }

    /**
     * Initializes weights and biases with random values.
     */
    private void initializeWeights() {
        Random rand = new Random();
        double range = Math.sqrt(2.0 / getInputSize()); // He initialization
        for (int i = 0; i < getInputSize(); i++) {
            for (int j = 0; j < getOutputSize(); j++) {
                weights[i][j] = rand.nextGaussian() * range;
            }
        }
        for (int i = 0; i < getOutputSize(); i++) {
            biases[i] = 0.0;
        }
    }

    /**
     * Performs a forward pass using ReLU activation.
     *
     * @param input the input vector
     * @return the output vector after activation
     */
    public double[] forward(double[] input) {
        double[] output = new double[getOutputSize()];

        for (int j = 0; j < getOutputSize(); j++) {
            double sum = biases[j];
            for (int i = 0; i < getInputSize(); i++) {
                sum += input[i] * weights[i][j];
            }
            output[j] = relu(sum);
        }
        return output;
    }

    /**
     * ReLU activation function.
     *
     * @param x the input value
     * @return max(0, x)
     */
    private double relu(double x) {
        return Math.max(0, x);
    }

    // Getters and setters for weights and biases (needed for GA)
    public double[][] getWeights() {
        return weights;
    }

    public double[] getBiases() {
        return biases;
    }

    public void setWeights(double[][] weights) {
        this.weights = weights;
    }

    public void setBiases(double[] biases) {
        this.biases = biases;
    }

	public int getInputSize() {
		return inputSize;
	}

	public void setInputSize(int inputSize) {
		this.inputSize = inputSize;
	}

	public int getOutputSize() {
		return outputSize;
	}

	public void setOutputSize(int outputSize) {
		this.outputSize = outputSize;
	}
}

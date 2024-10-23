package ai;

public class Layer {
    private int inputSize;
    private int outputSize;
    private double[][] weights;
    private double[] biases;

    public Layer(int inputSize, int outputSize) {
        this.inputSize = inputSize;
        this.outputSize = outputSize;
        weights = new double[inputSize][outputSize];
        biases = new double[outputSize];
        initializeWeights();
    }

    private void initializeWeights() {
        // Initialize weights and biases with random values between -1 and 1
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                weights[i][j] = Math.random() * 2 - 1;
            }
        }
        for (int i = 0; i < outputSize; i++) {
            biases[i] = Math.random() * 2 - 1;
        }
    }

    public double[] forward(double[] inputs) {
        if (inputs.length != inputSize) {
            throw new IllegalArgumentException("Input size must be " + inputSize);
        }

        double[] outputs = new double[outputSize];

        // Compute XW + B
        for (int j = 0; j < outputSize; j++) {
            double sum = 0.0;
            for (int i = 0; i < inputSize; i++) {
                sum += inputs[i] * weights[i][j];
            }
            sum += biases[j];
            // Apply ReLU activation
            outputs[j] = relu(sum);
        }

        return outputs;
    }

    private double relu(double x) {
        return Math.max(0, x);
    }

    public double[][] getWeights() {
        return weights;
    }

    public void setWeights(double[][] weights) {
        this.weights = weights;
    }

    public double[] getBiases() {
        return biases;
    }

    public void setBiases(double[] biases) {
        this.biases = biases;
    }

    public void mutate(double mutationRate) {
        // Mutate weights
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                if (Math.random() < mutationRate) {
                    weights[i][j] += Math.random() * 0.2 - 0.1;
                }
            }
        }
        // Mutate biases
        for (int i = 0; i < outputSize; i++) {
            if (Math.random() < mutationRate) {
                biases[i] += Math.random() * 0.2 - 0.1;
            }
        }
    }

    public Layer crossover(Layer partner) {
        Layer child = new Layer(inputSize, outputSize);

        // Crossover weights
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                child.weights[i][j] = Math.random() < 0.5 ? this.weights[i][j] : partner.weights[i][j];
            }
        }
        // Crossover biases
        for (int i = 0; i < outputSize; i++) {
            child.biases[i] = Math.random() < 0.5 ? this.biases[i] : partner.biases[i];
        }

        return child;
    }
}

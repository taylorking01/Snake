package ai;

public class NeuralNetwork {
    private Layer[] layers;

    public NeuralNetwork(int[] layerSizes) {
        layers = new Layer[layerSizes.length - 1];

        for (int i = 0; i < layers.length; i++) {
            layers[i] = new Layer(layerSizes[i], layerSizes[i + 1]);
        }
    }

    public double[] predict(double[] inputs) {
        double[] outputs = inputs;

        for (Layer layer : layers) {
            outputs = layer.forward(outputs);
        }

        return outputs;
    }

    // Mutation and crossover methods can be added here
}

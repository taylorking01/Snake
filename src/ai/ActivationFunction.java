package ai;

import java.util.function.Function;

public class ActivationFunction {

    // ReLU activation function: sets any negative values in the array to 0
    public static Function<double[], double[]> relu = x -> {
        double[] result = new double[x.length];
        for (int i = 0; i < x.length; i++) {
            result[i] = Math.max(0, x[i]);
        }
        return result;
    };

    // Softmax activation function: normalizes the array to represent probabilities
    public static Function<double[], double[]> softmax = x -> {
        double max = x[0];
        for (int i = 1; i < x.length; i++) {
            if (x[i] > max) {
                max = x[i];
            }
        }

        double[] expValues = new double[x.length];
        double sum = 0.0;
        for (int i = 0; i < x.length; i++) {
            expValues[i] = Math.exp(x[i] - max); // Subtract max for numerical stability
            sum += expValues[i];
        }

        for (int i = 0; i < expValues.length; i++) {
            expValues[i] /= sum;
        }

        return expValues;
    };

    // Optional method to add other activation functions if needed
    public static Function<double[], double[]> getActivation(String name) {
        switch (name.toLowerCase()) {
            case "relu":
                return relu;
            case "softmax":
                return softmax;
            default:
                throw new IllegalArgumentException("Unknown activation function: " + name);
        }
    }
}

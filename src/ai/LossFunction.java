package ai;

import java.util.function.BiFunction;
import java.util.function.Function;

public class LossFunction {

	// Mean Squared Error (MSE) as a BiFunction for double arrays
    public static BiFunction<double[], double[], Double> meanSquaredError = (yTrue, yPred) -> {
        if (yTrue.length != yPred.length) {
            throw new IllegalArgumentException("yTrue and yPred must have the same length.");
        }
        double sum = 0.0;
        for (int i = 0; i < yTrue.length; i++) {
            double diff = yTrue[i] - yPred[i];
            sum += diff * diff;
        }
        return sum / yTrue.length;
    };

    // Cross Entropy loss function as a BiFunction
    public static BiFunction<double[], double[], Double> crossEntropyLoss = (yTrue, yPred) -> {
        double loss = 0.0;
        double epsilon = 1e-15; // Prevent log(0)
        for (int i = 0; i < yTrue.length; i++) {
            loss -= yTrue[i] * Math.log(yPred[i] + epsilon);
        }
        return loss;
    };

    /**
     * Retrieves a loss function dynamically by name.
     * 
     * @param name The name of the loss function ("mse" or "crossentropy").
     * @return The corresponding loss function.
     */
    public static Object getLoss(String name) {
        switch (name.toLowerCase()) {
            case "mse":
                return meanSquaredError;
            case "crossentropy":
                return crossEntropyLoss;
            default:
                throw new IllegalArgumentException("Unknown loss function: " + name);
        }
    }

    public static void main(String[] args) {
        // Example usage of MSE
        BiFunction<Integer, Integer, Double> mseFunction = (BiFunction<Integer, Integer, Double>) getLoss("mse");
        double mseLoss = mseFunction.apply(5, 3);
        System.out.println("Mean Squared Error: " + mseLoss); // Output: Mean Squared Error: 4.0

        // Example usage of Cross Entropy Loss
        BiFunction<double[], double[], Double> ceFunction = (BiFunction<double[], double[], Double>) getLoss("crossentropy");
        double[] actual = {0, 1, 0};
        double[] predicted = {0.1, 0.8, 0.1};
        double ceLoss = ceFunction.apply(actual, predicted);
        System.out.println("Cross Entropy Loss: " + ceLoss); // Output: Cross Entropy Loss: (calculated value)
    }
}

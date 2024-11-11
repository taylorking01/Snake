package ai;

public class LossFunctions {

	/**
	 * Computes loss using cross entropy method
	 */
	public static double crossEntropyLoss(double[] yTrue, double[] yPred) {
		double loss = 0.0;
		double epilson = 1e-15; //Prevents log(0)
		
		for (int i = 0; i < yTrue.length; i++) {
			loss -= yTrue[i] * Math.log(yPred[i] + epilson);
		}
		
		return loss;
	}
	
	public static void main(String[] args) {
		double[] actual = {0, 1, 0};
		double[] target = {5.5, 4.1, 3.5};
		
		double loss = crossEntropyLoss(actual, target);
		System.out.print(loss);
	}
}

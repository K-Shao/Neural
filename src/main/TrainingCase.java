package main;

/**
 * This represents a training case to be used by a neural network. 
 * @author kevinshao
 *
 */
public class TrainingCase {
	
	/**
	 * An array of doubles to be fed into to the network. It's length should match 
	 * the network's number of input neurons, ie net.dimensions[0]. 
	 */
	private double [] input;
	
	/**
	 * An array of doubles which represents what we want from the network. It's length 
	 * should match the network's number of output neurons, ie net.dimensions[net.dimensions.length - 1]. 
	 */
	private double [] expectedOutput;
	
	/**
	 * Simple constructor, all parameters provided. 
	 * @param input The input to the network. 
	 * @param output The desired output from the network. 
	 */
	public TrainingCase (double [] input, double [] output) {
		this.input = input;
		expectedOutput = output;
	}

	/**
	 * Getter method for input. 
	 * @return Input
	 */
	public double[] getInput() {
		return input;
	}

	/**
	 * Getter method for expected output. 
	 * @return Output, in the form of a double array. 
	 */
	public double[] getExpectedOutput() {
		return expectedOutput;
	}

	/**
	 * Getter method for the expected output, in the form of a single number (not the vectorized version). 
	 * @return A single integer. 
	 */
	public int getExpected () {
		for (int i = 0; i < expectedOutput.length; i++) {
			if (expectedOutput[i] == 1) {
				return i;
			}
		}
		return -1;
	}
}

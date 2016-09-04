package main;

public class TrainingCase {
	
	private double [] input, expectedOutput;
	
	public TrainingCase (double [] input, double [] output) {
		this.input = input;
		expectedOutput = output;
	}

	public double[] getInput() {
		return input;
	}

	public double[] getExpectedOutput() {
		return expectedOutput;
	}

	public int getExpected () {
		for (int i = 0; i < expectedOutput.length; i++) {
			if (expectedOutput[i] == 1) {
				return i;
			}
		}
		return -1;
	}
}

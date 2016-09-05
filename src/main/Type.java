package main;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Extend this class to train and test network using your own data. 
 * @author kevinshao
 *
 */
public abstract class Type {

	/**
	 * Write out the parameters of the network to some file. Used to save the state of a network. 
	 * @param net The network to save. 
	 * @throws FileNotFoundException
	 */
	public abstract void writeParams(Network net) throws FileNotFoundException;

	/**
	 * Code to test the network. It is run after every iteration of SGD. 
	 * @param net The network to test. 
	 */
	public abstract void test(Network net);

	/**
	 * Returns a list of training cases to feed into the network. 
	 * @return A list of rraining cases. 
	 */
	public abstract List<TrainingCase> getTrainingCases();

	/**
	 * Read biases from wherever they're saved. 
	 * @return A string representation of the biases. 
	 */
	public abstract String pullBiases();

	/**
	 * Read weights from wherever they're saved. 
	 * @return A string representation of the weights. 
	 */
	public abstract String pullWeights();

}

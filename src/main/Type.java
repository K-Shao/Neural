package main;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Extend this class to train and test network using your own data. 
 * @author kevinshao
 *
 */
public abstract class Type {

	public abstract void writeWeights(Network net) throws FileNotFoundException;

	public abstract void writeBiases(Network net) throws FileNotFoundException;

	public abstract void test(Network net);

	public abstract List<TrainingCase> getTrainingCases();

	public abstract String pullBiases();

	public abstract String pullWeights();

}

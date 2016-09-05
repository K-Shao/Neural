package main;

import java.io.FileNotFoundException;
import java.util.List;

public abstract class Case implements Loader, Tester, Writer {

	@Override
	public abstract void writeWeights(Network net) throws FileNotFoundException;

	@Override
	public abstract void writeBiases(Network net) throws FileNotFoundException;

	@Override
	public abstract void test(Network net);

	@Override
	public abstract List<TrainingCase> getTrainingCases();

	@Override
	public abstract String pullBiases();

	@Override
	public abstract String pullWeights();

}

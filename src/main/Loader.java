package main;

import java.util.List;

public interface Loader {
	
	public List<TrainingCase> getTrainingCases ();
	
	public String pullBiases ();
	public String pullWeights ();
}

package main;

import java.io.FileNotFoundException;

public interface Writer {
	
	public void writeWeights (Network net) throws FileNotFoundException;
	
	public void writeBiases (Network net) throws FileNotFoundException;

}

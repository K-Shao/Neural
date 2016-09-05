package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Wiki extends Type {
	
	public List<Integer> pullWiki () throws NumberFormatException, IOException {
		BufferedReader br = null;
		String line = null;
		List<Integer> result = new ArrayList<Integer>();
		try {
			br = new BufferedReader(new FileReader("./Resources/wiki_ascii.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}		
		int size = 100000000;
		int start = (int) (Math.random() * (size-1000));
		for (int i = 0; i < start; i++) {
			br.readLine();
		}
		for (int i = 0; i < 1000; i++) {
			line = br.readLine();
			result.add(Integer.parseInt(line));
		}
		
		return result;
	}

	public double[] getTrainingCase(List<Integer> ascii, int end) {
		double [][] temp = new double [15][94];
		double [] result = new double [94*15];
		for (int i = end-15; i < end; i++) {
			temp[i-end+15] = Calculator.vectorize(ascii.get(i), 94);
		}
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				result[i*94+j] = temp[i][j];
			}
		}
		return result;
	}

	@Override
	public List<TrainingCase> getTrainingCases() {
		List<TrainingCase> training = new ArrayList<TrainingCase> ();
		List<Integer> ascii = null;
		try {
			ascii = this.pullWiki();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < 20000; i++) {
			int index = (int) (Math.random()*(ascii.size()-30)) + 15;
			TrainingCase tc = new TrainingCase(this.getTrainingCase(ascii, index), Calculator.vectorize(ascii.get(index), 94));
			training.add(tc);
		}
		return training;
	}

	@Override
	public String pullBiases() {
		String line = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./Resources/wiki_biases.txt"));
			line = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}

	@Override
	public String pullWeights() {
		String line = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./Resources/wiki_weights.txt"));
			line = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}

	@Override
	public void writeParams(Network net) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("./Resources/wiki_biases.txt");
		out.println(Arrays.deepToString(net.getBiases()));
		out.close();
		out = new PrintWriter("./Resources/wiki_weights.txt");
		out.println(Arrays.deepToString(net.getWeights()));
		out.close();

	}
	

	@Override
	public void test(Network net) {
		StringBuilder string = new StringBuilder("In the early da");
		int index = 15;
		for (int i = 0; i < 10000; i++) {
			List<Integer> characters = stringToShiftedAscii(string.toString());
			double [] input = getTrainingCase(characters, index);
			int toAdd = net.run(input, true) + 32;
			string.append((char)(toAdd));
			index++;
		}
		System.out.println("TEST: " + string);
		
	}
	
	public String trainingCaseToString (TrainingCase tc) {
		double [] input = tc.getInput();
		StringBuilder str = new StringBuilder ();
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 94; j++) {
				if (input[i*94+j]==1) {
					str.append((char)(j+32));
					break;
				}
			}
		}
		double [] output = tc.getExpectedOutput();
		char c = 0;
		for (int i = 0; i < 94; i++) {
			if (output[i] == 1) {
				c = (char) (i+32);
			}
		}
		return "Input: " + str.toString() + " Output: " + c;
	}
	
	
	/**
	 * Shift -32
	 * @return
	 */
	public static List<Integer> stringToShiftedAscii (String input) {
		List<Integer> result = new ArrayList<Integer>();
		for (char c: input.toCharArray()) {
			result.add(((int)c) - 32);
		}
		return result;
	}

}

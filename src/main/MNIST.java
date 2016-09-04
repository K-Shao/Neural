package main;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class MNIST implements Loader, Writer, Tester {
	
	public byte [] imageData = null;
	public byte [] labelData = null;
	
	public MNIST () throws IOException {
		Path labelPath = Paths.get("./Resources/labelFile");
		labelData = Files.readAllBytes(labelPath);
		Path imagePath = Paths.get("./Resources/imageFile");
		imageData = Files.readAllBytes(imagePath);
	}

	@Deprecated
	public void convertToImages() throws IOException {
		int rowIncrement = 28;
		int imageIncrement = 28*28;
		int offset = 16;
		
		for (int i = 0; i < 60000; i++) {
			BufferedImage theImage = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);
			for (int k = 0; k < imageIncrement/rowIncrement; k++) {
				for (int j = 0; j < rowIncrement; j++) {
		            theImage.setRGB(j, k, imageData[offset + (i*imageIncrement) + (k*rowIncrement) + j]);
				}
			}
			
		    File outputfile = new File("./Resources/Images/" + i + "-" + labelData[i+8] + ".png");
		    ImageIO.write(theImage, "png", outputfile);
		    System.out.println(i);
		}
	}

	public List<double[]> pullImages(int start, int number) {
		List<double[]> toReturn = new ArrayList<double[]>();
		int increment = 28 * 28;
		for (int i = start; i < start+number; i++) {
			double [] toAdd = new double [increment];
			for (int j = 0; j < increment; j++) {
				toAdd [j] = ((double) (imageData[(i*increment) + 16 + j] & 0xFF)) / 256;
			}
			toReturn.add(toAdd);
		}
		return toReturn;
	}

	public List<Integer> pullLabels(int start, int number) {
		List<Integer> toReturn = new ArrayList<Integer>();
		for (int i = start; i < start+number; i++) {
			toReturn.add((int) labelData[i + 8]);
		}
		return toReturn;
	}

	public String pullBiases() {
		String line = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./Resources/mnist_biases.txt"));
			line = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}

	public String pullWeights() {
		String line = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("./Resources/mnist_weights.txt"));
			line = br.readLine();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}

	@Override
	public void writeWeights(Network net) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("./Resources/mnist_weights.txt");
		out.println(Arrays.deepToString(net.getBiases()));
		out.close();
	}
	@Override
	public void writeBiases(Network net) throws FileNotFoundException {
		PrintWriter out = new PrintWriter("./Resources/mnist_biases.txt");
		out.println(Arrays.deepToString(net.getWeights()));
		out.close();
	}

	@Override
	public List<TrainingCase> getTrainingCases() {
		List<TrainingCase> training = new ArrayList<TrainingCase> ();
		List<double []> trainingSet = this.pullImages(0, 10000);
		List<Integer> expected = this.pullLabels(0, 10000);
		for (int i = 0; i < trainingSet.size(); i++) {
			TrainingCase tc = new TrainingCase(
										trainingSet.get(i), 
										Calculator.vectorize(expected.get(i), 10));
			training.add(tc);
		}
		return training;
	}
	
	@Override
	public void test (Network net) {
		int total = 10000;
		int correct = 0;
		List<double []> testSet = this.pullImages(40000, total);
		List<Integer> testExpect = this.pullLabels(40000, total);
		for (int i = 0; i < total; i++) {
			int guess = net.run(testSet.get(i), false);
			if (testExpect.get(i) == guess) {
				correct++;
			} else {
				try {
					
					java.nio.file.Files.copy(Paths.get(("./Resources/Images/" + (i+40000) + "-" + testExpect.get(i) + ".png")),
											Paths.get("./Resources/MNIST/" + (i+40000) + "-" + testExpect.get(i) + "-" + guess + ".png"));

				} catch (IOException e) {
					e.printStackTrace();
				}

				
			}
		}
		
		System.out.println("Correct: " + correct + "/" + total);
	}
	

}

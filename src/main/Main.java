package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
	
	private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private static MNIST mnist;

	public static void main(String[] args) throws IOException {

		
		mnist = new MNIST();
		
		System.out.println("Welcome to my simple MNIST neural network!");
		loop();
		

	}
	
	public static void loop () {
		System.out.println("Options: \"train\" to train a new neural network and write out its parameters, " + 
								"\"test\" to test the saved neural network, and \"exit\" to exit the program. ");
		String input = null;
		try {
			input = br.readLine();
		} catch (IOException e) {
			System.out.println("There was an error reading input");
			System.exit(1);
		}
		input = input.trim();
		input = input.toLowerCase();
		if (input.equals("train")) {
			Network net = new Network (new int [] {784, 50, 10});
			List<TrainingCase> training = mnist.getTrainingCases();
			net.SGD(training, 10, mnist);
			try {
				mnist.writeParams(net);
			} catch (FileNotFoundException e) {
				System.out.println("There was an error saving the network. ");
			}
			System.out.println("Training complete.");
		} else if (input.equals("test")) {
			Network net;
			try {
				net = new Network (new int[] {784, 50, 10}, Calculator.stringTo2DArray(mnist.pullBiases()), Calculator.stringTo3DArray(mnist.pullWeights()));
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Failed to load parameters. Make sure that ./Resources/mnist_biases.txt and ./Resources/mnist_weights.txt are both present and untampered. If this is your first time using the network, please train it using the train command. ");
				loop();
				return;
			}
			mnist.test(net);
			System.out.println("Testing complete");
		} else if (input.equals("exit")) {
			System.out.println("Bye!");
			System.exit(0);
		} else {
			System.out.println("Sorry, your input doesn't seem to match one of the options. Please try again. ");
		}
		
		
		
		loop();
	}

	

	
	

}

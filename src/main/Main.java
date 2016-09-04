package main;

import java.io.IOException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {

		MNIST mnist = new MNIST ();
//		Network net = new Network (new int [] {784, 100, 30, 10}, 3, 10);
//		net.SGD(mnist.getTrainingCases(), 10, mnist);
		
		
//		Wiki wiki = new Wiki();
//		Network net = new Network (new int [] {1410, 150, 100, 94}, 2, 30, Calculator.stringTo2DArray(wiki.pullBiases()), Calculator.stringTo3DArray(wiki.pullWeights()));
//		wiki.test(net); 
//		for (int i = 0; i < 1000; i++) {
//			List<TrainingCase> training = wiki.getTrainingCases ();
//			net.SGD(training, 2, wiki);
//			wiki.writeBiases(net);
//			wiki.writeWeights(net);
//			System.out.println("Training set " + i + " complete.");
//		}


		
		
		

		
		Network net = new Network (new int [] {784, 50, 10}, Calculator.stringTo2DArray(mnist.pullBiases()), Calculator.stringTo3DArray(mnist.pullWeights()));
		mnist.test(net);
	}
	

	

	
	

}

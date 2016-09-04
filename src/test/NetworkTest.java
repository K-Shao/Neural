package test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import main.Network;
import main.TrainingCase;

public class NetworkTest extends TestCase{
	
	/**
	 * This is the network object I will be using for testing Networks. 
	 */
	private Network net;
	
	@Override
	protected void setUp () {
		int [] dimensions = new int [] {2, 3, 1}; 
		net = new Network (dimensions, 3, 10); //Initialize a network with 2 input neurons, 
												//3 hidden neurons, and 1 output neurons (3 layers), 
												//a learning rate of 3 and a batch size of 10. 
	}
	
	/**
	 * Tests to ensure that the network is initialized to the correct dimensions. 
	 */
	public void testNetworkArraySize () {
		double [][] biases = net.getBiases();
		double [][][] weights = net.getWeights();
		assertEquals (biases[0].length, 3); //There should be 3 biases in the first non-input layer (one for each neuron)
		assertEquals (biases[1].length, 1); //There should be only one bias for the second non-input layer (the output neuron)
		assertEquals (weights[0].length, 3); //Similarly, there should be 3 weight arrays in the first non-input layer
		assertEquals (weights[1].length, 1); //Same logic, only 1 weight array for the output neuron
		assertEquals (weights[0][1].length, 2); //Each weight in the first non-input layer (I'm only testing the second neuron) should have 2 weights (one for each neuron in the prior layer)
		assertEquals (weights[1][0].length, 3); //Same logic, the output neuron should have 3 weights, one for each hidden neuron. 
	}
	
	/**
	 * Testing the size of batches so that SGD uses batches of the correct sizes. 
	 */
	public void testGetBatches () {
		List<TrainingCase> training = new ArrayList<TrainingCase>();
		for (int i = 0 ; i < 500; i++) {
			training.add(new TrainingCase(null, null));
		}
		//We have 500 training cases. 
		assertEquals(net.getBatches(training).size(), 10); //Since the network was initialized with batch size 10, net.getBatches().size() should be of size 10. 
		assertEquals(net.getBatches(training).get(0).size(), 50); //Since we have 500 training cases and 10 batches, each batch should be of size 50. 
	}


}

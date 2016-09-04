package test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import main.Network;
import main.TrainingCase;

public class NetworkTest extends TestCase{
	
	Network net;
	
	@Override
	protected void setUp () {
		int [] dimensions = new int [] {2, 3, 1};
		net = new Network (dimensions, 3, 10);
	}
	
	public void testNetworkArraySize () {
		double [][] biases = net.getBiases();
		double [][][] weights = net.getWeights();
		assertEquals (biases[0].length, 3);
		assertEquals (biases[1].length, 1);
		assertEquals (weights[0].length, 3);
		assertEquals (weights[1].length, 1);
		assertEquals (weights[0][1].length, 2);
		assertEquals (weights[1][0].length, 3);
	}
	
	public void testGetBatches () {
		List<TrainingCase> training = new ArrayList<TrainingCase>();
		for (int i = 0 ; i < 500; i++) {
			training.add(new TrainingCase(null, null));
		}
		assertEquals(net.getBatches(training).size(), 10);
		assertEquals(net.getBatches(training).get(0).size(), 50);
	}
	
	@SuppressWarnings("unused")
	public void testGetZs () {
		double [][] z = net.getZ(new double [] {-1.0, 1.0});
		int count = 0;
		for (double [] da: z) {
			for (double d: da) {
				count++;
			}
		}
		int expected = 0;
		for (int i = 1; i < net.dimensions().length; i++) {
			expected += net.dimensions()[i];
		}
		
		assertEquals(count, expected);
	}

}

package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * The Network class represents a single neural network, 
 * which can be trained using SGD (stochastic gradient descent) 
 * and asked to run on test data. 
 * @author kevinshao
 *
 */
public class Network {
	
	/**
	 * The number of layers in the network, including the input and output layers. 
	 * For example, a network with one hidden layer would have numLayers = 3
	 */
	private int numLayers;
	
	/**
	 * An array representing the dimensions of the network. 
	 * The first element in dimensions would represent the number of inputs, 
	 * the last element would represent the number of outputs, 
	 * and any numbers in between would represent the number of hidden neurons in each layer. 
	 * Therefore, dimensions.length should equal numlayers. 
	 */
	private int [] dimensions;
	
	/**
	 * A 2D array representing all the biases in the array. 
	 * The bias of the ith neuron of the jth layer (assuming input layer is layer 0 and the first neuron is neuron 0) 
	 * would be accessed by biases[j-1][i]
	 */
	private double [][] biases;
	
	/**
	 * A 3D array representing all the weights in the array. 
	 * Access is similar to that of the biases, where the kth weight of the ith neuron of the jth layer, all 0 indexed, 
	 * would be accessed by weights[j-1][i][k];
	 */
	private double [][][] weights;
	
	/**
	 * A double representing how quickly the network learns. 
	 * Too big and you could overshoot the "optimal" parameters.
	 * Too small and it could take too long. 
	 */
	private double learningRate = 3d;
	
	/**
	 * The batch size used for stochastic gradient descent. 
	 */
	private int batchSize = 50;
	
	
	/**
	 * Initialize a network with given dimensions. 
	 * @param dimensions The dimensions of the network, where each element in the array gives the size of a layer. 
	 */
	public Network (int [] dimensions) {
		this (dimensions, 3, 50);
	}
	
	/**
	 * Initialize a network with the given dimensions and specifications. 
	 * @param dimensions The dimensions of the network, where each element in the array gives the size of a layer. 
	 * @param learningRate The learning rate for the network. 
	 * @param batchSize The batch size, used for SGD. 
	 */
	public Network (int [] dimensions, double learningRate, int batchSize) {
		this.learningRate = learningRate;
		this.batchSize = batchSize;
		Random rng = new Random ();
		this.numLayers = dimensions.length;
		this.dimensions = dimensions;
		this.biases = new double [dimensions.length - 1][];
		for (int i = 1; i < dimensions.length; i++) { //Since you don't need biases for the first (input) layer!
			this.biases [i-1] = new double [dimensions[i]];
			for (int j = 0; j < dimensions[i]; j++) {
				this.biases[i-1][j] = rng.nextGaussian(); //Initialize all the biases with normally distributed random numbers. 
			}
		}
		
		this.weights = new double [dimensions.length - 1][][]; //Since you don't need weights for the first (input) layer
		for (int i = 1; i < dimensions.length; i++) {
			this.weights[i - 1] = new double [dimensions[i]][dimensions[i-1]];
			for (int j = 0; j < dimensions[i]; j++) {
				for (int k = 0; k < dimensions[i-1]; k++) {
					this.weights [i-1][j][k] = rng.nextGaussian();  //Initialize all the weights with normally distributed random numbers. 
				}
			}
		}
	}
	
	/**
	 * Initialize a network with the given parameters and default specifications, usually from a pre-trained network. 
	 * @param dimensions The dimensions of the network. 
	 * @param biases The biases of the network, where biases[i][j] denotes the bias of the jth neuron of the i+1th layer
	 * (0 indexed)
	 * @param weights The weights of the network, where weights[i][j][k] denotes the weight of the connection 
	 * between the kth neuron of the ith layer and the jth neuron of the i+1th layer (all 0 indexed). 
	 */
	public Network (int [] dimensions, double [][] biases, double [][][] weights) {
		this (dimensions, 3, 50, biases, weights);
	}
	
	/**
	 * Initialize a network with the given parameters and specifications, usually from a pre-trained network. 
	 * @param dimensions The dimensions of the network. 
	 * @param biases The biases of the network, where biases[i][j] denotes the bias of the jth neuron of the i+1th layer
	 * (0 indexed)
	 * @param weights The weights of the network, where weights[i][j][k] denotes the weight of the connection 
	 * between the kth neuron of the ith layer and the jth neuron of the i+1th layer (all 0 indexed). 
	 * @param learningRate The learning rate of the network. 
	 * @param batchSize The batch size of the network, used for SGD. 
	 */
	public Network (int [] dimensions, double learningRate, int batchSize, double [][] biases, double [][][] weights) {
		this.learningRate = learningRate;
		this.batchSize = batchSize;
		this.numLayers = dimensions.length;
		this.dimensions = dimensions;
		this.biases = biases;
		this.weights = weights;
	}

	/**
	 * Runs the network. 
	 * @param input The input of the array. 
	 * @param probabilisticReturn Tells whether or not the network should return probabilistically. 
	 * If false, the network will return max(output). If true, the network will treat the output array
	 * as probabilities of each element occurring, so max(output) may not be the one returned, especially
	 * if the results are close. 
	 * @return The result of running the network. 
	 */
	public int run (double [] input, boolean probabilisticReturn) {
		double [] result = feedForward (input); //Run the network. 
		double currentMax = 0;
		if (!probabilisticReturn) {
			int maxIndex = -1;
			for (int i = 0; i < result.length; i++) {
				if (result[i] > currentMax) {
					currentMax = result[i];
					maxIndex = i;
				}
			}
			return maxIndex;
		} else {
			//This code is for probabilistic return. 
			double sum = 0; //The sum of all elements in the output. 
			for (double d: result) {
				sum+=d;
			}
			double rand = Math.random() * sum; //A random number from 0 to the sum. 
			for (int i = 0; i < result.length; i++) {
				rand -= result[i]; //Subtract each element until 
				if (rand <= 0) { //the random number becomes less than 0.
					return i; //The lucky element that it ends on gets returned. 
				} 
			}
			return 0;
	
		}

	}
	

	/**
	 * Run the neural network. 
	 * @param input The input to the network. 
	 * @return The output of the network. 
	 */
	public double [] feedForward (double [] input) {
		assert input.length == this.dimensions[0]; //Each input should correspond an input neuron. 
		for (int i = 1; i < dimensions.length; i++) { //For each layer, 
			//set the input array to the data sent to the next layer, which is
			//the current layer (input) pre-multiplied by the corresponding layer of weights, added to the vector 
			//of biases, then all elements taken the sigmoid of. 
			input = Calculator.sigmoid(Calculator.vectorAdd(Calculator.matrixMultiply(weights[i-1], input), biases[i-1]));
		}
		return input;
	}
	
	/**
	 * Perform stochastic gradient descent on the network. 
	 * @param training A list of training cases to use. 
	 * @param iterations How many iterations of training to perform. 
	 * @param type Optional argument to test after each iteration. 
	 */
	public void SGD (List<TrainingCase> training, int iterations, Type type) {
		System.out.println("Starting SGD");
		List<List<TrainingCase>> batches = getBatches(training);
		for (int i = 0; i < iterations; i++) {
			for (List<TrainingCase> batch: batches) {
				this.update(batch);
			}
			if (type != null) {
				type.test(this);
			}
		}
	}
	
	/**
	 * Given a small batch of training cases, update the parameters of the network. 
	 * @param batch A small batch of training cases. 
	 */
	public void update (List<TrainingCase> batch) {
		List<double [][]> errors = new ArrayList<double [][]>();  
		List<double [][]> activations = new ArrayList<double [][]> (); 
		for (TrainingCase training: batch) {
			errors.add(this.getError(training)); //Get the errors of each neurons for each training case.
			activations.add(Calculator.sigmoid(this.getZ(training.getInput())));//Get the activations of each neuron for each trainign case
		}

		for (int i = 0; i < dimensions.length - 1; i++) {
			//i+1th layer
			for (int j = 0; j < biases[i].length; j++) {
				//jth neuron bias
				double [] biasErrors = new double [errors.size()];
				for (int k = 0; k < errors.size(); k++) {
					biasErrors[k] = errors.get(k)[i][j];
				}
				double change = Calculator.average(biasErrors) * this.learningRate * -1; //Compute change as 
																						//the average of all the errors
																						//multiplied by -1*learningRate
				this.biases[i][j] += change;
			}
		}
		for (int i = 0; i < dimensions.length - 1; i++) {
			//i+1th layer
			for (int j = 0; j < weights[i].length; j++) {
				//jth neuron weights
				for (int k = 0; k < dimensions[i]; k++) {
					//connection to kth neuron of previous layer
					double [] weightDerivatives = new double [errors.size()];
					if (i==0) { //There are no activations, we should use the inputs. 
						for (int l = 0; l < errors.size(); l++) {
							weightDerivatives[l] = batch.get(l).getInput()[k] * errors.get(l)[i][j]; 
						}
					} else {
						for (int l = 0; l < errors.size(); l++) {
							weightDerivatives[l] = activations.get(l)[i-1][k] * errors.get(l)[i][j]; //For weights, the error is 
																									//multiplied by its incoming connection, 
																									//or how important it is. 
						}
					}
					double change = Calculator.average(weightDerivatives) * this.learningRate * -1; //Compute change same way as biases. 
					this.weights[i][j][k] += change;
				}
			}
		}
	}
	
	/**
	 * This is the core of backpropagation. Given a training case, returns a 2D double array 
	 * where each element is the error of a neuron. error[i][j] indicates the error of the jth neuron in the i-1th layer. 
	 * @param input The input to the network. 
	 * @return A 2D double array, with each element corresponding to the error of a neuron. 
	 */
	public double [][] getError (TrainingCase input) {
		int layers = dimensions.length - 1;
		double [][] error = new double [layers][]; 
		double [][] zs = this.getZ(input.getInput());
		double [][] activations = Calculator.sigmoid(zs);
		double [] sigmoidPrime = Calculator.sigmoidPrime(zs[layers - 1]);
		double [] firstError = Calculator.hadamard(this.getCostDerivative(activations[layers - 1], input.getExpectedOutput()), sigmoidPrime);
		error [layers - 1] = firstError;
		for (int i = layers - 2; i >= 0; i--) {
			double [] errorVector = error [i+1];
			double [][] weightMatrix = this.weights[i+1];
			error[i] = Calculator.hadamard(Calculator.matrixMultiply(Calculator.transpose(weightMatrix), errorVector), Calculator.sigmoidPrime(zs[i]));
		}
		return error;
	}
	

	/**
	 * Run the entire network, but instead of giving an output, returns all of the z values computed throughout. 
	 * @param input The input to the network. 
	 * @return All the z values of the network, which is the output of each neuron before the sigmoid function is applied. 
	 */
	public double [][] getZ (double [] input) {
		assert input.length == this.dimensions[0];
		double [][] zs = new double [this.dimensions.length-1][];
		for (int i = 1; i < dimensions.length; i++) {
			if (i==1) {
				zs[0] = Calculator.vectorAdd(Calculator.matrixMultiply(weights[i-1], input), biases[i-1]);
			} else {
				zs[i-1] = Calculator.vectorAdd(Calculator.matrixMultiply(weights[i-1], Calculator.sigmoid(zs[i-2])), biases[i-1]);
			}
		}
		return zs;
	}
	
	/**
	 * Returns small batches of training cases, each of size this.batchSize
	 * @param training All the training cases to take a subset of. 
	 * @return A small subset of the training cases. 
	 */
	public List<List<TrainingCase>> getBatches (List<TrainingCase> training) {
		int trainingSize = training.size();
		Collections.shuffle(training);
		List<List<TrainingCase>> batches = new ArrayList<List<TrainingCase>>();
		for (int k = 0; k < trainingSize; ) {
			int currentBatchSize = 0;
			List<TrainingCase> batch = new ArrayList<TrainingCase>();
			while (currentBatchSize < this.batchSize) {
				if (k == trainingSize) {
					break;
				}
				batch.add(training.get(k));
				currentBatchSize++;
				k++;
			}
			batches.add(batch);
		}
		return batches;
	}
	
	/**
	 * Simply computes the derivative of the cost function. Since the cost is y=(1/2)(e-o)^2, y'=o-e. 
	 * @param output The actual output of the network. 
	 * @param expected The expected output of the network. 
	 * @return y'(o,e)
	 */
	public double [] getCostDerivative (double [] output, double [] expected) {
		return Calculator.vectorSubtract(output, expected);
	}
	
	//Getters and setters
	
	public int numLayers () {
		return numLayers;
	}
	
	public int[] dimensions () {
		return dimensions;
	}
	
	public int layerSize (int layer) {
		return dimensions[layer-1];
	}
	
	public double [][] getBiases () {
		return biases;
	}
	
	public double [][][] getWeights () {
		return weights;
	}
}

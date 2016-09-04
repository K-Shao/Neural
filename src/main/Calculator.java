package main;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
	
	/**
	 * The sigmoid function, returns s(x) = 1 / (1 + e^(-x))
	 * @param input A double. 
	 * @return The sigmoid of input. 
	 */
	public static double sigmoid (double input) {
		return 				1d 
				/ //_______________________
					(1d + Math.exp(-input));
	}
	
	/**
	 * The sigmoid function extended to handle an array of inputs. 
	 * @param input An array of doubles. 
	 * @return An array of doubles, where the sigmoid function has been applied element-wise to the inputs. 
	 */
	public static double [] sigmoid (double [] input) {
		double [] result = new double [input.length];
		for (int i = 0; i < input.length; i++) {
			result[i] = sigmoid (input[i]);
		}
		return result;
	}
	
	/**
	 * The sigmoid function extended to handle a 2D array of inputs. 
	 * @param input A 2D array of doubles. 
	 * @return A 2D array of doubles, where the sigmoid function has been applied element-wise to the inputs. 
	 */
	public static double [][] sigmoid (double [][] input) {
		double [][] result = new double [input.length][];
		for (int i = 0; i < input.length; i++) {
			result[i] = sigmoid(input[i]);
		}
		return result;
	}
	
	/**
	 * The sigmoid prime function, or the derivative of the sigmoid function. 
	 * When differentiated, we can see that s'(x) = s(x) * (1 - s(x))
	 * @param input A double, x
	 * @return s'(x) = s(x) * (1 - s(x))
	 */
	public static double sigmoidPrime (double input) {
		return sigmoid(input) * (1 - sigmoid(input));
	}
	
	/**
	 * The sigmoid prime function, extended to handle an array of doubles. 
	 * @param input An array of doubles. 
	 * @return An array of doubles, where the sigmoid prime function has been applied to input element-wise. 
	 */
	public static double [] sigmoidPrime (double [] input) {
		double [] result = new double [input.length];
		for (int i = 0; i < input.length; i++) {
			result[i] = sigmoidPrime (input[i]);
		}
		return result;
	}
	
	/**
	 * The dot product of two arrays, or the sum of all products of corresponding elements. 
	 * @param arr1 An array of doubles. 
	 * @param arr2 Another array of doubles. 
	 * @return The dot product of the two arrays. 
	 */
	public static double dotProduct (double [] arr1, double [] arr2) {
		assert arr1.length == arr2.length;
		double sum = 0d;
		for (int i = 0; i < arr1.length; i++) {
			sum += arr1[i] * arr2[i];
		}
		return sum;
	}
	
	/**
	 * Gives the hadamard product of two arrays of the same size, where the result is an array where corresponding elements
	 * in the two inputs have been multiplied together, but not summed. 
	 * @param vector1 An array of doubles. 
	 * @param vector2 Another array of doubles. 
	 * @return The hadamard product of the two inputs. 
	 */
	public static double [] hadamard (double [] vector1, double [] vector2) {
		assert vector1.length == vector2.length;
		double [] result = new double [vector1.length];
		for (int i = 0; i < vector1.length; i++) {
			result[i] = vector1[i] * vector2[i];
		}
		return result;
	}
	
	/**
	 * Gives the matrix sum of two matrices, where corresponding elements are simply added together. 
	 * @param matrix1 A 2D array of doubles. 
	 * @param matrix2 Another 2D array of doubles. 
	 * @return The sum of the two matrices. 
	 */
	public static double [][] matrixAdd (double [][] matrix1, double [][] matrix2) {
		assert matrix1.length == matrix2.length;
		assert matrix1.length > 0;
		assert matrix1[0].length == matrix2[0].length;
		
		double [][] sum = new double [matrix1.length][matrix1[0].length];
		
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix1[i].length; j++) {
				sum [i][j] = matrix1[i][j] + matrix2[i][j];
			}
		}
		return sum;
	}
	
	/**
	 * Gives the vector sum of two vectors, where corresponding elements are added together. 
	 * @param vector1 An array of doubles. 
	 * @param vector2 Another array of doubles. 
	 * @return The sum of the two arrays. 
	 */
	public static double [] vectorAdd (double [] vector1, double [] vector2) {
		assert vector1.length == vector2.length;
		double [] sum = new double [vector1.length];
		for (int i = 0 ; i < vector1.length; i++) {
			sum [i] = vector1[i] + vector2[i];
		}
		return sum;
	}
	
	/**
	 * The vector difference of two vectors, where the difference of corresponding elements are taken. 
	 * @param vector1 An array of doubles. 
	 * @param vector2 Another array of doubles. 
	 * @return vector2 - vector1. 
	 */
	public static double [] vectorSubtract (double [] vector1, double [] vector2) {
		assert vector1.length == vector2.length;
		double [] result = new double [vector1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = vector1[i] - vector2[i];
		}
		return result;
	}
	
	/**
	 * Scales a vector by a constant, multiplying all elements in the vector by the given scalar. 
	 * @param vector An array of doubles. 
	 * @param scalar A double, the constant to multiply by. 
	 * @return The vector, scaled by the scalar. 
	 */
	public static double [] vectorScale (double [] vector, double scalar) {
		double [] result = new double [vector.length];
		for (int i = 0; i < vector.length; i++) {
			result[i] = vector[i] * scalar;
		}
		return result;
	}
	
	/**
	 * Returns the product of two matrices. The product, a, will have elements a[i][j] equal to the 
	 * dot product of the ith row of the first matrix and the jth column of the second matrix. 
	 * @param matrix1 A 2D double array. 
	 * @param matrix2 Another 2D double array. 
	 * @return The product of the two matrices. 
	 */
	public static double [][] matrixMultiply (double [][] matrix1, double [][] matrix2) {
		double [][] result = new double [matrix1.length][matrix2[0].length];
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix2[i].length; j++) {
				result[i][j] = dotProduct(matrix1[i], getColumn (matrix2, j));
			}
		}
		return result;
	}
	
	/**
	 * Returns the product of a matrix and a vector. Computed the same way as the product of two matrices. 
	 * @param matrix A 2D double array. 
	 * @param vector A double array. 
	 * @return The product of the matrix and the vector. 
	 */
	public static double[] matrixMultiply(double[][] matrix, double[] vector) {
		double [] result = new double [matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			result [i] = dotProduct(matrix[i], vector);
		}
		return result;
	}
	
	/**
	 * Returns the transpose of a matrix x, where x[i][j] becomes x[j][i]. 
	 * @param matrix The matrix to transpose. 
	 * @return The transpose. 
	 */
	public static double [][] transpose (double [][] matrix) {
        double [][] transpose = new double [matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
        	for (int j = 0; j < matrix[i].length; j++) {
        		transpose[j][i] = matrix[i][j];
        	}
        }
        return transpose;
	}
	
	/**
	 * Get the ith column of a matrix. 
	 * @param matrix A 2D double array. 
	 * @param column The desired column. 
	 * @return A double array. 
	 */
	public static double [] getColumn (double [][] matrix, int column) {
		double [] result = new double [matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			result [i] = matrix[i][column];
		}
		return result;
	}
	
	/**
	 * Tests whether or not two matrices are equal. 
	 * @param matrix1 A 2D double array. 
	 * @param matrix2 Another 2D double array. 
	 * @return True iff the two matrices are of equal dimensions AND all elements are the same. 
	 */
	public static boolean matrixEquals (double [][] matrix1, double [][] matrix2) {
		if (matrix1 == matrix2) {
			return true;
		}
		if (matrix1.length != matrix2.length) {
			return false;
		}
		if (matrix1.length == 0) {
			return true;
		}
		if (matrix1[0].length != matrix2[0].length) {
			return false;
		}
		try {
			for (int i = 0; i < matrix1.length; i++) {
				for (int j = 0; j < matrix1[i].length; j++) {
					if (matrix1[i][j] != matrix2[i][j]) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	
	/**
	 * Tests whether or not two vectors are equal. 
	 * @param vector1 A double array. 
	 * @param vector2 Another double array. 
	 * @return True iff the dimensions of the two arrays are the same AND all the elements are the same. 
	 */
	public static boolean vectorEquals (double [] vector1, double [] vector2) {
		if (vector1.length != vector2.length) {
			return false;
		}
		if (vector1 == vector2) {
			return true;
		}
		for (int i = 0; i < vector1.length; i++) {
			if (vector1[i] != vector2[i]) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Returns the arithmetic mean of all the elements of a vector. 
	 * @param vector A double array. 
	 * @return The arithmetic mean. 
	 */
	public static double average (double [] vector) {
		double sum = 0;
		for (double d: vector) {
			sum += d;
		}
		return sum/vector.length;
	}
	
	/**
	 * Returns a vector with zeros everywhere except for a single 1. 
	 * @param number The index where the 1 should be. 
	 * @param total The total number of elements in the desired vector. 
	 * @return A double array. 
	 */
	public static double [] vectorize (int number, int total) {
		return vectorize(number, total, 1);
	}

	/**
	 * Returns a vector with zeros everywhere except for a single entry. 
	 * @param number The index where the non-zero should be. 
	 * @param total The total number of elements in the desired vector. 
	 * @param scale The non-zero element. 
	 * @return A double array. 
	 */
	public static double [] vectorize (int number, int total, double scale) {
		double [] result = new double [total];
		for (int i = 0 ; i < total; i++) {
			result[i] = 0;
		}
		result[number] = scale;
		return result;
	}
	
	/**
	 * Parses a string, formatted by Arrays.deepToString() method, back into a 2D double array. 
	 * @param input A string. 
	 * @return A 2D double array. 
	 */
	public static double [][] stringTo2DArray (String input) {
		input = input.trim();
		List<String> primaryDivide = new ArrayList<String>();
		int currentLayer = 0;
		char [] characters = input.toCharArray();
		int startIndex = 0;
		int index = 0;
		for (char character: characters) {
			if (character == '[') {
				currentLayer++;
			} 
			if (character == ']') {
				currentLayer--;
			}
			if (currentLayer == 1 && character == ',') {
				String currentString = input.substring(startIndex, startIndex+index);
				if (primaryDivide.isEmpty()) {
					currentString = currentString.substring(1, currentString.length());
				}
				primaryDivide.add(currentString);
				startIndex += index + 1;
				index = 0;
			} else {
				index++;
			}
		}
		String lastString = input.substring(startIndex, input.length());
		primaryDivide.add(lastString.substring(0, lastString.length()-1));	

		double [][] array = new double [primaryDivide.size()][];
		for (int i = 0; i < primaryDivide.size(); i++) {
			String string = primaryDivide.get(i);
			string = string.trim();
			string = string.substring(1, string.length()-1);
			String [] arr = string.split(",");
			array[i] = new double [arr.length];
			for (int j = 0; j < arr.length; j++) {
				array[i][j] = Double.parseDouble(arr[j]);
			}
		}
		return array;
	}

	/**
	 * Parses a string, formatted by Arrays.deepToString() method, back into a 3D double array. 
	 * @param input A string. 
	 * @return A 3D double array. 
	 */
	public static double [][][] stringTo3DArray (String input) {
		input = input.trim();
		List<String> primaryDivide = new ArrayList<String>();
		int currentLayer = 0;
		char [] characters = input.toCharArray();
		int startIndex = 0;
		int index = 0;
		for (char character: characters) {
			if (character == '[') {
				currentLayer++;
			} 
			if (character == ']') {
				currentLayer--;
			}
			if (currentLayer == 1 && character == ',') {
				String currentString = input.substring(startIndex, startIndex+index);
				if (primaryDivide.isEmpty()) {
					currentString = currentString.substring(1, currentString.length());
				}
				primaryDivide.add(currentString);
				startIndex += index + 1;
				index = 0;
			} else {
				index++;
			}
		}
		String lastString = input.substring(startIndex, input.length());
		primaryDivide.add(lastString.substring(0, lastString.length()-1));
		double [][][] toReturn = new double [primaryDivide.size()][][];
		for (int i = 0; i < primaryDivide.size(); i++) {
			toReturn[i] = stringTo2DArray(primaryDivide.get(i));
		}
		return toReturn;
	}

	
}

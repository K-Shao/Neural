package main;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
	
	
	public static double sigmoid (double input) {
		return 1d / (1d + Math.exp(-input));
	}
	public static double [] sigmoid (double [] input) {
		double [] result = new double [input.length];
		for (int i = 0; i < input.length; i++) {
			result[i] = sigmoid (input[i]);
		}
		return result;
	}
	public static double [][] sigmoid (double [][] input) {
		double [][] result = new double [input.length][];
		for (int i = 0; i < input.length; i++) {
			result[i] = sigmoid(input[i]);
		}
		return result;
	}
	
	public static double sigmoidPrime (double input) {
		return sigmoid(input) * (1 - sigmoid(input));
	}
	public static double [] sigmoidPrime (double [] input) {
		double [] result = new double [input.length];
		for (int i = 0; i < input.length; i++) {
			result[i] = sigmoidPrime (input[i]);
		}
		return result;
	}
	
	public static double dotProduct (double [] arr1, double [] arr2) {
		assert arr1.length == arr2.length;
		double sum = 0d;
		for (int i = 0; i < arr1.length; i++) {
			sum += arr1[i] * arr2[i];
		}
		return sum;
	}
	
	public static double [] hadamard (double [] vector1, double [] vector2) {
		assert vector1.length == vector2.length;
		double [] result = new double [vector1.length];
		for (int i = 0; i < vector1.length; i++) {
			result[i] = vector1[i] * vector2[i];
		}
		return result;
	}
	
	
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
	public static double [] vectorAdd (double [] vector1, double [] vector2) {
		assert vector1.length == vector2.length;
		double [] sum = new double [vector1.length];
		for (int i = 0 ; i < vector1.length; i++) {
			sum [i] = vector1[i] + vector2[i];
		}
		return sum;
	}
	
	public static double [] vectorSubtract (double [] vector1, double [] vector2) {
		assert vector1.length == vector2.length;
		double [] result = new double [vector1.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = vector1[i] - vector2[i];
		}
		return result;
	}
	public static double [] vectorScale (double [] vector, double scalar) {
		double [] result = new double [vector.length];
		for (int i = 0; i < vector.length; i++) {
			result[i] = vector[i] * scalar;
		}
		return result;
	}
	
	public static double [][] matrixMultiply (double [][] matrix1, double [][] matrix2) {
		double [][] result = new double [matrix1.length][matrix2[0].length];
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix2[i].length; j++) {
				result[i][j] = dotProduct(matrix1[i], getColumn (matrix2, j));
			}
		}
		return result;
	}
	public static double[] matrixMultiply(double[][] matrix, double[] vector) {
		double [] result = new double [matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			result [i] = dotProduct(matrix[i], vector);
		}
		return result;
	}
	
	public static double [][] transpose (double [][] matrix) {
        double [][] transpose = new double [matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
        	for (int j = 0; j < matrix[i].length; j++) {
        		transpose[j][i] = matrix[i][j];
        	}
        }
        return transpose;
	}
	
	public static double [] getColumn (double [][] matrix, int column) {
		double [] result = new double [matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			result [i] = matrix[i][column];
		}
		return result;
	}
	
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
	
	public static double average (double [] vector) {
		double sum = 0;
		for (double d: vector) {
			sum += d;
		}
		return sum/vector.length;
	}
	public static double [] vectorize (int number, int total) {
		return vectorize(number, total, 1);
	}

	public static double [] vectorize (int number, int total, double scale) {
		double [] result = new double [total];
		for (int i = 0 ; i < total; i++) {
			result[i] = 0;
		}
		result[number] = scale;
		return result;
	}
	
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

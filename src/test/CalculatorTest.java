package test;

import java.util.Arrays;

import junit.framework.TestCase;
import main.Calculator;

public class CalculatorTest extends TestCase {
	
	public void testSigmoid () {
		assertTrue(Calculator.sigmoid(0d) == .5d );
		assertTrue(Calculator.sigmoid(-100d) < .1);
		assertTrue(Calculator.sigmoid(100d) > .9);
		assertTrue(Calculator.vectorEquals(Calculator.sigmoid(new double [] {0d}), new double [] {.5d}));
	}
	
	public void testSigmoidPrime () {
		assertTrue (Calculator.sigmoidPrime(0) == .25);
		assertTrue (Calculator.sigmoidPrime(100d) < .09);
	}

	public void testDotProduct () {
		assertEquals (Calculator.dotProduct(new double [] {2, -1, 7}, new double [] {3, 4, 0}), 2d);
		assertEquals (Calculator.dotProduct(new double[] {0, 0, 0}, new double [] {3, 4, 5}), 0d);
	}
	
	public void testEquality () {
		double [][] matrix1 = new double [][] {{1, 2}, {3, 4}};
		double [][] matrix2 = new double [][] {{1, 2}, {3, 4}};
		assertTrue (Calculator.matrixEquals(matrix1, matrix2));
		
		double [] vector1 = new double [] {1, 2, 3, 4, 5};
		double [] vector2 = new double [] {1, 2, 3, 4, 5};
		double [] vector3 = new double [] {1, 2, 3, 4, 4};
		
		assertTrue (Calculator.vectorEquals(vector1, vector2));
		assertTrue (!Calculator.vectorEquals(vector1, vector3));
	}
	
	public void testMatrixMath () {
		double [][] matrix1 = new double [][] {{1, 2}, {3, 4}};
		double [][] matrix2 = new double [][] {{0, 1}, {0, 0}};
		double [][] matrixAddResult = new double [][] {{1, 3}, {3, 4}};
		double [][] matrixMultiplyResult = new double [][] {{0, 1}, {0, 3}};
		assertTrue (Calculator.matrixEquals(Calculator.matrixAdd(matrix1, matrix2), matrixAddResult));
		assertTrue (Calculator.matrixEquals(Calculator.matrixMultiply(matrix1, matrix2), matrixMultiplyResult));
		
		matrix1 = new double [][] {{1, 2}, {3, 4}};
		matrix2 = new double [][] {{0, 1}, {0, 0}};
		matrixMultiplyResult = new double [][] {{3, 4}, {0, 0}};
		assertTrue (Calculator.matrixEquals(Calculator.matrixAdd(matrix2, matrix1), matrixAddResult));
		assertTrue (Calculator.matrixEquals(Calculator.matrixMultiply(matrix2, matrix1), matrixMultiplyResult));
		
	}
	
	public void testVectorMath () {
		double [] vector1 = new double [] {1, 2, 3};
		double [] vector2 = new double [] {2, 3, 4};
		double [] vectorResult = new double [] {3, 5, 7};
		double [] vectorResult2 = new double []{2, 6, 12};
		assertTrue (Calculator.vectorEquals(Calculator.vectorAdd(vector1, vector2), vectorResult));
		assertTrue (Calculator.vectorEquals(Calculator.hadamard(vector1, vector2), vectorResult2));

	}
	
	public void testTranspose () {
		double [][] matrix = new double [][] {{1, 2, 3}, {4, 5, 6}};
		double [][] transpose = Calculator.transpose(matrix);
		double [][] expected = new double [][] {{1, 4}, {2, 5}, {3, 6}};
		assertTrue(Calculator.matrixEquals(transpose, expected));
	}
	
	public void testAverage () {
		double [] vector = new double [] {1, 3, 5, 6};
		assertEquals(Calculator.average(vector), 3.75);
	}
	
	public void testVectorize () {
		double [] vector5 = new double [] {0, 0, 0, 0, 0, 1, 0, 0, 0, 0};
		double [] vector3 = new double [] {0, 0, 0, 1, 0, 0, 0, 0, 0, 0};
		assertTrue(Calculator.vectorEquals(vector5, Calculator.vectorize(5, 10)));
		assertTrue(Calculator.vectorEquals(vector3, Calculator.vectorize(3, 10)));
	}
	
	public void testParseArray () {
		String original = "[[2.3, 3.4, 4.5], [5.6, 6.7, 7.8]]";
		double [][] array = Calculator.stringTo2DArray(original);
		String transformed = Arrays.deepToString(array);
		assertTrue(original.equals(transformed));
		
		original = "[[[1.0, 1.0], [2.0, 2.0], [3.0, 3.0]], [[4.0, 4.0], [5.0, 5.0], [6.0, 6.0]]]";
		double [][][] array3 = Calculator.stringTo3DArray(original);
		transformed = Arrays.deepToString(array3);
		assertTrue(original.equals(transformed));
	}
}

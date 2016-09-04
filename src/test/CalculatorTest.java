package test;

import java.util.Arrays;

import junit.framework.TestCase;
import main.Calculator;

public class CalculatorTest extends TestCase {
	
	/**
	 * Test of the Calculator.sigmoid(x) method, both the single form and the array form. 
	 * The sigmoid function is defined as s(x) = 1 / (1 + e^(-x)). 
	 * Therefore, (1) s(0) should equal 1/2, as e^0 = 1. 
	 * (2) s(a very small x) should be close to 0. 
	 * (3) s(a very large x) should be close to 1. 
	 * (4) Calculator.sigmoid(x) should also be able to take a double array and return a double array, 
	 * applying the sigmoid function elementwise. 
	 */
	public void testSigmoid () {
		assertTrue(Calculator.sigmoid(0d) == .5d ); //Testing (1)
		assertTrue(Calculator.sigmoid(-100d) < .1); //Testing (2)
		assertTrue(Calculator.sigmoid(100d) > .9); //Testing (3)
		assertTrue(Calculator.vectorEquals(Calculator.sigmoid(new double [] {0d}), new double [] {.5d})); //Testing (4)
	}
	
	/**
	 * Test of the Calculator.sigmoidPrime(x) method, which has only the single form. 
	 * The sigmoid function s(x), when differentiated wrt x, yields s'(x) = s(x) * (1 - s(x)). 
	 * Therefore, (1) s'(.5) should be .5 * (1 - .5) = .25. 
	 * (2) s'(a very large x) should be close to zero. 
	 */
	public void testSigmoidPrime () {
		assertTrue (Calculator.sigmoidPrime(0) == .25); //Testing (1)
		assertTrue (Calculator.sigmoidPrime(100d) < .09); //Testing (2)
	}

	/**
	 * Test of the Calculator.dotProduct(x,y) method, which takes two double arrays of equal size 
	 * and returns their dot product. 
	 * Dot product is computed by multiplying corresponding elements and summing. 
	 * [2, -1, 7] * [3, 4, 0] = 6 + -4 + 0 = 2. 
	 * [0, 0, 0] * [x, y, z] = 0 + 0 + 0 = 0. 
	 */
	public void testDotProduct () {
		assertEquals (Calculator.dotProduct(new double [] {2, -1, 7}, new double [] {3, 4, 0}), 2d);
		assertEquals (Calculator.dotProduct(new double[] {0, 0, 0}, new double [] {3, 4, 5}), 0d);
	}
	
	/**
	 * Testing for Calculator.matrixEquals(x,y) and Calculator.vectorEquals(x,y). 
	 * Both should return true iff the dimensions are the same and ALL elements are the same. 
	 */
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
	
	/**
	 * Testing a few matrix math operations in the Calculator class, matrixAdd(x,y) and matrixMultiply(x,y).
	 * Matrix addition is done element-wise. For matrix multiplication, the element at i,j of the result is 
	 * the dot product of the ith row of the first matrix and the jth column of the second matrix. 
	 * Therefore, matrix addition is commutative but matrix multiplication is not. 
	 */
	public void testMatrixMath () {
		double [][] matrix1 = new double [][] {{1, 2}, {3, 4}};
		double [][] matrix2 = new double [][] {{0, 1}, {0, 0}};
		double [][] matrixAddResult = new double [][] {{1, 3}, {3, 4}};
		double [][] matrixMultiplyResult = new double [][] {{0, 1}, {0, 3}};
		/*
		 *[1, 2] * [0, 1] = [1*0 + 2*0, 1*1 + 2*0] = [0, 1]
		 *[3, 4]   [0, 0]   [3*0 + 4*0, 3*1 + 4*0]   [0, 0]
		 */
		
		assertTrue (Calculator.matrixEquals(Calculator.matrixAdd(matrix1, matrix2), matrixAddResult));
		assertTrue (Calculator.matrixEquals(Calculator.matrixMultiply(matrix1, matrix2), matrixMultiplyResult));
		
		matrix1 = new double [][] {{1, 2}, {3, 4}};
		matrix2 = new double [][] {{0, 1}, {0, 0}};
		matrixMultiplyResult = new double [][] {{3, 4}, {0, 0}}; //Showing that multiplication is not commutative. 
		
		assertTrue (Calculator.matrixEquals(Calculator.matrixAdd(matrix2, matrix1), matrixAddResult));
		assertTrue (Calculator.matrixEquals(Calculator.matrixMultiply(matrix2, matrix1), matrixMultiplyResult));
		
	}
	
	/**
	 * Testing a few vector math operations in the Calculator class, vectorAdd(x,y) and hadamard(x,y). 
	 * Vector addition is done element-wise. To obtain the hadamard product of two vectors, arithmetic multiplication 
	 * is carried out element-wise. 
	 */
	public void testVectorMath () {
		double [] vector1 = new double [] {1, 2, 3};
		double [] vector2 = new double [] {2, 3, 4};
		double [] vectorResult = new double [] {3, 5, 7};
		double [] vectorResult2 = new double []{2, 6, 12};
		assertTrue (Calculator.vectorEquals(Calculator.vectorAdd(vector1, vector2), vectorResult));
		assertTrue (Calculator.vectorEquals(Calculator.hadamard(vector1, vector2), vectorResult2));

	}
	
	/**
	 * Testing the Calculator.transpose(x) method which should take a matrix and return its transpose. 
	 * The transpose of a matrix is where a[i][j] becomes a[j][i] for all i and j. 
	 */
	public void testTranspose () {
		double [][] matrix = new double [][] {{1, 2, 3}, {4, 5, 6}};
		double [][] transpose = Calculator.transpose(matrix);
		double [][] expected = new double [][] {{1, 4}, {2, 5}, {3, 6}};
		assertTrue(Calculator.matrixEquals(transpose, expected));
	}
	
	/**
	 * Testing Calculator.average(x), which returns the arithmetic mean of all elements in x. 
	 */
	public void testAverage () {
		double [] vector = new double [] {1, 3, 5, 6};
		assertEquals(Calculator.average(vector), 3.75);
	}
	
	/**
	 * Testing Calculator.vectorize(x,y), which creates a vector with y elements, all of which are 0 
	 * except the xth element, which is a 1. (0 indexed) 
	 */
	public void testVectorize () {
		double [] vector5 = new double [] {0, 0, 0, 0, 0, 1, 0, 0, 0, 0}; //Has 10 elements, all are 0 except 5th
		double [] vector3 = new double [] {0, 0, 0, 1, 0, 0, 0, 0, 0, 0}; //Has 10 elements, all are 0 except 3rd
		assertTrue(Calculator.vectorEquals(vector5, Calculator.vectorize(5, 10)));
		assertTrue(Calculator.vectorEquals(vector3, Calculator.vectorize(3, 10)));
	}
	
	/**
	 * Testing the Calculator.stringTo2DArray(x) and Calculator.stringTo3DArray(x) methods. 
	 * Should reverse the effects of Arrays.deepToString(x). 
	 */
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

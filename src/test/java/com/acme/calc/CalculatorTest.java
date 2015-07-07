package com.acme.calc;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

	private Calculator calculator = new Calculator();

	@Test
	public void additionShouldReturnCorrectResult() {
<<<<<<< HEAD
		//given
		double first = 6.0;
		double second = 5.0;
		//when
		Double res = calculator.add(first, second);
		//then
		Assert.assertTrue(res == 11.0);
=======
		// given
		double firstNumber = 5.0;
		double secondNumber = 6.0;
		// when
		Double result = calculator.add(firstNumber, secondNumber);
		// then
		Assert.assertFalse(result.isNaN());
		Assert.assertTrue(result == 11);
>>>>>>> upstream/master
	}

	private double firstNumber;
	private double secondNumber;
	private Exception thrown;
	@Test
<<<<<<< HEAD
	public void subtractionShouldReturnCorrectResult() {
		//given
		double first = 6.0;
		double second = 5.0;
		//when
		Double res = calculator.subtract(first, second);
		//then
		Assert.assertTrue(res == 1.0);
	}
	
	@Test
	public void multiplicationShouldReturnCorrectResult() {
		//given
		double first = 6.0;
		double second = 5.0;
		//when
		Double res = calculator.multiply(first, second);
		//then
		Assert.assertTrue(res == 30.0);
	}
	
	@Test
	public void divisionShouldReturnCorrectResult() {
		//given
		double first = 10.0;
		double second = 5.0;
		//when
		Double res = calculator.divide(first, second);
		//then
		Assert.assertTrue(res == 2.0);
	}
	
	@Test
	public void divisionShouldReturnThrowsException() {
		//given
		double first = 10.0;
		double second = 0.0;
		//when
		boolean thrown = false;
	    try {
	    	calculator.divide(first, second);
	    } 
	    catch (DivisorCannotBeZeroException e) {
	    	thrown = true;
	    }
	    //then
	    Assert.assertTrue(thrown);
	    
	}

=======
	public void divisionShouldThrowExceptionWhenDivisorIsZero() {
		givenNumbersWithZero();
		catchException(() -> calculator.divide(firstNumber,secondNumber));
		assertException(DivisorCannotBeZeroException.class);
	}

	private void catchException(Runnable runnable) {
		try {
			runnable.run();
		} catch (Exception e) {
			thrown = e;
		}
	}

	private void assertException(Class<DivisorCannotBeZeroException> expectedExceptionClass) {
		Assert.assertNotNull(thrown);
		Assert.assertTrue(expectedExceptionClass.equals(thrown.getClass()));
	}
	private void assertThat(Exception e, Class<?> expectedClass) {
		Assert.assertTrue(e.getClass().equals(expectedClass));
	}
	private void thenCorrectExceptionIsThrons(Exception e) {
		Assert.assertTrue(e.getClass().equals(
				DivisorCannotBeZeroException.class));
	}
	// TODO division and multiplication test!
	private void givenNumbersWithZero() {
		firstNumber = 5.0;
		secondNumber = 0.0;
	}
>>>>>>> upstream/master

}

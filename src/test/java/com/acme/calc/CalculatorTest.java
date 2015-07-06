package com.acme.calc;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {

	private Calculator calculator = new Calculator();

	@Test
	public void additionShouldReturnCorrectResult() {
		//given
		double first = 6.0;
		double second = 5.0;
		//when
		Double res = calculator.add(first, second);
		//then
		Assert.assertTrue(res == 11.0);
	}

	@Test
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


}

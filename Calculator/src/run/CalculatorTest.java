package run;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import exception.IllegalExpressionException;
import exception.IllegalOperatorException;

public class CalculatorTest {

	@Test
	public void testCalculator() throws IllegalExpressionException, IllegalOperatorException {
		assertEquals(new BigDecimal("8"), Calculator.evaluate("2 + 6"));
	}
	@Test
	public void testCalculator2() throws IllegalExpressionException, IllegalOperatorException {
		assertEquals(new BigDecimal("8"), Calculator.evaluate("(2 + 6)"));
	}
	@Test
	public void testCalculator3() throws IllegalExpressionException, IllegalOperatorException {
		assertEquals(new BigDecimal("8"), Calculator.evaluate("2*4"));
	}
	@Test
	public void testCalculator4() throws IllegalExpressionException, IllegalOperatorException {
		assertEquals(new BigDecimal("8"), Calculator.evaluate("16/2"));
	}
	@Test
	public void testCalculator5() throws IllegalExpressionException, IllegalOperatorException {
		assertEquals(new BigDecimal("80"), Calculator.evaluate("(16-8)*(3+7)"));
	}

}

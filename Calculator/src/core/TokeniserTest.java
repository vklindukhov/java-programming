package core;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dict.Constant;
import dict.Operator;
import dict.Token;
import exception.IllegalExpressionException;

public class TokeniserTest {

	@Test
	public void testTokeniseNormal() throws IllegalExpressionException {
		TokenList testList = new TokenList();
		testList.add(new MathToken(Token.NUMBER, "2"));
		testList.add(new MathToken(Token.BIN_OPERATOR, Operator.TIMES));
		testList.add(new MathToken(Token.NUMBER, "7.5"));
		assertEquals(testList, Tokeniser.format("2*7.5"));
	}
	@Test
	public void testTokeniseComplex() throws IllegalExpressionException  {
		TokenList testList = new TokenList();
		testList.add(new MathToken(Token.NUMBER, "1000"));
		testList.add(new MathToken(Token.BIN_OPERATOR, Operator.PLUS));
		testList.add(new MathToken(Token.L_BRACKET));
		testList.add(new MathToken(Token.NUMBER, "7.505"));
		testList.add(new MathToken(Token.BIN_OPERATOR, Operator.MINUS));
		testList.add(new MathToken(Token.UN_OPERATOR, Operator.NATLOG));
		testList.add(new MathToken(Token.L_BRACKET));
		testList.add(new MathToken(Token.CONSTANT, Constant.PI));
		testList.add(new MathToken(Token.R_BRACKET));
		testList.add(new MathToken(Token.R_BRACKET));
		assertEquals(Tokeniser.formBracketTree(testList), Tokeniser.format("1000+(7.505-ln(pi))"));
	}
	@Test
	public void testTokeniseNothing() throws IllegalExpressionException {
		TokenList nothingList = new TokenList();
		assertEquals(nothingList, Tokeniser.format(null));
		assertEquals(nothingList, Tokeniser.format(""));
		assertEquals(nothingList, Tokeniser.format(" "));
		assertEquals(nothingList, Tokeniser.format("	"));
		assertEquals(nothingList, Tokeniser.format("\n"));
	}
	@Test(expected=IllegalExpressionException.class)
	public void testTokeniseBadSymbol() throws IllegalExpressionException {
		assertEquals(null, Tokeniser.format("80.240@7"));
	}
	@Test(expected=IllegalExpressionException.class)
	public void testTokeniseBadString() throws IllegalExpressionException {
		assertEquals(null, Tokeniser.format("2.5*3.4 + abcd"));
	}
	@Test(expected=IllegalExpressionException.class)
	public void testTokeniseBadBrackets() throws IllegalExpressionException {
		assertEquals(null, Tokeniser.format("(2.5))*((3.4)"));
	}
	@Test(expected=IllegalExpressionException.class)
	public void testTokeniseBadBrackets2() throws IllegalExpressionException {
		assertEquals(null, Tokeniser.format("(2.5)*((3.4)"));
	}
}

package core;

import static org.junit.Assert.*;

import org.junit.Test;

import exception.IllegalExpressionException;

public class MathParserTest {

	@Test
	public void formTreeTest() throws IllegalExpressionException {
		TokenList testEx = Tokeniser.tokenise("1000 + (7.505 - ln(pi))");
		TokenList testTree = Tokeniser.formBracketTree(testEx);
		System.out.println(testTree);
	}
	@Test
	public void formTreeTest2() throws IllegalExpressionException {
		TokenList testEx = Tokeniser.tokenise("1000 + (7.505 - ln(pi) + sin(32)*cos(32))");
		TokenList testTree = Tokeniser.formBracketTree(testEx);
		System.out.println(testTree);
	}
	@Test
	public void formTreeTest3() throws IllegalExpressionException {
		TokenList testEx = Tokeniser.tokenise("1000 + 7.505 - ln(pi)");
		TokenList testTree = Tokeniser.formBracketTree(testEx);
		System.out.println(testTree);
	}
	
	@Test(expected = IllegalExpressionException.class)
	public void formTreeTestBad() throws IllegalExpressionException {
		TokenList testEx = Tokeniser.tokenise("1000 + (7.505 - ln((pi))");
		TokenList testTree = Tokeniser.formBracketTree(testEx);
		System.out.println(testTree);
	}

}

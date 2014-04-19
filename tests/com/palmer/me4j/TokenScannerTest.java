package com.palmer.me4j;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Token;
import com.palmer.me4j.token.builtin.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class TokenScannerTest
{
    @Test
    public void testTokenize ()
    {
        String input = "  { [(1.0 +-1.0*0.0/1.0 -0.0 )  ]   } ";
//        String input = "+ - * / ( ) [ ] { } 1.0 -1.0 0.0";
        Token[] tokenArray = new Token[]
                {
                        CurlyBraceGroupingToken.INSTANCE.getLeftOperator (),
                        SquareBraceGroupingToken.INSTANCE.getLeftOperator (),
                        ParensGroupingToken.INSTANCE.getLeftOperator (),
                        new OperandToken (1.0),
                        AdditionOperatorToken.INSTANCE,
                        new OperandToken (-1.0),
                        MultiplicationOperatorToken.INSTANCE,
                        new OperandToken (0.0),
                        DivisionOperatorToken.INSTANCE,
                        new OperandToken (1.0),
                        SubtractionOperatorToken.INSTANCE,
                        new OperandToken (0.0),
                        ParensGroupingToken.INSTANCE.getRightOperator (),
                        SquareBraceGroupingToken.INSTANCE.getRightOperator (),
                        CurlyBraceGroupingToken.INSTANCE.getRightOperator (),
                };
        List<Token> tokens = Arrays.asList (tokenArray);

        assertEquals (tokens, TokenScanner.tokenize (input));
    }

    @Test
    public void testInvalidOperatorThrowsException ()
    {
        String input = "+ - * / ( ) 1.0 & -1.0 0.0";
        String errorMessage = "Invalid operator symbol did not throw exception.";

        testStringThrowsException (input, UnsupportedOperationException.class, errorMessage);
    }

    @Test
    public void testUnmatchedLeftGroupingSymbolThrowsException ()
    {
        String input = "+ - * / ( 1.0 -1.0 0.0";
        String errorMessage = "Unmatched grouping symbol did not throw exception.";

        testStringThrowsException (input, UnsupportedOperationException.class, errorMessage);
    }

    @Test
    public void testUnmatchedRightGroupingSymbolThrowsException ()
    {
        String input = "+ - * / 1.0 -1.0 0.0 )";
        String errorMessage = "Unmatched grouping symbol did not throw exception.";

        testStringThrowsException (input, UnsupportedOperationException.class, errorMessage);
    }

    @Test
    public void testUnbalancedGroupingSymbolsThrowsException ()
    {
        String input = "+ - * [ / ( 1.0 -1.0 0.0 )";
        String errorMessage = "Unmatched grouping symbol did not throw exception.";

        testStringThrowsException (input, UnsupportedOperationException.class, errorMessage);
    }

    @Test
    public void testGroupingTokensOutOfOrderThrowsException ()
    {
        String input = "( 1.0 + [ 2 - 3.0 )]";
        String errorMessage = "Out of order grouping tokens did not throw exception.";

        testStringThrowsException (input, UnsupportedOperationException.class, errorMessage);
    }

    private void testStringThrowsException (String input, Class<? extends Exception> expectedExceptionClass, String errorMessage)
    {
        Exception expected = null;

        try
        {
            TokenScanner.tokenize (input);
            fail (errorMessage);
        }
        catch (Exception e)
        {
            expected = e;
        }

        assertNotNull (expected);
        assertTrue (expectedExceptionClass.isInstance (expected));
    }
}

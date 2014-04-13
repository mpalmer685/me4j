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
        String input = "+ - * / ( ) [ ] { } 1.0 -1.0 0.0";
        Token[] tokenArray = new Token[]
                {
                        AdditionOperatorToken.INSTANCE,
                        SubtractionOperatorToken.INSTANCE,
                        MultiplicationOperatorToken.INSTANCE,
                        DivisionOperatorToken.INSTANCE,
                        ParensGroupingToken.INSTANCE.getLeftOperator (),
                        ParensGroupingToken.INSTANCE.getRightOperator (),
                        SquareBraceGroupingToken.INSTANCE.getLeftOperator (),
                        SquareBraceGroupingToken.INSTANCE.getRightOperator (),
                        CurlyBraceGroupingToken.INSTANCE.getLeftOperator (),
                        CurlyBraceGroupingToken.INSTANCE.getRightOperator (),
                        new OperandToken (1.0),
                        new OperandToken (-1.0),
                        new OperandToken (0.0)
                };
        List<Token> tokens = Arrays.asList (tokenArray);

        assertEquals (tokens, TokenScanner.tokenize (input));
    }

    @Test
    public void testInvalidOperatorThrowsException ()
    {
        String input = "+ - * / ( ) 1.0 & -1.0 0.0";

        Exception expected = null;

        try
        {
            TokenScanner.tokenize (input);
            fail ("Invalid operator symbol did not throw exception.");
        }
        catch (Exception e)
        {
            expected = e;
        }

        assertNotNull (expected);
        assertTrue (expected instanceof UnsupportedOperationException);
    }

    @Test
    public void testUnmatchedGroupingSymbolThrowsException ()
    {
        String input = "+ - * / ( 1.0 -1.0 0.0";

        Exception expected = null;

        try
        {
            TokenScanner.tokenize (input);
            fail ("Unmatched grouping symbol did not throw exception.");
        }
        catch (Exception e)
        {
            expected = e;
        }

        assertNotNull (expected);
        assertTrue (expected instanceof UnsupportedOperationException);
    }
}

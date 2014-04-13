package com.palmer.me4j;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Token;
import com.palmer.me4j.token.builtin.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class TokenScannerTest
{
    @Test
    public void testTokenize ()
    {
        String input = "+ - * / ( ) 1.0 -1.0 0.0";
        Token[] tokenArray = new Token[]
                {
                        AdditionOperatorToken.INSTANCE,
                        SubtractionOperatorToken.INSTANCE,
                        MultiplicationOperatorToken.INSTANCE,
                        DivisionOperatorToken.INSTANCE,
                        ParensGroupingToken.INSTANCE.getLeftOperator (),
                        ParensGroupingToken.INSTANCE.getRightOperator (),
                        new OperandToken (1.0),
                        new OperandToken (-1.0),
                        new OperandToken (0.0)
                };
        List<Token> tokens = Arrays.asList (tokenArray);

        assertEquals (tokens, TokenScanner.tokenize (input));
    }
}

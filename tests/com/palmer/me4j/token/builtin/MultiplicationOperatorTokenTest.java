package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Precedence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class MultiplicationOperatorTokenTest
{
    @Test
    public void testMultiplicationOperation ()
    {
        OperandToken left = new OperandToken (2.0);
        OperandToken right = new OperandToken (5.0);
        OperandToken expected = new OperandToken (10.0);

        assertEquals (expected, MultiplicationOperatorToken.INSTANCE.performOperation (left, right));
    }

    @Test
    public void testMultiplicationPrecedence ()
    {
        assertEquals (Precedence.MULTIPLICATION_DIVISION, MultiplicationOperatorToken.INSTANCE.getPrecedence ());
    }

    @Test
    public void testMultiplicationSymbol ()
    {
        assertEquals ("*", MultiplicationOperatorToken.SYMBOL);
    }
}

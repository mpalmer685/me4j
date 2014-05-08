package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Precedence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NegativeUnaryOperatorTokenTest
{
    @Test
    public void testNegationOperation ()
    {
        OperandToken operand = new OperandToken (2.0);
        OperandToken expected = new OperandToken (-2.0);

        assertEquals (expected, NegativeUnaryOperatorToken.INSTANCE.performOperation (operand));
    }

    @Test
    public void testNegationPrecedence ()
    {
        assertEquals (Precedence.UNARY, NegativeUnaryOperatorToken.INSTANCE.getPrecedence ());
    }

    @Test
    public void testNegationSymbol ()
    {
        assertEquals ("-", NegativeUnaryOperatorToken.SYMBOL);
    }
}
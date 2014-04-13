package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Precedence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class SubtractionOperatorTokenTest
{
    @Test
    public void testSubtractionOperation ()
    {
        OperandToken left = new OperandToken (5.0);
        OperandToken right = new OperandToken (3.0);
        OperandToken expected = new OperandToken (2.0);

        assertEquals (expected, SubtractionOperatorToken.INSTANCE.performOperation (left, right));
    }

    @Test
    public void testSubtractionPrecedence ()
    {
        assertEquals (Precedence.ADDITION_SUBTRACTION, SubtractionOperatorToken.INSTANCE.getPrecedence ());
    }

    @Test
    public void testSubtractionSymbol ()
    {
        assertEquals ("-", SubtractionOperatorToken.SYMBOL);
    }
}

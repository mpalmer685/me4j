package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Precedence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class DivisionOperatorTokenTest
{
    @Test
    public void testDivisionOperation ()
    {
        OperandToken left = new OperandToken (10.0);
        OperandToken right = new OperandToken (2.0);
        OperandToken expected = new OperandToken (5.0);

        assertEquals (expected, DivisionOperatorToken.INSTANCE.performOperation (left, right));
    }

    @Test
    public void testDivisionPrecedence ()
    {
        assertEquals (Precedence.MULTIPLICATION_DIVISION, DivisionOperatorToken.INSTANCE.getPrecedence ());
    }

    @Test
    public void testDivisionSymbol ()
    {
        assertEquals ("/", DivisionOperatorToken.SYMBOL);
    }
}

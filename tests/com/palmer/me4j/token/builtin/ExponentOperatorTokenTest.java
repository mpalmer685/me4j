package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Precedence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mike Palmer on 4/15/14.
 */
public class ExponentOperatorTokenTest
{
    @Test
    public void testExponentOperation ()
    {
        OperandToken left = new OperandToken (3.0);
        OperandToken right = new OperandToken (2.0);
        OperandToken expected = new OperandToken (9.0);

        assertEquals (expected, ExponentOperatorToken.INSTANCE.performOperation (left, right));
    }

    @Test
    public void testExponentPrecedence ()
    {
        assertEquals (Precedence.EXPONENTIATION, ExponentOperatorToken.INSTANCE.getPrecedence ());
    }

    @Test
    public void textExponentSymbol ()
    {
        assertEquals ("^", ExponentOperatorToken.SYMBOL);
    }
}

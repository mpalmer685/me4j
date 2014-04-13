package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Precedence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class AdditionOperatorTokenTest
{
    @Test
    public void testAdditionOperation ()
    {
        OperandToken left = new OperandToken (2.0);
        OperandToken right = new OperandToken (3.0);
        OperandToken expected = new OperandToken (5.0);

        assertEquals (expected, AdditionOperatorToken.INSTANCE.performOperation (left, right));
    }

    @Test
    public void testAdditionPrecedence ()
    {
        assertEquals (Precedence.ADDITION_SUBTRACTION, AdditionOperatorToken.INSTANCE.getPrecedence ());
    }

    @Test
    public void testAdditionSymbol ()
    {
        assertEquals ("+", AdditionOperatorToken.SYMBOL);
    }
}

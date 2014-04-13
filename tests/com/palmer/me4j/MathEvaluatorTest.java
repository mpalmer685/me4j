package com.palmer.me4j;

import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

/**
 * Created by Mike Palmer on 4/12/14.
 */
public class MathEvaluatorTest
{
    @Test
    public void testSimpleArithmetic ()
    {
        String expression = "6 + 4 / 2";
        double expected = 8.0;

        assertEquals (0, Double.compare (expected, MathEvaluator.evaluate (expression)));
    }

    @Test
    public void testGroupingArithmetic ()
    {
        String expression = "( 6 + 4 ) / 2";
        double expected = 5.0;

        assertEquals (0, Double.compare (expected, MathEvaluator.evaluate (expression)));
    }

    @Test
    public void testAllOperations ()
    {
        String expression = "20 * 5.0 / ( ( 10.5 - 6.5 ) + 4.0 )";
        double expected = 12.5;

        assertEquals (0, Double.compare (expected, MathEvaluator.evaluate (expression)));
    }

    @Test
    public void testInvalidFormattingThrowsException ()
    {
        String expression = "(6 + 4) / 2";
        Exception expected = null;

        try
        {
            MathEvaluator.evaluate (expression);
            fail ("Invalid expression formatting did not throw exception.");
        }
        catch (Exception e)
        {
            expected = e;
        }

        assertNotNull (expected);
        assertTrue (expected instanceof IllegalArgumentException);
    }

    @Test
    public void testInvalidExpressionThrowsException ()
    {
        String expression = "6 +";
        Exception expected = null;

        try
        {
            MathEvaluator.evaluate (expression);
            fail ("Invalid expression did not throw exception.");
        }
        catch (Exception e)
        {
            expected = e;
        }

        assertNotNull (expected);
        assertTrue (expected instanceof EmptyStackException);
    }
}

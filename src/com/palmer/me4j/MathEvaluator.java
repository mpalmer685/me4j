package com.palmer.me4j;

/**
 * Created by Mike Palmer on 4/12/14.
 */
public class MathEvaluator
{
    public static double evaluate (String expression)
    {
        return new Expression (expression).evaluate ();
    }

    private MathEvaluator ()
    {
        throw new UnsupportedOperationException ();
    }
}

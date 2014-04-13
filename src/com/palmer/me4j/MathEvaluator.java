package com.palmer.me4j;

/**
 * Utility class for quickly performing simple evaluations of expression strings.
 *
 * Created by Mike Palmer on 4/12/14.
 */
public class MathEvaluator
{
    private MathEvaluator ()
    {
        throw new UnsupportedOperationException ();
    }

    /**
     * Evaluates a single expression string.
     * @param expression the expression string to evaluate
     * @return the numerical result of evaluating the expression
     */
    public static double evaluate (String expression)
    {
        return new Expression (expression).evaluate ();
    }
}

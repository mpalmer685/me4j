package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.*;

/**
 * Created by Mike Palmer on 4/15/14.
 */
public class ExponentOperatorToken extends BinaryOperatorToken
{
    private static final BinaryOperator exponentOperator = new BinaryOperator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return Math.pow (leftOperand, rightOperand);
        }
    };

    public static final String                SYMBOL   = "^";
    public static final ExponentOperatorToken INSTANCE = new ExponentOperatorToken ();

    private ExponentOperatorToken ()
    {
        super (SYMBOL, Precedence.EXPONENTIATION, Associativity.RIGHT_ASSOCIATIVE, exponentOperator);
    }
}

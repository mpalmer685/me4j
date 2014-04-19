package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.*;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class SubtractionOperatorToken extends BinaryOperatorToken
{
    private static final BinaryOperator subtractOperator = new BinaryOperator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return leftOperand - rightOperand;
        }
    };

    public static final String                   SYMBOL   = "-";
    public static final SubtractionOperatorToken INSTANCE = new SubtractionOperatorToken ();

    private SubtractionOperatorToken ()
    {
        super (SYMBOL, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, subtractOperator);
    }
}

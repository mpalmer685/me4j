package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.*;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class DivisionOperatorToken extends BinaryOperatorToken
{
    private static final BinaryOperator divideOperator = new BinaryOperator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return leftOperand / rightOperand;
        }
    };

    public static final String                SYMBOL   = "/";
    public static final DivisionOperatorToken INSTANCE = new DivisionOperatorToken ();

    private DivisionOperatorToken ()
    {
        super (SYMBOL, Precedence.MULTIPLICATION_DIVISION, Associativity.LEFT_ASSOCIATIVE, divideOperator);
    }
}

package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.Associativity;
import com.palmer.me4j.token.Operator;
import com.palmer.me4j.token.OperatorToken;
import com.palmer.me4j.token.Precedence;

/**
 * Created by Mike Palmer on 4/15/14.
 */
public class ExponentOperatorToken extends OperatorToken
{
    private static final Operator exponentOperator = new Operator ()
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

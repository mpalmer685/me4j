package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.Operator;
import com.palmer.me4j.token.OperatorToken;
import com.palmer.me4j.token.Precedence;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class SubtractionOperatorToken extends OperatorToken
{
    private static final Operator subtractOperator = new Operator ()
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
        super (SYMBOL, Precedence.ADDITION_SUBTRACTION, subtractOperator);
    }
}

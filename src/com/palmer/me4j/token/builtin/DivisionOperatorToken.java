package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.Operator;
import com.palmer.me4j.token.OperatorToken;
import com.palmer.me4j.token.Precedence;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class DivisionOperatorToken extends OperatorToken
{
    private static final Operator divideOperator = new Operator ()
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
        super (SYMBOL, Precedence.MULTIPLICATION_DIVISION, divideOperator);
    }
}

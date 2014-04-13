package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.Operator;
import com.palmer.me4j.token.OperatorToken;
import com.palmer.me4j.token.Precedence;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class AdditionOperatorToken extends OperatorToken
{
    private static final Operator addOperator = new Operator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return leftOperand + rightOperand;
        }
    };

    public static final String                SYMBOL   = "+";
    public static final AdditionOperatorToken INSTANCE = new AdditionOperatorToken ();

    private AdditionOperatorToken ()
    {
        super (SYMBOL, Precedence.ADDITION_SUBTRACTION, addOperator);
    }
}

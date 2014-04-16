package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.Associativity;
import com.palmer.me4j.token.Operator;
import com.palmer.me4j.token.OperatorToken;
import com.palmer.me4j.token.Precedence;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class MultiplicationOperatorToken extends OperatorToken
{
    private static final Operator multiplyOperator = new Operator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return leftOperand * rightOperand;
        }
    };

    public static final String                      SYMBOL   = "*";
    public static final MultiplicationOperatorToken INSTANCE = new MultiplicationOperatorToken ();

    private MultiplicationOperatorToken ()
    {
        super (SYMBOL, Precedence.MULTIPLICATION_DIVISION, Associativity.LEFT_ASSOCIATIVE, multiplyOperator);
    }
}

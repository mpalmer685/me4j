package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.Associativity;
import com.palmer.me4j.token.Precedence;
import com.palmer.me4j.token.UnaryOperator;
import com.palmer.me4j.token.UnaryOperatorToken;

/**
 * Created by Mike Palmer on 4/18/14.
 */
public class NegativeUnaryOperatorToken extends UnaryOperatorToken
{
    private static final UnaryOperator negateOperator = new UnaryOperator ()
    {
        @Override
        public double operate (double operand)
        {
            return -1d * operand;
        }
    };

    public static final String                     SYMBOL   = "-";
    public static final NegativeUnaryOperatorToken INSTANCE = new NegativeUnaryOperatorToken ();

    private NegativeUnaryOperatorToken ()
    {
        super (SYMBOL, Precedence.UNARY, Associativity.RIGHT_ASSOCIATIVE, negateOperator);
    }
}

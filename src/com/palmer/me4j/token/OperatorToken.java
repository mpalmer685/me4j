package com.palmer.me4j.token;

/**
 * Created by Mike Palmer on 4/18/14.
 */
public abstract class OperatorToken extends Token
{
    private final String m_symbol;

    public OperatorToken (String symbol)
    {
        if (symbol == null || symbol.isEmpty ())
        {
            throw new IllegalArgumentException ("Symbol must be set.");
        }

        m_symbol = symbol;
    }

    @Override
    public TokenType getTokenType ()
    {
        return TokenType.OPERATOR;
    }

    public abstract int getPrecedence ();

    public abstract Associativity getAssociativity ();

    @Override
    public String toString ()
    {
        return m_symbol;
    }
}

package com.palmer.me4j.token;

/**
 * Created by Mike Palmer on 3/27/14.
 */
public class OperandToken extends Token
{
    private final double m_value;

    public OperandToken (double value)
    {
        m_value = value;
    }

    @Override
    public TokenType getTokenType ()
    {
        return TokenType.OPERAND;
    }

    public double getValue ()
    {
        return m_value;
    }

    @Override
    public String toString ()
    {
        return Double.toString (m_value);
    }

    @Override
    public boolean equals (Object obj)
    {
        if (obj == this)
        {
            return true;
        }

        if (obj instanceof OperandToken)
        {
            OperandToken token = (OperandToken) obj;
            return Double.compare (m_value, token.m_value) == 0;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode ()
    {
        long bits = Double.doubleToLongBits (m_value);
        return (int) (bits ^ (bits >>> 32));
    }
}

package com.palmer.me4j.token;

/**
 * Created by Mike Palmer on 4/18/14.
 */
public class UnaryOperatorToken extends OperatorToken
{
    private final UnaryOperator m_operator;
    private final int           m_precedence;
    private final Associativity m_associativity;

    public UnaryOperatorToken (String symbol, int precedence, Associativity associativity, UnaryOperator operator)
    {
        super (symbol);

        if (precedence < 0)
        {
            throw new IllegalArgumentException ("Precedence cannot be negative.");
        }
        if (operator == null)
        {
            throw new IllegalArgumentException ("UnaryOperator cannot be null.");
        }

        m_operator = operator;
        m_precedence = precedence;
        m_associativity = associativity;
    }

    @Override
    public int getPrecedence ()
    {
        return m_precedence;
    }

    @Override
    public Associativity getAssociativity ()
    {
        return m_associativity;
    }

    public OperandToken performOperation (OperandToken operand)
    {
        double result = m_operator.operate (operand.getValue ());
        return new OperandToken (result);
    }

    @Override
    public boolean equals (Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass () != o.getClass ())
        {
            return false;
        }

        UnaryOperatorToken that = (UnaryOperatorToken) o;

        if (m_precedence != that.m_precedence)
        {
            return false;
        }
        if (m_associativity != that.m_associativity)
        {
            return false;
        }
        if (!m_operator.equals (that.m_operator))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode ()
    {
        int result = m_operator.hashCode ();
        result = 31 * result + m_precedence;
        result = 31 * result + m_associativity.hashCode ();
        return result;
    }
}

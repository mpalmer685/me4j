package com.palmer.me4j.token;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class GroupingToken extends Token
{
    private final OperatorToken m_leftOperator;
    private final OperatorToken m_rightOperator;

    public GroupingToken (String leftSymbol, String rightSymbol)
    {
        if (leftSymbol == null || leftSymbol.isEmpty ())
        {
            throw new IllegalArgumentException ("Left symbol must be set.");
        }
        if (rightSymbol == null || rightSymbol.isEmpty ())
        {
            throw new IllegalArgumentException ("Right symbol must be set.");
        }

        m_leftOperator = new OperatorToken (leftSymbol, Precedence.GROUPING_LEFT, dummyOperator);
        m_rightOperator = new OperatorToken (rightSymbol, Precedence.GROUPING_RIGHT, dummyOperator);
    }

    @Override
    public TokenType getTokenType ()
    {
        return TokenType.OPERATOR;
    }

    public OperatorToken getLeftOperator ()
    {
        return m_leftOperator;
    }

    public OperatorToken getRightOperator ()
    {
        return m_rightOperator;
    }

    private static Operator dummyOperator = new Operator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            throw new UnsupportedOperationException ("Grouping operators cannot perform operations.");
        }
    };

    @Override
    public String toString ()
    {
        return m_leftOperator.toString () + m_rightOperator.toString ();
    }

    @Override
    public boolean equals (Object obj)
    {
        if (obj == this)
        {
            return true;
        }

        if (obj instanceof GroupingToken)
        {
            GroupingToken token = (GroupingToken) obj;
            return m_leftOperator.equals (token.m_leftOperator) && m_rightOperator.equals (token.m_rightOperator);
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode ()
    {
        int prime = 17;
        int hashCode = 23;

        hashCode = hashCode * prime + m_leftOperator.hashCode ();
        hashCode = hashCode * prime + m_rightOperator.hashCode ();

        return hashCode;
    }
}

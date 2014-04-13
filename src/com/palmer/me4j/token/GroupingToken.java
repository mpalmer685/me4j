package com.palmer.me4j.token;

/**
 * A Token which represents the matching pair of symbols used for grouping.
 *
 * Created by Mike Palmer on 3/29/14.
 */
public class GroupingToken extends Token
{
    private final OperatorToken m_leftOperator;
    private final OperatorToken m_rightOperator;

    /**
     * Creates a new GroupingToken instance with the specified left and right symbols.
     * @param leftSymbol the symbol that represents the left grouping token
     * @param rightSymbol the symbol that represents the right grouping token
     */
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

    /**
     * Returns the symbol for this GroupingToken that matches (i.e. is paired with) the specified symbol.
     * @param symbol the symbol whose match is desired
     * @return the symbol that matches the specified symbol
     */
    public String getMatchingSymbol (String symbol)
    {
        String matchingSymbol;

        if (m_leftOperator.toString ().equals (symbol))
        {
            matchingSymbol = m_rightOperator.toString ();
        }
        else if (m_rightOperator.toString ().equals (symbol))
        {
            matchingSymbol = m_leftOperator.toString ();
        }
        else
        {
            throw new IllegalArgumentException ("The symbol " + symbol + " is not valid.");
        }

        return matchingSymbol;
    }

    private static final Operator dummyOperator = new Operator ()
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

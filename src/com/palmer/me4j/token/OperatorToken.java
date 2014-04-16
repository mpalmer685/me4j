package com.palmer.me4j.token;

/**
 * A Token that represents an Operator, which can perform operations on operands.
 *
 * Created by Mike Palmer on 3/27/14.
 */
public class OperatorToken extends Token
{
    private final String   m_symbol;
    private final Operator m_operator;
    private final int      m_precedence;
    private final Associativity m_associativity;

    /**
     * Creates a new OperatorToken instance.
     * @param symbol the String that will be used to represent this operator in the expression string
     * @param precedence the precedence value of this operator. A higher value indicates higher precedence when
     *                   determining order of operations.
     * @param operator the Operator class which will handle performing operations on operands
     */
    public OperatorToken (String symbol, int precedence, Associativity associativity, Operator operator)
    {
        if (symbol == null || symbol.isEmpty ())
        {
            throw new IllegalArgumentException ("Symbol must be set.");
        }
        if (precedence < 0)
        {
            throw new IllegalArgumentException ("Precedence cannot be negative.");
        }
        if (operator == null)
        {
            throw new IllegalArgumentException ("Operator cannot be null.");
        }

        m_symbol = symbol;
        m_operator = operator;
        m_precedence = precedence;
        m_associativity = associativity;
    }

    @Override
    public TokenType getTokenType ()
    {
        return TokenType.OPERATOR;
    }

    public int getPrecedence ()
    {
        return m_precedence;
    }

    public Associativity getAssociativity ()
    {
        return m_associativity;
    }

    /**
     * Performs this operator's operation on a left and right operand.
     * @param leftOperand the left operand
     * @param rightOperand the right operand
     * @return a new OperandToken whose value is the result of performing this operator's operation on the left
     * and right operand
     */
    public OperandToken performOperation (OperandToken leftOperand, OperandToken rightOperand)
    {
        double result = m_operator.operate (leftOperand.getValue (), rightOperand.getValue ());
        return new OperandToken (result);
    }

    @Override
    public String toString ()
    {
        return m_symbol;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (obj == this)
        {
            return true;
        }

        if (obj instanceof OperatorToken)
        {
            OperatorToken token = (OperatorToken) obj;
            // Operators are unique only by their symbols (it is assumed that their operation is directly dependent
            // upon the symbol, so checking both is redundant)
            return m_symbol.equals (token.toString ()) &&
                   m_precedence == token.m_precedence;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode ()
    {
        int hashCode = 23;
        int prime = 17;

        hashCode = hashCode * prime + m_symbol.hashCode ();

        return hashCode;
    }
}

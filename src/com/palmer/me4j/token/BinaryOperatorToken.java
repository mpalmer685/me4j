package com.palmer.me4j.token;

/**
 * A Token that represents an BinaryOperator, which can perform operations on operands.
 *
 * Created by Mike Palmer on 3/27/14.
 */
public class BinaryOperatorToken extends OperatorToken
{
    private final BinaryOperator m_operator;
    private final int            m_precedence;
    private final Associativity  m_associativity;

    /**
     * Creates a new OperatorToken instance.
     * @param symbol the String that will be used to represent this operator in the expression string
     * @param precedence the precedence value of this operator. A higher value indicates higher precedence when
     *                   determining order of operations.
     * @param associativity the Associativity, either left or right, of the operator
     * @param operator the BinaryOperator class which will handle performing operations on operands
     */
    public BinaryOperatorToken (String symbol, int precedence, Associativity associativity, BinaryOperator operator)
    {
        super (symbol);

        if (precedence < 0)
        {
            throw new IllegalArgumentException ("Precedence cannot be negative.");
        }
        if (operator == null)
        {
            throw new IllegalArgumentException ("BinaryOperator cannot be null.");
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

        BinaryOperatorToken that = (BinaryOperatorToken) o;

        if (m_precedence != that.m_precedence)
        {
            return false;
        }
        if (m_associativity != that.m_associativity)
        {
            return false;
        }
        if (!toString ().equals (that.toString ()))
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode ()
    {
        int result = toString ().hashCode ();
        result = 31 * result + m_precedence;
        result = 31 * result + m_associativity.hashCode ();
        return result;
    }
}

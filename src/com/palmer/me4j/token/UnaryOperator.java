package com.palmer.me4j.token;

/**
 * Interface for declaring a unary operation that can be performed by a UnaryOperatorToken.
 *
 * Created by Mike Palmer on 4/18/14.
 */
public interface UnaryOperator
{
    /**
     * Performs a mathematical operation on an operand.
     * @param operand the operand
     * @return the result of performing a mathematical operation on the operand
     */
    public double operate (double operand);
}

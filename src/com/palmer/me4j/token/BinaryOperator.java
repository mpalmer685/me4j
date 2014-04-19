package com.palmer.me4j.token;

/**
 * Interface for declaring a binary operation that can be performed by an OperatorToken.
 *
 * Created by Mike Palmer on 3/27/14.
 */
public interface BinaryOperator
{
    /**
     * Performs a mathematical operation on a left and right operand.
     * @param leftOperand the left operand
     * @param rightOperand the right operand
     * @return the result of performing a mathematical operation on the left and right operand
     */
    public double operate (double leftOperand, double rightOperand);
}

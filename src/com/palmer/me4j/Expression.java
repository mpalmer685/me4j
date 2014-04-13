package com.palmer.me4j;

import com.palmer.me4j.token.Token;

import java.util.List;

/**
 * An Expression is a mathematical sequence of operators and operands, written in infix notation, that can be parsed
 * and evaluated to return a numerical result.
 *
 * Created by Mike Palmer on 4/12/14.
 */
public class Expression
{
    private final String m_expressionString;

    /**
     * Creates a new Expression instance that can be evaluated.
     * @param expressionString the string to be parsed and evaluated
     */
    public Expression (String expressionString)
    {
        m_expressionString = expressionString;
    }

    /**
     * Evaluates this Expression instance.
     * @return the result of evaluating this Expression
     */
    public double evaluate ()
    {
        List<Token> tokens = TokenScanner.tokenize (m_expressionString);
        return TokenEvaluator.evaluateTokens (tokens);
    }
}

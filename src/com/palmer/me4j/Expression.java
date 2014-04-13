package com.palmer.me4j;

import com.palmer.me4j.token.Token;

import java.util.List;

/**
 * Created by Mike Palmer on 4/12/14.
 */
public class Expression
{
    private String m_expressionString;

    public Expression (String expressionString)
    {
        m_expressionString = expressionString;
    }

    public double evaluate ()
    {
        List<Token> tokens = TokenScanner.tokenize (m_expressionString);
        return TokenEvaluator.evaluateTokens (tokens);
    }
}

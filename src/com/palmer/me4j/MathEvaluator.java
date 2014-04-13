package com.palmer.me4j;

import com.palmer.me4j.token.Token;

import java.util.List;

/**
 * Created by Mike Palmer on 4/12/14.
 */
public class MathEvaluator
{
    public static double evaluate (String expression)
    {
        List<Token> tokens = TokenScanner.tokenize (expression);
        return TokenEvaluator.evaluateTokens (tokens);
    }
}

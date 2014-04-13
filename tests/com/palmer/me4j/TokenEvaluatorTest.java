package com.palmer.me4j;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Token;
import com.palmer.me4j.token.builtin.AdditionOperatorToken;
import com.palmer.me4j.token.builtin.MultiplicationOperatorToken;
import com.palmer.me4j.token.builtin.ParensGroupingToken;
import com.palmer.me4j.token.builtin.SubtractionOperatorToken;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class TokenEvaluatorTest
{
    @Test
    public void testSimpleEvaluateTokens ()
    {
        List<Token> tokens = createTokenList
                (
                        new OperandToken (1.0),
                        AdditionOperatorToken.INSTANCE,
                        new OperandToken (2.0),
                        MultiplicationOperatorToken.INSTANCE,
                        new OperandToken (3.0)
                );

        assertEquals (0, Double.compare (7.0, TokenEvaluator.evaluateTokens (tokens)));
    }

    @Test
    public void testSlightlyMoreComplicatedEvaluateTokens ()
    {
        List<Token> tokens = createTokenList
                (
                        new OperandToken (1.0),
                        MultiplicationOperatorToken.INSTANCE,
                        new OperandToken (2.0),
                        MultiplicationOperatorToken.INSTANCE,
                        new OperandToken (3.0),
                        AdditionOperatorToken.INSTANCE,
                        new OperandToken (4.0)
                );

        assertEquals (0, Double.compare (10.0, TokenEvaluator.evaluateTokens (tokens)));
    }

    @Test
    public void testSimpleEvaluationWithParentheses ()
    {
        List<Token> tokens = createTokenList
                (
                        new OperandToken (1.0),
                        SubtractionOperatorToken.INSTANCE,
                        ParensGroupingToken.INSTANCE.getLeftOperator (),
                        new OperandToken (2.0),
                        AdditionOperatorToken.INSTANCE,
                        new OperandToken (3.0),
                        ParensGroupingToken.INSTANCE.getRightOperator ()
                );

        assertEquals (0, Double.compare (-4.0, TokenEvaluator.evaluateTokens (tokens)));
    }

    @Test
    public void testSlightlyMoreComplexEvaluationWithParentheses ()
    {
        List<Token> tokens = createTokenList
                (
                        new OperandToken (1.0),
                        SubtractionOperatorToken.INSTANCE,
                        ParensGroupingToken.INSTANCE.getLeftOperator (),
                        new OperandToken (2.0),
                        AdditionOperatorToken.INSTANCE,
                        new OperandToken (3.0),
                        ParensGroupingToken.INSTANCE.getRightOperator (),
                        MultiplicationOperatorToken.INSTANCE,
                        new OperandToken (4.0)
                );

        assertEquals (0, Double.compare (-19.0, TokenEvaluator.evaluateTokens (tokens)));
    }

    @Test
    public void testEvaluationWithNestedParentheses ()
    {
        List<Token> tokens = createTokenList
                (
                        ParensGroupingToken.INSTANCE.getLeftOperator (),
                        new OperandToken (1.0),
                        SubtractionOperatorToken.INSTANCE,
                        ParensGroupingToken.INSTANCE.getLeftOperator (),
                        new OperandToken (2.0),
                        AdditionOperatorToken.INSTANCE,
                        new OperandToken (3.0),
                        ParensGroupingToken.INSTANCE.getRightOperator (),
                        ParensGroupingToken.INSTANCE.getRightOperator (),
                        MultiplicationOperatorToken.INSTANCE,
                        new OperandToken (4.0)
                );

        assertEquals (0, Double.compare (-16.0, TokenEvaluator.evaluateTokens (tokens)));
    }

    private static List<Token> createTokenList (Token... tokens)
    {
        return Arrays.asList (tokens);
    }
}

package com.palmer.me4j;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Token;
import com.palmer.me4j.token.builtin.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class TokenEvaluatorTest
{
    @Test
    public void testSimpleEvaluateTokens ()
    {
        // 1 + 2 * 3
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
        // 1 * 2 * 3 + 4
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
        // 1 - ( 2 + 3 )
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
        // 1 - ( 2 + 3 ) * 4
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
        // ( 1 - ( 2 + 3 ) ) * 4
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

    @Test
    public void testExponentOperation ()
    {
        // 3 + 2 ^ 2
        List<Token> tokenList1 = createTokenList (
                new OperandToken (3.0),
                AdditionOperatorToken.INSTANCE,
                new OperandToken (2.0),
                ExponentOperatorToken.INSTANCE,
                new OperandToken (2.0)
        );
        // ( 3 + 2 ) ^ 2
        List<Token> tokenList2 = createTokenList (
                ParensGroupingToken.INSTANCE.getLeftOperator (),
                new OperandToken (3.0),
                AdditionOperatorToken.INSTANCE,
                new OperandToken (2.0),
                ParensGroupingToken.INSTANCE.getRightOperator (),
                ExponentOperatorToken.INSTANCE,
                new OperandToken (2.0)
        );

        assertEquals (0, Double.compare (7.0, TokenEvaluator.evaluateTokens (tokenList1)));
        assertEquals (0, Double.compare (25.0, TokenEvaluator.evaluateTokens (tokenList2)));
    }

    @Test
    public void testExponentiationAssociativity ()
    {
        // 2 ^ 3 ^ 2
        List<Token> tokens = createTokenList (
                new OperandToken (2.0),
                ExponentOperatorToken.INSTANCE,
                new OperandToken (3.0),
                ExponentOperatorToken.INSTANCE,
                new OperandToken (2.0)
        );

        double result = TokenEvaluator.evaluateTokens (tokens);

        // NOT (2 ^ 3) ^ 2
        assertNotEquals (0, Double.compare (64.0, result));

        // BUT 2 ^ (3 ^ 2)
        assertEquals (0, Double.compare (512.0, result));
    }

    private static List<Token> createTokenList (Token... tokens)
    {
        return Arrays.asList (tokens);
    }
}

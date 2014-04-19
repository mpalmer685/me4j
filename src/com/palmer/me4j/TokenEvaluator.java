package com.palmer.me4j;

import com.palmer.me4j.token.*;

import java.util.List;
import java.util.Stack;

/**
 * Evaluates a sequence of Tokens that have been parsed from an expression string.  Evaluation includes converting the
 * expression from an infix List to a postfix Stack, and evaluating the Stack to determine a numerical result.
 *
 * Created by Mike Palmer on 3/29/14.
 */
class TokenEvaluator
{
    private final List<Token>          m_tokens;
    private       Stack<OperatorToken> m_operatorStack;
    private       Stack<Token>         m_postfixStack;

    private TokenEvaluator (List<Token> tokens)
    {
        m_tokens = tokens;
    }

    static double evaluateTokens (List<Token> tokens)
    {
        TokenEvaluator evaluator = new TokenEvaluator (tokens);
        evaluator.convertInfixToPostfix ();
        return evaluator.evaluatePostfixStack ();
    }

    private static <T> Stack<T> reverseStackOrder (Stack<T> originalStack)
    {
        Stack<T> reversedStack = new Stack<> ();

        while (!originalStack.isEmpty ())
        {
            reversedStack.push (originalStack.pop ());
        }

        return reversedStack;
    }

    private static void processOperatorToken (BinaryOperatorToken operator, Stack<OperandToken> operandStack)
    {
        OperandToken rightOperand = operandStack.pop ();
        OperandToken leftOperand = operandStack.pop ();

        operandStack.push (operator.performOperation (leftOperand, rightOperand));
    }

    private void convertInfixToPostfix ()
    {
        m_operatorStack = new Stack<> ();
        m_postfixStack = new Stack<> ();

        for (Token currentToken : m_tokens)
        {
            if (currentToken.getTokenType () == TokenType.OPERAND)
            {
                m_postfixStack.push (currentToken);
            }
            else
            {
                pushOperatorTokenToStack ((OperatorToken) currentToken);
            }
        }

        while (!m_operatorStack.isEmpty ())
        {
            m_postfixStack.push (m_operatorStack.pop ());
        }

        m_postfixStack = reverseStackOrder (m_postfixStack);
    }

    private void pushOperatorTokenToStack (OperatorToken currentOperator)
    {
        while (shouldMoveOperatorToPostfixStack (currentOperator))
        {
            OperatorToken topOperator = m_operatorStack.pop ();

            if (topOperator.getPrecedence () < Precedence.GROUPING_RIGHT)
            {
                m_postfixStack.push (topOperator);
            }

            if (currentOperator.getPrecedence () == Precedence.GROUPING_RIGHT &&
                    topOperator.getPrecedence () == Precedence.GROUPING_LEFT)
            {
                break;
            }
        }

        if (currentOperator.getPrecedence () != Precedence.GROUPING_RIGHT)
        {
            m_operatorStack.push (currentOperator);
        }
    }

    private boolean shouldMoveOperatorToPostfixStack (OperatorToken currentOperator)
    {
        if (m_operatorStack.isEmpty ())
        {
            return false;
        }
        else
        {
            boolean isCurrentOperatorPrecedenceLower = (currentOperator.getPrecedence () < m_operatorStack.peek ().getPrecedence () ||
                                                        (currentOperator.getPrecedence () == m_operatorStack.peek ().getPrecedence () &&
                                                        currentOperator.getAssociativity () == Associativity.LEFT_ASSOCIATIVE));

            boolean isStackOperatorNoGroupingLeft = m_operatorStack.peek ().getPrecedence () < Precedence.GROUPING_LEFT;
            boolean isCurrentOperatorGroupingRight = currentOperator.getPrecedence () == Precedence.GROUPING_RIGHT;

            return ((isCurrentOperatorPrecedenceLower && isStackOperatorNoGroupingLeft) || isCurrentOperatorGroupingRight);
        }
    }

    private double evaluatePostfixStack ()
    {
        Stack<OperandToken> operandStack = new Stack<> ();

        while (!m_postfixStack.isEmpty ())
        {
            Token currentToken = m_postfixStack.pop ();

            if (currentToken.getTokenType () == TokenType.OPERAND)
            {
                operandStack.push ((OperandToken) currentToken);
            }
            else
            {
                processOperatorToken ((BinaryOperatorToken) currentToken, operandStack);
            }
        }

        return operandStack.peek ().getValue ();
    }
}

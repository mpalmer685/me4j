package com.palmer.me4j;

import com.palmer.me4j.token.GroupingToken;
import com.palmer.me4j.token.OperatorToken;
import com.palmer.me4j.token.Token;
import com.palmer.me4j.token.TokenType;
import com.palmer.me4j.token.builtin.*;

import java.util.*;

/**
 * Converts an expression string into operand and operator tokens.
 *
 * Created by Mike Palmer on 3/29/14.
 */
class TokenScanner
{
    static final         Map<String, OperatorToken> REGISTERED_OPERATORS;
    private static final Map<String, GroupingToken> REGISTERED_GROUPINGS;

    static
    {
        REGISTERED_OPERATORS = new HashMap<> ();
        registerOperatorToken (AdditionOperatorToken.INSTANCE);
        registerOperatorToken (SubtractionOperatorToken.INSTANCE);
        registerOperatorToken (MultiplicationOperatorToken.INSTANCE);
        registerOperatorToken (DivisionOperatorToken.INSTANCE);

        REGISTERED_GROUPINGS = new HashMap<> ();
        registerGroupingToken (ParensGroupingToken.INSTANCE);
        registerGroupingToken (SquareBraceGroupingToken.INSTANCE);
        registerGroupingToken (CurlyBraceGroupingToken.INSTANCE);
    }

    private final String m_inputString;

    private int m_tokenStart;
    private int m_tokenEnd;
    private TokenType m_lastTokenType;

    private TokenScanner (String input)
    {
        m_inputString = input.trim ();

        m_tokenStart = m_tokenEnd = 0;
    }

    static List<Token> tokenize (String input)
    {
        quickValidateExpressionString (input);
        TokenScanner scanner = new TokenScanner (input);
        List<Token> tokens = new ArrayList<> ();

        while (scanner.hasNextToken ())
        {
            tokens.add (scanner.nextToken ());
        }

        return tokens;
    }

    private static void registerOperatorToken (OperatorToken token)
    {
        REGISTERED_OPERATORS.put (token.toString (), token);
    }

    private static void registerGroupingToken (GroupingToken token)
    {
        REGISTERED_GROUPINGS.put (token.getLeftOperator ().toString () + token.getRightOperator ().toString (), token);

        REGISTERED_OPERATORS.put (token.getLeftOperator ().toString (), token.getLeftOperator ());
        REGISTERED_OPERATORS.put (token.getRightOperator ().toString (), token.getRightOperator ());
    }

    private static void quickValidateExpressionString (String expression)
    {
        // Perform basic check for string validity.
        // The basic check includes ensuring that all grouping operators are paired, and that no unexpected operator
        // symbols exist in the string.
        GroupingCounter counter = new GroupingCounter (REGISTERED_GROUPINGS.values ());

        char[] expressionAsChars = expression.toCharArray ();

        for (char currentChar : expressionAsChars)
        {
            // 1. Check to see if the current character is a grouping symbol
            if (counter.put (String.valueOf (currentChar)))
            {
                continue;
            }

            // 2. Check to see if the current character is a valid operator symbol
            if (isCharacterPossibleOperator (currentChar) && !REGISTERED_OPERATORS.containsKey (String.valueOf (currentChar)))
            {
                throw new UnsupportedOperationException ("Unexpected character encountered: " + currentChar);
            }
        }

        counter.checkGroupingsAreMatched ();
    }

    private static boolean isCharacterPossibleOperator (char c)
    {
        return !(Character.isDigit (c) ||
                 Character.isAlphabetic (c) ||
                 Character.isWhitespace (c) ||
                 (c == '.'));
    }

    private static boolean isDigitOrDecimal (char c)
    {
        return Character.isDigit (c) || (c == '.');
    }

    private boolean hasNextToken ()
    {
        return m_tokenEnd < m_inputString.length ();
    }

    private Token nextToken ()
    {
        Token nextToken;

        m_tokenStart = m_tokenEnd;

        while (Character.isWhitespace (m_inputString.charAt (m_tokenStart)))
        {
            m_tokenStart++;
        }

        m_tokenEnd = m_tokenStart + 1;

        if (isDigitOrDecimal (m_inputString.charAt (m_tokenStart)) || (m_lastTokenType == TokenType.OPERATOR && m_inputString.charAt (m_tokenStart) == '-'))
        {
            while (m_tokenEnd < m_inputString.length () && isDigitOrDecimal (m_inputString.charAt (m_tokenEnd)))
            {
                m_tokenEnd++;
            }

            nextToken = TokenFactory.buildToken (m_inputString.substring (m_tokenStart, m_tokenEnd));
        }
        else
        {
            nextToken = TokenFactory.buildToken (String.valueOf (m_inputString.charAt (m_tokenStart)));
        }

        m_lastTokenType = nextToken.getTokenType ();

        return nextToken;
    }

    private static final class GroupingCounter
    {
        private final Map<String, GroupingToken> m_leftTokens;
        private final Map<String, GroupingToken> m_rightTokens;

        private final Stack<GroupingToken> m_tokenStack;

        GroupingCounter (Collection<GroupingToken> tokens)
        {
            m_leftTokens = new HashMap<> ();
            m_rightTokens = new HashMap<> ();

            m_tokenStack = new Stack<> ();

            for (GroupingToken token : tokens)
            {
                m_leftTokens.put (token.getLeftOperator ().toString (), token);
                m_rightTokens.put (token.getRightOperator ().toString (), token);
            }
        }

        boolean put (String symbol)
        {
            boolean status = false;

            if (m_leftTokens.containsKey (symbol))
            {
                m_tokenStack.push (m_leftTokens.get (symbol));

                status = true;
            }
            else if (m_rightTokens.containsKey (symbol))
            {
                GroupingToken leftToken = null;
                try
                {
                    leftToken = m_tokenStack.pop ();
                }
                catch (EmptyStackException e)
                {
                    // TODO Create "malformed expression" error because there is an unmatched right grouping operator
                    throw new UnsupportedOperationException ();
                }

                GroupingToken rightToken = m_rightTokens.get (symbol);

                if (!rightToken.equals (leftToken))
                {
                    // TODO Create "malformed expression" error because there is an unmatched left grouping operator
                    throw new UnsupportedOperationException ();
                }

                status = true;
            }

            return status;
        }

        void checkGroupingsAreMatched ()
        {
            if (!m_tokenStack.isEmpty ())
            {
                // TODO Create "malformed expression" error because there is an unmatched left grouping operator
                throw new UnsupportedOperationException ();
            }
        }
    }
}

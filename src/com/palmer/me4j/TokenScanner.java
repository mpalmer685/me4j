package com.palmer.me4j;

import com.palmer.me4j.token.GroupingToken;
import com.palmer.me4j.token.OperatorToken;
import com.palmer.me4j.token.Token;
import com.palmer.me4j.token.builtin.*;

import java.util.*;

/**
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

    private final Scanner m_scanner;

    private TokenScanner (String input)
    {
        m_scanner = new Scanner (input);
        m_scanner.useDelimiter (" ");
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

        if (!counter.areGroupingsMatched ())
        {
            throw new UnsupportedOperationException ("Invalid expression: Open and close grouping symbols do not match.");
        }
    }

    private static boolean isCharacterPossibleOperator (char c)
    {
        return !(Character.isDigit (c) ||
                 Character.isAlphabetic (c) ||
                 Character.isWhitespace (c) ||
                 (c == '.'));
    }

    private boolean hasNextToken ()
    {
        return m_scanner.hasNext ();
    }

    private Token nextToken ()
    {
        String symbol = m_scanner.next ();
        return TokenFactory.buildToken (symbol);
    }

    private static final class GroupingCounter
    {
        private final Map<String, Integer> m_leftTokenCounts;
        private final Map<String, Integer> m_rightTokenCounts;

        private final Collection<GroupingToken> m_groupingTokens;

        GroupingCounter (Collection<GroupingToken> tokens)
        {
            m_leftTokenCounts = new HashMap<> ();
            m_rightTokenCounts = new HashMap<> ();

            m_groupingTokens = tokens;

            for (GroupingToken token : tokens)
            {
                m_leftTokenCounts.put (token.getLeftOperator ().toString (), 0);
                m_rightTokenCounts.put (token.getRightOperator ().toString (), 0);
            }
        }

        boolean put (String symbol)
        {
            boolean status = false;

            if (m_leftTokenCounts.containsKey (symbol))
            {
                int count = m_leftTokenCounts.get (symbol);
                m_leftTokenCounts.put (symbol, count + 1);

                status = true;
            }
            else if (m_rightTokenCounts.containsKey (symbol))
            {
                int count = m_rightTokenCounts.get (symbol);
                m_rightTokenCounts.put (symbol, count + 1);

                status = true;
            }

            return status;
        }

        boolean areGroupingsMatched ()
        {
            for (GroupingToken groupingToken : m_groupingTokens)
            {
                Integer leftCount = m_leftTokenCounts.get (groupingToken.getLeftOperator ().toString ());
                Integer rightCount = m_rightTokenCounts.get (groupingToken.getRightOperator ().toString ());

                if (leftCount.intValue () != rightCount.intValue ())
                {
                    return false;
                }
            }

            return true;
        }
    }
}

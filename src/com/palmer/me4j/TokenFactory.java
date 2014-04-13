package com.palmer.me4j;

import com.palmer.me4j.token.GroupingToken;
import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.OperatorToken;
import com.palmer.me4j.token.Token;
import com.palmer.me4j.token.builtin.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mike Palmer on 3/28/14.
 */
public class TokenFactory
{
    private static final Map<String, OperatorToken> REGISTERED_OPERATORS;
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
    }

    private TokenFactory ()
    {
        throw new UnsupportedOperationException ();
    }

    public static Token buildToken (String symbol)
    {
        Token token = REGISTERED_OPERATORS.get (symbol);

        if (token == null)
        {
            try
            {
                double operandValue = Double.parseDouble (symbol);
                token = new OperandToken (operandValue);
            }
            catch (NumberFormatException e)
            {
                throw new IllegalArgumentException ("Symbol " + symbol + " is not a valid operator symbol and could not be parsed as an operand.");
            }
        }

        return token;
    }

    public static void registerOperatorToken (OperatorToken token)
    {
        REGISTERED_OPERATORS.put (token.toString (), token);
    }

    public static void registerGroupingToken (GroupingToken token)
    {
        REGISTERED_GROUPINGS.put (token.getLeftOperator ().toString () + token.getRightOperator ().toString (), token);

        REGISTERED_OPERATORS.put (token.getLeftOperator ().toString (), token.getLeftOperator ());
        REGISTERED_OPERATORS.put (token.getRightOperator ().toString (), token.getRightOperator ());
    }
}

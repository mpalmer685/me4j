package com.palmer.me4j;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.Token;

/**
 * Created by Mike Palmer on 3/28/14.
 */
class TokenFactory
{
    private TokenFactory ()
    {
        throw new UnsupportedOperationException ();
    }

    static Token buildToken (String symbol)
    {
        Token token = TokenScanner.REGISTERED_OPERATORS.get (symbol);

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
}

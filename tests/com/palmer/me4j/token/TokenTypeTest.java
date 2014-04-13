package com.palmer.me4j.token;

import org.junit.Test;

/**
 * Created by Mike Palmer on 3/26/14.
 */
public class TokenTypeTest
{
    @Test
    public void testOperator ()
    {
        TokenType.valueOf (TokenType.OPERATOR.toString ());
    }

    @Test
    public void testOperand ()
    {
        TokenType.valueOf (TokenType.OPERAND.toString ());
    }
}

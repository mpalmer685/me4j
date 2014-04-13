package com.palmer.me4j;

import com.palmer.me4j.token.OperandToken;
import com.palmer.me4j.token.OperatorToken;
import com.palmer.me4j.token.Token;
import com.palmer.me4j.token.builtin.*;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class TokenFactoryTest
{
    @Test
    public void testBuildOperand ()
    {
        double operand = 5.0;
        Token token = TokenFactory.buildToken (Double.toString (operand));

        assertTrue (token instanceof OperandToken);
    }

    @Test
    public void testBuildOperators ()
    {
        List<String> operators = createOperators ();

        Token token;

        for (String operator : operators)
        {
            token = TokenFactory.buildToken (operator);

            assertTrue (token instanceof OperatorToken);
        }
    }

    @Test
    public void testBuildFailsWithUnknownSymbol ()
    {
        Exception expectedException = null;

        try
        {
            TokenFactory.buildToken ("symbol");
        }
        catch (IllegalArgumentException e)
        {
            expectedException = e;
        }

        assertNotNull (expectedException);
    }
    @Test
    public void testPrivateConstructorThrowsException () throws Exception
    {
        Exception exception = null;

        Constructor<TokenFactory> constructor = TokenFactory.class.getDeclaredConstructor ();
        constructor.setAccessible (true);

        try
        {
            constructor.newInstance ();
            fail ("No Exception thrown.");
        }
        catch (InvocationTargetException e)
        {
            exception = (Exception) e.getCause ();
        }

        assertNotNull (exception);
        assertTrue (exception instanceof UnsupportedOperationException);
    }

    private static List<String> createOperators ()
    {
        List<String> operators = new ArrayList<> ();
        operators.add (AdditionOperatorToken.SYMBOL);
        operators.add (SubtractionOperatorToken.SYMBOL);
        operators.add (MultiplicationOperatorToken.SYMBOL);
        operators.add (DivisionOperatorToken.SYMBOL);
        operators.add (ParensGroupingToken.LEFT_SYMBOL);
        operators.add (ParensGroupingToken.RIGHT_SYMBOL);

        return operators;
    }
}

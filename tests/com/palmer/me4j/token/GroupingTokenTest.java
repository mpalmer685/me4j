package com.palmer.me4j.token;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class GroupingTokenTest
{
    private GroupingToken parens;
    private OperatorToken leftParen;
    private OperatorToken rightParen;

    private GroupingToken x;
    private GroupingToken y;
    private GroupingToken z;
    private GroupingToken notX;

    @Before
    public void setUp ()
    {
        parens = new GroupingToken ("(", ")");
        leftParen = new OperatorToken ("(", Precedence.GROUPING_LEFT, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        rightParen = new OperatorToken (")", Precedence.GROUPING_RIGHT, Associativity.LEFT_ASSOCIATIVE, dummyOperator);

        x = new GroupingToken ("(", ")");
        y = new GroupingToken ("(", ")");
        z = new GroupingToken ("(", ")");
        notX = new GroupingToken ("[", "]");
    }

    @Test
    public void testNullLeftSymbolThrowsException ()
    {
        Exception exception = null;

        try
        {
            new GroupingToken (null, ".");
            fail ();
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNotNull (exception);
        assertTrue (exception instanceof IllegalArgumentException);
    }

    @Test
    public void testNullRightSymbolThrowsException ()
    {
        Exception exception = null;

        try
        {
            new GroupingToken (".", null);
            fail ();
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNotNull (exception);
        assertTrue (exception instanceof IllegalArgumentException);
    }

    @Test
    public void testLeftOperationThrowsException ()
    {
        OperandToken operand = new OperandToken (1.0);

        Exception exception = null;

        try
        {
            parens.getLeftOperator ().performOperation (operand, operand);
            fail ();
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNotNull (exception);
        assertTrue (exception instanceof UnsupportedOperationException);
    }

    @Test
    public void testRightOperationThrowsException ()
    {
        OperandToken operand = new OperandToken (1.0);

        Exception exception = null;

        try
        {
            parens.getRightOperator ().performOperation (operand, operand);
            fail ();
        }
        catch (Exception e)
        {
            exception = e;
        }

        assertNotNull (exception);
        assertTrue (exception instanceof UnsupportedOperationException);
    }

    @Test
    public void testGetTokenType () throws Exception
    {
        assertEquals (TokenType.OPERATOR, parens.getTokenType ());
    }

    @Test
    public void testGetLeftOperator () throws Exception
    {
        assertEquals (leftParen, parens.getLeftOperator ());
    }

    @Test
    public void testGetRightOperator () throws Exception
    {
        assertEquals (rightParen, parens.getRightOperator ());
    }

    @Test
    public void testGetMatchingSymbol ()
    {
        assertEquals (rightParen.toString (), parens.getMatchingSymbol (leftParen.toString ()));
        assertEquals (leftParen.toString (), parens.getMatchingSymbol (rightParen.toString ()));
    }

    @Test
    public void testGetMatchingSymbolWithInvalidSymbolThrowsException ()
    {
        Exception expected = null;

        try
        {
            parens.getMatchingSymbol (".");
            fail ("Did not throw exception.");
        }
        catch (Exception e)
        {
            expected = e;
        }

        assertNotNull (expected);
        assertTrue (expected instanceof IllegalArgumentException);
    }

    @Test
    public void testToString () throws Exception
    {
        assertEquals ("()", parens.toString ());
    }

    @Test
    public void testEqualsIsReflexive ()
    {
        assertTrue (x.equals (x));
    }

    @Test
    public void testEqualsIsSymmetric ()
    {
        assertTrue (x.equals (y));
        assertTrue (y.equals (x));

        assertFalse (x.equals (notX));
        assertFalse (notX.equals (y));
    }

    @Test
    public void testEqualsIsTransitive ()
    {
        assertTrue (x.equals (y));
        assertTrue (y.equals (z));
        assertTrue (x.equals (z));
    }

    @Test
    public void testEqualsIsConsistent ()
    {
        assertTrue (x.equals (y));
        assertTrue (x.equals (y));
        assertTrue (x.equals (y));

        assertFalse (x.equals (notX));
        assertFalse (x.equals (notX));
        assertFalse (x.equals (notX));
    }

    @Test
    public void testEqualsFailsWithDifferentType ()
    {
        assertFalse (x.equals ("x"));
    }

    @Test
    public void testEqualsFailsWithNull ()
    {
        assertFalse (x.equals (null));
    }

    @Test
    public void testHashCodeIsConsistent ()
    {
        int hashCode = x.hashCode ();

        assertEquals (hashCode, x.hashCode ());
        assertEquals (hashCode, x.hashCode ());
    }

    @Test
    public void testHashCodesForEqualObjectsAreEqual ()
    {
        assertEquals (x.hashCode (), y.hashCode ());
    }

    @Test
    public void testHashCodesForUnequalObjectsAreUnequal ()
    {
        assertNotEquals (x.hashCode (), notX.hashCode ());
    }

    private static final Operator dummyOperator = new Operator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return 0;
        }
    };
}

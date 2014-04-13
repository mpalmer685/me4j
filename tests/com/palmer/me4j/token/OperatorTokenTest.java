package com.palmer.me4j.token;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mike Palmer on 3/27/14.
 */
public class OperatorTokenTest
{
    private static final String DIVIDE_SYMBOL         = "/";
    private static final String ADD_SYMBOL            = "+";
    private static final String DUMMY_SYMBOL          = ".";
    private static final String MULTIPLICATION_SYMBOL = "*";

    private OperandToken left;
    private OperandToken right;

    private OperatorToken add;
    private OperatorToken divide;

    private OperatorToken x;
    private OperatorToken y;
    private OperatorToken z;
    private OperatorToken notX;

    @Rule
    public ExpectedException expectedException = ExpectedException.none ();

    @Before
    public void setUp ()
    {
        left = new OperandToken (2.0);
        right = new OperandToken (5.0);

        add = new OperatorToken (ADD_SYMBOL, Precedence.ADDITION_SUBTRACTION, addOperator);
        divide = new OperatorToken (DIVIDE_SYMBOL, Precedence.MULTIPLICATION_DIVISION, divideOperator);

        x = new OperatorToken (DUMMY_SYMBOL, Precedence.ADDITION_SUBTRACTION, dummyOperator);
        y = new OperatorToken (DUMMY_SYMBOL, Precedence.ADDITION_SUBTRACTION, dummyOperator);
        z = new OperatorToken (DUMMY_SYMBOL, Precedence.ADDITION_SUBTRACTION, dummyOperator);
        notX = new OperatorToken (MULTIPLICATION_SYMBOL, Precedence.MULTIPLICATION_DIVISION, dummyOperator);
    }

    @Test
    public void testNullSymbolThrowsException ()
    {
        expectedException.expect (IllegalArgumentException.class);
        new OperatorToken (null, Precedence.ADDITION_SUBTRACTION, dummyOperator);
        expectedException = ExpectedException.none ();
    }

    @Test
    public void testNullOperatorThrowsException ()
    {
        expectedException.expect (IllegalArgumentException.class);
        new OperatorToken (ADD_SYMBOL, Precedence.ADDITION_SUBTRACTION, null);
        expectedException = ExpectedException.none ();
    }

    @Test
    public void testNegativePrecedenceThrowsException ()
    {
        expectedException.expect (IllegalArgumentException.class);
        new OperatorToken (ADD_SYMBOL, -1, dummyOperator);
        expectedException = ExpectedException.none ();
    }

    @Test
    public void testGetTokenType ()
    {
        assertEquals (TokenType.OPERATOR, add.getTokenType ());
        assertEquals (TokenType.OPERATOR, divide.getTokenType ());
    }

    @Test
    public void testPerformOperation ()
    {
        OperandToken expected;
        OperandToken actual;

        expected = new OperandToken (left.getValue () + right.getValue ());
        actual = add.performOperation (left, right);
        assertEquals (expected, actual);

        expected = new OperandToken (left.getValue () / right.getValue ());
        actual = divide.performOperation (left, right);
        assertEquals (expected, actual);
    }

    @Test
    public void testGetPrecedence ()
    {
        List<OperatorToken> tokens = generatePrecedenceTokens ();

        for (int idx = 1; idx < tokens.size (); ++idx)
        {
            assertTrue (tokens.get (idx - 1).getPrecedence () < tokens.get (idx).getPrecedence ());
        }
    }

    @Test
    public void testToString ()
    {
        assertEquals (add.toString (), ADD_SYMBOL);
        assertEquals (divide.toString (), DIVIDE_SYMBOL);
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

    private static Operator addOperator = new Operator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return leftOperand + rightOperand;
        }
    };

    private static Operator divideOperator = new Operator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return leftOperand / rightOperand;
        }
    };

    private static Operator dummyOperator = new Operator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return 0;
        }
    };

    private static List<OperatorToken> generatePrecedenceTokens ()
    {
        List<OperatorToken> tokens = new ArrayList<> ();

        tokens.add (new OperatorToken ("lowest", Precedence.ADDITION_SUBTRACTION, dummyOperator));
        tokens.add (new OperatorToken ("lower", Precedence.MULTIPLICATION_DIVISION, dummyOperator));
        tokens.add (new OperatorToken ("middle", Precedence.UNARY, dummyOperator));
        tokens.add (new OperatorToken ("higher", Precedence.EXPONENTIATION, dummyOperator));
        tokens.add (new OperatorToken ("highest", Precedence.GROUPING_RIGHT, dummyOperator));
        tokens.add (new OperatorToken ("highestest", Precedence.GROUPING_LEFT, dummyOperator));

        return tokens;
    }
}

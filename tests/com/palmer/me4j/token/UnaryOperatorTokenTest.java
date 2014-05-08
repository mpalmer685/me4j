package com.palmer.me4j.token;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

/**
 * Created by Mike Palmer on 4/18/14.
 */
public class UnaryOperatorTokenTest
{
    private static final String NEGATE_SYMBOL = "-";
    private static final String DUMMY_SYMBOL  = ".";

    private OperandToken operand;

    private UnaryOperatorToken negate;

    private UnaryOperatorToken x;
    private UnaryOperatorToken y;
    private UnaryOperatorToken z;
    private UnaryOperatorToken notX;

    @Rule
    public ExpectedException expectedException = ExpectedException.none ();

    @Before
    public void setUp ()
    {
        operand = new OperandToken (2.0);

        negate = new UnaryOperatorToken (NEGATE_SYMBOL, Precedence.UNARY, Associativity.RIGHT_ASSOCIATIVE, negateOperator);

        x = new UnaryOperatorToken (DUMMY_SYMBOL, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        y = new UnaryOperatorToken (DUMMY_SYMBOL, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        z = new UnaryOperatorToken (DUMMY_SYMBOL, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        notX = new UnaryOperatorToken (NEGATE_SYMBOL, Precedence.MULTIPLICATION_DIVISION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
    }

    @Test
    public void testNullSymbolThrowsException ()
    {
        expectedException.expect (IllegalArgumentException.class);
        new UnaryOperatorToken (null, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        expectedException = ExpectedException.none ();
    }

    @Test
    public void testNullOperatorThrowsException ()
    {
        expectedException.expect (IllegalArgumentException.class);
        new UnaryOperatorToken (NEGATE_SYMBOL, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, null);
        expectedException = ExpectedException.none ();
    }

    @Test
    public void testNegativePrecedenceThrowsException ()
    {
        expectedException.expect (IllegalArgumentException.class);
        new UnaryOperatorToken (NEGATE_SYMBOL, -1, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        expectedException = ExpectedException.none ();
    }

    @Test
    public void testGetTokenType ()
    {
        assertEquals (TokenType.OPERATOR, negate.getTokenType ());
    }

    @Test
    public void testGetPrecedence ()
    {
        assertEquals (Precedence.UNARY, negate.getPrecedence ());
    }

    @Test
    public void testGetAssociativity ()
    {
        assertEquals (Associativity.RIGHT_ASSOCIATIVE, negate.getAssociativity ());
    }

    @Test
    public void testPerformOperation ()
    {
        OperandToken expected;
        OperandToken actual;

        expected = new OperandToken (-1.0 * operand.getValue ());
        actual = negate.performOperation (operand);
        assertEquals (expected, actual);
    }

    @Test
    public void testToString ()
    {
        assertEquals (negate.toString (), NEGATE_SYMBOL);
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
    public void testEqualsFailsWithDifferentSymbols ()
    {
        UnaryOperatorToken differentSymbolToken = new UnaryOperatorToken ("+", Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        assertFalse (x.equals (differentSymbolToken));
    }

    @Test
    public void testEqualsFailsWithDifferentPrecedence ()
    {
        UnaryOperatorToken differentSymbolToken = new UnaryOperatorToken ("-", Precedence.MULTIPLICATION_DIVISION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        assertFalse (x.equals (differentSymbolToken));
    }

    @Test
    public void testEqualsFailsWithDifferentAssociativity ()
    {
        UnaryOperatorToken differentSymbolToken = new UnaryOperatorToken ("-", Precedence.ADDITION_SUBTRACTION, Associativity.RIGHT_ASSOCIATIVE, dummyOperator);
        assertFalse (x.equals (differentSymbolToken));
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

    private static final UnaryOperator negateOperator = new UnaryOperator ()
    {
        @Override
        public double operate (double operand)
        {
            return -1 * operand;
        }
    };

    private static final UnaryOperator dummyOperator = new UnaryOperator ()
    {
        @Override
        public double operate (double operand)
        {
            return 0;
        }
    };
}

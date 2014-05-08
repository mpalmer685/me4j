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
public class BinaryOperatorTokenTest
{
    private static final String DIVIDE_SYMBOL         = "/";
    private static final String ADD_SYMBOL            = "+";
    private static final String DUMMY_SYMBOL          = ".";
    private static final String MULTIPLICATION_SYMBOL = "*";

    private OperandToken left;
    private OperandToken right;

    private BinaryOperatorToken add;
    private BinaryOperatorToken divide;

    private BinaryOperatorToken x;
    private BinaryOperatorToken y;
    private BinaryOperatorToken z;
    private BinaryOperatorToken notX;

    @Rule
    public ExpectedException expectedException = ExpectedException.none ();

    @Before
    public void setUp ()
    {
        left = new OperandToken (2.0);
        right = new OperandToken (5.0);

        add = new BinaryOperatorToken (ADD_SYMBOL, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, addOperator);
        divide = new BinaryOperatorToken (DIVIDE_SYMBOL, Precedence.MULTIPLICATION_DIVISION, Associativity.LEFT_ASSOCIATIVE, divideOperator);

        x = new BinaryOperatorToken (DUMMY_SYMBOL, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        y = new BinaryOperatorToken (DUMMY_SYMBOL, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        z = new BinaryOperatorToken (DUMMY_SYMBOL, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        notX = new BinaryOperatorToken (MULTIPLICATION_SYMBOL, Precedence.MULTIPLICATION_DIVISION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
    }

    @Test
    public void testNullSymbolThrowsException ()
    {
        expectedException.expect (IllegalArgumentException.class);
        new BinaryOperatorToken (null, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        expectedException = ExpectedException.none ();
    }

    @Test
    public void testNullOperatorThrowsException ()
    {
        expectedException.expect (IllegalArgumentException.class);
        new BinaryOperatorToken (ADD_SYMBOL, Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, null);
        expectedException = ExpectedException.none ();
    }

    @Test
    public void testNegativePrecedenceThrowsException ()
    {
        expectedException.expect (IllegalArgumentException.class);
        new BinaryOperatorToken (ADD_SYMBOL, -1, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
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
    public void testEqualsFailsWithDifferentSymbols ()
    {
        BinaryOperatorToken differentSymbolToken = new BinaryOperatorToken ("-", Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        assertFalse (x.equals (differentSymbolToken));
    }

    @Test
    public void testEqualsFailsWithDifferentPrecedence ()
    {
        BinaryOperatorToken differentSymbolToken = new BinaryOperatorToken ("+", Precedence.MULTIPLICATION_DIVISION, Associativity.LEFT_ASSOCIATIVE, dummyOperator);
        assertFalse (x.equals (differentSymbolToken));
    }

    @Test
    public void testEqualsFailsWithDifferentAssociativity ()
    {
        BinaryOperatorToken differentSymbolToken = new BinaryOperatorToken ("+", Precedence.ADDITION_SUBTRACTION, Associativity.RIGHT_ASSOCIATIVE, dummyOperator);
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

    private static final BinaryOperator addOperator = new BinaryOperator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return leftOperand + rightOperand;
        }
    };

    private static final BinaryOperator divideOperator = new BinaryOperator ()
    {
        @Override
        public double operate (double leftOperand, double rightOperand)
        {
            return leftOperand / rightOperand;
        }
    };

    private static final BinaryOperator dummyOperator = new BinaryOperator ()
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

        tokens.add (new BinaryOperatorToken ("lowest", Precedence.ADDITION_SUBTRACTION, Associativity.LEFT_ASSOCIATIVE, dummyOperator));
        tokens.add (new BinaryOperatorToken ("lower", Precedence.MULTIPLICATION_DIVISION, Associativity.LEFT_ASSOCIATIVE, dummyOperator));
        tokens.add (new BinaryOperatorToken ("middle", Precedence.UNARY, Associativity.LEFT_ASSOCIATIVE, dummyOperator));
        tokens.add (new BinaryOperatorToken ("higher", Precedence.EXPONENTIATION, Associativity.LEFT_ASSOCIATIVE, dummyOperator));
        tokens.add (new BinaryOperatorToken ("highest", Precedence.GROUPING_RIGHT, Associativity.LEFT_ASSOCIATIVE, dummyOperator));
        tokens.add (new BinaryOperatorToken ("highestest", Precedence.GROUPING_LEFT, Associativity.LEFT_ASSOCIATIVE, dummyOperator));

        return tokens;
    }
}

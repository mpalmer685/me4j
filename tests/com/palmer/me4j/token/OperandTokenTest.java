package com.palmer.me4j.token;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Mike Palmer on 3/27/14.
 */
public class OperandTokenTest
{
    private List<OperandToken> tokens;

    private OperandToken x;
    private OperandToken y;
    private OperandToken z;
    private OperandToken notX;

    @Before
    public void setUp ()
    {
        tokens = new ArrayList<> ();
        tokens.add (new OperandToken (-10.0));
        tokens.add (new OperandToken (0.0));
        tokens.add (new OperandToken (10.0));

        x = new OperandToken (1.0);
        y = new OperandToken (1.0);
        z = new OperandToken (1.0);
        notX = new OperandToken (0.0);
    }

    @Test
    public void testGetTokenType () throws Exception
    {
        for (OperandToken token : tokens)
        {
            assertEquals (TokenType.OPERAND, token.getTokenType ());
        }
    }

    @Test
    public void testToString () throws Exception
    {
        for (OperandToken token : tokens)
        {
            assertEquals (Double.toString (token.getValue ()), token.toString ());
        }
    }

    @Test
    public void testEqualsReflexivity () throws Exception
    {
        assertTrue (x.equals (x));
    }

    @Test
    public void testEqualsSymmetry () throws Exception
    {
        assertTrue (x.equals (y));
        assertTrue (y.equals (x));

        assertFalse (x.equals (notX));
        assertFalse (notX.equals (x));
    }

    @Test
    public void testEqualsTransitivity () throws Exception
    {
        assertTrue (x.equals (y));
        assertTrue (y.equals (z));
        assertTrue (x.equals (z));
    }

    @Test
    public void testEqualsConsistency () throws Exception
    {
        assertTrue (x.equals (y));
        assertTrue (x.equals (y));
        assertTrue (x.equals (y));

        assertFalse (x.equals (notX));
        assertFalse (x.equals (notX));
        assertFalse (x.equals (notX));
    }

    @Test
    public void testEqualsWithDifferentTypeIsFalse () throws Exception
    {
        assertFalse (x.equals ("x"));
    }

    @Test
    public void testEqualsNotNull () throws Exception
    {
        assertFalse (x.equals (null));
    }

    @Test
    public void testHashCodeConsistency () throws Exception
    {
        int hashCode = x.hashCode ();
        assertEquals (hashCode, x.hashCode ());
        assertEquals (hashCode, x.hashCode ());
    }

    @Test
    public void testHashCodeForEqualObjectsIsEqual () throws Exception
    {
        assertEquals (x.hashCode (), y.hashCode ());
    }

    @Test
    public void testHashCodeForUnequalObjectsIsUnequal () throws Exception
    {
        assertNotEquals (x.hashCode (), notX.hashCode ());
    }
}

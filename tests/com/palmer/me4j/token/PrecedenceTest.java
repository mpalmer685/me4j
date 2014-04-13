package com.palmer.me4j.token;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Mike Palmer on 3/28/14.
 */
public class PrecedenceTest
{
    @Test
    public void testPrivateConstructorThrowsException () throws Exception
    {
        Exception exception = null;

        Constructor<Precedence> constructor = Precedence.class.getDeclaredConstructor ();
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
}

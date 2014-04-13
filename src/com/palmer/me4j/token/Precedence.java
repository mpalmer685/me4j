package com.palmer.me4j.token;

/**
 * Contains values for declaring operator precedence. Higher integer values represent higher precedence when determining
 * order of operations.
 *
 * Created by Mike Palmer on 3/27/14.
 */
public final class Precedence
{
    public static final int ADDITION_SUBTRACTION    = 10;
    public static final int MULTIPLICATION_DIVISION = 20;
    public static final int UNARY                   = 30;
    public static final int EXPONENTIATION          = 40;
    public static final int GROUPING_LEFT           = Integer.MAX_VALUE;
    public static final int GROUPING_RIGHT          = Integer.MAX_VALUE - 1;

    private Precedence ()
    {
        throw new UnsupportedOperationException ();
    }
}

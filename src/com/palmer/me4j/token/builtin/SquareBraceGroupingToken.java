package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.GroupingToken;

/**
 * Created by Mike Palmer on 4/12/14.
 */
public class SquareBraceGroupingToken extends GroupingToken
{
    private static final String        LEFT_SYMBOL  = "[";
    private static final String        RIGHT_SYMBOL = "]";
    public static final  GroupingToken INSTANCE     = new SquareBraceGroupingToken ();

    private SquareBraceGroupingToken ()
    {
        super (LEFT_SYMBOL, RIGHT_SYMBOL);
    }
}

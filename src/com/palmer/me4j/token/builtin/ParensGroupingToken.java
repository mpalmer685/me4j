package com.palmer.me4j.token.builtin;

import com.palmer.me4j.token.GroupingToken;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class ParensGroupingToken extends GroupingToken
{
    public static final String        LEFT_SYMBOL  = "(";
    public static final String        RIGHT_SYMBOL = ")";
    public static final GroupingToken INSTANCE     = new ParensGroupingToken ();

    private ParensGroupingToken ()
    {
        super (LEFT_SYMBOL, RIGHT_SYMBOL);
    }
}

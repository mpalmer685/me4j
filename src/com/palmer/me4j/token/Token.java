package com.palmer.me4j.token;

/**
 * Defines behavior for all Tokens.
 *
 * Created by Mike Palmer on 3/27/14.
 */
public abstract class Token
{
    /**
     * Returns the {@link TokenType} of this Token instance.
     * @return the TokenType of this Token instance.
     */
    public abstract TokenType getTokenType ();
}

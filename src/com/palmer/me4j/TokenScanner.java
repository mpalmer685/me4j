package com.palmer.me4j;

import com.palmer.me4j.token.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Mike Palmer on 3/29/14.
 */
public class TokenScanner
{
    private final Scanner m_scanner;

    public static List<Token> tokenize (String input)
    {
        TokenScanner scanner = new TokenScanner (input);
        List<Token> tokens = new ArrayList<> ();

        while (scanner.hasNextToken ())
        {
            tokens.add (scanner.nextToken ());
        }

        return tokens;
    }

    private TokenScanner (String input)
    {
        m_scanner = new Scanner (input);
        m_scanner.useDelimiter (" ");
    }

    private boolean hasNextToken ()
    {
        return m_scanner.hasNext ();
    }

    private Token nextToken ()
    {
        String symbol = m_scanner.next ();
        return TokenFactory.buildToken (symbol);
    }
}

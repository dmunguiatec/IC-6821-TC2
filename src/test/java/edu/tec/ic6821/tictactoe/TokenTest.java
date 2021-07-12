package edu.tec.ic6821.tictactoe;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TokenTest {

    @Test
    public void tokenOToString() {
        // given
        Token token = Token.O;

        // when
        String textToken = token.toString();

        // then
        assertEquals("O", textToken);
    }

    @Test
    public void tokenXToString() {
        Token token = Token.X;
        String textToken = token.toString();
        assertEquals("X", textToken);
    }
}

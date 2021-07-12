package edu.tec.ic6821.tictactoe;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatrixBoardTest {

    @Test
    public void applyMoveWithValidPosition() {
        // given
        MatrixBoard board = new MatrixBoard();

        // when
        MoveStatus status = board.applyMove(Row.TOP, Column.CENTER, Token.X);

        // then
        assertEquals(MoveStatus.TOKEN_SET, status);
    }

    @Test
    public void applyMoveOnOccupiedBox() {
        // given
        MatrixBoard board = new MatrixBoard();
        board.applyMove(Row.TOP, Column.LEFT, Token.X);

        // when
        MoveStatus status = board.applyMove(Row.TOP, Column.LEFT, Token.X);

        // then
        assertEquals(MoveStatus.BOX_OCCUPIED, status);
    }

    @Test
    public void applyMoveTokenWinsHorizontal() {
        // given
        MatrixBoard board = new MatrixBoard();
        board.applyMove(Row.TOP, Column.LEFT, Token.X);
        board.applyMove(Row.TOP, Column.CENTER, Token.X);

        // when
        MoveStatus status = board.applyMove(Row.TOP, Column.RIGHT, Token.X);

        // then
        assertEquals(MoveStatus.X_WINS, status);
    }

    @Test
    public void applyMoveTokenWinsVertical() {
        // given
        MatrixBoard board = new MatrixBoard();
        board.applyMove(Row.TOP, Column.CENTER, Token.O);
        board.applyMove(Row.MIDDLE, Column.CENTER, Token.O);

        // when
        MoveStatus status = board.applyMove(Row.BOTTOM, Column.CENTER, Token.O);

        // then
        assertEquals(MoveStatus.O_WINS, status);
    }

    @Test
    public void applyMoveTokenWinsOnFirstDiagonal() {
        // given
        MatrixBoard board = new MatrixBoard();
        board.applyMove(Row.TOP, Column.LEFT, Token.X);
        board.applyMove(Row.MIDDLE, Column.CENTER, Token.X);

        // when
        MoveStatus status = board.applyMove(Row.BOTTOM, Column.RIGHT, Token.X);

        // then
        assertEquals(MoveStatus.X_WINS, status);
    }

    @Test
    public void applyMoveTokenWinsOnSecondDiagonal() {
        // given
        MatrixBoard board = new MatrixBoard();
        board.applyMove(Row.TOP, Column.RIGHT, Token.O);
        board.applyMove(Row.MIDDLE, Column.CENTER, Token.O);

        // when
        MoveStatus status = board.applyMove(Row.BOTTOM, Column.LEFT, Token.O);

        // then
        assertEquals(MoveStatus.O_WINS, status);
    }

    @Test
    public void applyMoveGameTied() {
        // given
        MatrixBoard board = new MatrixBoard();
        board.applyMove(Row.TOP, Column.LEFT, Token.X);
        board.applyMove(Row.BOTTOM, Column.CENTER, Token.X);
        board.applyMove(Row.BOTTOM, Column.RIGHT, Token.X);
        board.applyMove(Row.TOP, Column.RIGHT, Token.X);
        board.applyMove(Row.MIDDLE, Column.LEFT, Token.X);
        board.applyMove(Row.TOP, Column.CENTER, Token.O);
        board.applyMove(Row.MIDDLE, Column.CENTER, Token.O);
        board.applyMove(Row.MIDDLE, Column.RIGHT, Token.O);

        // when
        MoveStatus status = board.applyMove(Row.BOTTOM, Column.LEFT, Token.O);

        // then
        assertEquals(MoveStatus.GAME_TIED, status);
    }

    @Test
    public void emptyBoardToString() {
        // given
        MatrixBoard board = new MatrixBoard();
        String emptyBoardText =
              "+---+---+---+\n"
            + "|   |   |   |\n"
            + "+---+---+---+\n"
            + "|   |   |   |\n"
            + "+---+---+---+\n"
            + "|   |   |   |\n"
            + "+---+---+---+\n";

        // when
        String boardText = board.toString();

        // then
        assertEquals(emptyBoardText, boardText);
    }

    @Test
    public void randomBoardToString() {
        // given
        MatrixBoard board = new MatrixBoard();
        board.applyMove(Row.TOP, Column.LEFT, Token.X);
        board.applyMove(Row.MIDDLE, Column.CENTER, Token.X);
        board.applyMove(Row.BOTTOM, Column.RIGHT, Token.X);
        board.applyMove(Row.MIDDLE, Column.RIGHT, Token.O);
        board.applyMove(Row.BOTTOM, Column.CENTER, Token.O);

        String randomBoardText =
              "+---+---+---+\n"
            + "| X |   |   |\n"
            + "+---+---+---+\n"
            + "|   | X | O |\n"
            + "+---+---+---+\n"
            + "|   | O | X |\n"
            + "+---+---+---+\n";

        // when
        String boardText = board.toString();

        // then
        assertEquals(randomBoardText, boardText);
    }

    @Test
    public void fullBoardToString() {
        // given
        MatrixBoard board = new MatrixBoard();
        board.applyMove(Row.TOP, Column.LEFT, Token.X);
        board.applyMove(Row.MIDDLE, Column.CENTER, Token.X);
        board.applyMove(Row.BOTTOM, Column.RIGHT, Token.X);
        board.applyMove(Row.TOP, Column.RIGHT, Token.X);
        board.applyMove(Row.MIDDLE, Column.LEFT, Token.X);
        board.applyMove(Row.MIDDLE, Column.RIGHT, Token.O);
        board.applyMove(Row.BOTTOM, Column.CENTER, Token.O);
        board.applyMove(Row.TOP, Column.CENTER, Token.O);
        board.applyMove(Row.BOTTOM, Column.LEFT, Token.O);

        String fullBoardText =
              "+---+---+---+\n"
            + "| X | O | X |\n"
            + "+---+---+---+\n"
            + "| X | X | O |\n"
            + "+---+---+---+\n"
            + "| O | O | X |\n"
            + "+---+---+---+\n";

        // when
        String boardText = board.toString();

        // then
        assertEquals(fullBoardText, boardText);
    }
}

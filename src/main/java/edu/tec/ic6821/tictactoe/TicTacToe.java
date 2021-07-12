package edu.tec.ic6821.tictactoe;

public final class TicTacToe {

    private TicTacToe() { }

    public static void main(String[] args) {
        Board board = new MatrixBoard();
        board.applyMove(Row.TOP, Column.RIGHT, Token.X);
        board.applyMove(Row.MIDDLE, Column.LEFT, Token.O);
        board.applyMove(Row.BOTTOM, Column.RIGHT, Token.X);
        board.applyMove(Row.MIDDLE, Column.CENTER, Token.O);
        board.applyMove(Row.MIDDLE, Column.RIGHT, Token.X);
        System.out.println(board);

        board = new CountingBoard();
        board.applyMove(Row.TOP, Column.RIGHT, Token.X);
        board.applyMove(Row.MIDDLE, Column.LEFT, Token.O);
        board.applyMove(Row.BOTTOM, Column.RIGHT, Token.X);
        board.applyMove(Row.MIDDLE, Column.CENTER, Token.O);
        board.applyMove(Row.MIDDLE, Column.RIGHT, Token.X);
        System.out.println(board);
    }
}

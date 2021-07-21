package minesweeper.board;

public interface Board {

    String toString();
    void placeMines(int firstMoveX, int firstMoveY);
    boolean stepOn(int x, int y);
    boolean noMoreFieldsToReveal();
}

package minesweeper.board.field;

public class Field {

    private boolean revealed;
    private int adjMinesCount;
    private boolean isAMine;

    public Field() {
        revealed = false;
        adjMinesCount = 0;
        isAMine = false;
    }

    public void revealField() {
        revealed = true;
    }

    public void setAdjMinesCount(int adjMinesCount) {
        this.adjMinesCount = adjMinesCount;
    }

    public void setAsAMine() {
        isAMine = true;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public boolean isAMine() {
        return isAMine;
    }

    public int getAdjMinesCount() {
        return adjMinesCount;
    }

    public void increaseAdjMinesCount(){
        adjMinesCount++;
    }

    public String displayRevealed() {
        if (isAMine) {
            return "*";
        } else {
            return String.valueOf(getAdjMinesCount());
        }
    }

    public String display() {
        final String NOT_REVEALED_FIELD = "-";

        if (isRevealed()) {
            return String.valueOf(getAdjMinesCount());
        } else {
            return NOT_REVEALED_FIELD;
        }
    }

    public static boolean isValid(int x, int y, int boardSize) {
        return x >= 0 && x < boardSize
                && y >= 0 && y < boardSize;
    }
}

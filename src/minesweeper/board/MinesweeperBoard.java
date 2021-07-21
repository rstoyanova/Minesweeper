package minesweeper.board;

import minesweeper.board.field.Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinesweeperBoard implements Board {
    private static final int SIZE_BEGINNER = 9;
    private static final int MINES_COUNT_BEGINNER = 10;
    private static final int SIZE_INTERMEDIATE = 16;
    private static final int MINES_COUNT_INTERMEDIATE = 40;
    private static final int SIZE_ADVANCED = 24;
    private static final int MINES_COUNT_ADVANCED = 99;
    private static final String BEGINNER = "beginner";
    private static final String INTERMEDIATE = "intermediate";
    private static final String ADVANCED = "advanced";


    private int size;
    private int minesCount;
    private int revealedFieldsCount;

    private final List<List<Field>> board;

    public MinesweeperBoard(String levelOfDifficulty) {
        switch (levelOfDifficulty) {
            case BEGINNER -> {
                size = SIZE_BEGINNER;
                minesCount = MINES_COUNT_BEGINNER;
            }
            case INTERMEDIATE -> {
                size = SIZE_INTERMEDIATE;
                minesCount = MINES_COUNT_INTERMEDIATE;
            }
            case ADVANCED -> {
                size = SIZE_ADVANCED;
                minesCount = MINES_COUNT_ADVANCED;
            }
        }

        board = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < size; j++) {
                board.get(i).add(new Field());
            }
        }
    }

    public void placeMines(int firstMoveX, int firstMoveY) {
        int placedMines = 0;

        while (placedMines < minesCount) {
            //get random valid indexes
            Random random = new Random();
            int x = random.nextInt(size);
            int y = random.nextInt(size);

            //check if its the user's first move
            if (x == firstMoveX && y == firstMoveY) {
                continue;
            }

            //check if it's already a mine field
            if (board.get(x).get(y).isAMine()) {
                continue;
            } else {
                board.get(x).get(y).setAsAMine();
                board.get(x).get(y).setAdjMinesCount(-1);
                updateAdjFieldsForMinesCount(x, y);
            }
            placedMines++;
        }
    }

    // if user is stepped on a mine returns true so GAME OVER
    public boolean stepOn(int x, int y) {
        final int ZERO_MINES = 0;

        if (board.get(x).get(y).isAMine()) {
            return true;
        } else {
            if (board.get(x).get(y).getAdjMinesCount() != ZERO_MINES) {
                board.get(x).get(y).revealField();
                revealedFieldsCount++;
            } else {
                recursiveReveal(x, y);
            }
            return false;
        }
    }

    public String toString(boolean revealAll) {
        final String TAB = "\t";
        final String NL = System.lineSeparator();

        StringBuilder boardDisplay = new StringBuilder(getTopOfTheBoard() + NL);

        for (int i = 0; i < size; i++) {
            boardDisplay.append(TAB + TAB).append(i).append("  ");
            if (i<10) {
                boardDisplay.append(" ");
            }
            for (int j = 0; j < size; j++) {
                if (revealAll) {
                    boardDisplay.append(board.get(i).get(j).displayRevealed()).append("  ");
                } else {
                    boardDisplay.append(board.get(i).get(j).display()).append("  ");
                }
            }
            boardDisplay.append(NL);
        }
        return boardDisplay.toString();
    }

    public int getDimension() {
        return size;
    }

    public boolean noMoreFieldsToReveal() {
        return size * size - minesCount == revealedFieldsCount;
    }

    public static boolean isValidDifficultyLevel(String level) {
        return level.equals(BEGINNER)
                || level.equals(INTERMEDIATE)
                || level.equals(ADVANCED);
    }

    private String getTopOfTheBoard() {
        final String TOP_OF_BOARD_BEGINNER = "\t\t    0  1  2  3  4  5  6  7  8";
        final String TOP_OF_BOARD_INTERMEDIATE = TOP_OF_BOARD_BEGINNER + "  9  10 11 12 13 14 15";
        final String TOP_OF_BOARD_ADVANCED = TOP_OF_BOARD_INTERMEDIATE + " 16 17 18 19 20 21 22 23";

        return switch (size) {
            case SIZE_BEGINNER -> TOP_OF_BOARD_BEGINNER;
            case SIZE_INTERMEDIATE -> TOP_OF_BOARD_INTERMEDIATE;
            case SIZE_ADVANCED -> TOP_OF_BOARD_ADVANCED;
            default -> "";
        };
    }

    private void updateAdjFieldsForMinesCount(int x, int y) {
        // viewing only the 3x3 grid around the mine
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (Field.isValid(i, j, size)
                        && !board.get(i).get(j).isAMine()) {
                    board.get(i).get(j).increaseAdjMinesCount();
                }
            }
        }
    }

    private void recursiveReveal(int x, int y) {
        final int ZERO_MINES = 0;
        if (!Field.isValid(x, y, size)
                || board.get(x).get(y).isRevealed()
                || board.get(x).get(y).getAdjMinesCount() != ZERO_MINES) {
            return;
        }
        board.get(x).get(y).revealField();
        revealedFieldsCount++;
        recursiveReveal(x - 1, y);
        recursiveReveal(x, y - 1);
        recursiveReveal(x, y + 1);
        recursiveReveal(x + 1, y);
    }

}

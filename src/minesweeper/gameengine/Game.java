package minesweeper.gameengine;

import minesweeper.board.MinesweeperBoard;
import minesweeper.board.field.Field;

import java.util.Scanner;

public class Game {
    private MinesweeperBoard board;

    public void start() {
        setLevel();
        play();
    }

    private void setLevel() {
        final String SET_LEVEL_MESSAGE = "###################################################" + System.lineSeparator()
                + "                   MINESWEEPER" + System.lineSeparator()
                + "###################################################" + System.lineSeparator()
                + "           Hi there! :)" + System.lineSeparator()
                + "   Choose your level of difficulty:" + System.lineSeparator()
                + "  -Beginner" + System.lineSeparator()
                + "  -Intermediate" + System.lineSeparator()
                + "  -Advanced" + System.lineSeparator() + System.lineSeparator()
                + " ---->  ";
        System.out.print(SET_LEVEL_MESSAGE);
        Scanner sc = new Scanner(System.in);
        String level = sc.nextLine().strip().toLowerCase();

        while (!MinesweeperBoard.isValidDifficultyLevel(level)) {
            final String INVALID_LEVEL_MESSAGE = "     !Please enter a valid level of difficulty!"
                    + System.lineSeparator() + " ---->  ";
            System.out.print(INVALID_LEVEL_MESSAGE);
            sc = new Scanner(System.in);
            level = sc.nextLine().strip().toLowerCase();
        }
        board = new MinesweeperBoard(level);
    }

    private void play(){
        final String ENTER_MOVE = "  Enter your move [row, column]" + System.lineSeparator()
                + " ->  ";
        final String GAME_OVER_MESSAGE = "      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + System.lineSeparator()
                + "                  GAME OVER" + System.lineSeparator()
                + "      @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + System.lineSeparator();
        final String WINNER_MESSAGE = "     **********************************" + System.lineSeparator()
                + "     ************ YOU WON! ************" + System.lineSeparator()
                + "     **********************************" + System.lineSeparator();

        final boolean WITH_ALL_REVEALED_FIELDS = true;
        boolean firstMove = true;
        Scanner sc = new Scanner(System.in);
        int row, column;
        do {
            if(!firstMove) {
                System.out.println(board.toString(!WITH_ALL_REVEALED_FIELDS));
            }
            System.out.print(ENTER_MOVE);
            if (board.noMoreFieldsToReveal()) {
                System.out.println(WINNER_MESSAGE);
            }
            row = sc.nextInt();
            column = sc.nextInt();
            while (!Field.isValid(row, column, board.getDimension())) {
                final String INVALID_COORDINATES = "     !You entered wrong coordinates!" + System.lineSeparator();
                System.out.print(INVALID_COORDINATES + ENTER_MOVE);
                row = sc.nextInt();
                column = sc.nextInt();
            }
            if (firstMove) {
                board.placeMines(row,column);
                firstMove = false;
            }
        } while(!board.stepOn(row, column));

        System.out.println(board.toString(WITH_ALL_REVEALED_FIELDS));
        System.out.println(GAME_OVER_MESSAGE);
    }

    public static void main(String[] args) {
        Game renka = new Game();
        renka.start();
    }
}

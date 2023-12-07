package tests;

import src.*;

public class DiceRulesTest {
    static Backgammon game;
    static BackgammonBoard b;

    public static void main(String[] args) {
        //SameRollTest();
        //twoSingleMoveRolls();
        basicTest();
    }
    
    private static void sameRollTest() {
        b = new BackgammonBoard("3 0", "11 0 11 0");
        game = new Backgammon(b);
        game.playTest(3, 5, 1);
    }

    private static void twoSingleMoveRolls() {
        b = new BackgammonBoard("3 0 2 0", "5 0 5 0 7 0 7 0 8 0 8 0");
        game = new Backgammon(b);
        game.playTest(2, 3, 1);
    }

    private static void doublesSingleMoveTest() {
        b = new BackgammonBoard("3 0 3 0 3 0", "9 0 9 0");
        game = new Backgammon(b);
        game.playTest(5, 5, 1);
    }

    private static void basicTest() {
        b = new BackgammonBoard();
        game = new Backgammon(b);
        game.playTest(3, 5, 2);
    }
}

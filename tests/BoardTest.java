package tests;
import org.junit.jupiter.api.*;

import src.*;
public class BoardTest {
    BackgammonBoard b;

    @Test
    public void getHomeTest() {
        b = new BackgammonBoard();
        Assertions.assertFalse(b.movePiece(6, 0, 6));
        b.movePiece(11, 1, 6);
        b.movePiece(11, 1, 6);
        b.movePiece(5, 1, 7);
        b.movePiece(5, 1, 7);
        b.movePiece(1, 0, 8);
        b.movePiece(1, 0, 8);
        Assertions.assertFalse(b.movePiece(6, 0, 6));
        b.movePiece(0, 1, 8);
        b.movePiece(0, 1, 8);
        b.movePiece(0, 1, 8);
        b.movePiece(0, 1, 8);
        b.movePiece(0, 1, 8);
        b.movePiece(4, 0, 5);
        b.movePiece(4, 0, 5);
        Assertions.assertFalse(b.movePiece(6, 0, 6));
        b.movePiece(4, 0, 4);
        Assertions.assertTrue(b.movePiece(6, 0, 6));
        Assertions.assertEquals(b.getTeams()[0].getNumActivePieces(), 14);
    }

    @Test
    public void EatingTests() {
        b = new BackgammonBoard();
        b.movePiece(0, 1, 2);
        b.movePiece(0, 1, 2);
        b.movePiece(0, 1, 2);
        b.movePiece(0, 1, 2);

        b.switchTurn();
        b.movePiece(0, 0, 1);
        Assertions.assertEquals(1, b.getTeams()[0].getEatenPieces().numNodes());
        b.switchTurn();
        Assertions.assertFalse(b.movePiece(4, 0, 2));
        System.out.println(b.boardString());
    }
}

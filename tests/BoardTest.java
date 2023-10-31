package tests;
import org.junit.jupiter.api.*;

import src.*;
public class BoardTest {
    BackgammonBoard b;

    @Test
    public void getHomeTest() {
        b = new BackgammonBoard();
        System.out.println(b.movePiece(6, 0, 6));
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
        System.out.println(b.getTeams()[0].boardString());
        Assertions.assertTrue(b.movePiece(6, 0, 6));
        Assertions.assertEquals(b.getTeams()[0].getNumActivePieces(), 14);
        System.out.println(b.getTeams()[0].boardString());
    }
}

package src;

import java.util.Scanner;

public class Backgammon {

    private BackgammonBoard b;
    private Dice d;
    Scanner console;

    public Backgammon() {
        b = new BackgammonBoard();
        d = new Dice();
        console = new Scanner(System.in);
    }

    public void play() {

    }

    public void startGame() {
        System.out.println("Choose teams. When ready type anything and press enter");
        System.out.println(b.boardString());
        console.nextLine();
        int[] rolls = d.rolls(2);
        
        
    }

    private void gameLoop() {

    }

    private void turn(Team t) {

    }

    public BackgammonBoard getBoard() {
        return b;
    }
}
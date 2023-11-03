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
        System.out.println("Welcome to the game of Backgammon! I will not be restating the rules as I assume you know them if you're playing this. The code for this game was created from scratch by Adam Heaney using only Java and dedication. \n\nHow to play: When it is your turn to move, input coordinates as follows: \"x y\". Here is what the board looks like:\n\n" + b.boardString() + "\nThe two teams are B and W (black and white). Black's home is the top row and White's is the bottom. E is how many of your pieces are eaten and I is how many of your pieces are home. When ready, pick teams, type anything, and press enter to begin the game!");
        console.nextLine();
        int[] rolls = d.rolls(2);
        while(rolls[0] == rolls[1])
            rolls = d.rolls(2);
        if(rolls[0] > rolls[1]) 
            System.out.println(b.getTeams()[0].getTeamName() + " goes first!");
        else 
            System.out.println(b.getTeams()[1].getTeamName() + " goes first!");
        System.out.println("Input the coordinates of the piece you'd like to move!");
        
    }

    private void gameLoop() {

    }

    private void turn(Team t) {

    }

    public BackgammonBoard getBoard() {
        return b;
    }
}
package src;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        startGame();
    }

    public void startGame() {
        System.out.println("Welcome to the game of Backgammon! I will not be restating the rules as I assume you know them if you're playing this. The code for this game was created from scratch by Adam Heaney using only Java and dedication. \n\nHow to play: When it is your turn to move, input coordinates as follows: \"x y\". Here is what the board looks like:\n\n" + b.boardString() + "\nThe two teams are B and W (black and white). Black's home is the top row and White's is the bottom. E is how many of your pieces are eaten and I is how many of your pieces are home. When ready, pick teams, type anything, and press enter to begin the game!");
        console.nextLine();
        ArrayList<Integer> rolls = d.rolls(2);
        while(rolls.get(0) == rolls.get(1))
            rolls = d.rolls(2);
        if(rolls.get(0) > rolls.get(1)) { 
            System.out.println(b.getTeams()[0].getTeamName() + " goes first!");
            b.setTurn(0);
        }
        else { 
            System.out.println(b.getTeams()[1].getTeamName() + " goes first!");
            b.setTurn(1);
        }
        while(rolls.size() > 0) {
            System.out.println("Input the coordinates of the piece you'd like to move with the roll/s " + rolls.toString());
            String move = console.nextLine();

            //regex things
            String regex = "\\d+\\s\\d+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(move);

            //makes sure that the input is valid
            while(!matcher.find()) {
                System.out.println("Input must be a string of the format \"x y\"");
                move = console.nextLine();
                matcher = pattern.matcher(move);
            }
            System.out.println("Which roll would you like to use? " + rolls.toString());
            int chosenRoll = console.nextInt();
            console.nextLine();

            //makes sure input is valid
            while(!rolls.contains(chosenRoll)) {
                System.out.println("You didn't roll that number, choose again.");
                System.out.println("Which roll would you like to use? " +  rolls.toString());
                chosenRoll = console.nextInt();
                console.nextLine();
            }
            Boolean validMove = b.movePiece(Integer.parseInt(move.substring(0, move.indexOf(" "))), Integer.parseInt(move.substring(move.indexOf(" ") + 1)), chosenRoll);
            if(validMove) {
                rolls.remove(rolls.indexOf(chosenRoll));
            }
            else {
                System.out.println("\n-------------------------------------------------------------");
                System.out.println("Invalid move! Move a different piece or use a different dice.");
                System.out.println("---------------------------------------------------------------\n");
            }
            System.out.println(b.boardString());
        }
    }

    private void gameLoop() {

    }

    private void turn(Team t) {

    }

    public BackgammonBoard getBoard() {
        return b;
    }

    private Boolean hasValidMove(int movement) {
        return b.hasValidMove(movement);
    }
}
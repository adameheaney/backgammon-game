package src;

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
public class Backgammon {

    private BackgammonBoard b;
    private Dice d;
    Scanner console;

    private int[] rolls;
    private boolean hasMoveOne;
    private boolean hasMoveTwo;
    private ArrayList<String> rollOneValidMoves;
    private ArrayList<String> rollTwoValidMoves;
    private ArrayList<String> rollOneMoves;
    private ArrayList<String> rollTwoMoves;


    public Backgammon() {
        b = new BackgammonBoard();
        d = new Dice();
        console = new Scanner(System.in);
    }

    public void play() {
        startGame();
        while(b.getTeams()[0].getNumActivePieces() > 0 && b.getTeams()[1].getNumActivePieces() > 0)
            gameLoop();
    }

    public void startGame() {
        System.out.println("Welcome to the game of Backgammon! I will not be restating the rules as I assume you know them if you're playing this. The code for this game was created from scratch by Adam Heaney using only Java and dedication. \n\nHow to play: When it is your turn to move, input coordinates as follows: \"x y\". Here is what the board looks like:\n\n" + b.boardString() + "\nThe two teams are B and W (black and white). Black's home is the top row and White's is the bottom. E is how many of your pieces are eaten and I is how many of your pieces are home. When ready, pick teams and press enter to begin the game!");
        console.nextLine();
        rolls = d.rolls(2);
        while(rolls[0] == rolls[1])
            rolls = d.rolls(2);
        if(rolls[0] > rolls[1]) { 
            System.out.println(b.getTeams()[0].getTeamName() + " goes first!");
            b.setTurn(0);
        }
        else { 
            System.out.println(b.getTeams()[1].getTeamName() + " goes first!");
            b.setTurn(1);
        }
        while(!rollsIsEmpty()) {
            System.out.println("Input the coordinates of the piece you'd like to move with the roll/s " + rollsToString());
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
            System.out.println("Which roll would you like to use? " + rollsToString());
            int chosenRoll = console.nextInt();
            console.nextLine();

            //makes sure input is valid
            while(!hasRoll(chosenRoll)) {
                System.out.println("You didn't roll that number, choose again.");
                System.out.println("Which roll would you like to use? " +  rollsToString());
                chosenRoll = console.nextInt();
                console.nextLine();
            }
            Boolean validMove = b.movePiece(Integer.parseInt(move.substring(0, move.indexOf(" "))), Integer.parseInt(move.substring(move.indexOf(" ") + 1).strip()), chosenRoll);
            if(validMove) {
                rolls[indexOfRoll(chosenRoll)] = -1;
            }
            else {
                System.out.println("\n-------------------------------------------------------------");
                System.out.println("Invalid move! Move a different piece or use a different dice.");
                System.out.println("---------------------------------------------------------------\n");
            }
            System.out.println(b.boardString());
        }
        b.switchTurn();
    }

    private void gameLoop() {
        initalizeRolls();
        turnToMove(); 
        b.switchTurn();
    }

            

    public BackgammonBoard getBoard() {
        return b;
    }

    //compartmentalizing:

    private boolean rollsIsEmpty() {
        for(int i = 0; i < rolls.length; i++) {
            if(rolls[i] != -1) {
                return false;
            }
        }
        return true;
    }

    private boolean hasRoll(int num) {
        if(num == -1) return false;
        for(int i = 0; i < rolls.length; i++) {
            if(rolls[i] == num) {
                return true;
            }
        }
        return false;
    }

    private int indexOfRoll(int num) {
        if(num == -1) return -1;
        for(int i = 0; i < rolls.length; i++) {
            if(rolls[i] == num)
                return i;
        }
        return -1;
    }

    private String rollsToString() {
        String rollString = "[";
        for(int i = 0; i < rolls.length; i++) {
            if(rolls[i] != -1) {
                rollString += rolls[i] + ", ";
            }
        }
        if(rollString.length() > 1)
            return rollString.substring(0, rollString.length() - 2) + "]";
        else return "[]";
    }

    public void initalizeRolls() {
        rolls = d.rolls(2);
        System.out.println("It is " + b.getTeams()[b.getTurn()].getTeamName() + "'s turn.");
        System.out.println("You rolled " + rollsToString());
        if(rolls[0] == rolls[1]) {
            System.out.println("You rolled doubles!");
            rolls[2] = rolls[0];
            rolls[3] = rolls[0];
        }
        rollOneMoves = b.getValidMoves(rolls[0],rolls[1]);
        rollTwoMoves = b.getValidMoves(rolls[1],rolls[0]);

        //THESE FOLLOWING LINES DEAL WITH RULES REGARDING DICE AND ROLLS

        //Checks if the first dice has moves, if not it discards it
        System.out.println("ROLL ONE MOVES: " + rollOneMoves.toString());
        System.out.println("ROLL TWO MOVES: " + rollTwoMoves.toString());
        if(rollOneMoves.isEmpty() && rollTwoMoves.isEmpty()) {
            System.out.println("You have no rolls :(");
            for(int i = 0; i < rolls.length; i++) {
                rolls[i] = -1;
            }
        } 
        else if(rollOneMoves.isEmpty()) {
            System.out.println("No moves for the roll " + rolls[0]);
            rolls[0] = -1;
        }
        //checks if the second dice has moves, if not it discards it
        else if(rollTwoMoves.isEmpty()) {
            System.out.println("No moves for the roll " + rolls[1]);
            rolls[1] = -1;
        }

        
        
        //checks if both dice have the same single valid move, and discards the lower dice, as the higher dice must be played in this situation
        if((rollTwoMoves.size() == 1 && rollOneMoves.size() == 1) 
        && (rollOneMoves.get(0).equals(rollTwoMoves.get(0)))
        && b.getTeams()[b.getTurn()].numPiecesOnSpace(Integer.parseInt(rollOneMoves.get(0).substring(0, rollOneMoves.get(0).indexOf(" "))), Integer.parseInt(rollOneMoves.get(0).substring(rollOneMoves.get(0).indexOf(" ") + 1, rollOneMoves.get(0).lastIndexOf(" ")))) < 4
        && b.getTeams()[b.getTurn()].getEatenPieces() == null
        && rolls[2] != -1) {
            int[] move = {Integer.parseInt(rollOneMoves.get(0).substring(0, rollOneMoves.get(0).indexOf(" "))), Integer.parseInt(rollOneMoves.get(0).substring(rollOneMoves.get(0).indexOf(" ") + 1, rollOneMoves.get(0).lastIndexOf(" ")))};
            System.out.println("There are only " + b.getTeams()[b.getTurn()].numPiecesOnSpace(move[0], move[1]) + " pieces on space [" + move[0] + ", " + move[1] + "], which is the only space you can move.");
            for(int i = rolls.length; i > b.getTeams()[b.getTurn()].numPiecesOnSpace(move[0], move[1]); i--) {
                rolls[i - 1] = -1;
            }
        }
        else if((rollTwoMoves.size() == 1 && rollOneMoves.size() == 1) 
        && (rollOneMoves.get(0).equals(rollTwoMoves.get(0)))
        && b.getTeams()[b.getTurn()].numPiecesOnSpace(Integer.parseInt(rollOneMoves.get(0).substring(0, rollOneMoves.get(0).indexOf(" "))), Integer.parseInt(rollOneMoves.get(0).substring(rollOneMoves.get(0).indexOf(" ") + 1, rollOneMoves.get(0).lastIndexOf(" ")))) < 2
        && b.getTeams()[b.getTurn()].getEatenPieces() == null) {
            System.out.println("There is only one move possible move with both dice, which is the same piece, so the highest roll must be played.");
            if(rolls[0] > rolls[1]) {
                System.out.println("Removed roll " + rolls[1]);
                rolls[1] = -1;
            }
            else {
                System.out.println("Removed roll " + rolls[0]);
                rolls[0] = -1;
            }
        }

        //MANY LINES: These lines deal with the rule that if a dice only has a valid 
        //move following a move with the other dice, the other dice must be played 
        //in the way that enables that dice to be played.

        hasMoveOne = true;
        hasMoveTwo = true;

        //The following arraylists store the valid moves that each dice can play in the event
        //that the other dice can only be played after the that dice
        rollOneValidMoves = new ArrayList<>();
        rollTwoValidMoves = new ArrayList<>();

        if(rollOneMoves.size() > 0 && rollTwoMoves.size() > 0 && b.getTeams()[b.getTurn()].getEatenPieces() == null) {
            //checks if the first dice has a valid move on its own
            hasMoveOne = false;
            for(String move : rollOneMoves) {
                if(move.endsWith("1")) {
                    hasMoveOne = true;
                }
                else {
                    rollTwoValidMoves.add(move.substring(0, move.lastIndexOf(" ")));
                }
            }

            //checks if the second dice has a valid move on its own
            hasMoveTwo = false;
            for(String move : rollTwoMoves) {
                if(move.endsWith("1")) {
                    hasMoveTwo = true;
                }
                else {
                    rollOneValidMoves.add(move.substring(0, move.lastIndexOf(" ")));
                }
            }

            //if the first dice has a move on its own but the second doesn't...
            if(hasMoveOne && !hasMoveTwo) {
                System.out.println("You must move the piece/s at coords: " + rollOneValidMoves.toString());
            }

            //if the second dice has a move on its own but the first doesn't
            else if(!hasMoveOne && hasMoveTwo) {
                System.out.println("You must move the piece/s at coords: " + rollTwoValidMoves.toString());
            }
        }
    }

    public void turnToMove() {
        System.out.println(b.boardString());
        //the while loop for entering your moves
        while(!rollsIsEmpty()) {
            String move = "";
            
            //regex things
            String regex = "\\d+\\s\\d+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(move);
            int chosenRoll;

            //if both rolls have valid moves on their own
            if(hasMoveOne && hasMoveTwo) {
                //for when there is an eaten piece:
                if(b.getTeams()[b.getTurn()].getEatenPieces() != null) {
                    System.out.println("You must move your eaten piece.");
                    System.out.println("Which roll would you like to use? " + rollsToString());
                    chosenRoll = console.nextInt();
                    console.nextLine();
                }
                //next two elseifs check if there is only one possible single move for a piece
                else if(rollTwoMoves.size() == 1 && rolls[1] != -1) {
                    move = rollTwoMoves.get(0).substring(0, rollTwoMoves.get(0).lastIndexOf(" "));
                    System.out.println("Must move piece " + rollTwoMoves.get(0).substring(0, rollTwoMoves.get(0).lastIndexOf(" ")) + " with roll " + rolls[1]);
                    chosenRoll = rolls[1];
                    System.out.println("Press enter to continue");
                    console.nextLine();
                }
                else if(rollOneMoves.size() == 1 && rolls[0] != -1) {
                    System.out.println("Must move piece " + rollOneMoves.get(0).substring(0, rollOneMoves.get(0).lastIndexOf(" ")) + " with roll " + rolls[0]);
                    move = rollOneMoves.get(0).substring(0, rollOneMoves.get(0).lastIndexOf(" "));;
                    chosenRoll = rolls[0];
                    System.out.println("Press enter to continue");
                    console.nextLine();
                }
                else{
                    System.out.println("Input the coordinates of the piece you'd like to move with the roll/s " + rollsToString());
                    move = console.nextLine();
                    while(!matcher.find()) {
                        System.out.println("Input must be a string of the format \"x y\"");
                        move = console.nextLine();
                        matcher = pattern.matcher(move);
                    }
                    System.out.println("Which roll would you like to use? " + rollsToString());
                    chosenRoll = console.nextInt();
                    console.nextLine();
                }
            }

            //if move two requires move one to move
            else if(hasMoveOne && !hasMoveTwo) {
                while(!rollOneValidMoves.contains(move)) {
                    System.out.println("You must move a piece at cords: " + rollOneValidMoves.toString());
                    move = console.nextLine();
                }
                chosenRoll = rolls[0];
            }
            //if move one requires move two to move
            else {
                while(!rollTwoValidMoves.contains(move)) {
                    System.out.println("You must move a piece at cords: " + rollTwoValidMoves.toString());
                    move = console.nextLine();
                }
                chosenRoll = rolls[1];
            }

            //makes sure input is valid
            while(!hasRoll(chosenRoll)) {
                System.out.println("You didn't roll that number, choose again.");
                System.out.println("Which roll would you like to use? " +  rollsToString());
                chosenRoll = console.nextInt();
                console.nextLine();
            }
            Boolean validMove = false;
            if(b.getTeams()[b.getTurn()].getEatenPieces() == null) {
                validMove = b.movePiece(Integer.parseInt(move.substring(0, move.indexOf(" "))), Integer.parseInt(move.substring(move.indexOf(" ") + 1).strip()), chosenRoll);
                if(validMove) {
                    rolls[indexOfRoll(chosenRoll)] = -1;
                }
            }
            else {
                validMove = b.moveEatenPiece(chosenRoll);
                if(validMove) {
                    rolls[indexOfRoll(chosenRoll)] = -1;
                }
            }
            if(!validMove) {
                System.out.println("\n-------------------------------------------------------------");
                System.out.println("Invalid move! Move a different piece or use a different dice.");
                System.out.println("---------------------------------------------------------------\n");
            }
            System.out.println(b.boardString());
        }

    }

    //--------------------------------
    //Testing functions
    //--------------------------------

    public Backgammon(BackgammonBoard b) {
        this.b = b;
        d = new Dice();
        console = new Scanner(System.in);
    }

    public void playTest(int roll1, int roll2, int turns) {
        initalizeRollsTest(roll1, roll2);
        while(turns > 0){
            turnToMove();
            turns--;
        }
    }
    public void initalizeRollsTest(int roll1, int roll2) {
        if(roll1 != roll2)
            rolls = new int[] {roll1, roll2, -1, -1};
        else
            rolls = new int[] {roll1, roll1, roll1, roll1};
        System.out.println("It is " + b.getTeams()[b.getTurn()].getTeamName() + "'s turn.");
        System.out.println("You rolled " + rollsToString());
        if(rolls[0] == rolls[1]) {
            System.out.println("You rolled doubles!");
            rolls[2] = rolls[0];
            rolls[3] = rolls[0];
        }
        System.out.println(b.boardString());
        ArrayList<int[]> allMoves = new ArrayList<>(b.getAllValidMoves(rolls));
        for(int[] list : allMoves) {
            System.out.println(Arrays.toString(list));
        }
        // rollOneMoves = b.getValidMoves(rolls[0],rolls[1]);
        // rollTwoMoves = b.getValidMoves(rolls[1],rolls[0]);

        //THESE FOLLOWING LINES DEAL WITH RULES REGARDING DICE AND ROLLS

        //Checks if the first dice has moves, if not it discards it
        System.out.println("ROLL ONE MOVES: " + rollOneMoves.toString());
        System.out.println("ROLL TWO MOVES: " + rollTwoMoves.toString());
        if(rollOneMoves.isEmpty() && rollTwoMoves.isEmpty()) {
            System.out.println("You have no rolls :(");
            for(int i = 0; i < rolls.length; i++) {
                rolls[i] = -1;
            }
        } 
        else if(rollOneMoves.isEmpty()) {
            System.out.println("No moves for the roll " + rolls[0]);
            rolls[0] = -1;
        }
        //checks if the second dice has moves, if not it discards it
        else if(rollTwoMoves.isEmpty()) {
            System.out.println("No moves for the roll " + rolls[1]);
            rolls[1] = -1;
        }

        
        
        //checks if both dice have the same single valid move, and discards the lower dice, as the higher dice must be played in this situation
        if((rollTwoMoves.size() == 1 && rollOneMoves.size() == 1) 
        && (rollOneMoves.get(0).equals(rollTwoMoves.get(0)))
        && b.getTeams()[b.getTurn()].numPiecesOnSpace(Integer.parseInt(rollOneMoves.get(0).substring(0, rollOneMoves.get(0).indexOf(" "))), Integer.parseInt(rollOneMoves.get(0).substring(rollOneMoves.get(0).indexOf(" ") + 1, rollOneMoves.get(0).lastIndexOf(" ")))) < 4
        && b.getTeams()[b.getTurn()].getEatenPieces() == null
        && rolls[2] != -1) {
            int[] move = {Integer.parseInt(rollOneMoves.get(0).substring(0, rollOneMoves.get(0).indexOf(" "))), Integer.parseInt(rollOneMoves.get(0).substring(rollOneMoves.get(0).indexOf(" ") + 1, rollOneMoves.get(0).lastIndexOf(" ")))};
            int numPiecesOnSpace = b.getTeams()[b.getTurn()].numPiecesOnSpace(move[0], move[1]);
            System.out.println("There are only " + numPiecesOnSpace + " pieces on space [" + move[0] + ", " + move[1] + "], which is the only space you can move.");
            System.out.println(rolls.length);
            for(int i = rolls.length; i > (4 - numPiecesOnSpace); i--) {
                rolls[i - 1] = -1;
            }
        }
        else if((rollTwoMoves.size() == 1 && rollOneMoves.size() == 1) 
        && (rollOneMoves.get(0).equals(rollTwoMoves.get(0)))
        && b.getTeams()[b.getTurn()].numPiecesOnSpace(Integer.parseInt(rollOneMoves.get(0).substring(0, rollOneMoves.get(0).indexOf(" "))), Integer.parseInt(rollOneMoves.get(0).substring(rollOneMoves.get(0).indexOf(" ") + 1, rollOneMoves.get(0).lastIndexOf(" ")))) < 2
        && b.getTeams()[b.getTurn()].getEatenPieces() == null) {
            System.out.println("There is only one move possible move with both dice, which is the same piece, so the highest roll must be played.");
            if(rolls[0] > rolls[1]) {
                System.out.println("Removed roll " + rolls[1]);
                rolls[1] = -1;
            }
            else {
                System.out.println("Removed roll " + rolls[0]);
                rolls[0] = -1;
            }
        }

        //MANY LINES: These lines deal with the rule that if a dice only has a valid 
        //move following a move with the other dice, the other dice must be played 
        //in the way that enables that dice to be played.

        hasMoveOne = true;
        hasMoveTwo = true;

        //The following arraylists store the valid moves that each dice can play in the event
        //that the other dice can only be played after the that dice
        rollOneValidMoves = new ArrayList<>();
        rollTwoValidMoves = new ArrayList<>();

        if(rollOneMoves.size() > 0 && rollTwoMoves.size() > 0 && b.getTeams()[b.getTurn()].getEatenPieces() == null) {
            //checks if the first dice has a valid move on its own
            hasMoveOne = false;
            for(String move : rollOneMoves) {
                if(move.endsWith("1")) {
                    hasMoveOne = true;
                }
                else {
                    rollTwoValidMoves.add(move.substring(0, move.lastIndexOf(" ")));
                }
            }

            //checks if the second dice has a valid move on its own
            hasMoveTwo = false;
            for(String move : rollTwoMoves) {
                if(move.endsWith("1")) {
                    hasMoveTwo = true;
                }
                else {
                    rollOneValidMoves.add(move.substring(0, move.lastIndexOf(" ")));
                }
            }

            //if the first dice has a move on its own but the second doesn't...
            if(hasMoveOne && !hasMoveTwo) {
                System.out.println("You must move the piece/s at coords: " + rollOneValidMoves.toString());
            }

            //if the second dice has a move on its own but the first doesn't
            else if(!hasMoveOne && hasMoveTwo) {
                System.out.println("You must move the piece/s at coords: " + rollTwoValidMoves.toString());
            }
        }
    }
}
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

    public void turnToMove() {
        rolls = d.rolls(2);
        System.out.println("It is " + b.getTeams()[b.getTurn()].getTeamName() + "'s turn.");
        System.out.println("You rolled " + rollsToString());
        if(rolls[0] == rolls[1]) {
            System.out.println("You rolled doubles!");
            rolls[2] = rolls[0];
            rolls[3] = rolls[0];
        }
        System.out.println(b.boardString());
        ArrayList<int[]> allMoves = new ArrayList<>(b.getAllValidMoves(rolls));
        getValidMoves(allMoves);
        //the while loop for entering your moves
        while(!allMoves.isEmpty()) {
            // for(int[] list : allMoves) {
            //     System.out.println(Arrays.toString(list));
            // }
            int[] move = new int[3];
            System.out.println("Input your desired move. You have the rolls " + rollsToString());
            String moveString = console.nextLine().strip();
            int i = 0;
            // Regex tings
            String regexPattern = "\\d{1,}\\s{1}\\d{1,}\\s{1}\\d{1,}";
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(moveString);
            while(matcher.matches() && moveString != "") {
                if(moveString.indexOf(" ") > 0){
                    move[i] = Integer.parseInt(moveString.substring(0, moveString.indexOf(" ")));
                    moveString = moveString.substring(moveString.indexOf(" ") + 1);
                }
                else {
                    move[i] = Integer.parseInt(moveString.substring(0));
                    moveString = "";
                }
                i++;
            }
            while(!matcher.matches() || !hasMove(move, allMoves)) {
                System.out.println("##########################\nINVALID INPUT\n##########################\nRemember, the format is \"roll X Y\" and don't forget to take a better look at the board. You have the rolls " + rollsToString());
                moveString = console.nextLine().strip();
                matcher = pattern.matcher(moveString);
                i = 0;
                while(moveString != "") {
                    if(moveString.indexOf(" ") > 0){
                        move[i] = Integer.parseInt(moveString.substring(0, moveString.indexOf(" ")));
                        moveString = moveString.substring(moveString.indexOf(" ") + 1);
                    }
                    else {
                        move[i] = Integer.parseInt(moveString.substring(0));
                        moveString = "";
                    }
                    i++;
                }
            }
            rolls[indexOfRoll(move[0])] = -1;
            b.movePiece(move[1], move[2], move[0]);
            allMoves = new ArrayList<>(b.getAllValidMoves(rolls));
            getValidMoves(allMoves);
            System.out.println(b.boardString());
        }
    }
    private void getValidMoves(ArrayList<int[]> allMoves) {
        if(b.getTeams()[b.getTurn()].getEatenPieces() == null){
            if(allMoves.isEmpty()) {
                System.out.println("You have no valid moves :(");
                for(int i = 0; i < rolls.length; i++) {
                    rolls[i] = -1;
                }
                return;
            } 
            else {
                for(int r = 0; r < rolls.length; r++) {
                    if(rolls[r] == -1) continue;
                    boolean hasMove = false;
                    for(int i = 0; i < allMoves.size(); i++) {
                        if(allMoves.get(i)[0] == rolls[r]) {
                            hasMove = true;
                        }
                    }
                    if(!hasMove) {
                        System.out.println("No moves for roll " + rolls[r] +". :( \nRemoved roll " + rolls[r]);
                        rolls[r] = -1;
                    }
                }
            }
            if(allMoves.size() <= 2 && b.getTeams()[b.getTurn()].numPiecesOnSpace(allMoves.get(0)[1], allMoves.get(0)[2]) < 2){
                //checks if both dice have the same single valid move, and discards the lower dice, as the higher dice must be played in this situation
                boolean onlyOneSpace = true;
                int[] firstMove = new int[] {allMoves.get(0)[1],allMoves.get(0)[2]};
                for(int i = 1; i < allMoves.size(); i++) {
                    int[] move = new int[] {allMoves.get(i)[1],allMoves.get(i)[2]};
                    if(!Arrays.equals(move, firstMove)) {
                        onlyOneSpace = false;
                    }
                }
                if(onlyOneSpace) {
                    int maxdex = 0;
                    for(int i = 1; i < allMoves.size(); i++) {
                        if(allMoves.get(i)[0] > allMoves.get(maxdex)[0]) {
                            maxdex = i;
                        }
                    } 
                    int[] temp = allMoves.get(maxdex);
                    allMoves.clear();
                    allMoves.add(temp);
                    for(int r = 0; r < rolls.length; r++) {
                        if(rolls[r] != allMoves.get(0)[0] && rolls[r] != -1) {
                            System.out.println("Removed roll " + rolls[r] + " since you can only play one square!");
                            rolls[r] = -1;
                        }
                    }
                }
            }

            //MANY LINES: These lines deal with the rule that if a dice only has a valid 
            //move following a move with the other dice, the other dice must be played 
            //in the way that enables that dice to be played.

            if(allMoves.size() > 0) {
                for(int r = 0; r < rolls.length; r++) {
                    boolean hasStarterMove = false;
                    boolean hasSecondaryMove = false;
                    if(rolls[r] == -1) continue;
                    for(int i = 0; i < allMoves.size(); i++) {
                        int[] move = allMoves.get(i);
                        if(move[0] == rolls[r] && move[3] == 1) {
                            hasStarterMove = true;
                        }
                        if(move[0] == rolls[r] && move[3] == 2) {
                            hasSecondaryMove = true;
                        }
                    }
                    if(hasSecondaryMove && !hasStarterMove) {
                        ArrayList<int[]> temp = new ArrayList<>();
                        for(int i = 0; i < allMoves.size(); i++) {
                            int[] move = allMoves.get(i);
                            int index = r == 1 ? 0 : 1;
                            if(move[0] == rolls[index] && move[3] == 1) {
                                temp.add(move);
                            }
                        }
                        allMoves.clear();
                        allMoves.addAll(temp);
                        break;
                    }
                }
            }
        }
        pruneAllMoves(allMoves);
    }
    private void pruneAllMoves(ArrayList<int[]> allMoves) {
        if(b.getTeams()[b.getTurn()].getEatenPieces() != null) {
            ArrayList<int[]> temp = new ArrayList<>();
            for(int r = 0; r < rolls.length; r++) {
                if(b.getTeams()[1 - b.getTurn()].numPiecesOnSpace(12 - rolls[r], b.getTeams()[1 - b.getTurn()].getHomeYPos()) < 2) {
                    temp.add(new int[] {rolls[r], 12 - rolls[r], b.getTeams()[1 - b.getTurn()].getHomeYPos()});
                }
            }
            allMoves.clear();
            allMoves.addAll(temp);
        }
        else {
            ArrayList<int[]> temp = new ArrayList<>();
            for(int i = 0; i < allMoves.size(); i++) {
                int[] move = allMoves.get(i);
                if(move[3] == 1)
                    temp.add(new int[] {move[0], move[1], move[2]});
            }
            allMoves.clear();
            allMoves.addAll(temp);
        }
    }

    private boolean hasMove(int[] move, ArrayList<int[]> allMoves) {
        for(int i = 0; i < allMoves.size(); i++) {
            System.out.println(Arrays.toString(allMoves.get(i)));
            if(Arrays.equals(move, allMoves.get(i))) return true;
        }
        return false;
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
        turnToMoveTest(roll1, roll2);
        b.switchTurn();
        turns--;
        while(turns > 0){
            turnToMove();
            b.switchTurn();
            turns--;
        }
    }
    public void turnToMoveTest(int roll1, int roll2) {
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
        getValidMoves(allMoves);
        //the while loop for entering your moves
        while(!allMoves.isEmpty()) {
            // for(int[] list : allMoves) {
            //     System.out.println(Arrays.toString(list));
            // }
            int[] move = new int[3];
            System.out.println("Input your desired move. You have the rolls " + rollsToString());
            String moveString = console.nextLine().strip();
            int i = 0;
            // Regex tings
            String regexPattern = "\\d{1,}\\s{1}\\d{1,}\\s{1}\\d{1,}";
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(moveString);
            while(matcher.matches() && moveString != "") {
                if(moveString.indexOf(" ") > 0){
                    move[i] = Integer.parseInt(moveString.substring(0, moveString.indexOf(" ")));
                    moveString = moveString.substring(moveString.indexOf(" ") + 1);
                }
                else {
                    move[i] = Integer.parseInt(moveString.substring(0));
                    moveString = "";
                }
                i++;
            }
            while(!matcher.matches() || !hasMove(move, allMoves)) {
                System.out.println("##########################\nINVALID INPUT\n##########################\nRemember, the format is \"roll X Y\" and don't forget to take a better look at the board. You have the rolls " + rollsToString());
                moveString = console.nextLine().strip();
                matcher = pattern.matcher(moveString);
                i = 0;
                while(moveString != "") {
                    if(moveString.indexOf(" ") > 0){
                        move[i] = Integer.parseInt(moveString.substring(0, moveString.indexOf(" ")));
                        moveString = moveString.substring(moveString.indexOf(" ") + 1);
                    }
                    else {
                        move[i] = Integer.parseInt(moveString.substring(0));
                        moveString = "";
                    }
                    i++;
                }
            }
            rolls[indexOfRoll(move[0])] = -1;
            b.movePiece(move[1], move[2], move[0]);
            allMoves = new ArrayList<>(b.getAllValidMoves(rolls));
            getValidMoves(allMoves);
            System.out.println(b.boardString());
        }
    }
}
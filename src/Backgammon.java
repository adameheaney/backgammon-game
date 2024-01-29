package src;

import java.util.Scanner;
import java.util.regex.Pattern;

import src.bot.BackgammonBot;

import java.util.regex.Matcher;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * @author Adam Heaney
 * @version 1.1
 * Finish date: 1.0: 12/7/23 | 1.1: 1/28/24
 * This class is the ultimate class that makes the game of backgammon the game of backgammon. This class stores a backgammon board and the dice,
 * it contains the methods for starting a game, and the logic for playing it through. As of now, it needs some testing, but I'm pretty sure it
 * works completely. The game logic functions as so: After getting the rolls, the game uses a method to get all possible moves. There are some
 * funky rules to backgammon, so there are instances where you need to get the rolls' second play (hard to explain) to make sure that the legal
 * move is played. So, after sifting through the rules and through the possible moves you can make, it comes out with a list of all possible
 * moves for that turn. Then, the user can only input the valid moves. This class is the comprehensive class of the program, where every other
 * class comes together to do their jobs-- BackgammonBoard, Team, Piece, Dice all come together to form this beautiful work of art.
 * 
 * Changelog: 1.1: Added bot functionality: play versus a bot, bot versus bot, or player versus player.
 */
public class Backgammon {

    /**
     * The backgammon board being used
     */
    private BackgammonBoard b;
    /**
     * the dice being used for generating the rolls
     */
    private Dice d;
    /**
     * scanner to get the user's moves
     */
    Scanner console;

    /**
     * The generated rolls; global variable to make it simpler for the extra methods pertaining to """rolls"""
     */
    private int[] rolls;

    /**
     * Constructs a new Backgammon game with a fresh board, dice, and scanner
     */
    public Backgammon() {
        b = new BackgammonBoard();
        d = new Dice();
        console = new Scanner(System.in);
    }

    /**
     * The method to call to start the game: it starts the game and then keeps the gameloop going until someone wins
     */
    public void play() {
        startGame();
        while(b.getTeams()[0].getNumActivePieces() > 0 && b.getTeams()[1].getNumActivePieces() > 0) {
            gameLoop();
            b.switchTurn();
        }
    }

    /**
     * This method is the method for the first turn of a game. It is a separate method due to the guaranteed simplicity of the first turn
     */
    private void startGame() {
        System.out.println("Welcome to the game of Backgammon! I will not be restating the rules as I assume you know them if you're playing this. The code for this game was created from scratch by Adam Heaney using only Java and dedication. \n\nHow to play: When it is your turn to move, input the roll and coordinates as follows: \"roll x y\". Here is what the board looks like:\n\n" + b.boardString() + "\nThe two teams are B and W (black and white). Black's home is the top row and White's is the bottom. E is how many of your pieces are eaten and I is how many of your pieces are home. When ready, pick teams and press enter to begin the game!");
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
            int[] move = new int[3];
            System.out.println("Input your desired move. You have the rolls " + rollsToString());
            String moveString = console.nextLine().strip();
            int i = 0;
            // Regex tings
            String regexPattern = "\\d{1,}\\s{1}\\d{1,}\\s{1}\\d{1,}";
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(moveString);
            while(matcher.matches() && moveString != "") {
                //parse through the string
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
            //checks if the input is valid
            while(!matcher.matches()) {
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
            Boolean validMove = b.movePiece(move[1], move[2], move[0]);
            if(validMove) {
                rolls[indexOfRoll(move[0])] = -1;
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

    //=========================================
    //          METHODS FOR ROLLS
    //=========================================

    /**
     * 
     * @return true if there are no more rolls to use
     */
    private boolean rollsIsEmpty() {
        for(int i = 0; i < rolls.length; i++) {
            if(rolls[i] != -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param num : number to get index of 
     * @return index of the number in the array
     */
    private int indexOfRoll(int num) {
        if(num == -1) return -1;
        for(int i = 0; i < rolls.length; i++) {
            if(rolls[i] == num)
                return i;
        }
        return -1;
    }

    /**
     * 
     * @return the current available rolls in string form
     */
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


    //========================================================
    //             METHOD FOR MOVEMENT
    //========================================================
    public void gameLoop() {
        //initialize the rolls
        rolls = d.rolls(2);
        System.out.println("It is " + b.getTeams()[b.getTurn()].getTeamName() + "'s turn.");
        System.out.println("You rolled " + rollsToString());
        if(rolls[0] == rolls[1]) {
            System.out.println("You rolled doubles!");
            rolls[2] = rolls[0];
            rolls[3] = rolls[0];
        }
        System.out.println(b.boardString());
        //gets all the possible moves with the rolls
        ArrayList<int[]> allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
        getValidMoves(allMoves);
        //the while loop for entering your moves
        while(!rollsIsEmpty() && !allMoves.isEmpty()) {
            // uncomment to print all the valid moves
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
                //parse through the string
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
            //checks if the input is valid
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
            //does the move
            rolls[indexOfRoll(move[0])] = -1;
            if(b.getTeams()[b.getTurn()].getEatenPieces() == null)
                b.movePiece(move[1], move[2], move[0]);
            else b.moveEatenPiece(move[0]);
            if(!rollsIsEmpty()) {
                allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
                getValidMoves(allMoves);
            }
            System.out.println(b.boardString());
        }
    }

    /**
     * Takes in a list of backgammon moves and sifts through them to return possible valid moves
     * @param allMoves
     */
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
            if(allMoves.size() <= 2 
            && b.getTeams()[b.getTurn()].numPiecesOnSpace(allMoves.get(0)[1], allMoves.get(0)[2]) < 2){
                //checks if both dice have the same single valid move, and discards the lower dice, as the higher dice must be played in this situation
                boolean onlyOneSpace = true;
                int[] firstMove = new int[] {allMoves.get(0)[1],allMoves.get(0)[2]};
                for(int i = 1; i < allMoves.size(); i++) {
                    int[] move = new int[] {allMoves.get(i)[1],allMoves.get(i)[2], allMoves.get(i)[3]};
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
                            if(move[0] == rolls[r] && move[3] == 2) {
                                temp.add(new int[] {rolls[index], move[1], move[2], 1});
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
    /**
     * prunes all of the valid moves to only include the roll, posx, and posy. Used in getValidMoves() method
     * @param allMoves
     */
    private void pruneAllMoves(ArrayList<int[]> allMoves) {
        if(b.getTeams()[b.getTurn()].getEatenPieces() != null) {
            ArrayList<int[]> temp = new ArrayList<>();
            for(int r = 0; r < rolls.length; r++) {
                if(rolls[r] != -1 && b.getTeams()[1 - b.getTurn()].numPiecesOnSpace(12 - rolls[r], b.getTeams()[1 - b.getTurn()].getHomeYPos()) < 2) {
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
                int[] tempAdd = new int[] {move[0], move[1], move[2]};
                if(move[3] == 1 && !intArrContains(temp, tempAdd))
                    temp.add(tempAdd);
            }
            allMoves.clear();
            allMoves.addAll(temp);
        }
    }

    private boolean intArrContains(ArrayList<int[]> list, int[] arr) {
        for(int i = 0; i < list.size(); i++) {
            int[] item = list.get(i);
            for(int j = 0; j < item.length; j++) {
                if(item[j] != arr[j]) {
                    break;
                }
                else if(j == item.length-1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * checks if allMoves has the move "move"
     * @param move
     * @param allMoves
     * @return
     */
    private boolean hasMove(int[] move, ArrayList<int[]> allMoves) {
        for(int i = 0; i < allMoves.size(); i++) {
            if(Arrays.equals(move, allMoves.get(i))) return true;
        }
        return false;
    }





    //--------------------------------------------------------------------------------------------------------------------
    //Methods to play with bots
    //--------------------------------------------------------------------------------------------------------------------

    /**
     * The method to call to start the game: it starts the game and then keeps the gameloop going until someone wins
     */
    public void playAgainstBot(BackgammonBot bot) {
        int turn = 1;
        System.out.println("---------------------------------------\n              TURN " + turn + "\n---------------------------------------");
        startBotGame(bot);
        bot.retrieveBoard(b);
        while(b.getTeams()[0].getNumActivePieces() > 0 && b.getTeams()[1].getNumActivePieces() > 0) {
            turn++;
            System.out.println("---------------------------------------\n              TURN " + turn + "\n---------------------------------------");
            botGameLoop(bot);
            b.switchTurn();
        }
    }

    public void botAgainstBot(BackgammonBot bot, BackgammonBot bot2) {
        printToFile("docs/game.txt");
        int turn = 1;
        System.out.println("---------------------------------------\n              TURN " + turn + "\n---------------------------------------");
        startBotGame(bot, bot2);
        bot.retrieveBoard(b);
        bot2.retrieveBoard(b);
        while(b.getTeams()[0].getNumActivePieces() > 0 && b.getTeams()[1].getNumActivePieces() > 0) {
            turn++;
            System.out.println("---------------------------------------\n              TURN " + turn + "\n---------------------------------------");
            botGameLoop(bot, bot2);
            b.switchTurn();
        }
    }

    private void startBotGame(BackgammonBot bot) {
        System.out.println("Welcome to the game of Backgammon! I will not be restating the rules as I assume you know them if you're playing this. The code for this game was created from scratch by Adam Heaney using only Java and dedication. \n\nHow to play: When it is your turn to move, input the roll and coordinates as follows: \"roll x y\". Here is what the board looks like:\n\n" + b.boardString() + "\nThe two teams are B and W (black and white). Black's home is the top row and White's is the bottom. E is how many of your pieces are eaten and I is how many of your pieces are home. When ready, pick teams and press enter to begin the game!");
        console.nextLine();
        rolls = d.rolls(2);
        
        while(rolls[0] == rolls[1])
            rolls = d.rolls(2);
        if(rolls[0] > rolls[1]) { 
            System.out.println(b.getTeams()[0].getTeamName() + " goes first!");
            System.out.println("You rolled " + rollsToString());
            b.setTurn(0);
        }
        else { 
            System.out.println(bot.getName() + " goes first!");
            System.out.println(bot.getName() + " rolled " + rollsToString());
            b.setTurn(1);
        }
        ArrayList<int[]> allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
        getValidMoves(allMoves);
        while(!rollsIsEmpty() && !allMoves.isEmpty()) {
            int[] move;
            if(b.getTurn() == 0)
                move = playerTurn(allMoves);
            else
                move = botTurn(bot, allMoves);
            //does the move
            rolls[indexOfRoll(move[0])] = -1;
            if(b.getTeams()[b.getTurn()].getEatenPieces() == null)
                b.movePiece(move[1], move[2], move[0]);
            else b.moveEatenPiece(move[0]);
            if(!rollsIsEmpty()) {
                allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
                getValidMoves(allMoves);
            }
            System.out.println(b.boardString());
        }
        b.switchTurn();
    }

    private void startBotGame(BackgammonBot bot, BackgammonBot bot2) {
        //System.out.println("Welcome to the game of Backgammon! I will not be restating the rules as I assume you know them if you're playing this. The code for this game was created from scratch by Adam Heaney using only Java and dedication. \n\nHow to play: When it is your turn to move, input the roll and coordinates as follows: \"roll x y\". Here is what the board looks like:\n\n" + b.boardString() + "\nThe two teams are B and W (black and white). Black's home is the top row and White's is the bottom. E is how many of your pieces are eaten and I is how many of your pieces are home. When ready, pick teams and press enter to begin the game!");
        //console.nextLine();
        rolls = d.rolls(2);
        
        while(rolls[0] == rolls[1])
            rolls = d.rolls(2);
        if(rolls[0] > rolls[1]) { 
            System.out.println(bot.getName() + " goes first!");
            System.out.println(bot.getName() + " rolled " + rollsToString());
            b.setTurn(0);
        }
        else { 
            System.out.println(bot2.getName() + " goes first!");
            System.out.println(bot2.getName() + " rolled " + rollsToString());
            b.setTurn(1);
        }
        ArrayList<int[]> allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
        getValidMoves(allMoves);
        while(!rollsIsEmpty() && !allMoves.isEmpty()) {
            int[] move;
            if(b.getTurn() == 0)
                move = botTurn(bot, allMoves);
            else
                move = botTurn(bot2, allMoves);
            //does the move
            rolls[indexOfRoll(move[0])] = -1;
            if(b.getTeams()[b.getTurn()].getEatenPieces() == null)
                b.movePiece(move[1], move[2], move[0]);
            else b.moveEatenPiece(move[0]);
            if(!rollsIsEmpty()) {
                allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
                getValidMoves(allMoves);
            }
            System.out.println(b.boardString());
        }
        b.switchTurn();
    }

    private void botGameLoop(BackgammonBot bot) {
        //initialize the rolls
        rolls = d.rolls(2);
        if(b.getTurn() == 0) {
            System.out.println("It is your turn.");
            System.out.println("You rolled " + rollsToString());
            if(rolls[0] == rolls[1]) {
                System.out.println("You rolled doubles!");
                rolls[2] = rolls[0];
                rolls[3] = rolls[0];
            }
        }
        else {
            System.out.println("It is " + bot.getName() + "'s your turn.");
            System.out.println(bot.getName() + " rolled " + rollsToString());
            if(rolls[0] == rolls[1]) {
                System.out.println(bot.getName() + " rolled doubles!");
                rolls[2] = rolls[0];
                rolls[3] = rolls[0];
            }
        }
        System.out.println(b.boardString());
        //gets all the possible moves with the rolls
        ArrayList<int[]> allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
        getValidMoves(allMoves);
        //the while loop for entering your moves
        while(!rollsIsEmpty() && !allMoves.isEmpty()) {
            int[] move;
            if(b.getTurn() == 0) {
                move = playerTurn(allMoves);
            }
            else {
                move = botTurn(bot, allMoves);
            }
            //does the move
            rolls[indexOfRoll(move[0])] = -1;
            if(b.getTeams()[b.getTurn()].getEatenPieces() == null)
                b.movePiece(move[1], move[2], move[0]);
            else b.moveEatenPiece(move[0]);
            if(!rollsIsEmpty()) {
                allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
                getValidMoves(allMoves);
            }
            System.out.println(b.boardString());
        }
    }

    private void botGameLoop(BackgammonBot bot, BackgammonBot bot2) {
        //initialize the rolls
        rolls = d.rolls(2);
        if(b.getTurn() == 0) {
            System.out.println("It is " + bot.getName() + "'s your turn.");
            System.out.println(bot.getName() + " rolled " + rollsToString());
            if(rolls[0] == rolls[1]) {
                System.out.println(bot.getName() + " rolled doubles!");
                rolls[2] = rolls[0];
                rolls[3] = rolls[0];
            }
        }
        else {
            System.out.println("It is " + bot2.getName() + "'s your turn.");
            System.out.println(bot2.getName() + " rolled " + rollsToString());
            if(rolls[0] == rolls[1]) {
                System.out.println(bot2.getName() + " rolled doubles!");
                rolls[2] = rolls[0];
                rolls[3] = rolls[0];
            }
        }
        System.out.println(b.boardString());
        //gets all the possible moves with the rolls
        ArrayList<int[]> allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
        getValidMoves(allMoves);
        //the while loop for entering your moves
        while(!rollsIsEmpty() && !allMoves.isEmpty()) {
            int[] move;
            if(b.getTurn() == 0) {
                move = botTurn(bot, allMoves);
            }
            else {
                move = botTurn(bot2, allMoves);
            }
            //does the move
            rolls[indexOfRoll(move[0])] = -1;
            if(b.getTeams()[b.getTurn()].getEatenPieces() == null)
                b.movePiece(move[1], move[2], move[0]);
            else b.moveEatenPiece(move[0]);
            if(!rollsIsEmpty()) {
                allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
                getValidMoves(allMoves);
            }
            System.out.println(b.boardString());
        }
    }
    
    private int[] botTurn(BackgammonBot bot, ArrayList<int[]> allMoves) {
        bot.evaluateMoves(allMoves);
        int[] botMove = bot.getMove();
        System.out.println("The bot played the piece at coordinates " + botMove[1] + " " + botMove[2] + " with roll " + botMove[0]);
        System.out.println(b.boardString());
        return botMove;
    }

    private int[] playerTurn(ArrayList<int[]> allMoves) {
        // uncomment to print all the valid moves
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
            //parse through the string
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
        //checks if the input is valid
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
        return move;
    }
    








    //--------------------------------
    //Testing functions
    //--------------------------------

    /**
     * creates a game with a custom board
     * @param b
     */
    public Backgammon(BackgammonBoard b) {
        this.b = b;
        d = new Dice();
        console = new Scanner(System.in);
    }

    /**
     * the testing version for playing
     * @param roll1
     * @param roll2
     * @param turns number of turns to play for
     */
    public void playTest(int roll1, int roll2, int turns) {
        turnToMoveTest(roll1, roll2);
        b.switchTurn();
        turns--;
        while(turns > 0){
            gameLoop();
            b.switchTurn();
            turns--;
        }
    }

    /**
     * turnToMove() but with custom rolls
     * @param roll1
     * @param roll2
     */
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
        ArrayList<int[]> allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
        getValidMoves(allMoves);
        //the while loop for entering your moves
        while(!rollsIsEmpty() && !allMoves.isEmpty()) {
            for(int[] list : allMoves) {
                System.out.println(Arrays.toString(list));
            }
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
            if(b.getTeams()[b.getTurn()].getEatenPieces() == null)
                b.movePiece(move[1], move[2], move[0]);
            else b.moveEatenPiece(move[0]);
            if(!rollsIsEmpty()) {
                allMoves = new ArrayList<>(b.getAllPossibleMoves(rolls));
                getValidMoves(allMoves);
            }
            System.out.println(b.boardString());
        }
    }

    //---------------------------------------------------
    //MISC
    //---------------------------------------------------

    private void printToFile(String fileName) {
        try {
            // Create a File object for the text file
            File file = new File(fileName);

            // Create a FileOutputStream to write to the file
            FileOutputStream fos = new FileOutputStream(file);

            // Create a PrintStream that points to the FileOutputStream
            PrintStream ps = new PrintStream(fos);

            // Save the current System.out before redirecting it
            PrintStream old = System.out;

            // Redirect System.out to the PrintStream
            System.setOut(ps);

            // Your output statements here will now be written to the file
            System.out.println("This output will be written to the file.");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
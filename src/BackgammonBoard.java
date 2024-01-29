package src;

import java.util.HashSet;

/**
 * @author Adam Heaney
 * This class represents a backgammon board. The board has two teams which store the pieces. It keeps track of the turn as well. It has
 * many methods for manipulating the pieces within the teams and other stuff. It also has a toString() method!
 */
public class BackgammonBoard {

    /**
     * The array that stores the teams. The size is 2.
     */
    private Team[] teams;

    /**
     * int that goes between 0 and 1 which represent which turn it is
     */
    private int turn = 0;

    /**
     * number of spaces in one row of the board
     */
    private final int NUM_SPACES = 12;
    /**
     * home position of team 0
     */
    private final int HOME_Y_POS_1 = 0;
    /**
     * home position of team 1
     */
    private final int HOME_Y_POS_2 = 1;

    /**
     * creates a board with the default setup
     */
    public BackgammonBoard() {
        //DO NOT CHANGE THESE NUMBERS AS THEY ARE WHAT SETS THE BOARD UP CORRECTLY AT THE START
        initializeBoard("0 1 0 1 0 1 0 1 0 1 11 1 11 1 4 0 4 0 4 0 6 0 6 0 6 0 6 0 6 0"
        , "0 0 0 0 0 0 0 0 0 0 11 0 11 0 4 1 4 1 4 1 6 1 6 1 6 1 6 1 6 1");
    }
    
    /**
     * creates a board with custom piece setups
     * @param team1Pieces
     * @param team2Pieces
     */
    public BackgammonBoard(String team1Pieces, String team2Pieces) {
        initializeBoard(team1Pieces, team2Pieces);
    }

    /**
     * initializes the board by creating the teams
     * @param team1Pieces
     * @param team2Pieces
     */
    private void initializeBoard(String team1Pieces, String team2Pieces) {
        //DO NOT CHANGE, THESE ARE THE INITIALIZING COORDS FOR THE PIECES
        teams = new Team[] {new Team(HOME_Y_POS_1, "W", team1Pieces, NUM_SPACES), 
                            new Team(HOME_Y_POS_2, "B", team2Pieces, NUM_SPACES)};
    }


    //---------------------------------
    //METHODS FOR MOVING PIECES
    //---------------------------------

    /**
     * moves a piece on posX and posY with the roll "movement"
     * @param startPosX
     * @param startPosY
     * @param movement
     * @return true if the piece was able to be moved
     */
    public boolean movePiece(int startPosX, int startPosY, int movement) {
        if(startPosX < 0 || startPosX > NUM_SPACES || startPosY < 0 || startPosY > 1) {
            return false;
        }
        if(teams[turn].getPieces()[startPosX][startPosY] == null) {
            return false;
        }
        Piece piece = teams[turn].getPieces()[startPosX][startPosY].getEnd().getPiece();
        int[] newPos = piece.calculateNewPos(movement, teams[turn]);
        if(teams[1 - turn].numPiecesOnSpace(newPos[0], newPos[1]) >= 2) {
            return false;
        }
        else if(teams[1 - turn].numPiecesOnSpace(newPos[0], newPos[1]) == 1) {
            teams[1 - turn].eatPiece(newPos);
            return teams[turn].movePiece(startPosX, startPosY, movement);
        }
        else if(teams[1 - turn].numPiecesOnSpace(newPos[0], newPos[1]) == 0) {
            return teams[turn].movePiece(startPosX, startPosY, movement);
        }
        return false;
    }

    /**
     * moves an eaten piece with the roll "movement"
     * @param movement
     * @return true if the piece was able to be moved
     */
    public boolean moveEatenPiece(int movement) {
        if(teams[turn].getEatenPieces() == null) return false;
        Piece piece = teams[turn].getEatenPieces().getEnd().getPiece();
        int[] newPos = piece.calculateNewPos(movement, teams[turn]);
        if(teams[1 - turn].numPiecesOnSpace(newPos[0], newPos[1]) >= 2) {
            return false;
        }
        else if(teams[1 - turn].numPiecesOnSpace(newPos[0], newPos[1]) == 1) {
            teams[1 - turn].eatPiece(newPos);
            return teams[turn].moveEatenPiece(movement);
        }
        else if(teams[1 - turn].numPiecesOnSpace(newPos[0], newPos[1]) == 0) {
            return teams[turn].moveEatenPiece(movement);
        }
        return false;
    }
    
    //----------------------------------------------------
    //METHODS FOR CHECKING VALID MOVES
    //----------------------------------------------------


    /**
     * Returns ALL valid moves with every dice in a list: The format of a move is [roll, posX, posY, which # move]
     * @param moveOne
     * @param moveTwo
     * @return
     */
    public HashSet<int[]> getAllPossibleMoves(int[] rolls) {
        HashSet<int[]> validMoves = new HashSet<>();
        //if(rolls[3] > -1)
        //    rolls = new int[] {rolls[0], -1, -1, -1};
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < NUM_SPACES; j++) {
                validMoves.addAll(validMovesForPlace(j, i, rolls, new int[] {j , i}, new boolean[4], new HashSet<int[]>()));
            }
        }
        return validMoves;
    }

    /**
     * A recursive method that returns the valid moves for a specific space for every roll in rolls[]
     * @param startPosX the posX of the space
     * @param startPosY the posY of the space
     * @param rolls the array of rolls
     * @param currPos for recursion: initiate with {startPosX, startPosY}
     * @param usedIndices for recursion: initiate with a new boolean[4]
     * @param validMoves for recursion: initiate with a new HashSet<int[]>
     * @return a HashSet<int[]> containing all possible moves
     */
    private HashSet<int[]> validMovesForPlace(int startPosX, int startPosY, int[] rolls, 
                                            int[] currPos, boolean[] usedIndices,
                                            HashSet<int[]> validMoves) {
        if(teams[turn].getPieces()[startPosX][startPosY] == null)
            return new HashSet<>();                        
        return checkMovePiece(startPosX, startPosY, rolls, currPos, usedIndices, validMoves);                        
    }
    /**
     * the method wrapped by validMovesForPlace()
     * DO NOT CALL IN CODE, CALL validMovesForPlace() INSTEAD
     */
    private HashSet<int[]> checkMovePiece(int startPosX, int startPosY, 
                                            int[] rolls, int[] currPos,
                                            boolean[] usedIndices,
                                            HashSet<int[]> validMoves) {
        
        for(int i = 0; i < rolls.length; i++) {
            if(rolls[i] == -1 || usedIndices[i]) {
                continue;
            }
            usedIndices[i] = true;
            int[] newPos = Piece.calculateNewPos(currPos[0], currPos[1], rolls[i], teams[turn]);
            if(teams[1 - turn].numPiecesOnSpace(newPos[0], newPos[1]) < 2) {
                if(teams[turn].checkMovePiece(currPos[0], currPos[1], rolls[i])) {
                    validMoves.add(new int[] {rolls[i], startPosX, startPosY, numTrue(usedIndices)});
                    checkMovePiece(startPosX, startPosY, rolls, newPos, usedIndices.clone(), validMoves);
                }
            }
            usedIndices[i] = false;
        }
        return validMoves;
    }

    /**
     * returns the number of true's in a boolean array
     * @param b
     * @return
     */
    private int numTrue(boolean[] b) {
        int numtrue = 0;
        for(int i = 0; i < b.length; i++) {
            if(b[i]) numtrue++;
        }
        return numtrue;
    }

    //-----------------------------------
    //MISCELLANEOUS METHODS
    //-----------------------------------
    
    /**
     * 
     * @return a string representation of the board
     */
    public String boardString() {
        String board = "";
        for(int i = 1; i >= 0; i--) {
            for(int j = 0; j < NUM_SPACES; j++) {
                if(teams[0].numPiecesOnSpace(j, i) > 0) {
                    board += teams[0].numPiecesOnSpace(j, i) + teams[0].getTeamName() + " ";
                }
                else if(teams[1].numPiecesOnSpace(j, i) > 0) {
                    board += teams[1].numPiecesOnSpace(j, i) + teams[1].getTeamName() + " ";
                }
                else {
                    board += "0  ";
                }
                if(j == NUM_SPACES / 2 - 1 || j == NUM_SPACES - 1) board += "| ";
                if(j == NUM_SPACES - 1) {
                    Team t = teams[i];
                    board += t.getTeamName() + " Home | ";
                    if(t.getEatenPieces() == null) board += "E: 0 ";
                    else board += "E: "+ t.getEatenPieces().numNodes() + " ";
                    if(t.getInactivePieces() == null) board += "I: 0 ";
                    else board += "I: "+ t.getInactivePieces().numNodes() + " ";
                    board += "\n";
                }
            }
        }
        return board;
    }

    /**
     * 
     * @return teams[] array
     */
    public Team[] getTeams() {
        return teams;
    }

    /**
     * switches turn from 0 to 1 // 1 to 0
     */
    public void switchTurn() {
        turn = 1 - turn;
    }
    
    /**
     * 
     * @return turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * sets turn 
     * @param turn
     */
    public void setTurn(int turn) {
        this.turn = turn;
    }


}

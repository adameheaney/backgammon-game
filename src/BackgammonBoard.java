package src;

import java.util.HashSet;

public class BackgammonBoard {

    private Team[] teams;
    private int turn = 0;

    private final int NUM_SPACES = 12;
    private final int HOME_Y_POS_1 = 0;
    private final int HOME_Y_POS_2 = 1;

    public BackgammonBoard() {
        initializeBoard("0 1 0 1 0 1 0 1 0 1 11 1 11 1 4 0 4 0 4 0 6 0 6 0 6 0 6 0 6 0"
        , "0 0 0 0 0 0 0 0 0 0 11 0 11 0 4 1 4 1 4 1 6 1 6 1 6 1 6 1 6 1");
    }
    
    public BackgammonBoard(String team1Pieces, String team2Pieces) {
        initializeBoard(team1Pieces, team2Pieces);
    }

    private void initializeBoard(String team1Pieces, String team2Pieces) {
        //DO NOT CHANGE, THESE ARE THE INITIALIZING COORDS FOR THE PIECES
        teams = new Team[] {new Team(HOME_Y_POS_1, "W", team1Pieces, NUM_SPACES), 
                            new Team(HOME_Y_POS_2, "B", team2Pieces, NUM_SPACES)};
    }


    //---------------------------------
    //METHODS FOR MOVING PIECES
    //---------------------------------
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
    public HashSet<int[]> getAllValidMoves(int[] rolls) {
        HashSet<int[]> validMoves = new HashSet<>();
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < NUM_SPACES; j++) {
                validMoves.addAll(validMovesForPlace(j, i, rolls, new int[] {j , i}, new boolean[4], new HashSet<int[]>()));
            }
        }
        return validMoves;
    }

    private HashSet<int[]> validMovesForPlace(int startPosX, int startPosY, int[] rolls, 
                                            int[] currPos, boolean[] usedIndices,
                                            HashSet<int[]> validMoves) {
        if(teams[turn].getPieces()[startPosX][startPosY] == null)
            return new HashSet<>();                        
        return checkMovePiece(startPosX, startPosY, rolls, currPos, usedIndices, validMoves);                        
    }
    //returns true if move one can be played after move two
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

    private int numTrue(boolean[] b) {
        int numtrue = 0;
        for(int i = 0; i < b.length; i++) {
            if(b[i]) numtrue++;
        }
        return numtrue;
    }

    public boolean checkMoveEatenPiece(int movement) {
        if(teams[turn].getEatenPieces() == null) return false;
        Piece piece = teams[turn].getEatenPieces().getEnd().getPiece();
        int[] newPos = piece.calculateNewPos(movement, teams[turn]);
        if(teams[1 - turn].numPiecesOnSpace(newPos[0], newPos[1]) >= 2) {
            return false;
        }
        else {
            return true;
        }
    }


    //-----------------------------------
    //MISCELLANEOUS METHODS
    //-----------------------------------
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

    public Team[] getTeams() {
        return teams;
    }

    public void switchTurn() {
        turn = 1 - turn;
    }
    
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }


}

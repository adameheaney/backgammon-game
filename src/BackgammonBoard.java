package src;

import java.util.ArrayList;

public class BackgammonBoard {

    private Team[] teams;
    private int turn = 0;

    private final int NUM_SPACES = 12;
    private final int HOME_Y_POS_1 = 0;
    private final int HOME_Y_POS_2 = 1;

    public BackgammonBoard() {
        initializeBoard();
    }

    private void initializeBoard() {
        //DO NOT CHANGE, THESE ARE THE INITIALIZING COORDS FOR THE PIECES
        String team1String = "0 1 0 1 0 1 0 1 0 1 11 1 11 1 4 0 4 0 4 0 6 0 6 0 6 0 6 0 6 0 ";
        String team2String = "0 0 0 0 0 0 0 0 0 0 11 0 11 0 4 1 4 1 4 1 6 1 6 1 6 1 6 1 6 1 ";
        teams = new Team[] {new Team(HOME_Y_POS_1, "W", team1String, NUM_SPACES), 
                            new Team(HOME_Y_POS_2, "B", team2String, NUM_SPACES)};
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
     * Returns the valid moves for moveOne, NOT for moveTwo
     * @param moveOne
     * @param moveTwo
     * @return
     */
    public ArrayList<String> getValidMoves(int moveOne, int moveTwo) {
        ArrayList<String> validMoves = new ArrayList<>();
        if(teams[turn].getEatenPieces() != null) {
            if(checkMoveEatenPiece(moveOne)) {
                validMoves.add("E");
                return validMoves;
            }
        }
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < NUM_SPACES; j++) {
                if(checkMovePiece(j, i, moveOne, moveTwo)){
                    validMoves.add(j + " " + i + " 2");
                }
                if(checkMovePiece(j, i, moveOne, 0)) {
                    validMoves.add(j + " " + i + " 1");
                }
            }
        }
        return validMoves;
    }

    private boolean checkMovePiece(int startPosX, int startPosY, int moveOne, int moveTwo) {
        if(teams[turn].getPieces()[startPosX][startPosY] == null)
            return false;
        Piece piece = teams[turn].getPieces()[startPosX][startPosY].getPiece();
        int[] moveTwoPos = null;
        int[] newPos;
        if(moveTwo != 0) {
            moveTwoPos = piece.calculateNewPos(moveTwo, teams[turn]);
            newPos = piece.calculateNewPos(moveTwoPos[0], moveTwoPos[1], moveOne, teams[turn]);
        }
        else {
            newPos = piece.calculateNewPos(startPosX, startPosY, moveOne, teams[turn]);
        }
        if(teams[1 - turn].numPiecesOnSpace(newPos[0], newPos[1]) >= 2 || (moveTwoPos != null && teams[1 - turn].numPiecesOnSpace(moveTwoPos[0], moveTwoPos[1]) >= 2)) {
            return false;
        }
        else if (moveTwo == 0){
            return teams[turn].checkMovePiece(startPosX, startPosY, moveOne);
        }
        else {
            return teams[turn].checkMovePiece(moveTwoPos[0], moveTwoPos[1], moveOne);
        }
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

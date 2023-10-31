package src;

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

    //TODO
    public boolean movePiece(int startPosX, int startPosY, int movement) {
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
    public int getNUM_SPACES() {
        return NUM_SPACES;
    }

    public Team[] getTeams() {
        return teams;
    }

    public void switchTurn() {
        turn = 1 - turn;
    }
    
}

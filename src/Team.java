package src;

public class Team {

    private final int HOME_Y_POS;
    private final String TEAM_NAME;
    private PieceNode[][] pieces;
    private PieceNode eatenPieces;
    private PieceNode inactivePieces;
    private int numActivePieces = 15;
    private int points;
    private boolean allPiecesInHome = false;
    private int numSpaces;

    public Team(int homeYPos, String teamName, 
    String piecePositions, int numSpaces) {
        HOME_Y_POS = homeYPos;
        TEAM_NAME = teamName;
        this.numSpaces = numSpaces;
        instantiatePieces(piecePositions, numSpaces);
    }

    private void instantiatePieces(String piecePositions, int numSpaces) {
        pieces = new PieceNode[numSpaces][2];
        while(!piecePositions.isBlank()) {
            int posX = Integer.parseInt(piecePositions.substring(0, piecePositions.indexOf(" ")));
            piecePositions = piecePositions.substring(piecePositions.indexOf(" ") + 1);
            int posY = Integer.parseInt(piecePositions.substring(0, piecePositions.indexOf(" ")));
            piecePositions = piecePositions.substring(piecePositions.indexOf(" ") + 1);
            PieceNode newNode = new PieceNode(new Piece(posX, posY));
            if(pieces[posX][posY] == null) {
                pieces[posX][posY] = newNode;
            }
            else {
                PieceNode currNode = pieces[posX][posY];
                while(currNode.getNext() != null) { 
                    currNode = currNode.getNext();
                }
                currNode.setNext(newNode);
            }
        }
    }

    public int getHomeYPos() {
        return HOME_Y_POS;
    }

    public String getTeamName() {
        return TEAM_NAME;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public String boardString() {
        String board = "";
        for(int i = 1; i >= 0; i--) {
            for(int j = 0; j < pieces.length; j++) {
                if(pieces[j][i] != null) {
                    int numPieces = 1;
                    PieceNode curr = pieces[j][i];
                    while(curr.getNext() != null) {
                        numPieces++;
                        curr = curr.getNext();
                    }
                    board += numPieces + " ";
                }
                else {
                    board += "0 ";
                }
                if(j == pieces.length - 1 && i == 1- HOME_Y_POS) {
                    if(eatenPieces != null) {
                        int eatenPieces = 1;
                        PieceNode eaten = this.eatenPieces;
                        while(eaten.getNext() != null) {
                            eatenPieces++;
                            eaten = eaten.getNext();
                        }
                        board += "E: " + eatenPieces + "\n";
                    }
                    board += "E: 0\n";
                }
                else if(j == pieces.length - 1 && i == HOME_Y_POS) {
                    if(inactivePieces != null) {
                        int inactivePieces = 1;
                        PieceNode inactive = this.inactivePieces;
                        while(inactive.getNext() != null) {
                            inactivePieces++;
                            inactive = inactive.getNext();
                        }
                        board += "I: " + inactivePieces + "\n";
                    }
                    board += "I: 0\n";
                }
            }
        }
        return board;
    }
    /*-----------------------------------------------
     *  Methods that manipulate or return Piece data
     ----------------------------------------------*/

    public int getNumSpaces() {
        return numSpaces;
    }
    public int getNumActivePieces() {
        return numActivePieces;
    }

    public PieceNode[][] getPieces() {
        return pieces;
    }

    public int numPiecesOnSpace(int posX, int posY) {
        if(pieces[posY][posX] == null)
            return 0;
        int num = 1;
        PieceNode curr = pieces[posY][posX];
        while(curr.getNext() != null) {
            num++;
            curr = curr.getNext();
        }
        return num;
    }
    //currently working on this
    public void movePiece(int startPosX, int startPosY, int movement) {
        if(pieces[startPosY][startPosX] == null)
            return;
        if(startPosY == HOME_Y_POS && startPosX + movement > numSpaces) {
            if(allPiecesInHome) {
                pieces[startPosX][startPosY].getPiece().move(movement, this);
            }
        }
        PieceNode curr = pieces[startPosY][startPosX];
        while(curr.getNext() != null) {
            curr = curr.getNext();
        }
        curr.getPiece().move(movement, this);
        if(!curr.getPiece().isInPlay()) {
            numActivePieces--;
        }
    }
}

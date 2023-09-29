package src;

public class Team {

    private final int HOME_Y_POS;
    private final String TEAM_NAME;
    private PieceNode[][] pieces;
    private int numActivePieces;
    BackgammonBoard b;

    public Team(int homeYPos, String teamName, 
    String piecePositions, int numSpaces, BackgammonBoard b) {
        HOME_Y_POS = homeYPos;
        TEAM_NAME = teamName;
        instantiatePieces(piecePositions, numSpaces);
        numActivePieces = 15;
        this.b = b;
    }

    private void instantiatePieces(String piecePositions, int numSpaces) {
        pieces = new PieceNode[2][numSpaces];
        while(!piecePositions.isBlank()) {
            int posX = Integer.parseInt(piecePositions.substring(0, piecePositions.indexOf(" ")));
            piecePositions = piecePositions.substring(piecePositions.indexOf(" ") + 1);
            int posY = Integer.parseInt(piecePositions.substring(0, piecePositions.indexOf(" ")));
            piecePositions = piecePositions.substring(piecePositions.indexOf(" ") + 1);
            PieceNode newNode = new PieceNode(new Piece(posX, posY));
            if(pieces[posY][posX] == null) {
                pieces[posY][posX] = newNode;
            }
            else {
                PieceNode currNode = pieces[posY][posX];
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

    public BackgammonBoard getBoard() {
        return b;
    }

    /*-----------------------------------------------
     *  Methods that manipulate or return Piece data
     ----------------------------------------------*/

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

    public void movePiece(int startPosX, int startPosY, int movement) {
        if(pieces[startPosY][startPosX] == null)
            return;
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

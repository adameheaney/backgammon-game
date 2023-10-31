package src;

public class Team {

    private PieceNode[][] pieces;
    private PieceNode eatenPieces;
    private PieceNode inactivePieces;
    private int numActivePieces = 15;
    private int points;

    private final int HOME_Y_POS;
    private final String TEAM_NAME;
    private final int numSpaces;

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
                    else
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
                    else
                    board += "I: 0\n";
                }
            }
        }
        return board;
    }
    
    public int getNumSpaces() {
        return numSpaces;
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

    public PieceNode getEatenPieces() {
        return eatenPieces;
    }

    public PieceNode getInactivePieces() {
        return inactivePieces;
    }

    public int numPiecesOnSpace(int posX, int posY) {
        if(pieces[posX][posY] == null)
            return 0;
        int num = 1;
        PieceNode curr = pieces[posX][posY];
        while(curr.getNext() != null) {
            num++;
            curr = curr.getNext();
        }
        return num;
    }

    public boolean movePiece(int startPosX, int startPosY, int movement) {
        /*in the event that you want to move a piece that is already in your home to complete the game, this checks if all of your pieces are home first before doing that. Pretty sure it works*/
        if(startPosY == HOME_Y_POS && startPosX + movement > numSpaces) {
            PieceNode piece = pieces[startPosX][startPosY].getEnd();
            if(allPiecesInHome()) {
                piece.getPiece().move(movement, this);
                if(inactivePieces == null) {
                    inactivePieces = pieces[startPosX][startPosY];
                }
                else {
                    inactivePieces.attach(eatenPieces);
                }
                if(piece == pieces[startPosX][startPosY]) {
                    pieces[startPosX][startPosY] = null;
                }
                else pieces[startPosX][startPosY].detach();
                numActivePieces--;
                return true;
            }
            else {
                return false;
            }
        }
        PieceNode piece = pieces[startPosX][startPosY].detach();
        if(piece == pieces[startPosX][startPosY]) {
                pieces[startPosX][startPosY] = null;
        }
        piece.getPiece().move(movement, this);
        if(pieces[piece.getPiece().getPosX()][piece.getPiece().getPosY()] == null) {
            pieces[piece.getPiece().getPosX()][piece.getPiece().getPosY()] = piece;
        }
        else pieces[piece.getPiece().getPosX()][piece.getPiece().getPosY()].attach(piece);
        return true;
    }

    public boolean moveEatenPiece(int movement) {
        if(eatenPieces == null) {
            return false;
        }
        PieceNode piece = eatenPieces.detach();
        if(piece == eatenPieces) eatenPieces = null;
        piece.getPiece().move(movement, this);
        if(pieces[piece.getPiece().getPosX()][piece.getPiece().getPosY()] == null)
            pieces[piece.getPiece().getPosX()][piece.getPiece().getPosY()] = piece;
        else
            pieces[piece.getPiece().getPosX()][piece.getPiece().getPosY()].attach(piece);
        return true;
    }

    private boolean allPiecesInHome() {
        int numPieces = 0;
        if(inactivePieces != null) 
            numPieces = inactivePieces.numNodes();  
        for(int i = 6; i < 12; i++) {
            if(pieces[i][HOME_Y_POS] != null) {
                numPieces += pieces[i][HOME_Y_POS].numNodes();
            }
        }
        return numPieces == 15;
    }
    

    public void eatPiece(int[] pos) {
        PieceNode piece = pieces[pos[0]][pos[1]].detach();
        if(pieces[pos[0]][pos[1]] == piece) {
            pieces[pos[0]][pos[1]] = null;
        }
        piece.getPiece().becomeEaten();
        if(eatenPieces != null) eatenPieces.attach(piece);
        else eatenPieces = piece;
    }
}

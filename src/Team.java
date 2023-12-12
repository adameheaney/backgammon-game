package src;

/**
 * @author Adam Heaney
 * This class represents a single team in a backgammon game. It stores the team's number of pieces, points, inactive pieces, eaten pieces,
 * pieces, home y position, and name.
 */
public class Team {

    /**
     * the pieces within the team placed on the board. The piece's positions dictate where it is placed in the array
     */
    private PieceNode[][] pieces;
    /**
     * chain of eaten pieces
     */
    private PieceNode eatenPieces;
    /**
     * chain of inactive pieces
     */
    private PieceNode inactivePieces;
    /**
     * number of active pieces on the board
     */
    private int numActivePieces;
    /**
     * number of points the team has (unimplemented)
     */
    private int points;
    /**
     * total number of pieces the team has
     */
    private int numPieces;

    /**
     * The team's home Y position: extremely important for board logic
     */
    private final int HOME_Y_POS;

    /**
     * the team's name
     */
    private final String TEAM_NAME;

    /**
     * number of spaces on the board's x axis
     */
    private final int numSpaces;

    /**
     * insantiates a new team with the home y position at <b>homeYPos</b>, a team name, pieces based on the inputted piecePositions string,
     * and the number of spaces.
     * @param homeYPos 0 or 1 
     * @param teamName preferably one letter
     * @param piecePositions Format: "x y E..." E = eaten piece, each "x y" pair = a new piece's coordinates. Example: "4 0 4 0 5 0 5 0 E E E" or "1 0 1 0 11 1 11 0"
     * @param numSpaces number of spaces on the board
     */
    public Team(int homeYPos, String teamName, 
    String piecePositions, int numSpaces) {
        HOME_Y_POS = homeYPos;
        TEAM_NAME = teamName;
        this.numSpaces = numSpaces;
        instantiatePieces(piecePositions, numSpaces);
    }

    /**
     * instantiates the pieces based on the piecePositions string
     * @param piecePositions
     * @param numSpaces
     */
    private void instantiatePieces(String piecePositions, int numSpaces) {
        pieces = new PieceNode[numSpaces][2];
        while(!piecePositions.isBlank()) {
            int posX;
            int posY;
            if(piecePositions.lastIndexOf(" ") != 1 && piecePositions.lastIndexOf(" ") != 2) {
                if(piecePositions.substring(0, 1).equals("E")) {
                    Piece newPiece = new Piece(0, 0);
                    newPiece.becomeEaten();
                    if(eatenPieces == null) {
                        eatenPieces = new PieceNode(newPiece);
                    }
                    else {
                        PieceNode currNode = eatenPieces;
                        while(currNode.getNext() != null) { 
                            currNode = currNode.getNext();
                        }
                        currNode.setNext(new PieceNode(newPiece));
                    }
                    if(piecePositions.length() > 1)
                        piecePositions = piecePositions.substring(2);
                    else piecePositions = "";
                    continue;
                }
                posX = Integer.parseInt(piecePositions.substring(0, piecePositions.indexOf(" ")));
                piecePositions = piecePositions.substring(piecePositions.indexOf(" ") + 1);
                posY = Integer.parseInt(piecePositions.substring(0, piecePositions.indexOf(" ")));
                piecePositions = piecePositions.substring(piecePositions.indexOf(" ") + 1);
            }
            else {
                if(piecePositions.substring(0, 1).equals("E")) {
                    Piece newPiece = new Piece(0, 0);
                    newPiece.becomeEaten();
                    if(eatenPieces == null) {
                        eatenPieces = new PieceNode(newPiece);
                    }
                    else {
                        PieceNode currNode = eatenPieces;
                        while(currNode.getNext() != null) { 
                            currNode = currNode.getNext();
                        }
                        currNode.setNext(new PieceNode(newPiece));
                    }
                    if(piecePositions.length() > 1)
                        piecePositions = piecePositions.substring(2);
                    else piecePositions = "";
                    continue;
                }
                posX = Integer.parseInt(piecePositions.substring(0, piecePositions.indexOf(" ")));
                piecePositions = piecePositions.substring(piecePositions.indexOf(" ") + 1);
                posY = Integer.parseInt(piecePositions.substring(0).strip());
                piecePositions = "";
            }
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
            numPieces++;
        }
        numActivePieces = numPieces;
    }

    /**
     * 
     * @return the HomeYPos
     */
    public int getHomeYPos() {
        return HOME_Y_POS;
    }

    /**
     * 
     * @return the team's name
     */
    public String getTeamName() {
        return TEAM_NAME;
    }
    /**
     * 
     * @return number of points the team has
     */
    public int getPoints() {
        return points;
    }

    /**
     * adds points to the team
     * @param points
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * 
     * @return the team's board as a string
     */
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
                if(j == pieces.length / 2 - 1) board += "| ";
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
    
    /**
     * 
     * @return number of spaces on the x axis
     */
    public int getNumSpaces() {
        return numSpaces;
    }


    /*-----------------------------------------------
     *  Methods that manipulate or return Piece data
     ----------------------------------------------*/

    /**
     * 
     * @return number of active pieces
     */
    public int getNumActivePieces() {
        return numActivePieces;
    }

    /**
     * 
     * @return the piece matrix
     */
    public PieceNode[][] getPieces() {
        return pieces;
    }

    /**
     * 
     * @return eatenPieces. It is a pieceNode that chains to all of the team's eaten pieces.
     */
    public PieceNode getEatenPieces() {
        return eatenPieces;
    }

    /**
     * 
     * @return inactivePieces. It is a pieceNode that chains to all of the team's inactive pieces.
     */
    public PieceNode getInactivePieces() {
        return inactivePieces;
    }

    /**
     * 
     * @param posX
     * @param posY
     * @return the number of pieces on the space at posX and posY
     */
    public int numPiecesOnSpace(int posX, int posY) {
        if(posX >= numSpaces || pieces[posX][posY] == null)
            return 0;
        int num = 1;
        PieceNode curr = pieces[posX][posY];
        while(curr.getNext() != null) {
            num++;
            curr = curr.getNext();
        }
        return num;
    }

    /**
     * moves a piece at (startPosX, startPosY) by (movement)
     * @param startPosX
     * @param startPosY
     * @param movement
     * @return true if the piece at (startPosX, startPosY) is able to move (movement)
     */
    public boolean movePiece(int startPosX, int startPosY, int movement) {
        if(eatenPieces != null) {
            return false;
        }
        /*in the event that you want to move a piece that is already in your home 
        to complete the game, this checks if all of your pieces are home first before 
        doing that. Pretty sure it works*/
        else if(startPosY == HOME_Y_POS && startPosX + movement >= numSpaces) {
            PieceNode piece = pieces[startPosX][startPosY].getEnd();
            if(allowedHome(startPosX, movement)) {
                piece.getPiece().move(movement, this);
                if(inactivePieces == null) {
                    inactivePieces = piece;
                }
                else {
                    inactivePieces.attach(piece);
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

    /**
     * checks if you can move piece at (startPosX, startPosY) with (movement)
     * @param startPosX
     * @param startPosY
     * @param movement
     * @return true if the piece can move
     */
    public boolean checkMovePiece(int startPosX,int startPosY, int movement) {
        if(eatenPieces != null) {
            return false;
        }
        else if(startPosY == HOME_Y_POS && startPosX + movement >= numSpaces) {
            if(allowedHome(startPosX, movement)) {
                return true;
            }
            else {
                return false;
            }
        }
        return true;
    }
    
    /**
     * moves an eaten piece based on (movement)
     * @param movement
     * @return true if the piece can move
     */
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

    /**
     * 
     * @return true if all of the team's pieces are in the home
     */
    private boolean allPiecesInHome() {
        int numPieces = 0;
        if(inactivePieces != null) 
            numPieces = inactivePieces.numNodes();  
        for(int i = numSpaces / 2; i < numSpaces; i++) {
            if(pieces[i][HOME_Y_POS] != null) {
                numPieces += pieces[i][HOME_Y_POS].numNodes();
            }
        }
        return numPieces == this.numPieces;
    }

    /**
     * determines of a piece at posX is allowed home based on (movement) and the team's pieces
     * @param posX
     * @param movement
     * @return
     */
    private boolean allowedHome(int posX, int movement) {
        if(!allPiecesInHome()) {
            return false;
        }
        if(movement + posX == numSpaces) {
            return true;
        }
        else if(movement + posX < numSpaces) {
            return false;
        }
        else if(movement + movement + posX > numSpaces) {
            for(int i = numSpaces / 2; i < posX; i++) {
                if(pieces[i][HOME_Y_POS] != null)
                    return false;
            }
        }
        return true;

    }
    
    /**
     * eats the piece at (pos)
     * @param pos
     */
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

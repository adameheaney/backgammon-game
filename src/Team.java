package src;

public class Team {

    private final int HOME_Y_POS;
    private final int TEAM;
    private final String TEAM_NAME;
    private PieceNode[][] pieces;

    public Team(int homeYPos, int team, String teamName, int teamNum, String piecePositions, int numSpaces) {
        HOME_Y_POS = homeYPos;
        this.TEAM = team;
        TEAM_NAME = teamName;
        instantiatePieces(teamNum, piecePositions, numSpaces);
    }

    private void instantiatePieces(int teamNum, String piecePositions, int numSpaces) {
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

    public int getTeamNum() {
        return TEAM;
    }

    public String getTeamName() {
        return TEAM_NAME;
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

    public movePiece(int startPosX, int startPosY, int newPosX, int newPosY) {
        
    }
}

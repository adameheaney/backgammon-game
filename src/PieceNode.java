package src;

public class PieceNode {
    
    private PieceNode next;
    private Piece piece;

    public PieceNode(Piece piece) {
        this.piece = piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setNext(PieceNode node) {
        next = node;
    }

    public PieceNode getNext() {
        return next;
    }
}

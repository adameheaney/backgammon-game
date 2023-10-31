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

    public void attach(PieceNode node) {
        PieceNode curr = this;
        while(curr.getNext() != null) {
            curr = curr.getNext();
        }
        curr.setNext(node);
    }

    public PieceNode detach() {
        PieceNode curr = this;
        while(curr.getNext() != null) {
            if(curr.getNext().getNext() != null)
                curr = curr.getNext();
            else {
                PieceNode end = curr.getNext();
                curr.setNext(null);
                return end;
            }
        }
        return curr;
    }

    public PieceNode getEnd() {
        PieceNode curr = this;
        while(curr.getNext() != null) {
            if(curr.getNext().getNext() != null)
                curr = curr.getNext();
            else {
                PieceNode end = curr.getNext();
                return end;
            }
        }
        return curr;
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

    public int numNodes() {
        PieceNode curr = this;
        int num = 1;
        while(curr.getNext() != null) {
            curr = curr.getNext();
            num++;
        }
        return num;
    }
}

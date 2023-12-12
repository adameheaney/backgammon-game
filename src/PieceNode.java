package src;

/**
 * @author Adam Heaney
 * The pieces in a team's pieces array are stored using LinkedLists, and this is a node that stores a piece.
 */
public class PieceNode {
    
    /**
     * the next piecenode
     */
    private PieceNode next;
    /**
     * the stored piece
     */
    private Piece piece;

    /**
     * instantiates a new piecenode using the given piece
     * @param piece
     */
    public PieceNode(Piece piece) {
        this.piece = piece;
    }

    /**
     * sets the piece of the node
     * @param piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * attaches a node to the end of the chain
     * @param node
     */
    public void attach(PieceNode node) {
        PieceNode curr = this;
        while(curr.getNext() != null) {
            curr = curr.getNext();
        }
        curr.setNext(node);
    }

    /**
     * detaches a node from the end of the chain
     * @return the detached node
     */
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

    /**
     * @return the last node in the chain
     */
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

    /**
     * 
     * @return the stored piece
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * sets the next node
     * @param node
     */
    public void setNext(PieceNode node) {
        next = node;
    }

    /**
     * 
     * @return the next node in the chain
     */
    public PieceNode getNext() {
        return next;
    }

    /**
     * 
     * @return the number of pieces in the chain
     */
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

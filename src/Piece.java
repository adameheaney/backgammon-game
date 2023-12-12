package src;

/**
 * @author Adam Heaney
 * This class represents a single piece in a game of backgammon. The piece contains its coordinates and if it is eaten or not. The piece
 * can be moved using many different methods, and can also calculate its future spot based on a roll. There is also a static method for
 * calculating the new position of a piece depending on a given space, team it is apart of, and the roll. The team is necessary info
 * for moving a piece since a piece's movement direction depends on where the team's home position is on the Y axis.
 */
public class Piece {

    /**
     * x pos of the piece
     */
    private int posX;
    /**
     * y pos of the piece
     */
    private int posY;
    /**
     * whether the piece is eaten or not
     */
    private boolean eaten = false;
    
    /**
     * instantiates a piece at posX and posY
     * @param posX
     * @param posY
     */
    public Piece(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * moves a piece by <b>movement</b> to a new position depending on the team's HomeYPos. Mutates the piece's position.
     * @param movement
     * @param t
     */
    public void move(int movement, Team t) {
        if(eaten) {
            putBackIn(12 - movement, t);
            return;
        }
        int[] newPos = new int[2];
        //clockwise movement
        if(t.getHomeYPos() == posY) {
            newPos[0] = movement + posX;
            newPos[1] = posY;
        }
        //counter clockwise movement
        else if(t.getHomeYPos() != posY) {
            if(posX - movement < 0) {
                newPos[0] = Math.abs(posX + 1 - movement);
                newPos[1] = 1 - posY;
            }
            else {
                newPos[0] = posX - movement;
                newPos[1] = posY;
            }
        }
        posX = newPos[0];
        posY = newPos[1];
    }

    /**
     * Calculates the new position for the piece based on the movement and team
     * @param movement
     * @param t
     * @return the new position {x, y}
     */
    public int[] calculateNewPos(int movement, Team t) {
        if(eaten) {
           return new int[] {12 - movement, 1-t.getHomeYPos()};
        }
        int[] newPos = new int[2];
        //clockwise movement
        if(t.getHomeYPos() == posY) {
            newPos[0] = movement + posX;
            newPos[1] = posY;
        }
        //counter clockwise movement
        else if(t.getHomeYPos() != posY) {
            if(posX - movement < 0) {
                newPos[0] = Math.abs(posX + 1 - movement);
                newPos[1] = 1 - posY;
            }
            else {
                newPos[0] = posX - movement;
                newPos[1] = posY;
            }
        }
        return newPos;
    }

    /**
     * calculates a new position for any piece at any position
     * @param posX
     * @param posY
     * @param movement
     * @param t
     * @return the new position {x, y}
     */
    public static int[] calculateNewPos(int posX, int posY, int movement, Team t) {
        int[] newPos = new int[2];
        //clockwise movement
        if(t.getHomeYPos() == posY) {
            newPos[0] = movement + posX;
            newPos[1] = posY;
        }
        //counter clockwise movement
        else if(t.getHomeYPos() != posY) {
            if(posX - movement < 0) {
                newPos[0] = Math.abs(posX + 1 - movement);
                newPos[1] = 1 - posY;
            }
            else {
                newPos[0] = posX - movement;
                newPos[1] = posY;
            }
        }
        return newPos;
    }

    /**
     * eats the piece // makes eaten=true
     */
    public void becomeEaten() {
        eaten = true;
    }

    /**
     * method that places an eaten piece back into the board
     * @param num
     * @param team
     */
    private void putBackIn(int num, Team team) {
        int[] newPos = {num, 1-team.getHomeYPos()};
        posX = newPos[0];
        posY = newPos[1];
        eaten = false;
    }

    /**
     * sets the position of the piece to the newPos
     * @param newPos
     */
    public void setPosition(int[] newPos) {
        posX = newPos[0];
        posY = newPos[1];
    }
    
    /**
     * @return returns the x position of the piece
     */
    public int getPosX() {
        return posX;
    }
    /**
     * @return returns the y position of the piece
     */
    public int getPosY() {
        return posY;
    }

    /**
     * 
     * @return if the piece is eaten
     */
    public boolean isEaten() {
        return eaten;
    }
}

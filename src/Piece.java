package src;

public class Piece {

    private int posX;
    private int posY;
    private boolean eaten = false;
    private boolean inPlay;
    private int numSpaces = 11;
    
    public Piece(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        inPlay = true;
    }

    public Piece(boolean inPlay) {
        this.inPlay = inPlay;
    }

    public int[] move(int movement, Team t) {
        if(eaten) {
            return putBackIn(movement + 5, t);
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
                newPos[0] = posY - movement;
                newPos[1] = posY;
            }
        }
        posX = newPos[0];
        posY = newPos[1];
        if(posX >= numSpaces) {
            inPlay = false;
        }
        return new int[] {posX, posY};
    }

    public void getEaten() {
        eaten = true;
    }

    private int[] putBackIn(int num, Team team) {
        int[] newPos = {1-team.getHomeYPos(), num};
        posX = newPos[0];
        posY = newPos[1];
        return newPos;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
    
    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean eaten) {
        this.eaten = eaten;
    }

    public boolean isInPlay() {
        return inPlay;
    }

    public void setInPlay(boolean inPlay) {
        this.inPlay = inPlay;
    }
}

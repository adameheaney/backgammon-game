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

    public void move(int movement, Team t) {
        if(eaten) {
            putBackIn(movement + 5, t);
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
        if(posX >= t.getNumSpaces()){
            inPlay = false;
        }
    }

    public int[] calculateNewPos(int movement, Team t) {
        if(eaten) {
           return new int[] {movement + 5, 1-t.getHomeYPos()};
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
        return newPos;
    }

    public void becomeEaten() {
        eaten = true;
    }

    private void putBackIn(int num, Team team) {
        int[] newPos = {num, 1-team.getHomeYPos()};
        posX = newPos[0];
        posY = newPos[1];
        eaten = false;
    }

    public void setPosition(int[] newPos) {
        posX = newPos[0];
        posY = newPos[1];
    }
    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
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

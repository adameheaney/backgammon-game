package src;

public class BackgammonBoard {

    private Team[] teams;
    private final int NUM_SPACES = 12;
    private Dice dice;

    public BackgammonBoard() {
        dice = new Dice();
        initializeBoard();
    }

    private void initializeBoard() {
        //DO NOT CHANGE, THESE ARE THE INITIALIZING COORDS FOR THE PIECES
        String team1String = "0 1 0 1 0 1 0 1 0 1 11 1 11 1 4 0 4 0 4 0 6 0 6 0 6 0 6 0 6 0 ";
        String team2String = "0 0 0 0 0 0 0 0 0 0 11 0 11 0 4 1 4 1 4 1 6 1 6 1 6 1 6 1 6 1 ";
        teams = new Team[] {new Team(0, "W", team1String, NUM_SPACES), 
                            new Team(1, "B", team2String, NUM_SPACES)};
    }

    public int[] roll() {
        int rollOne = dice.roll(1);
        int rollTwo = dice.roll(1);
        return new int[] {rollOne, rollTwo};
    }

    //TODO
    public boolean movePiece(int movement) {
        return false;
    }

    //TODO
    private boolean checkValidityOfPosition(int[] newPos, Team t) {
        return false;
    }

    public int getNUM_SPACES() {
        return NUM_SPACES;
    }

    public Team[] getTeams() {
        return teams;
    }
    
}

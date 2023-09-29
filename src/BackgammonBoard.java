package src;

public class BackgammonBoard {

    private Team[] teams;
    private int numSpaces;

    public BackgammonBoard(int numSpaces) {
        this.numSpaces = numSpaces;
        initializeBoard();
    }

    private void initializeBoard() {
        int team1 = 0;
        String team1String = "0 1 0 1 0 1 0 1 0 1 11 1 11 1 4 0 4 0 4 0 6 0 6 0 6 0 6 0 6 0";
        int team2 = 1;
        String team2String = "0 0 0 0 0 0 0 0 0 0 11 0 11 0 4 1 4 1 4 1 6 1 6 1 6 1 6 1 6 1";
        teams = new Team[] {new Team(team1, "Team 1", team1String, numSpaces, this), 
                            new Team(team2, "Team 2", team2String, numSpaces, this)};
    }

    //TODO
    private boolean checkValidityOfPosition(int[] newPos, Team t) {
        return false;
    }

    public int getNumSpaces() {
        return numSpaces;
    }

    public Team[] getTeams() {
        return teams;
    }
    
}

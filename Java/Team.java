package Java;

public class Team {

    private final int HOME_Y_POS;
    private final int TEAM;
    private final String TEAM_NAME;
    private Piece[] pieces;

    public Team(int homeYPos, int team, String teamName) {
        HOME_Y_POS = homeYPos;
        this.TEAM = team;
        TEAM_NAME = teamName;
        instantiatePieces();
    }

    private void instantiatePieces(int teamNum) {
        
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
}

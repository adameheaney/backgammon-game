package src;

public class BackgammonRunner {
    public static void main(String[] args) {
        Backgammon game = new Backgammon();
        BackgammonBoard b = game.getBoard();
        System.out.println(b.getTeams()[0].boardString());
        System.out.println();
        System.out.println(b.getTeams()[1].boardString());

        b.switchTurn();
        System.out.println(b.movePiece(0, 0, 2));
        System.out.println(b.movePiece(0, 0, 2));
        System.out.println(b.movePiece(0, 0, 2));
        System.out.println(b.movePiece(0, 0, 2));

        b.switchTurn();
        System.out.println(b.movePiece(0, 1, 1));
        System.out.println(b.getTeams()[0].boardString());
        System.out.println();
        System.out.println(b.getTeams()[1].boardString());

        b.switchTurn();
        System.out.println(b.moveEatenPiece(6));
        System.out.println(b.getTeams()[0].boardString());
        System.out.println();
        System.out.println(b.getTeams()[1].boardString());

        System.out.println(b.moveEatenPiece(2));
        System.out.println(b.getTeams()[0].boardString());
        System.out.println();
        System.out.println(b.getTeams()[1].boardString());
    }

}
package src;

public class Backgammon {
    public static void main(String[] args) {
        BackgammonBoard b = new BackgammonBoard();
        System.out.println(b.getTeams()[0].boardString());
        System.out.println(" \n ");
        System.out.println(b.getTeams()[1].boardString());
    }
}
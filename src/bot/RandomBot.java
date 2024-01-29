package src.bot;

import java.util.ArrayList;
import java.util.Arrays;

public class RandomBot extends BackgammonBot{


    public RandomBot() {
        super("RandomBot");
    }

    @Override
    public void evaluateMoves(ArrayList<int[]> possibleMoves) {
        // System.out.println("The bot has " + possibleMoves.size() + " possible moves.");
        // System.out.print("The bot's possible moves are: ");
        for(int i = 0; i < possibleMoves.size(); i++) {
            int[] move = possibleMoves.get(i);
            System.out.print("[x:" + move[1] + ", y:" + move[2] + ", r:" + move[0] + "]");
        }
        // System.out.println();
        move = possibleMoves.get((int)(Math.random()*possibleMoves.size()));
        // System.out.println(Arrays.toString(move));
    }
    
}

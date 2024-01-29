package src.bot;

import java.util.ArrayList;
import java.util.Arrays;

public class RandomBot extends BackgammonBot{


    public RandomBot() {
        super("RandomBot");
    }

    @Override
    public void evaluateMoves(ArrayList<int[]> possibleMoves) {
        move = possibleMoves.get((int)(Math.random()*possibleMoves.size()));
    }
    
}

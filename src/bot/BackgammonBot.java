package src.bot;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import src.BackgammonBoard;
/**
 * @author Adam Heaney
 * @version 1.0
 * This class is an abstract class that every Backgammon bot must extend to be able to be played against. It simplifies the process of creating
 * new bots and going through multiple versions of old ones.
 */
public abstract class BackgammonBot {
    

    BackgammonBoard board;
    final String botName;
    protected int[] move;
    private static final Map<String, Class<? extends BackgammonBot>> botTypes = new HashMap<>();

    static {
        botTypes.put("RandomBot", RandomBot.class);
        botTypes.put("Adthebot278", Adthebot278.class);
        //add bots here
    }


    public BackgammonBot(String botName) {
        this.botName = botName;
        move = new int[3];
    }

    /**
     * instantiates a bot of type <b>botType</b>
     * @param botType
     * @return
     */
    public static BackgammonBot instantiateBot(String botType) {
        Class<? extends BackgammonBot> botClass = botTypes.get(botType);
        if (botClass != null) {
            try {
                Constructor<? extends BackgammonBot> constructor = botClass.getDeclaredConstructor();
                return constructor.newInstance();
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null; // Handle invalid input or missing mappings
    }

    public String getName() {
        return botName;
    }

    public void retrieveBoard(BackgammonBoard b) {
        board = b;
    }

    public int[] getMove() {
        return move;
    }

    public abstract void evaluateMoves(ArrayList<int[]> possibleMoves);
    
}

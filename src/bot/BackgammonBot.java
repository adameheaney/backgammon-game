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
    
    /**
     * the current board that the bot is playing on
     */
    BackgammonBoard board;

    /**
     * the name of the bot
     */
    final String botName;

    /**
     * the selected move of the bot
     */
    protected int[] move;

    /**
     * a HashMap with String keys going to the classes of the bots
     */
    private static final Map<String, Class<? extends BackgammonBot>> botTypes = new HashMap<>();

    /**
     * executes before main method: adds the bots to the map.
     * Add your bot here to be able to play with it
     */
    static {
        botTypes.put("RandomBot", RandomBot.class);
        botTypes.put("Adthebot278", Adthebot278.class);
        //botTypes.put([name of bot], [botClass].class);
        //add bots here
    }


    /**
     * constructs a new bot with the name botName
     * @param botName
     */
    public BackgammonBot(String botName) {
        this.botName = botName;
        move = new int[3];
    }

    /**
     * instantiates a bot of type <b>botType</b> by looking in the HashMap
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

    /**
     * 
     * @return botName
     */
    public String getName() {
        return botName;
    }

    /**
     * gives the board to the bot
     * @param b
     */
    public void retrieveBoard(BackgammonBoard b) {
        board = b;
    }

    /**
     * 
     * @return chosen move
     */
    public int[] getMove() {
        return move;
    }

    /**
     * This method must be completed by every bot:
     * The method must set move to a valid move within possibleMoves
     * @param possibleMoves
     */
    public abstract void evaluateMoves(ArrayList<int[]> possibleMoves);
    
}

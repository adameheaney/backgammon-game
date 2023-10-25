package src;

/**
 * @author Adam Heaney
 * This class is a representation of a dice
 */
public class Dice {
    /**
     * number of sides the dice has
     */
    private int numSides = 6;

    /**
     * rolls the dice, returning the sum of the amount of rolls you choose to do
     * @param numRolls
     * @return
     */
    public int roll(int numRolls) {
        int sum = 0;
        for(; numRolls > 0; numRolls--) {
            sum += (int) (Math.random() * numSides + 1);
        }
        return sum;
    }

    /**
     * rolls the dice <code>numRolls</code> of times, returning the rolls in an array
     * @param numRolls
     * @return
     */
    public int[] rolls(int numRolls) {
        int[] rolls = new int[numRolls];
        for(int i = 0; i < numRolls; i++) {
            rolls[i] =  (int) (Math.random() * numSides + 1);
        }
        return rolls;
    }

    /**
     * sets the number of sides on the dice
     * @param n
     */
    public void setNumSides(int n) {
        numSides = n;
    }

    /**
     * gets the number of sides on the dice
     * @return
     */
    public int getNumSides() {
        return numSides;
    }
}

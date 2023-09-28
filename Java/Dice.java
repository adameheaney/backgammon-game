package Java;

public class Dice {
    private int numSides = 6;

    public int roll(int numDie) {
        int sum = 0;
        for(; numDie > 0; numDie--) {
            sum += (int) (Math.random() * 6 + 1);
        }
        return sum;
    }
}

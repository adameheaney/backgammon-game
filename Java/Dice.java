package Java;

public class Dice {

    private int numSides = 6;

    public int roll(int num) {
        int sum = 0;
        for(; num > 0; num--) {
            sum += (int) (Math.random() * numSides + 1);
        }
        return sum;
    }

    public void setNumSides(int n) {
        numSides = n;
    }

    public int getNumSides() {
        return numSides;
    }
}

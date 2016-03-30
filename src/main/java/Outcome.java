import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Daniel on 2016-03-29.
 */
public class Outcome {
    private PossibleEnd[] array;
    private int maxAttackers, maxDefenders;
    private int filled = 0;

    private double winningPercentage;
    private PossibleEnd midPoint;


    public Outcome(int maxAttackers, int maxDefenders) {
        array = new PossibleEnd[maxAttackers + maxDefenders];
        this.maxAttackers = maxAttackers;
        this.maxDefenders = maxDefenders;
    }


    public void add(int attackers, int defenders, double percentage) throws IllegalArgumentException {
        int index;
        if (defenders == 0) {
            index = maxAttackers - attackers;
        } else {
            index = maxAttackers - 1 + defenders;
        }
        if (array[index] != null) {
            throw new IllegalArgumentException("Outcome already filled");
        }
        array[index] = new PossibleEnd(attackers, defenders, percentage);
        filled++;
        if (filled == array.length) {
            calculateData();
        }
    }

    private void calculateData() {
        double sum =0.0;
        for (PossibleEnd temp : array) {
            sum+= temp.percentage;
            if(sum>0.5 && midPoint==null){
                midPoint=temp;
            }
            if(temp.attacker == 1 & temp.defenders ==0){
                winningPercentage = sum;
            }
        }
    }


    private class PossibleEnd {
        public int attacker, defenders;
        public double percentage;

        public PossibleEnd(int attacker, int defenders, double percentage) {
            this.attacker = attacker;
            this.defenders = defenders;
            this.percentage = percentage;
        }
    }

    @Override
    public String toString() {
        return "Outcome{" +
                "array=" + Arrays.toString(array) +
                ", winningPercentage=" + winningPercentage +
                ", midPoint=" + midPoint +
                '}';
    }
}


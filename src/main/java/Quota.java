/**
 * Created by danie on 2016-03-27.
 */
public class Quota {
    private int attackers, defenders;
    public Quota(int attackers, int defenders){
        this.attackers = attackers;
        this.defenders = defenders;
    }

    public int getAttackers() {
        return attackers;
    }

    public int getDefenders() {
        return defenders;
    }

    public boolean equalValues(int attackers, int defenders){
        return attackers == this.attackers && defenders == this.defenders;
    }
}

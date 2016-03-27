import java.util.HashMap;
import java.util.Map;

/**
 * Created by danie on 2016-03-27.
 */
public class Outcome {
    Map<Quota, Integer> map = new HashMap<Quota, Integer>();
    int denominator;
    public Outcome(Quota input){
        for(int i = 1; i<=input.getAttackers(); i++){
            map.put(new Quota(i,0), Integer.valueOf(0));
        }
        for(int i = 1; i<=input.getDefenders(); i++){
            map.put(new Quota(i,0), Integer.valueOf(0));
        }
    }
    public void  newResult(Quota quota){
        //map
        denominator++;

    }



}

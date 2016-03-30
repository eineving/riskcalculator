import static spark.Spark.get;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Daniel on 2016-03-29.
 */
public class Main {

    public static void main(String[] args) {


        get("/outcome/:attackers/:defenders", (request, response) -> {

            int attackers = Integer.valueOf(request.params(":attackers"));
            int defenders = Integer.valueOf(request.params(":defenders"));
            TreeOutcome graph = new TreeOutcome(attackers, defenders);


            Gson gson = new GsonBuilder().create();
            String test = gson.toJson(graph.getOutcome());

            response.body(test);
            return test;
        });

/*
        TreeOutcome tree = new TreeOutcome(18, 18);
        System.out.println(tree.getOutcome());
*/
    }

}

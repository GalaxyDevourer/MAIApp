package models.csv;

import java.util.ArrayList;
import java.util.List;

public class MPPTableGenerator {
    private String[] criteria;

    public MPPTableGenerator(String[] criteria) {
        this.criteria = criteria;
    }

    public List<String[]> generateTable () {
        List<String[]> data = new ArrayList<>();

        int number = criteria.length + 1;

        for (int i = 0; i < number; i++) {
            data.add(new String[number]);
        }

        return data;
    }

    /*
    public List<String[]> randomInitialization (List<String[]> table) {
        table.forEach( x -> {
            for (int i = 1; ) {

            }
        });
    }
    */

    public double initCell () {
        int random = (int) (Math.random()* 10);
        double[] possibleVariants = new double[] {1, 2, 3, 4, 5, 0.1, 0.2, 0.25, 0.33, 0.5};

        return possibleVariants[random];
    }

    public String[] getCriteria() {
        return criteria;
    }

    public void setCriteria(String[] criteria) {
        this.criteria = criteria;
    }
}

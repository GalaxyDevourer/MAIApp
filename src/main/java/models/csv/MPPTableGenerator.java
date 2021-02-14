package models.csv;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MPPTableGenerator {
    private String[] criteriaList;
    private String[] additional;

    private int size;
    private String path;
    private final String fileName = "MPP_General_Table.csv";

    public MPPTableGenerator () {};

    public MPPTableGenerator(String[] criteria, String path) {
        this.criteriaList = criteria;
        this.path = path;
        additional = new String[] {"OwnVector (B)", "Prior.Vec.(P)", "Lambda"};
        size = criteriaList.length + additional.length + 1;
    }

    public void generateTable () {
        int criteria = criteriaList.length;
        String[][] data = initialize(size);

        for (int i = 1; i < criteria+1; i++) {
            data[i][0] = criteriaList[i-1];
            data[0][i] = criteriaList[i-1];
        }

        data[0][size-3] = additional[0];
        data[0][size-2] = additional[1];
        data[0][size-1] = additional[2];

        data[criteria+1][0] = "Sum";
        data[size-2][size-2] = "IU=";
        data[size-1][size-2] = "VU=";

        writeToCSV(data);
    }

    private String[][] initialize (int size) {
        String [][] array = new String[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array[i][j] = "";
            }
        }

        return array;
    }

    private void writeToCSV (String[][] tableArray) {
        int size = tableArray[0].length;
        StringBuilder table = new StringBuilder();

        try(FileWriter writer = new FileWriter(path + "/" + fileName, false))
        {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    table.append(tableArray[i][j]).append(";");
                }
                table.append("\n");
            }

            writer.write(table.toString());
            writer.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private String[][] readFromCSV () {
        List<List<String>> lists = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path + "/" + fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";", -1);
                //System.out.println(Arrays.toString(values));
                lists.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] table = new String[size][size];
        for (int i = 0; i < lists.size(); i ++) {
            table[i] = (String[]) lists.get(i).toArray();
        }

        return table;
    }

    public void startCalculating () {
        String[][] tableArray = readFromCSV();
        //System.out.println(Arrays.deepToString(tableArray));

        calculateOwnVector(tableArray);
        calculateSum(tableArray);
        //System.out.println(Arrays.deepToString(tableArray));
        writeToCSV(tableArray);
    }

    private String[][] calculateOwnVector (String[][] tableArray) {
        for (int i = 1; i < criteriaList.length + 1; i++) {
            tableArray[i][size-3] = Math.pow(multInRow(tableArray, i), 1./criteriaList.length) + "";
        }

        return tableArray;
    }

    private double multInRow (String[][] tableArray, int row) {
        double mult = 1;

        for (int i = 1; i < criteriaList.length+1; i++) {
            //System.out.println(tableArray[row][i]);
            mult *= Double.parseDouble(tableArray[row][i]);
        }

        return mult;
    }

    private String[][] calculateSum (String[][] tableArray) {
        for (int j = 1; j < size-2; j++) {
            tableArray[size-3][j] = sumInColumn(tableArray, j) + "";
        }

        return tableArray;
    }

    private double sumInColumn (String[][] tableArray, int column) {
        double sum = 0;

        for (int i = 1; i < criteriaList.length + 1; i++) {
            sum += Double.parseDouble(tableArray[i][column]);
        }

        return sum;
    }

    /*
    private String[][] calculatePriorVector (String[][] tableArray) {

    }

    private String[][] calculateLambda (String[][] tableArray) {

    }

    private String[][] calculateVU (String[][] tableArray) {

    }
    */

    public String[] getCriteriaList() {
        return criteriaList;
    }

    public void setCriteriaList(String[] criteriaList) {
        this.criteriaList = criteriaList;
    }

    public String[] getAdditional() {
        return additional;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
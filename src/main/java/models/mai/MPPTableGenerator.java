package models.mai;

import models.csv.CSVManager;

public class MPPTableGenerator implements CSVManager {
    private String[] dataList;
    private String[] additional;

    private int size;
    private String path;

    private Boolean isTest = false;
    //private String[][] testArray;
    private double[] avgCIValues;

    public MPPTableGenerator () {};

    public MPPTableGenerator(String[] data, String path, Boolean isTest) {
        this.dataList = data;
        this.path = path;
        this.isTest = isTest;
        this.additional = new String[] {"OwnVector (B)", "Prior.Vec.(P)", "Lambda"};
        this.size = dataList.length + additional.length + 1;
        this.avgCIValues = new double[]{0,0,0,0.58,0.9,1.12,1.24,1.32,1.41,
                                        1.45,1.49,1.51,1.48,1.56,1.57,1.59};

        //if (isTest) initTestValues();
    }

    public void generateTable (String fileName) {
        int length = dataList.length;
        String[][] data = initialize(size, isTest);

        for (int i = 1; i < length+1; i++) {
            data[i][0] = dataList[i-1];
            data[0][i] = dataList[i-1];
        }

        data[0][size-3] = additional[0];
        data[0][size-2] = additional[1];
        data[0][size-1] = additional[2];

        data[length+1][0] = "Sum";
        data[size-2][size-2] = "IU=";
        data[size-1][size-2] = "VU=";

        writeToCSV(data, path, fileName);
    }

    private String[][] initialize (int size, Boolean isTest) {
        String [][] array = new String[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array[i][j] = "";
            }
        }

        /*
        if (isTest) {
            int s = testArray[0].length;
            for (int i = 0; i < s; i++) {
                System.arraycopy(testArray[i], 0, array[i + 1], 1, s);
            }
        }
        */

        return array;
    }

    public void startCalculating (String fileName) {
        String[][] tableArray = readFromCSV(path, fileName, size);

        calculateOwnVector(tableArray);
        calculateSum(tableArray);
        calculatePriorVectorAndLambda(tableArray);
        calculateVU(tableArray);
        writeToCSV(tableArray, path, fileName);
    }

    private void calculateOwnVector (String[][] tableArray) {
        int length = dataList.length;
        for (int i = 1; i < length + 1; i++) {
            tableArray[i][size-3] = Math.pow(multInRow(tableArray, i), 1./length) + "";
        }

    }

    private double multInRow (String[][] tableArray, int row) {
        double mult = 1;
        int length = dataList.length;

        for (int i = 1; i < length+1; i++) {
            //System.out.println(tableArray[row][i]);
            mult *= Double.parseDouble(tableArray[row][i]);
        }

        return mult;
    }

    private void calculateSum (String[][] tableArray) {
        for (int j = 1; j < size-2; j++) {
            tableArray[size-3][j] = sumInColumn(tableArray, j) + "";
        }

    }

    private double sumInColumn (String[][] tableArray, int column) {
        double sum = 0;
        int length = dataList.length;

        for (int i = 1; i < length + 1; i++) {
            sum += Double.parseDouble(tableArray[i][column]);
        }

        return sum;
    }

    private void calculatePriorVectorAndLambda (String[][] tableArray) {
        double sumOwnVector = Double.parseDouble(tableArray[size-3][size-3]);
        int length = dataList.length;

        for (int i = 1; i < length + 1; i++) {
            tableArray[i][size-2] = (Double.parseDouble(tableArray[i][size-3]) / sumOwnVector) + "";
            System.out.println("lambda " + i + " = " + Double.parseDouble(tableArray[i][size-2]) + " * " + Double.parseDouble(tableArray[size-3][i]) + " = " + (Double.parseDouble(tableArray[i][size-2]) * Double.parseDouble(tableArray[size-3][i])));
            tableArray[i][size-1] = (Double.parseDouble(tableArray[i][size-2]) * Double.parseDouble(tableArray[size-3][i])) + "";
        }

        double sumPriorVector = 0;
        double sumLambda = 0;

        for (int i = 1; i < length + 1; i++) {
            sumPriorVector += (Double.parseDouble(tableArray[i][size-2]));
            sumLambda += (Double.parseDouble(tableArray[i][size-1]));
        }
        tableArray[size-3][size-2] = sumPriorVector + "";
        tableArray[size-3][size-1] = sumLambda + "";
    }

    private void calculateVU (String[][] tableArray) {
        int length = dataList.length;
        double avgCI;

        if (length > 15) {
            avgCI = 1.6 * length * 0.01;
        }
        else avgCI = avgCIValues[length];

        tableArray[size-2][size-1] = ((Double.parseDouble(tableArray[size-3][size-1]) - length) / (length - 1)) + "";
        tableArray[size-1][size-1] = (Double.parseDouble(tableArray[size-2][size-1]) / avgCI) + "";
    }

    /*
    public void initTestValues () {
        testArray = new String[6][6];
        testArray[0] = new String[] {"1.00","0.25","0.50","0.20","0.40","0.25"};
        testArray[1] = new String[] {"4.00","1.00","1.50","0.50","0.33","2.00"};
        testArray[2] = new String[] {"2.00","0.67","1.00","0.33","0.50","1.00"};
        testArray[3] = new String[] {"5.00","2.00","3.00","1.00","1.00","1.00"};
        testArray[4] = new String[] {"2.50","3.00","2.00","1.00","1.00","2.00"};
        testArray[5] = new String[] {"4.00","0.50","1.00","1.00","0.50","1.00"};
    }

    */

    public String[] getDataList() {
        return dataList;
    }

    public void setDataList(String[] dataList) {
        this.dataList = dataList;
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
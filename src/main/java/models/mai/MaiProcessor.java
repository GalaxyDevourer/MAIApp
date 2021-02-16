package models.mai;

import models.csv.CSVManager;

import java.util.Arrays;
import java.util.List;

public class MaiProcessor implements CSVManager {
    private MPPTableGenerator criteria;
    private MPPTableGenerator alternatives;

    private final String critFileName;
    private final String altFileName;

    private String[][] results;

    public MaiProcessor(MPPTableGenerator criteria, String critFileName, MPPTableGenerator alternatives, String altFileName) {
        this.criteria = criteria;
        this.alternatives = alternatives;
        this.critFileName = critFileName;
        this.altFileName = altFileName;
    }

    public void startGenerating () {
        criteria.generateTable(critFileName + ".csv");

        int altSize = criteria.getDataList().length;
        for (int i = 0; i < altSize; i++) {
            alternatives.generateTable(altFileName + "_" + i + ".csv");
        }
    }

    public void startProcessing () {
        criteria.startCalculating(critFileName + ".csv");

        int altSize = criteria.getDataList().length;
        for (int i = 0; i < altSize; i++) {
            alternatives.startCalculating(altFileName + "_" + i + ".csv");
        }
    }

    public void doResults () {
        List<String[][]> criteriaList = criteria.getProcessedMPP();
        List<String[][]> alternativesList = alternatives.getProcessedMPP();

        int altCount = alternatives.getDataList().length;
        int altMatrixSize = altCount + 4;
        int critCount = criteria.getDataList().length;
        int critMatrixSize = critCount + 4;

        results = new String[altCount][2];

        for (int i = 0; i < altCount; i++) {
            double value = 0;
            for (int j = 0; j < critCount; j++) {
                double v1 = Double.parseDouble(alternativesList.get(j)[i+1][altMatrixSize-2]);
                double v2 = Double.parseDouble(criteriaList.get(0)[j+1][critMatrixSize-2]);

                value += (v1 * v2);
            }
            results[i] = new String[]{alternatives.getDataList()[i], value + ""};
        }

        writeToCSV(results, criteria.getPath(), "Results.csv" );
    }

    public MPPTableGenerator getCriteria() {
        return criteria;
    }

    public void setCriteria(MPPTableGenerator criteria) {
        this.criteria = criteria;
    }

    public MPPTableGenerator getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(MPPTableGenerator alternatives) {
        this.alternatives = alternatives;
    }

    public String[][] getResults() {
        return results;
    }
}

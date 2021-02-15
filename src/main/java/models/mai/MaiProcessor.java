package models.mai;

public class MaiProcessor {
    MPPTableGenerator criteria;
    MPPTableGenerator alternatives;

    String critFileName;
    String altFileName;

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
}

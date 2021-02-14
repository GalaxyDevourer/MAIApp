package models.gui;

public class InitialSettings {
    private String mainGoal;

    private String[] criteriaNamesList;
    private String[] alternativesNamesList;

    public InitialSettings () {}

    public InitialSettings(String mainGoal, String[] criteriaNamesList, String[] alternativesNamesList) {
        this.mainGoal = mainGoal;
        this.criteriaNamesList = criteriaNamesList;
        this.alternativesNamesList = alternativesNamesList;
    }

    public String getMainGoal() {
        return mainGoal;
    }

    public void setMainGoal(String mainGoal) {
        this.mainGoal = mainGoal;
    }

    public String[] getCriteriaNamesList() {
        return criteriaNamesList;
    }

    public void setCriteriaNamesList(String[] criteriaNamesList) {
        this.criteriaNamesList = criteriaNamesList;
    }

    public String[] getAlternativesNamesList() {
        return alternativesNamesList;
    }

    public void setAlternativesNamesList(String[] alternativesNamesList) {
        this.alternativesNamesList = alternativesNamesList;
    }
}

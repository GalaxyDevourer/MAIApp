package models.gui;

public class InitialSettings {
    private String mainGoal;

    private Integer criteriaNumbers;
    private Integer alternativesNumbers;

    private String[] criteriaNamesList;
    private String[] alternativesNamesList;

    public InitialSettings () {}

    public InitialSettings(String mainGoal, Integer criteriaNumbers, Integer alternativesNumbers, String[] criteriaNamesList, String[] alternativesNamesList) {
        this.mainGoal = mainGoal;
        this.criteriaNumbers = criteriaNumbers;
        this.alternativesNumbers = alternativesNumbers;
        this.criteriaNamesList = criteriaNamesList;
        this.alternativesNamesList = alternativesNamesList;
    }

    public String getMainGoal() {
        return mainGoal;
    }

    public void setMainGoal(String mainGoal) {
        this.mainGoal = mainGoal;
    }

    public Integer getCriteriaNumbers() {
        return criteriaNumbers;
    }

    public void setCriteriaNumbers(Integer criteriaNumbers) {
        this.criteriaNumbers = criteriaNumbers;
    }

    public Integer getAlternativesNumbers() {
        return alternativesNumbers;
    }

    public void setAlternativesNumbers(Integer alternativesNumbers) {
        this.alternativesNumbers = alternativesNumbers;
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

package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.csv.MPPTableGenerator;
import models.services.UtilGenerator;
import models.services.WindowsDialogs;

public class MenuController implements WindowsDialogs {
    @FXML Button readButton;
    @FXML TextField mainGoalField;
    @FXML Button generateButton;
    @FXML TextArea namesCriteria;
    @FXML Spinner<Integer> spinnerCriteria;
    @FXML TextArea namesAlternatives;
    @FXML Spinner<Integer> spinnerAlternatives;

    private MPPTableGenerator mpp;

    @FXML public void initialize () {
        spinnerCriteria.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 50, 6));
        spinnerAlternatives.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 20, 4));

        namesCriteria.setPromptText("Write the name of the criteria, separated by commas \n" +
                                    "Or leave this field empty for auto-generation");
        namesAlternatives.setPromptText("Write the name of the alternatives, separated by commas \n" +
                                    "Or leave this field empty for auto-generation");
    }

    @FXML public void startGenerating () {
        String[] criteriaList;
        String[] alternativesList;

        int criteriaValue = spinnerCriteria.getValue();
        int alternativesValue = spinnerAlternatives.getValue();

        if (!namesAlternatives.getText().equals("") && !namesCriteria.getText().equals("")) {
            criteriaList = namesCriteria.getText().split(",");
            alternativesList = namesAlternatives.getText().split(",");

            if (criteriaList.length == alternativesValue) {
                if (alternativesList.length == criteriaValue) {
                    System.out.println("Successfully started");
                    mpp = new MPPTableGenerator(criteriaList, "E:\\_ИНСТИТУТ\\3 КУРС\\Второй семестр\\Інженерія знань\\Практика\\Лаб 1");
                    mpp.generateTable();
                }
                else dialogRegMessage("Error!","Not enough or too many criteria names","Please, add some names in the list", Alert.AlertType.ERROR);
            }
            else dialogRegMessage("Error!","Not enough or too many alternatives names","Please, add some names in the list", Alert.AlertType.ERROR);
        }
        else {
            UtilGenerator gen = new UtilGenerator();
            criteriaList = gen.randomNamesGenerator("Crit", criteriaValue);
            alternativesList = gen.randomNamesGenerator("Alt", alternativesValue);

            mpp = new MPPTableGenerator(criteriaList, "E:\\_ИНСТИТУТ\\3 КУРС\\Второй семестр\\Інженерія знань\\Практика\\Лаб 1");
            mpp.generateTable();
        }
    }

    @FXML public void startReading () {
        mpp.startCalculating();
    }
}

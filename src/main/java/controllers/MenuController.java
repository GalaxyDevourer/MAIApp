package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.gui.InitialSettings;
import models.services.WindowsDialogs;

import java.util.Arrays;

public class MenuController implements WindowsDialogs {
    @FXML TextField mainGoalField;
    @FXML Button generateButton;
    @FXML TextArea namesCriteria;
    @FXML Spinner<Integer> spinnerCriteria;
    @FXML TextArea namesAlternatives;
    @FXML Spinner<Integer> spinnerAlternatives;

    @FXML public void initialize () {
        spinnerCriteria.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 50, 6));
        spinnerAlternatives.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 20, 4));
    }

    @FXML public void startGenerating () {
        String[] criteriaList = namesCriteria.getText().split(",");
        String[] alternativesList = namesAlternatives.getText().split(",");

        InitialSettings initialSettings = new InitialSettings(mainGoalField.getText(), spinnerCriteria.getValue(), spinnerAlternatives.getValue(), criteriaList, alternativesList);
        System.out.println(Arrays.toString(initialSettings.getCriteriaNamesList()));
        System.out.println(Arrays.toString(initialSettings.getAlternativesNamesList()));

        if (initialSettings.getAlternativesNamesList().length == initialSettings.getAlternativesNumbers()) {
            if (initialSettings.getCriteriaNamesList().length == initialSettings.getCriteriaNumbers()) {
                System.out.println("Successfully started");
            }
            else dialogRegMessage("Error!","Not enough or too many criteria names","Please, add some names in the list", Alert.AlertType.ERROR);
        }
        else dialogRegMessage("Error!","Not enough or too many alternatives names","Please, add some names in the list", Alert.AlertType.ERROR);
    }
}

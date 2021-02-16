package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import models.mai.MPPTableGenerator;
import models.mai.MaiProcessor;
import models.services.UtilGenerator;
import models.services.WindowsDialogs;

import java.io.File;
import java.util.Arrays;

public class MenuController implements WindowsDialogs {
    @FXML Button readButton;
    @FXML Button generateButton;
    @FXML TextArea namesCriteria;
    @FXML Spinner<Integer> spinnerCriteria;
    @FXML TextArea namesAlternatives;
    @FXML Spinner<Integer> spinnerAlternatives;

    @FXML TextField criteriaFileName;
    @FXML TextField alternativesFileName;
    @FXML TextField folderDownloadPath;
    @FXML Button fileFolderChooser;

    private MaiProcessor mai;

    @FXML public void initialize () {
        spinnerCriteria.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 50, 6));
        spinnerAlternatives.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(2, 20, 4));

        namesCriteria.setPromptText("Write the name of the criteria, separated by commas \n" +
                                    "Or leave this field empty for auto-generation");
        namesAlternatives.setPromptText("Write the name of the alternatives, separated by commas \n" +
                                    "Or leave this field empty for auto-generation");

        folderDownloadPath.setText("E:\\_ИНСТИТУТ\\3 КУРС\\Второй семестр\\Інженерія знань\\Практика\\Лаб 1");
    }

    @FXML public void startGenerating () {
        int criteriaValue = spinnerCriteria.getValue();
        int alternativesValue = spinnerAlternatives.getValue();

        String[] criteriaList;
        String[] alternativesList;

        if (!namesAlternatives.getText().equals("") && !namesCriteria.getText().equals("")) {
            criteriaList = namesCriteria.getText().split(",");
            System.out.println(Arrays.toString(criteriaList));
            alternativesList = namesAlternatives.getText().split(",");
            System.out.println(Arrays.toString(alternativesList));

            if (criteriaList.length == criteriaValue) {
                if (alternativesList.length == alternativesValue) {
                    System.out.println("Successfully started");

                    MPPTableGenerator criteria = new MPPTableGenerator(criteriaList, folderDownloadPath.getText());
                    MPPTableGenerator alternatives = new MPPTableGenerator(alternativesList, folderDownloadPath.getText());

                    mai = new MaiProcessor(criteria, criteriaFileName.getText(), alternatives, alternativesFileName.getText());
                    mai.startGenerating();
                }
                else dialogRegMessage("Error!","Not enough or too many criteria names","Please, add some names in the list", Alert.AlertType.ERROR);
            }
            else dialogRegMessage("Error!","Not enough or too many alternatives names","Please, add some names in the list", Alert.AlertType.ERROR);
        }
        else {
            UtilGenerator gen = new UtilGenerator();
            criteriaList = gen.randomNamesGenerator("Crit", criteriaValue);
            alternativesList = gen.randomNamesGenerator("Alt", alternativesValue);

            MPPTableGenerator criteria = new MPPTableGenerator(criteriaList, folderDownloadPath.getText());
            MPPTableGenerator alternatives = new MPPTableGenerator(alternativesList, folderDownloadPath.getText());

            mai = new MaiProcessor(criteria, "MPP_Criteria", alternatives, "MPP_Alternative");
            mai.startGenerating();
        }
    }

    @FXML public void startProcessing() {
        mai.startProcessing();
    }

    @FXML public void doResults() {
        mai.doResults();
    }

    @FXML
    public void chooseFolder () {
        File file = new DirectoryChooser().showDialog(fileFolderChooser.getScene().getWindow());
        folderDownloadPath.setText(file.getAbsolutePath());
    }
}

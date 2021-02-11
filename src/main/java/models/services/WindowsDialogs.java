package models.services;

import javafx.scene.control.Alert;

public interface WindowsDialogs {
    default void dialogRegMessage(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}

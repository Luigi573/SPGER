package mx.uv.fei.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class AlertPaneController {

    @FXML
    private DialogPane dialogPane;

    public void openWarningPane(String message) {
        Alert errorMessage = new Alert(Alert.AlertType.WARNING);
        errorMessage.setContentText(message);

        this.dialogPane = errorMessage.getDialogPane();
        String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        this.dialogPane.getStylesheets().add(css);
        this.dialogPane.getStyleClass().add("dialog");
        errorMessage.showAndWait();
    }

    public void openErrorPane(String message) {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setContentText(message);

        this.dialogPane = errorMessage.getDialogPane();
        String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        this.dialogPane.getStylesheets().add(css);
        this.dialogPane.getStyleClass().add("dialog");
        errorMessage.showAndWait();
    }

}
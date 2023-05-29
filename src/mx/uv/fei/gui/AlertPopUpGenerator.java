package mx.uv.fei.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;

public class AlertPopUpGenerator {

    @FXML
    private DialogPane dialogPane;

    public void showConnectionErrorMessage() {


        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setHeaderText("Error de conexión");
        errorMessage.setContentText("Favor de verificar su conexión a internet e inténtelo de nuevo");

        dialogPane = errorMessage.getDialogPane();
        String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("dialog");
        errorMessage.showAndWait();
    }

    public void showMissingFilesMessage() {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setHeaderText("Error");
        errorMessage.setContentText("Fallo al cargar FXML, archivo no encontrado");

        dialogPane = errorMessage.getDialogPane();
        String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("dialog");
        errorMessage.showAndWait();
    }

    public void showCustomMessage(Alert.AlertType AlertType, String header, String content) {
        Alert customMessage = new Alert(AlertType);
        customMessage.setHeaderText(header);
        customMessage.setContentText(content);

        dialogPane = customMessage.getDialogPane();
        String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("dialog");
        customMessage.showAndWait();
    }
}

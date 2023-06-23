package mx.uv.fei.gui;

import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;

public class AlertPopUpGenerator {

    @FXML
    private DialogPane dialogPane;

    public void showConnectionErrorMessage() {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setHeaderText("Error de conexión");
        errorMessage.setContentText("Hubo un error al conectarse al servidor, inténtelo más tarde");

        dialogPane = errorMessage.getDialogPane();
        String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("dialog");
        errorMessage.showAndWait();
    }

    public void showMissingFilesMessage() {
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setHeaderText("Error");
        errorMessage.setContentText("Fallo al cargar la ventana");

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
    
    public Optional<ButtonType> showConfirmationMessage(Alert.AlertType AlertType, String header, String content) {
        Alert customMessage = new Alert(AlertType);
        customMessage.setHeaderText(header);
        customMessage.setContentText(content);

        dialogPane = customMessage.getDialogPane();
        String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        dialogPane.getStylesheets().add(css);
        dialogPane.getStyleClass().add("dialog");
        Optional<ButtonType> buttonPressed = customMessage.showAndWait();
        return buttonPressed;
    }
}

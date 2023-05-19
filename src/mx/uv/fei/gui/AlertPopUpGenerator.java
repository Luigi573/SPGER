package mx.uv.fei.gui;

import javafx.scene.control.Alert;

public class AlertPopUpGenerator {
    public static void showConnectionErrorMessage(){
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setHeaderText("Error de conexión");
        errorMessage.setContentText("Favor de verificar su conexión a internet e inténtelo de nuevo");
        errorMessage.showAndWait();
    }
    public static void showMissingFilesMessage(){
        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
        errorMessage.setHeaderText("Error");
        errorMessage.setContentText("Fallo al cargar FXML, archivo no encontrado");
        errorMessage.showAndWait();
    }
    public static void showCustomMessage(Alert.AlertType AlertType, String header, String content){
        Alert customMessage = new Alert(AlertType);
        customMessage.setHeaderText(header);
        customMessage.setContentText(content);
        customMessage.showAndWait();
    }
}

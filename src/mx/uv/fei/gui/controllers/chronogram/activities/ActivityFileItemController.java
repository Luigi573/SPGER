package mx.uv.fei.gui.controllers.chronogram.activities;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import org.apache.commons.io.FileUtils;


public class ActivityFileItemController {
    private File file;
    
    @FXML
    private Button downloadFileButton;
    
    @FXML
    private Label FileNameLabel;

    @FXML
    private Pane pKGALListElement;

    @FXML
    void downloadFile(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccione un directorio");
        File selectedDirectory = directoryChooser.showDialog((Stage)((Node)event.getSource()).getScene().getWindow());
        if (selectedDirectory != null) {
            String downloadedFilePath = selectedDirectory + "\\" + file.getName();
            File downloadedFile = new File(downloadedFilePath);

            try {
                FileUtils.copyFile(file,downloadedFile);
                Alert successMessage = new Alert(Alert.AlertType.CONFIRMATION);
                successMessage.setHeaderText("Operación exitosa");
                successMessage.setContentText("Se ha descargado el archivo correctamente.");
                successMessage.showAndWait();
            } catch (IOException exception) {
                Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                errorMessage.setHeaderText("Ocurrió un error");
                errorMessage.setContentText("No se pudo descargar el archivo. Por favor intente de nuevo más tarde.");
                errorMessage.showAndWait();
            }
        }
    }
    
    public void setFile(String filePath) {
        File newFile = new File(filePath);
        this.file = newFile;
        FileNameLabel.setText(file.getName());
    }
    
    public void setFile(File file) {
        this.file = file;
        FileNameLabel.setText(file.getName());
    }
    
    public void hideDownloadButton() {
        this.downloadFileButton.setVisible(false);
    }
}

package mx.uv.fei.gui.controllers.chronogram.activities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;


public class ActivityFileItemController {
    private File file;
    
    @FXML
    private Button downloadFileButton;
    @FXML
    private Label FileNameLabel;

    @FXML
    private void downloadFile(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Seleccione un directorio");
        File selectedDirectory = directoryChooser.showDialog((Stage)((Node)event.getSource()).getScene().getWindow());
        if (selectedDirectory != null) {
            String downloadedFilePath = selectedDirectory + "\\" + file.getName();
            File downloadedFile = new File(downloadedFilePath);

            try {
                FileUtils.copyFile(file, downloadedFile);
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.INFORMATION, "Operación exitosa", "Se ha descargado el archivo correctamente.");
            } catch (IOException exception) {
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.ERROR, "Ocurrió un error", "No se pudo descargar el archivo. Por favor intente de nuevo más tarde.");
            }
        }
    }
    
    public void setFile(String filePath) {
        if(filePath != null){
            File newFile = new File(filePath);
            this.file = newFile;
            FileNameLabel.setText(file.getName());
        }
    }
    
    public void setFile(File file) {
        this.file = file;
        FileNameLabel.setText(file.getName());
    }
    
    public void hideDownloadButton() {
        this.downloadFileButton.setVisible(false);
    }
}

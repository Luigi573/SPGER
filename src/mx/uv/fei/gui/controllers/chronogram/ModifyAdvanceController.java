package mx.uv.fei.gui.controllers.chronogram;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ModifyAdvanceController {
    private Advance advance;
    private boolean hasFile;
    private String filePath;

    @FXML
    private TextArea advanceCommentsTextArea;

    @FXML
    private Pane advanceFilePane;

    @FXML
    private VBox advanceFileVBox;

    @FXML
    private TextField advanceTitleTextField;

    @FXML
    private Pane headerPane;

    @FXML
    private Button returnButton;

    @FXML
    private Button saveChangesButton;
    
    @FXML 
    private void initialize() {
        loadHeader();
    }

    @FXML
    private void returnToAdvanceInfo(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/AdvanceInfo.fxml"));
            Parent parent = loader.load();
            AdvanceInfoController controller = (AdvanceInfoController)loader.getController();
            controller.setAdvanceInfo(advance);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error de carga");
            errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
            errorMessage.showAndWait();
        }
    }

    @FXML
    private void updateAdvanceInfo(ActionEvent event) {
        int savedFileGeneratedId;
        savedFileGeneratedId = saveFile();
        
        Advance advanceToBeSaved = new Advance();
        advanceToBeSaved.setActivityID(ActivityInfoController.activity.getId());
        advanceToBeSaved.setFileID(savedFileGeneratedId);
        advanceToBeSaved.setTitle(advanceTitleTextField.getText());
        advanceToBeSaved.setComments(advanceCommentsTextArea.getText());
        
        AdvanceDAO advanceDAO = new AdvanceDAO();
        
        int result = 0;
        try {
            result = advanceDAO.updateAdvanceInfo(advance.getAdvanceID(), advanceToBeSaved);
        } catch (DataRetrievalException exception) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Ocurrió un error");
            errorMessage.setContentText(exception.getMessage());
            errorMessage.showAndWait(); 
        } finally {
            if (result > 0) {
                Alert successMessage = new Alert(Alert.AlertType.CONFIRMATION);
                successMessage.setHeaderText("Operación exitosa");
                successMessage.setContentText("Se ha guardado el nuevo avance correctamente.");
                successMessage.showAndWait();
            }
        }
    }
    
    @FXML
    private void uploadFileToAdvance(ActionEvent event) {    
        if (!hasFile) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccione el archivo a entregar");
            java.io.File file = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());

            if (file != null) {
                try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityFileItem.fxml"));
                        Pane pane = loader.load();
                        ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                        controller.setLabelText(file.getName());
                        advanceFileVBox.getChildren().add(pane);
                        this.filePath = file.getPath();
                        hasFile = true;
                    } catch (IOException | IllegalStateException exception) {
                        Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                        errorMessage.setHeaderText("Error al mostrar la información");
                        errorMessage.setContentText("Ocurrió un error al intentar mostrar la información.");
                        errorMessage.showAndWait();
                    }
            }
        } else {
                Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                errorMessage.setHeaderText("El avance ya tiene un archivo adjunto");
                errorMessage.setContentText("Quite el archivo adjunto antes de agregar uno nuevo.");
                errorMessage.showAndWait();
        }
    }
    
    @FXML
    private void removeFilesFromAdvance(ActionEvent event) {
        advanceFileVBox.getChildren().clear();
        hasFile = false;
    }
    
    public void setAdvance(Advance advance) {
        this.advance = advance;
        this.advanceTitleTextField.setText(advance.getTitle());
        this.advanceCommentsTextArea.setText(advance.getComments());
        showAdvanceFile();
    }
    
    private void loadHeader() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            headerPane.getChildren().add(header);
            
        }catch(IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setContentText("Error al cargar, faltan archivos");
            errorMessage.showAndWait();
        }
    }
    
    private void showAdvanceFile() {
        FileDAO fileDAO = new FileDAO();
        File file = new File();
        try {
            file = fileDAO.getFileByID(advance.getFileID());
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityFileItem.fxml"));               
            try{
                Pane advancePane = loader.load();
                ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                controller.setLabelText(file.getFilePath());
                
                advanceFileVBox.getChildren().add(advancePane);
                hasFile = true;
            }catch(IllegalStateException | IOException exception){
                Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                errorMessage.setHeaderText("Error de carga");
                errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
                errorMessage.showAndWait();
            }
        } catch (DataRetrievalException exception) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error de conexión");
            errorMessage.setContentText("Favor de verificar su conexión a internet e inténtelo de nuevo");
            errorMessage.showAndWait();
        }
    }
    
    private int saveFile() {
        int result = 0;
        if (this.filePath != null) {
                FileDAO fileDAO = new FileDAO();
                try {
                    result = fileDAO.addFile(this.filePath);
                } catch (DataInsertionException die) {
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error al guardar archivo");
                    errorMessage.setContentText("Ocurrió un error al intentar guardar el archivo. Por favor intente de nuevo más tarde.");
                    errorMessage.showAndWait(); 
                }
        }
        return result;
    }
}


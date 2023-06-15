package mx.uv.fei.gui.controllers.chronogram.advances;

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
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityFileItemController;
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
    private void returnToAdvanceInfo(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/AdvanceInfo.fxml"));
            Parent parent = loader.load();
            AdvanceInfoController controller = (AdvanceInfoController)loader.getController();
            controller.setAdvance(advance);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }

    @FXML
    private void updateAdvanceInfo(ActionEvent event) {
        int savedFileGeneratedId;
        savedFileGeneratedId = saveFile();
        
        Advance advanceToBeSaved = new Advance();
        advanceToBeSaved.setActivityID(advance.getActivityID());
        advanceToBeSaved.setFileID(savedFileGeneratedId);
        advanceToBeSaved.setTitle(advanceTitleTextField.getText());
        advanceToBeSaved.setComment(advanceCommentsTextArea.getText());
        
        AdvanceDAO advanceDAO = new AdvanceDAO();
        
        int result = 0;
        try {
            result = advanceDAO.updateAdvanceInfo(advance.getAdvanceID(), advanceToBeSaved);
        } catch (DataRetrievalException exception) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        } finally {
            if (result > 0) {
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.CONFIRMATION, "Operación exitosa", "Se ha guardado el nuevo avance correctamente.");
            }
        }
    }
    
    @FXML
    private void uploadFileToAdvance(ActionEvent event) {    
        if (!hasFile) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccione el archivo a entregar");
            java.io.File file = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());

            if(file != null){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityFileItem.fxml"));
                    Pane pane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    controller.setFile(file);
                    controller.hideDownloadButton();
                    advanceFileVBox.getChildren().add(pane);
                    this.filePath = file.getPath();
                    hasFile = true;
                }catch (IOException exception){
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.ERROR, "El avance ya tiene un archivo adjunto", "Quite el archivo adjunto antes de agregar uno nuevo.");
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
        this.advanceCommentsTextArea.setText(advance.getComment());
        showAdvanceFile();
    }
    
    public void loadHeader() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            headerPane.getChildren().add(header);
            
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    private void showAdvanceFile() {
        FileDAO fileDAO = new FileDAO();
        File file;
        
        if (advance.getFileID() != 0) {
            try {
                file = fileDAO.getFileByID(advance.getFileID());

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityFileItem.fxml"));               
                try {
                    Pane advancePane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    controller.setFile(file.getFilePath());
                    controller.hideDownloadButton();

                    advanceFileVBox.getChildren().add(advancePane);
                    hasFile = true;
                } catch(IOException exception){
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
            } catch (DataRetrievalException exception) {
                new AlertPopUpGenerator().showConnectionErrorMessage();
            }
        }
        
    }
    
    private int saveFile() {
        int result = 0;
        if (this.filePath != null) {
                FileDAO fileDAO = new FileDAO();
                try {
                    result = fileDAO.addFile(this.filePath);
                } catch (DataInsertionException die) {
                    new AlertPopUpGenerator().showConnectionErrorMessage();
                }
        }
        return result;
    }
}


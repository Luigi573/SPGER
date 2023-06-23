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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityFileItemController;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataDeletionException;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import org.apache.commons.io.FileUtils;

public class ModifyAdvanceController {
    private Activity activity;
    private Advance advance;
    private boolean hasFile;
    private Course course;
    private String filePath;
    private User user;
    private java.io.File file;
    

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
    private void returnToAdvanceInfo(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/AdvanceInfo.fxml"));
            Parent parent = loader.load();
            AdvanceInfoController controller = (AdvanceInfoController)loader.getController();
            controller.setActivity(activity);
            controller.setAdvance(advance);
            controller.setCourse(course);
            controller.setUser(user);
            controller.loadHeader();
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }

    @FXML
    private void updateAdvanceInfo(ActionEvent event) {
        int savedFileGeneratedId = 0;
         
        if (advanceFileVBox.getChildren().isEmpty()) {
            advance.setFileID(savedFileGeneratedId);
        } else {
            savedFileGeneratedId = saveFile();
            advance.setFileID(savedFileGeneratedId);
        }
        advance.setTitle(advanceTitleTextField.getText());
        advance.setComment(advanceCommentsTextArea.getText());
        
        AdvanceDAO advanceDAO = new AdvanceDAO();
        FileDAO fileDAO = new FileDAO();
        
        int result = 0;
        try {
            result = advanceDAO.updateAdvanceInfo(advance.getAdvanceID(), advance);
            if(advance.getFileID() == 0){
                System.out.println(file.getPath());
                fileDAO.removeActivityFile(file.getPath());
                file.delete();
            }    
        } catch (DataRetrievalException | DataDeletionException exception) {
            new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.ERROR, "Error al borrar al guardar la nueva información del avance", exception.getMessage());
        } finally {
            if (result > 0) {
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.CONFIRMATION, "Operación exitosa", "Se ha guardado el nuevo avance correctamente.");
                
                returnToAdvanceInfo(event);
            }
        }
    }
    
    @FXML
    private void uploadFileToAdvance(ActionEvent event) {    
        if (!hasFile) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccione el archivo a entregar");
            file = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());

            if(file != null){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityFileItem.fxml"));
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
            if(user != null){
                Pane header = loader.load();
                HeaderPaneController controller = (HeaderPaneController)loader.getController();
                controller.setCourse(course);
                controller.setUser(user);
                
                headerPane.getChildren().setAll(header);
            }
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    private void showAdvanceFile() {
        FileDAO fileDAO = new FileDAO();
        File fileToBeShown;
        
        if (advance.getFileID() != 0) {
            try {
                fileToBeShown = fileDAO.getFileByID(advance.getFileID());
                file = new java.io.File(fileToBeShown.getFilePath());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityFileItem.fxml"));               
                try {
                    Pane advancePane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    controller.setFile(fileToBeShown.getFilePath());
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
        
        if (file.getPath() != null) {
            String newDirectoryPath = System.getProperty("user.dir") + "\\Evidencias\\" + String.valueOf(user.getUserId()) + user.getFirstSurname() + user.getSecondSurname() + user.getName().replaceAll("\\s+", "") + "\\Avances";
            java.io.File userDirectory = new java.io.File(newDirectoryPath);
            if (!userDirectory.exists()) {
                if (!userDirectory.mkdirs()) {
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.ERROR, "Error al guardar archivo", "No se pudo guardar la copia del archivo en el servidor.");
                }
            }

            String newFilePath = newDirectoryPath + "\\" + file.getName();
            java.io.File fileCopy = new java.io.File(newFilePath);
            boolean willSaveFile = true;
            if (fileCopy.exists()) {
                ButtonType buttonPressed = new AlertPopUpGenerator().showConfirmationMessage(Alert.AlertType.CONFIRMATION, "El Archivo ya existe", "Previamente subió un archivo con el mismo nombre, ¿Desea sobreescribir dicho archivo?").get();
                if (buttonPressed != ButtonType.OK) {
                    willSaveFile = false;
                }
            }
            
            if (willSaveFile) {
                try {
                    FileUtils.copyFile(file, fileCopy);
                } catch (IOException exception) {
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.ERROR, "Error al guardar archivo", "No se pudo guardar la copia del archivo en el servidor.");
                }

                FileDAO fileDAO = new FileDAO();
                try {
                    result = fileDAO.addFile(newFilePath);
                } catch (DataInsertionException die) {
                    new AlertPopUpGenerator().showConnectionErrorMessage();
                }
            } else {
                result = -1;
            }
        }
        return result;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setCourse(Course course){
        this.course = course;
    }
    
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}


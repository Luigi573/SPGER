package mx.uv.fei.gui.controllers.chronogram.advances;

import java.io.File;
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
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityInfoController;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import org.apache.commons.io.FileUtils;

public class CreateNewAdvanceController {
    private Activity activity;
    private Course course;
    private User user;
    private File file;
    
    @FXML
    private Button createAdvanceButton;  
    @FXML
    private Button uploadFileButton; 
    @FXML
    private Pane headerPane;
    @FXML
    private TextArea advanceCommentsTextArea;
    @FXML
    private TextField advanceTitleTextField;
    @FXML
    private VBox fileVBox;

    @FXML
    private void createAdvance(ActionEvent event) {
        int savedFileGeneratedId = saveFile();
        
        Advance advance = new Advance();
        advance.setActivityID(this.activity.getId());
        advance.setFileID(savedFileGeneratedId);
        advance.setTitle(advanceTitleTextField.getText());
        advance.setComment(advanceCommentsTextArea.getText());
        advance.setState("Por revisar");
        
        AdvanceDAO advanceDAO = new AdvanceDAO();
        int result = 0;
        
        try {
            result = advanceDAO.addAdvance(advance);
        } catch (DataInsertionException exception) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        } finally {
            if (result > 0) {
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.INFORMATION, "Operación exitosa", "Se ha guardado el nuevo avance correctamente.");
                returnToAdvanceList(event);
            }
        }
    }

    @FXML
    private void returnToAdvanceList(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityInfo.fxml"));
            Parent parent = loader.load();
            ActivityInfoController controller = (ActivityInfoController)loader.getController();
            controller.setActivity(activity);
            controller.setCourse(course);
            controller.setUser(user);
            controller.loadAdvances();
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
    private void uploadFileToAdvance(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione el archivo a entregar");
        file = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());
        
        if (file != null) {
            try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityFileItem.fxml"));
                    Pane pane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    controller.setFile(file);
                    controller.hideDownloadButton();
                    
                    fileVBox.getChildren().setAll(pane);
                } catch (IOException exception) {
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
        }
    }

    public void loadHeader(){
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
    
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    
    public void setCourse(Course course){
        this.course = course;
    }
    
    public void setUser(User user){
        this.user = user;
    }
        
    private int saveFile() {
        int result = 0;
        
        if (file.getPath() != null) {         
            FileDAO fileDAO = new FileDAO();
            
            String newDirectoryPath = "C:\\Users\\Jesús Manuel\\Desktop\\SPGER\\Evidencias\\" + String.valueOf(user.getUserId()) + user.getFirstSurname() + user.getSecondSurname() + user.getName() + "\\Avances";
            File userDirectory = new File(newDirectoryPath);
            if (!userDirectory.exists()) {
                if (!userDirectory.mkdirs()) {
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.ERROR, "Error al guardar archivo", "No se pudo guardar la copia del archivo en el servidor.");
                }
            }

            String newFilePath = newDirectoryPath + "\\" + file.getName();
            File fileCopy = new File(newFilePath);
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

                try {
                    result = fileDAO.addFile(newFilePath);
                } catch (DataInsertionException die) {
                    new AlertPopUpGenerator().showConnectionErrorMessage();
                }
            }
        }
        return result;
    }
}


package mx.uv.fei.gui.controllers.chronogram.activities;

import java.io.IOException;
import java.sql.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class CreateActivityController{
    private int researchId;
    
    @FXML
    private DatePicker startDatePicker; 
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private Pane headerPane;            
    @FXML
    private TextField activityTitleTextField;
    @FXML
    private TextArea activityDescriptionTextArea;
    
    @FXML
    private void initialize(){
        loadHeader();
    }
    
    @FXML
    private void createActivity(ActionEvent event) {
        try{
            Date startDate = Date.valueOf(startDatePicker.getValue());
            Date dueDate = Date.valueOf(dueDatePicker.getValue());
            
            Activity activity = new Activity();
            activity.setTitle(activityTitleTextField.getText().trim());
            activity.setDescription(activityDescriptionTextArea.getText().trim());
            activity.setStartDate(startDate);
            activity.setDueDate(dueDate);
            activity.setResearchId(researchId);
        
            ActivityDAO activityDAO = new ActivityDAO();
        
            if(activityDAO.assertActivity(activity)){
                try{
                    if(activityDAO.addActivity(activity) > 0){
                        Alert successMessage = new Alert(Alert.AlertType.INFORMATION);
                        successMessage.setHeaderText("Mensaje de éxito");
                        successMessage.setContentText("Actividad creada exitosamente");
                        successMessage.showAndWait();
                    
                        returnToChronogram(event);
                    }
                }catch(DataInsertionException exception){
                    new AlertPopUpGenerator().showConnectionErrorMessage();
                }
            }else{
                Alert warningMessage = new Alert(Alert.AlertType.WARNING);
                warningMessage.setHeaderText("No se puede crear la actividad");
                warningMessage.setContentText("Favor de llenar todos los campos");
                warningMessage.showAndWait();
            }
        }catch(NullPointerException exception){
            Alert warningMessage = new Alert(Alert.AlertType.WARNING);
            warningMessage.setHeaderText("No se puede crear la actividad");
            warningMessage.setContentText("Favor de seleccionar una fecha válida");
            warningMessage.showAndWait();
        }
    }
    @FXML
    private void returnToChronogram(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/Chronogram.fxml"));
            Parent parent = loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    private void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            headerPane.getChildren().add(header);
            
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    public void setResearchId(int researchId){
        this.researchId = researchId;
    }
}
package mx.uv.fei.gui.controllers.chronogram;

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
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class CreateActivityController{
    private int researchId;
    private Parent parent;
    private Scene scene;
    private Stage stage;
    
    @FXML
    private DatePicker startDatePicker; 
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private TextField activityTitleTextField;
    @FXML
    private TextArea activityDescriptionTextArea;
    
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
                }catch(DataWritingException exception){
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error de conexión");
                    errorMessage.setContentText("Favor de verificar su conexión a internet e inténtelo de nuevo");
                    errorMessage.showAndWait();
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
            parent = loader.load();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setTitle("Error");
            errorMessage.setContentText("Hubo un error al cargar el cronograma, archivo no encontrado");
            errorMessage.showAndWait();
        }
    }
    public void setResearchId(int researchId){
        this.researchId = researchId;
    }
}
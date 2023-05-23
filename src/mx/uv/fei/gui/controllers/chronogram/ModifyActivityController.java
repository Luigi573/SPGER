package mx.uv.fei.gui.controllers.chronogram;

import java.io.IOException;
import java.sql.Date;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class ModifyActivityController{
    private Activity activity;
    
    @FXML
    private DatePicker startDatePicker; 
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private TextField activityTitleTextField;
    @FXML
    private TextArea activityDescriptionTextArea;
    
    @FXML
    private void saveChanges(ActionEvent event) {
        Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationMessage.setHeaderText("Guardar cambios");
        confirmationMessage.setContentText("¿Está seguro que desea modificar la actividad y volver al cronograma?");
        
        Optional<ButtonType> choice = confirmationMessage.showAndWait();
        if(choice.isPresent() && choice.get() == ButtonType.OK){
            ActivityDAO activityDAO = new ActivityDAO();
            
            activity.setDescription(activityDescriptionTextArea.getText().trim());
            activity.setTitle(activityTitleTextField.getText().trim());
            activity.setStartDate(Date.valueOf(startDatePicker.getValue()));
            activity.setDueDate(Date.valueOf(dueDatePicker.getValue()));
        
            if(activityDAO.assertActivity(activity)){
                try{
                    if(activityDAO.modifyActivity(activity) > 0){
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
                warningMessage.setContentText("Favor de llenar todos los campos correctamente");
                warningMessage.showAndWait();
            }
        }
    }
    @FXML
    private void cancelChanges(ActionEvent event) {
        Alert confirmationMessage = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationMessage.setHeaderText("Volver a cronograma");
        confirmationMessage.setContentText("¿Está seguro que desea cancelar los cambios a la actividad?");
        
        Optional<ButtonType> choice = confirmationMessage.showAndWait();
        if(choice.isPresent() && choice.get() == ButtonType.OK){
            returnToChronogram(event);
        }
    }
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
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setTitle("Error");
            errorMessage.setContentText("Hubo un error al cargar el cronograma, archivo no encontrado");
            errorMessage.showAndWait();
        }
    }
    public void setAcitivty(Activity activity){
        this.activity = activity;
        startDatePicker.setValue(activity.getStartDate().toLocalDate());
        dueDatePicker.setValue(activity.getDueDate().toLocalDate());
        activityTitleTextField.setText(activity.getTitle());
        activityDescriptionTextArea.setText(activity.getDescription());
    }
}

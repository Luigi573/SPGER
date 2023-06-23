package mx.uv.fei.gui.controllers.chronogram.activities;

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
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.chronogram.ChronogramController;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class ModifyActivityController{
    private Activity activity;
    private Course course;
    private User user;
    
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
            if(startDatePicker.getValue() != null && dueDatePicker.getValue() != null){
            activity.setDescription(activityDescriptionTextArea.getText().trim());
            activity.setTitle(activityTitleTextField.getText().trim());
            activity.setStartDate(Date.valueOf(startDatePicker.getValue()));
            activity.setDueDate(Date.valueOf(dueDatePicker.getValue()));
            
            ActivityDAO activityDAO = new ActivityDAO();

            if(!activityDAO.isBlank(activity)){
                if(activityDAO.isValidTitle(activity)){
                    if(activityDAO.isValidDate(activity)){
                        try{
                            if(activityDAO.addActivity(activity) > 0){
                                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.INFORMATION, "Mensaje de éxito", "Actividad creada exitosamente");

                                returnToChronogram(event);
                            }
                        }catch(DataInsertionException exception){
                            new AlertPopUpGenerator().showConnectionErrorMessage();
                        }
                    }else{
                        new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede crear la actividad", "La fecha de inicio no puede ser mayor a la fecha fin y la fecha de inicio no puede ser anterior a la fecha actual");
                    }
                }else{
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede crear la actividad", "El título es demasiado largo");
                }
            }else{
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede crear la actividad", "Favor de llenar todos los campos");
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede crear la actividad", "Favor de seleccionar una fecha válida");
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
            ChronogramController controller = (ChronogramController)loader.getController();
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
    public void setAcitivty(Activity activity){
        this.activity = activity;
        startDatePicker.setValue(activity.getStartDate().toLocalDate());
        dueDatePicker.setValue(activity.getDueDate().toLocalDate());
        activityTitleTextField.setText(activity.getTitle());
        activityDescriptionTextArea.setText(activity.getDescription());
    }
    
    public void setCourse(Course course){
        this.course = course;
    }
    
    public void setUser(User user){
        this.user = user;
    }
}

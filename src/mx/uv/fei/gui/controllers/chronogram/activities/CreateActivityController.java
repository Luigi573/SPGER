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
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.gui.controllers.chronogram.ChronogramController;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class CreateActivityController{
    private int researchId;
    private Course course;
    private User user;
    
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
    private void createActivity(ActionEvent event) {
        if(startDatePicker.getValue() != null && dueDatePicker.getValue() != null){
            Date startDate = Date.valueOf(startDatePicker.getValue());
            Date dueDate = Date.valueOf(dueDatePicker.getValue());

            Activity activity = new Activity();
            activity.setTitle(activityTitleTextField.getText().trim());
            activity.setDescription(activityDescriptionTextArea.getText().trim());
            activity.setStartDate(startDate);
            activity.setDueDate(dueDate);
            activity.setResearchId(researchId);

            ActivityDAO activityDAO = new ActivityDAO();

            if(!activityDAO.isBlank(activity)){
                if(activityDAO.isValidTitle(activity)){
                    try{
                        if(activityDAO.addActivity(activity) > 0){
                            new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.INFORMATION, "Mensaje de éxito", "Actividad creada exitosamente");
                            
                            returnToChronogram(event);
                        }
                    }catch(DataInsertionException exception){
                        new AlertPopUpGenerator().showConnectionErrorMessage();
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
    
    public void setResearchId(int researchId){
        this.researchId = researchId;
    }
    
    public void setUser(User user){
        this.user = user;
    }
    
    public void setCourse(Course course){
        this.course = course;
        
         
    }
    
    public void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            HeaderPaneController controller = (HeaderPaneController)loader.getController();
            
            if(user != null){
                controller.setUser(user);
            }
            
            if(course != null){
                controller.setCourse(course);
            }
            
            headerPane.getChildren().setAll(header);
        }catch(IOException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
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
}
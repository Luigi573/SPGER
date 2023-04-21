package mx.uv.fei.gui;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataWritingException;

public class CreateActivityController{
    private ActivityDAO activityDAO;
    private Date startDate;
    private Date dueDate;
    private Parent parent;
    private Scene scene;
    private Stage stage;
    private String title;
    private String description;
    
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
        activityDAO = new ActivityDAO();
        description = activityDescriptionTextArea.getText();
        title = activityTitleTextField.getText();
        setStartDate(event);
        setDueDate(event);
        
        Activity activity = new Activity(title, description, startDate, dueDate);
        
        if(activityDAO.assertActivity(activity)){
            try{
                if(activityDAO.addActivity(activity) > 0){
                    //Instance of GUI_SUCCESS_MESSAGE
                    System.out.println("Actividad creada correctamente");
                    
                    try{
                        returnToChronogram(event);
                    }catch(IOException exception){
                        System.out.println("Error al cargar Chronogram.FXML");
                    }
                }
            }catch(DataWritingException exception){
                //Instance of GUI_ERROR_MESSAGE
                System.out.println(exception.getMessage());
            }
        }else{
            System.out.println("Error al crear la actividad. Faltan campos por llenar o hay informacion invalida");
        }
    }
    @FXML
    private void setStartDate(ActionEvent event){
        try{
            LocalDate fecha = startDatePicker.getValue();
            startDate = Date.valueOf(fecha);
        }catch(NullPointerException exception){
            //Instance of GUI_ERROR_MESSAGE
        }
    }
    @FXML
    private void setDueDate(ActionEvent event){
        try{
        LocalDate fecha = dueDatePicker.getValue();
        dueDate = Date.valueOf(fecha);
        }catch(NullPointerException exception){
            //Instance of GUI_ERROR_MESSAGE
        }
    }
    @FXML
    private void returnToChronogram(ActionEvent event) throws IOException{
        parent = new FXMLLoader(getClass().getResource("Chronogram.fxml")).load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(parent);
        stage.setTitle("SPGER");
        stage.setScene(scene);
        stage.show();
    }
}